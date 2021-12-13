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
import java.util.Objects;

/**
 * 优惠券实体类
 *
 * @author 伊甸园商城 on 2017/6/1.
 */
@Data
@ApiModel(description = "优惠券实体")
public class Coupon {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;
    /**
     * 优惠券名称
     */
    @ApiModelProperty(value = "优惠券名称")
    private String name;
    /**
     * 生成张数
     */
    @ApiModelProperty(value = "生成张数")
    private int num;
    /**
     * 每人可以领取的张数
     */
    @ApiModelProperty(value = "每人可以领取的张数")
    private int limitNum;
    /**
     * 类型 1满减 2直降
     */
    @ApiModelProperty(value = "类型 1满减 2直降")
    private int type;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String desc;
    /**
     * 店铺id   平台的优惠券为0
     */
    @ApiModelProperty(value = "店铺id   平台的优惠券为0")
    private long storeId;
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
     * 删除标记  0未删除 1删除 默认0
     */
    @ApiModelProperty(value = "删除标记  0未删除 1删除 默认0")
    private int delFlag;

    /**
     * 满减对象
     */
    @ApiModelProperty(value = "满减对象")
    private CouponFull couponFull;

    /**
     * 直降对象
     */
    @ApiModelProperty(value = "直降对象")
    private CouponFall couponFall;

    /**
     * 是否领完
     */
    @ApiModelProperty(value = "是否领完")
    private boolean runOut;

    /**
     * 剩余数量
     */
    @ApiModelProperty(value = "剩余数量")
    private int canReceiveCount;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 优惠券是否已经领完 0 未领完 1 已领完
     */
    @ApiModelProperty(value = "优惠券是否已经领完 0 未领完 1 已领完")
    private String status;

    /**
     * 获取方式：1 领取 2 发放
     */
    @ApiModelProperty(value = "获取方式：1 领取 2 发放")
    private String useType;

    /**
     * 构建剩余数量
     *
     * @return 优惠券实体
     */
    public Coupon buildCanReceiveCount(int canReceiveCount) {
        this.canReceiveCount = canReceiveCount;
        return this;
    }

    /**
     * 构建是否领完
     *
     * @return 优惠券实体
     */
    public Coupon buildIsRunOut() {
        this.runOut = this.checkRunOut();
        return this;
    }

    /**
     * 是否领完 true 已领完
     */
    @JsonIgnore
    public boolean checkRunOut() {
        return this.canReceiveCount <= 0;
    }

    /**
     * 判断开始时间是否大于结束时间
     *
     * @return 开始时间大于结束时间返回true, 小于false
     */
    public boolean toCompareTime() {
        return this.getStartTime().isAfter(this.getEndTime());
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

    /**
     * 获得优惠券的价格
     *
     * @return 返回优惠券的价格
     */
    public BigDecimal getLastPrice() {
        if (this.isFall() && Objects.nonNull(this.couponFall)) {
            return this.couponFall.getPrice();
        } else if (this.isFullType() && Objects.nonNull(this.couponFull)) {
            return this.couponFull.getPrice();
        } else {
            return new BigDecimal(0);
        }

    }
}
