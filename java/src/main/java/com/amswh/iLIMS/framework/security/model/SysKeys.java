package com.amswh.iLIMS.framework.security.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("SysKeys")
public class SysKeys {

    @TableId(value = "id",type = IdType.INPUT)
    private  String id;

    @TableField("privateKey")
    private  String privateKey;

    @TableField("publicKey")
    private  String publicKey;

    @TableField("createTime")
    private LocalDateTime createTime;
}
