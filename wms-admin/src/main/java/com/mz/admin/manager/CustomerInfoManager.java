package com.mz.admin.manager;

import com.mz.admin.entity.CustomerInfo;

public interface CustomerInfoManager {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerInfo record);

    int insertSelective(CustomerInfo record);

    CustomerInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerInfo record);

    int updateByPrimaryKey(CustomerInfo record);
}