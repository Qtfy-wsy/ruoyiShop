package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.StoreRedEnvelopeCode;

import java.util.List;
import java.util.Map;

/**
 * Created by 魔金商城 on 18/4/9
 * 门店红包码数据库接口
 */
public interface StoreRedEnvelopeCodeMapper {

    /**
     * 添加红包码
     *
     * @param storeRedEnvelopeCodes 红包码实体类
     * @return 返回1成功 0失败
     */

    int addStoreRedEnvelopeCode(List<StoreRedEnvelopeCode> storeRedEnvelopeCodes);

    /**
     * 根据红包id查询红包码信息
     *
     * @param redEnvelopeId 红包id
     * @return 红包码信息
     */

    List<StoreRedEnvelopeCode> queryStoreRedEnvelopeCodeByRedEnvelopeId(long redEnvelopeId);

    /**
     * 删除红包码
     *
     * @param redEnvelopeId 红包id数组
     * @return 成功>=1 失败0
     */

    int deleteStoreRedEnvelopeCode(long[] redEnvelopeId);

    /**
     * 查询用户可以使用的红包
     *
     * @param customerId 会员id
     * @return 返回用户可以使用的红包
     */

    List<StoreRedEnvelopeCode> queryCustomerCanUseStoreRedEnvelope(long customerId);

    /**
     * 根据会员id和红包状态查询会员领取红包信息
     *
     * @param map 查询条件
     * @return 会员领取红包信息集合
     */

    List<StoreRedEnvelopeCode> queryStoreRedEnvelopeCodeByCustomerId(Map<String, Object> map);

    /**
     * 根据会员id和红包状态查询会员领取红包条数
     *
     * @param map 查询条件
     * @return 领取红包条数
     */

    int queryStoreRedEnvelopeCodeCountByCustomerId(Map<String, Object> map);

    /**
     * 查询可以领取的红包code
     *
     * @param redEnvelopeId 红包id
     * @return 红包集合
     */

    List<StoreRedEnvelopeCode> queryCanReceiveStoreRedEnvelopeCode(Long redEnvelopeId);

    /**
     * 查询用户可以领取的红包数量
     *
     * @param redEnvelopeId 红包id
     * @return 数量
     */

    int queryCanReceiveStoreRedEnvelopeCodeCount(Long redEnvelopeId);

    /**
     * 查询用户已领取的红包数量
     *
     * @param map customerId 用户id redEnvelopeId 红包id
     * @return 数量
     */

    int queryReceivedStoreRedEnvelopeCodeByCustomerId(Map<String, Object> map);

    /**
     * 更新红包领取状态
     *
     * @param storeRedEnvelopeCode 红包code实体
     * @return 更新数量
     */

    int updateReceivedStoreRedEnvelope(StoreRedEnvelopeCode storeRedEnvelopeCode);

    /**
     * 根据红包码查询红包id
     *
     * @param code 红包码
     * @return 对应的红包id
     */

    Long queryStoreRedEnvelopeIdByCode(String code);

    /**
     * 设置用户红包已使用
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int setStoreRedEnvelopeUsed(Map<String, Object> params);

}
