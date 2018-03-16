package com.bwzk.junit;/**
 * Created by DaMo on 2018-03-16.
 */

import com.bwzk.dao.i.da.FlowDataItemMapper;
import com.bwzk.dao.i.da.FlowMainMapper;
import com.bwzk.pojo.FlowDataItem;
import com.bwzk.pojo.FlowMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author DaMo
 * @UPDATE 2018-03-16-16:13
 * @since 2018-03-16-16:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/test*.xml"})
public class TestFlow {
    @Autowired
    FlowMainMapper flowMainMapper;
    @Autowired
    FlowDataItemMapper flowDataItemMapper;
    @Test
    public void test() throws Exception {
        FlowMain fm = new FlowMain();
        fm.setId(System.currentTimeMillis()+"");

        fm.setFid("111");
        fm.setApplyauth("浏览");
        fm.setApplytype(1);
        fm.setApplytimes(323);
        fm.setStardate("2018-12-12");
        fm.setRequesttime("2018-12-12");
        fm.setEnddate("2018-12-12");
        fm.setUserid("sdfsdf");
        fm.setUsercode("luyu");
        fm.setMemo("cesdfjoowenvsfd bz");
        fm.setStatus(0);
        fm.setResult(1);
        fm.setMsg("tongyi");
        fm.setRequestid("oahuixie");
        fm.setFtriggerflag("yes");

        flowMainMapper.insert(fm);
    }

    @Test
    public void test1() throws Exception {
        FlowDataItem fm = new FlowDataItem();
        fm.setId(System.currentTimeMillis()+"");

        fm.setPid(System.currentTimeMillis()+"");
        fm.setTablename("D_FILE1");
        fm.setKeyword("dangahoa-23-213");
        fm.setTitle("关于真是东方么 的lasof");
        fm.setMj("mimi");
        fm.setBgqx("changduan");
        fm.setArcid(1);
        flowDataItemMapper.insert(fm);
    }
    @Test
    public void test12() throws Exception {
        FlowMain fm = new FlowMain();
        fm.setId("1521188886568");
        fm.setStatus(1);
        flowMainMapper.updateByPrimaryKeySelective(fm);

    }

    @Test
    public void testuuid(){
        System.out.println( UUID.randomUUID().toString());
    }
}
