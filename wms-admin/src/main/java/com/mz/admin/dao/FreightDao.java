package com.mz.admin.dao;

import com.mz.admin.entity.Freight;
import com.mz.common.dao.IDao;
import com.mz.common.entity.QueryParam;

import java.util.List;
import java.util.Map;

public interface FreightDao extends IDao<Freight>{

    List<Map> selectFreightDetail(QueryParam queryParam);

    int countDetail(QueryParam queryParam);

    Freight selectFreight(QueryParam queryParam);
}