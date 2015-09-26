package com.build123.lifemall.common.generic;

import com.build123.lifemall.common.model.BaseModel;
import net.sf.json.JSONObject;

import java.util.List;


public interface BaseService<T extends BaseModel> {
    
    public BaseDao<T> getDao();
    
    public T findByObj(T obj, String entityName);

    public JSONObject findByPage(T pageInfo, String entityName);

    public JSONObject insert(T obj, String entityName);

    public JSONObject update(T obj, String entityName);

    public JSONObject delete(T obj, String entityName);

    public List<T> findByPageReturnList(T pageInfo, String entityName);

    public Integer findByPageCountReturnInteger(T pageInfo, String entityName);

    public Integer insertReturnInteger(T obj, String entityName);

    public Integer updateReturnInteger(T obj, String entityName);

    public Integer deleteReturnInteger(T obj, String entityName);

    public T insertReturnObject(T obj, String entityName);

    public List<T> all(String entityName);

    public List<T> findByObjReturnList(T obj, String entityName);
}
