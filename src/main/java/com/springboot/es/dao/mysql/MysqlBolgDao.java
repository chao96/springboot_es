package com.springboot.es.dao.mysql;

import com.springboot.es.entity.mysql.MysqlBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author xuchao
 * @since 2020/3/3 9:25
 */
public interface MysqlBolgDao extends JpaRepository<MysqlBlog, Integer> {

    @Query("select e from MysqlBlog e order by e.createTime desc")
    public List<MysqlBlog> listAll();

    @Query("select e from MysqlBlog e where e.title like concat('%',:keyword,'%')" +
            " or e.content like concat('%',:keyword,'%')")
    List<MysqlBlog> queryBlogs(@Param("keyword") String keyword);
}
