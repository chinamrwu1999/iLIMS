package com.amswh.MYLIMS.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ProjectDisease")
public class ProjectDisease {

    @TableField("projectCode")
    private String  projectCode ; //产品名称

    @TableField("diseaseCode")
    private String  diseaseCode ;  // 产品规格
}
