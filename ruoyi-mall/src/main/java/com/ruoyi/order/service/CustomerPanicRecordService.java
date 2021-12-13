package com.ruoyi.order.service;


import com.ruoyi.order.domain.OmsOrderSku;

/**
 * Created by 伊甸园商城 on 17/12/27.
 * 会员抢购纪录服务接口
 */
public interface CustomerPanicRecordService {

    /**
     * 查询用户指定抢购可以使用的数量
     *
     * @param customerId  会员id
     * @param marketingId 抢购id
     * @return 返回用户可以使用的数量
     */
    int queryCanUseNum(long customerId, long marketingId);

    /**
     * 更新用户的抢购纪录
     *
     * @param orderSku 订单下的单品
     * @return 成功返回>0 失败返回0
     */
    int updateCustomerPanicRecord(OmsOrderSku orderSku);

    /**
     * 查询秒杀使用的总数
     *
     * @param panicbuyId 秒杀折扣id
     * @return 返回秒杀使用的总数
     */
    int queryPanicUsedCount(long panicbuyId);

}
