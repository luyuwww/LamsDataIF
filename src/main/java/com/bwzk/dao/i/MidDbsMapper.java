package com.bwzk.dao.i;

import com.bwzk.dao.BaseDao;
import com.bwzk.pojo.MidDbs;
import com.bwzk.pojo.MidDbsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MidDbsMapper extends BaseDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_MIDDB
     *
     * @mbggenerated
     */
    int countByExample(MidDbsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_MIDDB
     *
     * @mbggenerated
     */
    int deleteByExample(MidDbsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_MIDDB
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer did);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_MIDDB
     *
     * @mbggenerated
     */
    int insert(MidDbs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_MIDDB
     *
     * @mbggenerated
     */
    int insertSelective(MidDbs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_MIDDB
     *
     * @mbggenerated
     */
    List<MidDbs> selectByExample(MidDbsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_MIDDB
     *
     * @mbggenerated
     */
    MidDbs selectByPrimaryKey(Integer did);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_MIDDB
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") MidDbs record, @Param("example") MidDbsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_MIDDB
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") MidDbs record, @Param("example") MidDbsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_MIDDB
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MidDbs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_MIDDB
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MidDbs record);
}