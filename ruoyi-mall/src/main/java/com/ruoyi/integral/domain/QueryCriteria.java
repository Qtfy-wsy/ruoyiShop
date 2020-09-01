package com.ruoyi.integral.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 积分商城订单搜索条件
 */
@Data
@ApiModel(description = "积分商城订单搜索条件")
public class QueryCriteria {

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String code;

    /**
     * 收货人
     */
    @ApiModelProperty(value = "收货人")
    private String receiver;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    private String status;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private long customerId;

    /**
     * 查询类型  空 全部  1 近一个月  2 一个月前
     */
    @ApiModelProperty(value = "查询类型  空 全部  1 近一个月  2 一个月前")
    private String timeType;

    /**
     * 获得查询的参数
     *
     * @return 返回查询的参数
     */
    public Map<String, Object> getQueryMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("code", this.code);
        params.put("receiver", this.receiver);
        params.put("mobile", this.mobile);
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
        params.put("code", this.code);
        if ("-1".equals(this.status)) {
            this.status = "";
        }
        params.put("status", this.status);
        params.put("customerId", this.customerId);
        params.put("timeType", this.timeType);
        return params;
    }


    /**
     * 构建用户id
     *
     * @param customerId 用户id
     * @return 查询实体
     */
    public com.ruoyi.integral.domain.QueryCriteria bulidCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }

}
