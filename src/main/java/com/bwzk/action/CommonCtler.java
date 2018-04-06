package com.bwzk.action;

import ch.qos.logback.classic.Logger;

import com.bwzk.service.i.ArcService;
import com.bwzk.service.i.SyncService;
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
import java.io.IOException;
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
     * 列出所有用户 测试方法
     */
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userlist", arcServcieImpl.listAllUser());
        return "userlist.jsp";
    }

    /**
     * 单点登录
     */
    @RequestMapping(value = "/sso", method = RequestMethod.GET)
    public String sso(@RequestParam String usercode, @RequestParam String token) {
        String lamsUrl = "http://" + arcServcieImpl.getLamsIP() + "/Lams/directLogin?usercode=";
        Boolean result = judgeSSO(usercode, token);
        if (result) {//返回0 表示成功
            lamsUrl = lamsUrl + usercode;
            log.error("验证成功可以登录档案系统!");
        } else {
            log.error("验证失败!");
        }
        return "redirect:" + lamsUrl;
    }


    /**
     * 查看日志
     */
    @RequestMapping("/syncDclassfy")
    public void syncDclassfy(@RequestParam Integer libcode, HttpServletResponse response) {
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
     * 档案写信贷系统 add
     */
    @RequestMapping(value = "/archive2lambdaADD", method = RequestMethod.GET)
    public void archive2lambdaADD(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            out.println(syncService.archive2lambdaADD());
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
     * 档案写信贷系统 del
     */
    @RequestMapping(value = "/archive2lambdaDEL", method = RequestMethod.GET)
    public void archive2lambdaDEL(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            out.println(syncService.archive2lambdaDEL());
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
     * 信贷系统写档案 add
     */
    @RequestMapping(value = "/lambda2ArchiveADD", method = RequestMethod.GET)
    public void lambda2ArchiveADD(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            out.println(syncService.lambda2ArchiveADD());
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
     * 信贷系统写档案 del
     */
    @RequestMapping(value = "/lambda2ArchiveDEL", method = RequestMethod.GET)
    public void lambda2ArchiveDEL(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            out.println(syncService.lambda2ArchiveDEL());
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
     * 获取访问者的IP
     */
    @RequestMapping(value = "/getClientIp", method = RequestMethod.GET)
    public void getClientIp(HttpServletRequest request, HttpServletResponse response,@RequestParam String getKey, @RequestParam String random) {
    
    	if (!getKey.equals("hams@2017")){
    		return ;
    	}
    	
    	PrintWriter out = null;
      
            response.setContentType("text/html;charset=GBK ");
            try {
				out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
        	out.println(ip);   
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
           int index = ip.indexOf(',');
            if (index != -1) {
            	
            } else {
            	out.println(ip);   
            }
        } else {
        	out.println( request.getRemoteAddr());
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
    private ArcService arcServcieImpl;

    @Autowired
    private SyncService syncService;
    @Autowired
    @Value("${interface.log.home.address}")
    private String logHomeAdd;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
