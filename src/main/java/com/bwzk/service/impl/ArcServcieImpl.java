package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.JdbcDao;
import com.bwzk.dao.i.da.SGroupMapper;
import com.bwzk.dao.i.da.SUserMapper;
import com.bwzk.dao.i.da.SUserroleMapper;
import com.bwzk.pojo.DClassifyZjk;
import com.bwzk.pojo.EFile;
import com.bwzk.pojo.FDTable;
import com.bwzk.pojo.SUser;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.ArcService;
import com.bwzk.util.DateUtil;
import com.bwzk.util.HttpDownload;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public List<SUser> listAllUser() {
        return sUserMapper.getAllUserList();
    }

    public List<Map<String , Object>> listZjkSWDList(){
        return jdbcDao.listZjkMap("SELECT * FROM "+oaGWDFile +" WHERE TBBJ=0 ORDER BY FWWH");
    }

    public List<Map<String , Object>> listZjkXWLWDList(){
        return jdbcDao.listZjkMap("SELECT * FROM "+oaXWLWDFile +" WHERE TBBJ=0 ORDER BY FWWH");
    }

    /**
     * 列出公文IDS
     */
    private List<String> listZjkGWidDList(){
        return jdbcDao.listZjkIdList("SELECT FW_DATA_ID FROM "+oaGWDFile +" WHERE TBBJ=0 ORDER BY FWWH");
    }
    /**
     * 列出校外来文IDS
     */
    private List<String> listZjkXWLWidList(){
        return jdbcDao.listZjkIdList("SELECT SW_DATA_ID FROM "+oaXWLWDFile +" WHERE TBBJ=0 ORDER BY SWWH");
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
        String result = "";
        result = syncGWData();
        result += syncWLFWData();
        return result;
    }

    //公文才有正文，
    public String syncGWData(){
        Integer dNum = 0;
        Integer eNum = 0;
        String msg = "公文  ";
        String tableName = "D_FILE" + libcodeGW ;
        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        //默认数据库当前时间
        String dateStr = getDatabaseType().getDateByDb();

        //获取所有ID列表
        List<String> gwIdList = listZjkGWidDList();
        //防止数据太多 内存溢出 逐条获取
        for (String oaid : gwIdList) {//oaGWDFile
            Map<String, Object> dataMap = jdbcDao.getOAItem("SELECT * FROM "+ oaGWDFile +" WHERE FW_DATA_ID='"+oaid+"'");
            String title = MapUtils.getString(dataMap , "FWBT" , "");
            //开始处理数据
            Integer maxdid = 0;
            if (dataMap.size() > 0 && dataMap != null) {
                List<Map<String,Object>> fjList = jdbcDao.listZjkMap("SELECT * FROM "+oaEFile + " WHERE WJID='" + oaid + "'");
                try {
                    maxdid = getMaxDid(tableName);
                    for (String OAData : dataMap.keySet()) {
                        FDTable wsField = getGWField(OAData);
                        if(null != wsField){
                            String oaValue = (dataMap.get(OAData) == null ? "" : dataMap.get(OAData).toString());
                            oaValue = (oaValue.contains("'") ? oaValue.replace(
                                    "'", "''") : oaValue);
                            fields.append(wsField.getFieldname()).append(",");
                            switch (wsField.getFieldtype()) {
                                case 11:
                                    if (StringUtils.isBlank(oaValue)) {
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
                    //由于OA没有把办法提供年度,所以这里用createtime他截取前4位
                    String swsj = (dataMap.get("SWSJ") == null ? "" : dataMap.get("SWSJ").toString());
                    //如果创建日期为空或则会有了年度就不截取了
                    if(StringUtils.isNotBlank(swsj) && swsj.length() > 4
                            && !fields.toString().toUpperCase().contains("ND")){
                        fields.append(",ND");
                        values.append(","+swsj.substring(0,4));
                    }

                    fields.append(",pid,createtime,qzh,did,attached,wjlx");
                    values.append(",-1,sysdate,'");
                    values.append(defaultQzh).append("',").append(maxdid).append(",").append(
                            fjList.size() > 0 ? 1 : 0).append(",'公文'");

                    String InsertSql = "insert into " + tableName + "" + " ("
                            + fields.toString() + ") values (" + values.toString() + " )";
                    log.error("插入一条数据成功.fileReciveTxt: " + InsertSql);
                    execSql(InsertSql);
                    jdbcDao.updateZjk("UPDATE " + oaGWDFile + " SET TBBJ = '1' WHERE FW_DATA_ID = '" + oaid + "'");
                    fields.setLength(0);
                    values.setLength(0);
                    //抓附件
                    addFjEfile(fjList , maxdid , libcodeWLFW);
                    //正文
                    addZwEfile(oaid , title, maxdid , libcodeGW);
                    //痕迹稿
                    addHjgEfile(oaid , title, maxdid , libcodeGW);
                    dNum++;
                } catch (Exception e) {
                    e.printStackTrace();
                    eNum++;
                    log.error("插入一条数据失败.fileReciveTxt: " + e.getMessage());
                    break;
                }
            }
        }

        try {
            execSql("UPDATE D_FILE"+ libcodeGW +" SET ATTACHED=0 WHERE DID NOT IN" +
                    " (SELECT PID FROM E_FILE"+ libcodeGW +")");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg + dNum;
    }

    //外来发文 校外来文没有正文 所有正文和痕迹稿都用doc 名字用父级的title
    public String syncWLFWData(){
        Integer dNum = 0;
        Integer eNum = 0;
        String msg = "校外来文  ";
        String tableName = "D_FILE" + libcodeWLFW;
        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        //默认数据库当前时间
        String dateStr = getDatabaseType().getDateByDb();

        //获取所有ID列表
        List<String> xwlwIdList = listZjkXWLWidList();
        //防止数据太多 内存溢出 逐条获取
        for (String oaid : xwlwIdList) {//oaGWDFile
            Map<String, Object> dataMap = jdbcDao.getOAItem("SELECT * FROM "+ oaXWLWDFile +" WHERE SW_DATA_ID='"+oaid+"'");
            String title = MapUtils.getString(dataMap , "SWBT" , "");
            //开始处理数据
            Integer maxdid = 0;
            if (dataMap.size() > 0 && dataMap != null) {
                List<Map<String,Object>> fjList = jdbcDao.listZjkMap("SELECT * FROM "+oaEFile + " WHERE WJID='" + oaid + "'");
                try {
                    maxdid = getMaxDid(tableName);
                    for (String OAData : dataMap.keySet()) {
                        FDTable wsField = getWLFWField(OAData);
                        if(null != wsField){
                            String oaValue = (dataMap.get(OAData) == null ? "" : dataMap.get(OAData).toString());
                            oaValue = (oaValue.contains("'") ? oaValue.replace(
                                    "'", "''") : oaValue);
                            fields.append(wsField.getFieldname()).append(",");
                            switch (wsField.getFieldtype()) {
                                case 11:
                                    if (StringUtils.isBlank(oaValue)) {
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
                    //由于OA没有把办法提供年度,所以这里用createtime他截取前4位
                    String swlwsj = (dataMap.get("SWLWSJ") == null ? "" : dataMap.get("SWLWSJ").toString());
                    //如果创建日期为空或则会有了年度就不截取了
                    if(StringUtils.isNotBlank(swlwsj) && swlwsj.length() > 4
                            && !fields.toString().toUpperCase().contains("ND")){
                        fields.append(",ND");
                        values.append(","+swlwsj.substring(0,4));
                    }

                    fields.append(",pid,createtime,qzh,did,attached,wjlx");
                    values.append(",-1,sysdate,'");
                    values.append(defaultQzh).append("',").append(maxdid).append(",1,'校外来文'");

                    String InsertSql = "insert into " + tableName + "" + " ("
                            + fields.toString() + ") values (" + values.toString() + " )";
                    log.error("插入一条数据成功.fileReciveTxt: " + InsertSql);
                    execSql(InsertSql);
                    jdbcDao.updateZjk("UPDATE " + oaXWLWDFile + " SET TBBJ = '1' WHERE SW_DATA_ID = '" + oaid + "'");
                    fields.setLength(0);
                    values.setLength(0);
                    //抓附件
                    addFjEfile(fjList , maxdid , libcodeWLFW);
                    //痕迹稿
                    addHjgEfile(oaid , title , maxdid , libcodeGW);
                    dNum++;
                } catch (Exception e) {
                    e.printStackTrace();
                    eNum++;
                    log.error("插入一条数据失败.fileReciveTxt: " + e.getMessage());
                    break;
                }
            }
        }

        try {
            execSql("UPDATE D_FILE"+ libcodeWLFW +" SET ATTACHED=0 WHERE DID NOT IN" +
                    " (SELECT PID FROM E_FILE"+ libcodeWLFW +")");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg + dNum;
    }

    /**
     * 电子文件集成 附件
     */
    private void addFjEfile(List<Map<String , Object>> fjList , Integer pid , Integer libcodezzz) {
        String tableName = "E_FILE" + libcodezzz ;
        String euuid , docID , FJDM , eBizName,fjdm , fjlx = "";

        for (Map<String, Object> dataMap : fjList) {
            euuid =  MapUtils.getString(dataMap , "FJID" , "");
            docID =  MapUtils.getString(dataMap , "WJID", "");
            fjdm = MapUtils.getString(dataMap , "FJDM", "");
            eBizName = MapUtils.getString(dataMap , "FJZWMC", "");
            fjlx = MapUtils.getString(dataMap , "FJLX", "");

            if(StringUtils.isBlank(euuid)){
                continue;
            }
            String ext = FilenameUtils.getExtension(fjdm);
            String realyFileName = UUID.randomUUID() + "." + ext;


            String efilepath = File.separator + tableName + File.separator
                    + DateUtil.getCurrentDateStr4Dir() +File.separator+pid+ File.separator;
            String realFile = basePath + efilepath + realyFileName;
            try {
                HttpDownload.download(fjURL+euuid, realFile);
            } catch (Exception e) {
                log.error("第一次抓取附件失败："+euuid);
            }
            File newFile = new File(realFile);
            if(newFile.exists() && FileUtils.sizeOf(newFile)>1024){
                //第一次抓取成功
            }else{
                //希尔说不存在再下载一次。我就醉了
                try {
                    Thread.sleep(2000L);
                    HttpDownload.download(fjURL+euuid, realFile);
                } catch (Exception e) {
                    log.error("第二次抓取附件-又-失败："+euuid);
                }
                newFile = new File(realFile);
            }

            if(!newFile.exists() ||  FileUtils.sizeOf(newFile)< 1024){
                log.error(euuid+":"+realFile+" 不存在或者为空");
                continue;
            }else{
                EFile eFile = new EFile();
                //DID,PID,EFILENAME,TITLE,EXT,PZM,PATHNAME,STATUS,ATTR,ATTREX,CREATOR,CREATETIME,FILESIZE,MD5,CONVERTSTATUS
                eFile.setDid(getMaxDid(tableName));
                eFile.setPid(pid);
                eFile.setTitle(FilenameUtils.getBaseName(eBizName));
                eFile.setEfilename(realyFileName);
                eFile.setExt(ext);
                eFile.setPzm(pzm);
                eFile.setPathname(FilenameUtils.normalize(ftpXdlj+efilepath));
                eFile.setStatus(0);
                eFile.setAttr(1);
                eFile.setAttrex(1);
                eFile.setCreator("ROOT");
                eFile.setCreatetime(new Date());
                eFile.setBz(euuid);
                eFile.setBbh(fjlx);
                eFile.setFilesize(((Long) newFile.length()).intValue() / 1000);
                eFile.setMd5(MD5.getFileMD5String(newFile));
                insertEfile(tableName , eFile);
            }
        }
    }

    /**
     * 抓正文 电子文件名字就叫正文
     */
    private void addZwEfile(String wjzj , String title, Integer pid , Integer libcodezzz) {
        String ext = "doc";
        String tableName = "E_FILE" + libcodezzz ;
        String realyFileName = UUID.randomUUID() + "." + ext;

        String efilepath = File.separator + tableName + File.separator
                + DateUtil.getCurrentDateStr4Dir() +File.separator+pid+ File.separator;
        String realFile = basePath + efilepath + realyFileName;
        try {
            HttpDownload.download(zwURL+wjzj, realFile);
        } catch (Exception e) {
            log.error("第一次抓取正文失败："+wjzj);
        }
        File newFile = new File(realFile);
        if(newFile.exists() && FileUtils.sizeOf(newFile)>1024){
            //第一次抓取成功
        }else{
            //希尔说不存在再下载一次。我就醉了
            try {
                Thread.sleep(2000L);
                HttpDownload.download(zwURL+wjzj, realFile);
            } catch (Exception e) {
                log.error("第二次抓取正文-也-失败："+wjzj);
            }
            newFile = new File(realFile);
        }

        if(!newFile.exists() ||  FileUtils.sizeOf(newFile)< 1024){
            log.error(wjzj +":"+realFile+" 不存在或者为空-正文");
            return;
        }else{
            EFile eFile = new EFile();
            //DID,PID,EFILENAME,TITLE,EXT,PZM,PATHNAME,STATUS,ATTR,ATTREX,CREATOR,CREATETIME,FILESIZE,MD5,CONVERTSTATUS
            eFile.setDid(getMaxDid(tableName));
            eFile.setPid(pid);
            eFile.setTitle("【正文】-"+title);
            eFile.setEfilename(realyFileName);
            eFile.setExt(ext);
            eFile.setPzm(pzm);
            eFile.setPathname(FilenameUtils.normalize(ftpXdlj+efilepath));
            eFile.setStatus(0);
            eFile.setAttr(1);
            eFile.setAttrex(1);
            eFile.setCreator("ROOT");
            eFile.setCreatetime(new Date());
            eFile.setBz(wjzj);
            eFile.setBbh("正文");
            eFile.setFilesize(((Long) newFile.length()).intValue() / 1000);
            eFile.setMd5(MD5.getFileMD5String(newFile));
            insertEfile(tableName , eFile);
        }
    }

    /**
     * 抓痕迹
     */
    private void addHjgEfile(String wjzj , String title , Integer pid , Integer libcodezzz) {
        String ext = "doc";
        String tableName = "E_FILE" + libcodezzz ;

        String realyFileName = UUID.randomUUID() + "." + ext;
        String efilepath = File.separator + tableName + File.separator
                + DateUtil.getCurrentDateStr4Dir() +File.separator+pid+ File.separator;
        String realFile = basePath + efilepath + realyFileName;
        try {
            HttpDownload.download(hjgURL+wjzj, realFile);
        } catch (Exception e) {
            log.error("第一次抓取痕迹稿失败："+wjzj);
        }
        File newFile = new File(realFile);
        if(newFile.exists() && FileUtils.sizeOf(newFile)>1024){
            //第一次就成功
        }else{
            //希尔说不存在再下载一次。我就醉了
            try {
                Thread.sleep(2000L);
                HttpDownload.download(hjgURL+wjzj, realFile);
            } catch (Exception e) {
                log.error("第二次抓取痕迹稿-也-失败："+wjzj);
            }
            newFile = new File(realFile);
        }

        if(!newFile.exists() ||  FileUtils.sizeOf(newFile)< 1024){
            log.error(wjzj +":"+realFile+" 不存在或者为空-痕迹稿");
            return;
        }else{
            EFile eFile = new EFile();
            //DID,PID,EFILENAME,TITLE,EXT,PZM,PATHNAME,STATUS,ATTR,ATTREX,CREATOR,CREATETIME,FILESIZE,MD5,CONVERTSTATUS
            eFile.setDid(getMaxDid(tableName));
            eFile.setPid(pid);
            eFile.setTitle("【痕迹稿】-"+title);
            eFile.setEfilename(realyFileName);
            eFile.setExt(ext);
            eFile.setPzm(pzm);
            eFile.setPathname(FilenameUtils.normalize(ftpXdlj+efilepath));
            eFile.setStatus(0);
            eFile.setAttr(1);
            eFile.setAttrex(1);
            eFile.setCreator("ROOT");
            eFile.setCreatetime(new Date());
            eFile.setBz(wjzj);
            eFile.setBbh("痕迹稿");
            eFile.setFilesize(((Long) newFile.length()).intValue() / 1000);
            eFile.setMd5(MD5.getFileMD5String(newFile));
            insertEfile(tableName , eFile);
        }
    }

    /**
     * 文书字段列表
     * @return
     */
    private List<FDTable> getGWFieldList(){
        if(oaFieldListGW == null){
            oaFieldListGW = sGroupMapper.getFtableList("F_D_FILE"+libcodeGW);
        }
        return oaFieldListGW;
    }

    /**
     * 文书字段列表
     * @return
     */
    private List<FDTable> getWLFWFieldList(){
        if(oaFieldListWLFW == null){
            oaFieldListWLFW = sGroupMapper.getFtableList("F_D_FILE"+libcodeWLFW);
        }
        return oaFieldListWLFW;
    }

    /**
     * oa和档案的字段对应关系 外来发文
     * @return
     */
    private Map<String , String> getFieldGWMappingList(){
        if(fieldMappingGW == null){
            fieldMappingGW = quert2Colum4Map("SELECT * FROM "+ fieldMappingtbNameGW , "F1" ,"F2");
        }
        return fieldMappingGW;
    }

    /**
     * oa和档案的字段对应关系 公文
     * @return
     */
    private Map<String , String> getFieldWLFWMappingList(){
        if(fieldMappingWLFW == null){
            fieldMappingWLFW = quert2Colum4Map("SELECT * FROM "+ fieldMappingtbNameWLFW , "F1" ,"F2");
        }
        return fieldMappingWLFW;
    }

    /**
     * 通过公文
     * @param GWField
     * @return
     */
    private FDTable getGWField(String oaField){
        FDTable result = null;
        Map<String , String> mapping = getFieldGWMappingList();
        List<FDTable> daFieldList = getGWFieldList();
        String daField = getFieldGWMappingList().get(oaField);

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
     * 通过对应关系得到文书档案的字段
     * @param oaField
     * @return
     */
    private FDTable getWLFWField(String oaField){
        FDTable result = null;
        Map<String , String> mapping = getFieldWLFWMappingList();
        List<FDTable> daFieldList = getWLFWFieldList();
        String daField = getFieldWLFWMappingList().get(oaField);

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


    protected Map<String , String> fieldMappingGW = null;//OA档案字段对应的字段list
    protected Map<String , String> fieldMappingWLFW = null;//OA档案字段对应的字段list
    protected List<FDTable> oaFieldListGW = null;//OA文书f表的字段list
    protected List<FDTable> oaFieldListWLFW = null;//OA文书f表的字段list
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
    /**
     * 公文
     */
    @Autowired
    @Value("${lams.oaSWDfile.table}")
    protected String oaGWDFile;
    /**
     * 校外公文
     */
    @Autowired
    @Value("${lams.oaXWLWDfile.table}")
    protected String oaXWLWDFile;
    @Autowired
    @Value("${lams.oaEfile.table}")
    protected String oaEFile;
    @Autowired
    @Value("${lams.oa.libcodeGW}")
    protected Integer libcodeGW;
    @Autowired
    @Value("${lams.oa.libcodeWLFW}")
    protected Integer libcodeWLFW;
    @Autowired
    @Value("${lams.oa.mappingtableanmeGW}")
    protected String fieldMappingtbNameGW;
    @Autowired
    @Value("${lams.oa.mappingtableanmeWLFW}")
    protected String fieldMappingtbNameWLFW;
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
    @Autowired
    @Value("${lams.oa.zw.downloadefileURL}")
    protected String zwURL;
    @Autowired
    @Value("${lams.oa.fj.downloadefileURL}")
    protected String fjURL;
    @Autowired
    @Value("${lams.oa.hjg.downloadefileURL}")
    protected String hjgURL;

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
