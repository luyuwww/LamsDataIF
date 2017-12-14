package com.bwzk.dao.i;

import com.bwzk.pojo.FlowDataItem;
import com.bwzk.pojo.FlowDataItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlowDataItemMapper {
    int countByExample(FlowDataItemExample example);

    int deleteByExample(FlowDataItemExample example);

    int deleteByPrimaryKey(Object id);

    int insert(FlowDataItem record);

    int insertSelective(FlowDataItem record);

    List<FlowDataItem> selectByExample(FlowDataItemExample example);

    FlowDataItem selectByPrimaryKey(Object id);

    int updateByExampleSelective(@Param("record") FlowDataItem record, @Param("example") FlowDataItemExample example);

    int updateByExample(@Param("record") FlowDataItem record, @Param("example") FlowDataItemExample example);

    int updateByPrimaryKeySelective(FlowDataItem record);

    int updateByPrimaryKey(FlowDataItem record);
}