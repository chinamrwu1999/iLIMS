package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Company")
public class Company {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("code")
    private String code;

    @TableField("fullName")
    private String fullName;

    @TableField("shortName")
    private String shortName;

    @TableField("reportName")
    private String reportName;

    @TableField("createTime")
    private String createTime;

}
