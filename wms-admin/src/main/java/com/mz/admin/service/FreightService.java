package com.mz.admin.service;

import com.mz.admin.entity.Freight;
import com.mz.common.entity.QueryParam;
import com.mz.common.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author tongzhou
 * @date 2018-03-20 16:25
 **/
public interface FreightService extends IService<Freight>{
    /**
     * 查询运费详细信息
     * @param queryParam
     * @return
     */
    List<Map> selectFreightDetail(QueryParam queryParam);

    /**
     * 统计
     * @param queryParam
     * @return
     */
    int countDetail(QueryParam queryParam);

    /**
     * 条件查询
     * @param queryParam
     * @return
     */
    Freight selectFreight(QueryParam queryParam);
}
