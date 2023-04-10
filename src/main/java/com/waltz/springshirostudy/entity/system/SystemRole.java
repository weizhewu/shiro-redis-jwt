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
 * @description: 系统角色
 * @createDate: 2023/4/6 18:21
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(tableName = "t_system_role",keyFields = "role_id")
public class SystemRole implements Serializable {
    @Column(name = "role_id",rule = "comment '主键，唯一标识'")
    @TableId(type = IdType.ASSIGN_UUID)
    private String roleId;

    @Column(name = "role_name",rule = "not null comment '角色名称'")
    private String roleName;

    // 备注
    @Column(name = "remark",rule ="comment '备注'" )
    private String remark;

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
