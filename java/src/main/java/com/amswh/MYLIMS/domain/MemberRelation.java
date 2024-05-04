package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("MemberRelation")
public class MemberRelation {

    @TableId(value = "id",type = IdType.AUTO)
    private long id;

    @TableField("fromMember")
    private long fromMember;

    @TableField("toMember")
    private long toMember;

    @TableField("relationType")
    private String relationType;


    @TableField("createTime")
    private String createTime;

}
