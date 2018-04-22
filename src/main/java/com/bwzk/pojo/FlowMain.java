package com.bwzk.pojo;

import java.io.Serializable;
import java.util.Date;

public class FlowMain implements Serializable {
    private String id;

    private String fid;

    private String title;

    private String applyauth;

    private Integer applytype;

    private Integer applytimes;

    private String stardate;

    private String enddate;

    private String requesttime;

    private String userid;

    private String usercode;

    private String username;

    private String memo;

    private Integer status;

    private Integer result;

    private String msg;

    private String requestid;

    private String ftriggerflag;

    private String sqrbm;

    public String getSqrbm() {
        return sqrbm;
    }

    public void setSqrbm(String sqrbm) {
        this.sqrbm = sqrbm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getApplyauth() {
        return applyauth;
    }

    public void setApplyauth(String applyauth) {
        this.applyauth = applyauth == null ? null : applyauth.trim();
    }

    public Integer getApplytype() {
        return applytype;
    }

    public void setApplytype(Integer applytype) {
        this.applytype = applytype == null ? null : applytype;
    }

    public Integer getApplytimes() {
        return applytimes;
    }

    public void setApplytimes(Integer applytimes) {
        this.applytimes = applytimes;
    }

    public String getStardate() {
        return stardate;
    }

    public void setStardate(String stardate) {
        this.stardate = stardate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid == null ? null : requestid.trim();
    }

    public String getFtriggerflag() {
        return ftriggerflag;
    }

    public void setFtriggerflag(String ftriggerflag) {
        this.ftriggerflag = ftriggerflag == null ? null : ftriggerflag.trim();
    }
}