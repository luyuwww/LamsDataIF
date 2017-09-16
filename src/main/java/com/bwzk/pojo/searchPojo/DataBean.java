package com.bwzk.pojo.searchPojo;/**
 * Created by DaMo on 2017-09-16.
 */

/**
 * @author DaMo
 * @UPDATE 2017-09-16-20:35
 * @since 2017-09-16-20:35
 */
public class DataBean {
    /**
     * id : 当前数据唯一标识
     * title : 题名+文件名
     * filetype : 电子文件格式（.doc\.pdf\.excel等）
     * date : 数据最后修改时间yyyy-MM-DD HH:mm:ss
     * content : 档案文件正文内容
     * fanwei : 归档范围, flh
     * quanzong : 全宗类别, qzh
     * type : 档案分类,  dalx.chname
     * permission : 是否有权限控制 （1：带权限，0：不带权限）
     * users : 授权用户ID，多个用户使用,分割
     * groups : 档案分类, dalx.code
     * url : 档案查看浏览链接地址
     */

    private String id;
    private String title;
    private String filetype;
    private String date;
    private String content;
    private String fanwei;
    private String quanzong;
    private String type;
    private Integer permission;
    private String users;
    private Integer groups;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFanwei() {
        return fanwei;
    }

    public void setFanwei(String fanwei) {
        this.fanwei = fanwei;
    }

    public String getQuanzong() {
        return quanzong;
    }

    public void setQuanzong(String quanzong) {
        this.quanzong = quanzong;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public Integer getGroups() {
        return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}