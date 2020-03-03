package com.springboot.es.controller;

import com.springboot.es.dao.es.EsBlogDao;
import com.springboot.es.dao.mysql.MysqlBolgDao;
import com.springboot.es.entity.es.Esblog;
import com.springboot.es.entity.mysql.MysqlBlog;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author xuchao
 * @since 2020/3/3 9:55
 */
@RestController
public class DataController {

    @Autowired
    MysqlBolgDao mysqlBolgDao;

    @Autowired
    EsBlogDao esBlogDao;

    @GetMapping("/blogs")
    public Object blog(){
        List<MysqlBlog> mysqlBlogs = mysqlBolgDao.listAll();
        return mysqlBlogs;
    }

    @PostMapping("/search")
    public Object search(@RequestBody Param param){
        HashMap<String, Object> map = new HashMap<>();
        StopWatch watch = new StopWatch();
        watch.start();

        String type = param.getType();
        if(type.equals("mysql")){
            List<MysqlBlog> mysqlBlogs = mysqlBolgDao.queryBlogs(param.getKeyword());
            map.put("list", mysqlBlogs);
        } else if(type.equals("es")){
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title", param.getKeyword()));
            builder.should(QueryBuilders.matchPhraseQuery("content", param.getKeyword()));
            String s = builder.toString();
            System.out.println(s);
            Page<Esblog> search = ( Page<Esblog>)esBlogDao.search(builder);
            List<Esblog> content = search.getContent();
            map.put("list", content);
        } else{
            return "error";
        }
        watch.stop();;
        long totalTimeMillis = watch.getTotalTimeMillis();
        map.put("duration", totalTimeMillis);
        return map;
    }

    @PostMapping("/insert")
    public void insertBlog(){
        MysqlBlog mysqlBlog = new MysqlBlog();
        mysqlBlog.setAuthor("xxx");
        mysqlBlog.setTitle("测试新增同步");

        mysqlBolgDao.save(mysqlBlog);
    }


    @Data
    public static class Param{
        // mysql、es
        private String type;

        private String keyword;
    }
}
