package com.ruoyi.order.domain;

import lombok.Data;

import java.util.Objects;

/**
 * Created by 伊甸园商城 on 17/12/27.
 * 会员的抢购纪录
 */
@Data
public class CustomerPanicRecord {

    /**
     * 主键id
     */
    private long id;

    /**
     * 会员id
     */
    private long customerId;

    /**
     * 抢购促销id 注意是对应sms_marketing_panicbuy表中的id
     */
    private long marketingId;

    /**
     * 用户已经购买抢购的数量
     */
    private int useNum;

    /**
     * 该抢购的会员限购数量
     */
    private int panicNum;

    /**
     * 订单id
     */
    private long orderId;

    /**
     * 构造会员的抢购纪录信息
     *
     * @param orderSku 订单单品
     * @return 返回会员抢购纪录信息
     */
    public static CustomerPanicRecord build(OmsOrderSku orderSku) {
        if (Objects.isNull(orderSku)) {
            return null;
        }
        CustomerPanicRecord customerPanicRecord = new CustomerPanicRecord();
        customerPanicRecord.customerId = orderSku.getCustomerId();
        customerPanicRecord.marketingId = orderSku.getPanicMarketingId();
        customerPanicRecord.useNum = orderSku.getNum();
        customerPanicRecord.panicNum = orderSku.getPanicLimitNum();
        customerPanicRecord.orderId = orderSku.getOrderId();
        return customerPanicRecord;
    }

    /**
     * 返回用户可以使用抢购的数量
     *
     * @return 返回用户可以使用的抢购数量
     */
    public int getCanUseNum() {
        return panicNum - useNum;
    }
}
