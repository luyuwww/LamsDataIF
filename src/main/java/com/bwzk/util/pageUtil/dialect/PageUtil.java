package com.bwzk.util.pageUtil.dialect;

/**
 * @author: www
 * @date: Sep 22, 2008
 *  
 * @Function: 
 */
public class PageUtil {
	 public static final String SQL_END_DELIMITER = ";";  
	 public static final String SQL_ORDER_BY_SUBSTRING = "orderBy";  
	 public static final String SQL_PRIMARY_KEY = "primaryKey";  
	 public static final int DEFAULT_PAGE_SIZE = 10;
	 
	 /**
	 *  所有记录条目数 为了好判断.第一次为 -1
	 */
	public static final int DEFAULT_FIRST_GET_ALL_RECODER_NUM = 10;
	 
	 /**
	 * @author: www
	 * @param sql 语句
	 * @return  sql 语句
	 * @function: 去除sql语句最后的 ";" 号 和两边的空格
	 */
	public static String trim(String sql) {  
	        sql = sql.trim();  
	        if (sql.endsWith(SQL_END_DELIMITER)) {  
	            sql = sql.substring(0, sql.length() - 1  
	                    - SQL_END_DELIMITER.length());  
	        }  
	        return sql; //返回时将所有sql语句转换为大写 
	}
	
	
}
