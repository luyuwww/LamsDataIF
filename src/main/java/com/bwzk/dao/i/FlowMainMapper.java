package com.bwzk.dao.i;

import com.bwzk.pojo.FlowMain;
import com.bwzk.pojo.FlowMainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlowMainMapper {
    int countByExample(FlowMainExample example);

    int deleteByExample(FlowMainExample example);

    int deleteByPrimaryKey(Object id);

    int insert(FlowMain record);

    int insertSelective(FlowMain record);

    List<FlowMain> selectByExample(FlowMainExample example);

    FlowMain selectByPrimaryKey(Object id);

    int updateByExampleSelective(@Param("record") FlowMain record, @Param("example") FlowMainExample example);

    int updateByExample(@Param("record") FlowMain record, @Param("example") FlowMainExample example);

    int updateByPrimaryKeySelective(FlowMain record);

    int updateByPrimaryKey(FlowMain record);
}