package com.bwzk.dao.i.da;

import com.bwzk.dao.BaseDao4DA;
import com.bwzk.pojo.SUserrole;
import com.bwzk.pojo.SUserroleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SUserroleMapper extends BaseDao4DA {
    int countByExample(SUserroleExample example);

    int deleteByExample(SUserroleExample example);

    int deleteByPrimaryKey(Integer did);

    int insert(SUserrole record);

    int insertSelective(SUserrole record);

    List<SUserrole> selectByExample(SUserroleExample example);

    SUserrole selectByPrimaryKey(Integer did);

    int updateByExampleSelective(@Param("record") SUserrole record, @Param("example") SUserroleExample example);

    int updateByExample(@Param("record") SUserrole record, @Param("example") SUserroleExample example);

    int updateByPrimaryKeySelective(SUserrole record);

    int updateByPrimaryKey(SUserrole record);
}