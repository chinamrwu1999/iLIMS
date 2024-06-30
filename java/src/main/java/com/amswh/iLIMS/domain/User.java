package com.amswh.iLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("User")
public class User {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    @TableField("partyId")
    public String  partyId ;

    @TableField("password")
    public String  password ;

    @TableField("createTime")
    public LocalDateTime createTime;

}
