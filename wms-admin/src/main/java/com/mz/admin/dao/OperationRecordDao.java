package com.mz.admin.dao;

import com.mz.admin.entity.OperationRecord;

public interface OperationRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OperationRecord record);

    int insertSelective(OperationRecord record);

    OperationRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationRecord record);

    int updateByPrimaryKey(OperationRecord record);
}