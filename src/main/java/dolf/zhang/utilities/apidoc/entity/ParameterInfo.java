package dolf.zhang.utilities.apidoc.entity;

/**
 * @author dolf
 * @version V1.0
 * @Description
 * @date 16/9/21
 */
public class ParameterInfo {

    /** 参数名称 **/
    private String name = "";

    /** 参数类型 **/
    private String type = "";

    /** 是否必须 **/
    private boolean required = true;

    /** 默认值 **/
    private String defaultValue = "";

    /** 描述 **/
    private String description= "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ParameterInfo{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", required=" + required +
                ", defaultValue='" + defaultValue + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
