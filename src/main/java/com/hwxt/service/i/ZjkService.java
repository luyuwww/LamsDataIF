package com.hwxt.service.i;

import com.hwxt.pojo.MidDbs;

import javax.jws.WebParam;
import java.util.List;

public interface ZjkService {
    public List<MidDbs> listAllMidDbs();
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
