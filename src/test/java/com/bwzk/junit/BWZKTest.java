package com.bwzk.junit;

import com.bwzk.service.BaseService;
import com.bwzk.service.i.FtpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/test*.xml"})
public class BWZKTest extends AbstractJUnit4SpringContextTests {
    // @Autowired
    // @Value("${interface.log.home.address}")
    // private String logHomeAdd;
    // @Autowired
    // private ArcService arcServcieImpl;
    @Test
    public void test() throws Exception {

        ftpServcie.downloadFile(baseService.getFtpIp(),baseService.getFtpUser()
                ,baseService.getFtpPassword(),baseService.getFtpPort(),"/测试/22479_预算调整1 (2).pdf"
                ,"d:/luyu/测试/测试/22479_预算调整1 (2).pdf");
    }

    @Autowired
    private BaseService baseService;

    @Autowired
    private FtpService ftpServcie;

    // @Autowired
    // private SingleService arcDataService;
}
