package com.amswh.iLIMS.project.domain;


/*
*	generated by wuzhicheng@fudan.edu.cn at 2024-05-21 14:04:23 
*/
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@TableName("person")
public class Person{

	@TableId(value="partyId")
	private String partyId;

	@NotBlank(message = "name 不能为空")
	@TableField("name")
	private String name;

	@NotBlank(message = "gender 不能为空")
	@TableField("gender")
	private String gender;

	@TableField("birthday")
	private LocalDate birthday;

	@TableField("wechat")
	private String wechat;

	@TableField("IdCardType")
	private String IdCardType;

	@TableField("IDNumber")
	private String IDNumber;

	@TableField("height")
	private Double height;

	@TableField("weight")
	private Double weight;

}