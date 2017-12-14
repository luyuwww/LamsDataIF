package com.bwzk.pojo;

import java.io.Serializable;
import java.util.Date;

public class FlowMain implements Serializable {
    private Object id;

    private Object fid;

    private Object title;

    private Object applyauth;

    private Object applytype;

    private Long applytimes;

    private Date stardate;

    private Date enddate;

    private Date requesttime;

    private Object userid;

    private Object usercode;

    private Object username;

    private Object memo;

    private Long status;

    private Long result;

    private Object msg;

    private Object requestid;

    private Object ftriggerflag;

    private static final long serialVersionUID = 1L;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getFid() {
        return fid;
    }

    public void setFid(Object fid) {
        this.fid = fid;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getApplyauth() {
        return applyauth;
    }

    public void setApplyauth(Object applyauth) {
        this.applyauth = applyauth;
    }

    public Object getApplytype() {
        return applytype;
    }

    public void setApplytype(Object applytype) {
        this.applytype = applytype;
    }

    public Long getApplytimes() {
        return applytimes;
    }

    public void setApplytimes(Long applytimes) {
        this.applytimes = applytimes;
    }

    public Date getStardate() {
        return stardate;
    }

    public void setStardate(Date stardate) {
        this.stardate = stardate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Date getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(Date requesttime) {
        this.requesttime = requesttime;
    }

    public Object getUserid() {
        return userid;
    }

    public void setUserid(Object userid) {
        this.userid = userid;
    }

    public Object getUsercode() {
        return usercode;
    }

    public void setUsercode(Object usercode) {
        this.usercode = usercode;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getMemo() {
        return memo;
    }

    public void setMemo(Object memo) {
        this.memo = memo;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Object getRequestid() {
        return requestid;
    }

    public void setRequestid(Object requestid) {
        this.requestid = requestid;
    }

    public Object getFtriggerflag() {
        return ftriggerflag;
    }

    public void setFtriggerflag(Object ftriggerflag) {
        this.ftriggerflag = ftriggerflag;
    }
}