package com.bwzk.pojo;

import java.io.Serializable;

public class OaDep implements Serializable {
    private String id;

    private String departmentname;

    private String supdepid;

    private String subcompanyid1;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname == null ? null : departmentname.trim();
    }

    public String getSupdepid() {
        return supdepid;
    }

    public void setSupdepid(String supdepid) {
        this.supdepid = supdepid == null ? null : supdepid.trim();
    }

    public String getSubcompanyid1() {
        return subcompanyid1;
    }

    public void setSubcompanyid1(String subcompanyid1) {
        this.subcompanyid1 = subcompanyid1 == null ? null : subcompanyid1.trim();
    }
}