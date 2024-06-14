package com.amswh.iLIMS.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("expPlan")
public class ExpPlan {

    @TableId("id")
    private String id;

    @TableField("employeeId")
    private String employeeId;

    @TableField("createTime")
    private LocalDateTime createTime;


}
