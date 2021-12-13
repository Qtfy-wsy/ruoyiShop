package com.ruoyi.marketing.domain;

import com.ruoyi.goods.domain.PmsSku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 抢购单品实体类
 *
 * @author 伊甸园商城 created on 2020/5/18
 */
@Data
@ApiModel(description = "抢购单品实体")
public class PanicBuySku {

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

    /**
     * 状态 0 即将开始 1 正在进行 默认0
     */
    private String status = "0";


    /**
     * 构建抢购单品实体
     *
     * @param panicBuy 抢购实体
     * @param sku      单品实体
     * @return 返回抢购单品实体
     */
    public static PanicBuySku buildPanicBuySku(PanicBuy panicBuy, PmsSku sku, int saleNum) {
        if (Objects.isNull(panicBuy) || Objects.isNull(sku)) {
            return null;
        }
        PanicBuySku panicBuySku = new PanicBuySku();
        panicBuySku.skuId = panicBuy.getSkuId();
        panicBuySku.name = sku.getName();
        panicBuySku.subTitle = sku.getSubtitle();
        panicBuySku.image = sku.getUrl();
        panicBuySku.oldPrice = sku.getPrice();
        panicBuySku.price = sku.getPrice().multiply(panicBuy.getDiscount());
        panicBuySku.saleNum = saleNum;
        panicBuySku.status = LocalDateTime.now().compareTo(panicBuy.getStartTime()) >= 0 ? "1" : "0";
        return panicBuySku;
    }

}
