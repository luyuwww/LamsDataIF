package com.bwzk.pojo.jsonbean;
import java.util.Date;
public class DelItem {

    private int libcode;
    private int dirid;
    private String efilename;
    private String deletor;
    private Date opertime;
	public int getLibcode() {
		return libcode;
	}
	public void setLibcode(int libcode) {
		this.libcode = libcode;
	}
	public int getDirid() {
		return dirid;
	}
	public void setDirid(int dirid) {
		this.dirid = dirid;
	}
	public String getEfilename() {
		return efilename;
	}
	public void setEfilename(String efilename) {
		this.efilename = efilename;
	}
	public String getDeletor() {
		return deletor;
	}
	public void setDeletor(String deletor) {
		this.deletor = deletor;
	}
	public Date getOpertime() {
		return opertime;
	}
	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}
     

}