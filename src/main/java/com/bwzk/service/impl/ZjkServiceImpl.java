package com.bwzk.service.impl;

import com.bwzk.dao.MiddleDao;
import com.bwzk.dao.i.MidDbsMapper;
import com.bwzk.dao.i.MidTabMapper;
import com.bwzk.dao.i.SUserMapper;
import com.bwzk.pojo.MidDbs;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.ZjkService;
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