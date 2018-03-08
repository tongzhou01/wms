package com.mz.admin.dao;

import com.mz.admin.entity.ExpressCompany;

public interface ExpressCompanyDao {
    int deleteByPrimaryKey(Long id);

    int insert(ExpressCompany record);

    int insertSelective(ExpressCompany record);

    ExpressCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpressCompany record);

    int updateByPrimaryKey(ExpressCompany record);
}