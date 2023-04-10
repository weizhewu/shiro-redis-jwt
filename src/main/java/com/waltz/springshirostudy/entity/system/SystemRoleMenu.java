package com.waltz.springshirostudy.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.waltz.springshirostudy.utils.table.Column;
import com.waltz.springshirostudy.utils.table.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 角色对应的菜单
 * @createDate: 2023/4/7 9:16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(tableName = "t_system_role_menu",keyFields = "role_menu_id")
public class SystemRoleMenu implements Serializable {
    @Column(name = "role_menu_id",rule = "comment '主键，唯一标识'")
    @TableId(type = IdType.ASSIGN_UUID)
    private String roleMenuId;

    // 角色id
    @Column(name = "role_id",rule = "not null  comment '角色id'")
    private String roleId;

    // 菜单id
    @Column(name = "menu_id",rule = "not null  comment '菜单id'")
    private String menuId;

    // 创建人
    @Column(name = "create_id",rule = "comment '创建人'")
    private String createId;

    // 创建时间
    @Column(name = "create_date",rule = "not null default CURRENT_TIMESTAMP comment '创建时间'")
    private Timestamp createDate;

    // 更新人
    @Column(name = "update_id",rule = "comment '更新人'")
    private String updateId;

    // 更新时间
    @Column(name = "update_date",rule = "not null default CURRENT_TIMESTAMP comment '更新时间'")
    private Timestamp updateDate;
}
