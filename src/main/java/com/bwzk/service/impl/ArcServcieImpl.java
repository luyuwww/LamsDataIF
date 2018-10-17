package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.JdbcDao;
import com.bwzk.dao.KyDao;
import com.bwzk.page.PageContext;
import com.bwzk.pojo.EFile;
import com.bwzk.pojo.FDTable;
import com.bwzk.pojo.SFwqpz;
import com.bwzk.pojo.SUser;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.ArcService;
import com.bwzk.service.i.FtpService;
import com.bwzk.util.GlobalFinalAttr;
import com.bwzk.util.MD5;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Service("arcServcieImpl")
public class ArcServcieImpl extends BaseService implements ArcService {
    @Transactional("txManager")
    public String fileRecive(String xml) {
        String resultStr = "";
        if (StringUtils.isNotEmpty(xml)) {
            log.error("====================================================");//临时记录
            log.error(xml);//临时记录
            resultStr = this.add2Archive(xml);
            log.error("====================================================");//临时记录
            //throw new RuntimeException("test TX");
        } else {
            resultStr = "OA发送xml为空.";
            log.error(resultStr);
        }
        return resultStr;
    }

    /**
     * @param xml 将xml解析并且添加到档案
     */
    private String add2Archive(String xml) {
        String resultStr = "1";
        return resultStr;
    }

    @Transactional("txManager")
    public List<SUser> listAllUser() {
        return sUserMapper.getAllUserList();
    }

    public String getBmidStrByDepCode(String depCode) {
        return super.getBmidByDepCode(depCode);
    }

    public String getBmidStrByUserCode(String userCode) {
        return super.getBmidByuserCode(userCode);
    }

    public String getLamsIP() {
        return super.getLamsIP();
    }

    /**
     * DW_V_PROJECT_ARCHIVE.prjectcode是主键
     */
    public String keyanPushXyPrj() {
        Integer dNum = 0;
        Integer eNum = 0;
        String msg = "纵向项目和横向项目成功插入 ";
        String targetTableName = "D_FILE" + xyLibcode;
        //默认数据库当前时间
        String dateStr = getDatabaseType().getDateByDb();

        List<String> dataList = kyDao.pkListBySql("SELECT PROJECTCODE FROM "+xyZjk);
        for (String prjCode : dataList) {
            //不包含就插入
            if(!jdbcDao.existsItem("SELECT PROJECTCODE FROM " + targetTableName + " WHERE PROJECTCODE='" + prjCode +"' AND STATUS=0")){
                Integer maxdid = 0;
                Map<String , Object> xyObj = null;
                xyObj = kyDao.query4Obj("SELECT SCIENCECODE,PROJECTNAME,PROJECTCODE,CHARGERNAME,UNITID," +
                        "CLIENTNAME,PROJECTTYPE,ACCEPTUNIT,ACCEPTDATE,EXECUTION,NOTE FROM "+ xyZjk
                        + " WHERE PROJECTCODE='" + prjCode +"'");

                String scienceCode = MapUtils.getString(xyObj , "SCIENCECODE");
                List<Map<String , Object>> xyEfile = new ArrayList<>();
                if(StringUtils.isNotBlank(scienceCode)){
                    xyEfile = kyDao.query4List("SELECT SCIENCECODE,FILENAME,FILETYPE,FILESOURCE FROM "
                            +xyZjkEFile + " WHERE FILESOURCE IS NOT NULL  AND SCIENCECODE='" + scienceCode +"'");
                }
                try {
                    maxdid = getMaxDid(targetTableName);
                    StringBuffer fields = new StringBuffer();
                    StringBuffer values = new StringBuffer();
                    for (String kyXyFieldName : xyObj.keySet()){
                        String kyXyValue = MapUtils.getString(xyObj , kyXyFieldName);
                        //如果非空进入下一个字段
                        if(StringUtils.isBlank(kyXyValue)) {
                            continue;
                        }else{
                            if(kyXyValue.length() > 2000){
                                kyXyValue = kyXyValue.substring(0 , 1999);
                            }
                        }

                        //这里获取对应的字段
                        FDTable kyDfileField = getXyPrjField(kyXyFieldName);
                        if (null == kyDfileField) {
                            continue;
                        }

                        kyXyValue = kyXyValue.replace("'", "''") ;
                        fields.append(kyDfileField.getFieldname()).append(",");
                        switch (kyDfileField.getFieldtype()) {
                            case 11:
                                if (StringUtils.isBlank(kyXyValue)) {
                                    values.append(dateStr + ",");
                                } else {
                                    values.append(generateTimeToSQLDate(kyXyValue)).append(",");
                                }
                                break;
                            case 1:
                                values.append("'").append(kyXyValue).append("',");
                                break;
                            case 3:
                                if (StringUtils.isBlank(kyXyValue)) {
                                    values.append("null ,");
                                } else {
                                    values.append(Integer.parseInt(kyXyValue)).append(",");
                                }
                                break;
                            default:
                                values.append("'").append(kyXyValue).append("',");
                                break;
                        }
                    }
                    fields.append(defaultField).append(",pid,qzh,did,attached");
                    values.append(defaultValue).append(",-1,'").append(defaultQzh)
                            .append("',").append(maxdid).append(",0");

                    String insertSql = "insert into " + targetTableName + "" + " ("
                            + fields.toString() + ") values (" + values.toString() + " )";
                    log.error("插入一条xy科研数据成功.keyanPushXyPrj: " + insertSql);
                    execSql(insertSql);
                    fields.setLength(0);
                    values.setLength(0);
                    addEfile(xyEfile, maxdid , xyLibcode);
                    dNum++;
                } catch(Exception e){
                    e.printStackTrace();
                    eNum++;
                    log.error("插入一条数据失败.keyanPushXyPrj: " + e.getMessage());
                    break;
                }
            }
        }
        return msg + dNum + "条, 失败:" + eNum+"条";
    }

    /**
     * 科研推送->档案抓取-外协项目的外协项目
     *DW_V_WX_PROJECT_ARCHIVE.wprjectcode是主键
     *SourceProjectCode是对应源档案的项目编号
     * @return
     */
    public String keyanPushWxPrj() {
        Integer dNum = 0;
        Integer eNum = 0;
        String msg = "外协项目插入 ";
        String wxDfile = "D_FILE" + wxLibcode;
        //默认数据库当前时间
        String dateStr = getDatabaseType().getDateByDb();

        List<String> dataList = kyDao.pkListBySql("SELECT WPROJECTCODE FROM "+ wxPrjZjk);
        for (String key : dataList) {
            //不包含就插入
            if(!jdbcDao.existsItem("SELECT SCIENCECODE FROM " + wxDfile + " WHERE WPROJECTCODE='" + key +"' AND STATUS=0")){
                Map<String , Object> wxObj = kyDao.query4Obj("SELECT SCIENCECODE,SOURCEPROJECTNAME,SOURCEPROJECTCODE,WPROJECTCODE" +
                        ",WPROJECTNAME,WCHARGERNAME,ACCEPTUNIT,ACCEPTDATE,EXECUTION,NOTE,IS_ARCHIVE FROM "
                        + wxPrjZjk + " WHERE WPROJECTCODE='" + key +"'");

                String scienceCode = MapUtils.getString(wxObj , "SCIENCECODE");
                List<Map<String , Object>> wxPrjEfileList = new ArrayList<>();
                if(StringUtils.isNotBlank(scienceCode)){
                    wxPrjEfileList = kyDao.query4List("SELECT SCIENCECODE,FILENAME,FILETYPE,FILESOURCE FROM "
                            +wxPrjZjkEFile + " WHERE FILESOURCE IS NOT NULL AND SCIENCECODE='" + scienceCode +"'");
                }

                Integer maxdid = 0;
                try {
                    maxdid = getMaxDid(wxDfile);
                    StringBuffer fields = new StringBuffer();
                    StringBuffer values = new StringBuffer();
                    for (String WXPrjField : wxObj.keySet()){
                        String kyValue = MapUtils.getString(wxObj , WXPrjField);
                        //如果非空进入下一个字段
                        if(StringUtils.isBlank(kyValue)) {
                            continue;
                        }else{
                            if(kyValue.length() > 2000){
                                kyValue = kyValue.substring(0 , 1999);
                            }
                        }

                        //这里获取对应的字段
                        FDTable daField = getNoPrjField(WXPrjField);
                        if (null == daField) {
                            continue;
                        }

                        kyValue = kyValue.replace("'", "''") ;
                        fields.append(daField.getFieldname()).append(",");
                        switch (daField.getFieldtype()) {
                            case 11:
                                if (StringUtils.isBlank(kyValue)) {
                                    values.append(dateStr + ",");
                                } else {
                                    values.append(generateTimeToSQLDate(kyValue)).append(",");
                                }
                                break;
                            case 1:
                                values.append("'").append(kyValue).append("',");
                                break;
                            case 3:
                                if (StringUtils.isBlank(kyValue)) {
                                    values.append("null ,");
                                } else {
                                    values.append(Integer.parseInt(kyValue))
                                            .append(",");
                                }
                                break;
                            default:
                                values.append("'").append(kyValue).append("',");
                                break;
                        }
                    }
                    fields.append(defaultField).append(",pid,qzh,did,attached");
                    values.append(defaultValue).append(",-1,'").append(defaultQzh)
                            .append("',").append(maxdid).append(",0");

                    String insertSql = "insert into " + wxDfile + "" + " ("
                            + fields.toString() + ") values (" + values.toString() + " )";
                    log.error("插入一条外协项目项目数据成功.keyanPushNoPrj: " + insertSql);
                    execSql(insertSql);
                    fields.setLength(0);
                    values.setLength(0);
                    addEfile(wxPrjEfileList, maxdid , wxLibcode);
                    dNum++;
                } catch(Exception e){
                    e.printStackTrace();
                    eNum++;
                    log.error("插入一条外协项目项目数据失败.keyanPushNoPrj: " + e.getMessage());
                    break;
                }
            }
        }
        return msg + dNum + "条, 失败:" + eNum+"条";
    }

    @Override
    public String daPushXyPrj() {
        String targetTableName = "D_FILE" + xyLibcode;

        List<Integer> dataList = super.queryDidList("SELECT DID FROM "+targetTableName
                +" WHERE STATUS=0 AND ATTR=0 AND (SYNCSTATUS IS NULL OR SYNCSTATUS='')");
        for (Integer did : dataList) {
            Map<String, Object> obj = super.queryForMap("SELECT SCIENCECODE,PROJECTCODE,TITLE PROJECTNAME,CHARGERNAME," +
                    "EDITTIME DATETIME,KEYWORD ARCHIVECODE FROM "+targetTableName
                    +" WHERE DID="+did);
            String updateSql = "";
            if(insertDa2Keyan(obj)){
                updateSql = "UPDATE " + targetTableName + " SET SYNCSTATUS='已同步到科研系统' WHERE DID=" + did;
            }else{
                updateSql = "UPDATE " + targetTableName + " SET SYNCSTATUS='同步到科研系统出现错误' WHERE DID=" + did;
            }
            execSql(updateSql);
        }
        return "XY项目同步到科研系统完成";
    }

    @Override
    public String daPushWxPrj() {
        String targetTableName = "D_FILE" + wxLibcode;
        List<Integer> dataList = super.queryDidList("SELECT DID FROM "+targetTableName
                +" WHERE  STATUS=0 AND ATTR=0 AND (SYNCSTATUS IS NULL OR SYNCSTATUS='')");
        for (Integer did : dataList) {
            Map<String, Object> obj = super.queryForMap("SELECT SCIENCECODE,SOURCEPROJECTCODE PROJECTCODE,TITLE PROJECTNAME," +
                    "WPROJECTCODE,WPROJECTNAME,WCHARGERNAME CHARGERNAME,EDITTIME DATETIME,KEYWORD ARCHIVECODE FROM "
                    +targetTableName +" WHERE DID="+did);
            String updateSql = "";
            if(insertDa2Keyan(obj)){
                updateSql = "UPDATE " + targetTableName + " SET SYNCSTATUS='已同步到科研系统' WHERE DID=" + did;
            }else{
                updateSql = "UPDATE " + targetTableName + " SET SYNCSTATUS='同步到科研系统出现错误' WHERE DID=" + did;
            }
        }
        return "外协项目同步到科研系统完成";
    }


    public String syncAllData(){
        return null;
    }
    /*
     * 分页抓取 300条
     */
    private List<Map<String, Object>> listMaps(String sql) {
        PageContext page = PageContext.getContext();
        page.setCurrentPage(1);
        page.setPageSize(300);
        page.setPagination(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("sql", sql);
        List<Map<String, Object>> mapList = sUserMapper.listPageMapQuery(map);
        return mapList;
    }

    /**
     * ftp copy文件并且插入efile
     */
    private void addEfile(List<Map<String , Object>> fjList , Integer pid , Integer libcode) {
        SFwqpz fwqpz = getFwqpz(null);
        Integer year = new DateTime().getYear();
        Integer month = new DateTime().getMonthOfYear();
        Integer day = new DateTime().getDayOfMonth();
        String tableName = "E_FILE" + libcode ;
        String scienceCode , fileSource,fileBizName , ext , fileType , fileDiskName, efileBasePath= "";
        efileBasePath= libcode.equals(xyLibcode)? xyPrjEfileBasePath : wxPrjEfileBasePath;
        efileBasePath = efileBasePath+"/"+year+"/"+month+"/"+day+"/";

        for (Map<String, Object> dataMap : fjList) {
            try {
                fileDiskName = pid + GlobalFinalAttr.getGuid();
                fileSource = MapUtils.getString(dataMap, "FILESOURCE");
                scienceCode = MapUtils.getString(dataMap, "SCIENCECODE");
                fileSource = MapUtils.getString(dataMap, "FILESOURCE");
                fileBizName = MapUtils.getString(dataMap, "FILENAME");
                fileType = MapUtils.getString(dataMap, "FILETYPE");
                if(StringUtils.isNotBlank(fileSource)){
                    ext = FilenameUtils.getExtension(fileSource);
                    if(StringUtils.isBlank(fileBizName)){
                        fileBizName = FilenameUtils.getBaseName(fileSource);
                    }
                    if(StringUtils.isNotBlank(fileType)){
                        fileBizName = "【"+fileType+"】"+fileBizName;
                    }
                    fileDiskName = fileDiskName +"." + ext;
                    fileDiskName = FilenameUtils.normalize(fwqpz.getSavedbname()+"/"+efileBasePath +fileDiskName);
                    ftpServcie.downloadFile(super.getFtpIp(),super.getFtpUser()
                            ,super.getFtpPassword(),super.getFtpPort(),fileSource ,fileDiskName);
                    File sFile = new File(fileDiskName);
                    if(sFile.exists()){
                        EFile eFile = new EFile();
                        eFile.setDid(getMaxDid(tableName));
                        eFile.setPid(pid);
                        eFile.setEfilename(FilenameUtils.getName(fileDiskName));
                        eFile.setTitle(fileBizName);
                        eFile.setExt(ext);
                        eFile.setPzm(fwqpz.getPzname());
                        eFile.setPathname(FilenameUtils.normalize(efileBasePath));
                        eFile.setStatus(0);
                        eFile.setAttr(1);
                        eFile.setAttrex(1);
                        eFile.setCreator("ROOT");
                        eFile.setXlh(scienceCode);
                        eFile.setCreatetime(new Date());
                        eFile.setFilesize(((Long) sFile.length()).intValue() / 1000);
                        eFile.setMd5(MD5.getFileMD5String(sFile));
                        insertEfile(tableName, eFile);
                        jdbcDao.excute("UPDATE D_FILE"+libcode+" SET ATTACHED=1 WHERE DID="+pid);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /******************************************************
     * xy字段列表
     * @return
     */
    private List<FDTable> getXyFieldList(){
        if(xyFieldList == null){
            xyFieldList = sGroupMapper.getFtableList("F_D_FILE"+xyLibcode);
        }
        return xyFieldList;
    }


    /******************************************************
     * noPrj字段列表
     * @return
     */
    private List<FDTable> getNoFieldList(){
        if(noFieldList == null){
            noFieldList = sGroupMapper.getFtableList("F_D_FILE"+ wxLibcode);
        }
        return noFieldList;
    }

    /**
     * xyPrj字段对应关系
     */
    private Map<String , String> getXyPrjFieldMappingList(){
        if(xyPrjFieldMapping == null){
            xyPrjFieldMapping = quert2Colum4Map("SELECT * FROM "+ xyFieldMappingtbName , "F1" ,"F2");
        }
        return xyPrjFieldMapping;
    }

    /**
     * noPrj字段对应关系
     */
    private Map<String , String> getnoPrjFieldMappingList(){
        if(noPrjFieldMapping == null){
            noPrjFieldMapping = quert2Colum4Map("SELECT * FROM "+ noPrjMappingtbName , "F1" ,"F2");
        }
        return noPrjFieldMapping;
    }
    /**
     * 通过对应关系得到可言XY档案的字段
     * @param oaField
     * @return
     */
    private FDTable getXyPrjField(String xyField){
        FDTable result = null;
        List<FDTable> daFieldList = getXyFieldList();
        String daField = getXyPrjFieldMappingList().get(xyField);

        if(StringUtils.isNotBlank(daField)){
            for (FDTable f : daFieldList) {
                if(f.getFieldname().toUpperCase().equals(daField.toUpperCase())){
                    result = f;
                    break;
                }
            }
        }
        return result;
    }
    /**
     * 通过对应关系得到noPrj档案的字段
     * @param oaField
     * @return
     */
    private FDTable getNoPrjField(String noPrjField){
        FDTable result = null;
        List<FDTable> daFieldList = getNoFieldList();
        String daField = getnoPrjFieldMappingList().get(noPrjField);

        if(StringUtils.isNotBlank(daField)){
            for (FDTable f : daFieldList) {
                if(f.getFieldname().toUpperCase().equals(daField.toUpperCase())){
                    result = f;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 将档案数据插入科研
     */
    private Boolean insertDa2Keyan(Map<String, Object> obj){
        String uuid = GlobalFinalAttr.getGuid();
        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        for (String k : obj.keySet()){
            Object v = MapUtils.getObject(obj , k);
            //如果非空进入下一个字段
            if(null == v) {
                continue;
            }else if(v instanceof String && StringUtils.isNotBlank(v.toString())){
                v = v.toString().replace("'", "''") ;
                fields.append(k + ",");
                values.append("'").append(v + "',");
            }else if(v instanceof  Date || v instanceof java.sql.Date){
                fields.append(k + ",");
                values.append(generateTimeToSQLDate(v)).append(",");
            }else{
                continue;
            }
        }
        fields.append("ID");
        values.append("'"+uuid+"'");
        String insertSql = "INSERT INTO  DA_PROJECT_ARCHIVE(" + fields.toString()
                + ") values (" + values.toString() + " )";
        try {
            kyDao.executeSql(insertSql);
            return true;
        } catch (Exception e) {
            log.error("同步到科研系统出现错误："+e.getMessage() ,e );
            return false;
        }finally {
            fields.setLength(0);
            values.setLength(0);
        }
    }

    protected Map<String , String> xyPrjFieldMapping = null;//OA档案字段对应的字段list
    protected Map<String , String> noPrjFieldMapping = null;//OA档案字段对应的字段list
    protected List<FDTable> xyFieldList = null;//xy科研f表的字段list
    protected List<FDTable> noFieldList = null;//no科研f表的字段list
    @Autowired
    private JdbcDao jdbcDao;
    @Autowired
    private KyDao kyDao;
    @Autowired
    private FtpService ftpServcie;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
