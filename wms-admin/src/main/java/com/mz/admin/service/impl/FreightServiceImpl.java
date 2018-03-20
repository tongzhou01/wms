package com.mz.admin.service.impl;

import com.mz.admin.dao.FreightDao;
import com.mz.admin.entity.Freight;
import com.mz.admin.service.FreightService;
import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tongzhou
 * @date 2018-03-20 16:25
 **/
@Service
public class FreightServiceImpl extends BaseService<Freight> implements FreightService {
    @Autowired
    FreightDao freightDao;

    @Override
    public IDao<Freight> getDao() {
        return freightDao;
    }
}
