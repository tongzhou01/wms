package com.mz.admin.controller;

import com.mz.admin.entity.ExpressCompany;
import com.mz.admin.service.ExpressCompanyService;
import com.mz.common.entity.Example;
import com.mz.common.entity.R;
import com.mz.common.service.IService;
import com.mz.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 快递公司
 *
 * @author tongzhou
 * @date 2018-03-13 10:16
 **/
@RestController
@RequestMapping("/api/express/")
public class ExpressCompanyController {

    @Autowired
    ExpressCompanyService expressCompanyService;
    @Autowired
    IService<Map<String, Object>> baseService;

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param startDate
     * @param endDate
     * @param orderNo
     * @param customerNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public R list(//@RequestBody Map<String, Object> param
                  @RequestParam(value = "page", required = false) Integer page,
                  @RequestParam(value = "rows", required = false) Integer rows,
                  @RequestParam(value = "startDate", required = false) String startDate,
                  @RequestParam(value = "endDate", required = false) String endDate,
                  @RequestParam(value = "orderNo", required = false) Integer orderNo,
                  @RequestParam(value = "customerNo", required = false) Integer customerNo
    ) {
        Example example = Example.create(ExpressCompany.class);
        example.equal("is_deleted", 0);
        if (orderNo != null)
            example.like("order_no", "%" + orderNo + "%");
        if (customerNo != null)
            example.like("customer_no", "%" + customerNo + "%");
        if (page != null && rows != null) {
            example.setPage(page);
            example.setPage(rows);
        }
        if (startDate != null && endDate != null) {
            example.greatEqual("gmt_create", startDate + " 00:00:00");
            example.lessEqual("gmt_create", endDate + " 23:59:59");
        }
        int count = baseService.count(example);
        List<Map<String, Object>> list = baseService.find(example);
        return CommonUtil.msg(list);
    }

    /**
     * 新增
     *
     * @param expressCompany
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public R add(@RequestBody ExpressCompany expressCompany) {
        int i = expressCompanyService.insertSelective(expressCompany);
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
        ExpressCompany expressCompany = expressCompanyService.selectByPrimaryKey(id);
        return CommonUtil.msg(expressCompany);
    }

    /**
     * 修改
     *
     * @param expressCompany
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public R edit(@RequestBody ExpressCompany expressCompany) {
        int i = expressCompanyService.updateByPrimaryKeySelective(expressCompany);
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
            i += baseService.del("express_company", id);
        }
        return CommonUtil.msg(ids, i);
    }

}
