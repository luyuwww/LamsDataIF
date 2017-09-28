package com.bwzk.pojo.searchPojo;/**
 * Created by DaMo on 2017-09-29.
 */

/**
 * @author DaMo
 * @UPDATE 2017-09-29-1:11
 * @since 2017-09-29-1:11
 */
public class  DelBean {
    /**
     * id : 唯一标识
     * type : 数据所属模块
     * date : 数据删除时间yyyy-MM-DD HH:mm:ss
     */

    private String id;
    private String type;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}