package com.amswh.iLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ExpSteps")
public class ExpSteps {

    @TableId(value = "id",type= IdType.AUTO)
    private int id;

    @TableField("productCode")
    private String productCode;

    @TableField("stepId")
    private String stepId;

    @TableField("stepName")
    private String stepName;

    @TableField("reagentId")
    private String reagentId;

    @TableField("createTime")
    private String createTime;

}
