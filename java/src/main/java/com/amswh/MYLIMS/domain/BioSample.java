package com.amswh.MYLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("BioSample")
public class BioSample {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("barCode")
    private String  barCode;

    @TableField("type")
    private String sampleType ;

    @TableField("weight")
    private Double  weight;

    @TableField("volume")
    private Double volume ;

    @TableField("sender")
    private String  sender;


    @TableField("color")
    private String  color;

    @TableField("location")
    private String location ;

    @TableField("sampleTime")
    private LocalDateTime sampleTime;

    @TableField("partnerCode")
    private String  partnerCode ;

    @TableField("isVIP")
    private Integer  isVIP ;

    @TableField("createTime")
    private LocalDateTime  createTime ;

    @TableField("sampleImage")
    private String  sampleImage ;

    @TableField("formImage")
    private String  formImage ;


    @TableField("surveyImage")
    private String  surveyImage ;

}
