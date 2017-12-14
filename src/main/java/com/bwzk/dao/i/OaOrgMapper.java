package com.bwzk.dao.i;

import com.bwzk.pojo.OaOrg;
import com.bwzk.pojo.OaOrgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OaOrgMapper {
    int countByExample(OaOrgExample example);

    int deleteByExample(OaOrgExample example);

    int insert(OaOrg record);

    int insertSelective(OaOrg record);

    List<OaOrg> selectByExample(OaOrgExample example);

    int updateByExampleSelective(@Param("record") OaOrg record, @Param("example") OaOrgExample example);

    int updateByExample(@Param("record") OaOrg record, @Param("example") OaOrgExample example);
}