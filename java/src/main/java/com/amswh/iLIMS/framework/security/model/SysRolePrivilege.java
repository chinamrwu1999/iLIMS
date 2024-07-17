package com.amswh.iLIMS.framework.security.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("SysRolePrivilege")
public class SysRolePrivilege {

    @TableId(value = "privilegeId",type= IdType.AUTO)
    private Long privilegeId;


    @TableField("roleId")
    private  Long roleId;

    @TableField("componentId")
    private  Long componentId;

    @TableField("thruDate")
    private LocalDateTime thruDate;

    @TableField("createTime")
    private LocalDateTime createTime;


}
