package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.marketing.domain.RedEnvelopeCode;
import com.ruoyi.member.domain.UmsMemberAddress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 17/11/6.
 * 订单结算
 */
@Data
@ApiModel(description = "订单结算")
public class OrderSettlement {

    /**
     * 用户所有的收货地址
     */
    @ApiModelProperty(value = "主键id")
    private List<UmsMemberAddress> customerAllAddresses;

    /**
     * 用户的收货地址
     */
    @ApiModelProperty(value = "用户的收货地址")
    private UmsMemberAddress customerAddress;

    /**
     * 每个店铺的结算信息
     */
    @ApiModelProperty(value = "每个店铺的结算信息")
    private List<StoreSettlement> storeSettlements;

    /**
     * 订单总金额(每个单品详情页面的价格总和)
     */
    @ApiModelProperty(value = "订单总金额(每个单品详情页面的价格总和)")
    private BigDecimal orderTotalPrice;

    /**
     * 订单最后实付金额 = 订单总金额-总的优惠 +运费
     */
    @ApiModelProperty(value = "订单最后实付金额 = 订单总金额-总的优惠 +运费")
    private BigDecimal orderLastTotalPrice;

    /**
     * 订单总运费
     */
    @ApiModelProperty(value = "订单总运费")
    private BigDecimal orderTotalFright;

    /**
     * 订单总的优惠
     */
    @ApiModelProperty(value = "订单总的优惠")
    private BigDecimal orderDiscountPrice;

    /**
     * 购物车id
     */
    @ApiModelProperty(value = "购物车id")
    private Long[] ids;

    /**
     * 单品信息  立即购买的时候使用格式为skuId,num   12131231,2 格式
     */
    @ApiModelProperty(value = "单品信息  立即购买的时候使用格式为skuId,num   12131231,2 格式")
    private String skuInfo;

    /**
     * 用户可以试用的红包
     */
    @ApiModelProperty(value = "用户可以试用的红包")
    private List<RedEnvelopeCode> redEnvelopeCodes;

    /**
     * 构造订单结算信息
     *
     * @param ids              购物车id
     * @param skuInfo          单品信息  立即购买的时候使用格式为skuId,num   12131231,2 格式
     * @param customerAddress  用户收货地址
     * @param storeSettlements 店铺计算信息
     * @return 返回订单结算信息
     */
    public static OrderSettlement build(Long[] ids, String skuInfo, UmsMemberAddress customerAddress, List<StoreSettlement> storeSettlements) {
        OrderSettlement orderSettlement = new OrderSettlement();
        orderSettlement.customerAddress = customerAddress;
        orderSettlement.ids = ids;
        orderSettlement.skuInfo = skuInfo;

        if (CollectionUtils.isEmpty(storeSettlements)) {
            return orderSettlement;
        }

        orderSettlement.storeSettlements = storeSettlements;
        orderSettlement.orderTotalPrice = storeSettlements.stream().map(StoreSettlement::getOriginTotalPrice).reduce(new BigDecimal(0), BigDecimal::add);
        orderSettlement.orderTotalFright = storeSettlements.stream().map(StoreSettlement::getFreightPrice).reduce(new BigDecimal(0), BigDecimal::add);
        orderSettlement.orderDiscountPrice = storeSettlements.stream().map(StoreSettlement::getTotalDiscountPrice).reduce(new BigDecimal(0), BigDecimal::add);
        orderSettlement.orderLastTotalPrice = orderSettlement.orderTotalPrice.subtract(orderSettlement.orderDiscountPrice).add(orderSettlement.orderTotalFright);

        return orderSettlement;
    }

    /**
     * 检查是否有单品信息
     *
     * @return 有返回true  没有返回fasle
     */
    public boolean validateSkus() {
        return !CollectionUtils.isEmpty(this.storeSettlements);
    }

    /**
     * 检车用户是否有收货地址
     *
     * @return 有就返回true  没有返回false
     */
    public boolean validateCustomerAddress() {
        return Objects.nonNull(this.customerAddress);
    }

    /**
     * 添加红包信息
     *
     * @param redEnvelopeCodes 用户可以使用的红包
     * @return 返回当前对象
     */
    public OrderSettlement addRedEnvelope(List<RedEnvelopeCode> redEnvelopeCodes) {
        // 没有店铺结算信息 直接返回
        if (CollectionUtils.isEmpty(storeSettlements)) {
            return this;
        }


        // 如果结算信息有预售促销或者众筹 则直接返回(预售和众筹促销不能使用红包)
        if (hasPreSaleMarketing() || hasCrowdfunding()) {
            return this;
        }

        this.redEnvelopeCodes = getFilterdRedEnvelopeCode(redEnvelopeCodes);
        return this;
    }

    /**
     * 判断是否有预售信息
     *
     * @return 有返回true  没有返回false
     */
    private boolean hasPreSaleMarketing() {
        return !CollectionUtils.isEmpty(storeSettlements.stream().filter(StoreSettlement::hasPreSaleMarketing).collect(Collectors.toList()));
    }

    /**
     * 判断是否有众筹促销
     *
     * @return 有返回true  没有返回false
     */
    private boolean hasCrowdfunding() {
        return !CollectionUtils.isEmpty(storeSettlements.stream().filter(StoreSettlement::hasCrowdfunding).collect(Collectors.toList()));
    }

    /**
     * 获得过滤后的红包
     *
     * @param redEnvelopeCodes 红包
     * @return 返回过滤后的红包
     */
    @JsonIgnore
    private List<RedEnvelopeCode> getFilterdRedEnvelopeCode(List<RedEnvelopeCode> redEnvelopeCodes) {
        // 如果用户没有可以使用的红包 则直接返回
        if (CollectionUtils.isEmpty(redEnvelopeCodes)) {
            return Collections.emptyList();
        }

        // 如果结算的信息中有平台的商品结算信息 则直接返回所有可用的红包
        if (hasAdminSettlement()) {
            return redEnvelopeCodes;
        }

        // 所有的店铺id
        List<Long> storeIds = getStoreIds();

        // 如果没有平台的商品结算信息 则根据结算信息中的店铺匹配用户可用的红包
        return redEnvelopeCodes.stream().filter(redEnvelopeCode -> !CollectionUtils.isEmpty(org.apache.commons.collections4.CollectionUtils.intersection(storeIds, redEnvelopeCode.getRedEnvelopeStoreId()))).collect(Collectors.toList());
    }

    /**
     * 获得结算信息中所有的店铺id(不包括平台的)
     *
     * @return 返回店铺id
     */
    @JsonIgnore
    private List<Long> getStoreIds() {
        return this.storeSettlements.stream().filter(storeSettlement -> storeSettlement.getStoreId() != 0).map(StoreSettlement::getStoreId).collect(Collectors.toList());
    }


    /**
     * 判断结算信息中是否有平台的结算信息
     *
     * @return 有平台的结算信息返回true  没有返回false
     */
    @JsonIgnore
    private boolean hasAdminSettlement() {
        return !CollectionUtils.isEmpty(this.storeSettlements.stream().filter(storeSettlement -> storeSettlement.getStoreId() == 0).collect(Collectors.toList()));
    }

    /**
     * 添加用户所有的收货地址
     *
     * @param customerAddresses 收货地址
     * @return 返回当前对象
     */
    public OrderSettlement addCustomerAllAddress(List<UmsMemberAddress> customerAddresses) {

        if (CollectionUtils.isEmpty(customerAddresses)) {
            return this;
        }

        // 没有默认收货地址 则直接返回
        if (Objects.isNull(this.customerAddress)) {
            this.customerAllAddresses = customerAddresses;
            this.customerAddress = customerAddresses.get(0);
            return this;
        }

        // 将收货地址排到第一位
        this.customerAllAddresses = customerAddresses.stream().filter(customerAddress1 -> !customerAddress1.getId().equals(this.customerAddress.getId())).collect(Collectors.toList());

        this.customerAllAddresses.add(0, customerAddresses.stream().filter(customerAddress1 -> customerAddress1.getId().equals(this.customerAddress.getId())).collect(Collectors.toList()).get(0));
        return this;
    }

    /**
     * 存在数量小于最小批发数量的单品
     */
    public boolean existNumLowerThanBatchSkuLimitNumSku() {
        return storeSettlements.stream().anyMatch(StoreSettlement::existNumLowerThanBatchSkuLimitNumSku);
    }
}
