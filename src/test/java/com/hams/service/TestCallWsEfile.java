package com.hams.service;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


public class TestCallWsEfile {
    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
        ArcDataWsSingle ws = new SingleServiceImplService().getArcDataWsSinglePort();
        //====================================txt插入====================================
        System.out.println(ws.fileReciveTxt("E_FILE26.xml", "1&;测试&;标题&;doc&;data&;/safd/asdf/asdf/asdf/&;3232&;", "qink"));
        //====================================json插入=============================================
        StringWriter sw = new StringWriter();
        Map<String, String> map = new HashMap<String, String>();
        map.put("PID", "12");
        map.put("EFILENAME", "这个名字最好不要用中文");
        map.put("TITLE", "实际的电子文件名称");
        map.put("EXT", "DOCX");
        map.put("PZM", "data");//从实施顾问那里获得
        map.put("PATHNAME", "/asdf/asdfasdf/asdf/asdf/");//从实施顾问那里获得
        map.put("FILESIZE", "25563");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(sw, map);

        System.out.println(ws.fileReciveJson("E_FILE26.xml", sw.toString().toString(), "qink"));
        sw.close();
//		//====================================xml====================================
        String xml = FileUtils.readFileToString(new File("D:/E_FILE26.XML"), "UTF-8");
        System.out.println(ws.fileReciveXml("E_FILE26.xml", xml, "qink"));
    }
}
//<field notnull="false" length="0" fieldtype="3" chname="文件ID" fieldname="PID" thevalue="1"/>
//<field notnull="false" length="254" fieldtype="1" chname="电子文件名" fieldname="EFILENAME" thevalue="just a string"/>
//<field notnull="false" length="254" fieldtype="1" chname="标题" fieldname="TITLE" thevalue="just a string"/>
//<field notnull="false" length="8" fieldtype="1" chname="电子文件扩展名" fieldname="EXT" thevalue="just a string"/>
//<field notnull="false" length="64" fieldtype="1" chname="配置名" fieldname="PZM" thevalue="just a string"/>
//<field notnull="false" length="254" fieldtype="1" chname="电子文件路径名" fieldname="PATHNAME" thevalue="just a string"/>
//<field notnull="false" length="4" fieldtype="3" chname="电子文件大小" fieldname="FILESIZE" thevalue="1"/>