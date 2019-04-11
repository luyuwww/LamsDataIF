package com.bwzk.dao;

import com.bwzk.pojo.EFile;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface JdbcDao<T> {
    /**
     * 用于执行任何SQL语句，一般用于执行DDL语句
     */
    public void excute(String sql) throws RuntimeException;

    /**
     * 用于执行更新SQL语句
     */
    public void update(String sql) throws RuntimeException;

    /**
     * 用于执行更新SQL语句
     */
    public void insert(String sql) throws RuntimeException;

    public String getLjfByLibcode(Integer libcode);

    /**
     * 为了版本更新得到一条 keyword和分类号 gid=keyword
     *
     * @return
     */
    public Map<String, Object> queryForMap(String sql);

    /**
     * 为了版本更新得到一条 keyword和分类号 gid=keyword
     *
     * @return
     */
    public List<Map<String, Object>> quertListMap(String sql);

    public List<String> quert4List(String sql);

    /**
     * 查新表2列 第一列是key第二列是value的一个map
     */
    public Map<String, String> quert2Colum4Map(String sql, String col1, String col2);

    /**
     * 查询为String
     */
    public String query4String(String sql);

    public Connection getConn();

    public Integer insertEfile(String tableName, EFile eFile);
    public T quert4Fx(String sql, Class<T> clazz);
}
