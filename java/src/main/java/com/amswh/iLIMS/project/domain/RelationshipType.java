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
@TableName("relationshiptype")
public class RelationshipType {

	@TableId(value="id",type = IdType.AUTO)
	private Integer id;

	@NotBlank(message = "name 不能为空")
	@TableField("name")
	private String name;

	@TableField("parentId")
	private Integer parentId;

	@TableField("description")
	private String description;

	@TableField("createTime")
	private LocalDateTime createTime;

}