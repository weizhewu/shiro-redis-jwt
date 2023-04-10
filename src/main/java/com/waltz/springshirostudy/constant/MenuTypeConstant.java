package com.waltz.springshirostudy.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 菜单类型常量类
 * @createDate: 2023/4/10 16:43
 **/
public class MenuTypeConstant {
    /**
     * 菜单编码前缀  目录
     */
    private static final String MENU_PREFIX_C = "C";
    /**
     * 菜单编码前缀  菜单
     */
    private static final String MENU_PREFIX_M = "M";
    /**
     * 菜单编码前缀  元素
     */
    private static final String MENU_PREFIX_E = "E";
    /**
     * 角色编码前缀 元素
     */
    private static final String ROLE_PREFIX_R = "R";


    public static final HashMap<Integer,String> TYPE_MAP = new HashMap<Integer,String>(){{
        put(1,MENU_PREFIX_C);
        put(2,MENU_PREFIX_M);
        put(3,MENU_PREFIX_E);
        put(4,ROLE_PREFIX_R);
    }};

}
