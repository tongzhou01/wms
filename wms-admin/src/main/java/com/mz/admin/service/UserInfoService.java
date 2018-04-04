package com.mz.admin.service;

import com.mz.admin.entity.UserInfo;
import com.mz.common.entity.R;
import com.mz.common.service.IService;

import javax.servlet.http.HttpServletResponse;
public interface UserInfoService extends IService<UserInfo> {

    R login(String username, String password, String imageCode, String uuid);

    void imageCode(String uuid, HttpServletResponse response);

    String txTest(String s);
}