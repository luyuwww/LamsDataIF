package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.i.oa.OaUserMapper;
import com.bwzk.pojo.OaUser;
import com.bwzk.pojo.OaUserExample;
import com.bwzk.pojo.SUser;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.OrgService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("orgServiceImpl")
public class OrgServcieImpl extends BaseService implements OrgService {

    @Autowired
    private OaUserMapper oaUserMapper;

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());

    public List<SUser> listDaUsers() {
        return sUserMapper.getAllUserList();
    }

    public List<OaUser> listOaUsers() {
        OaUserExample example = new OaUserExample();
        example.createCriteria().andLoginidIsNotNull();
        List<OaUser> resultr = oaUserMapper.selectByExample(example);
        System.out.println(resultr.size());
        return resultr;
    }

    public List<OaUser> listOaUsersLess4(){
        String[] inStatus = {"0","1","2","3"};
        OaUserExample example = new OaUserExample();
        example.createCriteria().andLoginidIsNotNull()
        .andStatusIn(Arrays.asList(inStatus));
        List<OaUser> resultr = oaUserMapper.selectByExample(example);
        System.out.println(resultr.size());
        return resultr;
    }

    public void sync(){
        syncGroup();
        syncUser();
    }

    private void syncGroup(){

    }
    private void syncUser(){

    }
}
