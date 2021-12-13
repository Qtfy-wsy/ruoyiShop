package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuBatch;
import com.ruoyi.marketing.domain.Marketing;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by 伊甸园商城 on 17/11/23.
 * 单品促销价格详情实体(主要是会员价,抢购,团购)
 */
@Data
@ApiModel(description = "单品促销价格详情实体(主要是会员价,抢购,团购)")
public class SkuMarketPriceDetail {

    /**
     * 单品最终的价格
     */
    @ApiModelProperty(value = "单品最终的价格")
    private BigDecimal price;

    /**
     * 是否有会员价格
     */
    @ApiModelProperty(value = "是否有会员价格")
    private boolean isMemberPrice;

    /**
     * 匹配的促销信息
     */
    @ApiModelProperty(value = "匹配的促销信息")
    private Marketing marketing;

    /**
     * 批发规则
     */
    @ApiModelProperty(value = "批发规则")
    private List<PmsSkuBatch> skuBatchList;

    /**
     * 构造价格详情实体
     *
     * @param price 单品的原价
     * @return 返回价格详情实体
     */
    public static SkuMarketPriceDetail build(BigDecimal price) {

        SkuMarketPriceDetail skuMarketPriceDetail = new SkuMarketPriceDetail();
        skuMarketPriceDetail.price = price;
        return skuMarketPriceDetail;
    }

    /**
     * 判断是否是抢购
     *
     * @return 是返回true  否则返回false
     */
    @JsonIgnore
    public boolean isPanic() {
        return !Objects.isNull(marketing) && marketing.isPanicBuyMarketing();
    }

    /**
     * 没有促销价格返回true
     *
     * @return 返回true
     */
    @JsonIgnore
    public boolean noMarketPrice() {
        return Objects.isNull(marketing) && !isMemberPrice;
    }

    /**
     * 设置会员价格
     *
     * @param price 会员价格
     */
    public SkuMarketPriceDetail setPriceToMemberPrice(BigDecimal price) {
        this.isMemberPrice = true;
        this.price = correctionPrice(price);
        return this;
    }

    /**
     * 设置促销后的价格
     *
     * @param marketing 促销信息
     * @return 返回当前对象
     */
    public SkuMarketPriceDetail setPriceToMarketPrice(Marketing marketing, PmsSku sku) {
        if (Objects.isNull(marketing)) {
            return this;
        }

        this.marketing = marketing;
        this.price = correctionPrice(marketing.calcMarketingPrice(price));
        //计算批发活动价格
        if (sku.isBatchSku() && !CollectionUtils.isEmpty(sku.getSkuBatchList())) {
            sku.getSkuBatchList().forEach(skuBatch -> {
                skuBatch.setOldBatchPrice(new BigDecimal(skuBatch.getBatchPrice().doubleValue()));
                skuBatch.setBatchPrice(correctionPrice(marketing.calcMarketingPrice(skuBatch.getBatchPrice())));
            });
            skuBatchList = sku.getSkuBatchList();
        }
        return this;
    }


    /**
     * 矫正价格
     *
     * @param price 原始价格
     * @return 如果原始价格 <0 则返回0元 否则返回原始价格
     */
    private BigDecimal correctionPrice(BigDecimal price) {
        // 如果价格为空 则直接返回0元
        if (Objects.isNull(price)) {
            return new BigDecimal(0);
        }

        // 判断价格是否小于0  如果小于0 则返回0 否则返回原价
        return price.compareTo(new BigDecimal(0)) < 0 ? new BigDecimal(0) : price;
    }
}
