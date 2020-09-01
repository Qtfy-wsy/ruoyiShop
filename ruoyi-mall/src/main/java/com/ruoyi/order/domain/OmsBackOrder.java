package com.ruoyi.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 退单退款对象 oms_back_order
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Data
public class OmsBackOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 退单号
     */
    @Excel(name = "退单号")
    private String backCode;

    /**
     * 订单id 对应订单表 oms_order中的id
     */
    @Excel(name = "订单id 对应订单表 oms_order中的id")
    private Long orderId;

    /**
     * 订单号 对应oms_order 表中的order_code
     */
    @Excel(name = "订单号 对应oms_order 表中的order_code ")
    private String orderCode;

    /**
     * 店铺id 平台的为0
     */
    @Excel(name = "店铺id 平台的为0 ")
    private Long storeId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private Long customerId;

    /**
     * 退货的单品ID多个用,分开  退款的时候不需要，因为退款只能整单退
     * 1001-1,1002-3 表示单品id为1001 的单品退货1件 单品id为1002的单品退货3件
     */
    @Excel(name = "退货的单品ID多个用,分开  退款的时候不需要，因为退款只能整单退 1001-1,1002-3 表示单品id为1001 的单品退货1件 单品id为1002的单品退货3件 ")
    private String skuidNums;

    /**
     * 1 退款 2 退货
     */
    @Excel(name = "1 退款 2 退货")
    private String type;

    /**
     * 退款／退货原因
     * 1:不想买了
     * 2:收货人信息有误
     * 3:未按指定时间发货
     * 4:其他
     * 5:不想买了
     * 6:商品质量问题
     * 7:收到商品与描述不符
     * 8:其他
     * 9:系统自动申请
     */
    @Excel(name = "退款／退货原因 ")
    private String reason;

    /**
     * 问题说明
     */
    @Excel(name = "问题说明")
    private String desc;

    /**
     * 申请凭据 0 没有任何凭据 1 有发票 2有质检报告
     */
    @Excel(name = "申请凭据 0 没有任何凭据 1 有发票 2有质检报告")
    private String credential;

    /**
     * 返回方式 1 快递返回 目前只有快递返回 （退货的时候用户给商城寄送商品）
     */
    @Excel(name = "返回方式 1 快递返回 目前只有快递返回 ", readConverterExp = "退=货的时候用户给商城寄送商品")
    private String backType;

    /**
     * 退款／退货金额
     */
    @Excel(name = "退款／退货金额")
    private BigDecimal backPrice;

    /**
     * 退货时候实际退款金额
     */
    @Excel(name = "退货时候实际退款金额")
    private BigDecimal realBackPrice;

    /**
     * 上传的退款凭证或者质检发票 多个图片 用, 隔开
     */
    @Excel(name = "上传的退款凭证或者质检发票 多个图片 用, 隔开")
    private String pics;

    /**
     * 退款／退货状态
     * 1:退款申请 （用户发送退款请求）
     * 2:退款成功（商家同意退款）
     * 3:退款拒绝 （商家拒绝退款）
     * 4:退货申请 （用户发起退货请求）
     * 5:退货拒绝   （商家拒绝退货）
     * 6:退货审核通过等待用户填写物流（商家审核通过，等待用户寄回商品）
     * 7: 待收货  （用户已经寄回商品，等待商家收货确认）
     * 8：退货完成（商家收货并且同意退款给用户）
     * 9:退货失败（商家不同意退款）
     */
    @Excel(name = "退款／退货状态 1:退款申请 ", readConverterExp = "用=户发送退款请求")
    private String status;

    /**
     * 是否预存款支付  0 否 1 是  默认0
     */
    @Excel(name = "是否预存款支付  0 否 1 是  默认0")
    private String predepositPay;

    /**
     * 物流公司名称
     */
    @Excel(name = "物流公司名称")
    private String logisCompanyName;

    /**
     * 物流单号
     */
    @Excel(name = "物流单号")
    private String waybillCode;

    /**
     * 退单操作日志
     */
    @ApiModelProperty(value = "退单操作日志")
    private List<OmsBackOrderLog> backOrderLogs;
    /**
     * 退款或喝退货的单品id和数量多个用,分开
     * 1001-1,1002-3 表示单品id为1001 的单品退货1件 单品id为1002的单品退货3件
     */
    @ApiModelProperty(value = "退款或喝退货的单品id和数量多个用,分开 1001-1,1002-3 表示单品id为1001 的单品退货1件 单品id为1002的单品退货3件")
    private String skuIdAndNums;
    /**
     * 订单商品
     */
    @ApiModelProperty(value = "订单商品")
    private List<OmsOrderSku> orderSkus;

    /**
     * 根据订单构造退款信息
     *
     * @param order    订单
     * @param backCode 退单号
     * @param reason   退款原因
     * @param desc     退款说明
     * @return 返回退款信息
     */
    public static OmsBackOrder buildRefund(OmsOrder order, String backCode, String reason, String desc) {
        OmsBackOrder backOrder = new OmsBackOrder();
        backOrder.backCode = backCode;
        backOrder.orderId = order.getId();
        backOrder.orderCode = order.getOrderCode();
        backOrder.storeId = order.getStoreId();
        backOrder.customerId = order.getCustomerId();
        backOrder.type = "1";
        backOrder.reason = reason;
        backOrder.desc = desc;
        backOrder.backPrice = order.getPrice();
        backOrder.status = "1";
        backOrder.predepositPay = order.getPredepositPay();
        return backOrder;
    }

    /**
     * 获得图片信息
     *
     * @return 返回图片信息
     */
    public List<String> getPicsLists() {
        if (StringUtils.isEmpty(this.pics)) {
            return Collections.emptyList();
        }
        return Arrays.asList(this.pics.split(","));
    }

    /**
     * 判断退款是否成功或者在进行中的
     *
     * @return 成功或者在进行中 返回true
     */
    @JsonIgnore
    public boolean isRefundSuccessOrProcess() {
        return "1".equals(this.status) || "2".equals(this.status);
    }

    /**
     * 判断是否在处理中
     *
     * @return 处理中返回true
     */
    public boolean isInBackProgress() {
        return "1467".contains(status);
    }

    /**
     * 判断是否是平台的 是返回true  否则返回fase
     *
     * @return 是平台返回true
     */
    @JsonIgnore
    public boolean isPlatform() {
        return this.storeId == 0;
    }

    /**
     * 是否预存款支付
     *
     * @return 是返回true
     */
    @JsonIgnore
    public boolean isPreepositPay() {
        return "1".equals(this.predepositPay);
    }

    /**
     * 判断是否可以退款
     *
     * @return 可以退款的条件  状态是3 商家拒绝退款
     */
    @JsonIgnore
    public boolean isCanRefund() {
        return "3".equals(this.status);
    }

    /**
     * 判断是否退货拒绝了
     *
     * @return 拒绝了返回true 否则返回false
     */
    @JsonIgnore
    public boolean isReturnRefuse() {
        return "5".equals(this.status) || "9".equals(this.status);
    }

    /**
     * 是否退款
     *
     * @return 退款返回true
     */
    @JsonIgnore
    public boolean isRefund() {
        return "1".equals(this.type);
    }

    /**
     * 判断是否退货成功
     *
     * @return 退货成功返回true
     */
    @JsonIgnore
    public boolean isReturnSuccess() {
        return "8".equals(this.status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackCode() {
        return backCode;
    }

    public void setBackCode(String backCode) {
        this.backCode = backCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getSkuidNums() {
        return skuidNums;
    }

    public void setSkuidNums(String skuidNums) {
        this.skuidNums = skuidNums;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }

    public BigDecimal getBackPrice() {
        return backPrice;
    }

    public void setBackPrice(BigDecimal backPrice) {
        this.backPrice = backPrice;
    }

    public BigDecimal getRealBackPrice() {
        return realBackPrice;
    }

    public void setRealBackPrice(BigDecimal realBackPrice) {
        this.realBackPrice = realBackPrice;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPredepositPay() {
        return predepositPay;
    }

    public void setPredepositPay(String predepositPay) {
        this.predepositPay = predepositPay;
    }

    public String getLogisCompanyName() {
        return logisCompanyName;
    }

    public void setLogisCompanyName(String logisCompanyName) {
        this.logisCompanyName = logisCompanyName;
    }

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("backCode", getBackCode())
                .append("orderId", getOrderId())
                .append("orderCode", getOrderCode())
                .append("storeId", getStoreId())
                .append("customerId", getCustomerId())
                .append("skuidNums", getSkuidNums())
                .append("type", getType())
                .append("reason", getReason())
                .append("desc", getDesc())
                .append("credential", getCredential())
                .append("backType", getBackType())
                .append("backPrice", getBackPrice())
                .append("realBackPrice", getRealBackPrice())
                .append("pics", getPics())
                .append("status", getStatus())
                .append("predepositPay", getPredepositPay())
                .append("logisCompanyName", getLogisCompanyName())
                .append("waybillCode", getWaybillCode())
                .append("createTime", getCreateTime())
                .toString();
    }
}
