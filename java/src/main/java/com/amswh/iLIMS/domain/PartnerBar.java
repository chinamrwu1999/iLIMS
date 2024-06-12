package com.amswh.iLIMS.domain;

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
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "barCode 不能为空")
    @TableField("barCode")
    private String barCode;
    @TableField("productCode")
    private String productCode;
    @TableField("partnerId")
    private String partnerId;
    @TableField("createTime")
    private LocalDateTime createTime;

}
