package com.bwzk.junit;/**
 * Created by DaMo on 2018-03-22.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bwzk.pojo.jsonbean.ArchivesAddBean;
import com.bwzk.pojo.jsonbean.DelItem;
import com.bwzk.pojo.jsonbean.ITEM;
import com.bwzk.pojo.jsonbean.MnsMessageDto;
import com.bwzk.service.i.SyncService;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author DaMo
 * @UPDATE 2018-03-22-15:10
 * @since 2018-03-22-15:10
 */


public class TestJsonBean {

	@Autowired
	private SyncService syncService;
	
    @Test
    public void json2Bean() throws IOException {
//        //  File del = new File("D:\\workspace\\idea\\LamsDataIF\\docu\\del2Da_single.json");
//        File add = new File("D:\\data\\add2Da_single.json");
//        String addStr = FileUtils.readFileToString(add);
//        System.out.println(addStr);
//        Map<String , String> mapObj = JSON.parseObject(addStr, Map.class);
//        MnsMessageDto<JSONObject> itemBean = JSON.parseObject(mapObj.get("messageBody"), MnsMessageDto.class);
//        System.out.println(itemBean.getData().toString());
//        ITEM item = JSON.parseObject(itemBean.getData().toString() , ITEM.class);
//        System.out.println(item.getItem().getAttr());
//
	
	
	
	
	
/////////////////////////////////////////////////////////////////////////////	
////        ITEM tuItem = JSON.parseObject(itemBean.getData().toString() , ITEM.class);


//        //String delStr = FileUtils.readFileToString(del);
//        JSONObject jsStr = JSONObject.parseObject(addStr);
//        JSONObject jsStr1=jsStr.getJSONObject("messageBody");
//        JSONObject jsStr2=jsStr1.getJSONObject("data");
//        JSONObject jsStr3=jsStr2.getJSONObject("item");
//        String jsStr4=jsStr3.getString("title");
//        System.out.println(jsStr4.toString());
//
//        AddItem addItem =JSON.parseObject(jsStr3.toJSONString(), AddItem.class);
//        System.out.println(addItem.getITEM().toString());

	////////////////////////////////////////////////////////////////////////
//	    File del = new File("D:\\data\\delete.json");
//	    String delStr = FileUtils.readFileToString(del);
//	    Map<String , String> mapObj = JSON.parseObject(delStr, Map.class);
//         MnsMessageDto<JSONObject> itemBean = JSON.parseObject(mapObj.get("messageBody"), MnsMessageDto.class);
//    
//         DelItem dia = JSON.parseObject(itemBean.getData().toString() , DelItem.class);
//          
//         System.out.println(dia.getDIRID());
         

    	
    	////////////////////////////////////
    	
    	
    	
    	syncService.lambda2ArchiveADD();
    	
    }






//    @Test
//    public void bean2Json() throws IOException {
//        AddItem addItem = new AddItem("2", "3",    "custid",    "CUSTCARD",    "CUSTNAME",
//                "FILETYPE",   "/sd/f/sdf/s/dfsd",   "sdfsdfdsf",  "doc",  "data" ,
//                1 , "sdfsdfmsmdfMD5" , "zxy" , "2018年3月22日17:12:47" ,12515161);
//
//        String add = JSON.toJSONString(addItem);
//        System.out.println(add);
//
//        DelItem delItem = new DelItem("2","21","/s/d/f/e/f/sx/dv/w/er" , "zxy","2018-3-22 17:17:22");
//        System.out.println(JSON.toJSONString(delItem));
//
//
//
//
//    }
}
