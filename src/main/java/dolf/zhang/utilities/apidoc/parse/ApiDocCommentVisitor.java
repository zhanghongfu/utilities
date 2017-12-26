package dolf.zhang.utilities.apidoc.parse;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;
import dolf.zhang.utilities.BaseEntity;
import dolf.zhang.utilities.BaseVo;
import dolf.zhang.utilities.apidoc.entity.ApiEntity;
import dolf.zhang.utilities.apidoc.entity.ParameterEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 说明: TODO
 *
 * @author zhanghongfu
 * @date 2017/12/25
 */
public class ApiDocCommentVisitor extends VoidVisitorAdapter {

    private List<ApiEntity> apis = new ArrayList<>(10);

    public List<ApiEntity> getApis() {
        return apis;
    }


    @Override
    public void visit(ClassOrInterfaceDeclaration n, Object cu) {
        CompilationUnit p = (CompilationUnit) cu;
        String className = n.getNameAsString();
        String packagePath = p.getPackageDeclaration().get().getNameAsString();
        try {
            //1.通过反射检查类是否存在
            Class<?> clazz = Class.forName(packagePath + "." + className);
            // 2.读取类上注释
            Optional<JavadocComment> javadocComment = n.getJavadocComment();
            Map classDocMap = new HashMap<>(10);
            if (javadocComment.isPresent()) {
                JavadocComment javadocComment1 = javadocComment.get();
                getJavadocComment(javadocComment1.parse(), classDocMap);
            }

            //3.读取类上注解

            List<String> urls = new ArrayList<>(10);

            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                urls = Arrays.asList(requestMapping.path());
                if (CollectionUtils.isEmpty(urls)) {
                    urls = Arrays.asList(requestMapping.value());
                }
            } else {
                urls.add(clazz.getName());
            }

            Map<String, Map> methodDocMaps = new HashMap<>(2);
            //读取方法注释
            Map<String, Map> methodParamAnnotationsMaps = new HashMap<>(1);
            Map<String, List<ParameterEntity>> methodParamMaps = new HashMap<>(1);
            Map<String, List<ParameterEntity>> methodHeaderMaps = new HashMap<>(1);
            Map<String, List<ParameterEntity>> methodCookieMaps = new HashMap<>(1);
            NodeList<BodyDeclaration<?>> members = n.getMembers();
            for (BodyDeclaration body : members) {
                List<ParameterEntity> parameterEntities = new ArrayList<>(1);
                List<ParameterEntity> headerEntities = new ArrayList<>(1);
                List<ParameterEntity> cookieEntities = new ArrayList<>(1);
                Map methodDocMap = new HashMap(10);

                Optional<Comment> comment = body.getComment();
                if (comment.isPresent()) {
                    Comment com = comment.get();
                    JavadocComment javadocComment1 = com.asJavadocComment();
                    Javadoc parse = javadocComment1.parse();
                    getJavadocComment(parse, methodDocMap);
                }
                methodDocMaps.put(body.asMethodDeclaration().getNameAsString(), methodDocMap);


                MethodDeclaration methodDeclaration = body.asMethodDeclaration();
                NodeList<Parameter> parameters = methodDeclaration.getParameters();
                Map parMap = new HashMap(1);
                for (Parameter parameter : parameters) {
                    if ("HttpServletRequest".equals(parameter.getType().asString()) || "HttpServletResponse".equals(parameter.getType().asString())) {
                        continue;
                    } else {

                        NodeList<AnnotationExpr> annotations = parameter.getAnnotations();
                        if (annotations.isEmpty()) {
                            parMap.put(parameter.getType().asString(), parameter.getName());
                            methodParamAnnotationsMaps.put(body.asMethodDeclaration().getNameAsString(), parMap);
                        } else {

                            AnnotationExpr annotationExpr = annotations.get(0);
                            if ("RequestHeader".equals(annotationExpr.getNameAsString())) {
                                getParamAnnotations(headerEntities, methodDocMap, annotations, annotationExpr);
                                methodHeaderMaps.put(body.asMethodDeclaration().getNameAsString(), headerEntities);
                            } else if ("CookieValue".equals(annotationExpr.getNameAsString())) {
                                getParamAnnotations(cookieEntities, methodDocMap, annotations, annotationExpr);
                                methodCookieMaps.put(body.asMethodDeclaration().getNameAsString(), cookieEntities);
                            } else {
                                getParamAnnotations(parameterEntities, methodDocMap, annotations, annotationExpr);
                                methodParamMaps.put(body.asMethodDeclaration().getNameAsString(), parameterEntities);
                            }
                        }
                    }
                }


            }
            //读取方法上所有注解
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                //获取方法上注解
                List<String> type = new ArrayList<>(2);
                List<String> methodUrls = getMethodAnnotation(method, urls, type);
                List<ParameterEntity> parameterEntities = methodParamMaps.get(method.getName());
                List<ParameterEntity> cookieEntities = methodCookieMaps.get(method.getName());
                List<ParameterEntity> headerEntities = methodHeaderMaps.get(method.getName());
                if (CollectionUtils.isEmpty(parameterEntities)) {
                    parameterEntities = new ArrayList<>(2);
                }
                ApiEntity api = new ApiEntity();
                api.setCookies(cookieEntities);
                api.setHeaders(headerEntities);
                //获取返回信息
                Class<?> returnType = method.getReturnType();
                if (BaseEntity.class.isAssignableFrom(method.getReturnType()) || BaseVo.class.isAssignableFrom(method.getReturnType())) {
                    String entityPath = returnType.getResource("").getPath() ;
                    entityPath = entityPath.replaceAll("target/classes", "src/main/java");
                    entityPath = entityPath + returnType.getName().replaceAll(returnType.getPackage().getName()+"\\.","") + ".java";
                    List<ParameterEntity> apiSuccesses = bean(entityPath);
                    api.setSuccess(apiSuccesses);
                }
                java.lang.reflect.Parameter[] parameters = method.getParameters();
                for (java.lang.reflect.Parameter parameter : parameters) {
                    Class classs = parameter.getType();
                    if (classs.getSuperclass() == BaseEntity.class) {
                        String entityPath = returnType.getResource("/").getPath() + returnType.getName().replaceAll("\\.", "/") + ".java";
                        entityPath = entityPath.replaceAll("target/classes", "src/main/java");
                        List<ParameterEntity> parameterEntityList = bean(entityPath);
                        parameterEntities.addAll(parameterEntityList);
                    }
                }
                api.setParameters(parameterEntities);
                Map map = methodDocMaps.get(method.getName());
                api.setVersion(MapUtils.getString(map, "version", MapUtils.getString(classDocMap, "version", "1.0.0")));
                api.setGroup(className);
                api.setType(type);
                api.setUrl(methodUrls);
                api.setGroupTitle(MapUtils.getString(classDocMap, "description", method.getName()));
                api.setAuthor(MapUtils.getString(classDocMap, "author", MapUtils.getString(map, "author", System.getenv("USERNAME"))));
                api.setDate(MapUtils.getString(map, "date", MapUtils.getString(classDocMap, "date", "")));
                api.setTitle(MapUtils.getString(map, "description", method.getName()));
                api.setName(method.getName());
                apis.add(api);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getParamAnnotations(List<ParameterEntity> headerEntities, Map methodDocMap, NodeList<AnnotationExpr> annotations, AnnotationExpr annotationExpr) {
        Optional<Node> parentNode1 = annotationExpr.getParentNode();
        Parameter par = (Parameter) parentNode1.get();
        //参数类型
        String parType = par.getType().asString();
        // 参数
        String parValue = par.getNameAsString();
        if (SingleMemberAnnotationExpr.class.isAssignableFrom(annotationExpr.getClass())) {
            ParameterEntity parameterEntity = new ParameterEntity();
            parameterEntity.setDescription(MapUtils.getString(methodDocMap, parValue, ""));

            SingleMemberAnnotationExpr annotation = (SingleMemberAnnotationExpr) annotations.get(0);
            Expression memberValue = annotation.getMemberValue();
            parValue = memberValue.asLiteralStringValueExpr().getValue();
            if (StringUtils.isEmpty(parameterEntity.getDescription())) {
                parameterEntity.setDescription(MapUtils.getString(methodDocMap, parValue, ""));
            }
            parameterEntity.setField(parValue);
            parameterEntity.setRequired(true);
            parameterEntity.setType(parType);
            headerEntities.add(parameterEntity);

        }else if (NormalAnnotationExpr.class.isAssignableFrom(annotationExpr.getClass())) {

            ParameterEntity parameterEntity = new ParameterEntity();
            parameterEntity.setDescription(MapUtils.getString(methodDocMap, parValue, ""));
            NormalAnnotationExpr annotation = (NormalAnnotationExpr) annotations.get(0);
            NodeList<MemberValuePair> pairs = annotation.getPairs();
            for (MemberValuePair pair : pairs) {
                String name = pair.getName().asString();
                String value = "";
                if (BooleanLiteralExpr.class.isAssignableFrom(pair.getValue().getClass())) {
                    BooleanLiteralExpr booleanLiteralExpr = (BooleanLiteralExpr) pair.getValue();
                    if ("required".equals(name)) {
                        parameterEntity.setRequired(booleanLiteralExpr.getValue());
                    }
                } else {
                    Expression value1 = pair.getValue();
                    if ("value".equals(name)) {
                        parValue = value1.asLiteralStringValueExpr().getValue();
                    }
                }

            }
            parameterEntity.setField(parValue);
            parameterEntity.setType(parType);
            if (StringUtils.isEmpty(parameterEntity.getDescription())) {
                parameterEntity.setDescription(MapUtils.getString(methodDocMap, parValue, ""));
            }
            headerEntities.add(parameterEntity);
        }else{
            ParameterEntity parameterEntity = new ParameterEntity();
            parameterEntity.setField(parValue);
            parameterEntity.setType(parType);
            parameterEntity.setDescription(MapUtils.getString(methodDocMap, parValue, ""));
            headerEntities.add(parameterEntity);
        }
    }

    private List<String> getMethodAnnotation(Method method, List<String> methodUrls, List<String> type) {
        List<String> urls = new ArrayList<>(2);
        Annotation[] declaredAnnotations = method.getAnnotations();
        for (Annotation annotation : declaredAnnotations) {
            if (annotation.annotationType().isAssignableFrom(RequestMapping.class)) {
                RequestMapping requestMapping = (RequestMapping) annotation;
                List<String> temp = Arrays.asList(requestMapping.path());
                if (CollectionUtils.isEmpty(temp)) {
                    temp = Arrays.asList(requestMapping.value());
                }
                if (CollectionUtils.isNotEmpty(temp)) {
                    for (String u : methodUrls) {
                        for (String t : temp) {
                            urls.add(u + "/" + t);
                        }
                    }
                }
                RequestMethod[] method1 = requestMapping.method();
                for (RequestMethod requestMethod : method1) {
                    type.add(requestMethod.name());
                }
            }
        }
        return urls;
    }

    private void getJavadocComment(Javadoc parse, Map map) {
        List<JavadocBlockTag> blockTags = parse.getBlockTags();
        for (JavadocBlockTag tag : blockTags) {
            if (tag.getName().isPresent()) {
                map.put(tag.getName().get(), tag.getContent().toText());
            } else {
                map.put(tag.getTagName(), tag.getContent().toText());
            }
        }
    }


    private List<ParameterEntity> bean(String path) {
        List<ParameterEntity> success = new ArrayList<>(10);
        try {
            CompilationUnit
                parse = JavaParser.parse(FileUtils.readFileToString(new File(path), "utf-8"));
            BeanDocCommentVisitor beanDocCommentVisitor = new BeanDocCommentVisitor();
            parse.accept(beanDocCommentVisitor, parse);
            success = beanDocCommentVisitor.getSuccess();
            return success;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }
}
