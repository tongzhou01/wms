package com.mz.admin.controller;

import com.mz.admin.entity.Country;
import com.mz.admin.service.CountryService;
import com.mz.common.entity.Example;
import com.mz.common.entity.R;
import com.mz.common.service.IService;
import com.mz.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 出库类型
 *
 * @author tongzhou
 * @date 2018-03-13 10:16
 **/
@RestController
@RequestMapping("/api/country/")
public class CountryController {

    @Autowired
    CountryService countryService;
    @Autowired
    IService<Map<String, Object>> baseService;

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public R list(//@RequestBody Map<String, Object> param
                  @RequestParam(value = "currentPage", required = false) Integer currentPage,
                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                  @RequestParam(value = "countryCn", required = false) String countryCn,
                  @RequestParam(value = "countryEn", required = false) String countryEn
    ) {
        Example example = Example.create(Country.class);
        example.equal("is_deleted", 0);
        if (currentPage != null && pageSize != null) {
            example.setPage(currentPage);
            example.setPage(pageSize);
        }
        if (countryCn != null) {
            example.like("country_cn", countryCn);
        }
        if (countryEn != null) {
            example.like("country_en", countryEn);
        }
        example.setOrderBy("sort desc");
        int total = baseService.count(example);
        List<Map<String, Object>> list = baseService.find(example);
        return CommonUtil.msg(list).put("total", total);
    }

    /**
     * 新增
     *
     * @param country
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public R add(@RequestBody Country country) {
        int i = countryService.insertSelective(country);
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
        Country country = countryService.selectByPrimaryKey(id);
        return CommonUtil.msg(country);
    }

    /**
     * 修改
     *
     * @param country
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public R edit(@RequestBody Country country) {
        int i = countryService.updateByPrimaryKeySelective(country);
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
            i += baseService.del("country", id);
        }
        return CommonUtil.msg(ids, i);
    }

}
