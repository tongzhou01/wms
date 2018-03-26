package com.mz.admin.service;

import com.mz.admin.entity.CustomerInfo;
import com.mz.common.service.IService;

public interface CustomerInfoService extends IService<CustomerInfo> {

    Integer selectMaxNumber();

    CustomerInfo selectByCustomerNo(String customerNo);
}