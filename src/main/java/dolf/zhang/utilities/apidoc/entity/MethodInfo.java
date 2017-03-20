package dolf.zhang.utilities.apidoc.entity;

import java.util.List;

/**
 * @author dolf
 * @version V1.0
 * @Description 方法信息
 * @date 16/9/21
 */
public class MethodInfo {

    private String requestMapping = "";

    /** 名称 **/
    private String name = "";

    /** 请求类型 **/
    private String requestMethod = "";

    /** 返回值 **/
    private String responseBody = "{}";
    /** Request PayLoad **/
    private String requestPayLoad = "{}";

    /** 描述 **/
    private String description = "";
    /** 创建时间 **/
    private String createDate = "";
    /** 作者 **/
    private String author = "";

    /**参数信息**/
    private List<ParameterInfo> parameters;

    public String getRequestMapping() {
        return requestMapping;
    }

    public void setRequestMapping(String requestMapping) {
        this.requestMapping = requestMapping;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<ParameterInfo> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterInfo> parameters) {
        this.parameters = parameters;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }


    @Override
    public String toString() {
        return "MethodInfo{" +
                "requestMapping='" + requestMapping + '\'' +
                ", name='" + name + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", responseBody='" + responseBody + '\'' +
                ", requestPayLoad='" + requestPayLoad + '\'' +
                ", description='" + description + '\'' +
                ", createDate='" + createDate + '\'' +
                ", author='" + author + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
