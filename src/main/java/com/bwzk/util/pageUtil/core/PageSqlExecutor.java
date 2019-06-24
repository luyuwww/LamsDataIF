package com.bwzk.util.pageUtil.core;


import com.bwzk.util.pageUtil.dialect.i.Dialect4Ibatis;
import com.bwzk.util.pageUtil.dialect.impl.MYSqlDialect4Ibatis;
import com.bwzk.util.pageUtil.dialect.impl.OracleDialect4Ibatis;
import com.bwzk.util.pageUtil.dialect.impl.SQLServerDialect4Ibatis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class PageSqlExecutor {

	public PageSqlExecutor(Connection conn ) {
		try {
			DataBase_Type = conn.getMetaData().getDatabaseProductName();
		} catch (SQLException e) {
			new Exception(e.getMessage() , e);
		}
		if (DataBase_Type.equalsIgnoreCase(MICROSOFT_SQL_SERVER)) {
			dialect = sqlServerDialect;
		} else if (DataBase_Type.equalsIgnoreCase(ORACLE_DATABASE)) {
			dialect = oracleDialect;
		} else if (DataBase_Type.equalsIgnoreCase(MYSQL_DATABASE)) {
			dialect = mySqlDialect;
		}else if (DataBase_Type.equalsIgnoreCase(H2_DATABASE)){
			dialect = sqlServerDialect;
		}
	}

	public String getPageSql(String sql, int pager, int pageSize, String primaryKey, String orderBySubStr){

		if(supportsLimit()){
			sql = dialect.getLimitString(sql, pager, pageSize, primaryKey, orderBySubStr);
		}
		System.out.println(sql);
		return sql;
	}

	public String getTotalSql(String sql ){
		sql = "SELECT COUNT(1) FROM ("+sql+") T";
		return sql;
	}


	/**
	 *  默认主键.主要为sqlserver数据库和档案系统考虑.因为本系统大多是使用did为主键.其他数据库不用考虑
	 */
	public static final String DEFAULT_PRIMARYKEY = "DID";

	/**
	 * @author: www
	 * @return
	 *
	 * @function: 判断是否支持分页,如果没有方言 就 不允许
	 */
	public boolean supportsLimit() {
		if (enableLimit && dialect != null) {
			return dialect.supportsLimit();
		}
		return false;
	}

	//日志
	public static final Logger logger = LoggerFactory.getLogger(PageSqlExecutor.class);
	//sqlserver方言 从connect..getMetaData().getDatabaseProductName()来获得
	public static final String MICROSOFT_SQL_SERVER = "Microsoft SQL Server";
	//oracle方言 从connect..getMetaData().getDatabaseProductName()来获得
	public static final String ORACLE_DATABASE = "Oracle";
	//mysql方言 从connect..getMetaData().getDatabaseProductName()来获得
	public static final String MYSQL_DATABASE = "MySQL";
	public static final String H2_DATABASE = "H2";


	private String DataBase_Type = ""; //方便得到数据库类类型 
	private Dialect4Ibatis mySqlDialect = new MYSqlDialect4Ibatis();
	private Dialect4Ibatis oracleDialect = new OracleDialect4Ibatis();
	private Dialect4Ibatis sqlServerDialect = new SQLServerDialect4Ibatis();
	private Dialect4Ibatis dialect;

	/**
	 * 默认允许分页
	 */
	private boolean enableLimit = true;

	public boolean isEnableLimit() {
		return enableLimit;
	}

	public void setEnableLimit(boolean enableLimit) {
		this.enableLimit = enableLimit;
	}

	public void setMySqlDialect(Dialect4Ibatis mySqlDialect) {
		this.mySqlDialect = mySqlDialect;
	}

	public void setOracleDialect(Dialect4Ibatis oracleDialect) {
		this.oracleDialect = oracleDialect;
	}

	public void setSqlServerDialect(Dialect4Ibatis sqlServerDialect) {
		this.sqlServerDialect = sqlServerDialect;
	}

}
