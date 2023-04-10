package com.waltz.springshirostudy.service;

import com.waltz.springshirostudy.common.result.ResponseResult;
import com.waltz.springshirostudy.common.result.ResultCode;
import com.waltz.springshirostudy.entity.system.SystemMenuModel;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 系统功能菜单
 * @createDate: 2023/4/10 16:33
 **/
public interface SystemMenuService {
    /**
     * 新增一个菜单
     * @param systemMenuModel 自定义系统菜单实体类
     * @return 新增数量
     */
    ResponseResult<ResultCode> saveOne(SystemMenuModel systemMenuModel);

}
