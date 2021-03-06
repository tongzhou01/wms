package com.mz.admin.controller;

import com.mz.admin.entity.OperateRecord;
import com.mz.admin.service.OperateRecordService;
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
 * 货物操作记录
 *
 * @author tongzhou
 * @date 2018-03-13 10:16
 **/
@RestController
@RequestMapping("/api/cargo/ops/")
public class OperateRecordController {

    @Autowired
    OperateRecordService operateRecordService;
    @Autowired
    IService<Map<String, Object>> baseService;

    /**
     * 分页查询
     *
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public R list(@RequestBody QueryParam queryParam) {
        int count = operateRecordService.count(queryParam);
        List<Map> index = operateRecordService.index(queryParam);
        return CommonUtil.msg(index).put("total", count);
    }

    /**
     * 新增
     *
     * @param operateRecord
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public R add(@RequestBody OperateRecord operateRecord) {
        operateRecord.setScanTime(new Date());
        int i = operateRecordService.insertSelective(operateRecord);
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
        OperateRecord operateRecord = operateRecordService.selectByPrimaryKey(id);
        return CommonUtil.msg(operateRecord);
    }

    /**
     * 修改
     *
     * @param operateRecord
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public R edit(@RequestBody OperateRecord operateRecord) {
        int i = operateRecordService.updateByPrimaryKeySelective(operateRecord);
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
            i += baseService.del("operate_record", id);
        }
        return CommonUtil.msg(ids, i);
    }

}
