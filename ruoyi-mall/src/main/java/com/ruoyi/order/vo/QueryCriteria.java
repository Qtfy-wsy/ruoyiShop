package com.ruoyi.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 魔金商城 on 17/6/6.
 * 退单搜索条件实体
 */
@Data
@ApiModel(description = "退单搜索条件实体")
public class QueryCriteria {
    /**
     * 退单号
     */
    @ApiModelProperty(value = "退单号")
    private String backCode;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderCode;

    /**
     * 退款／退货状态
     * 1:退款申请 （用户发送退款请求）
     * 2:退款成功（商家同意退款）
     * 3:退款拒绝 （商家拒绝退款）
     * 4:退货申请 （用户发起退货请求）
     * 5:退货拒绝   （商家拒绝退货）
     * 6:退货审核通过等待用户填写物流（商家审核通过，等待用户寄回商品）
     * 7: 待商家收货  （用户已经寄回商品，等待商家收货确认）
     * 8：退货完成（商家收货并且同意退款给用户）
     * 9:退货失败（商家不同意退款）
     * 空 查询全部
     */
    @ApiModelProperty(value = "退款／退货状态")
    private String status;

    /**
     * 店铺id  平台的为0
     */
    @ApiModelProperty(value = "店铺id  平台的为0")
    private long storeId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private long customerId;

    /**
     * 时间条件  空 全部 1 近一个月 2 一个月前
     */
    @ApiModelProperty(value = "时间条件  空 全部 1 近一个月 2 一个月前")
    private String timeType;

    /**
     * 处理状态 0 处理中 1 已处理
     */
    @ApiModelProperty(value = "处理状态 0 处理中 1 已处理")
    private String processStatus;

    /**
     * 获得查询的参数
     *
     * @return 返回查询参数
     */
    public Map<String, Object> getQueryMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("backCode", this.backCode);
        params.put("orderCode", this.orderCode);
        params.put("status", this.status);
        params.put("storeId", this.storeId);
        params.put("storeName", this.storeName);
        params.put("processStatus", this.processStatus);
        return params;
    }

    /**
     * 获得查询参数(前端)
     *
     * @return 返回查询参数
     */
    public Map<String, Object> getQueryMapForSite() {
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("backCode", backCode);
        params.put("timeType", timeType);
        return params;
    }
}
