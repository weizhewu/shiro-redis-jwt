package com.waltz.springshirostudy.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.waltz.springshirostudy.utils.table.Column;
import com.waltz.springshirostudy.utils.table.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 系统功能菜单
 * @createDate: 2023/4/6 18:30
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(tableName = "t_system_menu",keyFields = "guid")
public class SystemMenu {
    @Column(name = "guid",rule = "comment '主键，唯一标识'")
    @TableId(type = IdType.AUTO)
    private Integer guid;

    @Column(name = "menu_id",rule = "not null comment '菜单id'")
    private String menuId;

    // 上级菜单id
    @Column(name = "parent_id",rule = "not null comment '上级菜单id，0时是菜单'")
    private String parentId;

    // 菜单名称
    @Column(name = "menu_name",rule = "not null comment '菜单名称'")
    private String menuName;

    // 菜单url
    @Column(name = "url",rule = "not null comment '菜单url'")
    private String url;

    // 菜单标识
    @Column(name = "perms",rule = "not null comment '菜单标识'")
    private String perms;

    // 菜单类型： 0：目录，1：菜单，2：元素
    @Column(name = "menu_type",rule = "not null comment '菜单类型： 0：目录，1：菜单，2：元素'")
    private Integer menuType;

    // 菜单图标
    @Column(name = "icon",rule = "comment '菜单图标'")
    private String icon;

    // 状态 1：正常 0：禁用
    @Column(name = "status",rule = "not null default 1 comment '状态 1：正常 0：禁用'")
    private Integer status;

    // 排序
    @Column(name = "sort",rule = "comment '排序'")
    private Integer sort;

    // 是否显示
    @Column(name = "display",rule = "not null default 1 comment '是否显示'")
    private Integer display;

    // 创建人
    @Column(name = "create_id",rule = "not null comment '创建人'")
    private String createId;

    // 创建时间
    @Column(name = "create_date",rule = "not null default CURRENT_TIMESTAMP comment '创建时间'")
    private Timestamp createDate;

    // 更新人
    @Column(name = "update_id",rule = "not null comment '更新人'")
    private String updateId;

    // 更新时间
    @Column(name = "update_date",rule = "not null default CURRENT_TIMESTAMP comment '更新时间'")
    private Timestamp updateDate;
}
