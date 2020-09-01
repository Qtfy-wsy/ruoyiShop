package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.marketing.domain.*;
import com.ruoyi.util.MarketingConstant;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 魔金商城 on 17/8/9.
 * 促销信息返回
 */
@Data
public class MarketingResponse {

    /**
     * 促销id
     */
    private long id;

    /**
     * 单品id
     */
    @JsonIgnore
    private String skuId;

    /**
     * 促销名称
     */
    private String name;

    /**
     * 促销类型  1 直降 2 满赠 3抢购 4满减  5 满折
     */
    private String marketingType;

    /**
     * 促销下的单品
     */
    private List<SkuResponse> skus = new ArrayList<>();

    /**
     * 抢购详情
     */
    private PanicBuy panicBuy;

    /**
     * 直降促销
     */
    private Fall fall;

    /**
     * 满减促销
     */
    private List<FullDown> fullDowns;

    /**
     * 满折促销
     */
    private List<FullDiscount> fullDiscounts;

    /**
     * 满赠促销
     */
    private List<FullGift> fullGifts;

    /**
     * 使用当前优惠的优惠价格
     */
    private BigDecimal discountPrice;

    /**
     * 促销描述
     */
    private String marketingDesc;

    /**
     * 满赠,满折,满减的id 如果为-1 则说明没有满足的满赠满折,满减
     */
    private long fullMarketingId = -1;

    /**
     * 构造促销返回数据
     *
     * @param marketing 促销信息
     * @return 返回促销信息
     */
    public static MarketingResponse build(Marketing marketing) {
        MarketingResponse marketingResponse = new MarketingResponse();

        if (Objects.isNull(marketing)) {
            return marketingResponse;
        }

        marketingResponse.id = marketing.getId();
        marketingResponse.name = marketing.getName();
        marketingResponse.marketingType = marketing.getType();
        marketingResponse.panicBuy = marketing.getPanicBuy();
        marketingResponse.fall = marketing.getFall();
        marketingResponse.fullDowns = marketing.getFullDowns();
        marketingResponse.fullDiscounts = marketing.getFullDiscounts();
        marketingResponse.fullGifts = marketing.getFullGifts();
        marketingResponse.marketingDesc = marketing.getMarketingDesc();
        return marketingResponse;
    }

    /**
     * 判断当前优惠是否使用
     *
     * @return 使用返回true  没使用返回false
     */
    @JsonIgnore
    public boolean isUsedMarketing() {
        return Objects.nonNull(discountPrice) && !discountPrice.equals(new BigDecimal(0));
    }

    /**
     * 获得单品的总数
     *
     * @return 返回单品的总数
     */
    @JsonIgnore
    public BigDecimal getAllSkuNum() {
        return skus.stream().map(skuResponse -> new BigDecimal(skuResponse.getNum())).reduce(new BigDecimal(0), BigDecimal::add);
    }

    /**
     * 获得单品的重量
     *
     * @return 返回单品的重量
     */
    @JsonIgnore
    public BigDecimal getAllSkuWeight() {
        return skus.stream().map(SkuResponse::getLastWeight).reduce(new BigDecimal(0), BigDecimal::add);
    }

    /**
     * 判断是否满减
     *
     * @return 满减返回true
     */
    @JsonIgnore
    public boolean isFullDown() {
        return MarketingConstant.FULL_DOWN_MARKETING.equals(this.marketingType);
    }

    /**
     * 判断是否满赠
     *
     * @return 满赠返回true
     */
    @JsonIgnore
    public boolean isFullGift() {
        return MarketingConstant.FULL_GIFT_MARKETING.equals(this.marketingType);
    }

    /**
     * 判断是否满折
     *
     * @return 满折返回true
     */
    @JsonIgnore
    public boolean isFullDiscount() {
        return MarketingConstant.FULL_DISCOUNT_MARKETING.equals(this.marketingType);
    }

    /**
     * 设置单品id
     *
     * @param skuId 单品id
     * @return 返回当前对象
     */
    public MarketingResponse setSkuId(String skuId) {
        this.skuId = skuId;
        return this;
    }

    /**
     * 获得当前促销下单品的总价格
     *
     * @return 返回当前促销下单品的总价格
     */
    @JsonIgnore
    public BigDecimal getSkusTotalPrice() {
        return this.skus.stream().map(skuResponse -> skuResponse.getTotalPrice()).reduce(new BigDecimal(0), BigDecimal::add);
    }

    /**
     * 设置满减或者满折的情况下每个单品应该减去的平均优惠金额
     */
    public void calcDiscountEverySkuPrice() {

        // 使用了满减和满折 才计算每个商品的平均优惠价格
        if (this.isUsedMarketing()) {
            // 单品平均满减瞒折优惠的价格
            this.skus.stream().forEach(skuResponse -> {
                BigDecimal price = (skuResponse.getTotalPrice().divide(this.getSkusTotalPrice(), 10, RoundingMode.HALF_EVEN)).multiply(this.discountPrice).setScale(2, RoundingMode.HALF_EVEN);
                skuResponse.setDiscountPrice(price);
            });
        }

    }

    /**
     * 获得促销的类型
     *
     * @return 返回促销的类型
     */
    public int getSkuPriceDetailType() {
        if (MarketingConstant.FULL_DOWN_MARKETING.equals(this.marketingType)) {
            return 1;
        } else if (MarketingConstant.FULL_DISCOUNT_MARKETING.equals(this.marketingType)) {
            return 2;
        } else {
            return 0;
        }
    }
}
