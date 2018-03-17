package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.JdbcDao;
import com.bwzk.dao.i.da.SGroupMapper;
import com.bwzk.dao.i.da.SUserMapper;
import com.bwzk.dao.i.da.SUserroleMapper;
import com.bwzk.page.PageContext;
import com.bwzk.pojo.DClassifyZjk;
import com.bwzk.pojo.EFile;
import com.bwzk.pojo.FDTable;
import com.bwzk.pojo.SUser;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.ArcService;
import com.bwzk.util.DateUtil;
import com.bwzk.util.GlobalFinalAttr;
import com.bwzk.util.MD5;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service("arcServcieImpl")
@WebService(name = "ArcDataWs", targetNamespace = "http://service.unis.com/")
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

    public String syncDclassfy(Integer libcode){
        String msg = "";
        Integer num = 0;
        List<DClassifyZjk> flhList = sGroupMapper.listFlh(fhlZjb , libcode);

        if(null != flhList && flhList.size()>0){
            jdbcDao.excute("DELETE FROM D_CLASSIFY"+libcode);
            for (DClassifyZjk classy : flhList) {
                sUserMapper.insertClassify(classy , "D_CLASSIFY"+libcode);
                log.error("插入一条分类表 数据成功.syncDclassfy: "
                        + classy.getDid()+" : " + classy.getFlcode()+" : " + classy.getFlmc());
                num++;
            }
            msg = "同步成功:同步"+num+"条数据";
        }else{
            msg = "同步事变:中间库中该类型的分类为空";
        }
        return msg;
    }

    public String syncOaData(){
        Integer dNum = 0;
        Integer eNum = 0;
        String msg = "OA data ok 好的";
        //默认数据库当前时间
        String dateStr = getDatabaseType().getDateByDb();

        List<FDTable> fDTableList = getWsFieldList();// 相关档案类型的字段List
        String oaDfileSql = "SELECT * FROM " + oaDFile + " WHERE STATUS= 0 ORDER BY CREATETIME DESC";
        List<Map<String, Object>> dataList = listMaps(oaDfileSql);

        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        for (Map<String, Object> dataMap : dataList) {
            String oaid = "";
            String tableName = "";
            Integer maxdid = 0;
            if (dataMap.size() > 0 && dataMap != null) {
                oaid = dataMap.get("UUID").toString();
                tableName = "D_FILE" + libcode + "";

                List<Map<String,Object>> fjList = quertListMap("SELECT * FROM "+oaEFile + " WHERE PUUID='" + oaid + "'");
                try {
                    maxdid = getMaxDid(tableName);
                    for (String OAData : dataMap.keySet()) {
                        FDTable wsField = getWsField(OAData);
                        if(null != wsField){
                            String oaValue = (dataMap.get(OAData) == null ? "" : dataMap.get(OAData).toString());
                            oaValue = (oaValue.contains("'") ? oaValue.replace(
                                    "'", "''") : oaValue);
                            fields.append(wsField.getFieldname()).append(",");
                            switch (wsField.getFieldtype()) {
                                case 11:
                                    if (StringUtils.isNotBlank(oaValue)) {
                                        values.append(dateStr+",");
                                    } else {
                                        values.append(generateTimeToSQLDate(OAData)).append(",");
                                    }
                                    break;
                                case 1:
                                    values.append("'").append(oaValue).append("',");
                                    break;
                                case 3:
                                    if (StringUtils.isBlank(oaValue)) {
                                        values.append("null ,");
                                    } else {
                                        values.append(Integer.parseInt(oaValue))
                                                .append(",");
                                    }
                                    break;
                                default:
                                    values.append("'").append(oaValue).append("',");
                                    break;
                            }
                        }
                    }
                    fields.append(defaultField);
                    values.append(defaultValue);

                    fields.append(",pid,createtime,qzh,did,attached");
                    values.append(",-1,sysdate,'");
                    values.append(defaultQzh).append("',").append(maxdid).append(",").append(
                            fjList.size() > 0 ? 1 : 0       );

                    String InsertSql = "insert into " + tableName + "" + " ("
                            + fields.toString() + ") values (" + values.toString() + " )";
                    log.error("插入一条数据成功.fileReciveTxt: " + InsertSql);
                    execSql(InsertSql);
                    jdbcDao.excute("UPDATE " + oaDFile + " SET STATUS = 1 WHERE UUID = '" + oaid + "'");
                    fields.setLength(0);
                    values.setLength(0);
                    addEfile(fjList , maxdid);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("插入一条数据失败.fileReciveTxt: " + e.getMessage());
                    break;
                }
            }
        }
        return msg;
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
    private void addEfile(List<Map<String , Object>> fjList , Integer pid) {
        String tableName = "E_FILE" + libcode ;
        String euuid , docID , eBizName = "";

        for (Map<String, Object> dataMap : fjList) {
            euuid = dataMap.get("UUID") == null ? "" : MapUtils.getString(dataMap , "UUID");
            docID = dataMap.get("DOCUID") == null ? "" : MapUtils.getString(dataMap , "DOCUID");
            eBizName = dataMap.get("FILEBIZNAME") == null ? "" : MapUtils.getString(dataMap , "FILEBIZNAME");
            File sFile = new File(FilenameUtils.normalize(basePath + docID));
            if(sFile.exists()){
                String efilepath = DateUtil.getCurrentDateStr("yyyy/MM/dd/");
                File tFile = new File(FilenameUtils.normalize(basePath + efilepath + docID));
                try {
                    FileUtils.moveFile(sFile , tFile);
                } catch (IOException e1) {
                    log.error(e1.getMessage() , e1);
                }
                if(tFile.exists()){
                    EFile eFile = new EFile();
                    //DID,PID,EFILENAME,TITLE,EXT,PZM,PATHNAME,STATUS,ATTR,ATTREX,CREATOR,CREATETIME,FILESIZE,MD5,CONVERTSTATUS
                    eFile.setDid(getMaxDid(tableName));
                    eFile.setPid(pid);
                    eFile.setEfilename(docID);
                    eFile.setTitle(StringUtils.isBlank(eBizName) ? docID : eBizName);
                    eFile.setExt(FilenameUtils.getExtension(docID));
                    eFile.setPzm(pzm);
                    eFile.setPathname(ftpXdlj+efilepath);
                    eFile.setStatus(0);
                    eFile.setAttr(1);
                    eFile.setAttrex(1);
                    eFile.setCreator("ROOT");
                    eFile.setCreatetime(new Date());
                    eFile.setFilesize(((Long) tFile.length()).intValue() / 1000);
                    eFile.setMd5(MD5.getFileMD5String(tFile));
                    insertEfile(tableName , eFile);
                }
            }else{
                log.error(basePath + docID+" 文件不存在");
            }
        }

    }

    /**
     * 文书字段列表
     * @return
     */
    private List<FDTable> getWsFieldList(){
        if(oaFieldList == null){
            oaFieldList = sGroupMapper.getFtableList("F_D_FILE"+libcode);
        }
        return oaFieldList;
    }

    /**
     * oa和档案的字段对应关系
     * @return
     */
    private Map<String , String> getFieldMappingList(){
        if(fieldMapping == null){
            fieldMapping = quert2Colum4Map("SELECT * FROM "+ fieldMappingtbName , "F1" ,"F2");
        }
        return fieldMapping;
    }

    /**
     * 通过对应关系得到文书档案的字段
     * @param oaField
     * @return
     */
    private FDTable getWsField(String oaField){
        FDTable result = null;
        Map<String , String> mapping = getFieldMappingList();
        List<FDTable> daFieldList = getWsFieldList();
        String daField = getFieldMappingList().get(oaField);

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

    protected Map<String , String> fieldMapping = null;//OA档案字段对应的字段list
    protected List<FDTable> oaFieldList = null;//OA文书f表的字段list
    @Autowired
    private SGroupMapper sGroupMapper;
    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    private SUserroleMapper sUserroleMapper;
    @Autowired
    private JdbcDao jdbcDao;

    /**
     * 分类号中间表的表名
     */
    @Autowired
    @Value("${dclassfy.tablename}")
    protected String fhlZjb;
    @Autowired
    @Value("${lams.oaDfile.table}")
    protected String oaDFile;
    @Autowired
    @Value("${lams.oaEfile.table}")
    protected String oaEFile;
    @Autowired
    @Value("${lams.oa.libcode}")
    protected Integer libcode;
    @Autowired
    @Value("${lams.oa.mappingtableanme}")
    protected String fieldMappingtbName;
    @Autowired
    @Value("${lams.defaultField}")
    protected String defaultField;
    @Autowired
    @Value("${lams.defaultField.value}")
    protected String defaultValue;
    @Autowired
    @Value("${lams.oa.efile.basePath}")
    protected String basePath;
    @Autowired
    @Value("${lams.oa.efile.xdlj.basePath}")
    protected String ftpXdlj;

    @Autowired
    @Value("${lams.pzm}")
    protected String pzm;

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
