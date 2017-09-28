package com.bwzk.service.i;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "GlobalSearch", targetNamespace = "http://service.unis.com/")
public interface GlobalSearchService {
    /**
     * <p>Title: 统一查询抓取数据接口</p>
     */
    @WebMethod
    @WebResult
    public String GetData(@WebParam(name = "startTime") String startTime
            , @WebParam(name = "endTime") String endTime
            , @WebParam(name = "type") String type
            , @WebParam(name = "page") Integer page
            , @WebParam(name = "pageSize") Integer pageSize);
    /**
     * <p>Title: 获取删除数据接口</p>
     */
    @WebMethod
    @WebResult
    public String getDelData(@WebParam(name = "type") String type
            , @WebParam(name = "startTime") String startTime
            , @WebParam(name = "endTime") String endTime);

    /**
     * <p>Title: 获取删除数据接口</p>
     */
    @WebMethod
    @WebResult
    public String getDelDataIgnoreType(@WebParam(name = "startTime") String startTime
            , @WebParam(name = "endTime") String endTime);

    @WebMethod
    @WebResult
    public String Permission();
}
