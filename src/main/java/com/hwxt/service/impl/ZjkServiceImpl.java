package com.hwxt.service.impl;

import com.hwxt.dao.MiddleDao;
import com.hwxt.dao.i.MidDbsMapper;
import com.hwxt.dao.i.MidTabMapper;
import com.hwxt.dao.i.SUserMapper;
import com.hwxt.pojo.MidDbs;
import com.hwxt.pojo.MidTab;
import com.hwxt.pojo.MidTabExample;
import com.hwxt.service.BaseService;
import com.hwxt.service.i.ZjkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("zjkService")
public class ZjkServiceImpl extends BaseService implements ZjkService {
    public List<MidDbs> listAllMidDbs(){
        return midDbsMapper.listAll();
    }

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
        for (MidTab  midTab: mtList) {
            System.out.println(midTab.getMtbname());

        }
    }


    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    protected MiddleDao middleDao;
    @Autowired
    protected MidDbsMapper midDbsMapper;
    @Autowired
    protected MidTabMapper midTabMapper;
//      return midDbsMapper.listMidFieldMapping();

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}