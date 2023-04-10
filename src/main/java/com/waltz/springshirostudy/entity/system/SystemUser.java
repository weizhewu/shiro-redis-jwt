package com.waltz.springshirostudy.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @description: 系统用户
 * @createDate: 2023/4/6 15:49
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(tableName = "t_system_user",keyFields = "user_id")
@TableName(value = "t_system_user")
public class SystemUser implements Serializable {
    @Column(name = "user_id",rule = "comment '主键，唯一标识'")
    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;

    // 账号
    @Column(name = "account",rule = "not null comment '账号'")
    private String account;

    // 密码
    @Column(name = "password",rule = "not null comment '密码'")
    private String password;

    // 用户名称
    @Column(name = "user_name",rule = "comment '用户名称'")
    private String userName;

    // 邮箱
    @Column(name = "email",rule = "comment '邮箱'")
    private String email;

    // 状态
    @Column(name = "status",rule = "not null default 1 comment '状态 1：正常'")
    private Integer status;

    // 逻辑删除标志 0：未删除 1：已删除
    @Column(name = "del_flag",rule = "not null default 0 comment '逻辑删除标志 0：未删除 1：已删除'")
    private Integer delFlag;

    // 用户类型
    @Column(name = "user_type",rule = "comment '用户类型'")
    private Integer userType;

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
