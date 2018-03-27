package com.mz.admin.dao;

import com.mz.admin.entity.CargoInfo;
import com.mz.admin.model.CargoDetailVO;
import com.mz.common.dao.IDao;
import com.mz.common.entity.QueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoInfoDao extends IDao<CargoInfo> {

    List<CargoDetailVO> getDetail(QueryParam param);

    int countDetail(QueryParam param);
}