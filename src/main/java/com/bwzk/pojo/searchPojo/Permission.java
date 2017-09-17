package com.bwzk.pojo.searchPojo;/**
 * Created by DaMo on 2017-09-17.
 */

import java.util.List;

/**
 * @author DaMo
 * @UPDATE 2017-09-17-23:40
 * @since 2017-09-17-23:40
 */
public class Permission {

    /**
     * reponse : 响应状态SUCCESS/FAIL
     * total : 命中总数据量
     * data : [{"userid":"用户标识（username）","groups":"用户拥有的授权查看分类，多个使用逗号分割"}]
     */

    private String reponse;
    private Integer total;
    private List<DataBean> data;

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userid : 用户标识（username）
         * groups : 用户拥有的授权查看分类，多个使用逗号分割
         */

        private String userid;
        private String groups;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getGroups() {
            return groups;
        }

        public void setGroups(String groups) {
            this.groups = groups;
        }
    }
}
