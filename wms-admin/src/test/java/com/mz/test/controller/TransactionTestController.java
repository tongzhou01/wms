package com.mz.test.controller;

import com.alibaba.fastjson.JSON;
import com.mz.common.entity.R;
import com.mz.common.util.CommonUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事务测试
 *
 * @author tongzhou
 * @date 2018-03-28 14:50
 **/
@RestController
@RequestMapping("/transaction")
public class TransactionTestController {

    @RequestMapping("/test")
    public R TransactionTest(@RequestBody Long[] ids) {
        System.out.println(JSON.toJSONString(ids));
        //System.out.println(JSON.toJSONString(userInfo));
        return CommonUtil.msg(1).put("ids",ids);
    }
}
