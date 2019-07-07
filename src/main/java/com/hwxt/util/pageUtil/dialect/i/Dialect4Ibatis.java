package com.hwxt.util.pageUtil.dialect.i;

/**
 * @author: www
 * @date: Sep 9, 2008
 *  
 * @Function: 针对不同数据库的 分页方言
 */
public interface Dialect4Ibatis {
	
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
    public String getLimitString(String sql, int pager, int pageSize, String primaryKey, String orderBySubStr);
    
    /**
     * @author: www
     * @return
     *
     * @function: 判断是否需要分页使用
     */
    public boolean supportsLimit();  
}
