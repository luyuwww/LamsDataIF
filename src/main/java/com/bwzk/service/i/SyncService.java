package com.bwzk.service.i;

import com.bwzk.pojo.SUser;

import javax.jws.WebService;
import java.util.List;

public interface SyncService {
    /**
     * 档案写 信贷读  增加
     * @return
     */
   public String archive2lambdaADD();

    /**
     * 档案写  信贷读 删除
     * @return
     */
   public String archive2lambdaDEL();

    /**
     * 信贷写 档案读 增加
     * @return
     */
   public String lambda2ArchiveADD();

    /**
     * 信贷写 档案读   删除
     * @return
     */
   public String lambda2ArchiveDEL();
}
