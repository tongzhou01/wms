package com.mz.admin.dao;

import com.mz.admin.entity.CargoInfo;

public interface CargoInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(CargoInfo record);

    int insertSelective(CargoInfo record);

    CargoInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoInfo record);

    int updateByPrimaryKey(CargoInfo record);
}