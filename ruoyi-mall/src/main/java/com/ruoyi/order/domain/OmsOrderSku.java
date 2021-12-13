package com.ruoyi.order.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.util.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 订单单品对象 oms_order_sku
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Data
public class OmsOrderSku extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单id  对应oms_order表中的order_code
     */
    @Excel(name = "订单id  对应oms_order表中的order_code")
    private Long orderId;

    private long spuId ;
    /**
     * 单品id 对应pms_sku 表中的id
     */
    @Excel(name = "单品id 对应pms_sku 表中的id")
    private String skuId;

    /**
     * 购买的数量
     */
    @Excel(name = "购买的数量")
    private int num;

    /**
     * 单品的最终价格（原价－优惠价）
     */
    @Excel(name = "单品的最终价格", readConverterExp = "原=价－优惠价")
    private BigDecimal price;

    /**
     * 单品的价格(详情页的价格)
     */
    @Excel(name = "单品的价格(详情页的价格)")
    private BigDecimal skuPrice;

    /**
     * 单品的名称
     */
    @Excel(name = "单品的名称")
    private String skuName;

    /**
     * 单品的编号
     */
    @Excel(name = "单品的编号")
    private String skuNo;

    /**
     * 单品的图片
     */
    @Excel(name = "单品的图片")
    private String skuImage;

    /**
     * 单品的规格
     */
    @Excel(name = "单品的规格")
    private String skuSpecs;

    /**
     * 使用各个优惠减去的价格（这个字段为了方便扩展使用的是json格式）
     */
    @Excel(name = "使用各个优惠减去的价格", readConverterExp = "这=个字段为了方便扩展使用的是json格式")
    private String priceDetail;

    /**
     * 单品的分拥比例 0 表示不分佣金
     */
    @Excel(name = "单品的分拥比例 0 表示不分佣金")
    private BigDecimal commissionRate;

    /**
     * 单品的分拥比例 0 表示不分佣金
     */
    @Excel(name = "单品的分拥比例 0 表示不分佣金")
    private BigDecimal sCommissionRate;

    /**
     * 分类扣率
     */
    @Excel(name = "分类扣率")
    private BigDecimal cateRate;

    /**
     * 单品的原价格（社区团购的时候使用）
     */
    @Excel(name = "单品的原价格", readConverterExp = "社=区团购的时候使用")
    private BigDecimal oldPrice;

    /**
     * 供货价格（社区团购使用）
     */
    @Excel(name = "供货价格", readConverterExp = "社=区团购使用")
    private BigDecimal supplyPrice;
    /**
     * 抢购促销的id
     */
    @JsonIgnore
    @ApiModelProperty(value = "抢购促销的id")
    private long panicMarketingId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private long customerId;

    /**
     * 抢购的限制数量
     */
    @JsonIgnore
    @ApiModelProperty(value = "抢购的限制数量")
    private int panicLimitNum;
    /**
     * 单品在每个优惠下减去的金额
     */
    @ApiModelProperty(value = "单品在每个优惠下减去的金额")
    private List<SkuPriceDetail> skuPriceDetails;
    /**
     * 单品可以退货的数量
     */
    @ApiModelProperty(value = "单品可以退货的数量")
    private int returnNum;
    /**
     * 商品试用申请id
     */
    @ApiModelProperty(value = "商品试用申请id")
    private long applyTryId = CommonConstant.NO_SKU_TRY_APPLY;

    /**
     * 判断是否有抢购信息
     *
     * @return 抢购id不为-2  则说明有
     */
    @JsonIgnore
    public boolean isHasPanic() {
        return this.panicMarketingId != CommonConstant.NO_PANIC_MARKETING;
    }

    /**
     * 设置每个单品的优惠价格
     */
    public void setSkuPriceDetail() {
        if (!CollectionUtils.isEmpty(this.skuPriceDetails)) {
            this.priceDetail = JSON.toJSONString(this.skuPriceDetails);
        }
    }

    /**
     * 返回单个单品的价格 前端使用不能删除
     *
     * @return 返回单个单品的价格
     */
    public BigDecimal getUnitPrice() {
        if (this.num == 0) {
            return new BigDecimal(0);
        }

        return this.price.divide(new BigDecimal(this.num), 2, RoundingMode.HALF_EVEN);
    }

    /**
     * 增加单品每个促销下的金额实体
     *
     * @param skuPriceDetail 单品使用各种促销后的详情
     */
    public void addSkuPriceDetail(SkuPriceDetail skuPriceDetail) {

        if (Objects.isNull(skuPriceDetail)) {
            return;
        }

        if (CollectionUtils.isEmpty(skuPriceDetails)) {
            skuPriceDetails = new ArrayList<>();
        }
        skuPriceDetails.add(skuPriceDetail);
    }

    /**
     * 将价格json详情转化成list集合
     */
    public void convertJsonPriceDetailToList() {
        if (StringUtils.isEmpty(this.priceDetail)) {
            this.skuPriceDetails = new ArrayList<>();
        } else {
            this.skuPriceDetails = JSONObject.parseArray(this.priceDetail, SkuPriceDetail.class);
        }
    }

    /**
     * 是否设置了佣金比例
     *
     * @return 设置返回true  没有设置返回false
     */
    @JsonIgnore
    public boolean hasSetCommissionRate() {
        return Objects.nonNull(this.commissionRate) && this.commissionRate.floatValue() != 0;
    }

    /**
     * 是否设置了二级佣金比例
     *
     * @return 设置返回true  没有设置返回false
     */
    @JsonIgnore
    public boolean hasSetSCommissionRate() {
        return Objects.nonNull(this.sCommissionRate) && this.sCommissionRate.floatValue() != 0;
    }

    /**
     * 获得佣金
     *
     * @return 返回单品的佣金
     */
    @JsonIgnore
    public BigDecimal getCommission() {
        // 如果单品没有设置佣金比例 则直接返回0
        if (!hasSetCommissionRate()) {
            return new BigDecimal(0);
        }

        return this.price.multiply(this.commissionRate).setScale(2, RoundingMode.HALF_EVEN);
    }


    /**
     * 获得二级佣金
     *
     * @return 返回单品的二级佣金
     */
    @JsonIgnore
    public BigDecimal getSCommission() {
        // 如果单品没有设置二级佣金比例 则直接返回0
        if (!hasSetSCommissionRate()) {
            return new BigDecimal(0);
        }

        return this.price.multiply(this.sCommissionRate).setScale(2, RoundingMode.HALF_EVEN);

    }

}
