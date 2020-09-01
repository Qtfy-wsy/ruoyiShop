package com.ruoyi.order.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 退款退货操作日志对象 oms_back_order_log
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Data
public class OmsBackOrderLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 退单id 对应oms_back_order表中的id
     */
    @Excel(name = "退单id 对应oms_back_order表中的id")
    private Long backOrderId;

    /**
     * 操作人类型 1 用户  2 商家
     */
    @Excel(name = "操作人类型 1 用户  2 商家 ")
    private String operationType;

    /**
     * 留言
     */
    @Excel(name = "留言")
    private String message;

    /**
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
    @Excel(name = "1:退款申请 ", readConverterExp = "用=户发送退款请求")
    private String status;

    /**
     * 构造拒绝收货操作日志
     *
     * @param backOrderId 退单id
     * @param message     留言
     * @return 返回拒绝收货的操作日志
     */
    public static OmsBackOrderLog buildForRefuseToReceive(long backOrderId, String message) {
        OmsBackOrderLog backOrderLog = new OmsBackOrderLog();
        backOrderLog.backOrderId = backOrderId;
        backOrderLog.operationType = "2";
        backOrderLog.message = message;
        backOrderLog.status = "9";
        return backOrderLog;
    }

    /**
     * 构造同意确认收货的操作日志
     *
     * @param backOrderId 退单id
     * @param message     留言
     * @return 返回同意确认收货的操作日志
     */
    public static OmsBackOrderLog buildForConfirmReturn(long backOrderId, String message) {
        OmsBackOrderLog backOrderLog = new OmsBackOrderLog();
        backOrderLog.backOrderId = backOrderId;
        backOrderLog.operationType = "2";
        backOrderLog.message = message;
        backOrderLog.status = "8";
        return backOrderLog;
    }

    /**
     * 构造拒绝退货的操作日志
     *
     * @param backOrderId 退单id
     * @param message     留言
     * @return 返回拒绝退货的操作日志
     */
    public static OmsBackOrderLog buildForRefuseReturn(long backOrderId, String message) {
        OmsBackOrderLog backOrderLog = new OmsBackOrderLog();
        backOrderLog.backOrderId = backOrderId;
        backOrderLog.operationType = "2";
        backOrderLog.message = message;
        backOrderLog.status = "5";
        return backOrderLog;
    }

    /**
     * 构造用户填写物流信息的操作日志
     *
     * @param backOrderId 退单id
     * @return 返回操作日志
     */
    public static OmsBackOrderLog buildForFillTheLogistics(long backOrderId) {
        OmsBackOrderLog backOrderLog = new OmsBackOrderLog();
        backOrderLog.backOrderId = backOrderId;
        backOrderLog.operationType = "1";
        backOrderLog.status = "7";
        return backOrderLog;
    }

    /**
     * 构造同意退货的操作日志
     *
     * @param backOrderId 退单id
     * @param message     留言
     * @return 返回同意退货的操作日志
     */
    public static OmsBackOrderLog buildForAgreeToReturn(long backOrderId, String message) {
        OmsBackOrderLog backOrderLog = new OmsBackOrderLog();
        backOrderLog.backOrderId = backOrderId;
        backOrderLog.operationType = "2";
        backOrderLog.message = message;
        backOrderLog.status = "6";
        return backOrderLog;
    }

    /**
     * 构造同意退款的操作日志
     *
     * @param backOrderId 退单id
     * @param message     留言
     * @return 返回同意退单的操作日志
     */
    public static OmsBackOrderLog buildForAgreeToRefund(long backOrderId, String message) {
        OmsBackOrderLog backOrderLog = new OmsBackOrderLog();
        backOrderLog.backOrderId = backOrderId;
        backOrderLog.operationType = "2";
        backOrderLog.message = message;
        backOrderLog.status = "2";
        return backOrderLog;
    }

    /**
     * 构造拒绝退款的操作日志
     *
     * @param backOrderId 退单id
     * @param message     留言
     * @return 返回拒绝退单的操作日志
     */
    public static OmsBackOrderLog buildForRefuseToRefund(long backOrderId, String message) {
        OmsBackOrderLog backOrderLog = new OmsBackOrderLog();
        backOrderLog.backOrderId = backOrderId;
        backOrderLog.operationType = "2";
        backOrderLog.message = message;
        backOrderLog.status = "3";
        return backOrderLog;
    }

    /**
     * 构造申请退款的操作日志
     *
     * @param backOrderId 退单id
     * @param type        退单类型     1 退款 2 退货
     * @param reason      退货/退款原因
     * @return 返回退款日志对象
     */
    public static OmsBackOrderLog buildForApply(long backOrderId, String type, String reason) {
        OmsBackOrderLog backOrderLog = new OmsBackOrderLog();
        backOrderLog.backOrderId = backOrderId;
        backOrderLog.operationType = "1";
        if ("9".equals(reason)) {
            backOrderLog.operationType = "2";
        }
        // 退款
        if ("1".equals(type)) {
            backOrderLog.status = "1";
        } else {
            // 退货
            backOrderLog.status = "4";
        }
        return backOrderLog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBackOrderId() {
        return backOrderId;
    }

    public void setBackOrderId(Long backOrderId) {
        this.backOrderId = backOrderId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("backOrderId", getBackOrderId())
                .append("operationType", getOperationType())
                .append("message", getMessage())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .toString();
    }
}
