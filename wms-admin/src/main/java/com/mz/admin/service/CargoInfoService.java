package com.mz.admin.service;

import com.mz.admin.entity.CargoInfo;
import com.mz.admin.model.CargoDetailVO;
import com.mz.common.entity.QueryParam;
import com.mz.common.service.IService;

import java.util.List;

public interface CargoInfoService extends IService<CargoInfo>{

    /**
     * 查询详细
     * @param param
     * @return
     */
    List<CargoDetailVO> getDetail(QueryParam param);

    /**
     * 查询详细总数
     * @param param
     * @return
     */
    int countDetail(QueryParam param);
}