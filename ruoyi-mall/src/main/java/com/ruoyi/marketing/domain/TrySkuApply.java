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
import java.util.Objects;

/**
 * 商品试用申请表
 */
@Data
@ApiModel(description = "商品试用申请实体")
public class TrySkuApply {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 试用id对应sms_marketing_try表中的id
     */
    @ApiModelProperty(value = "试用id对应sms_marketing_try表中的id")
    private long tryId;

    /**
     * 单品id
     */
    @ApiModelProperty(value = "单品id")
    private String skuId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private long customerId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String customerName;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String customerImg;

    /**
     * 状态  0 试用申请中  1 申请通过 2 申请拒绝 3下单成功 4 已完成（已提交试用报告）
     */
    @ApiModelProperty(value = "状态  0 试用申请中  1 申请通过 2 申请拒绝 3下单成功 4 已完成（已提交试用报告）")
    private String status;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private long orderId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 申请时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "申请时间")
    private LocalDateTime applyTime;

    /**
     * 单品详情实体
     */
    @ApiModelProperty(value = "单品详情实体")
    private TryMarketing.SkuDetail sku;

    /**
     * 试用促销实体
     */
    private TryMarketing tryMarketing;


    /**
     * 判断是否是付费包邮
     *
     * @return 是否返回true  否则返回false
     */
    @JsonIgnore
    public boolean isPayPostage() {
        return Objects.nonNull(this.tryMarketing) && this.tryMarketing.isPayPostage();
    }


    /**
     * 构建单品详细信息
     *
     * @return 试用申请实体
     */
    public TrySkuApply buildSku() {
        this.sku = this.tryMarketing.getSku();
        return this;
    }


    /**
     * 构建试用促销详细信息
     *
     * @param tryMarketing 试用促销实体
     * @return 试用申请实体
     */
    public TrySkuApply buildTryMarketing(TryMarketing tryMarketing) {
        this.tryMarketing = tryMarketing;
        return this;
    }

    /**
     * 构建customerId
     *
     * @param customerId 用户id
     * @return 试用申请实体
     */
    public TrySkuApply buildCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }


}
