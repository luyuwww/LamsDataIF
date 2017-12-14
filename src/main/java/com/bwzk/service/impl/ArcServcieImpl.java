package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.JdbcDao;
import com.bwzk.dao.i.da.SGroupMapper;
import com.bwzk.dao.i.da.SUserMapper;
import com.bwzk.dao.i.da.SUserroleMapper;
import com.bwzk.pojo.SUser;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.ArcService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.util.List;

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

    @Autowired
    private SGroupMapper sGroupMapper;
    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    private SUserroleMapper sUserroleMapper;
    @Autowired
    private JdbcDao jdbcDao;

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
