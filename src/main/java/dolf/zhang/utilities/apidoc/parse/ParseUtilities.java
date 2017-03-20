package dolf.zhang.utilities.apidoc.parse;


import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import dolf.zhang.utilities.apidoc.entity.ClassInfo;
import dolf.zhang.utilities.apidoc.entity.FieldInfo;
import dolf.zhang.utilities.apidoc.entity.MethodInfo;
import dolf.zhang.utilities.apidoc.entity.ParameterInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Parse utilities.
 *
 * @author dolf  
 * @version V1.0
 * @date 16 /9/21
 * @description
 * @Description
 */
public class ParseUtilities {

    private ParseUtilities() {
    }


    /**
     * Read class entity class info.
     *
     * @param classs the classs
     * @return the class info
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     * @author dolf
     * @date 2017年03月20日 17:27:42
     * @description         生成实体说明代码
     * @responseBody
     * @requestPayLoad
     */
    public static ClassInfo readClassEntity(String classs) throws IOException, ParseException {
        ClassInfo classInfo = new ClassInfo();
        CompilationUnit cu;
        File file = new File(classs);
        cu = JavaParser.parse(file);
        ClassDocCommentVisitor classDocCommentVisitor = new ClassDocCommentVisitor();
        classDocCommentVisitor.visit(cu, null);

        classInfo = classDocCommentVisitor.getClassInfo();
        FieldVisitor fieldVisitor = new FieldVisitor();
        fieldVisitor.visit(cu, null);
        List<FieldInfo> fieldInfos = fieldVisitor.getFieldInfos();
        classInfo.setFieldInfos(fieldInfos);
        return classInfo;
    }


    /**
     * Read class api class info.
     *
     * @param classs the classs
     * @return the class info
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     * @author dolf
     * @date 2017年03月20日 17:27:43
     * @description         生成接口注释代码
     * @responseBody
     * @requestPayLoad
     */
    public static ClassInfo readClassApi(String classs) throws IOException, ParseException {
        ClassInfo classInfo = new ClassInfo();
        CompilationUnit cu;
        File file = new File(classs);
        cu = JavaParser.parse(file);
        ClassDocCommentVisitor classDocCommentVisitor = new ClassDocCommentVisitor();
        classDocCommentVisitor.visit(cu, null);
        classInfo = classDocCommentVisitor.getClassInfo();
        MethodVisitor methodVisitor = new MethodVisitor();
        methodVisitor.visit(cu, null);
        List<MethodInfo> methodInfos = methodVisitor.getMethodInfos();
        if (CollectionUtils.isNotEmpty(methodInfos)) {
            for (MethodInfo method : methodInfos) {
                if (StringUtils.isEmpty(method.getAuthor())) {
                    method.setAuthor(classInfo.getAuthor());
                }
                if (StringUtils.isEmpty(method.getCreateDate())) {
                    method.setCreateDate(classInfo.getCreateDate());
                }
            }
        }
        classInfo.setMethods(methodInfos);

        return classInfo;
    }

    private static void parseJavaDoc(String[] split, Map map) {
        for (String s : split) {
            String lineComment = s.replaceAll("\\*", "").replaceAll("\\r", "").replaceAll("\\n", "").trim();
            if (StringUtils.isNotEmpty(lineComment)) {
                if (lineComment.indexOf(" ") > -1) {
                    String key = lineComment.substring(0, lineComment.indexOf(" ")).trim();
                    String value = lineComment.substring(lineComment.indexOf(" "), lineComment.length()).trim();
                    if ("param".equals(key)) {
                        String[] split1 = value.split(" ");
                        if (split1.length > 1) {
                            key = value.substring(0, value.indexOf(" "));
                            value = value.substring(value.indexOf(" "), value.length()).trim();
                        }
                    }
                    map.put(key, value);
                }
            }
        }
    }

    /**
     * Gets key value for annotation.
     *
     * @param annotationSource the annotation source
     * @param annotationName   the annotation name
     * @return the key value for annotation
     */
    public static Map<String, String> getKeyValueForAnnotation(String annotationSource, String annotationName) {
        int index = annotationSource.indexOf(annotationName);
        Map<String, String> keyValue = new HashMap<String, String>();
        if (index != -1) {

            annotationSource = annotationSource.substring(annotationName.length() + 1); // remove the annotation name from it
            annotationSource = annotationSource.substring(1); // remove first bracakte
            annotationSource = annotationSource.substring(0, annotationSource.length() - 1);     // remove last brace
            annotationSource = annotationSource.replaceAll(" = ", "=");
            annotationSource = annotationSource.replaceAll(", ", ",");
            String[] statements = annotationSource.split(",");
            for (String statement : statements) {
                if (statement.indexOf("=") != -1) {  // it have key value pair
                    String[] keyValueStr = statement.split("=");
                    if (keyValueStr.length == 2) {
                        keyValue.put(keyValueStr[0], keyValueStr[1]);
                    }
                }
            }
        }
        return keyValue;
    }


    private static class FieldVisitor extends VoidVisitorAdapter {
        /**
         * The Field infos.
         */
        public List<FieldInfo> fieldInfos = new ArrayList<FieldInfo>();

        /**
         * Gets field infos.
         *
         * @return the field infos
         */
        public List<FieldInfo> getFieldInfos() {
            return this.fieldInfos;
        }

        @Override
        public void visit(FieldDeclaration n, Object arg) {
            if (n.getJavaDoc() != null) {
                FieldInfo fieldInfo = new FieldInfo();
                String content = n.getJavaDoc().getContent();
                fieldInfo.setDescription(content.trim());
                fieldInfo.setType(n.getType().toString());
                List<VariableDeclarator> variables = n.getVariables();
                if (CollectionUtils.isNotEmpty(variables)) {
                    String name = variables.get(0).getId().getName();
                    fieldInfo.setName(name);
                }
                fieldInfos.add(fieldInfo);
            }

        }


    }

    /**
     * 读取方法信息
     **/
    private static class MethodVisitor extends VoidVisitorAdapter {

        /**
         * The Method infos.
         */
        public List<MethodInfo> methodInfos = new ArrayList<MethodInfo>();

        /**
         * Gets method infos.
         *
         * @return the method infos
         */
        public List<MethodInfo> getMethodInfos() {
            return this.methodInfos;
        }

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            MethodInfo methodInfo = new MethodInfo();
            Map map = new HashMap();
            if (n.getJavaDoc() != null) {
                String content = n.getJavaDoc().getContent();

                String[] split = content.split("\\* @");

                parseJavaDoc(split, map);

                methodInfo.setName(n.getName());
                methodInfo.setDescription(MapUtils.getString(map, "description", ""));
                methodInfo.setAuthor(MapUtils.getString(map, "author", ""));
                methodInfo.setCreateDate(MapUtils.getString(map, "date", ""));
                methodInfo.setResponseBody(MapUtils.getString(map, "responseBody", ""));
                methodInfo.setResponseBody(MapUtils.getString(map, "responseBody", ""));
            }
            List<AnnotationExpr> methodAnnotations = n.getAnnotations();
            for (AnnotationExpr method : methodAnnotations) {
                if ("RequestMapping".equals(method.getName().getName())) {
                    Map<String, String> requestMapping = getKeyValueForAnnotation(method.toString(), "RequestMapping");
                    methodInfo.setRequestMethod(MapUtils.getString(requestMapping, "method", ""));
                    methodInfo.setRequestMapping(MapUtils.getString(requestMapping, "value", ""));
                }
            }
            //解析参数
            List<Parameter> parameters = n.getParameters();
            List<ParameterInfo> parameterInfo = new ArrayList<ParameterInfo>();
            for (Parameter p : parameters) {
                ParameterInfo info = new ParameterInfo();
                List<AnnotationExpr> annotations = p.getAnnotations();
                info.setName(p.getName());
                info.setType(p.getType().toString());
                if (CollectionUtils.isNotEmpty(annotations)) {
                    AnnotationExpr an = annotations.get(0);
                    NameExpr requestParam = an.getName();
                    if ("RequestParam".equals(requestParam.getName())) {
                        if (an.toString().indexOf(")") > 0) {
                            Map<String, String> requestMapping = getKeyValueForAnnotation(an.toString(), "RequestParam");
                            info.setDefaultValue(MapUtils.getString(requestMapping, "defaultValue", ""));
                            info.setRequired(MapUtils.getBoolean(requestMapping, "required", true));
                            info.setName(MapUtils.getString(requestMapping, "value", ""));
                        }
                    } else if ("PathVariable".equals(an.getName().getName())) {
                        if (an.toString().indexOf(")") > 0) {
                            Map<String, String> requestMapping = getKeyValueForAnnotation(an.toString(), "PathVariable");
                            info.setName(MapUtils.getString(requestMapping, "value", ""));
                        }

                    } else if ("RequestBody".equals(an.getName().getName())) {
                        if (an.toString().indexOf(")") > 0) {
                            Map<String, String> requestMapping = getKeyValueForAnnotation(an.toString(), "RequestBody");
                            info.setRequired(MapUtils.getBoolean(requestMapping, "required", true));
                        }
                        methodInfo.setResponseBody(MapUtils.getString(map, "responseBody", ""));
                    }

                    info.setDescription(MapUtils.getString(map, p.getName(), ""));
                    parameterInfo.add(info);
                }
            }
            methodInfo.setParameters(parameterInfo);
            methodInfos.add(methodInfo);
            super.visit(n, arg);
        }
    }

    /**
     * 读取类上注释
     *
     * @author dolf  
     * @date 2017年03月20日 17:27:42
     * @description
     */
    static class ClassDocCommentVisitor extends VoidVisitorAdapter {
        /**
         * The Info.
         */
        public ClassInfo info = null;

        /**
         * Gets class info.
         *
         * @return the class info
         */
        public ClassInfo getClassInfo() {

            return this.info;
        }


        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object cu) {
            JavadocComment javaDoc = n.getJavaDoc();
            info = new ClassInfo();
            if (javaDoc != null) {
                String con = javaDoc.getContent();
                String[] split = con.split("\\* @");
                Map map = new HashMap();
                parseJavaDoc(split, map);
                info.setName(n.getName());
                info.setAuthor(MapUtils.getString(map, "author", ""));
                info.setCreateDate(MapUtils.getString(map, "date", ""));
                info.setDescription(MapUtils.getString(map, "description", ""));
            }
        }
    }
}
