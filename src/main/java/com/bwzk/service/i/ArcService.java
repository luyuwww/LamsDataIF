package com.bwzk.service.i;

import com.bwzk.pojo.SUser;

import javax.jws.WebService;
import java.util.List;
import java.util.Map;

@WebService(name = "ArcDataWs", targetNamespace = "http://service.unis.com/")
public interface ArcService {
    /**
     * 数据接受服务
     *
     * @param xml
     */
    public String fileRecive(String xml);

    /**
     * 列出档案库所有用户
     */
    public List<SUser> listAllUser();

    public String syncWLFWData();
    public String syncGWData();
    /**
     * 列出中间库
     */
    public List<Map<String , Object>> listZjkSWDList();

    /**
     * 得到bmid
     *
     * @param depCode 梦龙的部门code
     * @return BMID str
     */
    public String getBmidStrByDepCode(String depCode);

    /**
     * 得到bmid
     *
     * @param userCode
     */
    public String getBmidStrByUserCode(String userCode);

    public String getLamsIP();

    public String syncDclassfy(Integer libcode);

    public String syncOaData();
}
