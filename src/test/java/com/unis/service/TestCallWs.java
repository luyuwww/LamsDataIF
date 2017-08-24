package com.unis.service;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public class TestCallWs {
    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
        ArcDataWsSingle ws = new SingleServiceImplService().getArcDataWsSinglePort();
        //txt插入
        System.out.println(ws.fileReciveTxt("D_FILE8.xml", "1&;-1&;1&;这'sdfasdfasdfasdfasdf'斯蒂芬&;4&;这是个提名&;", "aa"));
        //json插入
//		StringWriter sw = new StringWriter();
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("ATTACHED", "1");
//		map.put("PID", "-1");
//		map.put("KEYWORD", "这'sdfasdfasdfasdfasdf'斯蒂芬");
//		map.put("JH", "4");
//		map.put("TITLE", "这是个提名");
//		map.put("F13", "1986-06-19");
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(sw, map);
////		
//		System.out.println(ws.fileReciveJson("D_FILE8.xml", sw.toString().toString(), "jiamy"));
//		sw.close();
////		//xml
//		String xml = FileUtils.readFileToString(new File("d:/D_FILE8.XML") , "UTF-8");
//		System.out.println(ws.fileReciveXml("D_FILE8.xml", xml, "qink"));
    }
}

//        <field notnull="false" length="0" fieldtype="3" chname="原文标志" fieldname="ATTACHED" thevalue="1"/>
//        <field notnull="true" length="0" fieldtype="3" chname="父ID" fieldname="PID" thevalue="1"/>
//        <field notnull="false" length="64" fieldtype="1" chname="档号" fieldname="KEYWORD" thevalue="just a string"/>
//        <field notnull="false" length="0" fieldtype="3" chname="件号" fieldname="JH" thevalue="1"/>
//        <field notnull="false" length="254" fieldtype="1" chname="题名" fieldname="TITLE" thevalue="just a string"/>
//        <field notnull="false" length="50" fieldtype="11" chname="日期测试" fieldname="F13" thevalue="1986-06-19 hh:mm:ss.S"/>