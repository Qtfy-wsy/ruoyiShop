package com.ruoyi.integral.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 积分商城订单实体类
 */
@Data
@ApiModel(description = "积分商城订单实体类")
public class PointMallOrder {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

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
     * 积分商品id
     */
    @ApiModelProperty(value = "积分商品id")
    private long skuId;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量")
    private int num;

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
     * 收货人手机号
     */
    @ApiModelProperty(value = "收货人手机号")
    private String mobile;

    /**
     * 收货人电话
     */
    @ApiModelProperty(value = "收货人电话")
    private String phone;

    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编")
    private String zipCode;

    /**
     * 收货地址  省市区＋详细地址
     */
    @ApiModelProperty(value = "收货地址  省市区＋详细地址")
    private String address;

    /**
     * 使用的积分数量
     */
    @ApiModelProperty(value = "使用的积分数量")
    private int point;

    /**
     * 0 待发货，1 已发货，待用户收货 2 用户已收货，订单完成
     */
    @ApiModelProperty(value = "0 待发货，1 已发货，待用户收货 2 用户已收货，订单完成")
    private String status;

    /**
     * 物流公司id
     */
    @ApiModelProperty(value = "物流公司id")
    private long logisticsId;

    /**
     * 物流公司名
     */
    @ApiModelProperty(value = "物流公司名")
    private String logisticsCompanyName;

    /**
     * 运单号
     */
    @ApiModelProperty(value = "运单号")
    private String logisticsCode;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 发货时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "发货时间")
    private LocalDateTime deliveryTime;

    /**
     * 确认收货时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "确认收货时间")
    private LocalDateTime receiptTime;

    /**
     * 积分商品信息  json格式
     */
    @ApiModelProperty(value = "积分商品信息  json格式")
    private String skuInfo;

    /**
     * 积分商品详细信息
     */
    @ApiModelProperty(value = "积分商品详细信息")
    private SkuDetail skuDetail;


    /**
     * 将积分商品信息的json信息转化成对象信息
     */
    public PointMallOrder convertJsonToObject() {
        if (StringUtils.isEmpty(this.skuInfo)) {
            return this;
        }
        skuDetail = JSON.parseObject(this.skuInfo, SkuDetail.class);
        return this;
    }

    /**
     * 构建添加积分商城订单的参数
     *
     * @param randomCode 随机数
     * @return 积分商城订单实体
     */
    public PointMallOrder buildForAdd(long randomCode) {
        this.code = "Po" + randomCode;
        this.status = "0";
        this.skuInfo = JSON.toJSONString(this.skuDetail);
        this.point = skuDetail.getPoint() * this.num;
        return this;
    }

    /**
     * 构建添加积分商城订单的用户id
     *
     * @param customerId 用户id
     * @return 积分商城订单实体
     */
    public PointMallOrder buildCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * 校验参数
     *
     * @return true参数不全
     */
    public boolean checkParams() {
        return ObjectUtils.isEmpty(this.customerId) && StringUtils.isEmpty(this.skuInfo) && StringUtils.isEmpty(this.status) && StringUtils.isEmpty(this.address) && StringUtils.isEmpty(this.receiver) && StringUtils.isEmpty(this.mobile);
    }

    /**
     * 积分商品详细信息
     */
    @Data
    public static class SkuDetail {

        /**
         * 积分商品名
         */
        private String name;

        /**
         * 图片（只取1张）
         */
        private String pic;

        /**
         * 兑换所需积分
         */
        private int point;

        /**
         * 参考价格
         */
        private BigDecimal price;

        /**
         * 积分商品编号
         */
        private String code;

        /**
         * 根据积分商品信息构建积分商品详情
         *
         * @param pointSku 积分商品实体
         * @return 积分商品详细信息实体
         */
        public static SkuDetail buildSkuDetail(PointSku pointSku) {
            SkuDetail skuDetail = new SkuDetail();
            skuDetail.setName(pointSku.getName());
            if (!StringUtils.isEmpty(pointSku.getPics())) {
                skuDetail.setPic(pointSku.getPics().split(",")[0]);
            }
            skuDetail.setPoint(pointSku.getPoint());
            skuDetail.setPrice(pointSku.getPrice());
            skuDetail.setCode(pointSku.getCode());
            return skuDetail;
        }
    }

}

