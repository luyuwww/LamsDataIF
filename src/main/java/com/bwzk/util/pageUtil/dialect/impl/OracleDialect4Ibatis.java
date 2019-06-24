package com.bwzk.util.pageUtil.dialect.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.util.pageUtil.dialect.PageUtil;
import com.bwzk.util.pageUtil.dialect.i.Dialect4Ibatis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

/**
 * @author: www
 * @date: Sep 9, 2008
 *  
 * @Function: oracle分页方言 rownum
 */
public class OracleDialect4Ibatis implements Dialect4Ibatis {
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

         //判断 orderBySubStr是否为空,如果为空就不使用order by 子句
         if(StringUtils.isNotEmpty(orderBySubStr)){
//        	 sql+= sb.append(" ORDER BY ").append(orderBySubStr.trim());
        	 sql += " ORDER BY " + orderBySubStr.trim();
         }
         sb.append("SELECT * FROM ( SELECT ROW_.*, ROWNUM ROWNUM_ FROM (").append(sql);
         sb.append(" ) ROW_ ) WHERE ROWNUM_ <= ").append(pageSize*pager).append(" AND ROWNUM_ >");
         sb.append(pageSize*pager-pageSize);
         
//         log.info("底层执行SQL语句为: "+sb.toString());
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
