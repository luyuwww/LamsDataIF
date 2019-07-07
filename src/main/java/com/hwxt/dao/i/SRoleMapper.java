package com.hwxt.dao.i;

import com.hwxt.dao.BaseDao;
import com.hwxt.pojo.SRole;
import com.hwxt.pojo.SRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SRoleMapper extends BaseDao {
    int countByExample(SRoleExample example);

    int deleteByExample(SRoleExample example);

    int deleteByPrimaryKey(Integer did);

    int insert(SRole record);

    int insertSelective(SRole record);

    List<SRole> selectByExample(SRoleExample example);

    SRole selectByPrimaryKey(Integer did);

    int updateByExampleSelective(@Param("record") SRole record, @Param("example") SRoleExample example);

    int updateByExample(@Param("record") SRole record, @Param("example") SRoleExample example);

    int updateByPrimaryKeySelective(SRole record);

    int updateByPrimaryKey(SRole record);
}