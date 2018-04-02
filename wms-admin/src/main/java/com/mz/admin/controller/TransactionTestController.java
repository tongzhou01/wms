package com.mz.admin.controller;

import com.mz.admin.entity.UserInfo;
import com.mz.admin.service.UserInfoService;
import com.mz.common.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UserInfoService userInfoService;

    @RequestMapping("/test")
    public R TransactionTest(@RequestBody UserInfo userInfo) {
        userInfo.setSex((byte) 1);
        userInfoService.txTest(userInfo);
        return R.ok();
    }
}
