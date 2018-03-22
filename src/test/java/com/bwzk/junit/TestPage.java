package com.bwzk.junit;/**
 * Created by DaMo on 2018-03-16.
 */

import com.bwzk.dao.JdbcDao;
import com.bwzk.dao.i.SUserMapper;
import com.bwzk.page.PageContext;
import com.bwzk.pojo.EFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author DaMo
 * @UPDATE 2018-03-16-16:13
 * @since 2018-03-16-16:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/test*.xml"})
public class TestPage {



    @Test
    public void testuuid(){
        System.out.println( UUID.randomUUID().toString());
    }

    @Test
    public void testFy(){
        PageContext page = PageContext.getContext();

        page.setCurrentPage(1);
        page.setPageSize(12);
        page.setPagination(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("sql", "select * from s_user order by did");
        List<Map<String, Object>> mapList = sUserMapper.listPageMapQuery(map);
        Integer step = 1;
        for (Map<String, Object> ob : mapList) {
            System.out.println(step++ +"   " + ob.get("DID")+" : "+ ob.get("USERNAME"));
        }
    }

    @Test
    public void testF1y(){
        PageContext page = PageContext.getContext();

        page.setCurrentPage(1);
        page.setPageSize(50);
        page.setPagination(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("sql", "SELECT * FROM E_FILE1 WHERE STATUS=0 AND (BBH IS NULL OR BBH ='')");
        List<EFile> mapList = sUserMapper.efilelistPage(map);
        Integer step = 1;
        for (EFile ob : mapList) {
            System.out.println(step++ +"   " + ob.getDid()+" : "+ ob.getTitle());
        }
    }

    @Test
    public void testF1y11(){
        List<String> intll = jdbcDao.query4List("select TITLE from d_file1" , String.class);
        System.out.println(intll.get(0));
    }

    @Test
    public void testF1y112(){
        Integer di = jdbcDao.query4Object("select did from d_file1 where did=1" , Integer.class);
        System.out.println(di);
    }
    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    SUserMapper sUserMapper;
}
