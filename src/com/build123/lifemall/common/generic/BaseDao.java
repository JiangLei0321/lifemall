package com.build123.lifemall.common.generic;

import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

/**
 * Created by qi.li on 14-8-15.
 */
public interface  BaseDao<T> {
    public SqlSessionTemplate getSqlSessionTemplate();
    public T findByObj(T obj, String entityName);

    public List<T> findByPage(T pageInfo, String entityName);

    public Integer findByPageCount(T pageInfo, String entityName);

    public Integer update(T obj, String entityName);

    public Integer insert(T obj, String entityName);

    public Integer delete(T obj, String entityName);

    public T insertReturnObject(T obj, String entityName);

    public List<T> all(String entityName);

    public List<T> findByObjReturnList(T obj, String entityName);
}
