package com.ruoyi.order.service;

/**
 * Created by 魔金商城 on 2020/4/8.
 * 预存款服务api接口
 */
public interface PredepositRecordServiceApi {

    /**
     * 根据会员id和交易号更新支付成功状态
     *
     * @param transCode    交易号
     * @param customerId   会员id
     * @param outTransCode 外部交易流水号
     * @return 1:成功
     */
    int updateStatusSuccessByTransCode(String transCode, long customerId, String channel, String outTransCode);
}
