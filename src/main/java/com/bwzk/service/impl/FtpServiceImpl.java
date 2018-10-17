package com.bwzk.service.impl;


import ch.qos.logback.classic.Logger;
import com.bwzk.service.i.FtpService;
import com.bwzk.util.FtpDownloadFileException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.StringTokenizer;
@Service("ftpServcie")
public class FtpServiceImpl implements FtpService {
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());

    public FTPClient ftpClient;

    private String server;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void buildList(String pathList) throws Exception {
        StringTokenizer s = new StringTokenizer(pathList, " / "); // sign
        while (s.hasMoreElements()) {
            String theDir = s.nextElement().toString();
            theDir = new String(theDir.getBytes(LOCAL_CHARSET) , SERVER_CHARSET);
            try {
                if (ftpClient.makeDirectory(theDir)) {
                    ftpClient.changeWorkingDirectory(theDir);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean cdPath(String path) {
        try {
            return ftpClient.changeWorkingDirectory(path);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
           throw new RuntimeException("关闭FTP出现错误: " + e.getMessage());
        }
    }

    public void connectServer(String server, String user, String password, Integer port, String path) throws Exception {
        ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient.connect(server, port);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                close();
                throw new RuntimeException("连接FTP出现错误!");
            }
            if (!ftpClient.login(user, password)) {
                ftpClient.logout();
                throw new RuntimeException("登录FTP出现错误!");
            }
            // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                LOCAL_CHARSET = "UTF-8";
            }
            ftpClient.enterLocalPassiveMode();// 设置被动模式
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            buildList(path);
            ftpClient.changeWorkingDirectory(path);
        } catch (IOException e) {
            if (ftpClient.isConnected()) {
                close();
            }
            throw new RuntimeException(e);
        }

    }

    public void downloadFile(String path , String filename_in , String filename_out) throws FtpDownloadFileException {
        if(!exsitsFile(path,filename_in)){
            return;
        }
        if(null == ftpClient){
            throw new RuntimeException("FTP no login");
        }else{
            OutputStream fps = null;
            try {
            	File outFile = new File(filename_out);
        		FileUtils.touch(outFile);
                ftpClient.changeWorkingDirectory(path);
                ftpClient.setBufferSize(FTP_BUFFER_SIZE);
                fps = new FileOutputStream(outFile);
                ftpClient.retrieveFile(new String(filename_in.getBytes(LOCAL_CHARSET) , SERVER_CHARSET) , fps );
            } catch (Exception e) {
                throw new RuntimeException("down load ftp file error" , e);
            } finally {
                try {
                    fps.close();
                }  catch (Exception e) {
                    log.error(e.getMessage() , e);
                }
            }
        }
    }
    
    public Boolean downloadFile(String server, String user, String password, Integer port,
			  String path, String filename_in, String filename_out)  throws FtpDownloadFileException{
    	Boolean flag = Boolean.FALSE;
    	try{
	    	connectServer(server , user , password , port , path);
			File outFile = new File(filename_out);
			if(!outFile.exists()){//输出文件不存在 在下载
				downloadFile(path , filename_in , filename_out);//FPT下载到应用服务器
				flag = outFile.exists();
			}else{
				throw new RuntimeException(filename_out + " 文件已经存在");
			}
			
		} catch (Exception e) {
//			log.error("下载电子文件错误:" + e.getMessage());
            throw new FtpDownloadFileException("下载电子文件错误:" + e.getMessage());
		}finally{
			close();
		}
    	return flag;
    }
    
	public Boolean downloadFile(String server, String user, String password, Integer port,
			String namePath_in, String filename_out) throws FtpDownloadFileException{
		Boolean flag = Boolean.FALSE;
		String path = FilenameUtils.getFullPath(namePath_in);
		String fileName = FilenameUtils.getName(namePath_in);
		try{
	    	connectServer(server , user , password , port , path);
			File outFile = new File(filename_out);
			if(!outFile.exists()){//输出文件不存在 在下载
				downloadFile(path , fileName , filename_out);//FPT下载到应用服务器
				flag = outFile.exists();
			}else{
				throw new RuntimeException(filename_out + " 文件已经存在");
			}
		} catch (Exception e) {
			log.error("FTP下载错误:" + e.getMessage());
		}finally{
			close();
		}
		return flag;
	}

    public boolean upload(String filename_in, String filename_out) {
        if(null == ftpClient){
            throw new RuntimeException("FTP no login");
        }else{
            InputStream fis = null;
            try {
                File inF = new File(filename_in);
                if(inF.exists() && inF.isFile()){
                    fis = new FileInputStream(filename_in);
                    ftpClient.setBufferSize(FTP_BUFFER_SIZE);
                    filename_out = new String(filename_out.getBytes(LOCAL_CHARSET) , SERVER_CHARSET);
                    ftpClient.storeFile(filename_out , fis);
                }else{
                    throw new RuntimeException("要上传的文件不存在!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    
    public boolean upload(String server, String user, String password, Integer port,
			String filename_in, String filename_out)  throws Exception{
    	Boolean flag = Boolean.FALSE;
    	String path = FilenameUtils.getPath(filename_out);
    	try{
	    	connectServer(server , user , password , port , path);
	    	flag = upload(filename_in, filename_out);
		} catch (Exception e) {
			log.error("ftp生成水印错误:" + e.getMessage());
		}finally{
			close();
		}
    	return flag;
    }

    public Boolean exsitsFile(String path, String fileName) {
        Boolean flag = Boolean.FALSE;
        if(null == ftpClient){
            throw new RuntimeException("FTP no login");
        }else{
            try {
            	fileName = new String(fileName.getBytes(LOCAL_CHARSET) , SERVER_CHARSET);
                FTPFile[] ftpFiles = ftpClient.listFiles(path+fileName);
                flag = !(ftpFiles.length == 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 本地字符编码
     */
    private static String LOCAL_CHARSET = "GBK";

    // FTP协议里面，规定文件名编码为iso-8859-1
    private static String SERVER_CHARSET = "ISO-8859-1";
   //ftp上传下载的buffer size
    private static final  Integer FTP_BUFFER_SIZE = 10240;
}	