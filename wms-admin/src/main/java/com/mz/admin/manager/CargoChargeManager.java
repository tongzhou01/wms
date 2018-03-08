package com.mz.admin.manager;

import com.mz.admin.entity.CargoCharge;

public interface CargoChargeManager {
    int deleteByPrimaryKey(Long id);

    int insert(CargoCharge record);

    int insertSelective(CargoCharge record);

    CargoCharge selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoCharge record);

    int updateByPrimaryKey(CargoCharge record);
}