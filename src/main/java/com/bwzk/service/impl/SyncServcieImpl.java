package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.bwzk.dao.JdbcDao;
import com.bwzk.dao.i.SGroupMapper;
import com.bwzk.dao.i.SUserMapper;
import com.bwzk.dao.i.SUserroleMapper;
import com.bwzk.page.PageContext;
import com.bwzk.pojo.EFile;
import com.bwzk.pojo.jsonbean.DelItem;
import com.bwzk.pojo.jsonbean.ITEM;
import com.bwzk.pojo.jsonbean.MnsMessageDto;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.SyncService;
import com.bwzk.util.DateUtil;
import com.bwzk.util.GlobalFinalAttr;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("syncService")
public class SyncServcieImpl extends BaseService implements SyncService {

    public String archive2lambdaADD() {
        Integer eNum = 0;

        String[] libCodesArray = syncLibcodes.split("[,]");
        for (String libcode : libCodesArray) {

            List<EFile> efieList = listNoSyncAddEfile50("E_FILE" + libcode);
            CloudAccount account = new CloudAccount(this.ak, this.sk, this.endPoint);
            MNSClient client = account.getMNSClient();
            for (EFile eFile : efieList) {
                Map<String, Object> dFile = queryForMap("SELECT DID,PID,TITLE,DIRID,CUSTID,FILETYPE,CUSTCARD" +
                        " FROM D_FILE" + libcode + " WHERE STATUS=0 AND DID=" + eFile.getPid());

                if (null != dFile && StringUtils.isNotBlank(MapUtils.getString(dFile, "PID"))) {
                    String DIRID = MapUtils.getString(dFile, "DIRID");
                    String TITLE = MapUtils.getString(dFile, "TITLE");
                    String CUSTID = MapUtils.getString(dFile, "CUSTID");
                    String FILETYPE = MapUtils.getString(dFile, "FILETYPE");
                    String CUSTCARD = MapUtils.getString(dFile, "CUSTCARD");
                   String efilename= eFile.getPathname() + eFile.getEfilename() ;
                    efilename=efilename.replaceAll("\\\\", "");
                    ITEM.ItemBean addItem = new ITEM.ItemBean(libcode, DIRID, CUSTID, CUSTCARD, TITLE
                            , FILETYPE, efilename
                            , eFile.getExt(),  eFile.getPzm(), eFile.getAttr(), eFile.getMd5(), eFile.getCreator()
                            , DateUtil.dateToStr(eFile.getCreatetime()), eFile.getFilesize());
                    ITEM item = new ITEM();
                    item.setItem(addItem);
                    try {
                        String json = JSON.toJSONString(item);
                        MnsMessageDto mnsDto = new MnsMessageDto();
                        mnsDto.setType("hamsadd");
                        mnsDto.setUuid(GlobalFinalAttr.getGuid());
                        mnsDto.setData(json);
                        String messageBody = JSON.toJSONString(mnsDto);
                        Map<String , Object> mapObj = new HashMap<>();
                        mapObj.put("messageBody" , messageBody);
                        mapObj.put("properties" , getProperteis());

                        String finalMsg = JSON.toJSONString(mapObj);

                        System.out.println(finalMsg);

                        CloudQueue queue = client.getQueueRef(arcWriteAddQ);
                        Message message = new Message();
                        message.setMessageBody(finalMsg);
                        Message putMsg = queue.putMessage(message);
                        execSql("UPDATE E_FILE" + libcode + " SET BBH='ADD' WHERE DID=" + eFile.getDid());
                        eNum++;
                        log.error("sync add " + eNum + " itme2 queue:" + putMsg.getMessageId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage(), e);
                    }
                }
            }
            if (client != null) {
                client.close();
            }
        }
        return "档案系统 增加文件 写入队列 " + eNum + " 条数据";
    }

    public String archive2lambdaDEL() {
        Integer eNum = 0;

        String[] libCodesArray = syncLibcodes.split("[,]");
        for (String libcode : libCodesArray) {

            List<EFile> efieList = listNoSyncDelEfile50("E_FILE" + libcode);
            CloudAccount account = new CloudAccount(this.ak, this.sk, this.endPoint);
            MNSClient client = account.getMNSClient();
            for (EFile eFile : efieList) {
                Map<String, Object> dFile = queryForMap("SELECT DID,PID,DIRID,CUSTID FROM D_FILE" + libcode
                        + " WHERE STATUS=0 AND DID=" + eFile.getPid());

                if (null != dFile && StringUtils.isNotBlank(MapUtils.getString(dFile, "PID"))) {
 
                    String DIRID = MapUtils.getString(dFile, "DIRID");
                    String efilename= eFile.getPathname() + eFile.getEfilename() ;
                    efilename=efilename.replaceAll("\\\\", "");
                    DelItem delItem = new DelItem();
                    delItem.setLIBCODE(Integer.valueOf(libcode));
                    delItem.setEFILENAME(efilename);
                    delItem.setDELETOR(eFile.getDeltor());
                    delItem.setDIRID(Integer.valueOf(DIRID));
                    delItem.setOPERTIME( eFile.getDeltime() );
                      try {
                        String json = JSON.toJSONString(delItem);
                        CloudQueue queue = client.getQueueRef(arcWriteDelQ);
                        MnsMessageDto mnsDto = new MnsMessageDto();
                        mnsDto.setType("hamsdel");
                        mnsDto.setUuid(GlobalFinalAttr.getGuid());
                        mnsDto.setData(json);
                        String messageBody = JSON.toJSONString(mnsDto);
                        
                        
                        Map<String , Object> mapObj = new HashMap<>();
                      
                        
                        mapObj.put("messageBody" , messageBody);
                        mapObj.put("properties" , getProperteis());

                        String finalMsg = JSON.toJSONString(mapObj);

                        System.out.println(finalMsg);

                        
                        Message message = new Message();
                        message.setMessageBody(finalMsg);
                         Message putMsg = queue.putMessage(message);
                        execSql("UPDATE E_FILE" + libcode + " SET BBH='DEL' WHERE DID=" + eFile.getDid());
                        eNum++;
                    
                    
                  log.error("sync del " + eNum + " itme2 queue:" + putMsg.getMessageId());
                     } catch (Exception e) {
                         e.printStackTrace();
                         log.error(e.getMessage(), e);
                    }
                }
            }
            if (client != null) {
                client.close();
            }
        }
        return "档案系统 删除文件 写入队列 " + eNum + " 条数据";
    }

    @Override
    public String lambda2ArchiveADD() {
        Integer eNum = 0;
        CloudAccount account = new CloudAccount(this.ak, this.sk, this.endPoint);
        MNSClient client = account.getMNSClient();
        CloudQueue queue = null;
        try {
            queue = client.getQueueRef(lambdaWriteAddQ);
            Message popMsg = queue.popMessage();
            while (null != popMsg) {
                String json = popMsg.getMessageBodyAsString();
                System.out.println(json);
                 json = new String(json.getBytes("gbk"),"utf-8");
                Map<String , String> mapObj = JSON.parseObject(json, Map.class);
                MnsMessageDto<JSONObject> itemBean = JSON.parseObject(mapObj.get("messageBody"), MnsMessageDto.class);
                System.out.println(itemBean.getData().toString());
                ITEM ai = JSON.parseObject(itemBean.getData().toString() , ITEM.class);

                System.out.println(ai.getItem().toString());
                if (StringUtils.isNotBlank(ai.getItem().getDirid()) &&
                        StringUtils.isNotBlank(ai.getItem().getCustid())) {
                    //父级存在并且电子文件存在 修改父级的名字
                    String judgeEfileSql = "SELECT PID FROM E_FILE" + ai.getItem().getLibcode()
                            + " WHERE  CONCAT(PATHNAME,EFILENAME) ='" + ai.getItem().getEfilename() + "' AND STATUS=0 ";
                    List<Integer> efileList = jdbcDao.query4List(judgeEfileSql, Integer.class);
                    //电子文件已经存在.需要修改DFILE的FILETYPE
                    if (efileList.size() > 0 && StringUtils.isNotBlank(ai.getItem().getFiletype())) {
                        try {
                            execSql("UPDATE D_FILE" + ai.getItem().getLibcode() + " SET FILETYPE='"
                                    + ai.getItem().getFiletype() + "' WHERE DID=" + efileList.get(0));
                            log.error("update D_FILE" + ai.getItem().getLibcode() + "'filetype=" + ai.getItem().getFiletype());
                            continue;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 电子文件不存在 判断DFILE是否存在
                        String judgeDfileSql = "SELECT DID FROM D_FILE" + ai.getItem().getLibcode()
                                + " WHERE CUSTID='" + ai.getItem().getCustid() + "' AND DIRID='"
                                + ai.getItem().getDirid() + "' AND STATUS=0 AND FILETYPE='"
                                + ai.getItem().getFiletype() + "'";
                        List<Integer> dfileList = jdbcDao.query4List(judgeDfileSql, Integer.class);
                        if (dfileList.size() > 0) {//文件存在 好办了 直接插入EFILE关联
                            //插入Efile  dfile.did
                            insertEfileByAi(ai, dfileList.get(0));
                        } else {//不存在文件 需要穿件文件
                            String judgeDVolSql = "SELECT DID FROM D_VOL" + ai.getItem().getLibcode()
                                    + " WHERE CUSTID='" + ai.getItem().getCustid() + "' AND STATUS=0";
                            List<Integer> volList = jdbcDao.query4List(judgeDVolSql, Integer.class);
                            if (volList.size() > 0) {//案卷存在 文件不存在
                                //插入Dfile  dVOL.did
                                Integer dFileDid = insertDfileByAi(ai, volList.get(0));
                                //插入Efile  dfile.did
                                insertEfileByAi(ai, dFileDid);
                            } else {//案卷文件都不存在
                                //插入DVOL
                                Integer dVolDid = insertDvolByAi(ai);
                                //插入Dfile  dVOL.did
                                Integer dFileDid = insertDfileByAi(ai, dVolDid);
                                //插入Efile  dfile.did
                                insertEfileByAi(ai, dFileDid);
                            }
                        }
                    }
                } else {
                    log.error("custid or dirid is null");
                }
                System.out.println(ai.getItem().getEfilename());
                //queue.deleteMessage(popMsg.getReceiptHandle());
                popMsg = queue.popMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        client.close();
        return "档案系统从队列 接收增加文件 " + eNum + " 条数据";
    }

    @Override
    public String lambda2ArchiveDEL() {
        Integer eNum = 0;
         String efilename="";
	        String pathname="";
		 String edelfile="";
		 String delStr="";
        CloudAccount account = new CloudAccount(this.ak, this.sk, this.endPoint);
        MNSClient client = account.getMNSClient();
        CloudQueue queue = null;
        try {
            queue = client.getQueueRef(lambdaWriteDelQ);
            Message popMsg = queue.popMessage();
            while (null != popMsg) {
                System.out.println(popMsg.getMessageBodyAsString());
                delStr=popMsg.getMessageBodyAsString();
                delStr = new String(delStr.getBytes("gbk"),"utf-8");
               Map<String , String> mapObj = JSON.parseObject(delStr, Map.class);
                 MnsMessageDto<JSONObject> itemBean = JSON.parseObject(mapObj.get("messageBody"), MnsMessageDto.class);
        
		         
             edelfile=itemBean.getData().getJSONObject("delitem").getString("efilename");
                 efilename= FilenameUtils.getName( edelfile);
                 
                 pathname= edelfile.replace(efilename, "")  ;
                 pathname=pathname.replaceAll("\\\\", "");
              String sql = "UPDATE E_FILE" + itemBean.getData().getJSONObject("delitem").getString("libcode") + " SET BBH='DEL' , STATUS=1" +
                        " WHERE EFILENAME='" + efilename + "' AND PATHNAME='" +pathname + "'" ;
                System.out.println(sql);
                execSql(sql);
                 log.error("同步删除了电子文件:" + edelfile);
                eNum++;

//                queue.deleteMessage(popMsg.getReceiptHandle());
                popMsg = queue.popMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        client.close();
        return "档案系统从队列 接收删除电子文件 " + eNum + " 条数据";
    }

    /**
     * 得到没有同步的 增加
     *
     * @param eTableName
     * @return
     */
    private List<EFile> listNoSyncAddEfile50(String eTableName) {
        PageContext page = PageContext.getContext();
        page.setCurrentPage(1);
        page.setPageSize(50);
        page.setPagination(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("sql", "SELECT * FROM " + eTableName + " WHERE STATUS=0 AND (BBH IS NULL OR BBH ='')");
        return sUserMapper.efilelistPage(map);
    }

    /**
     * 得到没有同步的 删除
     *
     * @param eTableName
     * @return
     */
    private List<EFile> listNoSyncDelEfile50(String eTableName) {
        PageContext page = PageContext.getContext();
        page.setCurrentPage(1);
        page.setPageSize(50);
        page.setPagination(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("sql", "SELECT * FROM " + eTableName + " WHERE STATUS=1 AND BBH='ADD'");
        return sUserMapper.efilelistPage(map);
    }

    /**
     * 插入案卷
     *
     * @param ai
     * @return
     */
    private Integer insertDvolByAi(ITEM ai) {
        String tbname = "D_VOL" + ai.getItem().getLibcode();
        Integer voldid = getMaxDid(tbname);
        String sql = "INSERT INTO " + tbname + "(DID,PID,STATUS,ATTR,ATTREX,QZH,BMID,CUSTID,DIRID) VALUES("
                + voldid + ", -1, 0 , " + ai.getItem().getAttr() + "," + attrex + ",'" + qzh + "','" + qzh + "','"
                + ai.getItem().getCustid() + "','" + ai.getItem().getDirid() + "')";
        System.out.println(sql);
        execSql(sql);
        return voldid;
    }

    /**
     * 插入文件
     *
     * @param ai
     * @param volDid
     * @return
     */
    private Integer insertDfileByAi(ITEM ai, Integer volDid) {
        String tbname = "D_FILE" + ai.getItem().getLibcode();
        Integer fileDid = getMaxDid(tbname);
        String sql = "INSERT INTO " + tbname + "(DID,PID,ATTACHED,STATUS,ATTR,ATTREX,QZH,BMID,CUSTID,DIRID,FILETYPE) VALUES("
                + fileDid + ", " + volDid + ",1, 0 , " + ai.getItem().getAttr() + "," + attrex + ",'" + qzh + "','" + qzh + "','"
                + ai.getItem().getCustid() + "','" + ai.getItem().getDirid() + "','" + ai.getItem().getFiletype() + "')";
        System.out.println(sql);
        execSql(sql);
        return fileDid;
    }

    /**
     * 插入电子文件
     *
     * @param ai
     * @param fileDid
     * @return
     */
    private Integer insertEfileByAi(ITEM ai, Integer fileDid) {
        String tbname = "E_FILE" + ai.getItem().getLibcode();
        String efilename="";
        String pathname="";
        Integer eFileDid = getMaxDid(tbname);
        EFile efile = new EFile();
        efile.setDid(eFileDid);
        efile.setPid(fileDid);
        efile.setTitle(ai.getItem().getTitle());
        
         efilename= FilenameUtils.getName(ai.getItem().getEfilename());
        efile.setEfilename(efilename);
        efile.setAttr(0);
        efile.setAttrex(0);
        efile.setStatus(0);
        pathname=   ai.getItem().getEfilename().replace(efilename, "")  ;
        efile.setPathname(pathname);
       
        efile.setPzm(ai.getItem().getPzm());
        if (efile.getPzm()==null){
        	efile.setPzm("data");
        }
        efile.setExt(ai.getItem().getExt());
        efile.setBbh("ADD");
        efile.setMd5(ai.getItem().getMd5());
        efile.setCreator(ai.getItem().getCreator());
        efile.setTablename(tbname);
        
        String sql = "insert into " + tbname +
                "(DID,PID,EFILENAME,TITLE,EXT,PZM,PATHNAME,STATUS,ATTR,ATTREX,CREATOR,FILESIZE,MD5,CONVERTSTATUS , BBH) "
                + "values("+efile.getDid().toString()+","+efile.getPid().toString()+",'"+efile.getEfilename()+"','"+ efile.getTitle()+"','"+
                efile.getExt()+"','"+efile.getPzm()+"','"+efile.getPathname()+"',"+efile.getStatus().toString()+",0,0,'"+efile.getCreator()+"',"+efile.getFilesize().toString()+",'"+efile.getMd5()+"',0,'" + efile.getBbh()+"')"; 
        sql=sql.replaceAll("\\\\", "");
        System.out.println(sql);
        execSql(sql);
        return eFileDid;
    }


    private Map<String  ,String > getProperteis(){
        Map<String , String> properties = new HashMap<>();
        properties.put("propertyName", "JMS_MNSMessageType");
        properties.put("propertyType", "String");
        properties.put("propertyValue", "text");
        return properties;
    }
    @Autowired
    private SGroupMapper sGroupMapper;
    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    private SUserroleMapper sUserroleMapper;
    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    @Value("${lams.sync2queue.ak}")
    protected String ak;

    @Autowired
    @Value("${lams.sync2queue.sk}")
    protected String sk;

    @Autowired
    @Value("${lams.sync2queue.endpint}")
    protected String endPoint;

    @Autowired
    @Value("${lams.sync2queue.lambda2archives-add-read}")
    protected String arcWriteAddQ;

    @Autowired
    @Value("${lams.sync2queue.lambda2archives-del-read}")
    protected String arcWriteDelQ;

    @Autowired
    @Value("${lams.sync2queue.lambda2archives-del-write}")
    protected String lambdaWriteDelQ;

    @Autowired
    @Value("${lams.sync2queue.lambda2archives-add-write}")
    protected String lambdaWriteAddQ;

    @Autowired
    @Value("${lams.sync2queue.libcodes}")
    protected String syncLibcodes;


    @Autowired
    @Value("${lams.default.qzh}")
    protected String qzh;

    @Autowired
    @Value("${lams.dfile.attrex}")
    protected Integer attrex;

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
