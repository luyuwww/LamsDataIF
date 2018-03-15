package com.bwzk.service.i;

import com.bwzk.pojo.OaUser;
import com.bwzk.pojo.SUser;

import java.util.List;

public interface OrgService {
    /**
     * 同步用户和部门
     */
    public String syncUserGroup();
    /**
     * 列出所有用户
     */
    public List<SUser> listDaUsers();

    /**
     * 列出所有用户
     */
    public List<OaUser> listOaUsers();

    /**
     * 列出所有用户
     */
    public List<OaUser> listOaUsersLess4();


}
