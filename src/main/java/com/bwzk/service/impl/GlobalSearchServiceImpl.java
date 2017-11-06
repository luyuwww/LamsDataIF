
package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.JdbcDaoImpl;
import com.bwzk.dao.i.SUserMapper;
import com.bwzk.pojo.SDalx;
import com.bwzk.pojo.SHsz;
import com.bwzk.pojo.SQzh;
import com.bwzk.pojo.SUser;
import com.bwzk.pojo.jaxb.Field;
import com.bwzk.pojo.jaxb.Table;
import com.bwzk.pojo.searchPojo.*;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.GlobalSearchService;
import com.bwzk.service.i.SingleService;
import com.bwzk.util.DateUtil;
import com.bwzk.util.GlobalFinalAttr;
import com.bwzk.util.XmlObjUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("globSearch")
@WebService(name = "GlobalSearch", targetNamespace = "http://service.unis.com/")
public class GlobalSearchServiceImpl extends BaseService implements GlobalSearchService {

    public static List<SDalx> dalxList = null;
    public static List<SQzh> qzhList = null;

    @WebMethod
    @WebResult
    public String GetData(@WebParam(name = "startTime") String startTime
            , @WebParam(name = "endTime") String endTime
            , @WebParam(name = "type") String type
            , @WebParam(name = "page") Integer page
            , @WebParam(name = "pageSize") Integer pageSize) {
        SearchData sd = search(startTime, endTime, type, page, pageSize);
        String result = "";
        try {
            result = new ObjectMapper().writeValueAsString(sd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getDelData(String type, String startTime, String endTime) {
        return getDelDates(type, startTime, endTime);
    }

    @Override
    public String getDelDataIgnoreType(String startTime, String endTime) {
        return getDelDates("%", startTime, endTime);
    }

    @WebMethod
    @WebResult
    public String Permission() {
        Permission pm = getPermissionByusercode();
        String result = "";
        try {
            result = new ObjectMapper().writeValueAsString(pm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从回收站获取删除的电子文件数据
     *
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    private String getDelDates(String type, String startTime, String endTime) {
        String result = "";
        DelDatas dds = new DelDatas();
        List<DelBean> dbList = new ArrayList<>();
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(startTime) &&
                StringUtils.isNotBlank(endTime)) {
            try {
                List<SHsz> hszLsit = sUserMapper.queryHsz(type, startTime, endTime);
                dds.setTotal(hszLsit.size());
                dds.setReponse(GlobalFinalAttr.RSP_SUCCESS);
                for (SHsz hsz : hszLsit) {
                    DelBean db = new DelBean();
                    db.setId(hsz.getSrctenname() + "_" + hsz.getSrcid());
                    db.setType(hsz.getLibname());
                    db.setDate(new DateTime(hsz.getDeltime()).toString(S_DATE_FMT));
                    dbList.add(db);
                }
                dds.setData(dbList);
            } catch (Exception e) {
                dds.setTotal(0);
                dds.setReponse(GlobalFinalAttr.RSP_FAIL);
                dds.setData(dbList);
                e.printStackTrace();
            }
        } else {
            dds.setTotal(0);
            dds.setReponse(GlobalFinalAttr.RSP_FAIL);
            dds.setData(dbList);
        }
        try {
            result = new ObjectMapper().writeValueAsString(dds);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Permission getPermissionByusercode() {
        Integer jsid = null;
        Integer flag = 1;
        Permission pm = new Permission();
        try {
            List<SUser> userList = sUserMapper.getAllUserList();
            List<Permission.DataBean> dbs = new ArrayList<>();

            for (SUser user : userList) {
                if (user.getUsercode().equalsIgnoreCase("ROOT") || user.getPid().equals(-1)) {
                    continue;
                } else {
                    jsid = sUserMapper.getUserRoleDidsByCode(user.getUsercode());
                    if (null != jsid && jsid >= 0) {
                        Permission.DataBean db = new Permission.DataBean();
                        db.setUserid(user.getUsercode());
                        List<SDalx> dList = sUserMapper.getDalxListByUserCodeIgnoreUnit(jsid);
                        String groups = "-1";
                        if (null != dList && dList.size() >= 1) {
                            for (SDalx d : dList) {
                                groups += ("," + d.getCode());
                            }
                        }
                        db.setGroups(groups.replace("-1,", ""));
                        dbs.add(db);
                        flag++;
                    }
                }
            }

            pm.setReponse(GlobalFinalAttr.RSP_SUCCESS);
            pm.setTotal(flag);
            pm.setData(dbs);
        } catch (Exception e) {
            pm.setReponse(GlobalFinalAttr.RSP_FAIL);
            e.printStackTrace();
        }
        return pm;
    }

    private SearchData search(String startTime, String endTime, String type
            , Integer page, Integer pageSize) {
        SearchData sd = new SearchData();

        Long sL = new DateTime(DateUtil.strToDate(startTime, S_DATE_FMT)).getMillis();
        Long eL = new DateTime(DateUtil.strToDate(endTime, S_DATE_FMT)).getMillis();

        List<DataBean> datas = new ArrayList<>();
        IndexSearcher searcher = null;
        IndexReader mainIndexReader = null;
        BooleanQuery bQuery = null;
        StandardAnalyzer analyzer = new StandardAnalyzer(LUCEN_VERSION);
        QueryBuilder searchQueryBuilder = new QueryBuilder(analyzer);
        try {
            mainIndexReader = DirectoryReader.open(FSDirectory.open(new File(luceneIndexPath + "mainIndex")));
            searcher = new IndexSearcher(mainIndexReader);
            NumericRangeQuery nq = NumericRangeQuery.newLongRange(CREATETIME, sL,
                    eL, false, true);


            BooleanQuery all = new BooleanQuery();

            BooleanQuery dalxQuery = new BooleanQuery();
            QueryParser parser = null;

            /**************************************************************/

//            List<SDalx> dalxList = getDalxList();
//
//            for (SDalx dalx : dalxList) {
//                parser = new QueryParser(LUCEN_VERSION, "LIBCODE", analyzer);
//                //判断是OR还是AND来生产不同的解析式
//                parser.setDefaultOperator(QueryParser.OR_OPERATOR);
//                dalxQuery.add(parser.parse(dalx.getCode().toString()), BooleanClause.Occur.SHOULD);
//            }
//            all.add(new BooleanClause(dalxQuery, BooleanClause.Occur.MUST));
            all.add(new BooleanClause(nq, BooleanClause.Occur.MUST));

            TopDocs topDocs = null;
            BooleanQuery filterQuery = new BooleanQuery();
            QueryParser filterParse = new QueryParser(LUCEN_VERSION, "DLEVEL", analyzer);
            filterQuery.add(filterParse.parse(type), BooleanClause.Occur.MUST);
            SortField sortField = new SortField(CREATETIME, SortField.Type.STRING);
            topDocs = searcher.search(all, new QueryWrapperFilter(filterQuery), mainIndexReader.maxDoc() ,
                    new Sort(new SortField(null,  SortField.Type.DOC,false)));
            int breakFlag = 0;
            int docID = ((page - 1) * pageSize);
            for (int i = docID; (breakFlag < pageSize && i < topDocs.totalHits); i++) {
                Document doc = null;
                DataBean db = new DataBean();
                try {
                    doc = searcher.doc(topDocs.scoreDocs[i].doc);//INDEX_
                    SDalx dalx = getDalx(doc.get("LIBCODE"));
                    Map<String, Object> dfileMap = queryDfile(doc.get("EID"), doc.get("LIBCODE"));
                    String id = doc.get("TABLENAME") + "_" + doc.get("EID");
                    db.setId(id);
                    db.setTitle(MapUtils.getString(dfileMap, "TITLE") + " | " + doc.get("TITLE"));
                    db.setFiletype(doc.get("EXT"));
                    if (StringUtils.isNotBlank(doc.get("CONTEXT"))) {
                        //如果文档是压缩文件 就返回一个开头给搜索引擎
                        if (StringUtils.isNotBlank(doc.get("EXT")) &&
                                (doc.get("EXT").toLowerCase().equals("zip")
                                        || doc.get("EXT").toLowerCase().equals("ios")
                                        || doc.get("EXT").toLowerCase().equals("rar")
                                        || doc.get("EXT").toLowerCase().equals("tar")
                                )
                                ) {
                            db.setContent(doc.get("CONTEXT").substring(0, 1024).replaceAll("[ 　\n\b\r\t\u0000]", ""));
                        } else {
                            if(doc.toString().length()  > (1024*1024*3)){
                                db.setContent(doc.get("CONTEXT").substring(0 , (1024*1024*1))
                                        .replaceAll("[ 　\n\b\r\t\u0000]", ""));
                            }else{
                                db.setContent(doc.get("CONTEXT").replaceAll("[ 　\n\b\r\t\u0000]", ""));
                            }
                        }
                    } else {
                        db.setContent("");
                    }
                    if (StringUtils.isNotBlank(doc.get("CREATETIME"))) {
                        try {
                            db.setDate(new DateTime(Long.parseLong(doc.get("CREATETIME")))
                                    .toString(S_DATE_FMT));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                    db.setUsers("");
                    db.setPermission(1);
                    if (null != dalx) {
                        db.setType(dalx.getChname());
                        db.setGroups(dalx.getCode().toString());
                    } else {
                        db.setGroups("");
                    }

                    if (null != dfileMap) {
                        db.setFanwei(StringUtils.isNotBlank(MapUtils.getString(dfileMap, "FLNAME"))
                                ? MapUtils.getString(dfileMap, "FLNAME") : "");
                        if (StringUtils.isNotBlank(MapUtils.getString(dfileMap, "QZH"))) {
                            SQzh sqzh = getSQzh(MapUtils.getString(dfileMap, "QZH"));
                            if(sqzh != null){
                                db.setQuanzong(sqzh.getQzmc());
                            }else{
                                db.setQuanzong("THAMS");
                            }
                        } else {
                            db.setQuanzong("");
                        }
                    }
//                http://localhost:81/Lams/rest/restLoadFile?libcode=6&level=2&efiledid=1565285&convertStatus=0
                    db.setUrl("http://" + lamsIP + "/Lams/globalSearch/singleViewEfile"
                            + "?libcode=" + dalx.getCode()
                            + "&efiledid=" + doc.get("EID")
                            + "&randon=" + Math.random()
                            + "&usercode=");
                    datas.add(db);
                    breakFlag++;
                } catch (Error e) {
                    System.out.println(i);
                    e.printStackTrace();
                }
            }
            sd.setTotal(topDocs.totalHits);
            sd.setData(datas);
            sd.setReponse(GlobalFinalAttr.RSP_SUCCESS);
        } catch (Exception e) {
            sd.setReponse(GlobalFinalAttr.RSP_FAIL);
            e.printStackTrace();
        } finally {
            try {
                mainIndexReader.close();
            } catch (Exception e) {
                log.error("这里需要处理释放的资源异常");
            }
        }
        return sd;
    }


    private Map<String, Object> queryDfile(String eid, String libcode) {
        Map<String, Object> rslt = null;
        String sql = "QZH,TITLE,KEYWORD FROM D_FILE"
                + libcode + " WHERE DID = (SELECT PID FROM E_FILE" + libcode
                + " WHERE DID=" + eid + ")";
        try {
            if (super.existColumn("D_FILE" + libcode, "FLNAME") && !libcode.equals("15")) {
                rslt = super.queryForMap("SELECT FLNAME," + sql);
            } else {
                rslt = super.queryForMap("SELECT " + sql);
            }
        } catch (Exception e) {
            log.error(e.getMessage() + ": " + sql);
            rslt = new HashMap<>();
        }
        return rslt;
    }

    private SDalx getDalx(String libcode) {
        SDalx d = null;
        List<SDalx> dList = getDalxList();
        for (SDalx sDalx : dList) {
            if (libcode.equalsIgnoreCase(sDalx.getCode().toString())) {
                d = sDalx;
                break;
            }
        }
        return d;
    }

    private SQzh getSQzh(String qzhStr) {
        SQzh q = null;
        List<SQzh> qList = getQzhList();
        for (SQzh qzh : qList) {
            if (qzhStr.equalsIgnoreCase(qzh.getQzh())) {
                q = qzh;
                break;
            }
        }
        return q;
    }

    private List<SDalx> getDalxList() {
        if (null == dalxList) {
            dalxList = sUserMapper.getAllDalxList();
        }
        return dalxList;
    }

    private List<SQzh> getQzhList() {
        if (null == qzhList) {
            qzhList = sUserMapper.listQzh();
        }
        return qzhList;
    }

    @Autowired
    private SUserMapper sUserMapper;
    public static final Version LUCEN_VERSION = Version.LUCENE_4_9;//默认lucene的版本
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
    @Autowired
    @Value("${lucene.index.path}")
    private String luceneIndexPath;//luceneIndexPath
    @Autowired
    @Value("${lams.ip}")
    protected String lamsIP;
    private static final String DATE_FMT = "yyyyMMddHHmmss";
    private static final String S_DATE_FMT = "yyyy-MM-dd HH:mm:ss";
    private static final String CREATETIME = "CREATETIME";
}
