package com.springboot.es.dao.es;

import com.springboot.es.entity.es.Esblog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author xuchao
 * @since 2020/3/3 9:52
 */
public interface EsBlogDao extends ElasticsearchRepository<Esblog, Integer> {
}
