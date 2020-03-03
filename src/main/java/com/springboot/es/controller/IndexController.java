package com.springboot.es.controller;

import com.springboot.es.dao.mysql.MysqlBolgDao;
import com.springboot.es.entity.mysql.MysqlBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author xuchao
 * @since 2020/3/3 9:26
 */
@Controller
public class IndexController {

    @Autowired
    MysqlBolgDao mysqlBolgDao;

    @RequestMapping("/")
    public String index(){
        List<MysqlBlog> all = mysqlBolgDao.findAll();
        System.out.println(all.size());
        return "index.html";
    }
}
