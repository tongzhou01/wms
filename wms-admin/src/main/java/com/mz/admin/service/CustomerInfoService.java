package com.mz.admin.service;

import com.mz.admin.entity.CustomerInfo;
import com.mz.common.entity.QueryParam;
import com.mz.common.entity.R;
import com.mz.common.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CustomerInfoService extends IService<CustomerInfo> {

    Integer selectMaxNumber();

    CustomerInfo selectByCustomerNo(String customerNo);

    R dealBalance(Long id, BigDecimal money, Integer type);

    List<Map> selectCustomerDetail(QueryParam param);

    List<Map> selectCargoBatch(QueryParam param);
}