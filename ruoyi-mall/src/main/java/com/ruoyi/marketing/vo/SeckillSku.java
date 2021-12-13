package com.ruoyi.marketing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 伊甸园商城 on 2020/5/15.
 * 抢购商品
 */
@Data
@Builder
@ApiModel(description = "抢购商品")
public class SeckillSku {

    /**
     * 单品id
     */
    @ApiModelProperty(value = "单品id")
    private String skuId;

    /**
     * 单品名称
     */
    @ApiModelProperty(value = "单品名称")
    private String name;

    /**
     * 单品副标题
     */
    @ApiModelProperty(value = "单品副标题")
    private String subTitle;


    /**
     * 单品的图片
     */
    @ApiModelProperty(value = "单品的图片")
    private String image;

    /**
     * 单品的原价
     */
    @ApiModelProperty(value = "单品的原价")
    private BigDecimal oldPrice;

    /**
     * 单品的折后价格
     */
    @ApiModelProperty(value = "单品的折后价格")
    private BigDecimal price;

    /**
     * 单品已售数量
     */
    @ApiModelProperty(value = "单品已售数量")
    private int saleNum;
}
