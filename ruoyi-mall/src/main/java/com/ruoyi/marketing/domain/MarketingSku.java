package com.ruoyi.marketing.domain;

import com.ruoyi.goods.domain.PmsSku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 魔金商城 on 17/6/8.
 * 营销关联的单品信息
 */
@Data
@ApiModel(description = "营销关联的单品信息实体")
public class MarketingSku {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 单品id
     */
    @ApiModelProperty(value = "单品id")
    private String skuId;

    /**
     * 促销id
     */
    @ApiModelProperty(value = "促销id")
    private long marketingId;

    /**
     * 单品信息
     */
    @ApiModelProperty(value = "单品信息")
    private PmsSku sku;

    /**
     * 促销价格 （在众筹的时候使用，众筹价格）
     */
    @ApiModelProperty(value = "促销价格 （在众筹的时候使用，众筹价格）")
    private BigDecimal price = new BigDecimal(0);
}
