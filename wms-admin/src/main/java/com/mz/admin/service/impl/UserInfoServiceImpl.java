package com.mz.admin.service.impl;

import com.mz.admin.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl {

    @Autowired
    UserInfoDao userInfoDao;
    //@Autowired
    //BaseDao<ReturnMapEntity> baseDao;

    /*@Override
    public IDao<UserInfo> getDao() {
        return userInfoDao;
    }*/

    /*@Override
    public void login(String username, String password, String imageCode, String uuid) {
        Example example = new Example();
        example.setColumns(new String[]{"id"});
        example.equal("is_deleted", 0);
        example.equal("username", username);
        example.equal("password", MD5Util.MD5Encode(password));
        //ReturnMapEntity userInfo = baseDao.load(example);
        //System.out.println(JSON.toJSONString(userInfo));
    }*/
}