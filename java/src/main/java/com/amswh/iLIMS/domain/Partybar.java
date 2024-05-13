package com.amswh.iLIMS.domain;


/*
*	generated by wuzhicheng@fudan.edu.cn at 2024-05-13 16:16:34 
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
public class Partybar{

	@TableId(value="id",type = IdType.AUTO)
	@TableField("id")
	private Integer id;

	@NotBlank(message = "barCode 不能为空")
	@TableField("barCode")
	private String barCode;

	@TableField("partyId")
	private Integer partyId;

	@TableField("bindWay")
	private String bindWay;

	@TableField("partnerId")
	private Integer partnerId;

	@TableField("createTime")
	private LocalDateTime createTime;

}