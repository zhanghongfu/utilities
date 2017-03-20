package dolf.zhang.utilities.apidoc.entity;

/**
 * @author dolf
 * @version V1.0
 * @Description 类信息
 * @date 16/9/21
 */
public class FieldInfo {

    private String type ;

    private String description;

    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
