package com.mz.admin.service.impl;

import com.mz.admin.dao.CustomerInfoDao;
import com.mz.admin.entity.CustomerInfo;
import com.mz.admin.service.CustomerInfoService;
import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerInfoServiceImpl extends BaseService<CustomerInfo> implements CustomerInfoService {

    @Autowired
    CustomerInfoDao customerInfoDao;

    @Override
    public IDao<CustomerInfo> getDao() {
        return customerInfoDao;
    }

    @Override
    public Integer selectMaxNumber() {
        return customerInfoDao.selectMaxNumber();
    }

    @Override
    public CustomerInfo selectByCustomerNo(String customerNo) {
        return customerInfoDao.selectByCustomerNo(customerNo);
    }
}