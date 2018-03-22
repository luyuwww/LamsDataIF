package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSON;
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
import com.bwzk.pojo.jsonbean.AddItem;
import com.bwzk.pojo.jsonbean.DelItem;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.SyncService;
import com.bwzk.util.DateUtil;
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
                Map<String, Object> dFile = queryForMap("SELECT DID,PID,TITLE,DIRID,KEYWORD,FILETYPE,CUSTCARD" +
                        " ,F1,F2,F3,F4,F5,F6  FROM D_FILE" + libcode + " WHERE STATUS=0 AND DID=" + eFile.getPid());

                if (null != dFile && StringUtils.isNotBlank(MapUtils.getString(dFile, "PID"))) {
                    String DIRID = MapUtils.getString(dFile, "DIRID");
                    String TITLE = MapUtils.getString(dFile, "TITLE");
                    String KEYWORD = MapUtils.getString(dFile, "KEYWORD");
                    String FILETYPE = MapUtils.getString(dFile, "FILETYPE");
                    String CUSTCARD = MapUtils.getString(dFile, "CUSTCARD");
                    String f1 = MapUtils.getString(dFile, "F1");
                    String f2 = MapUtils.getString(dFile, "F2");
                    String f3 = MapUtils.getString(dFile, "F3");
                    String f4 = MapUtils.getString(dFile, "F4");
                    String f5 = MapUtils.getString(dFile, "F5");
                    String f6 = MapUtils.getString(dFile, "F6");

                    AddItem addItem = new AddItem(libcode, DIRID, KEYWORD, CUSTCARD, TITLE,
                            FILETYPE, FilenameUtils.normalize(eFile.getPathname() + eFile.getEfilename())
                            , eFile.getTitle(), eFile.getExt(),
                            eFile.getPzm(), eFile.getAttr(), eFile.getMd5(), eFile.getCreator()
                            , DateUtil.dateToStr(eFile.getCreatetime()), eFile.getFilesize());
                    try {
                        String json = JSON.toJSONString(addItem);
                        CloudQueue queue = client.getQueueRef(arcWriteAddQ);
                        Message message = new Message();
                        message.setMessageBody(json);
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
                Map<String, Object> dFile = queryForMap("SELECT DID,PID,DIRID,KEYWORD FROM D_FILE" + libcode
                        + " WHERE STATUS=0 AND DID=" + eFile.getPid());

                if (null != dFile && StringUtils.isNotBlank(MapUtils.getString(dFile, "PID"))) {
                    String DIRID = MapUtils.getString(dFile, "DIRID");
                    DelItem delItem = new DelItem(libcode, DIRID,
                            FilenameUtils.normalize(eFile.getPathname() + eFile.getEfilename()), eFile.getDeltor(),
                            DateUtil.dateToStr(eFile.getDeltime()));
                    try {
                        String json = JSON.toJSONString(delItem);
                        CloudQueue queue = client.getQueueRef(arcWriteDelQ);
                        Message message = new Message();
                        message.setMessageBody(json);
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
            while (null != popMsg){
                System.out.println(popMsg.getMessageBody());
                AddItem ai = JSON.parseObject(popMsg.getMessageBody() , AddItem.class);
                if(StringUtils.isNotBlank(ai.getITEM().getDIRID()) &&
                        StringUtils.isNotBlank(ai.getITEM().getCUSTID())){
                    String judgeDfileSql = "SELECT DID FROM D_FILE" + ai.getITEM().getLIBCODE()
                            + " WHERE CUSTID='"+ai.getITEM().getCUSTID()+ "' AND DIRID='"
                            + ai.getITEM().getDIRID()+"' AND STATUS=0 AND FILETYPE='"
                            + ai.getITEM().getFILETYPE() + "'";
                    List<Integer> dfileList = jdbcDao.query4List(judgeDfileSql , Integer.class);
                    if(dfileList.size() > 0){//文件存在 好办了 直接插入EFILE关联
                        //插入Efile  dfile.did
                        insertEfileByAi(ai ,  dfileList.get(0));
                    }else{//不存在文件 需要穿件文件
                        String judgeDVolSql = "SELECT DID FROM D_VOL" + ai.getITEM().getLIBCODE()
                                + " WHERE CUSTID='"+ai.getITEM().getCUSTID()+"' AND STATUS=0";
                        List<Integer> volList = jdbcDao.query4List(judgeDVolSql , Integer.class);
                        if(volList.size() > 0) {//案卷存在 文件不存在
                            //插入Dfile  dVOL.did
                            Integer dFileDid = insertDfileByAi(ai , volList.get(0));
                            //插入Efile  dfile.did
                            insertEfileByAi(ai , dFileDid);
                        }else{//案卷文件都不存在
                            //插入DVOL
                            Integer dVolDid = insertDvolByAi(ai);
                            //插入Dfile  dVOL.did
                            Integer dFileDid = insertDfileByAi(ai , dVolDid);
                            //插入Efile  dfile.did
                            insertEfileByAi(ai , dFileDid);
                        }
                    }
                }else{
                    log.error("custid or dirid is null");
                }
                System.out.println(ai.getITEM().getEFILENAME());
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
        CloudAccount account = new CloudAccount(this.ak, this.sk, this.endPoint);
        MNSClient client = account.getMNSClient();
        CloudQueue queue = null;
        try {
            queue = client.getQueueRef(lambdaWriteDelQ);
            Message popMsg = queue.popMessage();
            while (null != popMsg){
                DelItem di = JSON.parseObject(popMsg.getMessageBody() , DelItem.class);
                String baseFileName = FilenameUtils.getName(di.getDELITEM().getEFILENAME());
                String sql = "UPDATE E_FILE" + di.getDELITEM().getLIBCODE() + " SET BBH='DEL' , STATUS=1" +
                        " WHERE EFILENAME='" + baseFileName +"'";
                System.out.println(sql);
                execSql(sql);
                log.error("同步删除了电子文件:" + di.getDELITEM().getEFILENAME());
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
     * @param ai
     * @return
     */
    private Integer insertDvolByAi(AddItem ai){
        String tbname = "D_VOL" + ai.getITEM().getLIBCODE();
        Integer voldid = getMaxDid(tbname);
        String sql = "INSERT INTO "+tbname +"(DID,PID,STATUS,ATTR,ATTREX,QZH,BMID,KEYWORD,DIRID) VALUES("
                + voldid +", -1, 0 , "+ai.getITEM().getATTR()+","+attrex+",'"+qzh+"','"+qzh+"','"
                + ai.getITEM().getCUSTID() + "','" + ai.getITEM().getDIRID()+"'";
        System.out.println(sql);
        execSql(sql);
        return voldid;
    }

    /**
     * 插入文件
     * @param ai
     * @param volDid
     * @return
     */
    private Integer insertDfileByAi(AddItem ai , Integer volDid){
        String tbname = "D_FILE" + ai.getITEM().getLIBCODE();
        Integer fileDid = getMaxDid(tbname);
        String sql = "INSERT INTO "+tbname +"(DID,PID,ATTACHED,STATUS,ATTR,ATTREX,QZH,BMID,KEYWORD,DIRID,FILETYPE) VALUES("
                + fileDid +", "+volDid+",1, 0 , "+ai.getITEM().getATTR()+","+attrex+",'"+qzh+"','"+qzh+"','"
                + ai.getITEM().getCUSTID() + "','" + ai.getITEM().getDIRID()+"','" + ai.getITEM().getFILETYPE()+"'";
        System.out.println(sql);
        execSql(sql);
        return  fileDid;
    }

    /**
     * 插入电子文件
     * @param ai
     * @param fileDid
     * @return
     */
    private Integer insertEfileByAi(AddItem ai , Integer fileDid){
        String tbname = "E_FILE" + ai.getITEM().getLIBCODE();
        Integer eFileDid = getMaxDid(tbname);
        EFile efile = new EFile();
        efile.setDid(eFileDid);
        efile.setPid(fileDid);
        efile.setTitle(ai.getITEM().getTITLE());
        efile.setEfilename(FilenameUtils.getBaseName(ai.getITEM().getEFILENAME()));
        efile.setPathname(ai.getITEM().getEFILENAME().replace(efile.getEfilename(),""));
        efile.setPzm(ai.getITEM().getPZM());
        efile.setExt(ai.getITEM().getEXT());
        efile.setBbh("ADD");
        efile.setMd5(ai.getITEM().getMD5());
        efile.setCreator(ai.getITEM().getCREATOR());
        super.insertEfile(tbname , efile);
        return  eFileDid;
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
    @Value("${lams.sync2queue.endpiint}")
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
