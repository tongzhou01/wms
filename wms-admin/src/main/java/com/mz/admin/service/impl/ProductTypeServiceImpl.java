package com.mz.admin.service.impl;

import com.mz.admin.dao.ProductTypeDao;
import com.mz.admin.entity.ProductType;
import com.mz.admin.service.ProductTypeService;
import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tongzhou
 * @date 2018-03-21 12:04
 **/
@Service
public class ProductTypeServiceImpl extends BaseService<ProductType> implements ProductTypeService {

    @Autowired
    ProductTypeDao productTypeDao;
    @Override
    public IDao<ProductType> getDao() {
        return productTypeDao;
    }
}
