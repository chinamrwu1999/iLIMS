package com.amswh.iLIMS.project.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("surveyTemplate")
public class SurveyTemplate {

    @TableId("productCode")
    private String productCode;

    @TableField("template")
    private String template;

    @TableField("createTime")
    private LocalDateTime createTime;
}
