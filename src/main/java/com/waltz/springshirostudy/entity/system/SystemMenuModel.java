package com.waltz.springshirostudy.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 系统菜单
 * @createDate: 2023/4/7 11:03
 **/
@Data
public class SystemMenuModel implements Serializable {

    // 系统菜单id
    private String id;
    // 系统菜单名称
    private String systemMenuName;
    //父类菜单id
    private String parentId;
    // 系统菜单url
    private String url;
    // 系统菜单图标
    private String icon;
    // 系统菜单类型：0：目录，1：菜单，2：元素
    private Integer menuType;
    // 排序
    private Integer sort;
    // 子菜单
    private List<SystemMenuModel> childrenMenu;
}
