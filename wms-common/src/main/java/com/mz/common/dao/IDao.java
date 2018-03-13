package com.mz.common.dao;

import com.mz.common.entity.Example;
import com.mz.common.entity.QueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 基础数据访问层接口<br>
 * Created by tongzhou on 2017-09-12.
 */
public interface IDao<T> {

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加（所有参数）
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 添加（自定义参数）
     *
     * @param t
     * @return
     */
    int insertSelective(T t);

    /**
     * 根据ID查询一个
     *
     * @param id
     * @return
     */
    T selectByPrimaryKey(Integer id);

    /**
     * 更新（自定义参数）
     *
     * @param t
     * @return
     */
    int updateByPrimaryKeySelective(T t);

    /**
     * 更新（所有参数）
     *
     * @param t
     * @return
     */
    int updateByPrimaryKey(T t);

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
     * 逻辑删除（设置hidden_status为0）
     *
     * @param id
     * @return
     */
    int del(@Param("tableName") String tableName, @Param("id") Integer id);

    /**
     * 直接执行sql
     */
    List<Map<String, Object>> executeSql(@Param("sql") String sql);

    List<T> index(QueryParam queryParam);
}
