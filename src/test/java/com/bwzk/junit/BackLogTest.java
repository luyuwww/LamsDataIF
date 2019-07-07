package com.hwxt.junit;

import com.hwxt.pojo.SBacklog;
import com.hwxt.service.i.NoticeService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.JAXBException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/test*.xml"})
public class BackLogTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private NoticeService noticeServiceImpl;

    @Test
    public void test01() {
        List<SBacklog> backLoList = noticeServiceImpl.allBacklog("lihong");
        for (SBacklog bakLog : backLoList) {
            System.out.println(bakLog.getDetail());
        }
    }

    @Test
    public void test02() throws JAXBException {
    }

    @Test
    public void test03() {

        String[] ass = StringUtils.split(",luyu,ab,ccc,de", "[,]");
        System.out.println(ass.length);
        System.out.println(ass[0]);
    }
}
