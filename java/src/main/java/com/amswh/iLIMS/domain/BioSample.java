package com.amswh.iLIMS.domain;


/*
*	generated by wuzhicheng@fudan.edu.cn at 2024-05-21 14:04:23 
*/
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("biosample")
public class BioSample {

	@TableId(value="id",type = IdType.AUTO)
	private Integer id;

	@NotBlank(message = "barCode 不能为空")
	@TableField("barCode")
	private String barCode;

	@NotBlank(message = "type 不能为空")
	@TableField("type")
	private String type;

	@TableField("weight")
	private Double weight;

	@TableField("volume")
	private Double volume;

	@TableField("color")
	private String color;

	@TableField("location")
	private String location;

	@TableField("sampleTime")
	private LocalDateTime sampleTime;

	@TableField("sender")
	private String sender;

	@NotBlank(message = "status 不能为空")
	@TableField("status")
	private String status;

	@TableField("sampleImage")
	private String sampleImage;

	@TableField("formImage")
	private String formImage;

	@TableField("surveyImage")
	private String surveyImage;
	@TableField("createTime")
	private LocalDateTime createTime;

}