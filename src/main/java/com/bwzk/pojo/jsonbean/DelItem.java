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

    public DelItem(  String libcode,
                     String dirid,
                     String efilename,
                     String deletor,
                     String opertime) {
        DELITEMBean ddb = new DELITEMBean();
        ddb.setLibcode(libcode);
        ddb.setDirid(dirid);
        ddb.setEfilename(efilename);
        ddb.setDeletor(deletor);
        ddb.setOpertime(opertime);
        this.DELITEM = ddb;

    }



    /**
     * DELITEM : {"libcode":2,"dirid":12,"efilename":"/system1/2018/03/06/a3d0cc5d-c797-4aa6-9db2-589431b718f6.jpg也就是OSSkey","deletor":"文档删除人,建议 usercode","opertime":"2017-05-07 22:48:05"}
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
         * libcode : 2
         * dirid : 12
         * efilename : /system1/2018/03/06/a3d0cc5d-c797-4aa6-9db2-589431b718f6.jpg也就是OSSkey
         * deletor : 文档删除人,建议 usercode
         * opertime : 2017-05-07 22:48:05
         */

        @JSONField(name = "libcode")
        private String libcode;
        @JSONField(name = "dirid")
        private String dirid;
        @JSONField(name = "efilename")
        private String efilename;
        @JSONField(name = "deletor")
        private String deletor;
        @JSONField(name = "opertime")
        private String opertime;
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
		public String getOpertime() {
			return opertime;
		}
		public void setOpertime(String opertime) {
			this.opertime = opertime;
		}

        
    }
}
