package com.mz.admin.controller;

import com.mz.admin.service.UserInfoService;
import com.mz.common.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 登陆
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserInfoService userInfoService;

    /**
     * 账号密码登陆
     *
     * @param username
     * @param password
     * @param imageCode
     * @param uuid
     * @return R
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public R login(@RequestParam(value = "username") String username,
                   @RequestParam(value = "password") String password,
                   @RequestParam(value = "imageCode") String imageCode,
                   @RequestParam(value = "uuid") String uuid
    ) {
        return userInfoService.login(username, password, imageCode, uuid);
    }


    /**
     * 获取验证码
     *
     * @param uuid
     * @param response
     */
    @RequestMapping(value = "/image_code", method = RequestMethod.GET)
    public void imageCode(@RequestParam(value = "uuid") String uuid, HttpServletResponse response) {
        userInfoService.imageCode(uuid, response);
    }


}
