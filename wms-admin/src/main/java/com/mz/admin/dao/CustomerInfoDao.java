package com.mz.admin.dao;

import com.mz.admin.entity.CustomerInfo;
import com.mz.common.dao.IDao;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInfoDao extends IDao<CustomerInfo> {
    Integer selectMaxId();
}