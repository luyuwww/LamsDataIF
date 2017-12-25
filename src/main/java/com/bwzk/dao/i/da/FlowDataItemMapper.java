package com.bwzk.dao.i.da;

import com.bwzk.dao.BaseDao4DA;
import com.bwzk.pojo.FlowDataItem;
import com.bwzk.pojo.FlowDataItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlowDataItemMapper extends BaseDao4DA {
        int countByExample(FlowDataItemExample example);

    int deleteByExample(FlowDataItemExample example);

    int deleteByPrimaryKey(String id);

    int insert(FlowDataItem record);

    int insertSelective(FlowDataItem record);

    List<FlowDataItem> selectByExample(FlowDataItemExample example);

    FlowDataItem selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FlowDataItem record, @Param("example") FlowDataItemExample example);

    int updateByExample(@Param("record") FlowDataItem record, @Param("example") FlowDataItemExample example);

    int updateByPrimaryKeySelective(FlowDataItem record);

    int updateByPrimaryKey(FlowDataItem record);
}