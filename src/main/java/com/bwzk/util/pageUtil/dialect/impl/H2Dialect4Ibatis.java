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
 * @Function: sqlserver分页方言 top
 */
public class H2Dialect4Ibatis implements Dialect4Ibatis {
	private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
	 /**
     * @author: www
     * @param sql
     * @param pager 目标页面
     * @param pageSize 页面大小
     * @param primaryKey 主键.主要利用于H2
     * @param orderBySubStr orderby 子句
     * @return String 拼凑好的分页语句
     *
     * @function: 拼凑分页语句 传入核心业务sql后会加上分页
     */
    public String getLimitString(String sql, int pager, int pageSize, String primaryKey , String orderBySubStr){
    	sql = PageUtil.trim(sql);
    	
    	StringBuffer sb = new StringBuffer();  

    	sb.append("SELECT TOP ").append(pageSize).append(" A.* FROM ( ").append(sql);
		sb.append(" ) AS A WHERE A.").append(primaryKey).append(" NOT IN (SELECT TOP ");
		sb.append(pageSize * pager - pageSize).append(" B.").append(primaryKey);
		sb.append(" FROM ( ").append(sql).append(" ) AS B" );
		if(orderBySubStr!=null&& StringUtils.isNotEmpty(orderBySubStr.trim())){
			sb.append(" ORDER BY");
			String[] orderStr = StringUtils.split(orderBySubStr, ",");
			for (int i=0;i<orderStr.length;i++) {
				String str = orderStr[i];
				sb.append(" B.").append(str);
				if(i<orderStr.length-1){
					sb.append(",");
				}
			}
		}else{
			if(primaryKey!=null&&!primaryKey.equals("")){
				sb.append(" ORDER BY");
				sb.append(" B.").append(primaryKey);
			}
		}
		sb.append(")");
		
		//判断 orderBySubStr是否为空,如果为空就不使用order by 子句
		if(orderBySubStr!=null&& StringUtils.isNotEmpty(orderBySubStr.trim()) ){
			sb.append(" ORDER BY ").append(orderBySubStr);
		}else{
			if(primaryKey!=null&&!primaryKey.equals("")){
				sb.append(" ORDER BY ").append(primaryKey);
			}
		}
//		log.info("底层执行SQL语句为: "+sb.toString());
		return sb.toString();
	}

    /**
     * @author: www
     * @return
     *
     * @function: 判断是否需要分页使用
     */
    public boolean supportsLimit(){
    	   return true;  
    }  
}
