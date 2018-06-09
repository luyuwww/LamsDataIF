package com.bwzk.junit;

import com.bwzk.pojo.EFile;
import com.bwzk.service.BaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

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
//
    }

    @Autowired
    private BaseService baseService;
    // @Autowired
    // private SingleService arcDataService;
}
