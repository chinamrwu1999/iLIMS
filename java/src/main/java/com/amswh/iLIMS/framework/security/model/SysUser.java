package com.amswh.iLIMS.framework.security.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@TableName("SysUser")
public class SysUser {


    @TableId(value = "userId",type= IdType.AUTO)
    private Long userId;


    @TableField("userName")
    private  String username;

    @TableField("password")
    private String password;


    @TableField("status")
    private String status;

    @TableField("createTime")
    private LocalDateTime createTime;


    @TableField(exist = false)
    private Set<String> authorities;
    @TableField(exist = false)
    private String token;

}
