
package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.JdbcDaoImpl;
import com.bwzk.dao.i.SUserMapper;
import com.bwzk.pojo.SDalx;
import com.bwzk.pojo.jaxb.Field;
import com.bwzk.pojo.jaxb.Table;
import com.bwzk.pojo.searchPojo.DataBean;
import com.bwzk.pojo.searchPojo.SearchData;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.GlobalSearchService;
import com.bwzk.service.i.SingleService;
import com.bwzk.util.GlobalFinalAttr;
import com.bwzk.util.XmlObjUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
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
    public String catchData(@WebParam(name = "startTime") String startTime
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

    private SearchData search(String startTime , String endTime , String type
            , Integer page , Integer pageSize){
        SearchData sd = new SearchData();
        List<DataBean> datas = new ArrayList<>();
        IndexSearcher searcher = null;
        IndexReader mainIndexReader = null;
        BooleanQuery bQuery = null;
        StandardAnalyzer analyzer = new StandardAnalyzer(LUCEN_VERSION);
        QueryBuilder searchQueryBuilder = new QueryBuilder(analyzer);
        try {
            mainIndexReader = DirectoryReader.open(FSDirectory.open(new File( luceneIndexPath+"mainIndex")));
            searcher = new IndexSearcher(mainIndexReader);
            BooleanQuery all = new BooleanQuery();
            BooleanQuery dalxQuery = new BooleanQuery();
            BooleanQuery qzhQuery = new BooleanQuery();
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

            TopDocs topDocs = null;
            BooleanQuery filterQuery = new BooleanQuery();
            QueryParser filterParse = new QueryParser(LUCEN_VERSION , "DLEVEL" , analyzer);
            filterQuery.add(filterParse.parse("INDEX_") , BooleanClause.Occur.SHOULD);
            topDocs = searcher.search(all , new QueryWrapperFilter(filterQuery), mainIndexReader.maxDoc());

            int breakFlag = 0;
            int docID = ((page-1)*pageSize);
            for(int i =  docID == 0 ? 0 : docID+1 ; breakFlag < pageSize && i<topDocs.totalHits  ;i++) {
                DataBean db = new DataBean();
                Document doc = searcher.doc(topDocs.scoreDocs[i].doc);//INDEX_
//                for (IndexableField field : doc.getFields()) {
//                    if(field.name().equals("ALLCONTENT")){
//                        continue;
//                    }else{
//                        tempMap.put(field.name(), doc.get(field.name()));
//                    }
//                }
                db.setTitle(doc.get("TITLE"));
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

    @Autowired
    private SUserMapper sUserMapper;
    public static final Version LUCEN_VERSION = Version.LUCENE_4_9;//默认lucene的版本
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
    @Autowired
    @Value("${lucene.index.path}")
    private String luceneIndexPath;//luceneIndexPath
}
