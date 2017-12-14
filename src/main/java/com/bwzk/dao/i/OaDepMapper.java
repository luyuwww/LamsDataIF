package com.bwzk.dao.i;

import com.bwzk.pojo.OaDep;
import com.bwzk.pojo.OaDepExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OaDepMapper {
    int countByExample(OaDepExample example);

    int deleteByExample(OaDepExample example);

    int insert(OaDep record);

    int insertSelective(OaDep record);

    List<OaDep> selectByExample(OaDepExample example);

    int updateByExampleSelective(@Param("record") OaDep record, @Param("example") OaDepExample example);

    int updateByExample(@Param("record") OaDep record, @Param("example") OaDepExample example);
}