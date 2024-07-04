package com.amswh.iLIMS.domain;


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
    private int id;

    @TableField("parentId")
    private int parentId;

    @TableField("orderIndex")
    private int orderIndex;


    @TableField("name")
    private String name; //menuName


    @TableField("url")
    private String URL;

    @TableField("status")
    private boolean status;

    @TableField("visible")
    private boolean visible;


    @TableField("perms")
    private String perms;

    @TableField("icon")
    private String icon;

    @TableField("type")
    private String type;

    @TableField("createTime")
    private LocalDateTime createTime;

    private List<SysMenu> children = new ArrayList<SysMenu>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("menuId", id)
                .append("menuName", name)
                .append("parentId",parentId)
                .append("orderIndex", this.orderIndex)
                .append("path", this.URL)
               // .append("component", )
               // .append("isFrame", getIsFrame())
               // .append("IsCache", getIsCache())
                .append("menuType", this.type)
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
