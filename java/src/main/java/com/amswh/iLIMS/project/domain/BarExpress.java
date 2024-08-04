package com.amswh.iLIMS.project.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("BarExpress")
public class BarExpress {
    @TableId(value="barId",type = IdType.INPUT)
    private String barId;
    @TableField("udi")
    private String udi;
    @TableField("expressNo")
    private String expressNo;

    @TableField("createTime")
    private LocalDateTime createTime;

    public BarExpress(){
        this.barId=null;
        this.expressNo=null;
        this.udi=null;
    }

    public BarExpress(String barId,String expressNo,String udi){
        this.barId=barId;
        this.expressNo=expressNo;
        this.udi=udi;
    }

}
