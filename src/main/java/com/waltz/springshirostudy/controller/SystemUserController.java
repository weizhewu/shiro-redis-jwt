package com.waltz.springshirostudy.controller;

import com.waltz.springshirostudy.common.result.ResponseResult;
import com.waltz.springshirostudy.entity.LoginParam;
import com.waltz.springshirostudy.service.SystemUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 系统用户接口
 * @createDate: 2023/4/10 14:58
 **/
@RestController
@RequestMapping("/system/user")
public class SystemUserController {
    @Resource
    private SystemUserService systemUserService;
    @PostMapping("/login")
    public ResponseResult<String> login(@RequestBody LoginParam loginParam){

        return systemUserService.login(loginParam);
    }
}
