package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by 伊甸园商城 on 17/11/7.
 * 订单提交的参数
 */
@Data
@ApiModel(description = "订单提交的参数")
public class SubmitOrderParams {


    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long customerId;

    /**
     * 收货地址id
     */
    @ApiModelProperty(value = "收货地址id")
    private Long addressId;

    /**
     * 发票信息
     */
    @ApiModelProperty(value = "发票信息")
    private Invoice invoice;

    /**
     * 购物车id
     */
    @ApiModelProperty(value = "购物车id")
    private Long[] ids;

    /**
     * 单品信息(立即购买的时候使用 格式 单品id,单品数量)
     */
    @ApiModelProperty(value = "单品信息(立即购买的时候使用 格式 单品id,单品数量)")
    private String skuInfo;

    /**
     * 是否拼团 0 否 1 是
     */
    @ApiModelProperty(value = "是否拼团 0 否 1 是")
    private int isGroup;

    /**
     * 已存在团的团id
     */
    @ApiModelProperty(value = "已存在团的团id")
    private String groupId;


    /**
     * 众筹id
     */
    @ApiModelProperty(value = "众筹id")
    private Long crowdfundingId;

    /**
     * 众筹类型 4: 全款 5:1元支付 6:无回报支持
     */
    @ApiModelProperty(value = "众筹类型 4: 全款 5:1元支付 6:无回报支持")
    private String crowdfundingType;

    /**
     * 无回报支持众筹金额
     */
    @ApiModelProperty(value = "无回报支持众筹金额")
    private BigDecimal crowdfundingMoney;


    /**
     * 使用的红包
     */
    @ApiModelProperty(value = "使用的红包")
    private String redEnvelopeCode;

    /**
     * 店铺信息
     */
    @ApiModelProperty(value = "店铺信息")
    private List<StoreInfo> storeInfos;

    /**
     * 订单来源  1pc  2 h5   3 app 4 小程序
     */
    @ApiModelProperty(value = "订单来源  1pc  2 h5   3 app 4 小程序")
    private String source;

    /**
     * 捐赠信息
     */
    @ApiModelProperty(value = "捐赠信息")
    private String donationMessage;

    /**
     * 设置订单来源app
     */
    public void setFromApp() {
        this.source = "3";
    }

    /**
     * 设置订单来源小程序
     */
    public void setFromApplets() {
        this.source = "4";
    }

    /**
     * 设置订单来源pc
     */
    public void setFromPc() {
        this.source = "1";
    }

    /**
     * 设置订单来源h5
     */
    public void setFromMobile() {
        this.source = "2";
    }


    /**
     * 判断是否使用红包
     *
     * @return 使用红包返回true  没使用返回false
     */
    @JsonIgnore
    public boolean hasUseRedEnvelope() {
        return !StringUtils.isEmpty(this.redEnvelopeCode);
    }


    /**
     * 判断是否是无回报支持
     *
     * @return 是返回true  不是返回false
     */
    @JsonIgnore
    public boolean isNoRetrun() {
        return Objects.nonNull(this.crowdfundingId) && !this.crowdfundingId.equals(0) && "6".equals(this.crowdfundingType);
    }

    /**
     * 店铺信息
     */
    @Data
    public static class StoreInfo {
        /**
         * 店铺id
         */
        private long storeId;

        /**
         * 使用的优惠券
         */
        private String couponCode;

        /**
         * 用户选择的支付方式 0 在线支付 1 货到付款
         */
        private String payType;

        /**
         * 使用的积分
         */
        private int usePoints;

        /**
         * 订单备注
         */
        private String remark;
    }

    /**
     * 发票信息
     */
    @Data
    public static class Invoice {

        /**
         * 发票类型 0 不需要发票 1增值税普票 2增值税专票
         */
        private int type;

        /**
         * 发票抬头
         */
        private String title;

        /**
         * 发票内容
         * 1 商品明细 2 商品类别
         */
        private String content;

        /**
         * 发票增值税号
         */
        private String taxId;

        /**
         * 发票抬头类型 1 企业 2 个人
         */
        private String titleType;

        /**
         * 公司名称
         */
        private String invoiceCompanyName;

        /**
         * 注册地址
         */
        private String invoiceRegisterAddress;

        /**
         * 注册电话
         */
        private String invoiceRegisterMobile;

        /**
         * 开户银行
         */
        private String invoiceOpenBank;

        /**
         * 银行账户
         */
        private String invoiceBankAccount;
    }

}
