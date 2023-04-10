package com.waltz.springshirostudy.entity;

import com.waltz.springshirostudy.entity.system.SystemMenuModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 用户信息
 * @createDate: 2023/4/7 11:01
 **/
@Data
public class UserInfo implements Serializable {
    // 账号
    private String account;

    // 用户名称
    private String userName;

    // 用户权限列表
    private List<String> permissions;

    // 用户系统菜单
    private List<SystemMenuModel> menus;

    // token
    private String token;
}
