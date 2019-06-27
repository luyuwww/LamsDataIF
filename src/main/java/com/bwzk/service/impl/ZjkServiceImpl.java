package com.bwzk.service.impl;

import com.bwzk.dao.i.SUserMapper;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.ZjkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("zjkService")
public class ZjkServiceImpl extends BaseService implements ZjkService {

    public void test(){

    }

    @Autowired
    private SUserMapper sUserMapper;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}