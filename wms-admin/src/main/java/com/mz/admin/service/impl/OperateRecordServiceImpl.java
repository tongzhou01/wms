package com.mz.admin.service.impl;

import com.mz.admin.dao.OperateRecordDao;
import com.mz.admin.entity.OperateRecord;
import com.mz.admin.service.OperateRecordService;
import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperateRecordServiceImpl extends BaseService<OperateRecord> implements OperateRecordService {

    @Autowired
    OperateRecordDao operateRecordDao;

    @Override
    public IDao<OperateRecord> getDao() {
        return operateRecordDao;
    }

}