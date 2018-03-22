package com.bwzk.pojo.jsonbean;/**
 * Created by DaMo on 2018-03-22.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author DaMo
 * @UPDATE 2018-03-22-15:38
 * @since 2018-03-22-15:38
 */
public class DelItem {

    public DelItem() {
    }

    public DelItem(  String LIBCODE,
                     String DIRID,
                     String EFILENAME,
                     String DELETOR,
                     String OPERTIME) {
        DELITEMBean ddb = new DELITEMBean();
        ddb.setLIBCODE(LIBCODE);
        ddb.setDIRID(DIRID);
        ddb.setEFILENAME(EFILENAME);
        ddb.setDELETOR(DELETOR);
        ddb.setOPERTIME(OPERTIME);
        this.DELITEM = ddb;

    }



    /**
     * DELITEM : {"LIBCODE":2,"DIRID":12,"EFILENAME":"/system1/2018/03/06/a3d0cc5d-c797-4aa6-9db2-589431b718f6.jpg也就是OSSkey","DELETOR":"文档删除人,建议 usercode","OPERTIME":"2017-05-07 22:48:05"}
     */
    @JSONField(name = "DELITEM")
    private DELITEMBean DELITEM;

    public DELITEMBean getDELITEM() {
        return DELITEM;
    }

    public void setDELITEM(DELITEMBean DELITEM) {
        this.DELITEM = DELITEM;
    }

    public static class DELITEMBean {
        /**
         * LIBCODE : 2
         * DIRID : 12
         * EFILENAME : /system1/2018/03/06/a3d0cc5d-c797-4aa6-9db2-589431b718f6.jpg也就是OSSkey
         * DELETOR : 文档删除人,建议 usercode
         * OPERTIME : 2017-05-07 22:48:05
         */

        @JSONField(name = "LIBCODE")
        private String LIBCODE;
        @JSONField(name = "DIRID")
        private String DIRID;
        @JSONField(name = "EFILENAME")
        private String EFILENAME;
        @JSONField(name = "DELETOR")
        private String DELETOR;
        @JSONField(name = "OPERTIME")
        private String OPERTIME;

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

        public String getEFILENAME() {
            return EFILENAME;
        }

        public void setEFILENAME(String EFILENAME) {
            this.EFILENAME = EFILENAME;
        }

        public String getDELETOR() {
            return DELETOR;
        }

        public void setDELETOR(String DELETOR) {
            this.DELETOR = DELETOR;
        }

        public String getOPERTIME() {
            return OPERTIME;
        }

        public void setOPERTIME(String OPERTIME) {
            this.OPERTIME = OPERTIME;
        }
    }
}
