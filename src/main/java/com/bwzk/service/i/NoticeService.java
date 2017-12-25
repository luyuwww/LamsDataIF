package com.bwzk.service.i;

import com.bwzk.pojo.SBacklog;

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
     * 同步流程信息,并反向更新到lams
     */
    public void syncTaskInfo();
}
