package com.waltz.springshirostudy.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import com.waltz.springshirostudy.utils.table.Column;
import com.waltz.springshirostudy.utils.table.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 用户角色
 * @createDate: 2023/4/6 18:25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(tableName = "t_system_user_role",keyFields = "user_role_id")
public class SystemUserRole {
    @Column(name = "user_role_id",rule = "comment '主键，唯一标识'")
    @TableId(type = IdType.ASSIGN_UUID)
    private String userRoleId;

    // 用户id
    @Column(name = "user_id",rule = "not null  comment '用户id'")
    private String userId;

    // 角色id
    @Column(name = "role_id",rule = "not null  comment '角色id'")
    private String roleId;

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
