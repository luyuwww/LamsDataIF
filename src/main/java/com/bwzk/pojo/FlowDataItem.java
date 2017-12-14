package com.bwzk.pojo;

import java.io.Serializable;

public class FlowDataItem implements Serializable {
    private Object id;

    private Object pid;

    private Object tablename;

    private Object keyword;

    private Object title;

    private Object mj;

    private Object bgqx;

    private Long arcid;

    private static final long serialVersionUID = 1L;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public Object getTablename() {
        return tablename;
    }

    public void setTablename(Object tablename) {
        this.tablename = tablename;
    }

    public Object getKeyword() {
        return keyword;
    }

    public void setKeyword(Object keyword) {
        this.keyword = keyword;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getMj() {
        return mj;
    }

    public void setMj(Object mj) {
        this.mj = mj;
    }

    public Object getBgqx() {
        return bgqx;
    }

    public void setBgqx(Object bgqx) {
        this.bgqx = bgqx;
    }

    public Long getArcid() {
        return arcid;
    }

    public void setArcid(Long arcid) {
        this.arcid = arcid;
    }
}