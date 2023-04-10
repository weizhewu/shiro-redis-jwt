package com.waltz.springshirostudy.utils.shiro;

import com.waltz.springshirostudy.entity.system.SystemUserModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: Shiro工具类
 * @createDate: 2023/4/7 17:32
 **/
public class ShiroUtils {
    public static Session getSession() {

        return SecurityUtils.getSubject().getSession();
    }


    public static Subject getSubject() {

        return SecurityUtils.getSubject();
    }


    public static SystemUserModel getUserEntity() {

        return (SystemUserModel) SecurityUtils.getSubject().getPrincipal();
    }


    public static String getAccount() {

        return getUserEntity().getAccount();
    }


    public static void setSessionAttribute(Object key, Object value) {

        getSession().setAttribute(key, value);
    }


    public static Object getSessionAttribute(Object key) {

        return getSession().getAttribute(key);
    }


    public static boolean isLogin() {

        return SecurityUtils.getSubject().getPrincipal() != null;
    }


    public static void logout() {

        SecurityUtils.getSubject().logout();
    }


    public static SystemUserModel getUserEntityByCopy() {

        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof SystemUserModel) {
            return (SystemUserModel) principal;
        }
        SystemUserModel userModel = new SystemUserModel();
        BeanUtils.copyProperties(principal, userModel);

        return userModel;

    }
}
