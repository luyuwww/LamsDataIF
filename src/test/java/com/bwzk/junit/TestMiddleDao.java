package com.bwzk.junit;/**
 * Created by DaMo on 2017-08-19.
 */

import com.bwzk.dao.MiddleDao;
import com.bwzk.service.OutInterfaceServcie;
import com.caucho.hessian.client.HessianProxyFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 * @UPDATE 2017-08-19-19:13
 * @since 2017-08-19-19:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/test*.xml"})
public class TestMiddleDao {
    @Test
    public void test001() throws MalformedURLException {
        List<Map<String, Object>> rsult =  middleDao.pageList(
                "jdbc:mysql://127.0.0.1:3306/zjx?useUnicode=true&amp;characterEncoding=UTF-8",
                "root","root1234","com.mysql.jdbc.Driver","select * from s_user" , 1, 10, "DID" , "DID");
        for (Map<String, Object> obj : rsult) {
            System.out.println("mysql-"+obj.get("DID")+":"+obj.get("USERNAME"));

        }

        List<Map<String, Object>> rsultsqlserver =  middleDao.pageList(
                "jdbc:jtds:sqlserver://127.0.0.1:1433/cjyt;tds=8.0;lastupdatecount=true",
                "sa","ams2000","net.sourceforge.jtds.jdbc.Driver","select * from s_user" , 1, 10, "DID" , "DID");
        for (Map<String, Object> obj : rsultsqlserver) {
            System.out.println("sqlserver-"+obj.get("DID")+":"+obj.get("USERNAME"));

        }
        List<Map<String, Object>> oracleList =  middleDao.pageList(
                "jdbc:oracle:thin:@127.0.0.1:1521:ORCL",
                "thams","ams2000","oracle.jdbc.driver.OracleDriver","select * from s_user" , 1, 1022222, "DID" , "DID");
        for (Map<String, Object> obj : oracleList) {
            System.out.println("oracle-"+obj.get("DID")+":"+obj.get("USERNAME"));

        }

    }

//druid.datasource.url=jdbc:mysql://beijing2008.mysql.rds.aliyuncs.com:3308/stm?useUnicode=true&amp;characterEncoding=UTF-8
//druid.datasource.username=lams
//druid.datasource.password=lams1234
//
//#druid.datasource.url=jdbc:jtds:sqlserver://127.0.0.1:1433/hanergy;tds=8.0;lastupdatecount=true
//#druid.datasource.username=sa
//#druid.datasource.password=ams2000
//
//#druid.datasource.url=jdbc:oracle:thin:@127.0.0.1:1521:ORCL
//#druid.datasource.username=thams
//#druid.datasource.password=ams2000
    @Autowired
    protected MiddleDao middleDao;
}
