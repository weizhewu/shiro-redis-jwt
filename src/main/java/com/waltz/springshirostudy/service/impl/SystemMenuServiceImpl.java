package com.waltz.springshirostudy.service.impl;

import com.waltz.springshirostudy.common.result.ResponseResult;
import com.waltz.springshirostudy.common.result.ResultCode;
import com.waltz.springshirostudy.constant.MenuTypeConstant;
import com.waltz.springshirostudy.entity.system.SystemMenuModel;
import com.waltz.springshirostudy.mapper.SystemMenuMapper;
import com.waltz.springshirostudy.service.SystemMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 系统功能菜单
 * @createDate: 2023/4/10 16:35
 **/
@Service
@Slf4j
public class SystemMenuServiceImpl implements SystemMenuService {
    @Resource
    private SystemMenuMapper systemMenuMapper;
    @Override
    public ResponseResult<ResultCode> saveOne(SystemMenuModel systemMenuModel) {
        log.info("进入方法体saveOne，参数为:{}",systemMenuModel);
        if (Objects.isNull(systemMenuModel)){
            log.info("参数systemMenuModel为空");
            return ResponseResult.failure(ResultCode.PARAM_IS_BLANK);
        }
        if (Objects.isNull(systemMenuModel.getMenuType())){
            log.info("参数menuType不存在");

            return ResponseResult.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        int menuType = systemMenuModel.getMenuType();
        if (!MenuTypeConstant.TYPE_MAP.containsKey(menuType)){
            log.info("参数menuType:{}，无效",systemMenuModel.getMenuType());
            return ResponseResult.failure(ResultCode.PARAM_IS_INVALID);
        }

        return ResponseResult.success();
    }
}
