package com.amswh.iLIMS.project.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("expReagent")
public class ExpReagent {

    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
}
