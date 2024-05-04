package com.amswh.MYLIMS.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("CompanyPerson")
@Data
public class CompanyPerson {

    @TableField("companyId")
    private Integer companyId;

    @TableField("personId")
    private Integer personId;

    @TableField("relationType")
    private String relationType;

    @TableField("personRole")
    private String personRole;

    @TableField("alive")
    private String alive;

    @TableField("createTime")
    private String createTime;


}
