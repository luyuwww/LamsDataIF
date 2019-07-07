//package com.hwxt.junit;
//
//import java.io.StringWriter;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.xml.namespace.QName;
//
//import org.apache.commons.io.FileUtils;
//import org.codehaus.jackson.map.ObjectMapper;
//
//import cn.lams.BaseDataServiceImplService;
//import cn.lams.BaseDataWS;
//
//public class TestData {
//
//	public static void main(String[] args) {
//		try {
//			URL url = new URL("http://localhost/LamsDataIF/cxf/BaseDataWS?wsdl");
//			QName qname = new QName("http://service.lams.cn/", "BaseDataServiceImplService");
//			BaseDataWS bdsis = new BaseDataServiceImplService(url, qname)
//					.getBaseDataWSPort();
//			// bdsis.addUserByTxt("USERCODE4&;USERCODE4&; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &;",
//			// "12312", "122332");
//			// bdsis.delUserByTxt("12233");
//			// #bdsis.addOrgByTxt("&;gname&;'b'&;", "11", "123", "123");
//			// bdsis.modifyOrgByTxt("&;gname&;'b'&;", "11");
//			// bdsis.delOrgByTxt("11");
//			// StringWriter sw = new StringWriter();
//			// Map<String, String> map = new HashMap<String, String>();
//			// map.put("USERCODE", "444");
//			// map.put("USERNAME", "444");
//			// ObjectMapper mapper = new ObjectMapper();
//			// try {
//			// mapper.writeValue(sw, map);
//			// bdsis.addUserByJson(sw.toString().toString(), "999", "999");
//			// } catch (JsonGenerationException e) {
//			// // TODO Auto-generated catch block
//			// e.printStackTrace();
//			// } catch (JsonMappingException e) {
//			// // TODO Auto-generated catch block
//			// e.printStackTrace();
//			// } catch (IOException e) {
//			// // TODO Auto-generated catch block
//			// e.printStackTrace();
//			// }
//			
//			
//			
//			
//			// bdsis.addUserByTxt("USERCODE8&;USERCODE8&; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &;",
//			// "111", "111");
//			// bdsis.addDeptByTxt("&;gname&;'b'&;", "111", "111", "111");
//			// bdsis.addQzhByTxt("123&;11&;143&;", "333");
//			// bdsis.addOrUpdateDeptByTxt("&;gname&;'b'&;", "1111", "1111",
//			// "1111");
//			// bdsis.addOrUpdateUserByTxt("USERCODE8&;USERCODE8&; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &; &;",
//			// "1112", "1112");
//			// bdsis.addOrUpdateQzhByTxt("123&;11&;143&;", "33333");
//			// String xml = FileUtils.readFileToString(new File(
//			// "C:/XML/S_USER.XML"), "UTF-8");
//			// bdsis.addUserByXml(xml, "3333", "3333");
//			// String xml = FileUtils.readFileToString(new File(
//			// "C:/XML/S_GROUP.XML"), "UTF-8");
//			// bdsis.addDeptByXml(xml, "3333", "3333", "3333");
////			 String xml = FileUtils.readFileToString(new File(
////			 "C:/XML/S_QZH.XML"), "UTF-8");
////			 bdsis.addQzhByXml(xml, "3333");
//			// String xml = FileUtils.readFileToString(new File(
//			// "C:/XML/S_USER.XML"), "UTF-8");
//			// bdsis.addOrUpdateUserByXml(xml, "33331", "33331");
//			// String xml = FileUtils.readFileToString(new File(
//			// "C:/XML/S_GROUP.XML"), "UTF-8");
//			// bdsis.addOrUpdateDeptByXml(xml, "33332", "33332", "33332");
//			// String xml = FileUtils.readFileToString(new File(
//			// "C:/XML/S_QZH.XML"), "UTF-8");
//			// bdsis.addOrUpdateQzhByXml(xml, "33332");
////			StringWriter sw = new StringWriter();
////			Map<String, String> map = new HashMap<String, String>();
////			map.put("USERCODE", "4444");
////			map.put("USERNAME", "4444");
////			ObjectMapper mapper = new ObjectMapper();
////			mapper.writeValue(sw, map);
////			bdsis.addUserByJson(sw.toString().toString(), "4444", "4444");
////			StringWriter sw = new StringWriter();
////			Map<String, String> map = new HashMap<String, String>();
////			map.put("BZ", "4444");
////			map.put("GNAME", "4444");
////			ObjectMapper mapper = new ObjectMapper();
////			mapper.writeValue(sw, map);
////			bdsis.addDeptByJson(sw.toString().toString(), "4444", "4444", "4444");
////			StringWriter sw = new StringWriter();
////			Map<String, String> map = new HashMap<String, String>();
////			map.put("QZH", "4444");
////			map.put("QZMC", "4444");
////			ObjectMapper mapper = new ObjectMapper();
////			mapper.writeValue(sw, map);
////			bdsis.addQzhByJson(sw.toString().toString(), "123321");
////			StringWriter sw = new StringWriter();
////			Map<String, String> map = new HashMap<String, String>();
////			map.put("QZH", "44441");
////			map.put("QZMC", "44441");
////			ObjectMapper mapper = new ObjectMapper();
////			mapper.writeValue(sw, map);
////			bdsis.addOrUpdateQzhByJson(sw.toString().toString(), "1233211");
////			StringWriter sw = new StringWriter();
////			Map<String, String> map = new HashMap<String, String>();
////			map.put("BZ", "44441");
////			map.put("GNAME", "44441");
////			ObjectMapper mapper = new ObjectMapper();
////			mapper.writeValue(sw, map);
////			bdsis.addOrUpdateDeptByJson(sw.toString().toString(), "4444", "4444", "4444");
////			StringWriter sw = new StringWriter();
////			Map<String, String> map = new HashMap<String, String>();
////			map.put("USERCODE", "44441");
////			map.put("USERNAME", "44441");
////			ObjectMapper mapper = new ObjectMapper();
////			mapper.writeValue(sw, map);
////			bdsis.addOrUpdateUserByJson(sw.toString().toString(), "444412", "44442");
////			bdsis.delUserByKey("44424");
////			bdsis.delDeptByKey("123123");
////			bdsis.delQzhByKey("123321");
//			System.out.println(bdsis.delDeptByKey("123123"));
////			bdsis.addQzhByTxt("12&;0&;22&;", "2124345");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
//	}
//}
