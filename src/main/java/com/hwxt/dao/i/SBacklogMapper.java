package com.hwxt.dao.i;

import com.hwxt.dao.BaseDao;
import com.hwxt.pojo.SBacklog;
import com.hwxt.pojo.SBacklogExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SBacklogMapper extends BaseDao {
    int countByExample(SBacklogExample example);

    int deleteByExample(SBacklogExample example);

    int deleteByPrimaryKey(Integer did);

    int insert(SBacklog record);

    int insertSelective(SBacklog record);

    List<SBacklog> selectByExample(SBacklogExample example);

    SBacklog selectByPrimaryKey(Integer did);

    int updateByExampleSelective(@Param("record") SBacklog record, @Param("example") SBacklogExample example);

    int updateByExample(@Param("record") SBacklog record, @Param("example") SBacklogExample example);

    int updateByPrimaryKeySelective(SBacklog record);

    int updateByPrimaryKey(SBacklog record);

    @Select("SELECT COUNT(*) FROM S_BACKLOG WHERE ${whereSql}")
    Integer getBackLogNum(@Param("whereSql") String whereSql);
}