package com.mz.admin.service.impl;

import com.mz.admin.dao.CountryDao;
import com.mz.admin.entity.Country;
import com.mz.admin.service.CountryService;
import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl extends BaseService<Country> implements CountryService {

    @Autowired
    CountryDao countryDao;

    @Override
    public IDao<Country> getDao() {
        return countryDao;
    }
}