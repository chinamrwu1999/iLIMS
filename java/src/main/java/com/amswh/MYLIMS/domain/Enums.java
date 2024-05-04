package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("enums")
public class Enums {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("code")
    private String code;

    @TableField("name")
    private String name;

    @TableField("type")
    private String type;

    @TableField("index")
    private Integer index;


}
