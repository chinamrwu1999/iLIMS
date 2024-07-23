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
import java.time.LocalDateTime;

@Data
@TableName("partybar")
public class PartyBar {

	@TableId(value="id",type = IdType.AUTO)
	private Integer id;

	@TableField("partyId")
	private String partyId;

	@TableField("productCode")
	private String productCode;

	@TableField("partnerCode")
	private String partnerCode;

	@NotBlank(message = "barCode 不能为空")
	@TableField("barCode")
	private String barCode;

	@TableField("age")
	private Integer age;

	@TableField("bindWay")
	private String bindWay;

	@TableField("createTime")
	private LocalDateTime createTime;

}