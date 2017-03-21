package dolf.zhang.utilities.mybatis.plugin;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author dolf
 * @version V1.0
 * @Description: services plugins
 * @date 16/7/12
 */
public class ControllerPlugins extends PluginAdapter {
    FullyQualifiedJavaType importedAutowired;
    FullyQualifiedJavaType importedService;
    FullyQualifiedJavaType controller;
    FullyQualifiedJavaType service;
    FullyQualifiedJavaType entity;
    String controllerPackage = "";
    String controllerProject = "";
    String servicesPackage = "";
    Map<String, String> errorCodeMap = new HashMap<String, String>();

    public ControllerPlugins() {
        super();
    }

    public static Map load(String path, Class clazz) {
        Map map = new HashMap();
        if (StringUtils.isEmpty(path))
            return null;
        InputStream in = null;
        Properties p = new Properties();
        InputStreamReader reader = null;
        BufferedReader breader = null;
        try {
            if (clazz != null) {
                reader = new InputStreamReader(clazz.getResourceAsStream(path), "utf-8");

                in = clazz.getResourceAsStream(path);
            } else {
                reader = new InputStreamReader(ControllerPlugins.class.getResourceAsStream(path), "utf-8");
            }
            breader = new BufferedReader(reader);
            String s = breader.readLine();
            while (StringUtils.isNotEmpty(s)) {
                String[] split = s.split("=");
                p.setProperty(split[0], split[1]);
                s = breader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(breader);
        }
        return p;
    }

    @Override
    public boolean validate(List<String> warnings) {
        errorCodeMap.put("500", "系统异常");
        errorCodeMap.put("404", "Data Not Found");
        errorCodeMap.put("403", "没有权限");
        errorCodeMap.put("400", "缺少必传参数");
        errorCodeMap.put("401", "用户未登陆或者token失效");
        importedAutowired = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
        importedService = new FullyQualifiedJavaType("org.springframework.stereotype.Service");
        controllerPackage = String.valueOf(properties.get("controllerPackage"));
        controllerProject = String.valueOf(properties.get("controllerProject"));
        servicesPackage = String.valueOf(properties.get("servicesPackage"));
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        System.out.println("controller plugin  generated");
        List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
        String entityPackage = introspectedTable.getBaseRecordType();
        entity = new FullyQualifiedJavaType(entityPackage);

        //获取实体包路径
        String packageEntity = context.getJavaModelGeneratorConfiguration().getTargetPackage();
        //实体名称
        String entityName = entityPackage.replaceAll(packageEntity + ".", "");
        String serviceName = entityName.replaceAll("Entity", "") + "Services";
        service = new FullyQualifiedJavaType(servicesPackage + "." + serviceName);
        String controllerName = entityName.replaceAll("Entity", "") + "Controller";
        controller = new FullyQualifiedJavaType(controllerPackage + "." + controllerName);
        FullyQualifiedJavaType baseController = new FullyQualifiedJavaType("cn.com.admaster.ipg.base.controller.BaseController");
        TopLevelClass topLevelClass = new TopLevelClass(controller);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.setSuperClass(baseController);

        topLevelClass.addAnnotation("@RestController");
        topLevelClass.addAnnotation("@RequestMapping");
        topLevelClass.addImportedType(baseController);
        topLevelClass.addImportedType(service);
        topLevelClass.addImportedType(entity);
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMethod"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.PathVariable"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));
//        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestBody"));
//        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.apache.commons.collections.MapUtils"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("javax.servlet.http.HttpServletRequest"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("javax.servlet.http.HttpServletResponse"));
//        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Map"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.apache.http.HttpStatus"));

        topLevelClass.addImportedType(entity);

        addField(topLevelClass, service);


        if (introspectedTable.hasPrimaryKeyColumns()) {
            //目前项目中不允许有联合主键 故不做处理
            List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
            for (IntrospectedColumn column : columns) {
                String columnType = column.getFullyQualifiedJavaType().getShortName();
                String columnName = column.getJavaProperty();
                //删除
                String methodName = columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length());
                topLevelClass.addMethod(delete(introspectedTable, entityName, columnType, columnName, methodName));
                topLevelClass.addMethod(select(introspectedTable, entityName, columnType, columnName, methodName));
                break;
            }
        }
        GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, controllerProject, context.getJavaFormatter());
        files.add(file);
        writei18n(introspectedTable);
        return files;
    }

    private Method delete(IntrospectedTable introspectedTable, String entityName, String columnType, String columnName, String servicesMethodName) {
        System.out.println("controller plugin  delele method ");

        errorCodeMap.put("parameter.invalid", "无效参数");
        String tableName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();
        Method method = new Method();
        method.setName("delete");
        method.addAnnotation("@RequestMapping( value = \"v1/" + tableName.replaceAll("tb_", "") + "s/{" + columnName + "}\" , method = RequestMethod.DELETE )");
        method.addException(new FullyQualifiedJavaType("Exception"));
        method.setReturnType(new FullyQualifiedJavaType("Object"));
        Parameter request = new Parameter(new FullyQualifiedJavaType("HttpServletRequest"), "request");
        Parameter response = new Parameter(new FullyQualifiedJavaType("HttpServletResponse"), "response");
        Parameter p = new Parameter(new FullyQualifiedJavaType(columnType), columnName);
        p.addAnnotation("@PathVariable");
        method.getParameters().add(0, request);
        method.getParameters().add(1, response);
        method.getParameters().add(2, p);
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuffer sb = new StringBuffer();
        sb.append("if(" + columnName +
                " == null ){\n");
        sb.append("\t\t\t throw new Exception(\"");
        sb.append("parameter.invalid");
        sb.append("\");\n");
        sb.append("\t\t}\n");
        sb.append("\t\t");
        sb.append(toLowerCase(service.getShortName()));
        sb.append(".deleteBy" + servicesMethodName);
        sb.append("(");
        sb.append(columnName);
        sb.append(")");
        sb.append(";\n");
        sb.append("\t\tresponse.setStatus(HttpStatus.SC_NO_CONTENT);\n");
        sb.append("\t\treturn \"\";");
        method.addBodyLine(sb.toString());
        return method;

    }

    private Method select(IntrospectedTable introspectedTable, String entityName, String columnType, String columnName, String servicesMethodName) {
        System.out.println("controller plugin  select method ");
        errorCodeMap.put("parameter.invalid", "无效参数");
        String tableName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();
        Method method = new Method();
        method.setName("selectBy" + servicesMethodName);
        method.addAnnotation("@RequestMapping( value = \"v1/" + tableName.replaceAll("tb_", "") + "s/{" + columnName + "}\" , method = RequestMethod.GET )");
        method.addException(new FullyQualifiedJavaType("Exception"));
        method.setReturnType(new FullyQualifiedJavaType("Object"));
        Parameter request = new Parameter(new FullyQualifiedJavaType("HttpServletRequest"), "request");
        Parameter response = new Parameter(new FullyQualifiedJavaType("HttpServletResponse"), "response");
        Parameter p = new Parameter(new FullyQualifiedJavaType(columnType), columnName);
        p.addAnnotation("@PathVariable");
        method.getParameters().add(0, request);
        method.getParameters().add(1, response);
        method.getParameters().add(2, p);

        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuffer sb = new StringBuffer();
        sb.append("if(" + columnName +
                " == null ){\n");
        sb.append("\t\t\t throw new Exception(\"");
        sb.append("parameter.invalid");
        sb.append("\");\n");
        sb.append("\t\t}\n");
        sb.append("\t\treturn ");
        sb.append(toLowerCase(service.getShortName()));
        sb.append(".selectBy" + servicesMethodName);
        sb.append("(");
        sb.append(columnName);
        sb.append(")");
        sb.append(";\n");
        method.addBodyLine(sb.toString());
        return method;

    }

    private Method insert(IntrospectedTable introspectedTable, String entityName) {
        System.out.println("controller plugin  insert method ");
        String tableName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();
        Method method = new Method();
        method.setName("insert");
        method.addException(new FullyQualifiedJavaType("Exception"));
        method.addAnnotation("@RequestMapping( value = \"v1/" + tableName.replaceAll("tb_", "") + "\" , method = RequestMethod.POST )");
        method.setReturnType(new FullyQualifiedJavaType("Object"));
        Parameter map = new Parameter(new FullyQualifiedJavaType("Map"), "map");
        map.addAnnotation("@RequestBody(required = false)");

        Parameter request = new Parameter(new FullyQualifiedJavaType("HttpServletRequest"), "request");
        Parameter response = new Parameter(new FullyQualifiedJavaType("HttpServletResponse"), "response");
        method.getParameters().add(0, request);
        method.getParameters().add(1, response);
        method.getParameters().add(2, map);
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuffer sb = new StringBuffer();
        sb.append("if (MapUtils.isEmpty(map))\n");
        sb.append("\t\t{\n");
        sb.append("\t\t}\n");
        sb.append("\t\t" + entityName + " entity = new " + entityName + "();\n");
        sb.append("\t\treturn ");
        sb.append(toLowerCase(service.getShortName()));
        sb.append(".insert");
        sb.append("(entity)");
        sb.append(";");
        method.addBodyLine(sb.toString());
        return method;
    }

    private void addField(TopLevelClass topLevelClass, FullyQualifiedJavaType dao) {
        System.out.println("controller plugin  add field ");
        Field field = new Field();
        String fieldDaoName = toLowerCase(dao.getShortName());
        field.setName(fieldDaoName); // 设置变量名
        field.setType(dao); // 类型
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addAnnotation("@Autowired");
        topLevelClass.addField(field);
    }

    protected String toLowerCase(String tableName) {
        StringBuilder sb = new StringBuilder(tableName);
        sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        return sb.toString();
    }

    private void writei18n(IntrospectedTable introspectedTable) {
//        System.out.println("controller plugin  writei18n ");
//        String file = getClass().getResource("/").getFile() + "i18n/";
//        Map load = load("/i18n/message.properties", getClass());
//        errorCodeMap.putAll(load);
//        List<String> lines = new ArrayList<String>();
//        try {
//            for (Map.Entry entry : errorCodeMap.entrySet()) {
//                lines.add(entry.getKey() + "=" + entry.getValue());
//            }
//            FileUtils.writeLines(new File(properties.get("i18nPackage") + "/message.properties"), "utf-8", lines);
//            FileUtils.writeLines(new File(properties.get("i18nPackage") + "/message_zh_CN.properties"), "utf-8", lines);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }

    }
}
