package com.ruoyi.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 魔金商城 on 17/6/8.
 * 直降促销实体
 */
@Data
@ApiModel(description = "直降促销实体")
public class Fall {

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
     * 直降金额
     */
    @ApiModelProperty(value = "直降金额")
    private BigDecimal price;
}
