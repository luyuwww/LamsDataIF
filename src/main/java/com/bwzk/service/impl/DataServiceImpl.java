package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.i.SUserMapper;
import com.bwzk.pojo.jaxb.Field;
import com.bwzk.pojo.jaxb.Table;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.DataService;
import com.bwzk.service.i.SingleService;
import com.bwzk.util.GlobalFinalAttr;
import com.bwzk.util.XmlObjUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service("dataService")
@WebService(name = "DataWs", targetNamespace = "http://service.unis.com/")
public class DataServiceImpl extends BaseService implements DataService {

    /**
     * <p>Title: fileReciveXML</p>
     * <p>Description: 接受数据服务 数据使用 xml 传递,utf编码 </p>
     *
     * @param dataXml xm来传递
     * @param maxLevel 传递过来最大级别
     * @param gdrCode 数据的所属人 usercode
     * @date 2014年6月19日
     */
    @WebMethod
    public Integer fileReciveXml(@WebParam(name = "dataXml") String dataXml ,
                                 @WebParam(name = "maxLevel") String maxLevel ,
                                 @WebParam(name = "gdrCode") String gdrCode){
        return 1;
    }

    @Autowired
    private SUserMapper sUserMapper;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}