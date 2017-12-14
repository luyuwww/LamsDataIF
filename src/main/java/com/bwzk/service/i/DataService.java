package com.bwzk.service.i;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "DataWs", targetNamespace = "http://service.unis.com/")
public interface DataService {
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
                                 @WebParam(name = "gdrCode") String gdrCode);

}
