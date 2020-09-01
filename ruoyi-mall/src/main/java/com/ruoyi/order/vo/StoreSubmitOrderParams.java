package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.member.domain.UmsMember;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by 魔金商城 on 2018/4/10.
 * 门店订单提交的参数
 */
@Data
@ApiModel(description = "门店订单提交的参数")
public class StoreSubmitOrderParams {


    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long customerId;

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
     * 订单来源  1pc  2 h5   3 app 4 代客下单
     */
    @ApiModelProperty(value = " 订单来源  1pc  2 h5   3 app 4 代客下单")
    private String source;

    /**
     * 预约id
     */
    @ApiModelProperty(value = "预约id")
    private Long[] reservationIds;

    /**
     * 红包id
     */
    @ApiModelProperty(value = "红包id")
    private long redEnvelopeId = 0;

    /**
     * 店铺信息
     */
    @ApiModelProperty(value = "店铺信息")
    private List<StoreInfo> storeInfos;

    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型")
    private String payType;

    /**
     * 订单价格（代客下单使用）
     */
    @ApiModelProperty(value = "订单价格（代客下单使用）")
    private BigDecimal price = new BigDecimal(-1);

    /**
     * 构建购物车订单提交
     *
     * @param ids        购物车id
     * @param customerId 用户id
     * @return 门店订单提交实体
     */
    public static StoreSubmitOrderParams buildForCart(Long[] ids, long customerId) {
        StoreSubmitOrderParams storeSubmitOrderParams = new StoreSubmitOrderParams();
        storeSubmitOrderParams.ids = ids;
        storeSubmitOrderParams.customerId = customerId;
        return storeSubmitOrderParams;
    }

    /**
     * 设置订单来源app
     */
    public void setFromApp() {
        this.source = "3";
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
     * 设置订单来源代客下单
     */
    public void setFromStore() {
        this.source = "4";
    }

    /**
     * 构建用户id
     *
     * @param customer 用户实体
     * @return 门店订单提交参数实体
     */
    public StoreSubmitOrderParams buildCustomerId(UmsMember customer) {
        if (Objects.nonNull(customer)) {
            this.customerId = customer.getId();
        }
        return this;
    }

    /**
     * 判断是否使用了红包
     *
     * @return 使用了返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isUseRedEnvelope() {
        return 0 != this.redEnvelopeId;
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
         * 订单备注
         */
        private String remark;

        /**
         * 取货时间
         */
        private String pickUpTime;

        /**
         * 构造店铺信息
         *
         * @param storeId    店铺id
         * @param remark     订单备注
         * @param pickUpTime 取货时间
         * @return 店铺信息
         */
        public static StoreInfo build(long storeId, String remark, String pickUpTime) {
            StoreInfo storeInfo = new StoreInfo();
            storeInfo.storeId = storeId;
            storeInfo.remark = remark;
            storeInfo.pickUpTime = pickUpTime;
            return storeInfo;
        }
    }

}
