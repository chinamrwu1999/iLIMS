package com.amswh.iLIMS.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("reagent")
public class Reagent {

    @TableId("id")
    private String id;

    @TableField("name")
    private String name;

    @TableField("model")
    private String model;

    @TableField("spec")
    private int spec;

    @TableField("createTime")
    private LocalDateTime createTime;

}
