package com.ruoyi.integral.domain;

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
 * Created by 伊甸园商城 on 17/5/23.
 * 积分设置实体
 */
@Data
@ApiModel(description = "积分设置实体")
public class PointSeting {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 是否开启 0未开启 1开启 默认0未开启
     */
    @ApiModelProperty(value = "是否开启 0未开启 1开启 默认0未开启")
    private String isOpen;

    /**
     * 邮箱验证积分（只有第一次验证的时候赠送）
     */
    @ApiModelProperty(value = " 邮箱验证积分（只有第一次验证的时候赠送）")
    private int emailPoint;

    /**
     * 发表评论赠送积分
     */
    @ApiModelProperty(value = "发表评论赠送积分")
    private int commentPoint;

    /**
     * 使用多少积分
     */
    @ApiModelProperty(value = "使用多少积分")
    private int usePoint;

    /**
     * 抵多少钱
     */
    @ApiModelProperty(value = "抵多少钱")
    private int offsetMoney;

    /**
     * 修改时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;

    /**
     * 获得用户可以使用的最大积分
     *
     * @param price          订单不算运费的价格
     * @param customerPoints 用户拥有的积分
     * @return 返回用户可以使用的最大积分
     */
    @JsonIgnore
    public int getMaxPoint(BigDecimal price, int customerPoints) {

        // 如果积分没有开启 则直接返回0
        if ("0".equals(this.isOpen)) {
            return 0;
        }

        // 订单价格为0或者用户没有积分 则直接返回
        if (Objects.isNull(price) || price.equals(new BigDecimal(0)) || customerPoints == 0) {
            return 0;
        }

        // 获得订单能使用的最大积分
        int orderMaxPoint = getOrderMaxPoint(price);

        // 如果用户拥有的积分 大于等于订单能使用的积分  则直接返回订单能使用的积分
        if (customerPoints >= orderMaxPoint) {
            return orderMaxPoint;
        }

        // 计算用户能抵消使用的积分  如果积分设置是 1 10 100积分的 则直接返回用户手机的积分
        if (this.usePoint != 1000) {
            return customerPoints;
        }

        // 如果积分设置是 1000 积分的 则计算用户能返回的最大积分
        if (customerPoints >= 100) {
            return customerPoints;
        }

        // 返回用户能使用的最大积分
        return (customerPoints / 10) * 10;
    }

    /**
     * 获得订单能够使用的最大积分
     *
     * @param price 订单价格
     * @return 返回订单能够使用的最大积分
     */
    @JsonIgnore
    private int getOrderMaxPoint(BigDecimal price) {
        return price.divide(getOnePointPrice(), 0, BigDecimal.ROUND_DOWN).intValue();
    }

    /**
     * 获得一个积分的价格
     *
     * @return 返回价格积分的价格
     */
    private BigDecimal getOnePointPrice() {
        return new BigDecimal(this.offsetMoney).divide(new BigDecimal(this.usePoint), 3, BigDecimal.ROUND_DOWN);
    }

    /**
     * 获得积分的价格
     *
     * @param point 积分
     * @return 返回积分的价格
     */
    @JsonIgnore
    public BigDecimal getPointPirce(int point) {
        // 没有开启  或者没有积分直接返回0
        if ("0".equals(this.isOpen) || point == 0) {
            return new BigDecimal(0);
        }

        return getOnePointPrice().multiply(new BigDecimal(point));
    }

    /**
     * 判断是否启用
     *
     * @return 启用返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isOpen() {
        return "1".equals(this.isOpen);
    }
}
