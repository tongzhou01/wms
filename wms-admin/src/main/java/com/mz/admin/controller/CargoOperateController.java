package com.mz.admin.controller;

import com.mz.admin.entity.CargoInfo;
import com.mz.admin.service.CargoInfoService;
import com.mz.common.entity.Example;
import com.mz.common.entity.R;
import com.mz.common.service.IService;
import com.mz.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 货物操作
 *
 * @author tongzhou
 * @date 2018-03-13 10:16
 **/
@RestController
@RequestMapping("/api/cargo/")
public class CargoOperateController {

    @Autowired
    CargoInfoService cargoInfoService;
    @Autowired
    IService<Map<String, Object>> baseService;

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param orderNo
     * @param customerNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public R list(//@RequestBody Map<String, Object> param
                  @RequestParam(value = "currentPage", required = false) Integer currentPage,
                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                  @RequestParam(value = "startDate", required = false) String startDate,
                  @RequestParam(value = "endDate", required = false) String endDate,
                  @RequestParam(value = "orderNo", required = false) Integer orderNo,
                  @RequestParam(value = "customerNo", required = false) Integer customerNo
    ) {
        Example example = Example.create(CargoInfo.class);
        example.equal("is_deleted", 0);
        if (orderNo != null)
            example.like("order_no", "%" + orderNo + "%");
        if (customerNo != null)
            example.like("customer_no", "%" + customerNo + "%");
        if (currentPage != null && pageSize != null) {
            example.setPage(currentPage);
            example.setRows(pageSize);
        }
        if (startDate != null && endDate != null) {
            example.greatEqual("gmt_create", startDate + " 00:00:00");
            example.lessEqual("gmt_create", endDate + " 23:59:59");
        }
        int total = baseService.count(example);
        List<Map<String, Object>> list = baseService.find(example);
        return CommonUtil.msg(list).put("total", total);
    }

    /**
     * 新增
     *
     * @param cargoInfo
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public R add(@RequestBody CargoInfo cargoInfo) {
        int i = cargoInfoService.insertSelective(cargoInfo);
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
        CargoInfo cargoInfo = cargoInfoService.selectByPrimaryKey(id);
        return CommonUtil.msg(cargoInfo);
    }

    /**
     * 修改
     *
     * @param cargoInfo
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public R edit(@RequestBody CargoInfo cargoInfo) {
        int i = cargoInfoService.updateByPrimaryKeySelective(cargoInfo);
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
            i += baseService.del("cargo_info", id);
        }
        return CommonUtil.msg(ids, i);
    }

}
