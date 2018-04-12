package com.mz.admin.dao;

import com.mz.admin.entity.CustomerInfo;
import com.mz.common.dao.IDao;
import com.mz.common.entity.QueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface CustomerInfoDao extends IDao<CustomerInfo> {
    Integer selectMaxNumber();

    CustomerInfo selectByCustomerNo(String customerNo);

    int dealBalance(@Param("id") Long id, @Param("money") BigDecimal money, @Param("type") Integer type);

    List<Map> selectCustomerDetail(QueryParam param);

    List<Map> selectCargoBatch(QueryParam param);
}