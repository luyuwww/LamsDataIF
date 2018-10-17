package com.bwzk.junit;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;


public class ParseXmlTest {
    public static void main(String[] args) throws Exception {
//		File file = new File("C:/ML_OA_DATA.xml");
//		String xmlStr = FileUtils.readFileToString(file , "UTF-8");
//		Reader reader = new StringReader(xmlStr);
//        Unmarshaller unmarshaller =  JAXBContext.newInstance(MLXmlResult.class).createUnmarshaller();     
//        MLXmlResult result = (MLXmlResult) unmarshaller.unmarshal(reader); 
//        List<com.bwzk.pojo.MLXmlResult.DocInfo.Field> list = result.getDocInfo().getField();
//        List<com.bwzk.pojo.MLXmlResult.Attachments.Attachment> eFileList = result.getAttachments().getAttachment();
//        for (com.bwzk.pojo.MLXmlResult.Attachments.Attachment efile : eFileList) {
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
//        com.bwzk.pojo.MLXmlResult.DocInfo.Field theField = factory.createResultDocInfoField();
//        theField.setColName("中文桑德菲杰三闾大夫");
//        com.bwzk.pojo.MLXmlResult.DocInfo dii = factory.createResultDocInfo();
//        dii.getField().add(theField);
//        toxmlResult.setDocInfo(dii);
//        //可选 结束
//        mashaller.marshal(toxmlResult, new File("c:/outPut.xml"));
//		
//		service.sendCommonMessageByUserCode(nSenderPlatID, nSenderUserCode, sReceiverPlatUserIds,
//				strContent, nOnlineOnly, nReserveDays, strFromApp, strAppCode, strFromUserName, strCustomInfo)


        String ftpName = "ftp://192.168.188.219/29528_预算..pdf";
        System.out.println(FilenameUtils.getName(ftpName));
        System.out.println(FilenameUtils.getBaseName(ftpName));
        System.out.println(FilenameUtils.getExtension(ftpName));
        System.out.println(FilenameUtils.getFullPathNoEndSeparator(ftpName));
        System.out.println(ftpName.substring(ftpName.indexOf("/")));

        System.out.println(new DateTime().getYear());
        System.out.println(new DateTime().getMonthOfYear());
        System.out.println(new DateTime().getDayOfMonth());
    }
}
