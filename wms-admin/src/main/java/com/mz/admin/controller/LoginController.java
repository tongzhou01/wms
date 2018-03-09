package com.mz.admin.controller;

import com.mz.common.entity.R;
import com.mz.common.util.HttpClientUtil;
import com.mz.common.util.ValidateCodeUtil;
import com.mz.common.util.WXPayUtil;
import com.mz.common.util.WebTokenUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    public static Map<String, String> jedis = new HashMap<String, String>();
    Logger logger = Logger.getLogger(LoginController.class);
//    @Autowired
//    UserInfoService userInfoServiceImpl;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public R login(@RequestParam(value = "username", required = true) String username,
                   @RequestParam(value = "password", required = true) String password,
                   @RequestParam(value = "imageCode", required = true) String imageCode,
                   @RequestParam(value = "uuid", required = true) String uuid,
                   HttpServletRequest request
    ) {
        //userInfoServiceImpl.login(username,password,imageCode,uuid);
        return R.ok("ok");
    }

    @RequestMapping(value = "/imageCode", method = RequestMethod.GET)
    public void imageCode(@RequestParam(value = "uuid", required = true) String uuid, HttpServletResponse response) {
        //生成随机字串  
        String verifyCode = ValidateCodeUtil.generateVerifyCode(4);
        jedis.put(uuid, verifyCode);
        //生成图片  
        int w = 200, h = 80;
        try {
            ValidateCodeUtil.outputImage(w, h, response.getOutputStream(), verifyCode);

        } catch (IOException e) {

        }
    }

    @RequestMapping(value = "/getOpenid", method = RequestMethod.POST)
    @ResponseBody
    public String getOpenid(String url) {
        return HttpClientUtil.doGet(url, null, null);
    }

    /**
     * 加密获取token
     *
     * @param nonceStr
     * @param timestamp
     * @param sign
     * @return
     */
    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    @ResponseBody
    public String getToken(String nonceStr, String timestamp, String sign) {
        Map map = new HashMap<>();
        map.put("nonceStr", nonceStr);
        map.put("timestamp", timestamp);
        String token = "";
        try {
            String signLocal = WXPayUtil.generateSignature(map, "mingz-tech.com");
            if (signLocal.equals(sign.toUpperCase())) {
                map.put("createTime", String.valueOf(new Date().getTime()));
                token = WebTokenUtil.createJavaWebToken(map);
                return token;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Map map = new HashMap<>();
        map.put("timestamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
        map.put("nonceStr", WXPayUtil.generateNonceStr());
        //map.put("sign",sign);
        Set set = map.keySet();
        Arrays.sort(set.toArray());
        try {
            String sign = WXPayUtil.generateSignature(map, "mingz-tech.com");
            System.out.println("sign=" + sign);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
