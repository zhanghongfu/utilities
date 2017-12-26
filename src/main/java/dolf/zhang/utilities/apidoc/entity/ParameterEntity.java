package dolf.zhang.utilities.apidoc.entity;


import dolf.zhang.utilities.BaseEntity;

/**
 * 说明: TODO
 * @author zhanghongfu
 * @version
 * @date 2017/12/26
 */
public class ParameterEntity extends BaseEntity {
    /**类型**/
    private String type ;
    /**字段**/
    private String field ;
    /**描述**/
    private String description ;
    /**是否必填**/
    private boolean required = true;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
