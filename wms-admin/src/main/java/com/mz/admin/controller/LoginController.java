package com.mz.admin.controller;

import com.mz.admin.service.UserInfoService;
import com.mz.common.entity.R;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {

    Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    UserInfoService userInfoService;

    /**
     * 登陆
     * @param username
     * @param password
     * @param imageCode
     * @param uuid
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public R login(@RequestParam(value = "username", required = true) String username,
                   @RequestParam(value = "password", required = true) String password,
                   @RequestParam(value = "imageCode", required = true) String imageCode,
                   @RequestParam(value = "uuid", required = true) String uuid,
                   HttpServletRequest request
    ) {
        userInfoService.login(username, password, imageCode, uuid);
        return R.ok("ok");
    }


    /**
     * 获取验证码
     * @param uuid
     * @param response
     */
    @RequestMapping(value = "/imageCode", method = RequestMethod.GET)
    public void imageCode(@RequestParam(value = "uuid", required = true) String uuid, HttpServletResponse response) {
        userInfoService.imageCode(uuid, response);
    }


}
