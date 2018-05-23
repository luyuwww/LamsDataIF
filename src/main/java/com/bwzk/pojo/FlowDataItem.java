package com.bwzk.pojo;

import java.io.Serializable;

public class FlowDataItem implements Serializable {
    private String id;

    private String pid;

    private String tablename;

    private String keyword;

    private String title;

    private String mj;

    private String bgqx;

    private Integer arcid;

    private Integer ys;

    private Integer fs;

    private  String bz;
    private  String datadepid;

    public String getDatadepid() {
        return datadepid;
    }

    public void setDatadepid(String datadepid) {
        this.datadepid = datadepid;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getYs() {
        return ys;
    }

    public void setYs(Integer ys) {
        this.ys = ys;
    }

    public Integer getFs() {
        return fs;
    }

    public void setFs(Integer fs) {
        this.fs = fs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj == null ? null : mj.trim();
    }

    public String getBgqx() {
        return bgqx;
    }

    public void setBgqx(String bgqx) {
        this.bgqx = bgqx == null ? null : bgqx.trim();
    }

    public Integer getArcid() {
        return arcid;
    }

    public void setArcid(Integer arcid) {
        this.arcid = arcid;
    }
}