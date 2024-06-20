package com.amswh.iLIMS.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("surveyTemplate")
public class SurveyTemplate {

    @TableId("productId")
    private String productId;

    @TableField("template")
    private JsonNode template;

    @TableField("createTime")
    private LocalDateTime createTime;
}
