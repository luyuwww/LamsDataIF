package com.bwzk.pojo.jsonbean;
import java.util.Date;
public class DelItem {

    /**
     * item : {"pzm":111,"custcard":111,"attr":"Y","ext":"jpg","creator":111,"title":"微信图片_20180227175210","filesize":64516,"libcode":0,"md5":111,"efilename":"lambda/credit/20180316/5aab77c19d0225243000afad.jpg","filetype":111,"dirid":5,"opertime":111,"custid":"1000001348","custname":111}
     */

    private ItemBean delitem;

   

    public ItemBean getDelitem() {
		return delitem;
	}



	public void setDelitem(ItemBean delitem) {
		this.delitem = delitem;
	}



	public static class ItemBean {

        public ItemBean() {
        }



    public ItemBean(String libcode , String dirid , String  custid
                , String efilename,  Date opertime ) {
    this.libcode = libcode;
    this.dirid = dirid;
    this.efilename = efilename;
    this.deletor = deletor;
    this.opertime = opertime;
    this.custid = custid;
         }
 
    private String libcode;
    private String custid;
    private String dirid;
    private String efilename;
    private String deletor;
    private Date opertime;
 

	public String getLibcode() {
		return libcode;
	}



	public void setLibcode(String libcode) {
		this.libcode = libcode;
	}



	public String getDirid() {
		return dirid;
	}



	public void setDirid(String dirid) {
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



	public String getCustid() {
		return custid;
	}



	public void setCustid(String custid) {
		this.custid = custid;
	}



	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}
    }
}
 