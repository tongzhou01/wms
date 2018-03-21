package com.mz.admin.service.impl;

import com.mz.admin.dao.PackageTypeDao;
import com.mz.admin.entity.PackageType;
import com.mz.admin.service.PackageTypeService;
import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tongzhou
 * @date 2018-03-21 12:04
 **/
@Service
public class PackageTypeServiceImpl extends BaseService<PackageType> implements PackageTypeService{

    @Autowired
    PackageTypeDao packageTypeDao;
    @Override
    public IDao<PackageType> getDao() {
        return packageTypeDao;
    }
}
