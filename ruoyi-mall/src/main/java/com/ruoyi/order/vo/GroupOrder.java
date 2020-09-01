package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import com.ruoyi.order.domain.OmsOrderAttr;
import com.ruoyi.order.domain.OmsOrderSku;
import com.ruoyi.util.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 拼团订单实体
 */
@Data
@ApiModel(description = "拼团订单实体")
public class GroupOrder {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private long id;

    /**
     * 订单code
     */
    @ApiModelProperty(value = "订单code")
    private String orderCode;

    /**
     * 主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单
     */
    @ApiModelProperty(value = "主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单")
    private String masterOrderCode;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private long customerId;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String customerName;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String customerPic;

    /**
     * 订单的最终价格 (用户实际付款的金额)
     */
    @ApiModelProperty(value = "订单的最终价格 (用户实际付款的金额)")
    private BigDecimal price;


    /**
     * 1:待付款  （用户刚下单）
     * 2:代发货  （用户付完款 等待商城发货）
     * 3:代收货  （商城已经发货 等待用户确认收货）
     * 4:已完成  （用户已经确认收货 订单结束）
     * 5:取消订单 （用户未付款前取消订单）
     * 6:退款通过  （用户已经付款但是商城还未发货，用户发出退款申请，商城同意退款）
     * 7:退货通过   （用户已经确认收货后用户发出退货申请，商城同意所有退货申请 ，一个订单可能有多个单品）
     */
    @ApiModelProperty(value = "订单状态")
    private String status;

    /**
     * 订单来源 1pc  2 h5   3 app
     */
    @ApiModelProperty(value = "订单来源 1pc  2 h5   3 app")
    private String source;


    /**
     * 评价状态  0 未评价 1 已评价  默认为0 未评价
     */
    @ApiModelProperty(value = "评价状态  0 未评价 1 已评价  默认为0 未评价")
    private String evaluationStatus;

    /**
     * 订单店铺id  平台的订单id为0
     */
    @ApiModelProperty(value = "订单店铺id  平台的订单id为0")
    private long storeId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 支付时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    /**
     * 订单附属信息
     */
    @ApiModelProperty(value = "订单附属信息")
    private OmsOrderAttr orderAttr;

    /**
     * 订单单品信息
     */
    @ApiModelProperty(value = "订单单品信息")
    private List<OmsOrderSku> orderSkus;

    /**
     * 是否团长  0 是 1 否  当订单为拼团订单的时候有用
     */
    @ApiModelProperty(value = "是否团长  0 是 1 否  当订单为拼团订单的时候有用")
    private String groupHead;

    /**
     * 拼团订单的id  拼团订单的时候有用
     */
    @ApiModelProperty(value = "拼团订单的id  拼团订单的时候有用")
    private String groupId;

    /**
     * 拼团订单的促销id 拼团订单的时候有用
     */
    @ApiModelProperty(value = "拼团订单的促销id 拼团订单的时候有用")
    private long groupMarketingId;

    /**
     * 拼团的单品id （拼团订单时候有效）
     */
    @ApiModelProperty(value = "拼团的单品id （拼团订单时候有效）")
    private String groupSkuId;

    /**
     * 团员订单集合
     */
    @ApiModelProperty(value = "团员订单集合")
    private List<GroupOrder> groupMemberOrders;


    /**
     * 拼团状态 0未成团 1已成团 默认0
     */
    @ApiModelProperty(value = "拼团状态 0未成团 1已成团 默认0")
    private String groupStatus;

    /**
     * 成团人数
     */
    @ApiModelProperty(value = "成团人数")
    private int groupNum;

    /**
     * 开团时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "开团时间")
    private LocalDateTime openGroupTime;

    /**
     * 拼团订单定时任务处理状态 '0'未处理 '1'已处理 默认'0'
     */
    @ApiModelProperty(value = "拼团订单定时任务处理状态 '0'未处理 '1'已处理 默认'0'")
    private String autoHandleStatus;

    /**
     * 构建团员订单集合
     *
     * @param groupMemberOrders 团员订单集合
     * @return 拼团订单实体
     */
    public GroupOrder buildGroupMemberOrders(List<GroupOrder> groupMemberOrders) {
        this.groupMemberOrders = groupMemberOrders;
        return this;
    }

    /**
     * 判断是否已成团
     *
     * @return 已成团返回true  否则返回false
     */
    @JsonIgnore
    public boolean isGroupSuccess() {
        return "1".equals(this.groupStatus);
    }

    /**
     * 判断是否成为拼团订单
     *
     * @return 成为返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isInGroup() {
        return !"-1".equals(this.groupStatus);
    }

    /**
     * 判断是否被定时任务处理过
     *
     * @return true 已处理 false 未处理
     */
    public boolean isAutoHandled() {
        return "1".equals(this.autoHandleStatus);
    }

    /**
     * 返回订单收货人名称
     *
     * @return 返回订单收货人名称
     */
    @JsonIgnore
    public String getShippingName() {
        return Objects.nonNull(this.orderAttr) ? this.orderAttr.getReceiptName() : "";
    }

    /**
     * 返回订单收货人的手机号码
     *
     * @return 返回订单收货人的手机号码
     */
    @JsonIgnore
    public String getShippingMobile() {
        return Objects.nonNull(this.orderAttr) ? this.orderAttr.getReceiptMobile() : "";
    }

    /**
     * 查询条件实体
     */
    @Data
    @ApiModel(description = "拼团订单查询条件")
    public static class QueryCriteria {

        /**
         * 店铺id
         */
        @ApiModelProperty(value = "店铺id")
        private long storeId = CommonConstant.QUERY_WITH_NO_STORE;

        /**
         * 促销id
         */
        @ApiModelProperty(value = "促销id")
        private Long marketingId;

        /**
         * 用户id
         */
        @ApiModelProperty(value = "用户id")
        private long customerId = CommonConstant.NO_CUSTOMER_ID;

        /**
         * 单品id
         */
        @ApiModelProperty(value = "单品id")
        private String skuId;


        /**
         * 订单来源 1pc  2 h5   3 app
         */
        @ApiModelProperty(value = "订单来源 1pc  2 h5   3 app")
        private String source;

        /**
         * 店铺名称
         */
        @ApiModelProperty(value = " 店铺名称")
        private String storeName;

        /**
         * 拼团状态 -1无状态 0未成团 1已成团 默认-1
         */
        @ApiModelProperty(value = "拼团状态 -1无状态 0未成团 1已成团 默认-1")
        private String groupStatus;

        /**
         * 过滤支付时间 1过滤付款24小时之内的 -1付款24小时以上的
         */
        @ApiModelProperty(value = "过滤支付时间 1过滤付款24小时之内的 -1付款24小时以上的")
        private Long filterPayTime;


        /**
         * 用户名
         */
        @ApiModelProperty(value = "用户名")
        private String customerName;

        /**
         * 开始时间
         */
        @ApiModelProperty(value = "开始时间")
        private String startTime;

        /**
         * 拼团id
         */
        @ApiModelProperty(value = "拼团id")
        private String groupId;

        /**
         * 结束时间
         */
        @ApiModelProperty(value = "结束时间")
        private String endTime;

        /**
         * 获取查询参数
         */
        public Map<String, Object> getQueryParams() {
            Map<String, Object> params = new HashMap<>();
            params.put("marketingId", this.marketingId);
            params.put("skuId", this.skuId);
            params.put("storeName", this.storeName);
            params.put("storeId", this.storeId);
            params.put("groupStatus", this.groupStatus);
            params.put("customerId", this.customerId);
            params.put("filterPayTime", this.filterPayTime);
            params.put("customerName", this.customerName);
            params.put("startTime", this.startTime);
            params.put("endTime", this.endTime);
            params.put("groupId", this.groupId);
            return params;
        }


        /**
         * 构建查询参数,商品详情页用
         */
        public QueryCriteria buildForSpuDetail() {
            this.storeId = CommonConstant.QUERY_WITH_NO_STORE;
            //未成团
            this.groupStatus = "0";
            //过滤支付时间24小时之内
            this.filterPayTime = 1L;
            return this;
        }


    }


}
