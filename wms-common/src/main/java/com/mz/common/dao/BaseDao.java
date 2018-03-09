package com.mz.common.dao;

import com.mz.common.entity.Example;
import com.mz.common.entity.IEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * BaseDao 数据访问层通用接口<br>
 * Created by tongzhou on 2017-09-12.
 */
@Repository
public interface BaseDao<T extends IEntity> extends IDao<T> {

    /*---------------------------------------------------↓通用方法↓---------------------------------------------------*/

    /**
     * 查询一个
     */
    T load(Example example);

    /**
     * 查询多个，不分页
     */
    List<T> list(Example example);

    /**
     * 查询多个，分页
     */
    List<T> find(Example example);

    /**
     * 计数
     */
    int count(Example example);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    int del(@Param("tableName") String tableName, @Param("id") Integer id);

    /**
     * 直接执行sql
     */
    List<Map<String, Object>> executeSql(@Param("sql") String sql);
}