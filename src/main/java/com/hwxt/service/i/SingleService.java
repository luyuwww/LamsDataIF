package com.hwxt.service.i;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

public interface SingleService {
    /**
     * <p>Title: fileReciveTxt</p>
     * <p>Description: 接受数据服务 数据使用 txt 传递,utf编码, 用&;分割 </p>
     *
     * @param xmlName 要传递的xml名称, 不带xml
     * @param dataTxt txt 传递的值集合,utf编码, 用&;分割
     * @param gdrCode 数据的所属人 usercode
     * @date 2014年6月19日
     */
    public Integer fileReciveTxt(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataTxt") String dataTxt, @WebParam(name = "gdrCode") String gdrCode);

    /**
     * <p>Title: fileReciveTxt</p>
     * <p>Description: 接受数据服务 数据使用 xml 传递,utf编码 </p>
     *
     * @param xmlName 要传递的xml名称, 不带xml
     * @param dataXml xm来传递
     * @param gdrCode 数据的所属人 usercode
     * @date 2014年6月19日
     */
    public Integer fileReciveXml(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataXml") String dataXml, @WebParam(name = "gdrCode") String gdrCode);


    public Integer fileReciveJson(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataJson") String dataJson, @WebParam(name = "gdrCode") String gdrCode);
}
