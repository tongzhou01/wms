package com.mz.admin.service;

import com.mz.admin.entity.CargoInfo;
import com.mz.admin.model.CargoDetailVO;
import com.mz.common.entity.QueryParam;
import com.mz.common.service.IService;

import java.util.List;

public interface CargoInfoService extends IService<CargoInfo>{

    List<CargoDetailVO> getDetail(QueryParam param);
}