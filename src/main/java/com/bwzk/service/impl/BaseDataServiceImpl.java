package com.bwzk.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;

import com.bwzk.pojo.FDTable;
import com.bwzk.pojo.SGroup;
import com.bwzk.pojo.SQzh;
import com.bwzk.pojo.SUser;
import com.bwzk.pojo.SUserrole;
import com.bwzk.pojo.jaxb.Field;
import com.bwzk.pojo.jaxb.Table;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.BaseDataService;
import com.bwzk.util.CommonUtil;
import com.bwzk.util.ExceptionThrows;
import com.bwzk.util.GlobalFinalAttr;
import com.bwzk.util.IsExistDepOrUser;
import com.bwzk.util.XmlObjUtil;

@Service("baseDataServiceImpl")
@WebService(name = "BaseDataWS", targetNamespace = "http://service.lams.cn/")
public class BaseDataServiceImpl extends BaseService implements BaseDataService {
	/**
	 * <p>
	 * Title: 添加用户数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataTxt
	 * @param deptPk
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addUserByTxt(@WebParam(name = "dataTxt") String dataTxt,
			@WebParam(name = "deptPk") String deptPk,
			@WebParam(name = "primaryKey") String primaryKey) {
		Integer maxdid = 0;
		String SQL = "";
		String result = "1";
		Integer pid = null;
		StringBuffer fields = new StringBuffer();
		StringBuffer values = new StringBuffer();
		String xmlPath = GlobalFinalAttr.XML_PATH + "S_USER.XML";
		File xmlFile = new File(xmlPath);
		if (!xmlFile.exists()) {
			log.error("XML文件不存在");
			return result;
		}
		try {
			maxdid = getMaxDid("s_user");
			String xmlString = FileUtils.readFileToString(xmlFile,
					GlobalFinalAttr.PRJ_CHAR_CODE);
			Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
			List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
			String[] s = dataTxt.split(GlobalFinalAttr.SEPARATOR);
			if (xmlfields.size() != s.length) {
				log.error("传入的值于xml的数量不符合");
				return result;
			}
			int i = 0;
			for (Field field : xmlfields) {// 循环字段
				String theValue = (StringUtils.isBlank(s[i]) ? "" : s[i]);
				theValue = theValue.contains("'") ? theValue.replace("'", "''")
						: theValue;
				fields.append(field.getFieldname()).append(",");
				switch (field.getFieldtype()) {
				case 11:
					if (theValue.equals("")) {
						values.append("null,");
					} else {
						values.append(generateTimeToSQLDate(theValue)).append(
								",");
					}
					break;
				case 1:
					values.append("'").append(theValue).append("',");
					break;
				case 3:
					if (StringUtils.isBlank(theValue)) {
						values.append("null ,");
					} else {
						values.append(Integer.parseInt(theValue)).append(",");
					}
					break;
				default:
					values.append("'").append(theValue).append("',");
					break;
				}
				i = i + 1;
			}
			try {
				SUser user = sUserMapper.getUserByEsbid(primaryKey);
				new IsExistDepOrUser().isUserExist(user);
				SGroup group = sGroupMapper.getGroupByGfzj(deptPk);
				if (group == null) {
					pid = defaultYhGroup;
				} else {
					pid = group.getDid();
				}
				fields.append("did,pid,esbid,esbcode");
				values.append(maxdid).append(",").append(pid).append(",'")
						.append(primaryKey).append("',").append("'")
						.append(deptPk).append("'");
				SQL = "insert into " + table.getName() + " ("
						+ fields.toString() + ") values ( " + values.toString()
						+ " )";
				execSql(SQL);
				result = "0";
				log.error("插入一条数据成功.addUserByTxt: " + SQL);
				SUserrole userrole = new SUserrole();
				userrole.setDid(getMaxDid("S_USERROLE"));
				userrole.setYhid(maxdid);
				userrole.setJsid(jsid);
				sUserroleMapper.insert(userrole);
				log.error("用户:" + primaryKey + " 关联角色");
			} catch (ExceptionThrows e) {
				System.out.println(e.getMessage());
				log.error(e.getMessage());
				result = "1";
			}
			fields.setLength(0);
			values.setLength(0);
		} catch (Exception e) {
			log.error("插入一条数据错误.addUserByTxt:", e);
			log.error(SQL);
		}

		return result;
	}

	/**
	 * <p>
	 * Title: 修改用户数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataTxt
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String updateUserByTxt(@WebParam(name = "dataTxt") String dataTxt,
			@WebParam(name = "primaryKey") String primaryKey) {
		String SQL = "";
		String result = "1";
		String xmlPath = GlobalFinalAttr.XML_PATH + "S_USER.XML";
		File xmlFile = new File(xmlPath);
		if (!xmlFile.exists()) {
			log.error("XML文件不存在");
			return result;
		}
		try {
			StringBuffer fields = new StringBuffer();
			StringBuffer values = new StringBuffer();
			String xmlString = FileUtils.readFileToString(xmlFile,
					GlobalFinalAttr.PRJ_CHAR_CODE);
			Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
			List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
			String[] s = dataTxt.split(GlobalFinalAttr.SEPARATOR);
			if (xmlfields.size() != s.length) {
				log.error("传入的值于xml的数量不符合");
				return result;
			}
			int i = 0;
			for (Field field : xmlfields) {// 循环字段
				String theValue = (StringUtils.isBlank(s[i]) ? "" : s[i]);
				theValue = theValue.contains("'") ? theValue.replace("'", "''")
						: theValue;
				fields.append(field.getFieldname()).append("=");
				switch (field.getFieldtype()) {
				case 11:
					if (theValue.equals("")) {
						fields.append("null,");
					} else {
						fields.append(generateTimeToSQLDate(theValue)).append(
								",");
					}
					break;
				case 1:
					fields.append("'").append(theValue).append("',");
					break;
				case 3:
					if (StringUtils.isBlank(theValue)) {
						fields.append("null ,");
					} else {
						fields.append(Integer.parseInt(theValue)).append(",");
					}
					break;
				default:
					fields.append("'").append(theValue).append("',");
					break;
				}
				i = i + 1;
			}
			try {
				SUser user = sUserMapper.getUserByEsbid(primaryKey);
				new IsExistDepOrUser().isUserNotExist(user);
				SQL = "update " + table.getName() + " set "
						+ fields.toString().substring(0, fields.length() - 1)
						+ " where esbid = '" + primaryKey + "'";
				execSql(SQL);
				result = "0";
				log.error("更新一条数据成功.updateUserByTxt: " + SQL);
				fields.setLength(0);
				values.setLength(0);
			} catch (ExceptionThrows e) {
				System.out.println(e.getMessage());
				log.error(e.getMessage());
				result = "1";
			}
		} catch (Exception e) {
			log.error("更新一条数据错误.updateUserByTxt:", e);
			log.error(SQL);
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加部门数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataTxt
	 * @param primaryKey
	 * @param orgPk
	 * @param parentPk
	 * @return
	 */
	@WebMethod
	public String addDeptByTxt(@WebParam(name = "dataTxt") String dataTxt,
			@WebParam(name = "primaryKey") String primaryKey,
			@WebParam(name = "orgPk") String orgPk,
			@WebParam(name = "parentPk") String parentPk) {
		Integer maxdid = 0;
		String SQL = "";
		String result = "1";
		Integer pid = null;
		String qzh = null;
		String xmlPath = GlobalFinalAttr.XML_PATH + "S_GROUP.XML";
		File xmlFile = new File(xmlPath);
		if (!xmlFile.exists()) {
			log.error("XML文件不存在");
			return result;
		}
		try {
			maxdid = getMaxDid("s_group");
			StringBuffer fields = new StringBuffer();
			StringBuffer values = new StringBuffer();
			String xmlString = FileUtils.readFileToString(xmlFile,
					GlobalFinalAttr.PRJ_CHAR_CODE);
			Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
			List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
			String[] s = dataTxt.split(GlobalFinalAttr.SEPARATOR);
			if (xmlfields.size() != s.length) {
				log.error("传入的值于xml的数量不符合");
				return result;
			}
			int i = 0;
			for (Field field : xmlfields) {// 循环字段
				String theValue = (StringUtils.isBlank(s[i]) ? "" : s[i]);
				theValue = theValue.contains("'") ? theValue.replace("'", "''")
						: theValue;
				fields.append(field.getFieldname()).append(",");
				switch (field.getFieldtype()) {
				case 11:
					if (theValue.equals("")) {
						values.append("null,");
					} else {
						values.append(generateTimeToSQLDate(theValue)).append(
								",");
					}
					break;
				case 1:
					values.append("'").append(theValue).append("',");
					break;
				case 3:
					if (StringUtils.isBlank(theValue)) {
						values.append("null ,");
					} else {
						values.append(Integer.parseInt(theValue)).append(",");
					}
					break;
				default:
					values.append("'").append(theValue).append("',");
					break;
				}
				i = i + 1;
			}
			try {
				SGroup group = sGroupMapper.getGroupByGfzj(primaryKey);
				new IsExistDepOrUser().isDeptExist(group);
				String deptQzh = getQzhByKey(orgPk);
				SGroup parent = sGroupMapper.getGroupByGfzj(parentPk);
				qzh = (deptQzh == null ? defaultDeptQzh : deptQzh);
				pid = (parent == null ? defaultDeptPid : parent.getDid());
				fields.append("did,pid,qzh,gfzj,depcode");
				values.append(maxdid).append(",").append(pid).append(",'")
						.append(qzh).append("','").append(primaryKey)
						.append("','").append(parentPk).append("'");
				SQL = "insert into " + table.getName() + " ("
						+ fields.toString() + ") values ( " + values.toString()
						+ " )";
				execSql(SQL);
				result = "0";
				log.error("插入一条数据成功.addDeptByTxt: " + SQL);
				fields.setLength(0);
				values.setLength(0);
			} catch (ExceptionThrows e) {
				log.error(e.getMessage());
				System.out.println(e.getMessage());
				result = "1";
			}

		} catch (Exception e) {
			log.error("插入一条数据错误.addDeptByTxt:", e);
			log.error(SQL);
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataTxt
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String updateDeptByTxt(@WebParam(name = "dataTxt") String dataTxt,
			@WebParam(name = "primaryKey") String primaryKey) {
		String SQL = "";
		String result = "1";
		String xmlPath = GlobalFinalAttr.XML_PATH + "S_GROUP.XML";
		File xmlFile = new File(xmlPath);
		if (!xmlFile.exists()) {
			log.error("");
			return result;
		}
		try {
			StringBuffer fields = new StringBuffer();
			StringBuffer values = new StringBuffer();
			String xmlString = FileUtils.readFileToString(xmlFile,
					GlobalFinalAttr.PRJ_CHAR_CODE);
			Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
			List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
			String[] s = dataTxt.split(GlobalFinalAttr.SEPARATOR);
			if (xmlfields.size() != s.length) {
				log.error("传入的值于xml的数量不符合");
				return result;
			}
			int i = 0;
			for (Field field : xmlfields) {// 循环字段
				String theValue = (StringUtils.isBlank(s[i]) ? "" : s[i]);
				theValue = theValue.contains("'") ? theValue.replace("'", "''")
						: theValue;
				fields.append(field.getFieldname()).append("=");
				switch (field.getFieldtype()) {
				case 11:
					if (theValue.equals("")) {
						fields.append("null,");
					} else {
						fields.append(generateTimeToSQLDate(theValue)).append(
								",");
					}
					break;
				case 1:
					fields.append("'").append(theValue).append("',");
					break;
				case 3:
					if (StringUtils.isBlank(theValue)) {
						fields.append("null ,");
					} else {
						fields.append(Integer.parseInt(theValue)).append(",");
					}
					break;
				default:
					fields.append("'").append(theValue).append("',");
					break;
				}
				i = i + 1;
			}
			try {
				SGroup sg = sGroupMapper.getGroupByGfzj(primaryKey);
				new IsExistDepOrUser().isDepNotExist(sg);
				SQL = "update " + table.getName() + " set "
						+ fields.toString().substring(0, fields.length() - 1)
						+ " where gfzj = '" + primaryKey + "'";
				execSql(SQL);
				result = "0";
				log.error("更新一条数据成功.updateDeptByTxt: " + SQL);
				fields.setLength(0);
				values.setLength(0);
			} catch (ExceptionThrows e) {
				log.error(e.getMessage());
				System.out.println(e.getMessage());
				result = "1";
			}
		} catch (Exception e) {
			log.error("更新一条数据错误.updateDeptByTxt:", e);
			log.error(SQL);
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加用户数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataXml
	 * @param deptPk
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addUserByXml(@WebParam(name = "dataXml") String dataXml,
			@WebParam(name = "deptPk") String deptPk,
			@WebParam(name = "primaryKey") String primaryKey) {
		String result = "0";
		try {
			Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
			Map<String, String> vars = convertTable2Map(table);
			result = insertUser4Map(vars, deptPk, primaryKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 更新用户数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataXml
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String updateUserByXml(@WebParam(name = "dataXml") String dataXml,
			@WebParam(name = "primaryKey") String primaryKey) {
		String result = "0";
		try {
			Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
			Map<String, String> vars = convertTable2Map(table);
			result = updateUser4Map(vars, primaryKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加部门数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataXml
	 * @param primaryKey
	 * @param orgPk
	 * @param parentPk
	 * @return
	 */
	@WebMethod
	public String addDeptByXml(@WebParam(name = "dataXml") String dataXml,
			@WebParam(name = "primaryKey") String primaryKey,
			@WebParam(name = "orgPk") String orgPk,
			@WebParam(name = "parentPk") String parentPk) {
		String result = null;
		Map<String, String> vars = null;
		try {
			Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
			vars = convertTable2Map(table);
			result = insertDept4Map(vars, primaryKey, parentPk, orgPk);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataXml
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String updateDeptByXml(@WebParam(name = "dataXml") String dataXml,
			@WebParam(name = "primaryKey") String primaryKey) {
		String result = "0";
		Map<String, String> vars = null;
		try {
			Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
			vars = convertTable2Map(table);
			result = updateDept4Map(vars, primaryKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加用户数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataJson
	 * @param deptPk
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addUserByJson(@WebParam(name = "dataJson") String dataJson,
			@WebParam(name = "deptPk") String deptPk,
			@WebParam(name = "primaryKey") String primaryKey) {
		String result = "0";
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> vars = null;
			vars = mapper.readValue(dataJson, Map.class);
			result = insertUser4Map(vars, deptPk, primaryKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 修改用户数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataJson
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String updateUserByJson(
			@WebParam(name = "dataJson") String dataJson,
			@WebParam(name = "primaryKey") String primaryKey) {
		String result = "0";
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> vars = null;
			vars = mapper.readValue(dataJson, Map.class);
			result = updateUser4Map(vars, primaryKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加部门数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataJson
	 * @param primaryKey
	 * @param orgPk
	 * @param parentPk
	 * @return
	 */
	@WebMethod
	public String addDeptByJson(@WebParam(name = "dataJson") String dataJson,
			@WebParam(name = "primaryKey") String primaryKey,
			@WebParam(name = "orgPk") String orgPk,
			@WebParam(name = "parentPk") String parentPk) {
		String result = null;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> vars = null;
		try {
			vars = mapper.readValue(dataJson, Map.class);
			result = insertDept4Map(vars, primaryKey, parentPk, orgPk);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataJson
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String updateDeptByJson(
			@WebParam(name = "dataJson") String dataJson,
			@WebParam(name = "primaryKey") String primaryKey) {
		String result = "0";
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> vars = null;
			vars = mapper.readValue(dataJson, Map.class);
			result = updateDept4Map(vars, primaryKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		return result;
	}

	/**
	 * Title: 增加或修改用户数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * 
	 * @param dataTxt
	 * @param deptPk
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addOrUpdateUserByTxt(
			@WebParam(name = "dataTxt") String dataTxt,
			@WebParam(name = "deptPk") String deptPk,
			@WebParam(name = "primaryKey") String primaryKey) {
		Integer maxdid = 0;
		String SQL = "";
		String result = "1";
		Integer pid = null;
		StringBuffer fields = new StringBuffer();
		StringBuffer values = new StringBuffer();
		String xmlPath = GlobalFinalAttr.XML_PATH + "S_USER.XML";
		File xmlFile = new File(xmlPath);
		if (!xmlFile.exists()) {
			log.error("XML文件不存在");
			return result;
		}
		try {
			maxdid = getMaxDid("s_user");
			String xmlString = FileUtils.readFileToString(xmlFile,
					GlobalFinalAttr.PRJ_CHAR_CODE);
			Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
			List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
			String[] s = dataTxt.split(GlobalFinalAttr.SEPARATOR);
			if (xmlfields.size() != s.length) {
				log.error("传入的值于xml的数量不符合");
				return result;
			}
			int i = 0;
			for (Field field : xmlfields) {// 循环字段
				String theValue = (StringUtils.isBlank(s[i]) ? "" : s[i]);
				theValue = theValue.contains("'") ? theValue.replace("'", "''")
						: theValue;
				fields.append(field.getFieldname()).append(",");
				switch (field.getFieldtype()) {
				case 11:
					if (theValue.equals("")) {
						values.append("null,");
					} else {
						values.append(generateTimeToSQLDate(theValue)).append(
								",");
					}
					break;
				case 1:
					values.append("'").append(theValue).append("',");
					break;
				case 3:
					if (StringUtils.isBlank(theValue)) {
						values.append("null ,");
					} else {
						values.append(Integer.parseInt(theValue)).append(",");
					}
					break;
				default:
					values.append("'").append(theValue).append("',");
					break;
				}
				i = i + 1;
			}
			SUser user = sUserMapper.getUserByEsbid(primaryKey);
			if (user != null) {
				updateUserByTxt(dataTxt, primaryKey);
			} else {
				SGroup group = sGroupMapper.getGroupByGfzj(deptPk);
				if (group == null) {
					pid = defaultYhGroup;
				} else {
					pid = group.getDid();
				}
				fields.append("did,pid,esbid,esbcode");
				values.append(maxdid).append(",").append(pid).append(",'")
						.append(primaryKey).append("',").append("'")
						.append(deptPk).append("'");
				SQL = "insert into " + table.getName() + " ("
						+ fields.toString() + ") values ( " + values.toString()
						+ " )";
				execSql(SQL);
				result = "0";
				log.error("插入一条数据成功.addOrUpdateUserByTxt: " + SQL);
				SUserrole userrole = new SUserrole();
				userrole.setDid(getMaxDid("S_USERROLE"));
				userrole.setYhid(maxdid);
				userrole.setJsid(jsid);
				sUserroleMapper.insert(userrole);
				log.error("用户:" + primaryKey + " 关联角色");
				fields.setLength(0);
				values.setLength(0);
			}
		} catch (Exception e) {
			log.error("插入一条数据错误.addOrUpdateUserByTxt:", e);
			log.error(SQL);
		}

		http://localhost:81/Lams/rest/restLoadFile?libcode=6&level=2&efiledid=1565285&convertStatus=0
		return result;
	}

	/**
	 * <p>
	 * Title: 增加或修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataTxt
	 * @param primaryKey
	 * @param orgPk
	 * @param parentPk
	 * @return
	 */
	@WebMethod
	public String addOrUpdateDeptByTxt(
			@WebParam(name = "dataTxt") String dataTxt,
			@WebParam(name = "primaryKey") String primaryKey,
			@WebParam(name = "orgPk") String orgPk,
			@WebParam(name = "parentPk") String parentPk) {
		Integer maxdid = 0;
		String SQL = "";
		String result = "1";
		Integer pid = null;
		String qzh = null;
		String xmlPath = GlobalFinalAttr.XML_PATH + "S_GROUP.XML";
		File xmlFile = new File(xmlPath);
		if (!xmlFile.exists()) {
			log.error("XML文件不存在");
			return result;
		}
		try {
			maxdid = getMaxDid("s_group");
			StringBuffer fields = new StringBuffer();
			StringBuffer values = new StringBuffer();
			String xmlString = FileUtils.readFileToString(xmlFile,
					GlobalFinalAttr.PRJ_CHAR_CODE);
			Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
			List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
			String[] s = dataTxt.split(GlobalFinalAttr.SEPARATOR);
			if (xmlfields.size() != s.length) {
				log.error("传入的值于xml的数量不符合");
				return result;
			}
			int i = 0;
			for (Field field : xmlfields) {// 循环字段
				String theValue = (StringUtils.isBlank(s[i]) ? "" : s[i]);
				theValue = theValue.contains("'") ? theValue.replace("'", "''")
						: theValue;
				fields.append(field.getFieldname()).append(",");
				switch (field.getFieldtype()) {
				case 11:
					if (theValue.equals("")) {
						values.append("null,");
					} else {
						values.append(generateTimeToSQLDate(theValue)).append(
								",");
					}
					break;
				case 1:
					values.append("'").append(theValue).append("',");
					break;
				case 3:
					if (StringUtils.isBlank(theValue)) {
						values.append("null ,");
					} else {
						values.append(Integer.parseInt(theValue)).append(",");
					}
					break;
				default:
					values.append("'").append(theValue).append("',");
					break;
				}
				i = i + 1;
			}
			SGroup group = sGroupMapper.getGroupByGfzj(primaryKey);
			if (group != null) {
				updateDeptByTxt(dataTxt, primaryKey);
			} else {
				String deptQzh = getQzhByKey(orgPk);
				SGroup parent = sGroupMapper.getGroupByGfzj(parentPk);
				qzh = (deptQzh == null ? defaultDeptQzh : deptQzh);
				pid = (parent == null ? defaultDeptPid : parent.getDid());
				fields.append("did,pid,qzh,gfzj,depcode");
				values.append(maxdid).append(",").append(pid).append(",'")
						.append(qzh).append("','").append(primaryKey)
						.append("','").append(parentPk).append("'");
				SQL = "insert into " + table.getName() + " ("
						+ fields.toString() + ") values ( " + values.toString()
						+ " )";
				execSql(SQL);
				result = "0";
				log.error("插入一条数据成功.addOrUpdateDeptByTxt: " + SQL);
				fields.setLength(0);
				values.setLength(0);
			}
		} catch (Exception e) {
			log.error("插入一条数据错误.addOrUpdateDeptByTxt:", e);
			log.error(SQL);
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加或修改用户数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataXml
	 * @param deptPk
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addOrUpdateUserByXml(
			@WebParam(name = "dataXml") String dataXml,
			@WebParam(name = "deptPk") String deptPk,
			@WebParam(name = "primaryKey") String primaryKey) {
		String result = "1";
		Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
		Map<String, String> map = convertTable2Map(table);
		String archKey = "";
		String archVal = "";
		Integer pid = null;
		String SQL = "";
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
				SUser user = sUserMapper.getUserByEsbid(primaryKey);
				if (user != null) {
					updateUserByXml(dataXml, primaryKey);
				} else {
					SGroup group = sGroupMapper.getGroupByGfzj(deptPk);
					if (group == null) {
						pid = defaultYhGroup;
					} else {
						pid = group.getDid();
					}
					fields.append("did,pid,esbid,esbcode");
					values.append(maxdid).append(",").append(pid).append(",'")
							.append(primaryKey).append("',").append("'")
							.append(deptPk).append("'");
					SQL = "insert into s_user (" + fields.toString()
							+ ") values ( " + values.toString() + " )";
					System.out.println(SQL);
					execSql(SQL);
					result = "0";
					log.error("插入一条数据成功.addOrUpdateUserByXml: " + SQL);
					SUserrole userrole = new SUserrole();
					userrole.setDid(getMaxDid("S_USERROLE"));
					userrole.setYhid(maxdid);
					userrole.setJsid(jsid);
					sUserroleMapper.insert(userrole);
					log.error("用户:" + primaryKey + " 关联角色");
				}
			} catch (Exception e) {
				log.error("插入一条数据错误.addOrUpdateUserByXml:", e);
				log.error(SQL);
			}
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加或修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataXml
	 * @param primaryKey
	 * @param orgPk
	 * @param parentPk
	 * @return
	 */
	@WebMethod
	public String addOrUpdateDeptByXml(
			@WebParam(name = "dataXml") String dataXml,
			@WebParam(name = "primaryKey") String primaryKey,
			@WebParam(name = "orgPk") String orgPk,
			@WebParam(name = "parentPk") String parentPk) {
		String result = "1";
		Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
		Map<String, String> map = convertTable2Map(table);
		String archKey = "";
		String archVal = "";
		String qzh = null;
		Integer pid = null;
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
				SGroup group = sGroupMapper.getGroupByGfzj(primaryKey);
				if (group != null) {
					updateDeptByXml(dataXml, primaryKey);
				} else {
					String deptQzh = getQzhByKey(orgPk);
					SGroup parent = sGroupMapper.getGroupByGfzj(parentPk);
					qzh = (deptQzh == null ? defaultDeptQzh : deptQzh);
					pid = (parent == null ? defaultDeptPid : parent.getDid());
					fields.append("did,pid,qzh,gfzj,depcode");
					values.append(maxdid).append(",").append(pid).append(",'")
							.append(qzh).append("','").append(primaryKey)
							.append("','").append(parentPk).append("'");
					String SQL = "insert into s_group (" + fields.toString()
							+ ") values ( " + values.toString() + " )";
					System.out.println(SQL);
					execSql(SQL);
					result = "0";
					log.error("插入一条数据成功.addOrUpdateDeptByXml: " + SQL);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("插入一条数据失败.addOrUpdateDeptByXml: " + e.getMessage());
			}
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加或修改用户数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataJson
	 * @param deptPk
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addOrUpdateUserByJson(
			@WebParam(name = "dataJson") String dataJson,
			@WebParam(name = "deptPk") String deptPk,
			@WebParam(name = "primaryKey") String primaryKey) {
		String result = "0";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = null;
		try {
			map = mapper.readValue(dataJson, Map.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String archKey = "";
		String archVal = "";
		Integer pid = null;
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
				SUser user = sUserMapper.getUserByEsbid(primaryKey);
				if (user != null) {
					updateUserByJson(dataJson, primaryKey);
				} else {
					SGroup group = sGroupMapper.getGroupByGfzj(deptPk);
					if (group == null) {
						pid = defaultYhGroup;
					} else {
						pid = group.getDid();
					}
					fields.append("did,pid,esbid,esbcode");
					values.append(maxdid).append(",").append(pid).append(",'")
							.append(primaryKey).append("',").append("'")
							.append(deptPk).append("'");
					String SQL = "insert into s_user (" + fields.toString()
							+ ") values ( " + values.toString() + " )";
					System.out.println(SQL);
					execSql(SQL);
					result = "0";
					log.error("插入一条数据成功.addOrUpdateUserByJson: " + SQL);
					SUserrole userrole = new SUserrole();
					userrole.setDid(getMaxDid("S_USERROLE"));
					userrole.setYhid(maxdid);
					userrole.setJsid(jsid);
					sUserroleMapper.insert(userrole);
					log.error("用户:" + primaryKey + " 关联角色");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("插入一条数据失败.addOrUpdateUserByJson: " + e.getMessage());
			}
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加或修改部门数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataJson
	 * @param primaryKey
	 * @param orgPk
	 * @param parentPk
	 * @return
	 */
	@WebMethod
	public String addOrUpdateDeptByJson(
			@WebParam(name = "dataJson") String dataJson,
			@WebParam(name = "primaryKey") String primaryKey,
			@WebParam(name = "orgPk") String orgPk,
			@WebParam(name = "parentPk") String parentPk) {
		String result = "1";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = null;
		try {
			map = mapper.readValue(dataJson, Map.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String archKey = "";
		String archVal = "";
		String qzh = null;
		Integer pid = null;
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
				SGroup group = sGroupMapper.getGroupByGfzj(primaryKey);
				if (group != null) {
					updateDeptByJson(dataJson, primaryKey);
				} else {
					String deptQzh = getQzhByKey(orgPk);
					SGroup parent = sGroupMapper.getGroupByGfzj(parentPk);
					qzh = (deptQzh == null ? defaultDeptQzh : deptQzh);
					pid = (parent == null ? defaultDeptPid : parent.getDid());
					fields.append("did,pid,qzh,gfzj,depcode");
					values.append(maxdid).append(",").append(pid).append(",'")
							.append(qzh).append("','").append(primaryKey)
							.append("','").append(parentPk).append("'");
					String SQL = "insert into s_group (" + fields.toString()
							+ ") values ( " + values.toString() + " )";
					System.out.println(SQL);
					execSql(SQL);
					result = "0";
					log.error("插入一条数据成功.addOrUpdateDeptByJson: " + SQL);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("插入一条数据失败.addOrUpdateDeptByJson: " + e.getMessage());
			}
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加或修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataTxt
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addOrUpdateQzhByTxt(
			@WebParam(name = "dataTxt") String dataTxt,
			@WebParam(name = "primaryKey") String primaryKey) {
		Integer maxdid = 0;
		String SQL = "";
		String result = "1";
		// String xmlPath = xmlName.toUpperCase().contains(".XML") ?
		// GlobalFinalAttr.XML_PATH + xmlName : GlobalFinalAttr.XML_PATH +
		// xmlName + ".XML";
		String xmlPath = GlobalFinalAttr.XML_PATH + "S_QZH.XML";
		File xmlFile = new File(xmlPath);
		if (!xmlFile.exists()) {
			log.error("XML文件不存在");
			return result;
		}
		try {
			maxdid = getMaxDid("s_qzh");
			StringBuffer fields = new StringBuffer();
			StringBuffer values = new StringBuffer();
			String xmlString = FileUtils.readFileToString(xmlFile,
					GlobalFinalAttr.PRJ_CHAR_CODE);
			Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
			List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
			String[] s = dataTxt.split(GlobalFinalAttr.SEPARATOR);
			if (xmlfields.size() != s.length) {
				log.error("传入的值于xml的数量不符合");
				return result;
			}
			int i = 0;
			for (Field field : xmlfields) {// 循环字段
				String theValue = (StringUtils.isBlank(s[i]) ? "" : s[i]);
				theValue = theValue.contains("'") ? theValue.replace("'", "''")
						: theValue;
				fields.append(field.getFieldname()).append(",");
				switch (field.getFieldtype()) {
				case 11:
					if (theValue.equals("")) {
						values.append("null,");
					} else {
						values.append(generateTimeToSQLDate(theValue)).append(
								",");
					}
					break;
				case 1:
					values.append("'").append(theValue).append("',");
					break;
				case 3:
					if (StringUtils.isBlank(theValue)) {
						values.append("null ,");
					} else {
						values.append(Integer.parseInt(theValue)).append(",");
					}
					break;
				default:
					values.append("'").append(theValue).append("',");
					break;
				}
				i = i + 1;
			}
			SQzh sqzh = sQzhMapper.getSQzhByPrimarkKey(primaryKey);
			if (sqzh != null) {
				updateQzhByTxt(dataTxt, primaryKey);
			} else {
				fields.append("did,primarykey");
				values.append(maxdid).append(",'").append(primaryKey)
						.append("'");
				SQL = "insert into " + table.getName() + " ("
						+ fields.toString() + ") values ( " + values.toString()
						+ " )";
				execSql(SQL);
				result = "0";
				log.error("插入一条数据成功.addOrUpdateQzhByTxt: " + SQL);
				fields.setLength(0);
				values.setLength(0);
			}
		} catch (Exception e) {
			log.error("插入一条数据错误.addOrUpdateQzhByTxt:", e);
			log.error(SQL);
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加或修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataXml
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addOrUpdateQzhByXml(
			@WebParam(name = "dataXml") String dataXml,
			@WebParam(name = "primaryKey") String primaryKey) {
		String archKey = "";
		String archVal = "";
		String result = "1";
		String SQL = "";
		FDTable fDtable = null;
		List<FDTable> fDTableList = null;
		Map<String, String> map = null;
		StringBuffer fields = new StringBuffer();
		StringBuffer values = new StringBuffer();
		try {
			String xmlPath = GlobalFinalAttr.XML_PATH + "S_QZH.XML";
			File xmlFile = new File(xmlPath);
			if (!xmlFile.exists()) {
				log.error("XML文件不存在");
				return "1";
			}
			Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
			map = convertTable2Map(table);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		if (null != map && null != map.keySet() && map.keySet().size() > 0) {
			try {
				Integer maxdid = getMaxDid("s_qzh");
				fDTableList = sGroupMapper.getFtableList("F_S_QZH");
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
				SQzh sqzh = sQzhMapper.getSQzhByPrimarkKey(primaryKey);
				if (sqzh != null) {
					updateQzhByXml(dataXml, primaryKey);
				} else {
					fields.append("did,primarykey");
					values.append(maxdid).append(",'").append(primaryKey)
							.append("'");
					SQL = "insert into s_qzh (" + fields.toString()
							+ ") values ( " + values.toString() + " )";
					execSql(SQL);
					result = "0";
					log.error("插入一条数据成功.addOrUpdateQzhByXml: " + SQL);
				}
			} catch (Exception e) {
				log.error("插入一条数据错误.addOrUpdateQzhByXml:", e);
				log.error(SQL);
			}
		} else {
			result = "1";
		}
		fields.setLength(0);
		values.setLength(0);
		return result;
	}

	/**
	 * <p>
	 * Title: 增加或修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataJson
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addOrUpdateQzhByJson(
			@WebParam(name = "dataJson") String dataJson,
			@WebParam(name = "primaryKey") String primaryKey) {
		String archKey = "";
		String archVal = "";
		String result = "1";
		FDTable fDtable = null;
		List<FDTable> fDTableList = null;
		Map<String, String> map = null;
		StringBuffer fields = new StringBuffer();
		StringBuffer values = new StringBuffer();
		try {
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(dataJson, Map.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		if (null != map && null != map.keySet() && map.keySet().size() > 0) {
			try {
				Integer maxdid = getMaxDid("s_qzh");
				fDTableList = sGroupMapper.getFtableList("F_S_QZH");
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
				SQzh sqzh = sQzhMapper.getSQzhByPrimarkKey(primaryKey);
				if (sqzh != null) {
					updateQzhByJson(dataJson, primaryKey);
				} else {
					fields.append("did,primarykey");
					values.append(maxdid).append(",'").append(primaryKey)
							.append("'");
					String SQL = "insert into s_qzh (" + fields.toString()
							+ ") values ( " + values.toString() + " )";
					execSql(SQL);
					result = "0";
					log.error("插入一条数据成功.addOrUpdateQzhByJson: " + SQL);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("插入一条数据失败.addOrUpdateQzhByJson: " + e.getMessage());
			}
		} else {
			result = "1";
		}
		fields.setLength(0);
		values.setLength(0);
		return result;

	}

	/**
	 * <p>
	 * Title: 增加全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataTxt
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addQzhByTxt(@WebParam(name = "dataTxt") String dataTxt,
			@WebParam(name = "primaryKey") String primaryKey) {
		Integer maxdid = 0;
		String SQL = "";
		String result = "1";
		String xmlPath = GlobalFinalAttr.XML_PATH + "S_QZH.XML";
		File xmlFile = new File(xmlPath);
		if (!xmlFile.exists()) {
			log.error("XML文件不存在");
			return result;
		}
		try {
			maxdid = getMaxDid("s_qzh");
			StringBuffer fields = new StringBuffer();
			StringBuffer values = new StringBuffer();
			String xmlString = FileUtils.readFileToString(xmlFile,
					GlobalFinalAttr.PRJ_CHAR_CODE);
			Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
			List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
			String[] s = dataTxt.split(GlobalFinalAttr.SEPARATOR);
			if (xmlfields.size() != s.length) {
				log.error("传入的值于xml的数量不符合");
				return result;
			}
			int i = 0;
			for (Field field : xmlfields) {// 循环字段
				String theValue = (StringUtils.isBlank(s[i]) ? "" : s[i]);
				theValue = theValue.contains("'") ? theValue.replace("'", "''")
						: theValue;
				fields.append(field.getFieldname()).append(",");
				switch (field.getFieldtype()) {
				case 11:
					if (theValue.equals("")) {
						values.append("null,");
					} else {
						values.append(generateTimeToSQLDate(theValue)).append(
								",");
					}
					break;
				case 1:
					values.append("'").append(theValue).append("',");
					break;
				case 3:
					if (StringUtils.isBlank(theValue)) {
						values.append("null ,");
					} else {
						values.append(Integer.parseInt(theValue)).append(",");
					}
					break;
				default:
					values.append("'").append(theValue).append("',");
					break;
				}
				i = i + 1;
			}
			try {
				SQzh sqzh = sQzhMapper.getSQzhByPrimarkKey(primaryKey);
				new IsExistDepOrUser().isQzhExist(sqzh);
				fields.append("did,primarykey");
				values.append(maxdid).append(",'").append(primaryKey)
						.append("'");
				SQL = "insert into " + table.getName() + " ("
						+ fields.toString() + ") values ( " + values.toString()
						+ " )";
				execSql(SQL);
				result = "0";
				log.error("插入一条数据成功.addQzhByTxt: " + SQL);
				fields.setLength(0);
				values.setLength(0);
			} catch (ExceptionThrows e) {
				System.out.println(e.getMessage());
				log.error(e.getMessage());
				result = "1";
			}
		} catch (Exception e) {
			log.error("插入一条数据错误.addQzhByTxt:", e);
			log.error(SQL);
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataTxt
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String updateQzhByTxt(@WebParam(name = "dataTxt") String dataTxt,
			@WebParam(name = "primaryKey") String primaryKey) {
		String SQL = "";
		String result = "1";
		String xmlPath = GlobalFinalAttr.XML_PATH + "S_QZH.XML";
		File xmlFile = new File(xmlPath);
		if (!xmlFile.exists()) {
			log.error("XML文件不存在");
			return result;
		}
		try {
			StringBuffer fields = new StringBuffer();
			StringBuffer values = new StringBuffer();
			String xmlString = FileUtils.readFileToString(xmlFile,
					GlobalFinalAttr.PRJ_CHAR_CODE);
			Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
			List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
			String[] s = dataTxt.split(GlobalFinalAttr.SEPARATOR);
			if (xmlfields.size() != s.length) {
				log.error("传入的值于xml的数量不符合");
				return result;
			}
			int i = 0;
			for (Field field : xmlfields) {// 循环字段
				String theValue = (StringUtils.isBlank(s[i]) ? "" : s[i]);
				theValue = theValue.contains("'") ? theValue.replace("'", "''")
						: theValue;
				fields.append(field.getFieldname()).append("=");
				switch (field.getFieldtype()) {
				case 11:
					if (theValue.equals("")) {
						fields.append("null,");
					} else {
						fields.append(generateTimeToSQLDate(theValue)).append(
								",");
					}
					break;
				case 1:
					fields.append("'").append(theValue).append("',");
					break;
				case 3:
					if (StringUtils.isBlank(theValue)) {
						fields.append("null ,");
					} else {
						fields.append(Integer.parseInt(theValue)).append(",");
					}
					break;
				default:
					fields.append("'").append(theValue).append("',");
					break;
				}
				i = i + 1;
			}
			try {
				SQzh sqzh = sQzhMapper.getSQzhByPrimarkKey(primaryKey);
				new IsExistDepOrUser().isQzhNotExist(sqzh);
				SQL = "update " + table.getName() + " set "
						+ fields.toString().substring(0, fields.length() - 1)
						+ " where primarykey = '" + primaryKey + "'";
				execSql(SQL);
				result = "0";
				log.error("更新一条数据成功.updateQzhByTxt: " + SQL);
				fields.setLength(0);
				values.setLength(0);
			} catch (ExceptionThrows e) {
				System.out.println(e.getMessage());
				log.error(e.getMessage());
				result = "1";
			}
		} catch (Exception e) {
			log.error("更新一条数据错误.updateQzhByTxt:", e);
			log.error(SQL);
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 增加全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataXml
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addQzhByXml(@WebParam(name = "dataXml") String dataXml,
			@WebParam(name = "primaryKey") String primaryKey) {
		String archKey = "";
		String archVal = "";
		String result = "1";
		FDTable fDtable = null;
		List<FDTable> fDTableList = null;
		Map<String, String> map = null;
		StringBuffer fields = new StringBuffer();
		StringBuffer values = new StringBuffer();
		try {
			String xmlPath = GlobalFinalAttr.XML_PATH + "S_QZH.XML";
			File xmlFile = new File(xmlPath);
			if (!xmlFile.exists()) {
				log.error("XML文件不存在");
				return "1";
			}
			Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
			map = convertTable2Map(table);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		if (null != map && null != map.keySet() && map.keySet().size() > 0) {
			try {
				Integer maxdid = getMaxDid("s_qzh");
				fDTableList = sGroupMapper.getFtableList("F_S_QZH");
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
					SQzh sqzh = sQzhMapper.getSQzhByPrimarkKey(primaryKey);
					new IsExistDepOrUser().isQzhExist(sqzh);
					fields.append("did,primarykey");
					values.append(maxdid).append(",'").append(primaryKey)
							.append("'");
					String SQL = "insert into s_qzh (" + fields.toString()
							+ ") values ( " + values.toString() + " )";
					execSql(SQL);
					result = "0";
					log.error("插入一条数据成功.addQzhByXml: " + SQL);
				} catch (ExceptionThrows e) {
					System.out.println(e.getMessage());
					log.error(e.getMessage());
					result = "1";
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("插入一条数据失败.addQzhByXml: " + e.getMessage());
			}
		} else {
			result = "1";
		}
		fields.setLength(0);
		values.setLength(0);
		return result;
	}

	/**
	 * <p>
	 * Title: 修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataXml
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String updateQzhByXml(@WebParam(name = "dataXml") String dataXml,
			@WebParam(name = "primaryKey") String primaryKey) {
		String archKey = "";
		String archVal = "";
		String result = "1";
		FDTable fDtable = null;
		List<FDTable> fDTableList = null;
		Map<String, String> map = null;
		StringBuffer fields = new StringBuffer();
		StringBuffer values = new StringBuffer();
		try {
			String xmlPath = GlobalFinalAttr.XML_PATH + "S_QZH.XML";
			File xmlFile = new File(xmlPath);
			if (!xmlFile.exists()) {
				log.error("XML文件不存在");
				return "1";
			}
			Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
			map = convertTable2Map(table);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		if (null != map && null != map.keySet() && map.keySet().size() > 0) {
			try {
				fDTableList = sGroupMapper.getFtableList("F_S_QZH");
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
					SQzh sqzh = sQzhMapper.getSQzhByPrimarkKey(primaryKey);
					new IsExistDepOrUser().isQzhNotExist(sqzh);
					String SQL = "update s_qzh set "
							+ fields.toString().substring(0,
									fields.length() - 1)
							+ " where primarykey = '" + primaryKey + "'";
					System.out.println(SQL);
					execSql(SQL);
					result = "0";
					log.error("更新一条数据成功.updateQzhByXml: " + SQL);
				} catch (ExceptionThrows e) {
					System.out.println(e.getMessage());
					log.error(e.getMessage());
					result = "1";
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("更新一条数据失败.updateQzhByXml: " + e.getMessage());
			}
		} else {
			result = "1";
		}
		fields.setLength(0);
		values.setLength(0);
		return result;
	}

	/**
	 * <p>
	 * Title: 增加全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataJson
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String addQzhByJson(@WebParam(name = "dataJson") String dataJson,
			@WebParam(name = "primaryKey") String primaryKey) {
		String archKey = "";
		String archVal = "";
		String result = "1";
		FDTable fDtable = null;
		List<FDTable> fDTableList = null;
		Map<String, String> map = null;
		StringBuffer fields = new StringBuffer();
		StringBuffer values = new StringBuffer();
		try {
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(dataJson, Map.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		if (null != map && null != map.keySet() && map.keySet().size() > 0) {
			try {
				Integer maxdid = getMaxDid("s_qzh");
				fDTableList = sGroupMapper.getFtableList("F_S_QZH");
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
					SQzh sqzh = sQzhMapper.getSQzhByPrimarkKey(primaryKey);
					new IsExistDepOrUser().isQzhExist(sqzh);
					fields.append("did,primarykey");
					values.append(maxdid).append(",'").append(primaryKey)
							.append("'");
					String SQL = "insert into s_qzh (" + fields.toString()
							+ ") values ( " + values.toString() + " )";
					execSql(SQL);
					result = "0";
					log.error("插入一条数据成功.addQzhByJson: " + SQL);
				} catch (ExceptionThrows e) {
					System.out.println(e.getMessage());
					log.error(e.getMessage());
					result = "1";
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("插入一条数据失败.addQzhByJson: " + e.getMessage());
			}
		} else {
			result = "1";
		}
		fields.setLength(0);
		values.setLength(0);
		return result;
	}

	/**
	 * <p>
	 * Title: 修改全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param dataJson
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String updateQzhByJson(@WebParam(name = "dataJson") String dataJson,
			@WebParam(name = "primaryKey") String primaryKey) {
		String archKey = "";
		String archVal = "";
		String result = "1";
		FDTable fDtable = null;
		List<FDTable> fDTableList = null;
		Map<String, String> map = null;
		StringBuffer fields = new StringBuffer();
		StringBuffer values = new StringBuffer();
		try {
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(dataJson, Map.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		if (null != map && null != map.keySet() && map.keySet().size() > 0) {
			try {
				fDTableList = sGroupMapper.getFtableList("F_S_QZH");
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
					SQzh sqzh = sQzhMapper.getSQzhByPrimarkKey(primaryKey);
					new IsExistDepOrUser().isQzhNotExist(sqzh);
					String SQL = "update s_qzh set "
							+ fields.toString().substring(0,
									fields.length() - 1)
							+ " where primarykey = '" + primaryKey + "'";
					System.out.println(SQL);
					execSql(SQL);
					result = "0";
					log.error("更新一条数据成功.updateQzhByJson: " + SQL);
				} catch (ExceptionThrows e) {
					System.out.println(e.getMessage());
					log.error(e.getMessage());
					result = "1";
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("更新一条数据失败.updateQzhByJson: " + e.getMessage());
			}
		} else {
			result = "1";
		}
		fields.setLength(0);
		values.setLength(0);
		return result;
	}

	/**
	 * <p>
	 * Title: 删除用户数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String delUserByKey(@WebParam(name = "primaryKey") String primaryKey) {
		String result = "0";
		try {
			SUser user = sUserMapper.getUserByEsbid(primaryKey);
			new IsExistDepOrUser().isUserNotExist(user);
			sUserMapper.delUserByEsbid(primaryKey);
			log.error("删除一个用户:" + primaryKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 删除部门数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String delDeptByKey(@WebParam(name = "primaryKey") String primaryKey) {
		String result = "0";
		try {
			SGroup sg = sGroupMapper.getGroupByGfzj(primaryKey);
			new IsExistDepOrUser().isDepNotExist(sg);
			jdbcDao.excute("DELETE S_GROUP  WHERE GFZJ='" + primaryKey + "'");
			log.error("删除一个部门:" + primaryKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 删除全宗数据 返回; 0：成功！ 1：不成功！[失败原因]
	 * </p>
	 * 
	 * @param primaryKey
	 * @return
	 */
	@WebMethod
	public String delQzhByKey(@WebParam(name = "primaryKey") String primaryKey) {
		String result = "0";
		try {
			SQzh sqzh = sQzhMapper.getSQzhByPrimarkKey(primaryKey);
			new IsExistDepOrUser().isQzhNotExist(sqzh);
			jdbcDao.excute("DELETE S_QZH  WHERE primarykey='" + primaryKey
					+ "'");
			log.error("删除一个全宗:" + primaryKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			result = "1[" + e.getMessage() + "]";
		}
		return result;
	}

	/**
	 * Table2Map
	 * 
	 * @param table
	 * @return
	 */
	private Map<String, String> convertTable2Map(Table table) {
		Map<String, String> map = new HashMap<String, String>();
		List<Field> xmlfields = table.getFields();
		for (Field field : xmlfields) {
			String fieldname = field.getFieldname();
			String theValue = (StringUtils.isBlank(field.getThevalue()) ? ""
					: field.getThevalue());
			theValue = theValue.contains("'") ? theValue.replace("'", "''")
					: theValue;
			map.put(fieldname, theValue);
		}
		return map;
	}

	private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());

}
