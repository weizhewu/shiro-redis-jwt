package com.waltz.springshirostudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waltz.springshirostudy.entity.system.SystemMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 系统功能菜单
 * @createDate: 2023/4/10 16:13
 **/
@Mapper
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {
}
