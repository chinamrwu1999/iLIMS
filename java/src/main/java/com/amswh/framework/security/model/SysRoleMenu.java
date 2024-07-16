package com.amswh.framework.security.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("SysRoleMenu")
public class SysRoleMenu {
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;
    @TableField("roleId")
    private  Long roleId;

    @TableField("menuId")
    private  Long menuId;

    @TableField("createTime")
    private LocalDateTime createTime;
}