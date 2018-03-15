package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.i.da.FlowDataItemMapper;
import com.bwzk.dao.i.da.FlowMainMapper;
import com.bwzk.dao.i.da.SBacklogMapper;
import com.bwzk.dao.i.da.SUserMapper;
import com.bwzk.pojo.FlowDataItem;
import com.bwzk.pojo.FlowMain;
import com.bwzk.pojo.FlowMainExample;
import com.bwzk.pojo.SUser;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.NoticeService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("noticeServiceImpl")
public class NoticeServiceImpl extends BaseService implements NoticeService {

    public void sendActivitiMsg(String userCodes, String varsJson, String actTaskID) {
        System.out.println(actTaskID + "  ::::");
        String sqrbm = "";
        String sqrdm = "";
        String sqrxm = "";
        String sqyy = "";
        String sqtype = "";
        String mj = "";
        SUser user = null;

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> vars = null;
        try {
            String[] userCodeList = StringUtils.split(userCodes, "[,]");
            vars = mapper.readValue(varsJson, Map.class);
            sqrxm = (vars.get("sqrxm") == null ? "" : vars.get("sqrxm").toString());
            sqrdm = (vars.get("sqrdm") == null ? "" : vars.get("sqrdm").toString());
            sqrbm = (vars.get("sqrbm") == null ? "" : vars.get("sqrbm").toString());
            sqyy = (vars.get("sqyy") == null ? "" : vars.get("sqyy").toString());
            sqtype = (vars.get("sqtype") == null ? "" : vars.get("sqtype").toString());
            mj = (vars.get("mj") == null ? "" : vars.get("mj").toString());


            //写流程包







            for (String userCode : userCodeList) {
                user = sUserMapper.getUserByUsercode(userCode);
                if (user != null) {
                    String url = "http://" + lamsIP + "/LamsIFSS/gotoTask";
                    String content = msgVM;
                    String todoTitle = sendInfoTitle;

                    todoTitle = todoTitle.replace("@sqrUsername", sqrxm);
                    todoTitle = todoTitle.replace("@sqtype", sqtype);

                    content = content.replace("@sqrGroupName", sqrbm);
                    content = content.replace("@sqrUsername", sqrxm);
                    content = content.replace("@sqyy", sqyy);
                    content = content.replace("@sqtype", sqtype);
                    content = content.replace("@itemMJ", mj);
                    content = content.replace("@fqrUsername", user.getUsername());
                    content = content.replace("@gotoLamsUrl", url);
                    System.out.println(content);

//                    sendMsg2Lams("http://" + lamsIP + "/Lams/activiti/completeTask",
//                            taskId, sw.toString(), Boolean.FALSE.toString());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void syncTaskInfo(){
        FlowMainExample fEx = new FlowMainExample();
        fEx.createCriteria().andStatusEqualTo(0).andResultNotEqualTo(0);
        List<FlowMain> flowMainList = flowMainMapper.selectByExample(fEx);
        for (FlowMain flowMain : flowMainList) {
            //todo 发送
            //得到结果 反写状态
            //失败 记录入职 status 写 2
            // 成功 result写1
        }
    }

    public NoticeServiceImpl() {
        try {
            EncodedResource todoRes = new EncodedResource(
                    new ClassPathResource("vm/todo.vm"), "UTF-8");
            msgVM = FileCopyUtils.copyToString(todoRes.getReader());
        } catch (IOException e) {
            log.error(e.getMessage());
            System.out.println("系统初始化错误");
            System.exit(0);
        }
    }

    /**
     * 发动请求到 Lams
     *
     * @param url
     * @param taskId
     * @param varsJson
     * @param cancelSubmit
     * @return
     */
    private Integer sendMsg2Lams(String url, String taskId, String varsJson,
                            String cancelSubmit) {
        HttpPost post = null;
        HttpClient client = null;
        Integer status = 0;
        try {
            post = new HttpPost(url);
            client = new DefaultHttpClient();
            // 填充表单
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("taskId", taskId));
            params.add(new BasicNameValuePair("varsJson", varsJson));
            params.add(new BasicNameValuePair("cancelSubmit", cancelSubmit));
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            status = client.execute(post).getStatusLine().getStatusCode();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(client);
        }
        return status;
    }


    private String receiveMsg(String taskId, String status, String borrowsStatus) {
        try {
            File tempFIle = new File("c:/" + System.currentTimeMillis());
            FileUtils.writeStringToFile(tempFIle, taskId, true);
            FileUtils.writeStringToFile(tempFIle, status, true);
            FileUtils.writeStringToFile(tempFIle, borrowsStatus, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String wsRslt = "1";
        Map<String, Object> map = null;
        if (StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(status)) {
            map = new HashMap<String, Object>();
            map.put("OutSystemFqrCode", "ROOT");
            map.put("spjg",
                    status.contains("true") || status.contains("成功") ? "true"
                            : "false");
            try {
                ObjectMapper mapper = new ObjectMapper();
                StringWriter sw = new StringWriter();
                mapper.writeValue(sw, map);
                // http://localhost/Lams/activiti/completeTask
                sendMsg2Lams("http://" + lamsIP + "/Lams/activiti/completeTask",
                        taskId, sw.toString(), Boolean.FALSE.toString());
                System.out.println("call lams.activiti status: " + status);
                wsRslt = "0";
            } catch (Exception e) {
                log.error(e.getMessage());
                wsRslt = wsRslt + "[" + e.getMessage() + "]";
            }
        } else {
            wsRslt = wsRslt + "[taskid 或者 result为空]";
        }
        return wsRslt;
    }

    public String syncFLowBean(){
        String msg = "flow info ok 好的";
        return msg;
    }

    private String msgVM;

    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    private SBacklogMapper sBacklogMapper;
    @Autowired
    private FlowDataItemMapper flowDataItemMapper;
    @Autowired
    private FlowMainMapper flowMainMapper;

    @Autowired
    @Value("${sendinfo.todo.title}")
    private String sendInfoTitle;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
