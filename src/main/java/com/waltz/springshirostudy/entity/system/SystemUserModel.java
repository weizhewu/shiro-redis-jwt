package com.waltz.springshirostudy.entity.system;

import lombok.Data;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 系统用户信息
 * @createDate: 2023/4/7 17:34
 **/
@Data
public class SystemUserModel {
    private String account;

    private String userName;


    private String password;

    private String email;
}
