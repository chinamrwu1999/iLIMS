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
@TableName("bar")
public class Bar{

	@TableId(value="id",type = IdType.AUTO)
	private Integer id;

	@NotBlank(message = "barCode 不能为空")
	@TableField("barCode")
	private String barCode;

	@TableField("productCode")
	private String productCode;

	@TableField("batchNo")
	private String batchNo;

	@TableField("createTime")
	private LocalDateTime createTime;

}