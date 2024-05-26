package com.amswh.iLIMS.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("BarExpress")
public class BarExpress {
    @TableId(value="id",type = IdType.AUTO)
    private  Integer  id;

    @TableField("barCode")
    private String barCode;

    @TableField("udi")
    private String udi;

    @TableField("productCode")
    private String productCode;

    @TableField("expressNo")
    private String expressNo;

    @TableField("partnerId")
    private String partnerId;

    @TableField("handleWay")
    private String handleWay;//处理方式：自动分拣还是公手工分拣

    @TableField("createTime")
    private LocalDateTime createTime;

}
