package com.mz.admin.controller;

import com.mz.admin.entity.Shelf;
import com.mz.admin.service.ShelfService;
import com.mz.common.entity.Example;
import com.mz.common.entity.QueryParam;
import com.mz.common.entity.R;
import com.mz.common.service.IService;
import com.mz.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 货架
 *
 * @author tongzhou
 * @date 2018-03-13 10:16
 **/
@RestController
@RequestMapping("/api/shelf/")
public class ShelfController {

    @Autowired
    ShelfService shelfService;
    @Autowired
    IService<Map<String, Object>> baseService;

    /**
     * 分页查询
     *
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public R list(@RequestBody QueryParam queryParam
                  ) {
        Example example = Example.create(Shelf.class);
        example.equal("is_deleted", 0);
        Integer currentPage = (Integer) queryParam.get("currentPage");
        Integer pageSize = (Integer) queryParam.get("pageSize");
        if (currentPage != null && pageSize != null) {
            example.setPage(currentPage);
            example.setRows(pageSize);
        }
        int total = baseService.count(example);
        List<Map> list = shelfService.index(queryParam);
        return CommonUtil.msg(list).put("total", total);
    }

    /**
     * 新增
     *
     * @param shelf
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public R add(@RequestBody Shelf shelf) {
        shelf.setGmtCreate(new Date());
        int i = shelfService.insertSelective(shelf);
        return CommonUtil.msg(i);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public R getById(@RequestParam Long id) {
        Shelf shelf = shelfService.selectByPrimaryKey(id);
        return CommonUtil.msg(shelf);
    }

    /**
     * 修改
     *
     * @param shelf
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public R edit(@RequestBody Shelf shelf) {
        int i = shelfService.updateByPrimaryKeySelective(shelf);
        return CommonUtil.msg(i);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.DELETE)
    public R del(@RequestBody Integer[] ids) {
        int i = 0;
        for (int id : ids) {
            i += baseService.del("shelf", id);
        }
        return CommonUtil.msg(ids, i);
    }

}
