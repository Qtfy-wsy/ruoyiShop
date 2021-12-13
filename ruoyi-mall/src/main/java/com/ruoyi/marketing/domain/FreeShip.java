package com.ruoyi.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 伊甸园商城 on 17/6/9.
 * 包邮促销实体
 */
@Data
@ApiModel(description = "包邮促销实体")
public class FreeShip {

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
     * 包邮满的价格
     */
    @ApiModelProperty(value = "包邮满的价格")
    private BigDecimal fullPrice;

    /**
     * 包邮促销是否启用 0 不启用  1 启用 默认0
     */
    @ApiModelProperty(value = "包邮促销是否启用 0 不启用  1 启用 默认0")
    private String isUse;
}
