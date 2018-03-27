package com.bwzk.pojo.jsonbean;

import com.alibaba.fastjson.annotation.JSONField;

public class AddItem {

    public AddItem(String libcode, String dirid,    String custid,    String custcard,    String custname,
                   String filetype,    String efilename,    String title,    String ext,   String pzm ,
                   Integer attr , String md5 , String creator , String opertime , Integer filesize){
        ITEMBean item = new ITEMBean();
        item.setLibcode(libcode);
        item.setDirid(dirid);
        item.setCustid(custid);
        item.setCustcard(custcard);
        item.setCustname(custname);
        item.setFiletype(filetype);
        item.setEfilename(efilename);
        item.setTitle(title);
        item.setExt(ext);

        item.setPzm(pzm);
        item.setAttr(attr);
        item.setMd5(md5);
        item.setCreator(creator);
        item.setOpertime(opertime);
        item.setFilesize(filesize);
        this.ITEM = item;
    }

    public AddItem(ITEMBean ITEM) {
        this.ITEM = ITEM;
    }

    public AddItem() {
    }

    /**
     * ITEM : {"libcode":2,"dirid":12,"custid":"用户编号","custcard":"身份证号码","custname":"刘某某","filetype":"身份证/户口本/个人简历/最好有一个和中文对应的编号","efilename":"/system1/2018/03/06/a3d0cc5d-c797-4aa6-9db2-589431b718f6.jpg也就是OSSkey","title":"身份证A面","ext":"jpg","pzm":"一个固定字符串,         开发的时候确认","attr":"0/1一个固定值,         方便扩展","md5":"d0eb922766f4a7e28a17f49ae0fad6d5","creator":"文档上传人,建议 usercode/username","opertime":"2017-05-07 22:48:05","filesize":12423482}
     */

    @JSONField(name = "ITEM")
    private ITEMBean ITEM;

    public ITEMBean getITEM() {
        return ITEM;
    }

    public void setITEM(ITEMBean ITEM) {
        this.ITEM = ITEM;
    }

    public static class ITEMBean {
        /**
         * libcode : 2
         * dirid : 12
         * custid : 用户编号
         * custcard : 身份证号码
         * custname : 刘某某
         * filetype : 身份证/户口本/个人简历/最好有一个和中文对应的编号
         * efilename : /system1/2018/03/06/a3d0cc5d-c797-4aa6-9db2-589431b718f6.jpg也就是OSSkey
         * title : 身份证A面
         * ext : jpg
         * pzm : 一个固定字符串,         开发的时候确认
         * attr : 0/1一个固定值,         方便扩展
         * md5 : d0eb922766f4a7e28a17f49ae0fad6d5
         * creator : 文档上传人,建议 usercode/username
         * opertime : 2017-05-07 22:48:05
         * filesize : 12423482
         */

        @JSONField(name = "libcode")
        private String libcode;
        @JSONField(name = "dirid")
        private String dirid;
        @JSONField(name = "custid")
        private String custid;
        @JSONField(name = "custcard")
        private String custcard;
        @JSONField(name = "custname")
        private String custname;
        @JSONField(name = "filetype")
        private String filetype;
        @JSONField(name = "efilename")
        private String efilename;
        @JSONField(name = "title")
        private String title;
        @JSONField(name = "ext")
        private String ext;
        @JSONField(name = "pzm")
        private String pzm;
        @JSONField(name = "attr")
        private Integer attr;
        @JSONField(name = "md5")
        private String md5;
        @JSONField(name = "creator")
        private String creator;
        @JSONField(name = "opertime")
        private String opertime;
        @JSONField(name = "filesize")
        private Integer filesize;
        
        
        
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
		public String getCustid() {
			return custid;
		}
		public void setCustid(String custid) {
			this.custid = custid;
		}
		public String getCustcard() {
			return custcard;
		}
		public void setCustcard(String custcard) {
			this.custcard = custcard;
		}
		public String getCustname() {
			return custname;
		}
		public void setCustname(String custname) {
			this.custname = custname;
		}
		public String getFiletype() {
			return filetype;
		}
		public void setFiletype(String filetype) {
			this.filetype = filetype;
		}
		public String getEfilename() {
			return efilename;
		}
		public void setEfilename(String efilename) {
			this.efilename = efilename;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getExt() {
			return ext;
		}
		public void setExt(String ext) {
			this.ext = ext;
		}
		public String getPzm() {
			return pzm;
		}
		public void setPzm(String pzm) {
			this.pzm = pzm;
		}
		public Integer getAttr() {
			return attr;
		}
		public void setAttr(Integer attr) {
			this.attr = attr;
		}
		public String getMd5() {
			return md5;
		}
		public void setMd5(String md5) {
			this.md5 = md5;
		}
		public String getCreator() {
			return creator;
		}
		public void setCreator(String creator) {
			this.creator = creator;
		}
		public String getOpertime() {
			return opertime;
		}
		public void setOpertime(String opertime) {
			this.opertime = opertime;
		}
		public Integer getFilesize() {
			return filesize;
		}
		public void setFilesize(Integer filesize) {
			this.filesize = filesize;
		}


       
    }
}