package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.JdbcDao;
import com.bwzk.dao.KyDao;
import com.bwzk.page.PageContext;
import com.bwzk.pojo.EFile;
import com.bwzk.pojo.FDTable;
import com.bwzk.pojo.SUser;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.ArcService;
import com.bwzk.util.DateUtil;
import com.bwzk.util.MD5;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public String keyanPushXyPrj() {
        Integer dNum = 0;
        Integer eNum = 0;
        String msg = "纵向项目和横向项目成功插入 ";
        String tableName = "D_FILE" + xyLibcode;
        //默认数据库当前时间
        String dateStr = getDatabaseType().getDateByDb();

        List<String> dataList = kyDao.pkListBySql("SELECT SCIENCECODE FROM "+xyZjk);
        for (String key : dataList) {
            System.out.println(key);
            //不包含就插入
            if(!jdbcDao.existsItem("SELECT SCIENCECODE FROM" + xyLibcode + " SCIENCECODE='" + key +"' AND STATUS=0")){
                Map<String , Object> xyObj =
                        kyDao.query4Obj("SELECT * FROM "+xyZjk + " WHERE SCIENCECODE='" + key +"'");
                List<Map<String , Object>> xyEfile =
                        kyDao.query4List("SELECT * FROM "+xyZjkEFile + " WHERE SCIENCECODE='" + key +"'");
                Integer maxdid = 0;
                try {
                    maxdid = getMaxDid(tableName);
                    StringBuffer fields = new StringBuffer();
                    StringBuffer values = new StringBuffer();
                    for (String kyXyFieldName : xyObj.keySet()){
                        //这里获取对应的字段
                        FDTable kyDfileField = getXyPrjField(kyXyFieldName);
                        if (null != kyDfileField) {
                            String kyXyValue = (MapUtils.getString(xyObj , kyXyFieldName) == null ?
                                                "" : MapUtils.getString(xyObj , kyXyFieldName));
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
                                        values.append(Integer.parseInt(kyXyValue))
                                                .append(",");
                                    }
                                    break;
                                default:
                                    values.append("'").append(kyXyValue).append("',");
                                    break;
                            }
                        }
                    }
                    fields.append(defaultField).append(",pid,qzh,did,attached");
                    values.append(defaultValue).append(",-1,'").append(defaultQzh)
                            .append("',").append(maxdid).append(",").append(xyEfile.size() > 0 ? 1 : 0);

                    String insertSql = "insert into " + tableName + "" + " ("
                            + fields.toString() + ") values (" + values.toString() + " )";
                    log.error("插入一条XY科研数据成功.keyanPushXyPrj: " + insertSql);
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
        return msg + dNum + "条, 失败:" + dNum+"条";
    }

    @Override
    public String keyanPushNoPrj() {
        Integer dNum = 0;
        Integer eNum = 0;
        String msg = "无源项目的外协项目成功插入 ";
        String tableName = "D_FILE" + noLibcode;
        //默认数据库当前时间
        String dateStr = getDatabaseType().getDateByDb();

        List<String> dataList = kyDao.pkListBySql("SELECT SCIENCECODE FROM "+noPrjZjk);
        for (String key : dataList) {
            System.out.println(key);
            //不包含就插入
            if(!jdbcDao.existsItem("SELECT SCIENCECODE FROM" + noLibcode + " SCIENCECODE='" + key +"' AND STATUS=0")){
                Map<String , Object> noPrjObj =
                        kyDao.query4Obj("SELECT * FROM "+noPrjZjk + " WHERE SCIENCECODE='" + key +"'");
                List<Map<String , Object>> noPrjEfileList =
                        kyDao.query4List("SELECT * FROM "+noPrjZjkEFile + " WHERE SCIENCECODE='" + key +"'");
                Integer maxdid = 0;
                try {
                    maxdid = getMaxDid(tableName);
                    StringBuffer fields = new StringBuffer();
                    StringBuffer values = new StringBuffer();
                    for (String noPrjField : noPrjObj.keySet()){
                        //这里获取对应的字段
                        FDTable daField = getNoPrjField(noPrjField);
                        if (null != daField) {
                            String kyValue = (MapUtils.getString(noPrjObj , noPrjField) == null ?
                                    "" : MapUtils.getString(noPrjObj , noPrjField));
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
                    }
                    fields.append(defaultField).append(",pid,qzh,did,attached");
                    values.append(defaultValue).append(",-1,'").append(defaultQzh)
                            .append("',").append(maxdid).append(",").append(noPrjEfileList.size() > 0 ? 1 : 0);

                    String insertSql = "insert into " + tableName + "" + " ("
                            + fields.toString() + ") values (" + values.toString() + " )";
                    log.error("插入一条无源项目项目数据成功.keyanPushNoPrj: " + insertSql);
                    execSql(insertSql);
                    fields.setLength(0);
                    values.setLength(0);
                    addEfile(noPrjEfileList, maxdid , noLibcode);
                    dNum++;
                } catch(Exception e){
                    e.printStackTrace();
                    eNum++;
                    log.error("插入一条无源项目项目数据失败.keyanPushNoPrj: " + e.getMessage());
                    break;
                }
            }
        }
        return msg + dNum + "条, 失败:" + dNum+"条";
    }

    @Override
    public String daPushXyPrj() {
        return null;
    }

    @Override
    public String daPushNoPrj() {
        return null;
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
     * 电子文件集成
     */
    private void addEfile(List<Map<String , Object>> fjList , Integer pid , Integer libcode) {
        String tableName = "E_FILE" + libcode ;
        String scienceCode , filename , ext , filesource , xdlj= "";
        xdlj= libcode.equals(xyLibcode)? xyPjrXdLj : noPjrXdLj;

        for (Map<String, Object> dataMap : fjList) {
            scienceCode = dataMap.get("SCIENCECODE") == null ? "" : MapUtils.getString(dataMap , "SCIENCECODE");
            filename = dataMap.get("FILENAME") == null ? "" : MapUtils.getString(dataMap , "FILENAME");
            filesource = dataMap.get("FILESOURCE") == null ? "" : MapUtils.getString(dataMap , "FILESOURCE");
            ext = dataMap.get("EXT") == null ? "" : MapUtils.getString(dataMap , "EXT");
            File sFile = new File(FilenameUtils.normalize(basePath +"/" + xdlj + "/" + filesource +"/" + filename));
            if(sFile.exists()) {
                EFile eFile = new EFile();
                eFile.setDid(getMaxDid(tableName));
                eFile.setPid(pid);
                eFile.setEfilename(filename);
                eFile.setTitle(filename);
                eFile.setExt(ext);
                eFile.setPzm(pzm);
                eFile.setPathname(xdlj + "/" + filesource +"/");
                eFile.setStatus(0);
                eFile.setAttr(1);
                eFile.setAttrex(1);
                eFile.setCreator("ROOT");
                eFile.setXlh(scienceCode);
                eFile.setCreatetime(new Date());
                eFile.setFilesize(((Long) sFile.length()).intValue() / 1000);
                eFile.setMd5(MD5.getFileMD5String(sFile));
                insertEfile(tableName, eFile);
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
            noFieldList = sGroupMapper.getFtableList("F_D_FILE"+noLibcode);
        }
        return noFieldList;
    }

    /**
     * xyPrj字段对应关系
     */
    private Map<String , String> getXyPrjFieldMappingList(){
        if(xyPrjFieldMapping == null){
            xyPrjFieldMapping = quert2Colum4Map("SELECT * FROM "+ xyPrjFieldMapping , "F1" ,"F2");
        }
        return xyPrjFieldMapping;
    }

    /**
     * noPrj字段对应关系
     */
    private Map<String , String> getnoPrjFieldMappingList(){
        if(noPrjFieldMapping == null){
            noPrjFieldMapping = quert2Colum4Map("SELECT * FROM "+ noPrjFieldMapping , "F1" ,"F2");
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
    protected Map<String , String> xyPrjFieldMapping = null;//OA档案字段对应的字段list
    protected Map<String , String> noPrjFieldMapping = null;//OA档案字段对应的字段list
    protected List<FDTable> xyFieldList = null;//xy科研f表的字段list
    protected List<FDTable> noFieldList = null;//no科研f表的字段list
    @Autowired
    private JdbcDao jdbcDao;
    @Autowired
    private KyDao kyDao;

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
