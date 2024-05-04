package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("AnalyteProcess")
public class AnalyteProcess {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("analyteCode")
    private String  analyteCode;

    @TableField("action")
    private String  action;

    @TableField("status")
    private String  status;

    @TableField("employeeId")
    private String  employeeId;

    @TableField("remark")
    private String  remark;
}
