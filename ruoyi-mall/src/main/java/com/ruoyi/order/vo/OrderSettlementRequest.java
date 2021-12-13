package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by 伊甸园商城 on 18/1/26.
 * 订单结算 请求实体
 */
@Data
@ApiModel(description = "订单结算 请求实体")
public class OrderSettlementRequest {

    /**
     * 购物车id
     */
    @ApiModelProperty(value = "购物车id")
    private Long[] ids;

    /**
     * 单品信息 (立即购买的时候使用  单品id,单品数量)
     */
    @ApiModelProperty(value = "单品信息 (立即购买的时候使用  单品id,单品数量)")
    private String skuInfo;

    /**
     * 是否拼团 0 否 1 是
     */
    @ApiModelProperty(value = "是否拼团 0 否 1 是")
    private int isGroup;

    /**
     * 已存在团的团id
     */
    @ApiModelProperty(value = "已存在团的团id")
    private String groupId;

    /**
     * 收货地址id
     */
    @ApiModelProperty(value = "收货地址id")
    private Long addressId;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private Long customerId;

    /**
     * 众筹id
     */
    @ApiModelProperty(value = "众筹id")
    private Long crowdfundingId;

    /**
     * 众筹类型 4:全款 5:1元支付 6:无回报支持
     */
    @ApiModelProperty(value = "众筹类型 4:全款 5:1元支付 6:无回报支持")
    private String crowdfundingType;

    /**
     * 众筹
     */
    @ApiModelProperty(value = "众筹")
    private BigDecimal crowdfundingMoney;

    /**
     * 构造结算请求信息
     *
     * @param ids               购物车id
     * @param skuInfo           单品信息 (立即购买的时候使用  单品id,单品数量)
     * @param isGroup           是否拼团 0 否 1 是
     * @param groupId           已存在团的团id
     * @param addressId         收货地址id
     * @param crowdfundingType  众筹类型
     * @param crowdfundingId    众筹id
     * @param crowdfundingMoney 无回报支持众筹金额
     * @return 返回结算请求信息
     */
    public static OrderSettlementRequest build(Long[] ids, String skuInfo, int isGroup, String groupId, Long addressId, Long crowdfundingId, String crowdfundingType, BigDecimal crowdfundingMoney) {
        OrderSettlementRequest orderSettlementRequest = new OrderSettlementRequest();
        orderSettlementRequest.ids = ids;
        orderSettlementRequest.skuInfo = skuInfo;
        orderSettlementRequest.isGroup = isGroup;
        orderSettlementRequest.groupId = groupId;
        orderSettlementRequest.addressId = addressId;
        orderSettlementRequest.crowdfundingId = crowdfundingId;
        orderSettlementRequest.crowdfundingType = crowdfundingType;
        orderSettlementRequest.crowdfundingMoney = crowdfundingMoney;
        return orderSettlementRequest;
    }

    /**
     * 判断是否是众筹
     *
     * @return 是返回true  否则返回false
     */
    @JsonIgnore
    public boolean isCrowdfunding() {
        return Objects.nonNull(crowdfundingId) && !crowdfundingId.equals(0);
    }

    /**
     * 添加会员id
     *
     * @param customerId 会员id
     * @return 返回当前对象
     */
    public OrderSettlementRequest addCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }
}
