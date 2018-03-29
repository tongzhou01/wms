package com.mz.admin.service.impl;

import com.mz.admin.dao.CustomerInfoDao;
import com.mz.admin.entity.CustomerInfo;
import com.mz.admin.service.CustomerInfoService;
import com.mz.common.dao.IDao;
import com.mz.common.entity.R;
import com.mz.common.service.BaseService;
import com.mz.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    @Override
    public R dealBalance(Long id, BigDecimal money, Integer type) {
        CustomerInfo customerInfo = customerInfoDao.selectByPrimaryKey(id);
        BigDecimal balance = customerInfo.getBalance();
        if (money.compareTo(new BigDecimal(0)) < 0)
            return R.error("金额不能为负");
        if (type == 0 && balance.compareTo(money) < 0) {//扣钱时，余额不足返回失败
            return R.error("账户余额不足");
        }
        int i = customerInfoDao.dealBalance(id, money, type);
        return CommonUtil.msg(i);
    }
}