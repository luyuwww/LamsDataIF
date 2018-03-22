package com.bwzk.junit;/**
 * Created by DaMo on 2018-03-22.
 */

import com.alibaba.fastjson.JSON;
import com.bwzk.pojo.jsonbean.AddItem;
import com.bwzk.pojo.jsonbean.DelItem;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author DaMo
 * @UPDATE 2018-03-22-15:10
 * @since 2018-03-22-15:10
 */
public class TestJsonBean {

    @Test
    public void json2Bean() throws IOException {
        File del = new File("D:\\workspace\\idea\\LamsDataIF\\docu\\del2Da_single.json");
        File add = new File("D:\\workspace\\idea\\LamsDataIF\\docu\\add2Da_single.json");
        String addStr = FileUtils.readFileToString(add);
        String delStr = FileUtils.readFileToString(del);
        AddItem addItem =JSON.parseObject(addStr, AddItem.class);
        System.out.println(addItem.getITEM().getEFILENAME());

        DelItem di = JSON.parseObject(delStr , DelItem.class);
        System.out.println(di.getDELITEM().getEFILENAME());


    }


    @Test
    public void bean2Json() throws IOException {
        AddItem addItem = new AddItem("2", "3",    "custid",    "CUSTCARD",    "CUSTNAME",
                "FILETYPE",   "/sd/f/sdf/s/dfsd",   "sdfsdfdsf",  "doc",  "data" ,
                1 , "sdfsdfmsmdfMD5" , "zxy" , "2018年3月22日17:12:47" ,12515161);

        String add = JSON.toJSONString(addItem);
        System.out.println(add);

        DelItem delItem = new DelItem("2","21","/s/d/f/e/f/sx/dv/w/er" , "zxy","2018-3-22 17:17:22");
        System.out.println(JSON.toJSONString(delItem));




    }
}
