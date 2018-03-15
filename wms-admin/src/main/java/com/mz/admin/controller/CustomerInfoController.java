package com.mz.admin.controller;

import com.mz.admin.entity.CustomerInfo;
import com.mz.admin.service.CustomerInfoService;
import com.mz.common.entity.Example;
import com.mz.common.entity.R;
import com.mz.common.service.IService;
import com.mz.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户信息
 *
 * @author tongzhou
 * @date 2018-03-13 10:16
 **/
@RestController
@RequestMapping("/api/customer/")
public class CustomerInfoController {

    @Autowired
    CustomerInfoService customerInfoService;
    @Autowired
    IService<Map<String, Object>> baseService;

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param customerName
     * @param customerNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public R list(//@RequestBody Map<String, Object> param
                  @RequestParam(value = "currentPage", required = false) Integer currentPage,
                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                  @RequestParam(value = "startDate", required = false) String startDate,
                  @RequestParam(value = "endDate", required = false) String endDate,
                  @RequestParam(value = "customerName", required = false) String customerName,
                  @RequestParam(value = "customerNo", required = false) Integer customerNo
    ) {
        Example example = Example.create(CustomerInfo.class);
        example.equal("is_deleted", 0);
        if (customerName != null)
            example.like("customer_name", "%" + customerName + "%");
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
     * @param customerInfo
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public R add(@RequestBody CustomerInfo customerInfo) {
        Integer maxId = customerInfoService.selectMaxId();
        customerInfo.setCustomerNo("MZA" + (maxId == null ? 0 : maxId));
        customerInfo.setGmtCreate(new Date());
        int i = customerInfoService.insertSelective(customerInfo);
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
        CustomerInfo customerInfo = customerInfoService.selectByPrimaryKey(id);
        return CommonUtil.msg(customerInfo);
    }

    /**
     * 修改
     *
     * @param customerInfo
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public R edit(@RequestBody CustomerInfo customerInfo) {
        int i = customerInfoService.updateByPrimaryKeySelective(customerInfo);
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
            i += baseService.del("customer_info", id);
        }
        return CommonUtil.msg(ids, i);
    }

}
