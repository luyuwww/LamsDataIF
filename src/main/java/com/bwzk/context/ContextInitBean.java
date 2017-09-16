package com.bwzk.context;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.BaseDao;
import com.bwzk.dao.JdbcDao;
import com.bwzk.dao.i.SGroupMapper;
import com.bwzk.dao.i.SUserMapper;
import com.bwzk.util.XmlUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

@Component
public class ContextInitBean implements InitializingBean, ServletContextAware {

    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void setServletContext(ServletContext arg0) {
        try {
            createXmlUtil.generatorXml();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Autowired
    private XmlUtil createXmlUtil;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
