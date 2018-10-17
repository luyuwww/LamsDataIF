package com.bwzk.dao;
/**
 * Created by DaMo on 2018-06-09.
 */

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.BaseDao4KeYan;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 * @UPDATE 2018-06-09-17:36
 * @since 2018-06-09-17:36
 */
@Component("kyDao")
public class KyDao implements BaseDao4KeYan {

    public List<String> pkListBySql(String sql){
        return jdbcTemplate4OA.queryForList(sql , String.class);
    }

    public Map<String , Object> query4Obj(String sql){
        String coutnsql = "SELECT COUNT(*) T FROM (" + sql +") TA";
        if( jdbcTemplate4OA.queryForInt(coutnsql)>1){
            return query4List(sql).get(1);
        }else{
            return jdbcTemplate4OA.queryForMap(sql);
        }
    }

    public List<Map<String , Object>> query4List(String sql){
        return jdbcTemplate4OA.queryForList(sql);
    }

    public void executeSql(String sql){
        jdbcTemplate4OA.execute(sql);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate4OA;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
