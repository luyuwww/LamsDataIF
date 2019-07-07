package com.hwxt.service.i;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "BaseDataWS", targetNamespace = "http://service.lams.cn/")
public interface BaseDataService {
    /**
     * <p>
     * Title: 添加用户数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataTxt
     * @param deptPk
     * @param primaryKey
     * @return
     */
    public String addUserByTxt(@WebParam(name = "dataTxt") String dataTxt,
                               @WebParam(name = "deptPk") String deptPk,
                               @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 修改用户数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataTxt
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String updateUserByTxt(@WebParam(name = "dataTxt") String dataTxt,
                                  @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加部门数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataTxt
     * @param primaryKey
     * @param orgPk
     * @param parentPk
     * @return
     */
    @WebMethod
    public String addDeptByTxt(@WebParam(name = "dataTxt") String dataTxt,
                               @WebParam(name = "primaryKey") String primaryKey,
                               @WebParam(name = "orgPk") String orgPk,
                               @WebParam(name = "parentPk") String parentPk);

    /**
     * <p>
     * Title: 修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataTxt
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String updateDeptByTxt(@WebParam(name = "dataTxt") String dataTxt,
                                  @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加用户数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataXml
     * @param deptPk
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String addUserByXml(@WebParam(name = "dataXml") String dataXml,
                               @WebParam(name = "deptPk") String deptPk,
                               @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 更新用户数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataXml
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String updateUserByXml(@WebParam(name = "dataXml") String dataXml,
                                  @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加部门数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataXml
     * @param primaryKey
     * @param orgPk
     * @param parentPk
     * @return
     */
    @WebMethod
    public String addDeptByXml(@WebParam(name = "dataXml") String dataXml,
                               @WebParam(name = "primaryKey") String primaryKey,
                               @WebParam(name = "orgPk") String orgPk,
                               @WebParam(name = "parentPk") String parentPk);

    /**
     * <p>
     * Title: 修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataXml
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String updateDeptByXml(@WebParam(name = "dataXml") String dataXml,
                                  @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加用户数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataJson
     * @param deptPk
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String addUserByJson(@WebParam(name = "dataJson") String dataJson,
                                @WebParam(name = "deptPk") String deptPk,
                                @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 修改用户数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataJson
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String updateUserByJson(
            @WebParam(name = "dataJson") String dataJson,
            @WebParam(name = "primaryKey") String primaryKey,
            @WebParam(name = "deptPk") String deptPk);

    /**
     * <p>
     * Title: 增加部门数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataJson
     * @param primaryKey
     * @param orgPk
     * @param parentPk
     * @return
     */
    @WebMethod
    public String addDeptByJson(@WebParam(name = "dataJson") String dataJson,
                                @WebParam(name = "primaryKey") String primaryKey,
                                @WebParam(name = "orgPk") String orgPk,
                                @WebParam(name = "parentPk") String parentPk);

    /**
     * <p>
     * Title: 修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataJson
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String updateDeptByJson(
            @WebParam(name = "dataJson") String dataJson,
            @WebParam(name = "primaryKey") String primaryKey);

    /**
     * Title: 增加或修改用户数据 返回; 0：成功！ 1：不成功！[失败原因]
     *
     * @param dataTxt
     * @param deptPk
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String addOrUpdateUserByTxt(
            @WebParam(name = "dataTxt") String dataTxt,
            @WebParam(name = "deptPk") String deptPk,
            @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加或修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataTxt
     * @param primaryKey
     * @param orgPk
     * @param parentPk
     * @return
     */
    @WebMethod
    public String addOrUpdateDeptByTxt(
            @WebParam(name = "dataTxt") String dataTxt,
            @WebParam(name = "primaryKey") String primaryKey,
            @WebParam(name = "orgPk") String orgPk,
            @WebParam(name = "parentPk") String parentPk);

    /**
     * <p>
     * Title: 增加或修改用户数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataXml
     * @param deptPk
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String addOrUpdateUserByXml(
            @WebParam(name = "dataXml") String dataXml,
            @WebParam(name = "deptPk") String deptPk,
            @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加或修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataXml
     * @param primaryKey
     * @param orgPk
     * @param parentPk
     * @return
     */
    @WebMethod
    public String addOrUpdateDeptByXml(
            @WebParam(name = "dataXml") String dataXml,
            @WebParam(name = "primaryKey") String primaryKey,
            @WebParam(name = "orgPk") String orgPk,
            @WebParam(name = "parentPk") String parentPk);

    /**
     * <p>
     * Title: 增加或修改用户数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataJson
     * @param deptPk
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String addOrUpdateUserByJson(
            @WebParam(name = "dataJson") String dataJson,
            @WebParam(name = "deptPk") String deptPk,
            @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加或修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataJson
     * @param primaryKey
     * @param orgPk
     * @param parentPk
     * @return
     */
    @WebMethod
    public String addOrUpdateDeptByJson(
            @WebParam(name = "dataJson") String dataJson,
            @WebParam(name = "primaryKey") String primaryKey,
            @WebParam(name = "orgPk") String orgPk,
            @WebParam(name = "parentPk") String parentPk);

    /**
     * <p>
     * Title: 增加或修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataTxt
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String addOrUpdateQzhByTxt(
            @WebParam(name = "dataTxt") String dataTxt,
            @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加或修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataXml
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String addOrUpdateQzhByXml(
            @WebParam(name = "dataXml") String dataXml,
            @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加或修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataJson
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String addOrUpdateQzhByJson(
            @WebParam(name = "dataJson") String dataJson,
            @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataTxt
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String addQzhByTxt(@WebParam(name = "dataTxt") String dataTxt,
                              @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataTxt
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String updateQzhByTxt(@WebParam(name = "dataTxt") String dataTxt,
                                 @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataXml
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String addQzhByXml(@WebParam(name = "dataXml") String dataXml,
                              @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataXml
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String updateQzhByXml(@WebParam(name = "dataXml") String dataXml,
                                 @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 增加全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataJson
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String addQzhByJson(@WebParam(name = "dataJson") String dataJson,
                               @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param dataJson
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String updateQzhByJson(@WebParam(name = "dataJson") String dataJson,
                                  @WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 删除用户数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String delUserByKey(@WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 删除部门数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param primaryKey
     * @return
     */

    @WebMethod
    public String delDeptByKey(@WebParam(name = "primaryKey") String primaryKey);

    /**
     * <p>
     * Title: 删除全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
     * </p>
     *
     * @param primaryKey
     * @return
     */
    @WebMethod
    public String delQzhByKey(@WebParam(name = "primaryKey") String primaryKey);

}
