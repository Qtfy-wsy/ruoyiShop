package com.ruoyi.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 魔金商城 on 17/6/9.
 * 满折促销实体
 */
@Data
@ApiModel(description = "满折促销实体")
public class FullDiscount {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 促销id
     */
    @ApiModelProperty(value = "促销id")
    private long marketingId;

    /**
     * 满多少钱
     */
    @ApiModelProperty(value = "满多少钱")
    private BigDecimal fullPrice;

    /**
     * 打多少折
     */
    @ApiModelProperty(value = "打多少折")
    private BigDecimal discount;
}
