package com.waltz.springshirostudy.filter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.waltz.springshirostudy.entity.LoginParam;
import com.waltz.springshirostudy.entity.system.SystemUser;
import com.waltz.springshirostudy.mapper.SystemUserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 账号信息认证
 * @createDate: 2023/4/10 9:51
 **/
public class UserRealm extends AuthorizingRealm {
    @Resource
    private SystemUserMapper systemUserMapper;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证(登录时调用)
     * @param token AuthenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException 异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String account = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        // 查询用户信息
        LoginParam loginParam = new LoginParam();
        loginParam.setAccount(account);
        LambdaQueryWrapper<SystemUser> systemUserLambdaQueryWrapper = Wrappers.lambdaQuery();
        systemUserLambdaQueryWrapper.select(SystemUser::getPassword,SystemUser::getStatus);
        systemUserLambdaQueryWrapper.eq(SystemUser::getAccount,account);
        systemUserLambdaQueryWrapper.eq(SystemUser::getDelFlag,0);
        SystemUser systemUser = systemUserMapper.selectOne(systemUserLambdaQueryWrapper);

        // 账号不存在
        if (Objects.isNull(systemUser)){
            throw new UnknownAccountException("账号不存在");
        }

        // 密码错误
        if (!password.equals(systemUser.getPassword())) {
            throw new IncorrectCredentialsException("账号密码不正确");
        }

        //账号锁定
        if (systemUser.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        return new SimpleAuthenticationInfo(systemUser,password,getName());
    }
}
