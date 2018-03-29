package com.mz.admin.dao;

import com.mz.admin.entity.CustomerInfo;
import com.mz.common.dao.IDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CustomerInfoDao extends IDao<CustomerInfo> {
    Integer selectMaxNumber();

    CustomerInfo selectByCustomerNo(String customerNo);

    int dealBalance(@Param("id") Long id, @Param("money") BigDecimal money, @Param("type") Integer type);
}