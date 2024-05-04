package com.amswh.MYLIMS.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("")
public class Disease {

    @TableId
    @TableField("code")
    private String  code ; //疾病代码

    @TableField("name")
    private String  name ; //疾病名称



}
