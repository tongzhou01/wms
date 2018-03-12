package com.mz.admin.service.impl;

import com.mz.admin.dao.UserInfoDao;
import com.mz.admin.entity.UserInfo;
import com.mz.admin.service.UserInfoService;
import com.mz.common.component.RedisCache;
import com.mz.common.dao.BaseDao;
import com.mz.common.dao.IDao;
import com.mz.common.entity.Example;
import com.mz.common.entity.R;
import com.mz.common.service.BaseService;
import com.mz.common.util.CommonUtil;
import com.mz.common.util.MD5Util;
import com.mz.common.util.ValidateCodeUtil;
import com.mz.common.util.WebTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends BaseService<UserInfo> implements UserInfoService {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    BaseDao<Map> baseDao;
    @Autowired
    RedisCache redisCache;

    @Override
    public IDao<UserInfo> getDao() {
        return userInfoDao;
    }

    @Override
    public R login(String username, String password, String imageCode, String uuid) {
        if (imageCode.toUpperCase().equals(redisCache.getValue(uuid))) {
            Example example = Example.create(UserInfo.class);
            example.equal("is_deleted", 0);
            example.setTableName("user_info");
            example.equal("user_name", username);
            example.equal("password", MD5Util.MD5Encode(password));
            Map userInfoMap = baseDao.load(example);
            String token = WebTokenUtil.createJavaWebToken(userInfoMap);
            return CommonUtil.msg(userInfoMap).put("token",token);
        } else {
            return R.error(500,"验证码错误");
        }
    }

    @Override
    @RequestMapping(value = "/imageCode", method = RequestMethod.GET)
    public void imageCode(@RequestParam(value = "uuid", required = true) String uuid, HttpServletResponse response) {
        //生成随机字串
        String verifyCode = ValidateCodeUtil.generateVerifyCode(4);
        redisCache.setValue(uuid, verifyCode);
        //生成图片
        int w = 200, h = 80;
        try {
            ValidateCodeUtil.outputImage(w, h, response.getOutputStream(), verifyCode);

        } catch (IOException e) {
        }
    }
}