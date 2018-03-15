package com.bwzk.action;

import ch.qos.logback.classic.Logger;
import com.bwzk.service.i.ArcService;
import com.bwzk.service.i.NoticeService;
import com.bwzk.service.i.OrgService;
import com.bwzk.util.GlobalFinalAttr;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class CommonCtler {
    /**
     * 主页跳转
     */
    @RequestMapping(value = {"/index.html", "/"})
    public String gotoIndex() {
        return "index.jsp";
    }

    /**
     * 列出所有日志- add
     */
    @RequestMapping(value = "/viewLogList")
    public String viewLogList(Model model) {
        try {
            File[] listFile = new File(logHomeAdd).listFiles();
            model.addAttribute("listFile", listFile);
            model.addAttribute("fileType", "log");
            return "listLog.jsp";
        } catch (Exception e) {
            log.error("获取日志列表错误.", e);
            return "index.jsp";
        }
    }

    /**
     * 列出所有XML
     */
    @RequestMapping(value = "/viewXMLList")
    public String viewXMLList(Model model) {
        try {
            File[] listFile = new File(GlobalFinalAttr.XML_PATH).listFiles();
            model.addAttribute("listFile", listFile);
            model.addAttribute("fileType", "xml");
            return "listLog.jsp";
        } catch (Exception e) {
            log.error("获取日志列表错误.", e);
            return "index.jsp";
        }
    }

    /**
     * 查看日志
     */
    @RequestMapping("/viewLog")
    public void viewLog(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            String filePath = request.getParameter("logFilePath");
            String fileType = request.getParameter("fileType");
            if (StringUtils.isEmpty(filePath)) {
                out.print("获取日志错误!");
            } else {
                filePath = new String(filePath.getBytes("ISO-8859-1"), "UTF-8");
                File tempFile = new File((fileType.equals("xml") ? GlobalFinalAttr.XML_PATH : logHomeAdd)
                        + File.separatorChar + filePath);
                if (null != tempFile) {
                    List<String> stList = FileUtils.readLines(tempFile);
                    for (String str : stList) {
//						out.println(str+"<br/>");
                        out.println(str);
                    }
                } else {
                    out.print("获取日志错误!");
                }
            }
            out.println("</XMP>");
            out.println("</BODY>");
            out.println("</HTML>");
        } catch (Exception e) {
            out.println("读取日志错误" + e.getMessage());
            log.error("读取日志错误" + e.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * 列出所有档案用户 测试方法
     */
    @RequestMapping(value = "/listDaUsers", method = RequestMethod.GET)
    public String listDaUsers(Model model) {
        model.addAttribute("userlist", orgServiceImpl.listDaUsers());
        return "daUserlist.jsp";
    }
    /**
     * 列出所有OA用户 测试方法
     */
    @RequestMapping(value = "/listOAUsers", method = RequestMethod.GET)
    public String listOAUsers(Model model) {
        model.addAttribute("userlist", orgServiceImpl.listOaUsersLess4());
        return "oaUserlist.jsp";
    }

    /**
     * 单点登录
     */
    @RequestMapping(value = "/sso", method = RequestMethod.GET)
    public String sso(@RequestParam String usercode, @RequestParam String token) {
        return "redirect:" ;
    }


    /**
     * 流程过来的消息 需要发送到 邮件和待办
     */
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public void sendMsg(@RequestParam String userCodes, @RequestParam String varsJson, @RequestParam String actTaskID) {
        if (StringUtils.isNotEmpty(varsJson) && StringUtils.isNotEmpty(actTaskID)) {
            noticeServiceImpl.sendActivitiMsg(userCodes, varsJson, actTaskID);
        }
    }

    /**
     * 查看日志
     */
    @RequestMapping("/syncDclassfy")
    public void syncDclassfy(@RequestParam Integer libcode ,  HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            out.println(arcServcieImpl.syncDclassfy(libcode));
            out.println("</XMP>");
            out.println("</BODY>");
            out.println("</HTML>");
        } catch (Exception e) {
            out.println("读取日志错误" + e.getMessage());
            log.error("读取日志错误" + e.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * 同步组织机构
     */
    @RequestMapping("/syncUserGroup")
    public void syncUserGroup(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            out.println(orgServiceImpl.syncUserGroup());
            out.println("</XMP>");
            out.println("</BODY>");
            out.println("</HTML>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("读取日志错误" + e.getMessage());
            log.error("读取日志错误" + e.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }


    /**
     * 同步OA数据
     */
    @RequestMapping("/syncOaData")
    public void syncOaData(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            out.println(arcServcieImpl.syncOaData());
            out.println("</XMP>");
            out.println("</BODY>");
            out.println("</HTML>");
        } catch (Exception e) {
            out.println("读取日志错误" + e.getMessage());
            log.error("读取日志错误" + e.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }


    /**
     * 同步流程数据
     */
    @RequestMapping("/syncFlowData")
    public void syncFlowData(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            out.println(noticeServiceImpl.syncFLowBean());
            out.println("</XMP>");
            out.println("</BODY>");
            out.println("</HTML>");
        } catch (Exception e) {
            out.println("读取日志错误" + e.getMessage());
            log.error("读取日志错误" + e.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * 内部调用 判断是否是允许用户 ture是的
     */
    private Boolean judgeSSO(String usercode, String token) {
        Boolean result = false;
        return result;
    }

    @Autowired
    private OrgService orgServiceImpl;
    @Autowired
    private NoticeService noticeServiceImpl;
    @Autowired
    private ArcService arcServcieImpl;
    @Autowired
    @Value("${interface.log.home.address}")
    private String logHomeAdd;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
