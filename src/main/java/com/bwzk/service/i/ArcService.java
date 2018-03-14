package com.bwzk.service.i;

import com.bwzk.pojo.SUser;

import javax.jws.WebService;
import java.util.List;

@WebService(name = "ArcDataWs", targetNamespace = "http://service.unis.com/")
public interface ArcService {
    /**
     * 数据接受服务
     *
     * @param xml
     */
    public String fileRecive(String xml);

    /**
     * 列出所有用户
     */
    public List<SUser> listAllUser();

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
}
