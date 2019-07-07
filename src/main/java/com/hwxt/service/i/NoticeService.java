package com.hwxt.service.i;

import com.hwxt.pojo.SBacklog;

import java.util.List;

public interface NoticeService {
    /**
     * <p>Title: sendActivitiMsg</p>
     * <p>Description: 发送流程待办</p>
     *
     * @param userCodes
     * @param varsJson
     * @param actTaskID
     * @date 2014年6月19日
     */
    public void sendActivitiMsg(String userCodes, String varsJson, String actTaskID);

    /**
     * 得到个人待办
     *
     * @param usercode
     */
    public String toDoList(String usercode);

    /**
     * 测试 得到用户代办列表
     *
     * @param usercode
     */
    public List<SBacklog> allBacklog(String usercode);
}
