package com.hwxt.service.impl;

import ch.qos.logback.classic.Logger;
import com.hwxt.dao.i.SBacklogMapper;
import com.hwxt.dao.i.SUserMapper;
import com.hwxt.pojo.SBacklog;
import com.hwxt.pojo.SBacklogExample;
import com.hwxt.pojo.SUser;
import com.hwxt.service.BaseService;
import com.hwxt.service.i.NoticeService;
import com.hwxt.util.SeriKeyOper;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
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
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String toDoList(String usercode) {
        String result = "";
        Integer todoNum = sBacklogMapper.getBackLogNum(" OPERMODULEOWNER='izerui' AND OPERMODULEZH='任务列表'"
                + " AND ISOPER=0 AND USERCODE ='" + usercode + "'");
        if (todoNum > 0) {
            try {
                SUser user = sUserMapper.getUserByUsercode(usercode);
                if (user != null) {
                    String title = "档案系统审批: 待处理" + todoNum + "条数据";
                    String url = generatUrl(user, "任务列表", "izerui");
                    System.out.println(title);
                    System.out.println(url);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public List<SBacklog> allBacklog(String usercode) {
        SBacklogExample ex = new SBacklogExample();
        ex.createCriteria().andUsercodeEqualTo("chenli");
        List<SBacklog> backLoList = sBacklogMapper.selectByExample(ex);
        return backLoList;
    }

    /**
     * 得到登录Lams并且进入相应模块的URL
     *
     * @param user     用户
     * @param modName  模块中文名称
     * @param modOwner 模块所属者
     */
    private String generatUrl(SUser user, String modName, String modOwner) {
        StringBuffer sb = new StringBuffer();
        sb.append("http://").append(lamsIP).append("/Lams/autoLogin?card=").append(SeriKeyOper.encrypt(user.getUsercode()));
        sb.append("&serikey=").append(SeriKeyOper.encrypt(user.getPasswd())).append("&moduleName=");
        sb.append(SeriKeyOper.encrypt(modName)).append("&moduleOwner=").append(SeriKeyOper.encrypt(modOwner));
        sb.append("&random=").append(Math.random());
        return sb.toString();
    }

    public NoticeServiceImpl() {
        try {
            EncodedResource todoRes = new EncodedResource(new ClassPathResource("vm/todo.vm"), "UTF-8");
            msgVM = FileCopyUtils.copyToString(todoRes.getReader());
//			System.out.println(msgVM);
        } catch (IOException e) {
            log.error(e.getMessage());
            System.out.println("系统初始化错误");
            System.exit(0);
        }
    }

    private String msgVM;

    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    private SBacklogMapper sBacklogMapper;
    @Autowired
    @Value("${sendinfo.todo.title}")
    private String sendInfoTitle;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
