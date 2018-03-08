package com.mz.admin.manager;

import com.mz.admin.entity.CargoInfo;

public interface CargoInfoManager {
    int deleteByPrimaryKey(Long id);

    int insert(CargoInfo record);

    int insertSelective(CargoInfo record);

    CargoInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoInfo record);

    int updateByPrimaryKey(CargoInfo record);
}