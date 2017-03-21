package dolf.zhang.utilities.mybatis.plugin;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dolf
 * @version V1.0
 * @Description: services plugins
 * @date 16/7/12
 */
public class ServicesPlugins extends PluginAdapter {
    FullyQualifiedJavaType importedAutowired;
    FullyQualifiedJavaType importedService;
    FullyQualifiedJavaType dao;
    FullyQualifiedJavaType service;
    FullyQualifiedJavaType entity;

    public ServicesPlugins() {
        super();
    }


    @Override
    public boolean validate(List<String> warnings) {
        importedAutowired = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
        importedService = new FullyQualifiedJavaType("org.springframework.stereotype.Service");
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();

        String entityPackage = introspectedTable.getBaseRecordType();
        entity = new FullyQualifiedJavaType(entityPackage);

        dao = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
        //获取实体包路径
        String packageEntity = context.getJavaModelGeneratorConfiguration().getTargetPackage();
        //实体名称
        String entityName = entityPackage.replaceAll(packageEntity + ".", "");
        String serviceName = entityName.replaceAll("Entity", "") + "Services";
        String servicePackage = String.valueOf(properties.get("servicesPackage"));
        service = new FullyQualifiedJavaType(servicePackage + "." + serviceName);
        TopLevelClass topLevelClass = new TopLevelClass(service);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        //加入spring  注解
        topLevelClass.addAnnotation("@Service");
        //导入包
        addServicesImport(topLevelClass);
        //新增属性
        addServicesField(topLevelClass, dao);
        //检查是否有主键
        if (introspectedTable.hasPrimaryKeyColumns()) {
            //目前项目中不允许有联合主键 故不做处理
            List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
            for (IntrospectedColumn column : columns) {
                String columnType = column.getFullyQualifiedJavaType().getShortName();
                String columnName = column.getJavaProperty();
//                if ("BIGINT".equals(column.getJdbcTypeName()))
//                    columnType = "Long";
//                else columnType = "String";
                //删除
                String methodName = columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length());
                topLevelClass.addMethod(delete(introspectedTable, "deleteBy" + methodName, columnName, columnType));
                topLevelClass.addMethod(select(introspectedTable, "selectBy" + methodName, columnName, columnType));
                topLevelClass.addMethod(update(introspectedTable));
                break;
            }
        }
        topLevelClass.addMethod(insert(introspectedTable, entityName));




        GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, context.getJavaModelGeneratorConfiguration().getTargetProject(), context.getJavaFormatter());
        files.add(file);
        return files;
    }

    private Method update(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("update");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        Parameter p = new Parameter(entity, "entity");
        method.getParameters().add(p);
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuffer sb = new StringBuffer();
        sb.append("return ");
        sb.append(toLowerCase(dao.getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getUpdateByPrimaryKeySelectiveStatementId());
        sb.append("(entity)");
        sb.append(";");
        method.addBodyLine(sb.toString());
        return method;
    }


    private Method select(IntrospectedTable introspectedTable, String methodName, String column, String type) {
        Method method = new Method();
        method.setName(methodName);
        method.setReturnType(entity);
        Parameter p = new Parameter(new FullyQualifiedJavaType(type), column);
        method.getParameters().add(p);
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuffer sb = new StringBuffer();
        sb.append("return ");
        sb.append(toLowerCase(dao.getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getSelectByPrimaryKeyStatementId());
        sb.append("(" + column +
                ")");
        sb.append(";");
        method.addBodyLine(sb.toString());
        return method;
    }

    private Method delete(IntrospectedTable introspectedTable, String methodName, String column, String type) {
        Method method = new Method();
        method.setName(methodName);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        Parameter p = new Parameter(new FullyQualifiedJavaType(type), column);
        method.getParameters().add(p);
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuffer sb = new StringBuffer();
        sb.append("return ");
        sb.append(toLowerCase(dao.getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getDeleteByPrimaryKeyStatementId());
        sb.append("(" + column +
                ")");
        sb.append(";");
        method.addBodyLine(sb.toString());
        return method;
    }

    private Method insert(IntrospectedTable introspectedTable, String entityName) {
        Method method = new Method();
        method.setName("insert");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        Parameter p = new Parameter(new FullyQualifiedJavaType(entityName), "entity");
        method.getParameters().add(p);
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuffer sb = new StringBuffer();
        sb.append("return ");
        sb.append(toLowerCase(dao.getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getInsertSelectiveStatementId());
        sb.append("(entity)");
        sb.append(";");
        method.addBodyLine(sb.toString());
        return method;
    }

    private void addServicesField(TopLevelClass topLevelClass, FullyQualifiedJavaType dao) {
        Field field = new Field();
        String fieldDaoName = toLowerCase(dao.getShortName());
        field.setName(fieldDaoName); // 设置变量名
        field.setType(dao); // 类型
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addAnnotation("@Autowired");
        topLevelClass.addField(field);
    }

    private void addServicesImport(TopLevelClass topLevelClass) {
        topLevelClass.addImportedType(importedService);
        topLevelClass.addImportedType(importedAutowired);
        topLevelClass.addImportedType(dao);
        topLevelClass.addImportedType(entity);
    }


    public List<Method> getMethod(TopLevelClass topLevelClass){
        return topLevelClass.getMethods();
    }

    protected String toLowerCase(String tableName) {
        StringBuilder sb = new StringBuilder(tableName);
        sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        return sb.toString();
    }
}
