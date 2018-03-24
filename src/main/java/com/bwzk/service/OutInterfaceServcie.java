package com.bwzk.service;

/**
 * 提供外部系统接口调用的接口<br>
 * 提供最大值(did)
 * Created by www on 2015-08-28.
 */
public interface OutInterfaceServcie {

    public Integer getMaxByField(String tableName, String field);

    public Integer getMaxDid(String tableName);
}
