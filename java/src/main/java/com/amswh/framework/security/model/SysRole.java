package com.amswh.framework.security.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("SysRole")
public class SysRole {
    @TableId(value = "roleId",type= IdType.AUTO)
    private Long userId;


    @TableField("name")
    private  String roleName;

    @TableField("status")
    private String status;

    @TableField("createTime")
    private LocalDateTime createTime;
}

