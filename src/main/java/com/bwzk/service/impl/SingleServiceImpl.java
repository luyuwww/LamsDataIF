package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.i.da.SUserMapper;
import com.bwzk.pojo.jaxb.Field;
import com.bwzk.pojo.jaxb.Table;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.SingleService;
import com.bwzk.util.GlobalFinalAttr;
import com.bwzk.util.XmlObjUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service("arcDataService")
@WebService(name = "ArcDataWsSingle", targetNamespace = "http://service.unis.com/")
public class SingleServiceImpl extends BaseService implements SingleService {

    @WebMethod
    public Integer fileReciveTxt(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataTxt") String dataTxt, @WebParam(name = "gdrCode") String gdrCode) {
        Integer maxdid = 0;
        String SQL = "";
        String bmid = "";
        String qzh = "";
        Integer resultInteger = -1;
        String xmlPath = xmlName.toUpperCase().contains(".XML") ?
                GlobalFinalAttr.XML_PATH + xmlName : GlobalFinalAttr.XML_PATH + xmlName + ".XML";
        File xmlFile = new File(xmlPath);
        if (!xmlFile.exists()) {
            log.error("请检查传入的xmlName");
            return resultInteger;
        }
        if (StringUtils.isBlank(gdrCode)) {
            log.error("请检查传入的gdrCode 为空");
            return resultInteger;
        } else {
            bmid = getBmidByuserCode(gdrCode);
            qzh = getQzhByUserCode(gdrCode);
        }
        try {
            maxdid = getMaxDid(xmlName.toUpperCase().replace(".XML", ""));
            StringBuffer fields = new StringBuffer();
            StringBuffer values = new StringBuffer();
            String xmlString = FileUtils.readFileToString(xmlFile, GlobalFinalAttr.PRJ_CHAR_CODE);
            Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
            List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
            String[] s = dataTxt.split(GlobalFinalAttr.SEPARATOR);
            if (xmlfields.size() != s.length) {
                log.error("传入的值于xml的数量不符合");
                return resultInteger;
            }
            int i = 0;
            for (Field field : xmlfields) {//循环字段
                String theValue = (StringUtils.isBlank(s[i]) ? "" : s[i]);
                theValue = theValue.contains("'") ? theValue.replace("'", "''") : theValue;
                fields.append(field.getFieldname()).append(",");
                switch (field.getFieldtype()) {
                    case 11:
                        if (theValue.equals("")) {
                            values.append(getSysdate() + ",");
                        } else {
                            values.append(generateTimeToSQLDate(theValue)).append(",");
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
            if (xmlName.toUpperCase().contains("E_FILE")) {
                fields.append("status, attr,attrex,creator,did ");
                values.append("0,").append(attr).append(",").append(attrex).append(",'").append(gdrCode);
                values.append("',").append(maxdid);
            } else {
                fields.append("status, attr,attrex,qzh,bmid,creator,did ");
                values.append("0,").append(attr).append(",").append(attrex).append(",'");
                values.append(qzh).append("','").append(bmid).append("','").append(gdrCode);
                values.append("',").append(maxdid);
            }

            SQL = "insert into " + table.getName() + " (" + fields.toString()
                    + ") values ( " + values.toString() + " )";
            execSql(SQL);
            resultInteger = maxdid;
            log.error("插入一条数据成功.fileReciveTxt: " + SQL);
            fields.setLength(0);
            values.setLength(0);
        } catch (Exception e) {
            log.error("插入一条数据错误.fileReciveTxt:", e);
            log.error(SQL);
        } finally {
        }
        return resultInteger;
    }

    @WebMethod
    public Integer fileReciveXml(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataXml") String dataXml, @WebParam(name = "gdrCode") String gdrCode) {
        Integer maxdid = 0;
        String SQL = "";
        String bmid = "";
        String qzh = "";
        Integer resultInteger = -1;
        String xmlPath = xmlName.toUpperCase().contains(".XML") ?
                GlobalFinalAttr.XML_PATH + xmlName : GlobalFinalAttr.XML_PATH + xmlName + ".XML";
        File xmlFile = new File(xmlPath);
        if (!xmlFile.exists()) {
            log.error("请检查传入的xmlName");
            return resultInteger;
        }
        if (StringUtils.isBlank(gdrCode)) {
            log.error("请检查传入的gdrCode 为空");
            return resultInteger;
        } else {
            bmid = getBmidByuserCode(gdrCode);
            qzh = getQzhByUserCode(gdrCode);
        }
        try {
            maxdid = getMaxDid(xmlName.toUpperCase().replace(".XML", ""));
            StringBuffer fields = new StringBuffer();
            StringBuffer values = new StringBuffer();
            Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
            List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
            int i = 0;
            for (Field field : xmlfields) {//循环字段
                String theValue = (StringUtils.isBlank(field.getThevalue()) ? "" : field.getThevalue());
                theValue = theValue.contains("'") ? theValue.replace("'", "''") : theValue;
                fields.append(field.getFieldname()).append(",");
                switch (field.getFieldtype()) {
                    case 11:
                        if (theValue.equals("")) {
                            values.append(getSysdate() + ",");
                        } else {
                            values.append(generateTimeToSQLDate(theValue)).append(",");
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
            if (xmlName.toUpperCase().contains("E_FILE")) {
                fields.append("status, attr,attrex,creator,did ");
                values.append("0,").append(attr).append(",").append(attrex).append(",'").append(gdrCode);
                values.append("',").append(maxdid);
            } else {
                fields.append("status, attr,attrex,qzh,bmid,creator,did ");
                values.append("0,").append(attr).append(",").append(attrex).append(",'");
                values.append(qzh).append("','").append(bmid).append("','").append(gdrCode);
                values.append("',").append(maxdid);
            }

            SQL = "insert into " + table.getName() + " (" + fields.toString()
                    + ") values ( " + values.toString() + " )";
            execSql(SQL);
            resultInteger = maxdid;
            log.error("插入一条数据成功.fileReciveTxt: " + SQL);
            fields.setLength(0);
            values.setLength(0);
        } catch (Exception e) {
            log.error("插入一条数据错误.fileReciveTxt:", e);
            log.error(SQL);
        } finally {
        }
        return resultInteger;
    }

    @WebMethod
    public Integer fileReciveJson(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataJson") String dataJson, @WebParam(name = "gdrCode") String gdrCode) {
        Integer maxdid = 0;
        String SQL = "";
        String bmid = "";
        String qzh = "";
        Integer resultInteger = -1;
        String xmlPath = xmlName.toUpperCase().contains(".XML") ?
                GlobalFinalAttr.XML_PATH + xmlName : GlobalFinalAttr.XML_PATH + xmlName + ".XML";
        File xmlFile = new File(xmlPath);
        if (!xmlFile.exists()) {
            log.error("请检查传入的xmlName");
            return resultInteger;
        }
        if (StringUtils.isBlank(gdrCode)) {
            log.error("请检查传入的gdrCode 为空");
            return resultInteger;
        } else {
            bmid = getBmidByuserCode(gdrCode);
            qzh = getQzhByUserCode(gdrCode);
        }
        try {
            maxdid = getMaxDid(xmlName.toUpperCase().replace(".XML", ""));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> vars = null;
            vars = mapper.readValue(dataJson, Map.class);
            StringBuffer fields = new StringBuffer();
            StringBuffer values = new StringBuffer();
            String xmlString = FileUtils.readFileToString(xmlFile, GlobalFinalAttr.PRJ_CHAR_CODE);
            Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
            List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
            if (xmlfields.size() != vars.size()) {
                log.error("传入的值于xml的数量不符合");
                return resultInteger;
            }
            int i = 0;
            for (Field field : xmlfields) {//循环字段
                String theValue = (vars.get(field.getFieldname()) == null ? "" : vars.get(field.getFieldname()).toString());
                theValue = theValue.contains("'") ? theValue.replace("'", "''") : theValue;
                fields.append(field.getFieldname()).append(",");
                switch (field.getFieldtype()) {
                    case 11:
                        if (theValue.equals("")) {
                            values.append(getSysdate() + ",");
                        } else {
                            values.append(generateTimeToSQLDate(theValue)).append(",");
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
            if (xmlName.toUpperCase().contains("E_FILE")) {
                fields.append("status, attr,attrex,creator,did ");
                values.append("0,").append(attr).append(",").append(attrex).append(",'").append(gdrCode);
                values.append("',").append(maxdid);
            } else {
                fields.append("status, attr,attrex,qzh,bmid,creator,did ");
                values.append("0,").append(attr).append(",").append(attrex).append(",'");
                values.append(qzh).append("','").append(bmid).append("','").append(gdrCode);
                values.append("',").append(maxdid);
            }

            SQL = "insert into " + table.getName() + " (" + fields.toString()
                    + ") values ( " + values.toString() + " )";
            execSql(SQL);
            resultInteger = maxdid;
            log.error("插入一条数据成功.fileReciveTxt: " + SQL);
            fields.setLength(0);
            values.setLength(0);
        } catch (Exception e) {
            log.error("插入一条数据错误.fileReciveTxt:", e);
            log.error(SQL);
        } finally {
        }
        return resultInteger;
    }

    @Autowired
    private SUserMapper sUserMapper;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}