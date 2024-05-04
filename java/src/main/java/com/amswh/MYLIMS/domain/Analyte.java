package com.amswh.MYLIMS.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("Analyte")
public class Analyte {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("barCode")
    private String  barCode;

    @TableField("analyteCode")
    private String  analyteCode;

    @TableField("productCode")
    private String  productCode;

    @TableField("createTime")
    private LocalDateTime createTime ;


}
