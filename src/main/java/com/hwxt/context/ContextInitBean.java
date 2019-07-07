package com.hwxt.context;

import ch.qos.logback.classic.Logger;
import com.hwxt.dao.i.SGroupMapper;
import com.hwxt.util.XmlUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

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
    private SGroupMapper sGroupMapper;
    @Autowired
    private XmlUtil createXmlUtil;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
