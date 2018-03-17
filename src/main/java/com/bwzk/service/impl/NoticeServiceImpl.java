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
import com.bwzk.util.GlobalFinalAttr;
import org.apache.commons.collections.MapUtils;
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

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("noticeServiceImpl")
public class NoticeServiceImpl extends BaseService implements NoticeService {

    public void sendActivitiMsg(String userCodes, String varsJson, String actTaskID) {
        String sqrbm = "";
        String lymd = "";
        String sqrdm = "";
        String sqrxm = "";
        String sqyy = "";
        String sqtype = "";
        String lylx = "";
        Integer lylxNum = 1;//默认时间段
        Integer times = 0;
        String starTime = "";
        String endTime = "";
        String requestTime = "";
        String mj = "";
        SUser user = null;

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> vars = null;
        try {
            String[] userCodeList = StringUtils.split(userCodes, "[,]");
            vars = mapper.readValue(varsJson, Map.class);
            sqrxm = (vars.get("sqrxm") == null ? "" : vars.get("sqrxm").toString());
            lymd = (vars.get("lymd") == null ? "" : vars.get("lymd").toString());
            sqrdm = (vars.get("sqrdm") == null ? "" : vars.get("sqrdm").toString());
            sqrbm = (vars.get("sqrbm") == null ? "" : vars.get("sqrbm").toString());
            sqyy = (vars.get("sqyy") == null ? "" : vars.get("sqyy").toString());
            sqtype = (vars.get("sqtype") == null ? "" : vars.get("sqtype").toString());

            lylx = (vars.get("timeortimes") == null ? "" : vars.get("timeortimes").toString());
            requestTime = (vars.get("sqrq") == null ? "" : vars.get("sqrq").toString());
            if (lylx.equals("次数")) {
                lylxNum = 0;
                times = MapUtils.getInteger(vars, "times");
            } else {
                starTime = (vars.get("startime") == null ? "" : vars.get("startime").toString());
                endTime = (vars.get("endtime") == null ? "" : vars.get("endtime").toString());
            }

            mj = (vars.get("mj") == null ? "" : vars.get("mj").toString());
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) vars.get("dataList");

            if (null == dataList || dataList.size() < 1) {
                log.error("档案系统发送了错误数.dataList为空");
                return;
            }
            for (String userCode : userCodeList) {
                SUser sqrUser = sUserMapper.getUserByUsercode(sqrdm);
                if (sqrUser != null) {
                    FlowMain fm = new FlowMain();
                    fm.setId(GlobalFinalAttr.getGuid());
                    fm.setFid(actTaskID);
                    // @sqrxm(@sqrbm)提交的档案@sqtype的申请
                    fm.setTitle(sendInfoTitle.replace("@sqrxm" , sqrxm).replace("@sqrbm" , sqrbm)
                            .replace("@sqtype" , sqtype));
                    fm.setApplyauth(sqtype);
                    fm.setApplytype(lylxNum);
                    fm.setApplytimes(times);
                    fm.setStardate(starTime);
                    fm.setEnddate(endTime);
                    fm.setRequesttime(requestTime);
                    fm.setUserid(sqrUser.getEsbid());//oa用户主键
                    fm.setUsercode(sqrUser.getUsercode());
                    fm.setUsername(sqrUser.getUsername());
                    fm.setMemo(lymd + ":" + sqyy);

                    fm.setStatus(0);//0:有效   1:过期或者失效
                    fm.setResult(0);//0:未处理(OA没有处理) 1:同意利用  2:否决利用
                    flowMainMapper.insert(fm);

                    for (Map<String, Object> map : dataList) {
                        Integer libcode = MapUtils.getInteger(map, "DALXLIBCODE");
                        Integer level = MapUtils.getInteger(map, "DALXLEVEL");
                        Integer itemDid = MapUtils.getInteger(map, "DID");
                        String itemTbName = GlobalFinalAttr.getTableNameByParam(libcode, level);
                        String itemMj = (map.get("MJ") == null ? "" : map.get("MJ").toString());
                        String bgqx = (map.get("BGQX") == null ? "" : map.get("BGQX").toString());
                        String keyword = (map.get("KEYWORD") == null ? "" : map.get("KEYWORD").toString());
                        String title = (map.get("TITLE") == null ? "" : map.get("TITLE").toString());

                        FlowDataItem item = new FlowDataItem();
                        item.setId(GlobalFinalAttr.getGuid());
                        item.setPid(fm.getId());

                        item.setTablename(itemTbName);
                        item.setKeyword(keyword);
                        item.setTitle(title);
                        item.setMj(itemMj);
                        item.setBgqx(bgqx);
                        item.setArcid(itemDid);
                        flowDataItemMapper.insert(item);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 将结果流程审批结果同步到lams
     */
    public void syncTaskInfo() {
        FlowMainExample fEx = new FlowMainExample();
        fEx.createCriteria().andStatusEqualTo(0).andResultNotEqualTo(0);
        List<FlowMain> flowMainList = flowMainMapper.selectByExample(fEx);
        for (FlowMain fm : flowMainList) {
            fm.setStatus(2);
            try {
                if(receiveMsg(fm.getFid() , fm.getResult() , fm.getMsg())){
                    fm.setStatus(1);
                }
            } catch (Exception e) {
                log.error(e.getMessage() , e);
                e.printStackTrace();
            }
            flowMainMapper.updateByPrimaryKeySelective(fm);
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


    /**
     * 接收Oa流程发来的参数
     * 0:未处理(OA没有处理) 1:同意利用  2:否决利用
     *
     * @param taskId
     * @param status
     * @return
     */
    protected Boolean receiveMsg(String taskId, Integer result, String spyj) {
        Boolean wsRslt = Boolean.FALSE;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();
        map.put("OutSystemFqrCode", "ROOT");
        map.put("spjg", result.equals(1) ? "true" : "false");//1:同意利用  2:否决利用
        map.put("spyj", spyj);//审批意见
        try {
            ObjectMapper mapper = new ObjectMapper();
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, map);
            // http://localhost/Lams/activiti/completeTask
            sendMsg2Lams("http://" + lamsIP + "/Lams/activiti/completeTask",
                    taskId, sw.toString(), Boolean.FALSE.toString());
            System.out.println("call lams.activiti status: " + status);
            wsRslt = Boolean.TRUE;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return wsRslt;
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
