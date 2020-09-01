package com.ruoyi.store.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.IntStream;

/**
 * 门店订单对象 t_store_order
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Data
public class TStoreOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    private String orderCode;

    /**
     * 主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单
     */
    @Excel(name = "主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单")
    private String masterOrderCode;

    /**
     * 会员id
     */
    @Excel(name = "会员id")
    private Long customerId;

    /**
     * 订单的原始价格
     */
    @Excel(name = "订单的原始价格")
    private BigDecimal originalPrice;

    /**
     * 订单的最终价格(订单的最终成交价格)（原始价格-红包的价格）
     */
    @Excel(name = "订单的最终价格(订单的最终成交价格)", readConverterExp = "原=始价格-红包的价格")
    private BigDecimal price;

    /**
     * 订单状态 1 待付款（刚下单） 2 待取货（已经付款） 3 交易完成  4 取消订单
     */
    @Excel(name = "订单状态 1 待付款", readConverterExp = "刚=下单")
    private String status;

    /**
     * 店铺id
     */
    @Excel(name = "店铺id")
    private Long storeId;

    /**
     * 评价状态  0 未评价 1 已评价  默认为0 未评价
     */
    @Excel(name = "评价状态  0 未评价 1 已评价  默认为0 未评价")
    private String evaluationStatus;

    /**
     * 使用红包减去的金额
     */
    @Excel(name = "使用红包减去的金额")
    private BigDecimal redEnvelopePrice;

    /**
     * 红包的卷码
     */
    @Excel(name = "红包的卷码")
    private String redEnvelopeCode;

    /**
     * 核销码
     */
    @Excel(name = "核销码")
    private String writeOffCode;

    /**
     * 订单来源 1pc  2 h5   3 app 4 代客下单
     */
    @Excel(name = "订单来源 1pc  2 h5   3 app 4 代客下单")
    private String source;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /**
     * 取消时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "取消时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cancelTime;

    /**
     * 订单取消原因 1 现在不想买 2商品价格较贵 3价格波动 4商品缺货 5 重复下单 6发票信息有误 7 其他原因 8 无法备齐货物 9不是有效订单 10 买家主动要求
     */
    @Excel(name = "订单取消原因 1 现在不想买 2商品价格较贵 3价格波动 4商品缺货 5 重复下单 6发票信息有误 7 其他原因 8 无法备齐货物 9不是有效订单 10 买家主动要求")
    private String cancelReason;

    /**
     * 取货时间
     */
    @Excel(name = "取货时间")
    private String pickUpTime;

    /**
     * 实际取货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际取货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realPickUpTime;

    /**
     * 支付类型
     */
    @Excel(name = "支付类型")
    private String payType;

    /**
     * 是否是预存款支付  0否1 是 默认0
     */
    @Excel(name = "是否是预存款支付  0否1 是 默认0 ")
    private String predepositPay;

    /**
     * 购物车id
     */
    @ApiModelProperty(value = "购物车id")
    private Long[] cartIds;

    /**
     * 预约id
     */
    @ApiModelProperty(value = "预约id")
    private Long[] reservationIds;

    /**
     * 门店订单操作日志
     */
    @ApiModelProperty(value = "门店订单操作日志")
    private List<TStoreOrderOperationLog> storeOrderOperationLogs;
    /**
     * 门店订单的单品信息
     */
    @ApiModelProperty(value = "门店订单的单品信息")
    private List<TStoreOrderSku> storeOrderSkus;
    /**
     * 订单附属信息
     */
    @ApiModelProperty(value = "订单附属信息")
    private TStoreOrderAttr storeOrderAttr;

    /**
     * 返回真正的支付方式
     */
    @ApiModelProperty(value = "真正的支付方式")
    public String getRealPayType() {
        if ("4".equals(this.source)) {
            return this.payType;
        } else {
            return "在线支付";
        }
    }

    /**
     * 判断订单是否是待取货状态
     *
     * @return 待取货返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isWaitForPickUp() {
        return "2".equals(this.status);
    }

    /**
     * 判断订单是否是交易完成状态
     *
     * @return 交易完成状态返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isFinish() {
        return "3".equals(this.status);
    }

    /**
     * 设置订单id  主要是订单附属信息和订单单品下的订单id
     */
    public void setEveryOrderId() {

        // 设置订单附属信息的id
        if (Objects.nonNull(this.storeOrderAttr)) {
            this.storeOrderAttr.setOrderId(this.id);
        }

        // 设置订单单品的订单id
        if (!CollectionUtils.isEmpty(this.storeOrderSkus)) {
            this.storeOrderSkus.stream().forEach(orderSku -> orderSku.setOrderId(this.id));
        }
    }


    /**
     * 返回订单下面的单品的名称
     *
     * @return 返回订单下面的单品的名称
     */
    @JsonIgnore
    @ApiModelProperty(value = "订单下面的单品的名称")
    public String getOrderSkuNames() {
        if (CollectionUtils.isEmpty(this.storeOrderSkus)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        IntStream.range(0, this.storeOrderSkus.size()).forEach(index -> {
            sb.append(this.storeOrderSkus.get(index).getSkuName());
            if (index != this.storeOrderSkus.size() - 1) {
                sb.append(",");
            }
        });
        return sb.toString();
    }

    /**
     * 构建门店订单单品信息
     *
     * @param storeOrderSkus 门店订单单品集合
     * @return 门店订单实体
     */
    public TStoreOrder buildStoreOrderSkus(List<TStoreOrderSku> storeOrderSkus) {
        this.storeOrderSkus = storeOrderSkus;
        return this;
    }

    /**
     * 判断是否使用了红包
     *
     * @return 看是否有红包券吗  有返回true  没有返回false
     */
    public boolean isRedEnvelopeUsed() {
        return !StringUtils.isEmpty(this.redEnvelopeCode);
    }

    /**
     * 设置使用红包的情况下每个单品应该减去的平均优惠金额
     */
    public void calcRedEnvelopeEverySkuPrice() {
        // 如果没有使用红包 则直接返回
        if (!isRedEnvelopeUsed()) {
            return;
        }

        // 单品的总价格
        final BigDecimal allPrice = getAllSkuPrice();

        // 如果单品的价格为0  则直接返回
        if (allPrice.intValue() == 0) {
            return;
        }

        // 使用了红包则计算每个单品应该平均优惠的价格
        this.storeOrderSkus.stream().forEach(storeOrderSku -> {
            // 单品平均红包优惠的价格
            BigDecimal price = (storeOrderSku.getPrice().divide(allPrice, 10, RoundingMode.HALF_EVEN)).multiply(this.redEnvelopePrice).setScale(2, RoundingMode.HALF_EVEN);
            storeOrderSku.setPrice(storeOrderSku.getPrice().subtract(price));
            storeOrderSku.setRedEnvelopePrice(price);
        });

    }

    /**
     * 获得每个单品的累计金额
     *
     * @return 返回每个单品的累计金额
     */
    @JsonIgnore
    @ApiModelProperty(value = "每个单品的累计金额")
    private BigDecimal getAllSkuPrice() {
        return this.storeOrderSkus.stream().map(TStoreOrderSku::getPrice).reduce(new BigDecimal(0), BigDecimal::add);
    }

    /**
     * 设置订单状态
     */
    public void setCreateStoreOrderStatus() {
        if (!ArrayUtils.isEmpty(this.reservationIds)) {
            this.status = "3";
            return;
        }
        if (!ArrayUtils.isEmpty(this.cartIds)) {
            this.status = "1";
            return;
        }
        this.status = "1";
    }

    /**
     * 不需要付款的订单
     */
    @JsonIgnore
    public boolean isNoNeedPay() {
        return "3".equals(this.status);
    }

    /**
     * 查询参数实体
     *
     * @author SK
     * @since 2018/4/9
     */
    @Data
    public static class QueryCriteria {

        /**
         * 订单号
         */
        private String orderCode;

        /**
         * 买家账号名
         */
        private String customerName;

        /**
         * 门店名称
         */
        private String storeName;

        /**
         * 门店id
         */
        private long storeId = -1;

        /**
         * 订单状态
         */
        private String status;

        /**
         * 会员id
         */
        private long customerId = -1;

        /**
         * 订单来源
         */
        private String source;

        /**
         * 查询类型  空 全部  1 近一个月  2 一个月前
         */
        private String timeType;


        /**
         * 构建门店id
         *
         * @param storeId 门店id
         * @return 查询参数实体
         */
        public QueryCriteria buildStoreId(long storeId) {
            this.storeId = storeId;
            return this;
        }

        /**
         * 构建会员id
         *
         * @param customerId 会员id
         * @return 查询参数实体
         */
        public QueryCriteria buildCustomerId(long customerId) {
            this.customerId = customerId;
            return this;
        }

        /**
         * 获取查询参数map
         *
         * @return 查询参数map
         */
        public Map<String, Object> getQueryMap() {
            Map<String, Object> params = new HashMap<>();
            if ("6".equals(this.status)) {
                params.put("status", "3");
                params.put("evaluationStatus", "0");
            } else {
                params.put("status", status);
            }
            params.put("storeId", storeId);
            params.put("customerName", customerName);
            params.put("storeName", storeName);
            params.put("orderCode", orderCode);

            params.put("customerId", customerId);
            if (!StringUtils.isEmpty(source)) {
                params.put("source", source);
            }
            params.put("timeType", this.timeType);
            return params;
        }


    }

}
