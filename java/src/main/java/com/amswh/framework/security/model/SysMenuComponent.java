package com.amswh.framework.security.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("SysMenuComponent")
public class SysMenuComponent {
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @TableField("menuId")
    private Long menuId;

    @TableId("componentId")
    private Long componentId;

    @TableField("createTime")
    private LocalDateTime createTime;

}
