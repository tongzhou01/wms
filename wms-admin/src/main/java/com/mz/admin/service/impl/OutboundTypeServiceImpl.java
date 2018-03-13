package com.mz.admin.service.impl;

import com.mz.admin.dao.OutboundTypeDao;
import com.mz.admin.entity.OutboundType;
import com.mz.admin.service.OutboundTypeService;
import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutboundTypeServiceImpl extends BaseService<OutboundType> implements OutboundTypeService {

    @Autowired
    OutboundTypeDao outboundTypeDao;

    @Override
    public IDao<OutboundType> getDao() {
        return outboundTypeDao;
    }
}