package com.hwxt.service;

import ch.qos.logback.classic.Logger;
import com.hwxt.dao.JdbcDao;
import com.hwxt.dao.i.SGroupMapper;
import com.hwxt.dao.i.SQzhMapper;
import com.hwxt.dao.i.SUserMapper;
import com.hwxt.dao.i.SUserroleMapper;
import com.hwxt.pojo.*;
import com.hwxt.util.CommonUtil;
import com.hwxt.util.DateUtil;
import com.hwxt.util.ExceptionThrows;
import com.hwxt.util.GlobalFinalAttr.DatabaseType;
import com.hwxt.util.IsExistDepOrUser;
import com.caucho.hessian.client.HessianProxyFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class BaseService {
    /**
     * 得到数据库信息 databaseType 和 databaseTime
     */
    protected Map<String, Object> getDBInfo() throws RuntimeSqlException {
        Date dataTime = null;
        Map<String, Object> infos = new LinkedHashMap<String, Object>();
        TimeZone.setDefault(TimeZone.getTimeZone("ETC/GMT-8")); // 设置时区 中国/北京/香港
        String typeStr = getDBTyeStr();
        if (StringUtils.isNotEmpty(typeStr)) {
            if (typeStr != null && typeStr.equals("Microsoft SQL Server")) {
                dataTime = sUserMapper.selectDateTimeForMSSQL();
            } else if (typeStr != null && typeStr.equals("Oracle")) {
                dataTime = sUserMapper.selectDateTimeForOra();
            } else if (typeStr != null && typeStr.equals("Db2")) {
                dataTime = sUserMapper.selectDateTimeForDB2();
            } else if (typeStr != null && typeStr.equals("MySQL")) {
                dataTime = sUserMapper.selectDateTimeForMySQL();
            } else if (typeStr != null && typeStr.equals("H2")) {
                dataTime = sUserMapper.selectDateTimeForH2();
            } else {
                dataTime = new Date();
                log.error("DB Type not funder!");
            }
        } else {
            dataTime = new Date();
            log.error("get database time is error!");
        }
        infos.put("databaseType", typeStr);
        infos.put("databaseTime", dataTime);
        return infos;
    }

    protected String generateTimeToSQLDate(Object date) {
        String datevalue = null;
        String typeStr = getDBTyeStr();
        TimeZone.setDefault(TimeZone.getTimeZone("ETC/GMT-8")); // 设置时区 中国/北京/香港
        if (date instanceof Date) {
            datevalue = DateUtil.getDateTimeFormat().format(date);
        } else if (date instanceof String) {
            datevalue = (String) date;
        }
        if (StringUtils.isNotEmpty(typeStr)) {
            if (typeStr != null && typeStr.equals("Microsoft SQL Server")) {
                datevalue = "cast('" + datevalue + "' as datetime)";
            } else if (typeStr != null && typeStr.equals("Oracle")) {
                if (datevalue.indexOf(".") > -1) {// 防止出现 2056-12-25 00:00:00.0
                    // 而无法导入
                    datevalue = datevalue.substring(0,
                            datevalue.lastIndexOf("."));
                }
                datevalue = "TO_DATE('" + datevalue
                        + "', 'yyyy-MM-dd HH24:mi:ss')";
            } else if (typeStr != null && typeStr.equals("Db2")) {
                datevalue = "TIMESTAMP('" + datevalue + "' )";
            } else if (typeStr != null && typeStr.equals("MySQL")) {
                datevalue = "DATE_FORMAT('" + datevalue
                        + "', '%Y-%m-%d %H:%i:%s')";
            } else if (typeStr != null && typeStr.equals("H2")) {
                datevalue = "PARSEDATETIME('" + datevalue
                        + "'，'dd-MM-yyyy hh:mm:ss.SS' )";
            } else {
                datevalue = "";
                log.error("DB Type not funder!");
            }
        } else {
            datevalue = "";
            log.error("get database time is error!");
        }
        return datevalue;
    }

    /**
     * 得到数据库的时间 如果错误返回new的时间
     */
    protected Date getDBDateTime() throws RuntimeSqlException {
        Date dbDate = null;
        TimeZone.setDefault(TimeZone.getTimeZone("ETC/GMT-8")); // 设置时区 中国/北京/香港
        String typeStr = getDBTyeStr();
        if (StringUtils.isNotEmpty(typeStr)) {
            if (typeStr.equals("Microsoft SQL Server")) {
                dbDate = sUserMapper.selectDateTimeForMSSQL();
            } else if (typeStr.equals("Oracle")) {
                dbDate = sUserMapper.selectDateTimeForOra();
            } else if (typeStr.equals("Db2")) {
                dbDate = sUserMapper.selectDateTimeForDB2();
            } else if (typeStr.equals("MySQL")) {
                dbDate = sUserMapper.selectDateTimeForMySQL();
            } else if (typeStr.equals("H2")) {
                dbDate = sUserMapper.selectDateTimeForH2();
            } else {
                dbDate = new Date();
                log.error("DB is no look!");
            }
        } else {
            dbDate = new Date();
            log.error("get database time is error!");
        }
        return dbDate;
    }

    /**
     * 得到数据库的类型str
     */
    protected String getDBTyeStr() throws RuntimeSqlException {
        String typeStr = null;
        TimeZone.setDefault(TimeZone.getTimeZone("ETC/GMT-8")); // 设置时区 中国/北京/香港
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        try {
            conn = jdbcDao.getConn();
            dbmd = conn.getMetaData();
            typeStr = dbmd.getDatabaseProductName();
        } catch (Exception e) {
            log.error("get database type is error!", e);
        } finally {
            try {
                dbmd = null;
                conn.close();
            } catch (SQLException exx) {
                log.error(exx.getMessage());
            }
        }
        return typeStr;
    }

    /**
     * 得到数据库类型的 DatabaseType
     */
    protected DatabaseType getDatabaseType() {
        DatabaseType databaseType = null;
        try {
            databaseType = DatabaseType.getDatabaseType(getDBTyeStr());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return databaseType;
    }

    /**
     * 根据表名判断数据表是否存在
     */
    protected Boolean existTable(String tablename) {
        boolean result = false;
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        ResultSet rs = null;
        try {
            conn = jdbcDao.getConn();
            dbmd = conn.getMetaData();
            String schemaName = getSchemaName(dbmd);
            rs = dbmd.getTables(null, schemaName, tablename,
                    new String[]{"TABLE"});
            if (rs.next()) {
                result = true;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                dbmd = null;
                rs.close();
                conn.close();
            } catch (SQLException e) {
                log.error("获取ConnectionMetaData关闭链接错误!");
            }
        }
        return result;
    }

    /**
     * 判断表的字段是否存在
     */
    protected boolean existColumn(String tablename, String columnName) {
        return existColumnOrIndex(tablename, columnName, true);
    }

    /**
     * 判断字段的索引是否存在
     */
    protected boolean existIndex(String tablename, String indexName) {

        return existColumnOrIndex(tablename, indexName, false);
    }

    protected Map<String, Object> queryForMap(String sql) {
        return jdbcDao.queryForMap(sql);
    }

    protected List<Map<String, Object>> quertListMap(String sql) {
        return jdbcDao.quertListMap(sql);
    }

    protected String queryForString(String sql) {
        return jdbcDao.query4String(sql);
    }

    /**
     * 查新表2列 第一列是key第二列是value的一个map
     */
    protected Map<String, String> quert2Colum4Map(String sql, String col1,
                                                  String col2) {
        return jdbcDao.quert2Colum4Map(sql, col1, col2);
    }

    protected Integer queryPid(String sql){
        return jdbcDao.queryPid(sql);
    }

    /**
     * 判断表的字段或者索引是否存在
     *
     * @param tablename         表名
     * @param columnOrIndexName 字段名, 或者索引名
     * @param isColumn          true字段 false索引
     * @return boolean true存在 false 不存在
     */
    protected boolean existColumnOrIndex(String tablename,
                                         String columnOrIndexName, boolean isColumn) {
        boolean result = false;
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        ResultSet rs = null;
        try {
            conn = jdbcDao.getConn();
            dbmd = conn.getMetaData();
            String schemaName = getSchemaName(dbmd);
            if (isColumn) {
                rs = dbmd.getColumns(null, schemaName, tablename,
                        columnOrIndexName);
                if (rs.next()) {
                    result = true;
                }
            } else {
                rs = dbmd.getIndexInfo(null, schemaName, tablename, false,
                        false);
                while (rs.next()) {
                    String indexName = rs.getString(6);
                    if (indexName != null
                            && indexName.equals(columnOrIndexName)) {
                        result = true;
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                dbmd = null;
                rs.close();
                conn.close();
            } catch (SQLException e) {
                log.error("获取ConnectionMetaData关闭链接错误!");
            }
        }
        return result;
    }

    /**
     * 根据表字段是否可以为空
     */
    protected boolean validateColumnIsNULL(String tablename, String columnName) {
        boolean result = false;
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        ResultSet rs = null;
        try {
            conn = jdbcDao.getConn();
            dbmd = conn.getMetaData();
            String schemaName = getSchemaName(dbmd);
            rs = dbmd.getColumns(null, schemaName, tablename, columnName);
            if (rs.next()) {
                String notnull = rs.getString(11);
                result = notnull != null && notnull.equals("1");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                dbmd = null;
                rs.close();
                conn.close();
            } catch (SQLException e) {
                log.error("获取ConnectionMetaData关闭链接错误!");
            }
        }
        return result;
    }

    /**
     * 执行sql文件
     */
    protected boolean runScript(Reader reader) {
        boolean result = false;
        Connection conn = null;
        try {
            conn = jdbcDao.getConn();
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setErrorLogWriter(null);
            runner.setLogWriter(null);
            runner.runScript(reader);
            result = true;
        } catch (Exception ex) {
            log.error(ex.getMessage() + "执行sql文件错误", ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error(e.getMessage() + "获取ConnectionMetaData关闭链接错误!", e);
            }
        }
        return result;
    }

    /**
     * 获取表模式 private
     */
    private String getSchemaName(DatabaseMetaData dbmd) throws SQLException {
        String schemaName;
        switch (getDatabaseType().getValue()) {
            case 1:// mssql
                schemaName = sqlserverSchemaName;
                break;
            case 4:// h2
                schemaName = null;
                break;
            default:
                schemaName = dbmd.getUserName();
                break;
        }
        return schemaName;
    }

    protected void execSql(String sql) {
        jdbcDao.excute(sql);
    }

    /**
     * 通过groupdid得到bmid
     *
     * @author: LuYu
     */
    protected String getBmidByDepCode(String depCode) {
        StringBuffer bmid = new StringBuffer();
        SGroup firstGroup = this.getGroupByDepCode(depCode);
        try {
            List<SGroup> groupList = this.getGroupList(firstGroup.getDid(),
                    null);
            bmid.append(groupList.get(0).getQzh());
            Collections.reverse(groupList);
            for (SGroup group : groupList) {
                bmid.append("_").append(group.getDid());
            }
        } catch (Exception e) {
            bmid.append("");
            log.error("getUserByUserCode类在通过groupDID得到group的时候的时错误,在 getGroupByDid is "
                    + e.getMessage());
        }
        return bmid.toString();
    }

    /**
     * 通过groupdid得到bmid
     *
     * @author: LuYu
     */
    protected String getBmidByuserCode(String usercode) {
        StringBuffer bmid = new StringBuffer();
        try {
            Integer groupDid = sUserMapper.getUserByUsercode(usercode).getPid();
            List<SGroup> groupList = this.getGroupList(groupDid, null);
            bmid.append(groupList.get(0).getQzh());
            Collections.reverse(groupList);
            for (SGroup group : groupList) {
                bmid.append("_").append(group.getDid());
            }
        } catch (Exception e) {
            bmid.append("");
            log.error("getUserByUserCode类在通过groupDID得到group的时候的时错误,在 getGroupByDid is "
                    + e.getMessage());
        }
        if (StringUtils.isBlank(bmid.toString())) {
            bmid.append(defaultQzh);
        }
        return bmid.toString();
    }

    /**
     * 根据usercode的到qzh
     *
     * @author: LuYu
     */
    protected String getQzhByUserCode(String usercode) {
        String qzh = sUserMapper.getQzhByUserCode(usercode);
        if (StringUtils.isBlank(qzh)) {
            qzh = defaultQzh;
        }
        return qzh;
    }

    /**
     * 获取数据库参数 数据库类型名称,时间
     */
    protected String getSysdate() {
        if (sysdate != null) {
            return sysdate;
        }
        try {
            String databaseType = getDBTyeStr();
            if (databaseType != null
                    && databaseType.equals("Microsoft SQL Server")) {
                sysdate = "GETDATE()";
            } else if (databaseType != null && databaseType.equals("Oracle")) {
                sysdate = "SYSDATE";
            } else if (databaseType != null && databaseType.equals("Db2")) {
                sysdate = "CURRENT TIMESTAMP";
            } else if (databaseType != null && databaseType.equals("MySQL")) {
                sysdate = "NOW()";
            } else if (databaseType != null && databaseType.equals("H2")) {
                sysdate = "current_timestamp";
            }
        } catch (Exception e) {
            log.error("get database time is error!");
        }
        return sysdate;
    }

    protected SGroup getGroupByDid(Integer did) {
        return sGroupMapper.selectByPrimaryKey(did);
    }

    protected SGroup getGroupByDepCode(String depCode) {
        return sGroupMapper.getGroupByDepCode(depCode);
    }

    protected SUser getUserByUserCode(String usercode) {
        return sUserMapper.getUserByUsercode(usercode);
    }

    /**
     * 通过组的得到一个groupList 从小到大 从最底层到最高层
     */
    private List<SGroup> getGroupList(Integer groupDid, List<SGroup> groupList) {
        SGroup tempGroup = this.getGroupByDid(groupDid);
        if (groupList == null) {
            groupList = new ArrayList<SGroup>();
        }
        groupList.add(tempGroup);
        if (tempGroup.getPid() != 0) {
            this.getGroupList(tempGroup.getPid(), groupList);
        }
        return groupList;
    }

    /**
     * 会先从Lams获取did ,如果获取失败 或者为-1 会自己查询数据库获取
     *
     * @param tableName
     * @return
     */
    public Integer getMaxDid(String tableName) {
        Integer maxDid = -1;
        try {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            OutInterfaceServcie remote = (OutInterfaceServcie) factory
                    .create(OutInterfaceServcie.class, "http://" + lamsIP + "/Lams/hs/openInterface.hs");
            maxDid = remote.getMaxDid(tableName);
        } catch (Exception e) {
            log.error("从Lams获取DID出现错误: " + e.getMessage());
        }
        if (maxDid.equals(-1)) {
            maxDid = sUserMapper.getMaxDid(tableName);
            maxDid = (maxDid == null ? 1 : maxDid++);
        }
        return ++maxDid;
    }

    protected String insertUser4Map(Map<String, String> map, String dept_zj,
                                    String esbid) {
        String archKey = "";
        String archVal = "";
        Integer pid = null;
        String result = "1";
        FDTable fDtable = null;
        List<FDTable> fDTableList = null;
        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        if (null != map && null != map.keySet() && map.keySet().size() > 0) {
            try {
                Integer maxdid = getMaxDid("s_user");
                fDTableList = sGroupMapper.getFtableList("F_S_USER");
                Set<String> fieldSet = map.keySet();
                for (String outSysField : fieldSet) {
                    archKey = outSysField;
                    archVal = map.get(outSysField);
                    if (StringUtils.isNotBlank(archVal)
                            && StringUtils.isNotBlank(archKey)) {
                        archVal = (StringUtils.isBlank(archVal) ? "" : archVal);
                        archVal = (archVal.contains("'") ? archVal.replace("'",
                                "''") : archVal);// 兼容单引号
                        fDtable = CommonUtil.getFDtable(fDTableList, archKey);
                        fields.append(fDtable.getFieldname()).append(",");
                        switch (fDtable.getFieldtype()) {
                            case 11:
                                if (archVal.equals("")) {
                                    values.append("sysdate,");
                                } else {
                                    values.append(generateTimeToSQLDate(archVal))
                                            .append(",");
                                }
                                break;
                            case 1:
                                values.append("'").append(archVal).append("',");
                                break;
                            case 3:
                                if (StringUtils.isBlank(archVal)) {
                                    values.append("null ,");
                                } else {
                                    values.append(Integer.parseInt(archVal))
                                            .append(",");
                                }
                                break;
                            default:
                                values.append("'").append(archVal).append("',");
                                break;
                        }
                    }
                }
                try {
                    SUser user = sUserMapper.getUserByEsbid(esbid);
                    new IsExistDepOrUser().isUserExist(user);
                    SGroup group = sGroupMapper.getGroupByGfzj(dept_zj);
                    if (group == null) {
                        pid = defaultYhGroup;
                    } else {
                        pid = group.getDid();
                    }
                    fields.append("did,pid,esbid,esbcode");
                    values.append(maxdid).append(",").append(pid).append(",'")
                            .append(esbid).append("',").append("'")
                            .append(dept_zj).append("'");
                    String SQL = "insert into s_user (" + fields.toString()
                            + ") values ( " + values.toString() + " )";
                    System.out.println(SQL);
                    execSql(SQL);
                    result = "0";
                    log.error("插入一条数据成功. " + SQL);
                    SUserrole userrole = new SUserrole();
                    userrole.setDid(getMaxDid("S_USERROLE"));
                    userrole.setYhid(maxdid);
                    userrole.setJsid(jsid);
                    sUserroleMapper.insert(userrole);
                    log.error("用户:" + esbid + " 关联角色");
                } catch (ExceptionThrows e) {
                    System.out.println(e.getMessage());
                    log.error(e.getMessage());
                    result = "1";
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入一条数据失败." + e.getMessage());
            }
        } else {
            result = "1";
        }
        fields.setLength(0);
        values.setLength(0);
        return result;
    }

    protected String updateUser4Map(Map<String, String> map, String esbid) {
        String archKey = "";
        String archVal = "";
        String result = "0";
        FDTable fDtable = null;
        List<FDTable> fDTableList = null;
        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        if (null != map && null != map.keySet() && map.keySet().size() > 0) {
            try {
                fDTableList = sGroupMapper.getFtableList("F_S_USER");
                Set<String> fieldSet = map.keySet();
                for (String outSysField : fieldSet) {
                    archKey = outSysField;
                    archVal = map.get(outSysField);
                    if (StringUtils.isNotBlank(archVal)
                            && StringUtils.isNotBlank(archKey)) {
                        archVal = (StringUtils.isBlank(archVal) ? "" : archVal);
                        archVal = (archVal.contains("'") ? archVal.replace("'",
                                "''") : archVal);// 兼容单引号
                        fDtable = CommonUtil.getFDtable(fDTableList, archKey);
                        fields.append(fDtable.getFieldname()).append("=");
                        switch (fDtable.getFieldtype()) {
                            case 11:
                                if (archVal.equals("")) {
                                    fields.append("sysdate,");
                                } else {
                                    fields.append(generateTimeToSQLDate(archVal))
                                            .append(",");
                                }
                                break;
                            case 1:
                                fields.append("'").append(archVal).append("',");
                                break;
                            case 3:
                                if (StringUtils.isBlank(archVal)) {
                                    fields.append("null ,");
                                } else {
                                    fields.append(Integer.parseInt(archVal))
                                            .append(",");
                                }
                                break;
                            default:
                                fields.append("'").append(archVal).append("',");
                                break;
                        }
                    }
                }
                try {
                    SUser user = sUserMapper.getUserByEsbid(esbid);
                    new IsExistDepOrUser().isUserNotExist(user);
                    String SQL = "update s_user set "
                            + fields.toString().substring(0,
                            fields.length() - 1) + " where esbid = '"
                            + esbid + "'";
                    System.out.println(SQL);
                    execSql(SQL);
                    result = "0";
                    log.error("更新一条数据成功. " + SQL);
                } catch (ExceptionThrows e) {
                    System.out.println(e.getMessage());
                    log.error(e.getMessage());
                    result = "1";
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("更新一条数据失败. " + e.getMessage());
            }
        } else {
            result = "1";
        }
        fields.setLength(0);
        values.setLength(0);
        return result;
    }

    protected String insertDept4Map(Map<String, String> map, String gfzj,
                                    String parent_org_no, String qzh_zj) {
        String archKey = "";
        String archVal = "";
        String qzh = null;
        Integer pid = null;
        String result = "1";
        FDTable fDtable = null;
        List<FDTable> fDTableList = null;
        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        if (null != map && null != map.keySet() && map.keySet().size() > 0) {
            try {
                Integer maxdid = getMaxDid("s_group");
                fDTableList = sGroupMapper.getFtableList("F_S_GROUP");
                Set<String> fieldSet = map.keySet();
                for (String outSysField : fieldSet) {
                    archKey = outSysField;
                    archVal = map.get(outSysField);
                    if (StringUtils.isNotBlank(archVal)
                            && StringUtils.isNotBlank(archKey)) {
                        archVal = (StringUtils.isBlank(archVal) ? "" : archVal);
                        archVal = (archVal.contains("'") ? archVal.replace("'",
                                "''") : archVal);// 兼容单引号
                        fDtable = CommonUtil.getFDtable(fDTableList, archKey);
                        fields.append(fDtable.getFieldname()).append(",");
                        switch (fDtable.getFieldtype()) {
                            case 11:
                                if (archVal.equals("")) {
                                    values.append("sysdate,");
                                } else {
                                    values.append(generateTimeToSQLDate(archVal))
                                            .append(",");
                                }
                                break;
                            case 1:
                                values.append("'").append(archVal).append("',");
                                break;
                            case 3:
                                if (StringUtils.isBlank(archVal)) {
                                    values.append("null ,");
                                } else {
                                    values.append(Integer.parseInt(archVal))
                                            .append(",");
                                }
                                break;
                            default:
                                values.append("'").append(archVal).append("',");
                                break;
                        }
                    }
                }
                try {
                    SGroup group = sGroupMapper.getGroupByGfzj(gfzj);
                    new IsExistDepOrUser().isDeptExist(group);
                    String deptQzh = getQzhByKey(qzh_zj);
                    SGroup parent = sGroupMapper.getGroupByGfzj(parent_org_no);
                    qzh = (deptQzh == null ? defaultDeptQzh : deptQzh);
                    pid = (parent == null ? defaultDeptPid : parent.getDid());
                    fields.append("did,pid,qzh,gfzj,depcode");
                    values.append(maxdid).append(",").append(pid).append(",'")
                            .append(qzh).append("','").append(gfzj)
                            .append("','").append(parent_org_no).append("'");
                    String SQL = "insert into s_group (" + fields.toString()
                            + ") values ( " + values.toString() + " )";
                    System.out.println(SQL);
                    execSql(SQL);
                    result = "0";
                    log.error("插入一条数据成功. " + SQL);
                } catch (ExceptionThrows e) {
                    log.error(e.getMessage());
                    System.out.println(e.getMessage());
                    result = "1";
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入一条数据失败. " + e.getMessage());
            }
        } else {
            result = "1";
        }
        fields.setLength(0);
        values.setLength(0);
        return result;
    }

    protected String updateDept4Map(Map<String, String> map, String gfzj) {
        String archKey = ""; // 档案字段
        String archVal = ""; // 档案字段对应的值
        String result = "1";
        FDTable fDtable = null;
        List<FDTable> fDTableList = null;
        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        if (null != map && null != map.keySet() && map.keySet().size() > 0) {
            try {
                fDTableList = sGroupMapper.getFtableList("F_S_GROUP");
                Set<String> fieldSet = map.keySet();
                for (String outSysField : fieldSet) {
                    archKey = outSysField;
                    archVal = map.get(outSysField);
                    if (StringUtils.isNotBlank(archVal)
                            && StringUtils.isNotBlank(archKey)) {
                        archVal = (StringUtils.isBlank(archVal) ? "" : archVal);
                        archVal = (archVal.contains("'") ? archVal.replace("'",
                                "''") : archVal);// 兼容单引号
                        fDtable = CommonUtil.getFDtable(fDTableList, archKey);
                        fields.append(fDtable.getFieldname()).append("=");
                        switch (fDtable.getFieldtype()) {
                            case 11:
                                if (archVal.equals("")) {
                                    fields.append("sysdate,");
                                } else {
                                    fields.append(generateTimeToSQLDate(archVal))
                                            .append(",");
                                }
                                break;
                            case 1:
                                fields.append("'").append(archVal).append("',");
                                break;
                            case 3:
                                if (StringUtils.isBlank(archVal)) {
                                    fields.append("null ,");
                                } else {
                                    fields.append(Integer.parseInt(archVal))
                                            .append(",");
                                }
                                break;
                            default:
                                fields.append("'").append(archVal).append("',");
                                break;
                        }
                    }
                }
                try {
                    SGroup sg = sGroupMapper.getGroupByGfzj(gfzj);
                    new IsExistDepOrUser().isDepNotExist(sg);
                    String SQL = "update s_group set "
                            + fields.toString().substring(0,
                            fields.length() - 1) + " where gfzj = '"
                            + gfzj + "'";
                    System.out.println(SQL);
                    execSql(SQL);
                    result = "0";
                    log.error("更新一条数据成功. " + SQL);
                } catch (ExceptionThrows e) {
                    log.error(e.getMessage());
                    System.out.println(e.getMessage());
                    result = "1";
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("更新一条数据失败. " + e.getMessage());
            }
        } else {
            result = "1";
        }
        fields.setLength(0);
        values.setLength(0);
        return result;
    }

    protected String insertUser(SUserWithBLOBs suser, String dept_zj,
                                String esbid) {
        Integer pid = null;
        String result = "1";
        try {
            Integer maxdid = getMaxDid("s_user");
            SUser user = sUserMapper.getUserByEsbid(esbid);
            new IsExistDepOrUser().isUserExist(user);
            SGroup group = sGroupMapper.getGroupByGfzj(dept_zj);
            if (group == null) {
                pid = defaultYhGroup;
            } else {
                pid = group.getDid();
            }
            suser.setDid(maxdid);
            suser.setPid(pid);
            suser.setEsbid(esbid);
            suser.setEsbcode(dept_zj);
            sUserMapper.insert(suser);
            log.error("增加一个用户:" + esbid);
            result = "0";
            SUserrole userrole = new SUserrole();
            userrole.setDid(getMaxDid("S_USERROLE"));
            userrole.setYhid(maxdid);
            userrole.setJsid(jsid);
            sUserroleMapper.insert(userrole);
            log.error("用户:" + esbid + " 关联角色");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            result = "1";
        }
        return result;
    }

    protected String updateUser(SUserWithBLOBs suser, String esbid) {
        String result = "1";
        try {
            SUser user = sUserMapper.getUserByEsbid(esbid);
            new IsExistDepOrUser().isUserNotExist(user);
            suser.setEsbid(esbid);
            sUserMapper.updateByKey(suser);
            result = "0";
            log.error("修改一个用户： " + esbid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            result = "1";
        }
        return result;
    }

    protected String insertDept(SGroup sgroup, String gfzj,
                                String parent_org_no, String qzh_zj) {
        String qzh = null;
        Integer pid = null;
        String result = "1";
        try {
            Integer maxdid = getMaxDid("s_group");
            SGroup group = sGroupMapper.getGroupByGfzj(gfzj);
            new IsExistDepOrUser().isDeptExist(group);
            String deptQzh = getQzhByKey(qzh_zj);
            SGroup parent = sGroupMapper.getGroupByGfzj(parent_org_no);
            qzh = (deptQzh == null ? defaultDeptQzh : deptQzh);
            pid = (parent == null ? defaultDeptPid : parent.getDid());
            sgroup.setDid(maxdid);
            sgroup.setPid(pid);
            sgroup.setQzh(qzh);
            sgroup.setGfzj(gfzj);
            sgroup.setDepcode(parent_org_no);
            sGroupMapper.insert(sgroup);
            result = "0";
            log.error("增加一个部门. " + gfzj);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            result = "1";
        }
        return result;
    }

    protected String updateDept(SGroup sgroup, String gfzj) {
        String result = "1";
        try {
            SGroup sg = sGroupMapper.getGroupByGfzj(gfzj);
            new IsExistDepOrUser().isDepNotExist(sg);
            sgroup.setGfzj(gfzj);
            sGroupMapper.updateByKey(sgroup);
            result = "0";
            log.error("修改一个部门. " + gfzj);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
            result = "1";
        }
        return result;
    }

    public Integer insertEfile(String tableName, final EFile eFile) {
        return jdbcDao.insertEfile(tableName, eFile);
    }

    /**
     * 根据部门名称获取全宗号
     *
     * @param qzmc
     * @return
     */
    protected String getQzh(String qzmc) {
        String sql = "select qzh from s_qzh where bz = '" + qzmc + "'";
        String qzh = jdbcDao.query4String(sql);
        return qzh;
    }

    /**
     * 根据pid获取全宗号
     *
     * @param pid
     * @return
     */
    protected String getQzhByPid(Integer pid) {
        String sql = "select qzh from s_qzh where did = " + pid;
        String qzh = jdbcDao.query4String(sql);
        return qzh;
    }

    protected String getQzhByKey(String key) {
        String sql = "select qzh from s_qzh where primarykey = " + key;
        String qzh = jdbcDao.query4String(sql);
        return qzh;
    }

    /**
     * 通过pzm的到服务器的配置.如果空的返回默认的
     *
     * @param pzm s_fwqpz.pzname
     * @return
     */
    public SFwqpz getFwqpz(String pzm) {
        SFwqpz fwqpz = null;
        if (StringUtils.isNotBlank(pzm)) {
            fwqpz = sGroupMapper.getFwqpzByPzm(pzm);
        }
        return null == fwqpz ? sGroupMapper.getDefaultFwqpz() : fwqpz;
    }

    public List<FDTable> fTableList(String fTableName){
        List<FDTable> list = null;
        fTableName = fTableName.toUpperCase();
        if(fTableName.startsWith("F_")){
            list = sUserMapper.getFtableList(fTableName);
        }else{
            list = sUserMapper.getFtableList("F_"+fTableName);
        }
        return list;
    }
    /**
     * <p>Title:  跟据字段中文名得到fdtable</p>
     *
     * @param fieldChname 字段中文名称得到fdtable
     * @date 2014年5月8日
     */
    public FDTable getFDtable(List<FDTable> list, String fieldname) {
        FDTable fDtable = null;
        for (FDTable ele : list) {
            if (ele.getFieldname().equals(fieldname)) {
                fDtable = ele;
                break;
            }
        }
        return fDtable;
    }

    public String getLamsIP() {
        return lamsIP;
    }

    public Integer getAttr() {
        return attr;
    }

    public Integer getAttrex() {
        return attrex;
    }

    public Integer getStatus() {
        return status;
    }

    @Autowired
    protected JdbcDao jdbcDao;
    @Autowired
    protected SGroupMapper sGroupMapper;
    @Autowired
    protected SUserMapper sUserMapper;
    @Autowired
    protected SQzhMapper sQzhMapper;
    @Autowired
    protected SUserroleMapper sUserroleMapper;
    @Autowired
    @Value("${default.jsid}")
    protected Integer jsid;// 默认的角色 普通用户
    // 默认用户部门
    @Autowired
    @Value("${lams.default.nogroup.user.pid}")
    protected Integer defaultYhGroup;
    @Autowired
    @Value("${sqlserverSchemaName}")
    protected String sqlserverSchemaName;

    /**
     * 默认的全宗号
     */
    @Autowired
    @Value("${lams.default.qzh}")
    protected String defaultQzh;
    /**
     * 默认部门pid
     */
    @Autowired
    @Value("${default.dept.pid}")
    protected Integer defaultDeptPid;
    /**
     * 默认部门全宗号
     */
    @Autowired
    @Value("${default.dept.qzh}")
    protected String defaultDeptQzh;
    @Autowired
    @Value("${lams.dfile.attrex}")
    protected Integer attrex;// 移交接收状态
    @Autowired
    @Value("${lams.dfile.attr}")
    protected Integer attr;// 归档前后
    @Autowired
    @Value("${lams.ip}")
    protected String lamsIP;

    @Autowired
    @Value("${lams.dfile.status}")
    protected Integer status;//状态
    /**
     * 是否调试
     */
    @Autowired
    @Value("${Enterprise.Efile.Convert.isdebug}")
    protected Boolean debug;

    private String sysdate = null;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
