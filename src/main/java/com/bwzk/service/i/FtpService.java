package com.bwzk.service.i;

import com.bwzk.util.FtpDownloadFileException;

public interface FtpService {

	public String getServer();
	/**
	 * 连接ftp服务器
	 * @param server 服务器IP地址
	 * @param user 登录用户
	 * @param password 密码
	 * @param port 端口
	 * @param path 服务器主目录下目录名(文件夹名)
	 * @throws Exception
	 */
	public void connectServer(String server, String user, String password, Integer port,
                              String path) throws Exception;

	/**
	 * 关闭ftp连接
	 */
	public void close();

	/**
	 * 在FTP服务器上建立指定的目录,当目录已经存在的情下不会影响目录下的文件,这样用以判断FTP
	 * 上传文件时保证目录的存在目录格式必须以"/"根目录开头
	 * @param pathList String
	 * @throws Exception
	 */
	public void buildList(String pathList) throws Exception;

	/**
	 * 进入目录
	 * @param path
	 * @return
	 */
	public boolean cdPath(String path);

	/**
	 * 文件上传
	 * @param path FTP服务器上的路径
	 * @param filename_in 本地文件名(包括路径地址)
	 * @param filename_out 写入到服务器上文件名
	 * @throws Exception
	 */
	public boolean upload(String filename_in, String filename_out)  throws Exception;
	public boolean upload(String server, String user, String password, Integer port,
                          String filename_in, String filename_out)  throws Exception;

	/**
	 * 文件下载
	 * @param path FTP服务器上的路径
	 * @param filename_in 服务器上的文件名
	 * @param filename_out 下载到本地的文件名
	 */
	public void downloadFile(String path, String filename_in, String filename_out)  throws FtpDownloadFileException;

	public Boolean downloadFile(String server, String user, String password, Integer port,
                                String path, String filename_in, String filename_out)  throws FtpDownloadFileException;
	public Boolean downloadFile(String server, String user, String password, Integer port,
                                String namePath_in, String filename_out)  throws FtpDownloadFileException;
	
	/**
	 * 判断文件是否存在
	 * @param path
	 * @param fileName
	 */
	public Boolean exsitsFile(String path, String fileName) ;
}
