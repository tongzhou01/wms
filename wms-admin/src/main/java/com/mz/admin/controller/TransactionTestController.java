package com.mz.admin.controller;

import com.mz.admin.entity.UserInfo;
import com.mz.admin.service.UserInfoService;
import com.mz.common.entity.R;
import com.mz.common.util.CommonUtil;
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
        userInfo.setId(1l);
        String s = userInfoService.txTest("1");
        return CommonUtil.msg(s);
    }

    public static void main(String[] args) {
        char m = 'a';
        char k = 'z';
        int i = m + k;
        System.out.println(i);
    }

    public static String sub(String s, int l) {
        StringBuffer stringBuffer = new StringBuffer();
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                System.out.println("c=" + c);
                count++;
            } else {
                System.out.println("c=" + c + "哈");
                count = count + 2;
            }
            c = 'k';
            if (count > l) {
                break;
            }
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }
}
