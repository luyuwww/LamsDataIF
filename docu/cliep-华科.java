    /**
     * 电子文件集成
     */
    private void addEfile(List<Map<String , Object>> fjList , Integer pid , Integer libcodezzz) {
        String tableName = "E_FILE" + libcodezzz ;
        String euuid , docID , FJDM , eBizName,fjdm = "";

        for (Map<String, Object> dataMap : fjList) {
            euuid = dataMap.get("FJID") == null ? "" : MapUtils.getString(dataMap , "FJID");
            docID = dataMap.get("WJID") == null ? "" : MapUtils.getString(dataMap , "WJID");
            fjdm = dataMap.get("FJDM") == null ? "" : MapUtils.getString(dataMap , "FJDM");
            eBizName = dataMap.get("FJZWMC") == null ? "" : MapUtils.getString(dataMap , "FJZWMC");
            String downloadURL =  dataMap.get("DOWNLOADURL") == null ? "" : MapUtils.getString(dataMap , "DOWNLOADURL");

            if(StringUtils.isBlank(downloadURL)){
                continue;
            }
            String ext = FilenameUtils.getExtension(fjdm);
            String realyFileName = UUID.randomUUID() + "." + ext;

            String efilepath = basePath + File.separator + tableName + File.separator
                    + DateUtil.getCurrentDateStr4Dir() + File.separator+ realyFileName;
            try {
                HttpDownload.download(beforeURL+downloadURL, efilepath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            File newFile = new File(efilepath);
            //希尔说不存在再下载一次。我就醉了
            if(!newFile.exists()){
                try {
                    HttpDownload.download(beforeURL+downloadURL, efilepath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            File newFile = new File("d:/1.txt");
            if(!newFile.exists()){
                continue;
            }else{
                EFile eFile = new EFile();
                //DID,PID,EFILENAME,TITLE,EXT,PZM,PATHNAME,STATUS,ATTR,ATTREX,CREATOR,CREATETIME,FILESIZE,MD5,CONVERTSTATUS
                eFile.setDid(getMaxDid(tableName));
                eFile.setPid(pid);
                eFile.setEfilename(realyFileName);
                eFile.setExt(ext);
                eFile.setPzm(pzm);
                eFile.setPathname(ftpXdlj+efilepath);
                eFile.setStatus(0);
                eFile.setAttr(1);
                eFile.setAttrex(1);
                eFile.setCreator("ROOT");
                eFile.setCreatetime(new Date());
                eFile.setFilesize(((Long) newFile.length()).intValue() / 1000);
                eFile.setMd5(MD5.getFileMD5String(newFile));
                insertEfile(tableName , eFile);
            }
        }

    }