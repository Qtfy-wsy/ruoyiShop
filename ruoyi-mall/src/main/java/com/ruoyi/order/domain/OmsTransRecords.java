package com.ruoyi.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付流水对象 oms_trans_records
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Data
@Builder
@ApiModel(description = "交易记录实体")
public class OmsTransRecords extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 系统内部订单id
     */
    @Excel(name = "系统内部订单id")
    private String lsTransCode;
    /**
     * 外部系统交易流水号
     */
    @Excel(name = "外部系统交易流水号")
    private String transCode;
    /**
     * 类型 1 门店订单 2 订单 3 预存款
     */
    @Excel(name = "类型 1 门店订单 2 订单 3 预存款")
    private String type;
    /**
     * 交易金额
     */
    @Excel(name = "交易金额")
    private BigDecimal money;
    /**
     * 订单id
     */
    @Excel(name = "订单id")
    private Long orderId;
    /**
     * 支付方式
     */
    @Excel(name = "支付方式")
    private String channel;
    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    @Tolerate
    public OmsTransRecords() {
    }


}
