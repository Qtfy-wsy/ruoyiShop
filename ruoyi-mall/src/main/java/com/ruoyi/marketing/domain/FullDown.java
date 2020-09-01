package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 魔金商城 on 17/6/8.
 * 满减促销实体
 */
@Data
@ApiModel(description = "满减促销实体")
public class FullDown {

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
     * 减多少钱
     */
    @ApiModelProperty(value = "减多少钱")
    private BigDecimal price;

    /**
     * 校验金额,满的金额小于等于减的金额返回true
     */
    @JsonIgnore
    public boolean checkPrice() {
        return this.fullPrice.compareTo(this.price) < 1;
    }
}
