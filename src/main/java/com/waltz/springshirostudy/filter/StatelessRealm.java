package com.waltz.springshirostudy.filter;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.waltz.springshirostudy.common.result.ResponseResult;
import com.waltz.springshirostudy.constant.JwtToken;
import com.waltz.springshirostudy.constant.RedisConstant;
import com.waltz.springshirostudy.entity.LoginParam;
import com.waltz.springshirostudy.entity.UserInfo;
import com.waltz.springshirostudy.service.RedisService;
import com.waltz.springshirostudy.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 无状态认证
 * @createDate: 2023/4/6 15:38
 **/
@Slf4j
public class StatelessRealm extends AuthorizingRealm {
    @Resource
    private RedisService redisService;
    @Resource
    private SystemUserService systemUserService;

    @Override
    public boolean supports(AuthenticationToken token) {
        /*
         * 仅支持jwtToken 类型的Token，
         * 那么如果在StatelessAuthFilter类中返回的是UsernamePasswordToken，那么将会报如下错误信息：
         * Please ensure that the appropriate Realm implementation is configured correctly or
         * that the realm accepts AuthenticationTokens of this type.StatelessAuthFilter.isAccessAllowed()
         */
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String account = (String) principals.getPrimaryPrincipal();
        LoginParam loginParam = new LoginParam();
        loginParam.setAccount(account);
        Set<String> permsSet = new HashSet<>();
        // 查询角色和权限(这里根据业务自行查询)
        ResponseResult<List<String>> result = systemUserService.listUserPermissionByAccount(loginParam);
        if (ResponseResult.ifSuccess(result)){
            List<String> permsList = result.getData();
            if (Objects.nonNull(permsList) && CollectionUtils.isNotEmpty(permsList)){
                permsSet.addAll(permsList);
            }
            // 将查到的权限和角色分别传入authorizationInfo中
            simpleAuthorizationInfo.setStringPermissions(permsSet);
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String account = (String) token.getPrincipal();
        String accessToken = (String) token.getCredentials();

        log.info("shiro用户登陆信息，account：{}，accessToken:{}", account, accessToken);

        // 账号为空
        if (StringUtils.isBlank(account)) {
            log.warn("account为空");
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        LoginParam loginParam = new LoginParam();
        loginParam.setAccount(account);
        // 查询用户是否存在
        ResponseResult<UserInfo> userInfoRes = systemUserService.getUserInfoByAccount(loginParam);
        if (!ResponseResult.ifSuccess(userInfoRes)){
            log.warn("用户信息为空");
            throw new AuthenticationException("该帐号不存在(The account does not exist.):" + account);
        }

        // 校验accessToken
        String refreshToken = (String) redisService.getObject(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account);
        log.info("redis中用户token信息，refreshToken:{}", refreshToken);
        if (StringUtils.isNotBlank(refreshToken) && accessToken.equals(refreshToken)) {
            // 刷新refreshToken
            redisService.expire(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account, RedisConstant.REFRESH_TOKEN_EXPIRATION_TIME);
            return new SimpleAuthenticationInfo(account, accessToken, getName());
        }
        throw new AuthenticationException("身份认证失败，请重新登陆！");
    }
}
