package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("Member")
@Data
public class Member {
    @TableId(value = "id",type = IdType.AUTO)
    private long id;

    @TableField("name")
    private String name;

    @TableField("gender")
    private String gender;


    @TableField("IDNumber")
    private String IDNumber;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("region")
    private String region;

    @TableField("openId")
    private String openId;

    @TableField("createTime")
    private String createTime;

}
