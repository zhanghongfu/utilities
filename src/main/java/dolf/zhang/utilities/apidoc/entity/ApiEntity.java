package dolf.zhang.utilities.apidoc.entity;


import dolf.zhang.utilities.BaseEntity;

import java.util.List;

/**
 * 说明: TODO
 * @author zhanghongfu
 * @version
 * @date 2017/12/25
 */
public class ApiEntity extends BaseEntity {
    /**
     * 调用方式
     */
    private List<String> type ;

    /**
     * 地址
     */
    private List<String> url ;

    /**
     * 名称
     */
    private String title ;

    /**
     * 名字
     */
    private String name ;

    /**
     * 组
     */
    private String group ;

    /**
     * 版本
     */
    private String version ;

    /**
     * 组名称
     */
    private String groupTitle;

    /**作者**/
    private String author;

    /**
     * 时间
     */
    private String date;

    /**
     * 返回值
     */
    private List<ParameterEntity> success;

    /**
     * 参数
     */
    private List<ParameterEntity> parameters;

    /**
     * 请求头
     */
    private List<ParameterEntity> headers;

    /**
     * cookies
     */
    private List<ParameterEntity> cookies;

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ParameterEntity> getSuccess() {
        return success;
    }

    public void setSuccess(List<ParameterEntity> success) {
        this.success = success;
    }

    public List<ParameterEntity> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterEntity> parameters) {
        this.parameters = parameters;
    }


    public List<ParameterEntity> getHeaders() {
        return headers;
    }

    public void setHeaders(List<ParameterEntity> headers) {
        this.headers = headers;
    }

    public List<ParameterEntity> getCookies() {
        return cookies;
    }

    public void setCookies(List<ParameterEntity> cookies) {
        this.cookies = cookies;
    }
}
