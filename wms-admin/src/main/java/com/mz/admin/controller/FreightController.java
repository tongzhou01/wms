package com.mz.admin.controller;

import com.mz.admin.entity.Freight;
import com.mz.admin.service.FreightService;
import com.mz.common.entity.QueryParam;
import com.mz.common.entity.R;
import com.mz.common.service.IService;
import com.mz.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运费配置
 *
 * @author tongzhou
 * @date 2018-03-20 16:23
 **/
@RestController
@RequestMapping("/api/freight/")
public class FreightController {
    @Autowired
    FreightService freightService;

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
        int total = freightService.countDetail(queryParam);
        List<Map> list = freightService.selectFreightDetail(queryParam);
        return CommonUtil.msg(list).put("total", total);
    }

    /**
     * 新增
     *
     * @param freight
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public R add(@RequestBody Freight freight) {
        Map map = new HashMap();
        map.put("countryId",freight.getDestination());
        map.put("packageTypeId",freight.getPackageType());
        map.put("productTypeId",freight.getProductType());
        QueryParam queryParam = new QueryParam(map);
        Freight freight1 = freightService.selectFreight(queryParam);
        if (freight1 == null) {
            return R.error("该类型运费已存在");
        }
        freight.setGmtCreate(new Date());
        int i = freightService.insertSelective(freight);
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
        Freight freight = freightService.selectByPrimaryKey(id);
        return CommonUtil.msg(freight);
    }

    /**
     * 修改
     *
     * @param freight
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public R edit(@RequestBody Freight freight) {
        int i = freightService.updateByPrimaryKeySelective(freight);
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
            i += baseService.del("freight", id);
        }
        return CommonUtil.msg(ids, i);
    }

    /**
     * 订单运费计算
     *
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/freight_price", method = RequestMethod.POST)
    @ResponseBody
    public R getFreightPrice(@RequestBody QueryParam queryParam
                             ) {
        BigDecimal weight = BigDecimal.valueOf(Double.valueOf(queryParam.get("weight").toString()));
        Freight freightInfo = freightService.selectFreight(queryParam);
        Map<String, Object> map = new HashMap<>();
        if (freightInfo != null) {
            BigDecimal initPrice = freightInfo.getInitPrice();//初始价格
            BigDecimal initWeight = freightInfo.getInitWeight();//首重
            BigDecimal steppingPrice = freightInfo.getSteppingPrice();//续重价格
            BigDecimal steppingWeight = freightInfo.getSteppingWeight();//步进
            BigDecimal fuelCharge = freightInfo.getFuelCharge();//步进
            BigDecimal finalPrice = BigDecimal.valueOf(0);
            if (weight.compareTo(initWeight) == -1) {//-1表示小于，0是等于，1是大于
                finalPrice = initPrice.add(fuelCharge);
            } else {
                finalPrice = ((weight.subtract(initWeight)).divide(steppingWeight, 0, BigDecimal.ROUND_UP)).multiply(steppingPrice).add(fuelCharge).add(initPrice);
            }
            map.put("finalPrice", finalPrice);
            map.put("priceId", freightInfo.getId());
            return CommonUtil.msg(map);
        } else {
            return CommonUtil.msg("未查询到运费信息");
        }
    }

}
