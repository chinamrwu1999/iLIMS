package com.amswh.MYLIMS.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Project")
public class Project {


    @TableId
    @TableField("code")
    private String  code ; //项目代码


    @TableField("name")
    private String  name ; //项目名称

    @TableField("type")
    private String  spec ;  // 项目类型


}
