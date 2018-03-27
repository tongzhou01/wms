package com.mz.admin.service.impl;

import com.mz.admin.dao.FreightDao;
import com.mz.admin.entity.Freight;
import com.mz.admin.service.FreightService;
import com.mz.common.dao.IDao;
import com.mz.common.entity.QueryParam;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<Map> selectFreightDetail(QueryParam queryParam) {
        return freightDao.selectFreightDetail(queryParam);
    }

    @Override
    public int countDetail(QueryParam queryParam) {
        return freightDao.countDetail(queryParam);
    }

    @Override
    public Freight selectFreight(QueryParam queryParam) {
        return freightDao.selectFreight(queryParam);
    }
}
