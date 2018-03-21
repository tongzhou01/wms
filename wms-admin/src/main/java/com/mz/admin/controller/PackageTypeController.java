package com.mz.admin.controller;

import com.mz.admin.entity.PackageType;
import com.mz.admin.service.PackageTypeService;
import com.mz.common.entity.QueryParam;
import com.mz.common.entity.R;
import com.mz.common.service.IService;
import com.mz.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/package/")
public class PackageTypeController {

    @Autowired
    PackageTypeService packageTypeService;
    @Autowired
    IService<Map<String, Object>> baseService;

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public R list(@RequestBody QueryParam param
    ) {
        int total = baseService.count(param);
        List<Map> list = baseService.index(param);
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
