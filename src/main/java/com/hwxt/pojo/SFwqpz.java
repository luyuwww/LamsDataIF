package com.hwxt.pojo;

/**
 * SFwqpz entity.
 *
 * @author MyEclipse Persistence Tools
 */

public class SFwqpz implements java.io.Serializable {

    // Fields
    private Integer did;
    private String pzname;
    private Integer ccfs;
    private Integer dbtype;
    private String serveraddr;
    private String username;
    private String passwd;
    private String savedbname;
    private String port;
    private Integer isdefault;
    private String bz;
    private String utf8passwd;

    // Constructors

    public String getUtf8passwd() {
        return utf8passwd;
    }

    public void setUtf8passwd(String utf8passwd) {
        this.utf8passwd = utf8passwd;
    }

    /**
     * default constructor
     */
    public SFwqpz() {
    }

    /**
     * minimal constructor
     */
    public SFwqpz(String pzname) {
        this.pzname = pzname;
    }

    /**
     * full constructor
     */
    public SFwqpz(String pzname, Integer ccfs, Integer dbtype,
                  String serveraddr, String username, String passwd,
                  String savedbname, String port, Integer isdefault, String bz) {
        this.pzname = pzname;
        this.ccfs = ccfs;
        this.dbtype = dbtype;
        this.serveraddr = serveraddr;
        this.username = username;
        this.passwd = passwd;
        this.savedbname = savedbname;
        this.port = port;
        this.isdefault = isdefault;
        this.bz = bz;
    }

    // Property accessors

    public Integer getDid() {
        return this.did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getPzname() {
        return this.pzname;
    }

    public void setPzname(String pzname) {
        this.pzname = pzname;
    }

    public Integer getCcfs() {
        return this.ccfs;
    }

    public void setCcfs(Integer ccfs) {
        this.ccfs = ccfs;
    }

    public Integer getDbtype() {
        return this.dbtype;
    }

    public void setDbtype(Integer dbtype) {
        this.dbtype = dbtype;
    }

    public String getServeraddr() {
        return this.serveraddr;
    }

    public void setServeraddr(String serveraddr) {
        this.serveraddr = serveraddr;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSavedbname() {
        return this.savedbname;
    }

    public void setSavedbname(String savedbname) {
        this.savedbname = savedbname;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getIsdefault() {
        return this.isdefault;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
    }

    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

}