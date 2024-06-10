package com.amswh.iLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("PartyContact")
public class PartyContact {

    @TableId(value="id",type = IdType.AUTO)
    private Integer id;

    @TableField("partyId")
    private String partyId;

    @TableField("contactType")
    private String contactType;

    @TableField("contact")
    private String contact;

    @TableField("createTime")
    private LocalDateTime createTime;


}
