package com.bwzk.service.i;

import com.bwzk.pojo.SUser;

import javax.jws.WebService;
import java.util.List;

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

    /**
     * 科研推送->档案抓取-纵向项目和横向项目
     * @return
     */
    public String keyanPushXyPrj();

    /**
     * 科研推送->档案抓取-无源项目的外协项目
     * @return
     */
    public String keyanPushNoPrj();

    /**
     * 档案推送->科研抓取-纵向和横向归档
     * @return
     */
    public String daPushXyPrj();

    /**
     * 档案推送->科研抓取-无源项目归档
     * @return
     */
    public String daPushNoPrj();

    public String syncAllData();
}
