package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Patient")
public class Patient  {

    @TableId(value = "patientId",type = IdType.AUTO)
    private Integer patientId;

    @TableField("barCode")
    private String barCode;

    @TableField("name")
    private String name;

    @TableField("gender")
    private String gender;

    @TableField("age")
    private int age;

    @TableField("IDType")
    private String IDType;

    @TableField("IDNumber")
    private String IDNumber;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("livingPlace")
    private String livingPlace;

    @TableField("nativeId")
    private String nativeId; //籍贯地代码

    @TableField("src")
    private String src;

    @TableField("createTime")
    private String createTime;
}
