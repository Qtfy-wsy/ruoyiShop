package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 注册营销表实体类
 * <p>
 * Created by 伊甸园商城 on 2017/6/6.
 */
@Data
@ApiModel(description = "注册营销表实体")
public class RegisterMarketing {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 是否开启  0未开启 1开启 默认0
     */
    @ApiModelProperty(value = "是否开启  0未开启 1开启 默认0")
    private String isUse;

    /**
     * 送优惠券的id
     */
    @ApiModelProperty(value = "送优惠券的id")
    private long couponId;

    /**
     * 送积分的数量
     */
    @ApiModelProperty(value = "送积分的数量")
    private int pointNum;

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
     * 优惠券集合
     */
    @ApiModelProperty(value = "优惠券集合")
    private List<Coupon> couponList;

    /**
     * 判断是否开启
     *
     * @return 开启返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isUse() {
        return "1".equals(this.isUse);
    }

    /**
     * 判断当前时间是否在有效时间内
     *
     * @return 在有效时间内返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isTimeEffective() {
        return LocalDateTime.now().isBefore(endTime) && LocalDateTime.now().isAfter(startTime);
    }

    /**
     * 判断当前促销是否可用
     *
     * @return 可用返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isEffective() {
        return this.isUse() && this.isTimeEffective();
    }

    /**
     * 是否可以领取优惠券
     *
     * @return 可以领取返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isCanReceiveCoupon() {
        return this.isEffective() && this.couponId != -1;
    }
}
