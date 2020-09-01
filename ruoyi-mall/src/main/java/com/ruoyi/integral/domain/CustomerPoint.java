package com.ruoyi.integral.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by 魔金商城 on 17/5/25.
 * 会员积分
 */
@Data
@ApiModel(description = "会员积分实体")
public class CustomerPoint {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private long customerId;

    /**
     * 积分详情
     */
    @ApiModelProperty(value = "积分详情")
    private String detail;

    /**
     * 积分类型 1 收入 2支出
     */
    @ApiModelProperty(value = "积分类型 1 收入 2支出")
    private String type;

    /**
     * 积分纪录来源类型 1 订单使用 2 订单取消 3 操作员修改 4 签到 5 积分商城使用 6 邮箱验证 7 评论 8 注册赠送
     */
    @ApiModelProperty(value = "积分纪录来源类型 1 订单使用 2 订单取消 3 操作员修改 4 签到 5 积分商城使用 6 邮箱验证 7 评论 8 注册赠送")
    private String sourceType;

    /**
     * 积分数量 正数表示增加，负数表示减少
     */
    @ApiModelProperty(value = "积分数量 正数表示增加，负数表示减少")
    private int point;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 构造第一次验证邮箱时候的积分对象
     *
     * @param customerId 用户id
     * @param point      赠送的积分
     * @return 返回积分对象
     */
    public static com.ruoyi.integral.domain.CustomerPoint buildForVerifyEmail(long customerId, int point) {
        com.ruoyi.integral.domain.CustomerPoint customerPoint = new com.ruoyi.integral.domain.CustomerPoint();
        customerPoint.customerId = customerId;
        customerPoint.point = point;
        customerPoint.type = "1";
        customerPoint.sourceType = "6";
        customerPoint.detail = "邮箱验证赠送积分";
        return customerPoint;
    }

    /**
     * 构造评论赠送的积分对象
     *
     * @param customerId 用户id
     * @param point      赠送的积分
     * @return 返回积分对象
     */
    public static com.ruoyi.integral.domain.CustomerPoint buildForComment(long customerId, int point) {
        com.ruoyi.integral.domain.CustomerPoint customerPoint = new com.ruoyi.integral.domain.CustomerPoint();
        customerPoint.customerId = customerId;
        customerPoint.point = point;
        customerPoint.type = "1";
        customerPoint.sourceType = "7";
        customerPoint.detail = "评论赠送积分";
        return customerPoint;
    }

    /**
     * 构造注册时候的积分对象
     *
     * @param customerId 用户id
     * @param point      赠送的积分
     * @return 返回积分对象
     */
    public static com.ruoyi.integral.domain.CustomerPoint buildForRegister(long customerId, int point) {
        com.ruoyi.integral.domain.CustomerPoint customerPoint = new com.ruoyi.integral.domain.CustomerPoint();
        customerPoint.customerId = customerId;
        customerPoint.point = point;
        customerPoint.type = "1";
        customerPoint.sourceType = "8";
        customerPoint.detail = "注册赠送积分";
        return customerPoint;
    }

    /**
     * 构造下订单时候的积分对象
     *
     * @param customerId 用户id
     * @param point      使用的积分
     * @return 返回积分对象
     */
    public static com.ruoyi.integral.domain.CustomerPoint buildForOrder(long customerId, int point) {
        com.ruoyi.integral.domain.CustomerPoint customerPoint = new com.ruoyi.integral.domain.CustomerPoint();
        customerPoint.customerId = customerId;
        customerPoint.point = -point;
        customerPoint.type = "2";
        customerPoint.sourceType = "1";
        customerPoint.detail = "订单使用积分";
        return customerPoint;
    }

    /**
     * 构造取消订单时候的积分对象
     *
     * @param customerId 会员id
     * @param point      返回的积分
     * @return 返回积分对象
     */
    public static com.ruoyi.integral.domain.CustomerPoint buildForCancelOrder(long customerId, int point) {
        com.ruoyi.integral.domain.CustomerPoint customerPoint = new com.ruoyi.integral.domain.CustomerPoint();
        customerPoint.customerId = customerId;
        customerPoint.point = point;
        customerPoint.type = "1";
        customerPoint.sourceType = "2";
        customerPoint.detail = "取消订单返回积分";
        return customerPoint;
    }

    /**
     * 构造签到时候的积分对象
     *
     * @param customerId 会员id
     * @param point      赠送的积分
     * @return 返回积分对象
     */
    public static com.ruoyi.integral.domain.CustomerPoint buildForSignIn(long customerId, int point) {
        com.ruoyi.integral.domain.CustomerPoint customerPoint = new com.ruoyi.integral.domain.CustomerPoint();
        customerPoint.customerId = customerId;
        customerPoint.point = point;
        customerPoint.type = "1";
        customerPoint.sourceType = "4";
        customerPoint.detail = "签到赠送积分";
        return customerPoint;
    }

    /**
     * 构造积分商城购物时候的积分对象
     *
     * @param customerId 会员id
     * @param point      消费的积分
     * @return 返回积分对象
     */
    public static com.ruoyi.integral.domain.CustomerPoint buildForPointMallOrder(long customerId, int point) {
        com.ruoyi.integral.domain.CustomerPoint customerPoint = new com.ruoyi.integral.domain.CustomerPoint();
        customerPoint.customerId = customerId;
        customerPoint.point = -point;
        customerPoint.type = "2";
        customerPoint.sourceType = "5";
        customerPoint.detail = "积分商城使用";
        return customerPoint;
    }

    /**
     * 设置管理员新增积分时候的默认值
     *
     * @return 返回积分信息
     */
    public com.ruoyi.integral.domain.CustomerPoint setValuesForManagerAdd() {
        this.sourceType = "3";
        if (this.point >= 0) {
            this.type = "1";
        } else {
            this.type = "2";
        }
        return this;
    }

    /**
     * 检查更改积分
     *
     * @param point 当前总积分
     * @return true 可以更改 false 更改后总金额小于0，不可以更改
     */
    public boolean checkChangePoint(int point) {
        if (this.point >= 0) {
            return true;
        }
        if (Math.abs(this.point) > point) {
            return false;
        }
        return true;
    }
}
