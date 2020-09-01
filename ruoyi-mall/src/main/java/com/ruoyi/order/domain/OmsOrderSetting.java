package com.ruoyi.order.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 订单设置对象 oms_order_setting
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Data
public class OmsOrderSetting extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 是否可以退款和退货  0 可以 1 不可以 默认0
     */
    @Excel(name = "是否可以退款和退货  0 可以 1 不可以 默认0 ")
    private String allowBack;

    /**
     * 订单自定收货几天前的订单 默认1
     */
    @Excel(name = "订单自定收货几天前的订单 默认1")
    private Integer aotuConfirm;

    /**
     * 退款说明
     */
    @Excel(name = "退款说明")
    private String refundsDesc;

    /**
     * 退货说明
     */
    @Excel(name = "退货说明")
    private String returnDesc;

    /**
     * 是否支持货到付款  0 支付 1 不支持 默认0
     */
    @Excel(name = "是否支持货到付款  0 支付 1 不支持 默认0 ")
    private String cashonDelivery;

    /**
     * 判断是否可以退单
     *
     * @return 可以返回true  不可以返回false
     */
    public boolean isCanBackOrder() {
        return "0".equals(this.allowBack);
    }

}
