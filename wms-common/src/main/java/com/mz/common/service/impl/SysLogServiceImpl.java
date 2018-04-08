package com.mz.common.service.impl;

import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import com.mz.common.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统日志
 *
 * @author tongzhou
 * @date 2018-04-08 10:17
 **/
@Service
public class SysLogServiceImpl extends BaseService implements SysLogService{

    @Autowired

    @Override
    public IDao getDao() {
        return null;
    }
}
