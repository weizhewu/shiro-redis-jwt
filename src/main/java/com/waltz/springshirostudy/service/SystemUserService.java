package com.waltz.springshirostudy.service;


import com.waltz.springshirostudy.common.result.ResponseResult;
import com.waltz.springshirostudy.entity.LoginParam;
import com.waltz.springshirostudy.entity.UserInfo;

import java.util.List;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 系统用户
 * @createDate: 2023/4/7 10:21
 **/
public interface SystemUserService {
    /**
     * 根据账号查询用户权限
     * @param loginParam login所需参数
     * @return ResponseResult
     */
    ResponseResult<List<String>> listUserPermissionByAccount(LoginParam loginParam);

    /**
     * 根据账号查询用户信息：账号、用户名称
     * @param loginParam login所需参数
     * @return ResponseResult
     */
    ResponseResult<UserInfo> getUserInfoByAccount(LoginParam loginParam);

    ResponseResult<String> login(LoginParam loginParam);
}
