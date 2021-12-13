package com.ruoyi.store.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门店订单操作日志对象 t_store_order_operation_log
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Data
public class TStoreOrderOperationLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 门店订单id 对应t_store_order 表中的id
     */
    @Excel(name = "门店订单id 对应t_store_order 表中的id")
    private Long orderId;

    /**
     * 操作类型 1 确认付款 2 核销（提货） 3 取消订单
     */
    @Excel(name = "操作类型 1 确认付款 2 核销", readConverterExp = "提=货")
    private String type;

    /**
     * 操作人
     */
    @Excel(name = "操作人")
    private String operationName;

    /**
     * 操作说明
     */
    @ApiModelProperty(value = "操作说明")
    private String remark;

    /**
     * 构造取消门店订单的操作日志
     *
     * @param orderId       门店订单id
     * @param operationName 操作人
     * @return 返回门店订单操作日志
     */
    public static TStoreOrderOperationLog buildForCancelOrder(long orderId, String operationName) {
        TStoreOrderOperationLog storeOrderOperationLog = new TStoreOrderOperationLog();
        storeOrderOperationLog.orderId = orderId;
        storeOrderOperationLog.type = "3";
        storeOrderOperationLog.operationName = operationName;

        return storeOrderOperationLog;
    }

    /**
     * 构造核销的门店订单操作日志
     *
     * @param orderId       门店订单id
     * @param operationName 操作人
     * @return 返回门店订单操作日志
     */
    public static TStoreOrderOperationLog buildForPickUpOrder(long orderId, String operationName) {
        TStoreOrderOperationLog storeOrderOperationLog = new TStoreOrderOperationLog();
        storeOrderOperationLog.orderId = orderId;
        storeOrderOperationLog.type = "2";
        storeOrderOperationLog.operationName = operationName;

        return storeOrderOperationLog;
    }


    /**
     * 构造确认付款的门店订单操作日志
     *
     * @param orderId       门店订单id
     * @param remark        备注
     * @param operationName 操作人
     * @return 返回门店订单操作日志
     */
    public static TStoreOrderOperationLog buildForConfirmOrderPayed(long orderId, String remark, String operationName) {
        TStoreOrderOperationLog storeOrderOperationLog = new TStoreOrderOperationLog();
        storeOrderOperationLog.orderId = orderId;
        storeOrderOperationLog.type = "1";
        storeOrderOperationLog.remark = remark;
        storeOrderOperationLog.operationName = operationName;

        return storeOrderOperationLog;
    }
}
