package com.mz.admin.manager;

import com.mz.admin.entity.OutboundType;

public interface OutboundTypeManager {
    int deleteByPrimaryKey(Long id);

    int insert(OutboundType record);

    int insertSelective(OutboundType record);

    OutboundType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OutboundType record);

    int updateByPrimaryKey(OutboundType record);
}