package com.bwzk.pojo.jsonbean;

import com.bwzk.util.DateUtil;
import org.apache.commons.io.FilenameUtils;

public class ITEM {


    /**
     * item : {"pzm":111,"custcard":111,"attr":"Y","ext":"jpg","creator":111,"title":"微信图片_20180227175210","filesize":64516,"libcode":0,"md5":111,"efilename":"lambda/credit/20180316/5aab77c19d0225243000afad.jpg","filetype":111,"dirid":5,"opertime":111,"custid":"1000001348","custname":111}
     */

    private ItemBean item;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class ItemBean {

        public ItemBean() {
        }



        public ItemBean(String libcode , String dirid , String  custid, String custcard, String custname
                , String filetype, String efilename, String ext ,  String pzm ,  Integer attr
                , String md5, String creator , String opertime , Integer filesize) {
    this.pzm = pzm;
    this.custcard = custcard;
    this.attr = attr;
    this.ext = ext;
    this.creator = creator;
    this.filesize = filesize;
    this.libcode = libcode;
    this.md5 = md5;
    this.efilename = efilename;
    this.filetype = filetype;
    this.dirid = dirid;
    this.opertime = opertime;
    this.custid = custid;
    this.custname = custname;
}

        /**
         * pzm : 111
         * custcard : 111
         * attr : Y
         * ext : jpg
         * creator : 111
         * title : 微信图片_20180227175210
         * filesize : 64516
         * libcode : 0
         * md5 : 111
         * efilename : lambda/credit/20180316/5aab77c19d0225243000afad.jpg
         * filetype : 111
         * dirid : 5
         * opertime : 111
         * custid : 1000001348
         * custname : 111
         */

        private String pzm;
        private String custcard;
        private Integer attr;
        private String ext;
        private String creator;
        private String title;
        private Integer filesize;
        private String libcode;
        private String md5;
        private String efilename;
        private String filetype;
        private String dirid;
        private String opertime;
        private String custid;
        private String custname;


        public String getPzm() {
            return pzm;
        }

        public void setPzm(String pzm) {
            this.pzm = pzm;
        }

        public String getCustcard() {
            return custcard;
        }

        public void setCustcard(String custcard) {
            this.custcard = custcard;
        }

        public Integer getAttr() {
            return attr;
        }

        public void setAttr(Integer attr) {
            this.attr = attr;
        }

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getFilesize() {
            return filesize;
        }

        public void setFilesize(Integer filesize) {
            this.filesize = filesize;
        }

        public String getLibcode() {
            return libcode;
        }

        public void setLibcode(String libcode) {
            this.libcode = libcode;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getEfilename() {
            return efilename;
        }

        public void setEfilename(String efilename) {
            this.efilename = efilename;
        }

        public String getFiletype() {
            return filetype;
        }

        public void setFiletype(String filetype) {
            this.filetype = filetype;
        }

        public String getDirid() {
            return dirid;
        }

        public void setDirid(String dirid) {
            this.dirid = dirid;
        }

        public String getOpertime() {
            return opertime;
        }

        public void setOpertime(String opertime) {
            this.opertime = opertime;
        }

        public String getCustid() {
            return custid;
        }

        public void setCustid(String custid) {
            this.custid = custid;
        }

        public String getCustname() {
            return custname;
        }

        public void setCustname(String custname) {
            this.custname = custname;
        }
    }
}