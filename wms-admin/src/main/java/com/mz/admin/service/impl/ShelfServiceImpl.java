package com.mz.admin.service.impl;

import com.mz.admin.dao.ShelfDao;
import com.mz.admin.entity.Shelf;
import com.mz.admin.service.ShelfService;
import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShelfServiceImpl extends BaseService<Shelf> implements ShelfService {

    @Autowired
    ShelfDao shelfDao;

    @Override
    public IDao<Shelf> getDao() {
        return shelfDao;
    }
}