package com.waltz.springshirostudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waltz.springshirostudy.entity.system.SystemUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 系统用户
 * @createDate: 2023/4/7 9:54
 **/
@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {
    /**
     * 根据用户账号查询用户权限
     * @param account 账号
     * @return 权限集合
     */
    List<String> listUserPermissionByAccount(String account);
}
