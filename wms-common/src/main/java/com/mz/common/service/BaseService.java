package com.mz.common.service;

import com.mz.common.dao.IDao;
import com.mz.common.entity.Example;
import com.mz.common.entity.QueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Abstract 基础数据访问层<br>
 * Created by tongzhou on 2017-09-12.
 */
@Service
public abstract class BaseService<T> implements IService<T> {

    public abstract IDao<T> getDao();

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return getDao().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T t) {
        return getDao().insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return getDao().insertSelective(t);
    }

    @Override
    public T selectByPrimaryKey(Long id) {
        return getDao().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return getDao().updateByPrimaryKeySelective(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return getDao().updateByPrimaryKey(t);
    }

    /*---------------------------------------------------↓BaseDao通用方法↓---------------------------------------------------*/
    @Override
    public T load(Example example) {
        return getDao().load(example);
    }

    @Override
    public List<T> list(Example example) {
        return getDao().list(example);
    }

    @Override
    public List<T> find(Example example) {
        return getDao().find(example);
    }

    @Override
    public int count(Example example) {
        return getDao().count(example);
    }

    @Override
    public int count(QueryParam queryParam) {
        return getDao().count(queryParam);
    }

    @Override
    public int del(String tableName, Integer id) {
        return getDao().del(tableName, id);
    }

    /**
     * 直接执行sql
     */
    public List<Map<String, Object>> executeSql(@Param("sql") String sql) {
        return getDao().executeSql(sql);
    }

    @Override
    public List<Map> index(QueryParam queryParam) {
        return getDao().index(queryParam);
    }
}
