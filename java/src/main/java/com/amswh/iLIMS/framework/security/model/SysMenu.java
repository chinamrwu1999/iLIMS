package com.amswh.iLIMS.framework.security.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("SysMenu")
public class SysMenu {
    @TableId(value = "roleId",type= IdType.AUTO)
    private  Integer menuId;
    @TableField("name")
    private  String name;
    @TableField("label")
    private String label;
    @TableField("parentId")
    private Integer parentId;

    @TableField("displayIndex")
    private Integer displayIndex;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private List<SysMenu> children;

    @TableField(exist = false)
    private String url;

}
