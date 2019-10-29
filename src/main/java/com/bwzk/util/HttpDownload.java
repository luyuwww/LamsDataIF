package com.bwzk.util;

import org.apache.commons.io.FileUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author DaMo
 * @UPDATE 2019/10/29-0:55
 * @since 2019/10/29-0:55
 */
public class HttpDownload {
    public static final int cache = 10 * 1024;
    public static final boolean isWindows;
    public static final String splash;
    public static final String root;
    static {
        if (System.getProperty("os.name") != null && System.getProperty("os.name").toLowerCase().contains("windows")) {
            isWindows = true;
            splash = "\\";
            root="D:";
        } else {
            isWindows = false;
            splash = "/";
            root="/search";
        }
    }

    /**
     * 根据url下载文件，文件名从response header头中获取
     * @param url
     * @return
     */
    public static String download(String url) {
        return download(url, null);
    }

    /**
     * 根据url下载文件，保存到filepath中
     * @param url
     * @param filepath
     * @return
     */
    public static String download(String url, String filepath) {

        HttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        InputStream is = null;
        FileOutputStream fileout = null;
        try {
            HttpResponse response = client.execute(httpget);

            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            if (filepath == null)
                filepath = getFilePath(response);
            File file = new File(filepath);
            FileUtils.touch(file);
            fileout = new FileOutputStream(file);
            /**
             * 根据实际运行效果 设置缓冲区大小
             */
            byte[] buffer=new byte[cache];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer,0,ch);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(fileout != null){
                try {
                    fileout.flush();
                    fileout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
        return null;
    }
    /**
     * 获取response要下载的文件的默认路径
     * @param response
     * @return
     */
    public static String getFilePath(HttpResponse response) {
        String filepath = root + splash;
        String filename = getFileName(response);

        if (filename != null) {
            filepath += filename;
        } else {
            filepath += getRandomFileName();
        }
        return filepath;
    }
    /**
     * 获取response header中Content-Disposition中的filename值
     * @param response
     * @return
     */
    public static String getFileName(HttpResponse response) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        //filename = new String(param.getValue().toString().getBytes(), "utf-8");
                        //filename=URLDecoder.decode(param.getValue(),"utf-8");
                        filename = param.getValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filename;
    }
    /**
     * 获取随机文件名
     * @return
     */
    public static String getRandomFileName() {
        return String.valueOf(System.currentTimeMillis());
    }
    public static void outHeaders(HttpResponse response) {
        Header[] headers = response.getAllHeaders();
        for (int i = 0; i < headers.length; i++) {
            System.out.println(headers[i]);
        }
    }

    public static void main(String[] args) {
        String url = "http://33281.url.9xiazaiqi.com/xiaz/oracle%209i%20%E6%95%B0%E6%8D%AE%E5%BA%93%E9%9B%86%E6%88%90%E8%BD%AF%E4%BB%B6%E5%8C%85%20%E5%AE%98%E6%96%B9%E4%B8%AD%E6%96%87%E7%89%88(%E9%99%84%E5%AE%89%E8%A3%85%E6%95%99%E7%A8%8B)%2032%E4%BD%8D/64%E4%BD%8D@156_568422.exe";
        String filepath = "d:/1/2//1/4/doubaidouba.exe";
        HttpDownload.download(url, filepath);
    }
}
