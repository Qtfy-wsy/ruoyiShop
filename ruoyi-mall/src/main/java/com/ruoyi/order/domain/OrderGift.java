package com.ruoyi.order.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by 魔金商城 on 18/1/2.
 * 订单赠品
 */
@Data
@ApiModel(description = "订单赠品实体")
public class OrderGift {

    /**
     * 赠送单品的id
     */
    @ApiModelProperty(value = "赠送单品的id")
    private String skuId;

    /**
     * 赠送单品的图片
     */
    @ApiModelProperty(value = "赠送单品的图片")
    private String url;

    /**
     * 赠送单品的名称
     */
    @ApiModelProperty(value = "赠送单品的名称")
    private String skuName;

    /**
     * 赠送单品的规格
     */
    @ApiModelProperty(value = "赠送单品的规格")
    private String specs;

    /**
     * 赠送单品的编号
     */
    @ApiModelProperty(value = "赠送单品的编号")
    private String skuNo;

    /**
     * 赠送单品的数量
     */
    @ApiModelProperty(value = "赠送单品的数量")
    private int num;
}
