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
    public String catchData(@WebParam(name = "startTime") String startTime
            , @WebParam(name = "endTime") String endTime
            , @WebParam(name = "type") String type
            , @WebParam(name = "page") Integer page
            , @WebParam(name = "pageSize") Integer pageSize);
}
