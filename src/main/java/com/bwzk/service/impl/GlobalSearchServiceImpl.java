
package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.JdbcDaoImpl;
import com.bwzk.dao.i.SUserMapper;
import com.bwzk.pojo.SDalx;
import com.bwzk.pojo.jaxb.Field;
import com.bwzk.pojo.jaxb.Table;
import com.bwzk.pojo.searchPojo.DataBean;
import com.bwzk.pojo.searchPojo.Permission;
import com.bwzk.pojo.searchPojo.SearchData;
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

    @WebMethod
    @WebResult
    public String GetData(@WebParam(name = "startTime") String startTime
            , @WebParam(name = "endTime") String endTime
            , @WebParam(name = "type") String type
            , @WebParam(name = "page") Integer page
            , @WebParam(name = "pageSize") Integer pageSize) {
        SearchData sd = search(startTime , endTime , type , page ,pageSize);
        String result = "";
        try {
            result = new ObjectMapper().writeValueAsString(sd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @WebMethod
    @WebResult
    public String Permission(@WebParam(name = "usercode") String usercode){
        Permission pm = getPermissionByusercode(usercode);
        String result = "";
        try {
            result = new ObjectMapper().writeValueAsString(pm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Permission getPermissionByusercode(String usercode){
        Permission pm = new Permission();
        Integer jsid = null;
        try {
            jsid = sUserMapper.getUserRoleDidsByCode(usercode);
            List<SDalx> dList = sUserMapper.getDalxListByUserCodeIgnoreUnit(jsid);
            List<Permission.DataBean> dbs = new ArrayList<>();
            Permission.DataBean db = new Permission.DataBean();
            db.setUserid(usercode);
            String groups = "-1";
            if(null != dList && dList.size()>=1){
                for (SDalx d : dList) {
                    groups+=(","+d.getCode());
                }
            }
            db.setGroups(groups.replace("-1," , ""));
            dbs.add(db);

            pm.setReponse(GlobalFinalAttr.RSP_SUCCESS);
            pm.setTotal(1);
            pm.setData(dbs);
        } catch (Exception e) {
            pm.setReponse(GlobalFinalAttr.RSP_FAIL);
            e.printStackTrace();
        }
        return pm;
    }

    private SearchData search(String startTime , String endTime , String type
            , Integer page , Integer pageSize){
        SearchData sd = new SearchData();

        Long sL =  new DateTime(DateUtil.strToDate(startTime , S_DATE_FMT)).getMillis();
        Long eL =  new DateTime(DateUtil.strToDate(endTime , S_DATE_FMT)).getMillis();

        List<DataBean> datas = new ArrayList<>();
        IndexSearcher searcher = null;
        IndexReader mainIndexReader = null;
        BooleanQuery bQuery = null;
        StandardAnalyzer analyzer = new StandardAnalyzer(LUCEN_VERSION);
        QueryBuilder searchQueryBuilder = new QueryBuilder(analyzer);
        try {
            mainIndexReader = DirectoryReader.open(FSDirectory.open(new File( luceneIndexPath+"mainIndex")));
            searcher = new IndexSearcher(mainIndexReader);
            NumericRangeQuery nq = NumericRangeQuery.newLongRange(CREATETIME, sL ,
                            eL , false ,true);


            BooleanQuery all = new BooleanQuery();

            BooleanQuery dalxQuery = new BooleanQuery();
            QueryParser parser = null;

            /**************************************************************/

            List<SDalx> dalxList = getDalxList();

            for (SDalx dalx : dalxList) {
                parser = new QueryParser(LUCEN_VERSION  , "LIBCODE" , analyzer);
                //判断是OR还是AND来生产不同的解析式
                parser.setDefaultOperator(QueryParser.OR_OPERATOR);
                dalxQuery.add( parser.parse(dalx.getCode().toString()), BooleanClause.Occur.SHOULD);
            }
            all.add(new BooleanClause(dalxQuery , BooleanClause.Occur.MUST));
            all.add(new BooleanClause(nq , BooleanClause.Occur.MUST));

            TopDocs topDocs = null;
            BooleanQuery filterQuery = new BooleanQuery();
            QueryParser filterParse = new QueryParser(LUCEN_VERSION , "DLEVEL" , analyzer);
            filterQuery.add(filterParse.parse("INDEX_") , BooleanClause.Occur.SHOULD);
            topDocs = searcher.search(all , new QueryWrapperFilter(filterQuery), mainIndexReader.maxDoc());
            int breakFlag = 0;
            int docID = ((page-1)*pageSize);
            for(int i =  docID == 0 ? 0 : docID ; breakFlag < pageSize && i<topDocs.totalHits  ;i++) {
                DataBean db = new DataBean();
                Document doc = searcher.doc(topDocs.scoreDocs[i].doc);//INDEX_
                SDalx dalx = getDalx(doc.get("LIBCODE"));
                Map<String , Object> dfileMap = queryDfile(doc.get("EID"), doc.get("LIBCODE"));
                String id = doc.get("TABLENAME")+"_"+ doc.get("DID");
                db.setTitle(doc.get("TITLE"));
                db.setFiletype(doc.get("EXT"));
                db.setContent(doc.get("CONTEXT"));
                if(StringUtils.isNotBlank(doc.get("CREATETIME"))){
                    try {
                        db.setDate(new DateTime(Long.parseLong(doc.get("CREATETIME")))
                                .toString(S_DATE_FMT));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                if(null != dalx){
                    db.setType(dalx.getChname());
                    db.setGroups(dalx.getCode());
                }

                if(null != dfileMap){
                    db.setFanwei(MapUtils.getString(dfileMap , "FLH"));
                    db.setQuanzong(MapUtils.getString(dfileMap , "QZH"));
                }

                db.setUrl("http://www.baidu.com");
                datas.add(db);
                breakFlag++;
            }
            sd.setTotal(topDocs.totalHits);
            sd.setData(datas);
            sd.setReponse(GlobalFinalAttr.RSP_SUCCESS);
        } catch (Exception e) {
            sd.setReponse(GlobalFinalAttr.RSP_FAIL);
            e.printStackTrace();
        }finally{
            try {
                mainIndexReader.close();
            } catch (Exception e){
                log.error("这里需要处理释放的资源异常");
            }
        }
        return sd;
    }

    private List<SDalx> getDalxList(){
        if(null == dalxList){
            dalxList = sUserMapper.getAllDalxList();
        }
        return dalxList;
    }

    private SDalx getDalx(String libcode ){
        SDalx d = null;
        List<SDalx> dList = getDalxList();
        for (SDalx sDalx : dList) {
            if(libcode.equalsIgnoreCase(sDalx.getCode().toString())){
                d = sDalx;
                break;
            }
        }
        return d;
    }

    private Map<String , Object> queryDfile(String eid , String libcode){
        Map<String , Object> rslt = null;
        try {
            rslt = super.queryForMap("SELECT FLH,QZH,TITLE,KEYWORD FROM D_FILE"
                    +libcode + " WHERE DID = (SELECT PID FROM E_FILE"+libcode
                    +" WHERE DID="+eid+")");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rslt;
    }

    @Autowired
    private SUserMapper sUserMapper;
    public static final Version LUCEN_VERSION = Version.LUCENE_4_9;//默认lucene的版本
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
    @Autowired
    @Value("${lucene.index.path}")
    private String luceneIndexPath;//luceneIndexPath
    private static final String DATE_FMT = "yyyyMMddHHmmss";
    private static final String S_DATE_FMT = "yyyy-MM-dd HH:mm:ss";
    private static final String CREATETIME = "CREATETIME";
}
