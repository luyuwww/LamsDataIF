package com.hwxt.service.impl;

import com.hwxt.dao.MiddleDao;
import com.hwxt.dao.i.MidDbsMapper;
import com.hwxt.dao.i.MidTabMapper;
import com.hwxt.dao.i.SUserMapper;
import com.hwxt.pojo.MidDbs;
import com.hwxt.pojo.MidFieldMapping;
import com.hwxt.pojo.MidTab;
import com.hwxt.pojo.MidTabExample;
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
        String[] arr = mt.getUpdatesql().split("[;]");
        if(arr.length != 3){
            throw new RuntimeException("格式错误："+mt.getUpdatesql());
        }

        List<MidFieldMapping> mappingList = midTabMapper.listFieldMapping(mt.getMtbname() , mt.getPid() , mt.getDid());
        String ssql = generatorStableSelectSql(mt , mappingList);
        if(StringUtils.isNotBlank(ssql)){
            Integer totalCount = middleDao.countSzie(dbs , ssql);
            Integer tPage = totalCount/pageSize +1;
            for (int i = 1; i <= tPage; i++) {
                List<Map<String, Object>> resultList = middleDao.pageList(dbs , ssql , i, pageSize, "DID" , "DID");
                for (Map<String, Object> item : resultList) {
                    try {
                        String updateSql = "";
                        //插入一条记录
                        if(insertOneD_tab(mt , item)){
                            updateSql = generatorUpdateSql(mt , item , arr , Boolean.TRUE);
                        }else{
                            updateSql = generatorUpdateSql(mt , item , arr , Boolean.FALSE);
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
        }else{
            throw new RuntimeException("ssql 为空："+mt);
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

    private Boolean testConn(MidDbs dbs){
        return middleDao.testConnection(dbs.getDburl() , dbs.getDbtype() , dbs.getDbname()
                , dbs.getUsername() , dbs.getPassword());
    }

    /**
     * 生成源表查询sql TODO
     * @param mt
     * @param fmList
     * @return
     */
    private String generatorStableSelectSql(MidTab mt , List<MidFieldMapping> fmList){
        //如果不为空。并且简单判断是否合法
        String ssql = "";
        if(null != mt && StringUtils.isNotBlank(mt.getSsql()) &&
            (mt.getSsql().toUpperCase().startsWith("SELECT") || mt.getSsql().toUpperCase().startsWith("WHERE") )){
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

    //插入d表并且更新顶级  TODO  别忘记录日志
    private Boolean insertOneD_tab(MidTab mt  , Map<String, Object> item){


        if(mt.getDid()==1){
            //TODO 处理顶级
        }
        return Boolean.FALSE;

    }

    //插入 TODO  别忘记录日志
    private Boolean insertOneE_tab(MidTab mt  ,  Map<String, Object> item){
        return Boolean.FALSE;

    }

    //更新updatesql
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
        sql = sql + (ok? arr[0] : arr[1]) + " WHERE " + wheresql;
        return sql;
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