package com.mz.admin.service;

import com.mz.admin.entity.Shelf;
import com.mz.common.service.IService;

public interface ShelfService extends IService<Shelf>{

    /**
     * 根据货架号查询
     * @param shelfNo
     * @return
     */
    Shelf selectByShelfNo(String shelfNo);
}