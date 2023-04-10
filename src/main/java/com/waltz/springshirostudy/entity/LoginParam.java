package com.waltz.springshirostudy.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: login所需参数
 * @createDate: 2023/4/7 17:24
 **/
@Data
public class LoginParam implements Serializable {
    // 账号
    private String account;

    // 密码
    private String password;

    // 跳转路径
    private String redirectPath;
}
