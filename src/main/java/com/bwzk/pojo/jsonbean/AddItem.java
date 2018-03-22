package com.bwzk.pojo.jsonbean;

import com.alibaba.fastjson.annotation.JSONField;

public class AddItem {

    public AddItem(String LIBCODE, String DIRID,    String CUSTID,    String CUSTCARD,    String CUSTNAME,
                   String FILETYPE,    String EFILENAME,    String TITLE,    String EXT,   String PZM ,
                   Integer ATTR , String MD5 , String CREATOR , String OPERTIME , Integer FILESIZE){
        ITEMBean item = new ITEMBean();
        item.setLIBCODE(LIBCODE);
        item.setDIRID(DIRID);
        item.setCUSTID(CUSTID);
        item.setCUSTCARD(CUSTCARD);
        item.setCUSTNAME(CUSTNAME);
        item.setFILETYPE(FILETYPE);
        item.setEFILENAME(EFILENAME);
        item.setTITLE(TITLE);
        item.setEXT(EXT);

        item.setPZM(PZM);
        item.setATTR(ATTR);
        item.setMD5(MD5);
        item.setCREATOR(CREATOR);
        item.setOPERTIME(OPERTIME);
        item.setFILESIZE(FILESIZE);
        this.ITEM = item;
    }

    public AddItem(ITEMBean ITEM) {
        this.ITEM = ITEM;
    }

    public AddItem() {
    }

    /**
     * ITEM : {"LIBCODE":2,"DIRID":12,"CUSTID":"用户编号","CUSTCARD":"身份证号码","CUSTNAME":"刘某某","FILETYPE":"身份证/户口本/个人简历/最好有一个和中文对应的编号","EFILENAME":"/system1/2018/03/06/a3d0cc5d-c797-4aa6-9db2-589431b718f6.jpg也就是OSSkey","TITLE":"身份证A面","EXT":"jpg","PZM":"一个固定字符串,         开发的时候确认","ATTR":"0/1一个固定值,         方便扩展","MD5":"d0eb922766f4a7e28a17f49ae0fad6d5","CREATOR":"文档上传人,建议 usercode/username","OPERTIME":"2017-05-07 22:48:05","FILESIZE":12423482}
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
         * LIBCODE : 2
         * DIRID : 12
         * CUSTID : 用户编号
         * CUSTCARD : 身份证号码
         * CUSTNAME : 刘某某
         * FILETYPE : 身份证/户口本/个人简历/最好有一个和中文对应的编号
         * EFILENAME : /system1/2018/03/06/a3d0cc5d-c797-4aa6-9db2-589431b718f6.jpg也就是OSSkey
         * TITLE : 身份证A面
         * EXT : jpg
         * PZM : 一个固定字符串,         开发的时候确认
         * ATTR : 0/1一个固定值,         方便扩展
         * MD5 : d0eb922766f4a7e28a17f49ae0fad6d5
         * CREATOR : 文档上传人,建议 usercode/username
         * OPERTIME : 2017-05-07 22:48:05
         * FILESIZE : 12423482
         */

        @JSONField(name = "LIBCODE")
        private String LIBCODE;
        @JSONField(name = "DIRID")
        private String DIRID;
        @JSONField(name = "CUSTID")
        private String CUSTID;
        @JSONField(name = "CUSTCARD")
        private String CUSTCARD;
        @JSONField(name = "CUSTNAME")
        private String CUSTNAME;
        @JSONField(name = "FILETYPE")
        private String FILETYPE;
        @JSONField(name = "EFILENAME")
        private String EFILENAME;
        @JSONField(name = "TITLE")
        private String TITLE;
        @JSONField(name = "EXT")
        private String EXT;
        @JSONField(name = "PZM")
        private String PZM;
        @JSONField(name = "ATTR")
        private Integer ATTR;
        @JSONField(name = "MD5")
        private String MD5;
        @JSONField(name = "CREATOR")
        private String CREATOR;
        @JSONField(name = "OPERTIME")
        private String OPERTIME;
        @JSONField(name = "FILESIZE")
        private Integer FILESIZE;


        public String getLIBCODE() {
            return LIBCODE;
        }

        public void setLIBCODE(String LIBCODE) {
            this.LIBCODE = LIBCODE;
        }

        public String getDIRID() {
            return DIRID;
        }

        public void setDIRID(String DIRID) {
            this.DIRID = DIRID;
        }

        public String getCUSTID() {
            return CUSTID;
        }

        public void setCUSTID(String CUSTID) {
            this.CUSTID = CUSTID;
        }

        public String getCUSTCARD() {
            return CUSTCARD;
        }

        public void setCUSTCARD(String CUSTCARD) {
            this.CUSTCARD = CUSTCARD;
        }

        public String getCUSTNAME() {
            return CUSTNAME;
        }

        public void setCUSTNAME(String CUSTNAME) {
            this.CUSTNAME = CUSTNAME;
        }

        public String getFILETYPE() {
            return FILETYPE;
        }

        public void setFILETYPE(String FILETYPE) {
            this.FILETYPE = FILETYPE;
        }

        public String getEFILENAME() {
            return EFILENAME;
        }

        public void setEFILENAME(String EFILENAME) {
            this.EFILENAME = EFILENAME;
        }

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public String getEXT() {
            return EXT;
        }

        public void setEXT(String EXT) {
            this.EXT = EXT;
        }

        public String getPZM() {
            return PZM;
        }

        public void setPZM(String PZM) {
            this.PZM = PZM;
        }

        public Integer getATTR() {
            return ATTR;
        }

        public void setATTR(Integer ATTR) {
            this.ATTR = ATTR;
        }

        public String getMD5() {
            return MD5;
        }

        public void setMD5(String MD5) {
            this.MD5 = MD5;
        }

        public String getCREATOR() {
            return CREATOR;
        }

        public void setCREATOR(String CREATOR) {
            this.CREATOR = CREATOR;
        }

        public String getOPERTIME() {
            return OPERTIME;
        }

        public void setOPERTIME(String OPERTIME) {
            this.OPERTIME = OPERTIME;
        }

        public Integer getFILESIZE() {
            return FILESIZE;
        }

        public void setFILESIZE(Integer FILESIZE) {
            this.FILESIZE = FILESIZE;
        }
    }
}