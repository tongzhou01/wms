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
     * @param currentPage
     * @param pageSize
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public R list(//@RequestBody Map<String, Object> param
                  @RequestParam(value = "currentPage", required = false) Integer currentPage,
                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                  @RequestParam(value = "startDate", required = false) String startDate,
                  @RequestParam(value = "endDate", required = false) String endDate,
                  @RequestParam(value = "companyName", required = false) String companyName,
                  @RequestParam(value = "companyCode", required = false) String companyCode
    ) {
        Example example = Example.create(ExpressCompany.class);
        example.equal("is_deleted", 0);
        if (currentPage != null && pageSize != null) {
            example.setPage(currentPage);
            example.setRows(pageSize);
        }
        if (startDate != null && endDate != null) {
            example.greatEqual("gmt_create", startDate + " 00:00:00");
            example.lessEqual("gmt_create", endDate + " 23:59:59");
        }
        if (companyName != null) {
            example.like("company_name", "%" + companyName + "%");
        }
        if (companyCode != null) {
            example.like("company_code", "%" + companyCode + "%");
        }
        example.setOrderBy("sort desc");
        int total = baseService.count(example);
        List<Map<String, Object>> list = baseService.find(example);
        return CommonUtil.msg(list).put("total", total);
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
