package com.mz.admin.service.impl;

import com.mz.admin.dao.ExpressCompanyDao;
import com.mz.admin.entity.ExpressCompany;
import com.mz.admin.service.ExpressCompanyService;
import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpressCompanyServiceImpl extends BaseService<ExpressCompany> implements ExpressCompanyService {
    @Autowired
    ExpressCompanyDao expressCompanyDao;

    @Override
    public IDao<ExpressCompany> getDao() {
        return expressCompanyDao;
    }
}