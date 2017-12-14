package com.bwzk.pojo;

import java.io.Serializable;

public class OaOrg implements Serializable {
    private String id;

    private String subcompanyname;

    private String supsubcomid;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSubcompanyname() {
        return subcompanyname;
    }

    public void setSubcompanyname(String subcompanyname) {
        this.subcompanyname = subcompanyname == null ? null : subcompanyname.trim();
    }

    public String getSupsubcomid() {
        return supsubcomid;
    }

    public void setSupsubcomid(String supsubcomid) {
        this.supsubcomid = supsubcomid == null ? null : supsubcomid.trim();
    }
}