package com.amswh.iLIMS.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("SysMenu")
public class SysMenu {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField("parentId")
    private int parentId;

    @TableField("orderIndex")
    private int orderIndex;


    @TableField("name")
    private String name;


    @TableField("url")
    private String URL;

    @TableField("status")
    private boolean status;


    @TableField("perms")
    private String perms;

    @TableField("icon")
    private String icon;

    @TableField("type")
    private String type;

    @TableField("createTime")
    private LocalDateTime createTime;
}
