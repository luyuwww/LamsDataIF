package com.hwxt.action;

import com.hwxt.pojo.EFile;
import com.hwxt.pojo.SFwqpz;
import com.hwxt.service.BaseService;
import com.hwxt.util.DateUtil;
import com.hwxt.util.MD5;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * Created by luyuwww on 2015-10-13.
 */
@Controller
public class FileUploadCtler {
    /**
     * 接收http上传的电子文件
     */
    @RequestMapping(value = "/uploadEfile", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String upload(@RequestParam("Filedata") MultipartFile file,
                         @RequestParam("title") String title,
                         @RequestParam("tableName") String tableName,
                         @RequestParam("fileDID") Integer fileDID,
                         @RequestParam("pzm") String pzm,
                         @RequestParam("upUserCode") String upUserCode) {

        SFwqpz fwqpz = null;
        String result = null;
        String ext, md5Str, efilepath, realyFileName, uploadFileName = null;
        //电子文件不可以为, 电子文件的pid不为空
        if (null != file && StringUtils.isNotBlank(pzm) && fileDID != null) {
            uploadFileName = file.getOriginalFilename();
            try {
                fwqpz = baseService.getFwqpz(pzm);
                if (StringUtils.isBlank(uploadFileName) || !file.getOriginalFilename().contains(".") || fileDID < 0) {
                    throw new RuntimeException("上传的电子文件名字为空,或无后缀,或者pid小于0");
                }
                ext = FilenameUtils.getExtension(uploadFileName);
                realyFileName = UUID.randomUUID() + "." + ext;

                efilepath = File.separator + tableName + File.separator
                        + DateUtil.getCurrentDateStr() + File.separator;
                File newFile = new File(FilenameUtils.getFullPath((StringUtils.isBlank(fwqpz.getSavedbname())
                        ? tempFilePath : fwqpz.getSavedbname()) + efilepath) + realyFileName);
                // 路径类似: D:\flvVideo\D_FILE2\84\2013-09-22\6cf7d800-6d69-4a9d-b886-c702445c9295.png
                if (!newFile.exists()) {
                    FileUtils.touch(newFile);
                }
                file.transferTo(newFile);

                EFile eFile = new EFile();
                //DID,PID,EFILENAME,TITLE,EXT,PZM,PATHNAME,STATUS,ATTR,ATTREX,CREATOR,CREATETIME,FILESIZE,MD5,CONVERTSTATUS
                eFile.setDid(baseService.getMaxDid(tableName));
                eFile.setPid(fileDID);
                eFile.setEfilename(realyFileName);
                eFile.setTitle(StringUtils.isNotBlank(title) ? title : FilenameUtils.getBaseName(uploadFileName));
                eFile.setExt(ext);
                eFile.setPzm(pzm);
                eFile.setPathname(efilepath);
                eFile.setStatus(baseService.getStatus());
                eFile.setAttr(baseService.getAttr());
                eFile.setAttrex(baseService.getAttrex());
                eFile.setCreator(StringUtils.isNotBlank(upUserCode) ? upUserCode : "ROOT");
                eFile.setCreatetime(new Date());
                eFile.setFilesize(((Long) file.getSize()).intValue() / 1000);
                eFile.setMd5(MD5.getFileMD5String(newFile));
                baseService.insertEfile(tableName, eFile);
                result = eFile.getDid() + "&success";
            } catch (Exception e) {
                e.printStackTrace();
                result = "-1&paramaterError" + e.getMessage();
            }
        } else {
            result = "-1&paramaterMissError";
        }
        return result;
    }

    @Autowired
    @Value("${file.temp.savepath}")
    private String tempFilePath;

    @Autowired
    private BaseService baseService;
}
