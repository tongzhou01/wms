package com.mz.common.dao;

import com.mz.common.entity.IEntity;


/**
 * 基础数据访问层接口<br>
 * Created by tongzhou on 2017-09-12.
 */
public interface IDao<T extends IEntity> {

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


}
