package com.amswh.framework.system.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("SysMenu")
public class SysMenu {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("parentId")
    private Long parentId;

    @TableField("orderIndex")
    private int orderIndex;


    @TableField("menuName")
    private String menuName; //menuName


    @TableField("path")
    private String Path;

    @TableField("component")
    private String component;

    @TableField("query")
    private String query;

    @TableField("status")
    private boolean status;

    @TableField("isFrame")
    private boolean frame;

    @TableField("visible")
    private boolean visible;


    @TableField("perms")
    private String perms;

    @TableField("icon")
    private String icon;

    @TableField("menuType")
    private String menuType;

    @TableField("cached")
    private boolean cached;

    @TableField("createTime")
    private LocalDateTime createTime;

    private List<SysMenu> children = new ArrayList<SysMenu>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("menuId", id)
                .append("menuName", menuName)
                .append("parentId",parentId)
                .append("orderIndex", this.orderIndex)
                .append("path", this.Path)
                .append("component", component)
                .append("isFrame", frame)
                .append("IsCache", cached)
                .append("menuType", this.menuType)
                .append("visible", this.visible)
                .append("status ", this.status)
                .append("perms", getPerms())
                .append("icon", getIcon())
                //.append("createBy", getCreateBy())
                .append("createTime",this.createTime)
               // .append("updateBy", getUpdateBy())
               // .append("updateTime", getUpdateTime())
             //   .append("remark", getRemark())
                .toString();
    }
}
