package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("BioSample")
public class BioSample {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @NotBlank
    @TableField("barCode")
    private String  barCode;

    @NotBlank
    @TableField("type")
    private String sampleType ;

    @TableField("sender")
    private String  sender;

    @TableField("weight")
    private Double  weight;

    @TableField("volume")
    private Double volume ;

    @TableField("color")
    private String  color;

    @TableField("location")
    private String location ;

    @TableField("sampleTime")
    private LocalDateTime sampleTime;

    @TableField("partnerCode")
    private String  partnerCode ;

    @TableField("isVIP")
    private boolean  isVIP ;

    @TableField("createTime")
    private LocalDateTime  createTime ;

    @NotBlank
    @TableField("sampleImage")
    private String  sampleImage ;

    @TableField("formImage")
    private String  formImage ;


    @TableField("surveyImage")
    private String  surveyImage ;

}
