package com.mz.admin.service.impl;

import com.mz.admin.dao.CargoInfoDao;
import com.mz.admin.entity.CargoInfo;
import com.mz.admin.service.CargoInfoService;
import com.mz.common.dao.IDao;
import com.mz.common.entity.QueryParam;
import com.mz.common.entity.R;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargoInfoServiceImpl extends BaseService<CargoInfo> implements CargoInfoService {

    @Autowired
    CargoInfoDao cargoInfoDao;

    @Override
    public IDao<CargoInfo> getDao() {
        return cargoInfoDao;
    }

}