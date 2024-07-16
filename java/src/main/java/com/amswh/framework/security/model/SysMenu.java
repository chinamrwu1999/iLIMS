package com.amswh.framework.security.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("SysMenu")
public class SysMenu {
    @TableId(value = "roleId",type= IdType.AUTO)
    private  Long menuId;
    @TableField("name")
    private  String name;
    @TableField("label")
    private String label;
    @TableField("parentId")
    private Long parentId;

    @TableField("createTime")
    private LocalDateTime createTime;

}
