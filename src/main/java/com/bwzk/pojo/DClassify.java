package com.bwzk.pojo;

import java.io.Serializable;
import java.util.List;

public class DClassify implements Serializable{
	protected Integer did;
	protected Integer pid;
	protected String flcode;
	protected String flmc;
	protected String bz;

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getFlcode() {
		return flcode;
	}

	public void setFlcode(String flcode) {
		this.flcode = flcode;
	}

	public String getFlmc() {
		return flmc;
	}

	public void setFlmc(String flmc) {
		this.flmc = flmc;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
}
