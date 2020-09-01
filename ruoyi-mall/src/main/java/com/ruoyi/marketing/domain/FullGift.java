package com.ruoyi.marketing.domain;

import com.alibaba.fastjson.JSON;
import com.ruoyi.goods.domain.PmsSku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 魔金商城 on 18/1/2.
 * 满赠实体
 */
@Data
@ApiModel(description = "满赠实体")
public class FullGift {

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
     * 赠品的详情
     * 赠送的单品信息格式为
     * [{skuId:’1’,num:1},{skuId:’2’,num:2}]  json数组
     */
    @ApiModelProperty(value = "赠品的详情")
    private String skuInfos;

    /**
     * 促销的赠品信息
     */
    @ApiModelProperty(value = "促销的赠品信息")
    private List<GiftSkuInfo> giftSkuInfos;

    /**
     * 将赠品的json信息转化成对象信息
     */
    public void convertJsonToObject() {
        if (StringUtils.isEmpty(this.skuInfos)) {
            return;
        }
        giftSkuInfos = JSON.parseArray(this.skuInfos, GiftSkuInfo.class);
    }

    /**
     * 赠品信息
     */
    @Data
    @ApiModel(description = "赠品信息实体")
    public static class GiftSkuInfo {

        /**
         * 单品id
         */
        @ApiModelProperty(value = "单品id")
        private String skuId;

        /**
         * 赠送的数量
         */
        @ApiModelProperty(value = "赠送的数量")
        private int num;

        /**
         * 赠品的单品详情
         */
        @ApiModelProperty(value = "赠品的单品详情")
        private PmsSku sku;
    }

}
