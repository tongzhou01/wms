package com.mz.common.dao;

import com.mz.common.entity.QueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BaseDao 数据访问层通用接口<br>
 * Created by tongzhou on 2017-09-12.
 */
@Repository
public interface BaseDao<T> extends IDao<T> {
}