package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.goods.domain.PmsSkuBatch;
import com.ruoyi.goods.domain.PmsSkuSpecValue;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.TrySkuApply;
import com.ruoyi.order.domain.OmsShoppingCart;
import com.ruoyi.store.domain.TStoreShoppingCart;
import com.ruoyi.util.CommonConstant;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by 魔金商城 on 17/8/9.
 * 单品返回信息(购物车)
 */
@Data
public class SkuResponse {

    /**
     * 购物车id
     */
    private long cartId;

    /**
     * 购物车中单品的数量
     */
    private int num;

    /**
     * 单品id
     */
    private String skuId;

    private long spuId;

    /**
     * 单品名称
     */
    private String name;

    /**
     * 单品的图片
     */
    private String image;

    /**
     * 单品的重量
     */
    private BigDecimal weight = new BigDecimal(0);

    /**
     * 原价格(单品最初始设置的价格)
     */
    private BigDecimal oldPrice;

    /**
     * 价格(详情页的价格)
     */
    private BigDecimal price;

    /**
     * 满减或者满折优惠的价格(算的是平摊后的价格)
     */
    private BigDecimal discountPrice;

    /**
     * 是否选中 默认否
     */
    private boolean checked = false;

    /**
     * 购物车中默认的促销id(满减或者满折)
     */
    private long marketingId;

    /**
     * 佣金比例
     */
    private BigDecimal commissionRate;

    /**
     * 是否有促销(满减或者满折)
     * true 有 false没有 默认没有
     */
    @JsonIgnore
    private boolean hasMarketing = false;

    /**
     * 单品的库存
     */
    private int stock;

    /**
     * 抢购会员限购的数量(已经减去用户已经购买该抢购的数量) 如果为-2 则说明该单品没有抢购信息 如果用户还没有使用抢购 则是抢购的原始数量
     */
    private int limitStock = CommonConstant.NO_PANIC_MARKETING;

    /**
     * 单品抢购的id 默认没有抢购信息
     */
    @JsonIgnore
    private long panicMarketId = CommonConstant.NO_PANIC_MARKETING;


    /**
     * 单品规格值
     */
    private List<PmsSkuSpecValue> skuSpecValues;

    /**
     * 详情页的促销(定金预售,全款预售,抢购,直降,拼团)
     */
    @JsonIgnore
    private Marketing marketing;

    /**
     * 试用申请
     */
    @JsonIgnore
    private TrySkuApply trySkuApply;

    /**
     * 购物车类型  1 购物车 2 门店购物车
     */
    private String type;

    /**
     * 是否是虚拟商品 0否 1 是
     */
    private String isVirtual = "0";

    /**
     * 批发规则
     */
    private List<PmsSkuBatch> skuBatchList;

    /**
     * 是否批发商品 0否 1是
     */
    private String isBatchSku;

    /**
     * 物流模版id
     */
    private long logisticsTemplateId;

    /**
     * 获得单品信息
     *
     * @param shoppingCart 购物车
     * @return 返回单品信息
     */
    public static SkuResponse build(OmsShoppingCart shoppingCart) {
        SkuResponse skuResponse = new SkuResponse();
        if (Objects.isNull(shoppingCart) || Objects.isNull(shoppingCart.getSku())) {
            return skuResponse;
        }
        skuResponse.oldPrice = shoppingCart.getSkuPrice();
        skuResponse.name = shoppingCart.getSkuName();
        skuResponse.image = shoppingCart.getSkuImage();
        skuResponse.num = shoppingCart.getNum();
        skuResponse.cartId = shoppingCart.getId();
        skuResponse.spuId = shoppingCart.getSpuId();
        skuResponse.skuId = shoppingCart.getSkuId();
        skuResponse.marketingId = shoppingCart.getMarketingId();
        skuResponse.weight = shoppingCart.getSkuWeight();
        skuResponse.skuSpecValues = shoppingCart.getSkuSpecValues();
        skuResponse.stock = shoppingCart.getSkuStock();
        skuResponse.commissionRate = shoppingCart.getSkuCommissionRate();
        skuResponse.isVirtual = shoppingCart.getIsVirtualSku();
        skuResponse.skuBatchList = shoppingCart.getSkuBatchList();
        skuResponse.isBatchSku = shoppingCart.getIsBatchSku();
        skuResponse.logisticsTemplateId = shoppingCart.getLogisticsTemplateId();
        return skuResponse;
    }

    /**
     * 获得门店单品信息
     *
     * @param storeShoppingCart 门店购物车
     * @return 返回门店单品信息
     */
    public static SkuResponse build(TStoreShoppingCart storeShoppingCart) {
        SkuResponse skuResponse = new SkuResponse();
        if (Objects.isNull(storeShoppingCart) || Objects.isNull(storeShoppingCart.getSku())) {
            return skuResponse;
        }
        skuResponse.oldPrice = storeShoppingCart.getSkuPrice();
        skuResponse.price = storeShoppingCart.getSkuPrice();
        skuResponse.name = storeShoppingCart.getSkuName();
        skuResponse.image = storeShoppingCart.getSkuImage();
        skuResponse.num = storeShoppingCart.getNum();
        skuResponse.cartId = storeShoppingCart.getId();
        skuResponse.skuId = storeShoppingCart.getSkuId();
        skuResponse.weight = storeShoppingCart.getSkuWeight();
        skuResponse.skuSpecValues = storeShoppingCart.getSkuSpecValues();
        skuResponse.stock = storeShoppingCart.getSkuStock();
        return skuResponse;
    }

    /**
     * 获得最小购买数量
     */
    public int getLimitCartNum() {
        if ("1".equals(isBatchSku) && !CollectionUtils.isEmpty(skuBatchList)) {
            return skuBatchList.get(0).getBatchNum();
        } else {
            return 1;
        }
    }

    /**
     * 判断是否有预售信息
     *
     * @return 有定金预售或者全款预售返回true
     */
    @JsonIgnore
    public boolean hasPreSale() {
        return Objects.isNull(marketing) ? false : marketing.isDepositPreSaleMarketing() || marketing.isFullPreSaleMarkting();
    }

    /**
     * 判断是否是虚拟商品
     *
     * @return 是返回true  否返回false
     */
    @JsonIgnore
    public boolean isVirtualSku() {
        return "1".equals(this.isVirtual);
    }

    /**
     * 没有促销
     *
     * @return 没有促销返回true  有返回fase
     */
    @JsonIgnore
    public boolean hasNoMarketing() {
        return !hasMarketing;
    }

    /**
     * 返回单品的总重量
     *
     * @return 返回单品的总重量
     */
    @JsonIgnore
    public BigDecimal getLastWeight() {
        return this.weight.multiply(new BigDecimal(this.num));
    }

    /**
     * 设置促销后的价格
     *
     * @param skuMarketPriceDetail 促销后的价格实体
     * @return 返回促销后的价格
     */
    public SkuResponse setMarketingPrice(SkuMarketPriceDetail skuMarketPriceDetail) {
        this.price = skuMarketPriceDetail.getPrice();
        this.marketing = skuMarketPriceDetail.getMarketing();
        return this;
    }

    /**
     * 设置团购详情
     *
     * @param marketingDetail 团购详情
     */
    public void setGroupMarketingDetail(Marketing marketingDetail) {
        // 如果不是拼团则直接返回
        if (Objects.isNull(marketingDetail) || !marketingDetail.isGroupMarketingType()) {
            return;
        }

        this.marketing = marketingDetail;
        this.price = marketingDetail.getGroupMarketing().getPrice();

        // 拼团限购 (如果用户购买的数量 大于该拼团的限购数量 则将购买的数量设置为限购的数量)
        if (marketingDetail.getGroupMarketing().getLimitNum() != 0) {
            if (this.num > marketingDetail.getGroupMarketing().getLimitNum()) {
                this.num = marketingDetail.getGroupMarketing().getLimitNum();
            }
        }

    }

    /**
     * 获得当前单品的总价格 每个价格*件数
     *
     * @return 返回单品的总价格
     */
    @JsonIgnore
    public BigDecimal getTotalPrice() {
        // 如果有试用促销的申请  并且是付邮试用 则设置一个单品的价格为0
        if (Objects.nonNull(this.trySkuApply) && this.trySkuApply.isPayPostage()) {
            // 计算n-1件单品的价格
            return this.price.multiply(new BigDecimal(this.num - 1));

        }
        return this.price.multiply(new BigDecimal(this.num));
    }

    /**
     * 获得单品单品的总价（减去满减满折后的价格）
     *
     * @return 返回单品单品的总价（减去满减满折后的价格）
     * 这个方法和 getTotalPrice 方法唯一的区别就是减去了满减或者满折的价格
     */
    public BigDecimal getTotalPriceAfterDiscount() {
        // 如果有优惠价格 则总价格减去优惠价格
        if (Objects.nonNull(this.discountPrice)) {
            return this.getTotalPrice().subtract(this.discountPrice);
        }

        return this.getTotalPrice();
    }

    /**
     * 数量小于最小批发数量
     */
    @JsonIgnore
    public boolean numLowerThanBatchSkuLimitNum() {
        return "1".equals(isBatchSku) && !CollectionUtils.isEmpty(skuBatchList) && num < skuBatchList.get(0).getBatchNum();
    }

    /**
     * 获取起订量
     */
    public int getLimitBatchNum() {
        if (CollectionUtils.isEmpty(skuBatchList)) {
            return 1;
        } else {
            return skuBatchList.get(0).getBatchNum();
        }
    }
}
