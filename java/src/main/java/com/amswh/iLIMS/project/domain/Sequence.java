package com.amswh.iLIMS.project.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sequence")
public class Sequence {

    @TableId("seqName")
    private String seqName;

    @TableField("seqId")
    private Long seqId;

    @TableField("updateTime")
    private LocalDateTime updateTime;

}
