package com.hwxt.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class StrUtil {

	/**
	 * \\data\\文书档案\\2002-2-510-001-<001-007>.jpg"<br>
	 * 返回 \文书档案\2002-2-510-001-<001-007>.jpg
	 * 
	 * @param s
	 * @return
	 */
	public static String getNoDataStr(String s) {
		if (s.contains("data")) {
			return s.substring(s.indexOf("data") + 4, s.length());
		} else {
			return s;
		}
	}
	public static List<String> getFileNameListNoPrint(String s) {
		List<String> fileList = new ArrayList<String>();
		String fullpath = FilenameUtils.getFullPath(s);///data\文书档案\
		String fileName = FilenameUtils.getName(s);//JOA05国华电党〔2007〕1号-01.pdf;02.tif;03.tif;04.tif;05.pdf
		
		if(s.contains("<") && s.contains(">") && !s.contains(";")){
			fileList.addAll(getJkh(fullpath + fileName));
		}else if(s.contains(";") && !s.contains(";$")){//文〔2005〕12号-01.pdf;02.tif;03.pdf
			String[] willSubArray = StringUtils.split(fileName, ";");
			if(isMulti(willSubArray)){
				String theBase = fileName.split("-")[0]+"-";
				willSubArray = StringUtils.split(fileName.split("-")[1], ";");
				for (String str : willSubArray) {
					fileList.add(fullpath + theBase + str);
				}
			}else{
				for (String str : willSubArray) {
					if (str.contains("<") && str.contains(">")) {
						fileList.addAll(getJkh(fullpath + str));
					}else{
						fileList.add(fullpath + str);
					}
				}
			}
		}else if(s.contains(";$")){
			String[] willSubArray = StringUtils.split(fileName , ";$");
			for (String str : willSubArray) {
				if (str.contains("<") && str.contains(">")) {
					fileList.addAll(getJkh(fullpath + str));
				}else{
					fileList.add(fullpath  + str);
				}
			}
		}else{
			fileList.add(fullpath + fileName);
		}
		return fileList;
	}
	
	/**
	 * 处理尖括号
	 * @param 
	 * @return
	 */
	private static List<String> getJkh(String s){
		String ext = FilenameUtils.getExtension(s);
		String baseName = s.substring(0 ,  s.indexOf("<"));
		
		List<String> fileList = new ArrayList<String>();
		String lr = s.substring(s.indexOf("<"), s.indexOf(">") + 1);
		List<String> splitStr = subList(lr);
		for (String str : splitStr) {
			fileList.add(baseName + str+"."+ext);
		}
		return fileList;
	}
	/**
	 * 将 <001-003>变成 001 002 003 List 补零
	 */
	private static List<String> subList(String s) {
		List<String> sublist = new ArrayList<String>();
		String noLr = s.substring(1, s.length() - 1);
		String starStr = noLr.split("-")[0];
		Integer starInt = Integer.parseInt(starStr);
		Integer endInt = Integer.parseInt(noLr.split("-")[1]);
		for (Integer i = starInt; i <= endInt; i++) {
			sublist.add(returnAddedZero(i.toString() , starStr.length()));
		}
		return sublist;
	}
	
	/**
	 * 补零操作
	 * @param num 要补零的字符
	 * @param length 位数
	 * @return str
	 */
	public static String returnAddedZero(String str,int length){
		if(length==0){
			return str;
		}
		while(str.length()<length){
			str = "0"+str;
		}
        return str;
	}
	
	public static Boolean isMulti(String[] arr){
		Boolean result= arr.length >= 2 && arr[0].contains("-")&& !arr[1].contains("-");
		return result;
	}
	
	@Deprecated
	public static List<String> getFileNameList(String s) {
		List<String> fileList = new ArrayList<String>();
		String fullpath = FilenameUtils.getFullPath(s);
		if(s.contains("JOA05国华置业综〔2006〕3号-01.pdf;02.tif;03.pdf")){
			System.out.println(s);
		}
		if (!s.contains(";$") && s.contains(";") && s.contains("-")) {//包含 ; 和-  但是不是&;
			String willSub = s.replace(fullpath, "");
			String baseName = StringUtils.split(willSub, "-")[0];
			String[] willSubArray = StringUtils.split(StringUtils.split(willSub, "-")[1], ";");
			for (String str : willSubArray) {
				if(str.contains(";") || str.contains("<")){
					fileList.addAll(getFileNameList(fullpath + str));
				}else{
					System.out.println(fullpath + baseName + "-" + str);
					fileList.add(fullpath + baseName + "-" + str);
				}
			}
		}else if (!s.contains(";$") && s.contains(";") && !s.contains("-")) {//包含 ; 没有 -  但是不是&;
			String willSub = s.replace(fullpath, "");
			String[] willSubArray = StringUtils.split(willSub, ";");
			for (String str : willSubArray) {
				if(str.contains(";") || str.contains("<")){
					fileList.addAll(getFileNameList(fullpath + str));
				}else{
					fileList.add(fullpath + str);
				}
			}
		} else if (s.contains(";$")) {//包含&;
			String willSub = s.replace(fullpath, "");
			String[] willSubArray = StringUtils.split(willSub, ";$");
			for (String str : willSubArray) {             
				if(str.contains(";") || str.contains("<")){
					fileList.addAll(getFileNameList(fullpath + str));
				}else{
					fileList.add(fullpath + str);
				}
			}
		} else if (s.contains("<") && s.contains(">")) {//包含<>
			String lr = s.substring(s.indexOf("<"), s.indexOf(">") + 1);
			List<String> splitStr = subList(lr);
			for (String str : splitStr) {
				if(str.contains(";") || str.contains("<")){
					fileList.addAll(getFileNameList(fullpath + str));
				}else{
					fileList.add(fullpath + str);
				}
			}
		} else {
			fileList.add(s);
		} 
		return fileList;
	}
}
