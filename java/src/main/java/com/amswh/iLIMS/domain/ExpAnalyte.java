package com.amswh.iLIMS.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ExpAnalyte")
public class ExpAnalyte {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;

    @TableField("expPlanId")
     private String expPlanId;

    @TableField("analyteCode")
     private String analyteCode;


}
