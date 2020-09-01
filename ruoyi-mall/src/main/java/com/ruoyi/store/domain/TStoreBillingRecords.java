package com.ruoyi.store.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 门店账单收入支出对象 t_store_billing_records
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Data
public class TStoreBillingRecords extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单code
     */
    @Excel(name = "订单code")
    private String orderCode;

    /**
     * 账单进出类型 0 进 1 出
     */
    @Excel(name = "账单进出类型 0 进 1 出")
    private String type;

    /**
     * 店铺id
     */
    @Excel(name = "店铺id")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称")
    private String storeName;

    /**
     * 账单记录类型 1 提货完成 2 代客下单完成
     */
    @Excel(name = "账单记录类型 1 提货完成 2 代客下单完成")
    private String recordType;

    /**
     * 订单价格
     */
    @Excel(name = "订单价格")
    private BigDecimal orderPrice;

    /**
     * 结算状态 0 未结算 1 已结算 默认0
     */
    @Excel(name = "结算状态 0 未结算 1 已结算 默认0 ")
    private String status;

    /**
     * 构造门店订单提货的账单记录
     *
     * @param storeOrder 订单记录
     * @return 返回账单记录
     */
    public static TStoreBillingRecords buildForPickUp(TStoreOrder storeOrder) {
        // 如果没有订单信息则直接返回
        if (Objects.isNull(storeOrder)) {
            return null;
        }
        // 账单记录
        TStoreBillingRecords storeBillingRecord = new TStoreBillingRecords();
        storeBillingRecord.orderCode = storeOrder.getOrderCode();
        storeBillingRecord.type = "0";
        storeBillingRecord.storeId = storeOrder.getStoreId();
        storeBillingRecord.storeName = storeOrder.getStoreName();
        storeBillingRecord.recordType = "1";
        storeBillingRecord.orderPrice = storeOrder.getPrice();
        storeBillingRecord.status = "0";
        return storeBillingRecord;
    }

    /**
     * 构造门店代客下单的账单记录
     *
     * @param storeOrder 订单记录
     * @return 返回账单记录
     */
    public static TStoreBillingRecords buildForReservation(TStoreOrder storeOrder, String storeName) {
        // 如果没有订单信息则直接返回
        if (Objects.isNull(storeOrder)) {
            return null;
        }
        // 账单记录
        TStoreBillingRecords storeBillingRecord = new TStoreBillingRecords();
        storeBillingRecord.orderCode = storeOrder.getOrderCode();
        storeBillingRecord.type = "0";
        storeBillingRecord.storeId = storeOrder.getStoreId();
        storeBillingRecord.storeName = storeName;
        storeBillingRecord.recordType = "2";
        storeBillingRecord.orderPrice = storeOrder.getPrice();
        storeBillingRecord.status = "0";
        return storeBillingRecord;
    }

    /**
     * 门店账单记录查询参数
     */
    @Data
    @ApiModel(description = "查询参数")
    public static class QueryCriteria {

        /**
         * 门店名称
         */
        @ApiModelProperty(value = "门店名称")
        private String storeName;

        /**
         * 门店手机号
         */
        @ApiModelProperty(value = "门店手机号")
        private String mobile;

        /**
         * 开始时间
         */
        @ApiModelProperty(value = "开始时间")
        private String startTime;

        /**
         * 结束时间
         */
        @ApiModelProperty(value = "结束时间")
        private String endTime;

        /**
         * 账单记录类型 1 提货完成 2 代客下单完成
         */
        @ApiModelProperty(value = "账单记录类型 1 提货完成 2 代客下单完成")
        private String recordType;

        /**
         * 门店id
         */
        @ApiModelProperty(value = "门店id")
        private long storeId = -1;


        /**
         * 结算状态 0 未结算 1 已结算 默认0
         */
        @ApiModelProperty(value = "结算状态 0 未结算 1 已结算 默认0")
        private String status;


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
         * 构建账单进出类型
         *
         * @param recordType 账单记录类型
         * @return 查询参数实体
         */
        public QueryCriteria buildRecordType(String recordType) {
            this.recordType = recordType;
            return this;
        }


        /**
         * 获得查询参数map
         */
        public Map<String, Object> getQueryMap() {
            Map<String, Object> params = new HashMap<>();
            params.put("storeName", storeName);
            params.put("mobile", mobile);
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            params.put("storeId", storeId);
            params.put("status", status);
            if (!StringUtils.isEmpty(recordType)) {
                params.put("recordType", recordType);
            }
            return params;
        }


    }
}
