package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 伊甸园商城 on 17/6/5.
 * 订单搜索条件
 */
@Data
@ApiModel(description = "订单搜索条件")
public class QueryOrderCriteria {

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderCode;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String customerName;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private long customerId;

    /**
     * 支付类型  1在线支付  2货到付款
     */
    @ApiModelProperty(value = "支付类型  1在线支付  2货到付款")
    private String payType;

    /**
     * 订单状态
     * 空:全部
     * 1:待付款  （用户刚下单）
     * 2:代发货  （用户付完款 等待商城发货）
     * 3:代收货  （商城已经发货 等待用户确认收货）
     * 4:已完成  （用户已经确认收货 订单结束）
     * 5:已关闭 （用户未付款前取消订单,退款成功,退货成功）
     * 6:待评价
     * 7:已评价
     */
    @ApiModelProperty(value = "订单状态 1:待付款  （用户刚下单）,2:代发货  （用户付完款 等待商城发货）,3:代收货  （商城已经发货 等待用户确认收货）, 4:已完成  （用户已经确认收货 订单结束）, 5:已关闭 （用户未付款前取消订单,退款成功,退货成功）,6:待评价,7:已评价")
    private String status;
    private int evaluationStatus;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private String endTime;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 查询类型  空 全部  1 近一个月  2 一个月前
     */
    @ApiModelProperty(value = "查询类型  空 全部  1 近一个月  2 一个月前")
    private String timeType;

    /**
     * 订单类型 0 普通订单 1 定金预售订单 2全款预售订单
     */
    @ApiModelProperty(value = " 订单类型 0 普通订单 1 定金预售订单 2全款预售订单")
    private String orderType;

    /**
     * 促销id(现阶段用于查找众筹订单)
     */
    @ApiModelProperty(value = "促销id(现阶段用于查找众筹订单)")
    private long marketingId;

    /**
     * 社区团购团长手机号
     */
    @ApiModelProperty(value = "社区团购团长手机号")
    private String communityBuyHeadMobile;

    /**
     * 社区团购名称
     */
    @ApiModelProperty(value = "社区团购名称")
    private String communityBuyName;

    /**
     * 社区团购小区名称
     */
    @ApiModelProperty(value = "社区团购小区名称")
    private String communityName;

    /**
     * 获得查询的参数
     *
     * @return 返回查询的参数
     */
    public Map<String, Object> getQueryMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", this.orderCode);
        params.put("customerId", this.customerId);
        params.put("customerName", this.customerName);
        params.put("payType", this.payType);
        params.put("status", this.status);
        params.put("startTime", this.startTime);
        params.put("endTime", this.endTime);
        params.put("storeId", this.storeId);
        params.put("storeName", this.storeName);
        params.put("orderType", this.orderType);
        params.put("marketingId", marketingId);
        return params;
    }

    /**
     * 获得查询社区团购的参数
     *
     * @return 返回查询社区团购的参数
     */
    public Map<String, Object> getQueryMapForCommunity() {
        Map<String, Object> params = new HashMap<>();
        if ("-1".equals(this.status)) {
            this.status = "";
        }
        params.put("status", this.status);
        params.put("customerId", this.customerId);
        return params;
    }

    /**
     * 获得查询的参数(前端查询订单)
     *
     * @return 返回查询的参数
     */
    public Map<String, Object> getQueryMapForSite() {
        Map<String, Object> params = new HashMap<>();
        if ("-1".equals(this.status)) {
            this.status = "";
        }
        params.put("status", this.status);
        params.put("customerId", this.customerId);
        params.put("orderCode", this.orderCode);
        params.put("timeType", this.timeType);
        return params;
    }

    /**
     * 获得查询社区团购的参数（后端）
     *
     * @return 返回查询社区团购的参数
     */
    public Map<String, Object> getQueryMapForCommunityAdmin() {
        Map<String, Object> params = new HashMap<>();
        params.put("communityBuyHeadMobile", this.communityBuyHeadMobile);
        params.put("communityBuyName", this.communityBuyName);
        params.put("communityName", this.communityName);
        params.put("startTime", this.startTime);
        params.put("endTime", this.endTime);
        return params;
    }

    /**
     * 判断是否是待评价订单
     */
    @JsonIgnore
    public boolean isToEvalutionOrderStatus() {
        return "6".equals(this.status);
    }


}
