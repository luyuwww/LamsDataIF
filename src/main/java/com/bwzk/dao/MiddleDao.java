package com.bwzk.dao;

import com.bwzk.util.JdbcUtil;
import com.bwzk.util.pageUtil.core.PageSqlExecutor;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 * @UPDATE 2019-06-25-0:38
 * @since 2019-06-25-0:38
 */
@Component("middleDao")
public class MiddleDao {
    public List<Map<String, Object>> pageList(String url, String dbType, String dbName
            , String username, String password   , String sql , int pager, int pageSize
            , String primaryKey, String orderBySubStr) {
        ResultSet rs = null;
        Statement st = null;
        Connection conn = null;
        List list = new ArrayList();
        try {
            conn = JdbcUtil.getConnection(url , dbType , dbName , username, password);
            PageSqlExecutor p = new PageSqlExecutor(conn);
            sql = p.getPageSql(sql, pager, pageSize, primaryKey, orderBySubStr);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {//对结果集进行遍历
                ResultSetMetaData md = rs.getMetaData();
                int columnCount = md.getColumnCount();
                Map<String,Object> rowData = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i) , rs.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(rs,st,conn);
        }
        return list;
    }

    public Integer execUpdateSql(String url, String dbType, String dbName
            , String username, String password , String updateSql){
        Integer rslt = 0;
        Statement st = null;
        Connection conn = null;
        List list = new ArrayList();
        try {
            conn = JdbcUtil.getConnection(url , dbType , dbName , username, password);
            st = conn.createStatement();
            rslt = st.executeUpdate(updateSql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(st,conn);
        }
        return rslt;
    }

    public Boolean testConnection(String url, String dbType, String dbName
            , String username, String password){
        Boolean rslt = Boolean.FALSE;
        Connection conn = null;
        List list = new ArrayList();
        try {
            conn = JdbcUtil.getConnection(url , dbType , dbName , username, password);
            if(null != conn){
                rslt = Boolean.TRUE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn);
        }
        return rslt;
    }

    private Connection getConn(String url, String username, String password, String dirvername) {
        Connection conn = null;
        try {
            Class.forName(dirvername);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {

            e.printStackTrace();

        }
        return conn;
    }
}
