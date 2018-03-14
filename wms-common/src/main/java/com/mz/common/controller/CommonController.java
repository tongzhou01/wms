package com.mz.common.controller;

import com.mz.common.entity.R;
import com.mz.common.util.CommonUtil;
import com.mz.common.util.WebTokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * CommonController
 *
 * @author tongzhou
 * @date 2018-03-14 16:05
 **/
@Controller
@RequestMapping("/api/common")
public class CommonController {

    @RequestMapping(value = "/getUserInfoByToken", method = RequestMethod.POST)
    @ResponseBody
    public R getUserInfoByToken(@RequestBody String token) {
        Map<String, Object> tokenMsg = WebTokenUtil.parserJavaWebToken(token);
        return CommonUtil.msg(tokenMsg);
    }
}
