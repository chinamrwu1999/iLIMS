package com.amswh.framework.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("SysRole")
public class SysRole {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    @TableField("roleKey")
    private String roleKey;

    @TableField("roleName")
    private String roleName;

    @TableField("status")
    private boolean status;

    @TableField("createTime")
    private LocalDateTime createTime;
}
