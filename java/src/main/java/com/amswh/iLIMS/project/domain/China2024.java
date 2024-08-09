package com.amswh.iLIMS.project.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("china2024")
public class China2024 {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("adcode")
    private  String adCode;

    @TableField("citycode")
    private  String cityCode;

    @TableField("name")
    private  String name;

    @TableField("level")
    private  String level;

    @TableField("parent")
    private  String parent;

    @TableField("longitude")
    private Float longitude;

    @TableField("latitude")
    private Float latitude;


    List<China2024> children=null;




}
