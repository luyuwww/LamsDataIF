package com.bwzk.pojo.jsonbean;
import java.util.Date;
public class DELITEM {

    private int LIBCODE;
    private int DIRID;
    private String EFILENAME;
    private String DELETOR;
    private Date OPERTIME;
    public void setLIBCODE(int LIBCODE) {
         this.LIBCODE = LIBCODE;
     }
     public int getLIBCODE() {
         return LIBCODE;
     }

    public void setDIRID(int DIRID) {
         this.DIRID = DIRID;
     }
     public int getDIRID() {
         return DIRID;
     }

    public void setEFILENAME(String EFILENAME) {
         this.EFILENAME = EFILENAME;
     }
     public String getEFILENAME() {
         return EFILENAME;
     }

    public void setDELETOR(String DELETOR) {
         this.DELETOR = DELETOR;
     }
     public String getDELETOR() {
         return DELETOR;
     }

    public void setOPERTIME(Date OPERTIME) {
         this.OPERTIME = OPERTIME;
     }
     public Date getOPERTIME() {
         return OPERTIME;
     }

}