package com.mz.common.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * BaseDao 数据访问层通用接口<br>
 * Created by tongzhou on 2017-09-12.
 */
@Repository
public interface BaseDao extends IDao<Map<String, Object>> {

}