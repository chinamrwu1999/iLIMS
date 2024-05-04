package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("CompanyRelation")
public class CompanyRelation {

    @TableField("fromId")
    private Integer fromId;

    @TableField("toId")
    private Integer toId;

    @TableField("relationType")
    private String relationType;


}
