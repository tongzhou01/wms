package com.mz.common.dao;

import com.mz.common.dao.IDao;
import com.mz.common.entity.IEntity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * BaseDao 数据访问层通用接口<br>
 * Created by tongzhou on 2017-09-12.
 */
@Repository
public interface BaseDao<T extends IEntity> extends IDao<T> {

}