package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import lombok.NonNull;

@Data
@TableName("Patient")
public class Patient  {

    @TableId(value = "patientId",type = IdType.AUTO)
    private Integer patientId;


    @NotBlank
    @TableField("barCode")
    private String barCode;

    @NotBlank
    @TableField("name")
    private String name;

    @NotBlank
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

    @TableField("src") //本条记录的来源：微信扫码绑定、第三方API、手工录入
    private String src;

    @TableField("createTime")
    private String createTime;
}
