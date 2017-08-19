package com.bwzk.junit;/**
 * Created by DaMo on 2017-08-19.
 */

import com.bwzk.service.OutInterfaceServcie;
import com.caucho.hessian.client.HessianProxyFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.MalformedURLException;

/**
 * @author DaMo
 * @UPDATE 2017-08-19-19:13
 * @since 2017-08-19-19:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/test*.xml"})
public class TestHassian {
    @Test
    public void test001() throws MalformedURLException {
        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setOverloadEnabled(true);
        OutInterfaceServcie remote = (OutInterfaceServcie) factory
                .create(OutInterfaceServcie.class, "http://" + lamsIP + "/Lams/hs/openInterface.hs");
        System.out.println(remote.getMaxDid("d_file1"));
    }
    @Autowired
    @Value("${lams.ip}")
    protected String lamsIP;
}
