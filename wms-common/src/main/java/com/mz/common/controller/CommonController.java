package com.mz.common.controller;

import com.mz.common.entity.R;
import com.mz.common.util.CommonUtil;
import com.mz.common.util.WXPayUtil;
import com.mz.common.util.WebTokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Random;

/**
 * CommonController
 *
 * @author tongzhou
 * @date 2018-03-14 16:05
 **/
@Controller
@RequestMapping("/api/common")
public class CommonController {

    /**
     * 根据Token获取用户信息
     * @param token
     * @return
     */
    @RequestMapping(value = "/getUserInfoByToken", method = RequestMethod.POST)
    @ResponseBody
    public R getUserInfoByToken(@RequestBody String token) {
        Map<String, Object> tokenMsg = WebTokenUtil.parserJavaWebToken(token);
        return CommonUtil.msg(tokenMsg);
    }

    /**
     * 获取内单号
     * @return
     */
    @RequestMapping(value = "/order_no", method = RequestMethod.GET)
    @ResponseBody
    public R getOrderNo() {
        Random random = new Random();
        long randomNum = WXPayUtil.getCurrentTimestampMs() + random.nextInt(4);
        return CommonUtil.msg("MZ" + randomNum);
    }


}
