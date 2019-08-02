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
        MidTabExample example = new MidTabExample();
        example.createCriteria().andPidEqualTo(midDbsDid);
        example.setOrderByClause("DID ASC");
        return midTabMapper.selectByExample(example);
    }

    /**
     * 测试连接
     * @param midDbsDID
     */
    public Boolean testConn(Integer midDbsDID){
        MidDbs dbs = midDbsMapper.selectByPrimaryKey(midDbsDID);
        return middleDao.testConnection(dbs.getDburl() , dbs.getDbtype() , dbs.getDbname()
                , dbs.getUsername() , dbs.getPassword());
    }


    public void startSyncAll(){

    }

    public void startSyncOneDBS(Integer midDbsDID) {
        MidDbs dbs = midDbsMapper.selectByPrimaryKey(midDbsDID);
        MidTabExample me = new MidTabExample();
        me.createCriteria().andPidEqualTo(midDbsDID);
        me.setOrderByClause("DID ASC");
        List<MidTab> mtList = midTabMapper.selectByExample(me);
        for (MidTab midTab : mtList) {
            if(midTab.getTtbname().toUpperCase().startsWith("D_")){
                syncLevel(dbs , midTab);
            }else{
                syncEfile(dbs , midTab);
            }
        }
    }

    /**
     * 同步非efile
     */
    private void syncLevel(MidDbs dbs , MidTab tb){
        List<MidFieldMapping> list =  midTabMapper.listFieldMapping(tb.getMtbname());
        if(null != list && list.size() > 0){
            Integer tSzie = middleDao.countSzie(dbs , tb.getSsql();
            Integer tPage = tSzie/pageSize +1;
            for (int i = 1; i <= tPage; i++) {
                List<Map<String, Object>> rsult = middleDao.pageList(dbs
                        , tb.getSsql() , 1, 10, "DID" , "DID");
            }
            while(middleDao.countSzie(dbs , tb.getSsql() > 0){

            }
            List<Map<String, Object>> rsult = middleDao.pageList(dbs
                    , tb.getSsql() , 1, 10, "DID" , "DID");
//
        }
    }

    /**
     * 同步eifle
     * @param table
     */
    private void syncEfile(MidDbs dbs , MidTab tb){

    }




    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    protected MiddleDao middleDao;
    @Autowired
    protected MidDbsMapper midDbsMapper;
    @Autowired
    protected MidTabMapper midTabMapper;

    private static Integer pageSize = 30;

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}