package dolf.zhang.utilities.apidoc.entity;

import java.util.List;

/**
 * @author dolf
 * @version V1.0
 * @Description 类信息
 * @date 16/9/21
 */
public class ClassInfo {

    /** 作者 **/
    private String author = "";

    /** 描述 **/
    private String description = "";
    /** 创建时间 **/
    private String createDate = "";
    /** 创建时间 **/
    private String name = "";

    /**方法**/
    private List<MethodInfo> methods;

    /** 字段 **/
    private List<FieldInfo> fieldInfos;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description =  description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<MethodInfo> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodInfo> methods) {
        this.methods = methods;
    }


    public List<FieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    public void setFieldInfos(List<FieldInfo> fieldInfos) {
        this.fieldInfos = fieldInfos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
