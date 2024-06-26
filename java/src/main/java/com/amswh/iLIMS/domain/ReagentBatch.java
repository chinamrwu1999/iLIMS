package com.amswh.iLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("reagentBatch")
public class ReagentBatch {

    @TableId(value = "id",type= IdType.AUTO)
    private  int id;

    @TableField("reagentId")
    private String reagentId;

    @TableField("batchNo")
    private String batchNo;

    @TableField("quantity")
    private int quantity;

    @TableField("produceDate")
    private LocalDate produceDate;

    @TableField("expireDate")
    private LocalDate expireDate;

    @TableField("createTime")
    private LocalDateTime createTime;

}
