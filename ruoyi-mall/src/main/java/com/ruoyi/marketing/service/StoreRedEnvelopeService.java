package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.StoreRedEnvelope;
import com.ruoyi.marketing.domain.StoreRedEnvelopeDetails;
import com.ruoyi.util.PageHelper;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 伊甸园商城 on 18/4/9
 * 门店红包服务接口
 */
public interface StoreRedEnvelopeService {

    /**
     * 添加红包
     *
     * @param storeRedEnvelope 红包实体类
     * @return 返回 -1失败 -2开始时间大于结束时间 >=1成功
     */
    int addStoreRedEnvelope(StoreRedEnvelope storeRedEnvelope);

    /**
     * 分页查询红包
     *
     * @param pageHelper 分页帮助类
     * @param name       红包名称
     * @param storeId    店铺id
     * @return 返回红包信息
     */
    PageHelper<StoreRedEnvelope> queryStoreRedEnvelopes(PageHelper<StoreRedEnvelope> pageHelper, String name, long storeId);

    /**
     * 删除及批量删除红包
     *
     * @param ids 红包id数组
     * @return 成功1 失败0
     */
    int deleteStoreRedEnvelope(long[] ids);

    /**
     * 查询红包详情页
     *
     * @param id 红包id
     * @return 返回红包详情页
     */
    StoreRedEnvelopeDetails queryStoreRedEnvelopeDetails(long id);

    /**
     * 根据id查询门店红包
     *
     * @param redEnvelopeId 红包id
     * @return 返回门店红包
     */
    StoreRedEnvelope queryStoreRedEnvelopeById(long redEnvelopeId);


    /**
     * 根据订单价和门店id查询可用红包
     *
     * @param price   订单总价
     * @param storeId 门店id
     * @return 可用门店红包集合
     */
    List<StoreRedEnvelope> queryCanUseStoreRedEnvelopes(BigDecimal price, long storeId);

    /**
     * 根据红包id领取红包
     *
     * @param redEnvelopeId 红包id
     * @param customerId    用户id
     * @return 领取的红包code
     */
    String receiveStoreRedEnvelopeCode(long redEnvelopeId, long customerId);

    /**
     * 设置门店红包已使用
     *
     * @param redEnvelopeCode 红包code
     * @param customerId      用户id
     * @return 1成功 否则失败
     */
    int setStoreRedEnvelopeUsed(String redEnvelopeCode, long customerId);
}
