package com.bwzk.page;


import ch.qos.logback.classic.Logger;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

/**
 * @Function: sqlserver分页方言 top
 */
public class SqlServerDialect{
	private Logger log =  (Logger) LoggerFactory.getLogger(this.getClass());
    public String getLimitString(String sql, int pager, int pageSize, String primaryKey , String orderBySubStr){
    	sql = sql.trim();
    	
    	StringBuffer sb = new StringBuffer();  
	//		select top pageSize * from table where id not in (select top first id from table) 	
    	sb.append("SELECT TOP ").append(pageSize).append(" A.* FROM ( ").append(sql);
		sb.append(" ) AS A WHERE A.").append(primaryKey).append(" NOT IN (SELECT TOP ");
		sb.append(pageSize * pager - pageSize).append(" B.").append(primaryKey);
		sb.append(" FROM ( ").append(sql).append(" ) AS B" );
		if(orderBySubStr!=null&&StringUtils.isNotEmpty(orderBySubStr.trim())){
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
		
//		//判断 orderBySubStr是否为空,如果为空就不使用order by 子句
//		if(orderBySubStr!=null&&StringUtils.isNotEmpty(orderBySubStr.trim()) ){
//			sb.append(" ORDER BY ").append(orderBySubStr);
//		}else{
//			if(primaryKey!=null&&!primaryKey.equals("")){
//				sb.append(" ORDER BY ").append(primaryKey);
//			}
//		}
		log.info("底层执行SQL语句为: "+sb.toString());
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
