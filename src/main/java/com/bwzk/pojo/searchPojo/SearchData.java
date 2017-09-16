package com.bwzk.pojo.searchPojo;/**
 * Created by DaMo on 2017-09-16.
 */

import java.util.List;

/**
 * @author DaMo
 * @UPDATE 2017-09-16-18:59
 * @since 2017-09-16-18:59
 */
public class SearchData {

    /**
     * reponse : FAIL
     * total : 12121212
     * data : [{"id":"当前数据唯一标识","title":"题名+文件名","filetype":"电子文件格式（.doc.pdf.excel等）","date":"数据最后修改时间yyyy-MM-DD HH:mm:ss","content":"档案文件正文内容","fanwei":"归档范围","quanzong":"全宗类别","type":"档案分类","permission":"是否有权限控制01","users":"授权用户ID，多个用户使用,分割","groups":"档案分类","url":"档案查看浏览链接地址"},{"id":"当前数据唯一标识","title":"题名+文件名","filetype":"电子文件格式（.doc.pdf.excel等）","date":"数据最后修改时间yyyy-MM-DD HH:mm:ss","content":"档案文件正文内容","fanwei":"归档范围","quanzong":"全宗类别","type":"档案分类","permission":"是否有权限控制01","users":"授权用户ID，多个用户使用,分割","groups":"档案分类","url":"档案查看浏览链接地址"}]
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
}
