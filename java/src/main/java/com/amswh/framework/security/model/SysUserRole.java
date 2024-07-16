package com.amswh.framework.security.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("SysUserRole")
public class SysUserRole {

    @TableId(value = "id",type= IdType.AUTO)
    private  Long id;

    @TableField("userId")
    private Long userId;

    @TableField("roleId")
    private Long roleId;

    @TableField("createTime")
    private LocalDateTime createTime;




}
