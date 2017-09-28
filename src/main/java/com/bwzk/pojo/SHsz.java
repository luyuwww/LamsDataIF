package com.bwzk.pojo;

import java.util.Date;

/**
 * SHsz entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SHsz implements java.io.Serializable {

	// Fields

	private Integer did;
	private String lb ;
	private Integer libcode;
	private String libname ;
	private String keyword ;
	private String title ;
	private Integer srcid;
	private String srctchname ;
	private String srctenname ;
	private String deltor ;
	private Date deltime ;
	private String bz ;

	// Constructors


	// Property accessors

	public Integer getDid() {
		return this.did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public String getLb() {
		return this.lb;
	}

	public void setLb(String lb) {
		this.lb = lb;
	}

	public Integer getLibcode() {
		return this.libcode;
	}

	public void setLibcode(Integer libcode) {
		this.libcode = libcode;
	}

	public String getLibname() {
		return this.libname;
	}

	public void setLibname(String libname) {
		this.libname = libname;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSrcid() {
		return this.srcid;
	}

	public void setSrcid(Integer srcid) {
		this.srcid = srcid;
	}

	public String getSrctchname() {
		return this.srctchname;
	}

	public void setSrctchname(String srctchname) {
		this.srctchname = srctchname;
	}

	public String getSrctenname() {
		return this.srctenname;
	}

	public void setSrctenname(String srctenname) {
		this.srctenname = srctenname;
	}

	public String getDeltor() {
		return this.deltor;
	}

	public void setDeltor(String deltor) {
		this.deltor = deltor;
	}

	public Date getDeltime() {
		return this.deltime;
	}

	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}