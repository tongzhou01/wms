package com.mz.admin.controller;

import com.mz.admin.entity.ProductType;
import com.mz.admin.service.ProductTypeService;
import com.mz.common.entity.QueryParam;
import com.mz.common.entity.R;
import com.mz.common.service.IService;
import com.mz.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product/")
public class ProductTypeController {

    @Autowired
    ProductTypeService productTypeService;
    @Autowired
    IService<Map<String, Object>> baseService;

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public R list(@RequestBody QueryParam param
    ) {
        int total = productTypeService.count(param);
        List<Map> list = productTypeService.index(param);
        return CommonUtil.msg(list).put("total", total);
    }

    /**
     * 新增
     *
     * @param productType
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public R add(@RequestBody ProductType productType) {
        productType.setGmtCreate(new Date());
        int i = productTypeService.insertSelective(productType);
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
        ProductType productType = productTypeService.selectByPrimaryKey(id);
        return CommonUtil.msg(productType);
    }

    /**
     * 修改
     *
     * @param productType
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public R edit(@RequestBody ProductType productType) {
        int i = productTypeService.updateByPrimaryKeySelective(productType);
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
            i += baseService.del("product_type", id);
        }
        return CommonUtil.msg(ids, i);
    }
}
