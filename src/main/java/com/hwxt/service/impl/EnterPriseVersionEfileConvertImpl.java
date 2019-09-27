package com.hwxt.service.impl;

import com.hwxt.dao.JdbcDao;
import com.hwxt.dao.MiddleDao;
import com.hwxt.dao.i.SGroupMapper;
import com.hwxt.dao.i.SUserMapper;
import com.hwxt.pojo.EFile;
import com.hwxt.pojo.SDalx;
import com.hwxt.service.BaseService;
import com.hwxt.service.i.EnterPriseVersionEfileConvert;
import com.hwxt.util.MD5;
import com.hwxt.util.StrUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 * @UPDATE 2019/9/16-14:44
 * @since 2019/9/16-14:44
 */

@Service("eFileChangeService")
public class EnterPriseVersionEfileConvertImpl extends BaseService implements EnterPriseVersionEfileConvert {
    @Override
    public void starConvaerAll() {
        File existEfile = new File("c:/efileW/existEfile.html");
        String neExistEfileBasePath = "c:/efileW/no/";
        File noExtend = new File("c:/efileW/noExtend.html");
        Integer libcode = -1;
        List<SDalx> dalistList = sGroupMapper.getAllDalxList();
        for (SDalx sDalx : dalistList) {
            starConvertEfile(sDalx);
        }

    }

    public List<SDalx> allDalx() {
      return sGroupMapper.getAllDalxList();
    }

    public void converBylibcode(Integer libcode){
        SDalx dalx = sGroupMapper.getDalxBycode(libcode);
        starConvertEfile(dalx);
    }

    @Override
    public void starConvertEfile(SDalx dalx) {
        File existEfile = new File("c:/temp/existEfile.html");
        String neExistEfileBasePath = "c:/temp/no/";
        File noExtend = new File("c:/temp/noExtend.html");

        String dTbName = "D_FILE"+dalx.getCode();
        String eTbName = "E_FILE"+dalx.getCode();
        String dzwjm = queryForString("SELECT FIELDNAME FROM F_"+dTbName +" WHERE CHNAME='"+fieldChname+"'");
        if(StringUtils.isNotBlank(dzwjm)){
            String sql = "SELECT DID,PID,QZH,KEYWORD,TITLE,"+dzwjm+ " FROM " + dTbName
                    + " WHERE " + dzwjm + " IS NOT NULL AND STATUS=0 AND CATCHEFILESTATUS IS NULL";

            if(debug){   System.out.println(sql);  }
            List<Map<String, Object>> dzwjmList = middleDao.pageList(jdbcDao.getConn() , sql , 1, 50, "DID" , "DID");
            while (dzwjmList.size() > 0){
                if(debug){   System.out.println(dzwjmList.size() );  }
                for (Map<String, Object> dzwjMap : dzwjmList) {
                    String s =  dzwjMap.get(dzwjm).toString();
                    Integer DFILEDID = MapUtils.getInteger(dzwjMap , "DID");
                    if(StringUtils.isNotBlank(s)){

                        String qzh = MapUtils.getString(dzwjMap , "QZH");
                        List<String> result = StrUtil.getFileNameListNoPrint(StrUtil.getNoDataStr(FilenameUtils.normalize(s)));
                        for (String fileName : result) {
                            if(StringUtils.isNotBlank(fileName)
                                    && StringUtils.isNotBlank(FilenameUtils.getExtension(fileName))
                                    && !fileName.endsWith(".ZDY")){
                                String md5 = "";
                                Long fileSize = 1l;
                                if(checkFileExist){
                                    File f = new File(absPath + fileName);
                                    if(f.exists()){
                                        try {
                                            FileUtils.writeStringToFile(existEfile,qzh+ fileName +"<br/>" ,  true);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        fileSize = f.length();
                                        md5 = MD5.getFileMD5String(f);
                                    }else{
                                        try {
                                            FileUtils.writeStringToFile(new File(neExistEfileBasePath+qzh+"-"+dalx.getChname()+"-"+dalx.getCode()+".html"),dalx.getChname()
                                                    + "   :    " + (dzwjMap.get("TITLE") == null? "" : dzwjMap.get("TITLE").toString())
                                                    + "   :    " + (dzwjMap.get("KEYWORD") == null? "" : dzwjMap.get("KEYWORD").toString())
                                                    + "   :    " + qzh +  fileName +"<br/>" ,  true);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                EFile eFile = new EFile();
                                eFile.setDid(getMaxDid(eTbName));
                                eFile.setPid(DFILEDID);
                                eFile.setPathname(FilenameUtils.getPathNoEndSeparator(qzh+ fileName));
                                eFile.setTitle(FilenameUtils.getBaseName(qzh+ fileName));
                                eFile.setPzm(pzm);
                                eFile.setEfilename(FilenameUtils.getName(fileName));
                                eFile.setExt(FilenameUtils.getExtension(qzh+ fileName));
                                eFile.setMd5(md5);
                                eFile.setFilesize(fileSize.intValue());
                                insertEfile(eFile, dalx.getCode(), "interface");
                                execSql("UPDATE "+dTbName+" SET ATTACHED=1,CATCHEFILESTATUS=1 WHERE DID="+DFILEDID);
                            }else{
                                try {
                                    FileUtils.writeStringToFile(noExtend,qzh+ fileName +"<br/>" ,  true);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                execSql("UPDATE "+dTbName+" SET CATCHEFILESTATUS=1 WHERE DID="+DFILEDID);
                            }
                        }
                    }else{
                        execSql("UPDATE "+dTbName+" SET CATCHEFILESTATUS=1 WHERE DID="+DFILEDID);
                    }

                }
                dzwjmList = middleDao.pageList(jdbcDao.getConn() , sql , 1, 50, "DID" , "DID");
            }
        }
    }

    protected void  insertEfile(EFile efile, Integer libcode , String creator){
        String eFileTableName = "E_FILE" + libcode;
        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        Integer maxdid = getMaxDid(eFileTableName);
        try {
            fields.append("PID, PATHNAME,TITLE,PZM,EFILENAME,EXT,MD5,FILESIZE,XLH,");
            values.append(efile.getPid()).append(",'").append(efile.getPathname()).append("','");
            values.append(efile.getTitle().replace("."+efile.getExt(), "")).append("','").append(efile.getPzm()).append("','");
            values.append(efile.getEfilename()).append("','").append(efile.getExt()).append("','");
            values.append(efile.getMd5()).append("',").append(efile.getFilesize()).append(",'板式文件',");

            fields.append("CREATETIME,STATUS,ATTR,ATTREX,CREATOR,DID");
            values.append(getSysdate()).append(",0,").append(dfileAttr).append(",").append(dfileAttrex).append(",'");
            values.append(creator).append("',").append(maxdid);
            String SQL = "insert into " + eFileTableName + " (" + fields.toString()
                    + ") values ( " + values.toString() + " )";
            if(debug){   System.out.println(SQL );  }
            execSql(SQL);
        } catch (Exception e) {
            log.error(e.getMessage() ,  e);
        }
        fields.setLength(0);
        values.setLength(0);
    }




    protected String dfileAttr = "0";//归档前后  1未归档  0已归档
    protected String dfileAttrex = "1";//移交

    @Autowired
    private SUserMapper sUserMapper;


    /**
     * 转换时候判断文件是否存在
     */
    @Autowired
    @Value("${Enterprise.Efile.Convert.CheckFileExist}")
    protected Boolean checkFileExist;
    /**
     * 存放电子文件字段的
     */
    @Autowired
    @Value("${Enterprise.Efile.Convert.FieldChname}")
    protected String fieldChname;
    /**
     * 绝对路径
     */
    @Autowired
    @Value("${Enterprise.Efile.Convert.FilePath}")
    protected String absPath;

    /**
     * 绝对路径
     */
    @Autowired
    @Value("${Enterprise.Efile.Convert.pzm}")
    private String pzm ;
    @Autowired
    protected MiddleDao middleDao;
    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private SGroupMapper sGroupMapper;

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
