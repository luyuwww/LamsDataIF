package com.hwxt.util.pageUtil.dialect.impl;

import ch.qos.logback.classic.Logger;
import com.hwxt.util.pageUtil.dialect.PageUtil;
import com.hwxt.util.pageUtil.dialect.i.Dialect4Ibatis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

/**
 * @author: www
 * @date: Sep 9, 2008
 *  
 * @Function: mysql分页方言 limit
 */
public class MYSqlDialect4Ibatis implements Dialect4Ibatis {
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
	 /**
     * @author: www
     * @param sql
     * @param pager 目标页面
     * @param pageSize 页面大小
     * @param primaryKey 主键.主要利用于sqlserver
     * @param orderBySubStr orderby 子句
     * @return String 拼凑好的分页语句
     *
     * @function: 拼凑分页语句 传入核心业务sql后会加上分页
     */
    public String getLimitString(String sql, int pager, int pageSize, String primaryKey , String orderBySubStr){
    	 sql = PageUtil.trim(sql);
         StringBuffer sb = new StringBuffer(); 
         
         sb.append(sql);  
         //判断 orderBySubStr是否为空,如果为空就不使用order by 子句
         if(orderBySubStr!=null&& StringUtils.isNotEmpty(orderBySubStr)){
        	 sb.append(" ORDER BY ").append(orderBySubStr.trim());
         }
         if (pager > 0) {  
             sb.append(" limit ").append(pager*pageSize-pageSize).append(',').append(pageSize);
         } else {  
             sb.append(" limit ").append(pageSize);;  
         }  
         
//         log.info("底层执行SQL语句为: "+sb.toString());
//         System.out.println("底层执行SQL语句为: "+sb.toString());
         return sb.toString();  
	}

    /**
     * @author: www
     * @function: 判断是否需要分页使用
     */
    public boolean supportsLimit(){
    	   return true;  
    }  
}
