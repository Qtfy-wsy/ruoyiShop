package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 魔金商城 on 17/11/9.
 * 提交订单返回实体
 */
@Data
@ApiModel(description = "提交订单返回实体")
public class SubmitOrderResponse {

    /**
     * 订单提交成功
     */
    @ApiModelProperty(value = "订单提交成功")
    private String result = "0";


    /**
     * 是否需要支付(0 需要  1不需要)
     */
    @ApiModelProperty(value = "是否需要支付(0 需要  1不需要)")
    private int needPay;

    /**
     * 订单code
     */
    @ApiModelProperty(value = "订单code")
    private String orderCode;

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderMoney;

    /**
     * 构造空实体
     *
     * @return 返回空实体
     */
    public static SubmitOrderResponse buildEmpty() {
        return new SubmitOrderResponse();
    }

    /**
     * 构造订单提交失败实体
     *
     * @param result 返回码
     * @return 返回订单提交实体
     */
    public static SubmitOrderResponse buildFail(String result) {
        SubmitOrderResponse submitOrderResponse = buildEmpty();
        submitOrderResponse.result = result;
        return submitOrderResponse;
    }

    /**
     * 构造不需要支付的返回实体
     *
     * @param orderMoney 订单金额
     * @return 返回不需要支付的实体
     */
    public static SubmitOrderResponse buildNoNeedPayResponse(BigDecimal orderMoney, String orderCode) {
        SubmitOrderResponse submitOrderResponse = SubmitOrderResponse.buildEmpty();
        submitOrderResponse.needPay = 1;
        submitOrderResponse.orderMoney = orderMoney;
        submitOrderResponse.orderCode = orderCode;
        return submitOrderResponse;
    }

    /**
     * 构造需要支付的返回实体
     *
     * @param orderCode  订单号
     * @param orderMoney 支付金额
     * @return 返回实体
     */
    public static SubmitOrderResponse buildNeedPayResponse(String orderCode, BigDecimal orderMoney) {
        SubmitOrderResponse submitOrderResponse = SubmitOrderResponse.buildEmpty();
        submitOrderResponse.needPay = 0;
        submitOrderResponse.orderCode = orderCode;
        submitOrderResponse.orderMoney = orderMoney;
        return submitOrderResponse;
    }

    /**
     * 判断订单是否提交成功
     *
     * @return 成功返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return "0".equals(this.result);
    }

}
