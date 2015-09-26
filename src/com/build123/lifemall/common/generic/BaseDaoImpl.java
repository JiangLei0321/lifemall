package com.build123.lifemall.common.generic;

import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

/**
 * Created by qi.li on 14-8-15.
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    public abstract SqlSessionTemplate getSqlSessionTemplate();

    @Override
    public T findByObj(T obj,String entityName) {
        return getSqlSessionTemplate().selectOne("findByObj"+entityName, obj);
    }

    @Override
    public List<T> findByPage(T pageInfo,String entityName) {
        return getSqlSessionTemplate().selectList("findByPage"+entityName, pageInfo);
    }

    @Override
    public Integer findByPageCount(T pageInfo,String entityName) {
        return getSqlSessionTemplate().selectOne("findByPageCount"+entityName, pageInfo);
    }

    @Override
    public Integer update(T obj,String entityName) {
        return getSqlSessionTemplate().update("update"+entityName, obj);
    }

    @Override
    public Integer insert(T obj,String entityName) {
        return getSqlSessionTemplate().insert("insert"+entityName, obj);
    }

    @Override
    public Integer delete(T obj,String entityName) {
        return getSqlSessionTemplate().delete("delete"+entityName, obj);
    }

    @Override
    public T insertReturnObject(T obj,String entityName) {
        getSqlSessionTemplate().insert("insert"+entityName, obj);
        return obj;
    }

    @Override
    public List<T> all(String entityName) {
        return getSqlSessionTemplate().selectList("all"+entityName);
    }

    @Override
    public List<T> findByObjReturnList(T obj, String entityName) {
        return getSqlSessionTemplate().selectList("findByObj"+entityName, obj);
    }

}
