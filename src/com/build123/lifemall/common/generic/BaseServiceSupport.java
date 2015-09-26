package com.build123.lifemall.common.generic;

import com.build123.lifemall.common.model.BaseModel;
import com.build123.lifemall.common.model.SharePageEntity;
import com.build123.lifemall.common.util.BaseController;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public abstract class BaseServiceSupport<T extends BaseModel> implements BaseService<T> {

    /** 日志记录器 **/
    private static final Logger log = Logger.getLogger(BaseServiceSupport.class);

    public abstract BaseDao<T> getDao();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public T findByObj(T obj,String entityName) {
        T result = getDao().findByObj(obj,entityName);
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public JSONObject findByPage(T pageInfo,String entityName) {
        SharePageEntity sharePageEntity = (SharePageEntity) pageInfo;
        JSONObject jsonObject = new JSONObject();
        if(sharePageEntity.getIndex()!=null && sharePageEntity.getRow()!=null){
            //查询客户分页总条数
            Integer count = getDao().findByPageCount(pageInfo,entityName);
            jsonObject.put(SharePageEntity.SHAREPAGE_COUNT_STR, count);
        }else{
            jsonObject.put(SharePageEntity.SHAREPAGE_COUNT_STR, SharePageEntity.NOTSHARE_TOTALCOUNT);
        }
        List<T> page = getDao().findByPage(pageInfo,entityName);
        jsonObject.put(SharePageEntity.SHAREPAGE_ITEMS_STR, page);
        return jsonObject;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public JSONObject insert(T obj,String entityName) {
        JSONObject jsonObject = null;
        Integer count = getDao().insert(obj,entityName);
        return jsonObject = BaseController.isScueessMessage(count);
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public JSONObject update(T obj,String entityName) {
        JSONObject jsonObject = null;
        Integer count = getDao().update(obj,entityName);
        return jsonObject = BaseController.isScueessMessage(count);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public JSONObject delete(T obj,String entityName) {
        JSONObject jsonObject = null;
        Integer count = getDao().delete(obj,entityName);
        return jsonObject = BaseController.isScueessMessage(count);
    }

    @Override
    public List<T> findByPageReturnList(T pageInfo,String entityName) {
        List<T> page = getDao().findByPage(pageInfo,entityName);
        if(page != null && !page.isEmpty()) {
            return page;
        }
        return null;
    }

    @Override
    public Integer findByPageCountReturnInteger(T pageInfo,String entityName) {
        return getDao().findByPageCount(pageInfo,entityName);
    }

    @Override
    public Integer insertReturnInteger(T obj,String entityName) {
        return getDao().insert(obj,entityName);
    }

    @Override
    public Integer updateReturnInteger(T obj,String entityName) {
        return getDao().update(obj,entityName);
    }

    @Override
    public List<T> all(String entityName) {
        List<T> list = getDao().all(entityName);
        if(list != null && !list.isEmpty()){
            return list;
        }
        return null;
    }

    @Override
    public Integer deleteReturnInteger(T obj,String entityName) {
        return getDao().delete(obj,entityName);
    }

    @Override
    public List<T> findByObjReturnList(T obj, String entityName) {
        return getDao().findByObjReturnList(obj, entityName);
    }

    @Override
    public T insertReturnObject(T obj,String entityName) {
        return getDao().insertReturnObject(obj,entityName);
    }
}
