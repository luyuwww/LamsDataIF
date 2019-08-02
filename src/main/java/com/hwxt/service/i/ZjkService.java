package com.hwxt.service.i;

import com.hwxt.pojo.MidDbs;
import com.hwxt.pojo.MidTab;

import java.util.List;

public interface ZjkService {
    public List<MidDbs> listAllMidDbs();
    public List<MidTab> listMidTablsByDbsId(Integer midDbsDid);
    /**
     * 测试连接
     * @param url
     * @param dbType
     * @param dbName
     * @param username
     * @param password
     * @return
     */
    public Boolean testConn(Integer midDbsDID);

    public void startSyncAll();

    /**
     *
     * @param midDbsDID
     */
    public void startSyncOneDBS(Integer midDbsDID);
}
