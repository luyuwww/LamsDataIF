package com.bwzk.pojo.searchPojo;/**
 * Created by DaMo on 2017-09-29.
 */

import java.util.List;

/**
 * @author DaMo
 * @UPDATE 2017-09-29-1:10
 * @since 2017-09-29-1:10
 */
public class DelDatas {

    /**
     * reponse : UCCESS
     * total : 121212
     * data : [{"id":"唯一标识","type":"数据所属模块","date":"数据删除时间yyyy-MM-DD HH:mm:ss"}]
     */

    private String reponse;
    private int total;
    private List<DelBean> data;

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DelBean> getData() {
        return data;
    }

    public void setData(List<DelBean> data) {
        this.data = data;
    }
}
