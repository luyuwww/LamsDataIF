package com.bwzk.dao.i.da;

import com.bwzk.dao.BaseDao4DA;
import com.bwzk.pojo.FlowMain;
import com.bwzk.pojo.FlowMainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlowMainMapper extends BaseDao4DA {
    int countByExample(FlowMainExample example);

    int deleteByExample(FlowMainExample example);

    int deleteByPrimaryKey(String id);

    int insert(FlowMain record);

    int insertSelective(FlowMain record);

    List<FlowMain> selectByExample(FlowMainExample example);

    FlowMain selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FlowMain record, @Param("example") FlowMainExample example);

    int updateByExample(@Param("record") FlowMain record, @Param("example") FlowMainExample example);

    int updateByPrimaryKeySelective(FlowMain record);

    int updateByPrimaryKey(FlowMain record);
}