package com.mz.admin.service;

import com.mz.admin.entity.UserInfo;
import com.mz.common.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface UserInfoService extends IService<UserInfo>{

    Map login(String username, String password, String imageCode, String uuid);

    void imageCode(String uuid, HttpServletResponse response);
}