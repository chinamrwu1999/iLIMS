package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class Person {

    @TableId(value = "id",type = IdType.AUTO)
    private long id;

    @TableField("name")
    private String name;

    @TableField("gender")
    private String gender;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("username")
    private String userName;

    @TableField("password")
    private String password;
    @TableField("openId")
    private String openId;

    @TableField("createTime")
    private String createTime;

}
