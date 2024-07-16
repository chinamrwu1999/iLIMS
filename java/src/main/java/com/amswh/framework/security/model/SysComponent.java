package com.amswh.framework.security.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("SysComponent")
public class SysComponent {

    @TableId(value = "componentId",type= IdType.AUTO)
    private Long componentId;


    @TableField("name")
    private  String name;

    @TableField("description")
    private String description;

    @TableField("url")
    private String url;

    @TableField("createTime")
    private LocalDateTime createTime;


}
