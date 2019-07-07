package com.hwxt.junit;

import org.apache.commons.lang.StringUtils;


public class ParseXmlTest {
    public static void main(String[] args) throws Exception {
//		File file = new File("C:/ML_OA_DATA.xml");
//		String xmlStr = FileUtils.readFileToString(file , "UTF-8");
//		Reader reader = new StringReader(xmlStr);
//        Unmarshaller unmarshaller =  JAXBContext.newInstance(MLXmlResult.class).createUnmarshaller();     
//        MLXmlResult result = (MLXmlResult) unmarshaller.unmarshal(reader); 
//        List<com.hwxt.pojo.MLXmlResult.DocInfo.Field> list = result.getDocInfo().getField();
//        List<com.hwxt.pojo.MLXmlResult.Attachments.Attachment> eFileList = result.getAttachments().getAttachment();
//        for (com.hwxt.pojo.MLXmlResult.Attachments.Attachment efile : eFileList) {
//        	System.out.println(efile.getFtpdir());
//		}
////        for (Result.DocInfo.Field field : list) {
////        	System.out.println(field.getColName()+":"+field.getValue());
////		}
//  
//        Marshaller mashaller =  JAXBContext.newInstance(MLXmlResult.class).createMarshaller();
//        MLObjectFactory factory = new MLObjectFactory();
//        MLXmlResult toxmlResult = factory.createResult();
//        //可选 开始
//        com.hwxt.pojo.MLXmlResult.DocInfo.Field theField = factory.createResultDocInfoField();
//        theField.setColName("中文桑德菲杰三闾大夫");
//        com.hwxt.pojo.MLXmlResult.DocInfo dii = factory.createResultDocInfo();
//        dii.getField().add(theField);
//        toxmlResult.setDocInfo(dii);
//        //可选 结束
//        mashaller.marshal(toxmlResult, new File("c:/outPut.xml"));
//		
//		service.sendCommonMessageByUserCode(nSenderPlatID, nSenderUserCode, sReceiverPlatUserIds,
//				strContent, nOnlineOnly, nReserveDays, strFromApp, strAppCode, strFromUserName, strCustomInfo)

        String a = "null&;'gname'&;'bz'&;";
        String[] aa = a.split("&;");
        for (String aaa : aa) {
            System.out.println(aaa);
            System.out.println(StringUtils.isBlank(aaa));
        }

    }
}
