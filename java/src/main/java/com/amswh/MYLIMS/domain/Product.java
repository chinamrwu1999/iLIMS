package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("")
public class Product {


    @TableId
    @TableField("code")
    private String  code ; //产品代码


    @TableField("name")
    private String  name ; //产品名称

    @TableField("spec")
    private String  spec ;  // 产品规格

}
