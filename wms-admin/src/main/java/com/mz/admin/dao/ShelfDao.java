package com.mz.admin.dao;

import com.mz.admin.entity.Shelf;
import com.mz.common.dao.IDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfDao extends IDao<Shelf>{
    Shelf selectByShelfNo(String shelfNo);
}