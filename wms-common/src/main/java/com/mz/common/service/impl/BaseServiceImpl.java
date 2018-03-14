package com.mz.common.service.impl;

import com.mz.common.dao.BaseDao;
import com.mz.common.dao.IDao;
import com.mz.common.service.BaseService;
import com.mz.common.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 基础实现类
 *
 * @author tongzhou
 * @date 2018-03-13 12:18
 **/
@Service
public class BaseServiceImpl extends BaseService<Map<String, Object>> implements IService<Map<String, Object>>{
    @Autowired
    BaseDao baseDao;

    @Override
    public IDao<Map<String, Object>> getDao() {
        return baseDao;
    }
}
