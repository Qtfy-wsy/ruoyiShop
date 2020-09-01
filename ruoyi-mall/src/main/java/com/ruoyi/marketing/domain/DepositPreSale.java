package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 定金预售实体类
 */
@Data
@ApiModel(description = "定金预售实体")
public class DepositPreSale {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 营销id
     */
    @ApiModelProperty(value = "营销id")
    private long marketingId;

    /**
     * 定金百分比
     */
    @ApiModelProperty(value = "定金百分比")
    private BigDecimal depositPre;


    /**
     * 转化定金百分比（存数据库时候用，把整数转化为百分比小数）
     */
    @JsonIgnore
    public DepositPreSale convertDepositPre() {
        this.depositPre = this.depositPre.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
        return this;
    }

}
