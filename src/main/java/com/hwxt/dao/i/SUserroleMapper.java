package com.hwxt.dao.i;

import com.hwxt.dao.BaseDao;
import com.hwxt.pojo.SUserrole;
import com.hwxt.pojo.SUserroleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SUserroleMapper extends BaseDao {
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