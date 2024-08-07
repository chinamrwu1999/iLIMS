package com.amswh.iLIMS.framework.system.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("UserLogin")
public class LoginLog {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    @TableField("userId")
    public String  userId ;

    @TableField("loginType")
    public String  loginType ;

    @TableField("loginTime")
    public LocalDateTime loginTime;

    @TableField("checkoutTime")
    public LocalDateTime checkoutTime;


}
