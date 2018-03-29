package com.mz.admin.controller;

import com.mz.admin.entity.CargoInfo;
import com.mz.admin.entity.CustomerInfo;
import com.mz.admin.entity.OperateRecord;
import com.mz.admin.model.CargoDetailVO;
import com.mz.admin.service.CargoInfoService;
import com.mz.admin.service.CustomerInfoService;
import com.mz.admin.service.OperateRecordService;
import com.mz.common.entity.QueryParam;
import com.mz.common.entity.R;
import com.mz.common.service.IService;
import com.mz.common.util.CommonUtil;
import com.mz.common.util.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    OperateRecordService operateRecordService;
    @Autowired
    CustomerInfoService customerInfoService;
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
                  /*@RequestParam(value = "currentPage", required = false) Integer currentPage,
                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                  @RequestParam(value = "startDate", required = false) String startDate,
                  @RequestParam(value = "endDate", required = false) String endDate,
                  @RequestParam(value = "orderNo", required = false) String orderNo,
                  @RequestParam(value = "customerNo", required = false) String customerNo,
                  @RequestParam(value = "type", required = false) Integer type*/
    ) {
        int total = cargoInfoService.count(param);
        List<Map> list = cargoInfoService.index(param);
        return CommonUtil.msg(list).put("total", total);
    }

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "getDetail", method = RequestMethod.POST)
    public R getDetail(@RequestBody QueryParam param
    ) {
        int total = cargoInfoService.countDetail(param);
        List<CargoDetailVO> list = cargoInfoService.getDetail(param);
        return CommonUtil.msg(list).put("total", total);
    }

    /**
     * 新增
     *
     * @param cargoInfo
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public R add(@RequestBody CargoInfo cargoInfo, @RequestParam Byte type) {
        CustomerInfo customerInfo = customerInfoService.selectByCustomerNo(cargoInfo.getCustomerNo());
        if (customerInfo == null) {
            return R.error("未查询到客户信息");
        }
        cargoInfo.setGmtCreate(new Date());
        Random random = new Random();
        if (type == 0) {
            long randomNum = WXPayUtil.getCurrentTimestampMs() + random.nextInt(4);
            cargoInfo.setOrderNo("MZ" + randomNum);
        }
        int i = cargoInfoService.insertSelective(cargoInfo);
        Long id = cargoInfo.getId();
        if (id != null && id > 0) {
            OperateRecord operateRecord = new OperateRecord();
            operateRecord.setScanTime(new Date());
            operateRecord.setCargoId(id);
            operateRecord.setShelfNo(cargoInfo.getShelfNo());
            operateRecord.setType(type);
            operateRecordService.insertSelective(operateRecord);
        }

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
