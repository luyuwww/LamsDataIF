package com.hwxt.service.impl;

import com.hwxt.dao.MiddleDao;
import com.hwxt.dao.i.MidDbsMapper;
import com.hwxt.dao.i.MidTabMapper;
import com.hwxt.dao.i.SUserMapper;
import com.hwxt.pojo.FDTable;
import com.hwxt.pojo.MidDbs;
import com.hwxt.pojo.MidFieldMapping;
import com.hwxt.pojo.MidTab;
import com.hwxt.service.BaseService;
import com.hwxt.service.i.ZjkService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("zjkService")
public class ZjkServiceImpl extends BaseService implements ZjkService {
    public List<MidDbs> listAllMidDbs(){
        return midDbsMapper.listAll();
    }

    /**
     * 列出一个中间库下的中间表。
     * @param midDbsDid
     * @return
     */
    public List<MidTab> listMidTablsByDbsId(Integer midDbsDid){
        return  midTabMapper.listMidTab4Pid(midDbsDid);
    }

    /**
     * 测试连接
     * @param midDbsDID
     */
    public Boolean testConn(Integer midDbsDID){
        MidDbs dbs = midDbsMapper.selectByPrimaryKey(midDbsDID);
        return testConn(dbs);
    }


    public void startSyncAll(){
        List<MidDbs> midDbsList = midDbsMapper.listAll();
        for (MidDbs dbs : midDbsList) {
            if(testConn(dbs)){
                try {
                    startSyncOneDBS(dbs);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }
            }else{
                log.error("数据库连接失败：" +dbs);
            }
        }

    }

    public void startSyncOneDBS(Integer midDbsDID) throws Exception{
        MidDbs dbs = midDbsMapper.selectByPrimaryKey(midDbsDID);
        startSyncOneDBS(dbs);
    }

    /**
     * 同步非efile
     */
    private void syncLevel(MidDbs dbs , MidTab mt){
        String[] updateArr = mt.getUpdatesql().split("[;]");
        String[] pidArr = mt.getPidsql().split("[;]");
        List<FDTable> fdTableList = fTableList(mt.getTtbname());
        //验证几个语句
        if(updateArr.length != 3){
            throw new RuntimeException("p_mitab.updatesql格式错误："+mt.getUpdatesql());
        }
        if(pidArr.length != 3){
            throw new RuntimeException("p_mitab.Pidsql格式错误："+mt.getUpdatesql());
        }
        if(!(mt.getSsql().toUpperCase().startsWith("SELECT") || mt.getSsql().toUpperCase().startsWith("WHERE"))){
            throw new RuntimeException("p_mitab.ssql格式错误："+mt.getSsql());
        }

        List<MidFieldMapping> mappingList = midTabMapper.listFieldMapping(mt.getMtbname() , mt.getPid() , mt.getDid());
        if(null == mappingList || mappingList.size() ==0){
            throw new RuntimeException(mt.getDid()+"p_mitab字段对应关系不可以为空");
        }

        String ssql = generatorSsql(mt , mappingList);
        if(StringUtils.isBlank(ssql)){
            throw new RuntimeException("ssql 为空或者生成错误："+mt);
        }

        Integer totalCount = middleDao.countSzie(dbs , ssql);
        Integer tPage = totalCount/pageSize +1;
        for (int i = 1; i <= tPage; i++) {
            List<Map<String, Object>> resultList = middleDao.pageList(dbs , ssql , i, pageSize, "DID" , "DID");
            for (Map<String, Object> item : resultList) {
                try {
                    String updateSql = "";
                    //插入一条记录
                    //插入数据
                    if(insertOneD_tab(mt , item , mappingList , fdTableList , pidArr)){
                        updateSql = generatorUpdateSql(mt , item , updateArr , Boolean.TRUE);
                    }else{
                        updateSql = generatorUpdateSql(mt , item , updateArr , Boolean.FALSE);
                    }
                    //更新
                    if(StringUtils.isNotBlank(updateSql)){
                        middleDao.execUpdateSql( dbs , updateSql);
                    }else{
                        log.error("update sql 错误："+mt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(StringUtils.isNotBlank(mt.getCallbacksql())){
                jdbcDao.excute(mt.getCallbacksql());
            }
        }

    }

    /**
     * 同步efile
     * @param table
     */
    private void syncEfile(MidDbs dbs , MidTab tb){

    }

    /**
     * 开始同步一条 middb
     * @param dbs
     * @throws Exception
     */
    private void startSyncOneDBS(MidDbs dbs) throws Exception{
        List<MidTab> mtList = midTabMapper.listMidTab4Pid(dbs.getDid());
        for (MidTab midTab : mtList) {
            if(midTab.getTtbname().toUpperCase().startsWith("D_")){
                syncLevel(dbs , midTab);
            }else{
                syncEfile(dbs , midTab);
            }
        }
    }

    //插入d表并且更新顶级
    private Boolean insertOneD_tab(MidTab mt  , Map<String, Object> item , List<MidFieldMapping> mappingList ,
                                   List<FDTable> fdTableList , String[] pidSqlArr){
        Boolean result = Boolean.FALSE;
        String SQL = "";
        try {
            String pidSql = generatorPidSql(mt , item ,pidSqlArr);
            Integer pid = queryPid(pidSql);
            StringBuffer fields = new StringBuffer();
            StringBuffer values = new StringBuffer();
            Integer maxdid = getMaxDid(mt.getTtbname());
            for (MidFieldMapping mp : mappingList) {
                String defaultValue = StringUtils.isNotBlank(mp.getDefaultvalue()) ? mp.getDefaultvalue() : "";
                FDTable tField = getFDtable(fdTableList , mp.getTfield());
                if(null == tField){
                    log.error(mt.getTtbname()+" not found field:"+ mp.getTfield());
                    continue;
                }
                String theValue = (item.get(mp.getSfield()) == null ? "" : item.get(mp.getSfield()).toString());
                theValue = StringUtils.isBlank(theValue) ? defaultValue : theValue;
                theValue = theValue.contains("'") ? theValue.replace("'", "''") : theValue;
                fields.append(tField.getFieldname()).append(",");
                switch (tField.getFieldtype()) {
                    case 11:
                        if (theValue.equals("")) {
                            values.append(getSysdate() + ",");
                        } else {
                            values.append(generateTimeToSQLDate(theValue)).append(",");
                        }
                        break;
                    case 1:
                        values.append("'").append(theValue).append("',");
                        break;
                    case 3:
                        if (StringUtils.isBlank(theValue)) {
                            values.append("null ,");
                        } else {
                            values.append(Integer.parseInt(theValue)).append(",");
                        }
                        break;
                    default:
                        values.append("'").append(theValue).append("',");
                        break;
                }
                fields.append(mt.getDefaultfield()).append(",DID,PID");
                values.append(mt.getDefaultvalue()).append(","+maxdid).append(","+pid);
            }
            SQL = "insert into " + mt.getTtbname() + " (" + fields.toString()
                    + ") values ( " + values.toString() + " )";
            execSql(SQL);
            log.error("插入一条数据成功.SQL: " + SQL);
            fields.setLength(0);
            values.setLength(0);
            result = Boolean.TRUE;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.error("失败 SQL："+ SQL+ " " + e.getMessage() , e);
        }
        return result;

    }

    //插入 TODO  别忘记录日志
    private Boolean insertOneE_tab(MidTab mt  ,  Map<String, Object> item){
        return Boolean.FALSE;

    }

    /**
     * 生成源表查询sql
     * @param mt
     * @param fmList
     * @return
     */
    private String generatorSsql(MidTab mt , List<MidFieldMapping> fmList){
        //如果不为空。并且简单判断是否合法
        String ssql = "";
        if(null != mt && StringUtils.isNotBlank(mt.getSsql()) &&
                (mt.getSsql().toUpperCase().startsWith("SELECT") || mt.getSsql().toUpperCase().startsWith("WHERE"))){
            if(mt.getSsql().toUpperCase().startsWith("SELECT")){
                ssql = mt.getSsql();
            }else{
                ssql = "SELECT ";
                for (MidFieldMapping mp : fmList) {
                    if(StringUtils.isNotBlank(mp.getSfield())){
                        ssql += (mp.getSfield()+" , ");
                    }
                }
                ssql +=("1 aaaaaaaa "+ mt.getSsql());
            }
        }else{
            ssql = "";
            throw new RuntimeException("检查源表查询语句SSQL是否合法："+mt);
        }
        return ssql.replace("`" , "'");
    }

    //更新updatesql  flag=1;flag=2;did
    private String generatorUpdateSql(MidTab mt , Map<String, Object> item ,String[] arr , Boolean ok){
        Object value = item.get(arr[2]);
        if(null == value){
            return "";
        }
        String wheresql = "";
        String sql = "UPDATE " + mt.getStbname() +" SET ";
        if(value instanceof Integer){
            wheresql = arr[2]+" = " + ((Integer) value);
        }else if(value instanceof Number){
            wheresql = arr[2]+" = " + ((Number) value).intValue();
        }else{
            wheresql = arr[2]+" = '"+value+"'";
        }
        sql = sql + (ok ? arr[0] : arr[1]) + " WHERE " + wheresql;
        return sql;
    }

    //更新updatesql select did from d_prj3;prjcode;prjcode
    private String generatorPidSql(MidTab mt , Map<String, Object> item ,String[] pidArr){
        String pTableFiled = pidArr[1];
        Object value = item.get(pidArr[2]);
        if(null == value){
            return "";
        }
        String whereSql = "";
        if(value instanceof Integer){
            whereSql = pTableFiled +" = " + ((Integer) value);
        }else if(value instanceof Number){
            whereSql = pTableFiled +" = " + ((Number) value).intValue();
        }else{
            whereSql = pTableFiled +" = '"+value+"'";
        }
        String pidSql = pidArr[0] + " WHERE " + whereSql ;
        return pidSql;
    }

    private Boolean testConn(MidDbs dbs){
        return middleDao.testConnection(dbs.getDburl() , dbs.getDbtype() , dbs.getDbname()
                , dbs.getUsername() , dbs.getPassword());
    }
    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    protected MiddleDao middleDao;
    @Autowired
    protected MidDbsMapper midDbsMapper;
    @Autowired
    protected MidTabMapper midTabMapper;

    private static Integer pageSize = 50;

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}