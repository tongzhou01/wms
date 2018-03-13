package com.mz.admin.controller;

import com.mz.admin.entity.OperateRecord;
import com.mz.admin.service.OperateRecordService;
import com.mz.common.entity.Example;
import com.mz.common.entity.R;
import com.mz.common.service.IService;
import com.mz.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 货物操作记录
 *
 * @author tongzhou
 * @date 2018-03-13 10:16
 **/
@RestController
@RequestMapping("/api/cargo/ops_record/")
public class OperateRecordController {

    @Autowired
    OperateRecordService operateRecordService;
    @Autowired
    IService<Map<String, Object>> baseService;

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public R list(//@RequestBody Map<String, Object> param
                  @RequestParam(value = "page", required = false) Integer page,
                  @RequestParam(value = "rows", required = false) Integer rows,
                  @RequestParam(value = "startDate", required = false) String startDate,
                  @RequestParam(value = "endDate", required = false) String endDate
    ) {
        Example example = Example.create(OperateRecord.class);
        example.equal("is_deleted", 0);
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
     * @param operateRecord
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public R add(@RequestBody OperateRecord operateRecord) {
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
    public R getById(@RequestParam Integer id) {
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
