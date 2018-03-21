package com.mz.admin.controller;

import com.mz.admin.entity.PackageType;
import com.mz.admin.service.PackageTypeService;
import com.mz.common.entity.Example;
import com.mz.common.entity.R;
import com.mz.common.service.IService;
import com.mz.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/packageType")
public class PackageTypeController {

    @Autowired
    PackageTypeService packageTypeService;
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
                  @RequestParam(value = "endDate", required = false) String endDate
    ) {
        Example example = Example.create(PackageType.class);
        example.equal("is_deleted", 0);
        if (currentPage != null && pageSize != null) {
            example.setPage(currentPage);
            example.setPage(pageSize);
        }
        if (startDate != null && endDate != null) {
            example.greatEqual("gmt_create", startDate + " 00:00:00");
            example.lessEqual("gmt_create", endDate + " 23:59:59");
        }
        example.setOrderBy("gmt_create desc");
        int total = baseService.count(example);
        List<Map<String, Object>> list = baseService.find(example);
        return CommonUtil.msg(list).put("total", total);
    }

    /**
     * 新增
     *
     * @param packageType
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public R add(@RequestBody PackageType packageType) {
        int i = packageTypeService.insertSelective(packageType);
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
        PackageType packageType = packageTypeService.selectByPrimaryKey(id);
        return CommonUtil.msg(packageType);
    }

    /**
     * 修改
     *
     * @param packageType
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public R edit(@RequestBody PackageType packageType) {
        int i = packageTypeService.updateByPrimaryKeySelective(packageType);
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
            i += baseService.del("package_type", id);
        }
        return CommonUtil.msg(ids, i);
    }
}
