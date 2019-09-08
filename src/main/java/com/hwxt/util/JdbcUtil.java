package com.hwxt.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.*;

public class JdbcUtil {
    public static void close(ResultSet rs, Statement st, Connection conn) {
        close(rs);
        close(st, conn);
    }

    public static void close(Statement st, Connection conn) {
        close(st);
        close(conn);
    }

    public static void close(ResultSet rs) {
        try {
            if (null != rs) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement st) {
        try {
           if(null != st){
               st.close();
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn) {
        try {
            if(null != conn){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(String url, String dbtype, String dbName, String USERNAME, String PASSWORD) {
        Connection conn = null;
        String dburl = getDBUrl(dbtype, url, dbName);
        String driverName = getDriverName(dbtype);
        if (StringUtils.isNotBlank(driverName) && StringUtils.isNotBlank(dburl)) {
            try {
                Class.forName(driverName);
                conn = DriverManager.getConnection(dburl, USERNAME, PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new RuntimeException("dbtype no null or url error!");
        }
        return conn;
    }


    public static String getDriverName(String dbType) {
        String driverClass = "";
        if (StringUtils.isNotBlank(dbType)) {
            dbType = dbType.toLowerCase();
            switch (dbType) {
                case "mysql":
                    driverClass = "com.mysql.jdbc.Driver";
                    break;
                case "mssql":
                    driverClass = "net.sourceforge.jtds.jdbc.Driver";
                    break;
                case "sqlserver":
                    driverClass = "net.sourceforge.jtds.jdbc.Driver";
                    break;
                case "oracle":
                    driverClass = "oracle.jdbc.driver.OracleDriver";
                    break;
                default:
                    driverClass = "oracle.jdbc.driver.OracleDriver";
                    break;
            }
        }
        return driverClass;
    }

    public static String getDBUrl(String dbType, String url, String dbName) {
        String returnUrl = "";
        if (StringUtils.isNotBlank(dbType)) {
            dbType = dbType.toLowerCase();
            switch (dbType) {
                case "mysql":
                    returnUrl = "jdbc:mysql://" + url + "/" + dbName + "?useUnicode=true&amp;characterEncoding=UTF-8";
                    break;
                case "mssql":
                    returnUrl = "jdbc:jtds:sqlserver://" + url + "/" + dbName + ";tds=8.0;lastupdatecount=true";
                    break;
                case "sqlserver":
                    returnUrl = "jdbc:jtds:sqlserver://" + url + "/" + dbName + ";tds=8.0;lastupdatecount=true";
                    break;
                case "oracle":
                    returnUrl = "jdbc:oracle:thin:@" + url + ":" + dbName;
                    break;
                default:
                    returnUrl = "jdbc:oracle:thin:@" + url + ":" + dbName;
                    break;
            }
        }
        return returnUrl;
    }
}