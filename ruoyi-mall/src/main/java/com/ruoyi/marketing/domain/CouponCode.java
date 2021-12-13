package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券券码实体类
 *
 * @author 伊甸园商城 on 2017/6/1.
 */
@Data
@ApiModel(description = "优惠券券码实体")
public class CouponCode {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;
    /**
     * 优惠券id
     */
    @ApiModelProperty(value = "优惠券id")
    private long couponId;
    /**
     * 优惠券的券码
     */
    @ApiModelProperty(value = "优惠券的券码")
    private String code;

    private long orderId;
    /**
     * 领取优惠券的会员id
     */
    @ApiModelProperty(value = "领取优惠券的会员id")
    private long customerId;
    /**
     * 优惠券状态  0 未领取 1已领取未使用 2 已使用 3 已失效
     */
    @ApiModelProperty(value = "优惠券状态  0 未领取 1已领取未使用 2 已使用 3 已失效")
    private String status;
    /**
     * 领取时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "领取时间")
    private LocalDateTime receiveTime;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime userTime;
    /**
     * 会员名称（用于连会员表查询）
     */
    @ApiModelProperty(value = "会员名称（用于连会员表查询）")
    private String username;

    /**
     * 类型 1满减 2直降
     */
    @ApiModelProperty(value = "类型 1满减 2直降")
    private int type;
    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;
    /**
     * 优惠券名称
     */
    @ApiModelProperty(value = "优惠券名称")
    private String name;
    /**
     * 优惠券描述
     */
    @ApiModelProperty(value = "优惠券描述")
    private String desc;
    /**
     * 开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;
    /**
     * 优惠券是否删除  0 未删除 1 删除
     */
    @ApiModelProperty(value = "优惠券是否删除  0 未删除 1 删除")
    private int delFlag;
    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;
    /**
     * 店铺状态  0 填写资料中  1 店铺审核中 2 审核通过 3 审核不通过 4 店铺关闭
     */
    @ApiModelProperty(value = "店铺状态  0 填写资料中  1 店铺审核中 2 审核通过 3 审核不通过 4 店铺关闭")
    private String storeStatus;
    /**
     * 店铺是否删除 0 未删除 1 删除
     */
    @ApiModelProperty(value = "店铺是否删除 0 未删除 1 删除")
    private String storeDelFlag;
    /**
     * 直降金额
     */
    @ApiModelProperty(value = "直降金额")
    private BigDecimal fallPrice;
    /**
     * 满多少钱(满减中使用,直降中不用该属性)
     */
    @ApiModelProperty(value = "满多少钱(满减中使用,直降中不用该属性)")
    private BigDecimal fullPrice;
    /**
     * 减多少钱
     */
    @ApiModelProperty(value = "减多少钱")
    private BigDecimal price;

    public CouponCode(long id, long couponId, String code, long customerId, String status, LocalDateTime receiveTime, String username) {
        super();
        this.id = id;
        this.couponId = couponId;
        this.code = code;
        this.customerId = customerId;
        this.status = status;
        this.receiveTime = receiveTime;
        this.username = username;
    }

    public CouponCode() {
    }

    /**
     * 获得优惠券的价格
     *
     * @return 返回优惠券的价格
     */
    public BigDecimal getLastPrice() {
        if (this.isFall()) {
            return this.fallPrice;
        } else if (this.isFullType()) {
            return this.price;
        } else {
            return new BigDecimal(0);
        }


    }

    /**
     * 获取优惠券领取信息
     *
     * @return 优惠券领取信息实体类
     */
    @JsonIgnore
    public CouponCode getCouponCodeToChangeStoreName() {
        if (this.storeId == 0) {
            this.storeName = "商城自营";
        }
        return this;
    }

    /**
     * 判断优惠券的类型是否是直降
     *
     * @return 直降返回true  否则返回false
     */
    @JsonIgnore
    public boolean isFall() {
        return 2 == this.type;
    }

    /**
     * 判断优惠券的类型是否是满减
     *
     * @return 满减返回true  否则返回false
     */
    @JsonIgnore
    public boolean isFullType() {
        return 1 == this.type;
    }
}
