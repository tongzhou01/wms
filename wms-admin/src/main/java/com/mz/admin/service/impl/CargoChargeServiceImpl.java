package com.mz.admin.service.impl;

import com.mz.admin.dao.CargoChargeDao;
import com.mz.admin.entity.CargoCharge;
import com.mz.admin.service.CargoChargeService;
import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargoChargeServiceImpl extends BaseService<CargoCharge> implements CargoChargeService {

    @Autowired
    CargoChargeDao cargoChargeDao;

    @Override
    public IDao<CargoCharge> getDao() {
        return cargoChargeDao;
    }
}