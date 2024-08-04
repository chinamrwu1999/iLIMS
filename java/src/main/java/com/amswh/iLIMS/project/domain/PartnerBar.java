package com.amswh.iLIMS.project.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("partnerBar")
public class PartnerBar {
    @TableId(value="barId",type = IdType.INPUT)
    private String barId;
    @NotBlank(message = "barCode 不能为空")
    @TableField("barCode")
    private String barCode;
    @TableField("productCode")
    private String productCode;
    @TableField("partnerCode")
    private String partnerCode;
    @TableField("createTime")
    private LocalDateTime createTime;

}
