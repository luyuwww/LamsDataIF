package com.bwzk.dao.i.oa;

import com.bwzk.dao.BaseDao4OA;
import com.bwzk.pojo.OaUser;
import com.bwzk.pojo.OaUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OaUserMapper extends BaseDao4OA {
    int countByExample(OaUserExample example);

    int deleteByExample(OaUserExample example);

    int insert(OaUser record);

    int insertSelective(OaUser record);

    List<OaUser> selectByExample(OaUserExample example);

    int updateByExampleSelective(@Param("record") OaUser record, @Param("example") OaUserExample example);

    int updateByExample(@Param("record") OaUser record, @Param("example") OaUserExample example);
}