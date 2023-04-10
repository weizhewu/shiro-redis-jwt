package com.waltz.springshirostudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.waltz.springshirostudy.common.result.ResponseResult;
import com.waltz.springshirostudy.common.result.ResultCode;
import com.waltz.springshirostudy.constant.AuthConstant;
import com.waltz.springshirostudy.constant.RedisConstant;
import com.waltz.springshirostudy.entity.LoginParam;
import com.waltz.springshirostudy.entity.UserInfo;
import com.waltz.springshirostudy.entity.system.SystemUser;
import com.waltz.springshirostudy.mapper.SystemUserMapper;
import com.waltz.springshirostudy.service.RedisService;
import com.waltz.springshirostudy.service.SystemUserService;
import com.waltz.springshirostudy.utils.number.DifficultPassword;
import com.waltz.springshirostudy.utils.shiro.ShiroUtils;
import com.waltz.springshirostudy.utils.token.JwtTokenUtil;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 系统用户
 * @createDate: 2023/4/7 10:37
 **/
@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Resource
    private SystemUserMapper systemUserMapper;
    @Resource
    private RedisService redisService;

    @Override
    public ResponseResult<List<String>> listUserPermissionByAccount(LoginParam loginParam) {
        if (Objects.isNull(loginParam) || Objects.isNull(loginParam.getAccount())){
            return ResponseResult.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        List<String> perms = systemUserMapper.listUserPermissionByAccount(loginParam.getAccount());
        if (CollectionUtils.isEmpty(perms)){
            return ResponseResult.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }
        return ResponseResult.success(perms);
    }

    @Override
    public ResponseResult<UserInfo> getUserInfoByAccount(LoginParam loginParam) {
        if (Objects.isNull(loginParam) || Objects.isNull(loginParam.getAccount())){
            return ResponseResult.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        LambdaQueryWrapper<SystemUser> systemUserLambdaQueryWrapper = Wrappers.lambdaQuery();
        systemUserLambdaQueryWrapper.eq(SystemUser::getAccount,loginParam.getAccount());
        systemUserLambdaQueryWrapper.eq(SystemUser::getDelFlag,0);
        systemUserLambdaQueryWrapper.select(SystemUser::getAccount,SystemUser::getUserName,SystemUser::getPassword);
        SystemUser systemUser = systemUserMapper.selectOne(systemUserLambdaQueryWrapper);
        UserInfo userInfo = new UserInfo();
        if (Objects.nonNull(systemUser)){
            userInfo.setAccount(systemUser.getAccount());
            userInfo.setUserName(systemUser.getUserName());
            return ResponseResult.success(userInfo);
        }
        return ResponseResult.failure(ResultCode.RESULT_CODE_DATA_NONE);
    }

    @Override
    public ResponseResult login(LoginParam loginParam) {
        UsernamePasswordToken token = null;
        String account = "";
        String password = "";
        String accessToken = null;
        UserInfo userInfo = new UserInfo();
        try {
            if (Objects.isNull(loginParam) || Objects.isNull(loginParam.getAccount()) || Objects.isNull(loginParam.getPassword())){
                return ResponseResult.failure(ResultCode.PARAM_NOT_COMPLETE);
            }
            account = loginParam.getAccount();
            // 对密码进行加密
            password = loginParam.getPassword();
            userInfo.setAccount(account);
            LambdaQueryWrapper<SystemUser> lambdaQueryWrapper = Wrappers.lambdaQuery();
            lambdaQueryWrapper.select(SystemUser::getAccount,SystemUser::getUserName);
            lambdaQueryWrapper.eq(SystemUser::getAccount,account);
            lambdaQueryWrapper.eq(SystemUser::getDelFlag,0);
            SystemUser systemUser = systemUserMapper.selectOne(lambdaQueryWrapper);
            userInfo.setUserName(systemUser.getUserName());
            Subject subject = ShiroUtils.getSubject();

            password = DifficultPassword.difficult(AuthConstant.MD5,password,AuthConstant.SALT,AuthConstant.SALT_TIMES);
            String count = redisService.getObjectString(RedisConstant.SHIRO_REDIS_KEY + account + RedisConstant.DISABLE_TYPE);
            if (StringUtils.isNotBlank(count) && Integer.parseInt(count)>=AuthConstant.DISABLE_COUNT){
                return ResponseResult.failure(ResultCode.USER_ACCOUNT_FORBIDDEN);
            }
            token = new UsernamePasswordToken(account,password);
            subject.login(token);
            accessToken = JwtTokenUtil.createToken(userInfo.getAccount(),userInfo.getUserName());
            redisService.saveObject(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN+account,accessToken,RedisConstant.EXPR_HOUR*2);
            userInfo.setToken(accessToken);

        } catch (UnknownAccountException e){
            return ResponseResult.failure(ResultCode.USER_ACCOUNT_NOT_EXIST);
        } catch (LockedAccountException e) {
            return ResponseResult.failure(ResultCode.USER_ACCOUNT_LOCKED);
        } catch (IncorrectCredentialsException e){
            return ResponseResult.failure(ResultCode.USER_PASSWORD_ERROR);
        }
        return ResponseResult.success(accessToken);
    }

}
