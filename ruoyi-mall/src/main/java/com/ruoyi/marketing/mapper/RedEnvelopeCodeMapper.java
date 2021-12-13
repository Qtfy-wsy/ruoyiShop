package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.RedEnvelopeCode;

import java.util.List;
import java.util.Map;

/**
 * Created by 伊甸园商城 on 18/1/15
 * 红包码数据库接口
 */
public interface RedEnvelopeCodeMapper {

    /**
     * 添加红包码
     *
     * @param redEnvelopeCode 红包码实体类
     * @return 返回1成功 0失败
     */

    int addRedEnvelopeCode(List<RedEnvelopeCode> redEnvelopeCode);

    /**
     * 根据红包id查询红包码信息
     *
     * @param redEnvelopeId 红包id
     * @return 红包码信息
     */

    List<RedEnvelopeCode> queryRedEnvelopeCodeByRedEnvelopeId(long redEnvelopeId);

    /**
     * 删除红包码
     *
     * @param redEnvelopeId 红包id数组
     * @return 成功>=1 失败0
     */

    int deleteRedEnvelopeCode(long[] redEnvelopeId);

    /**
     * 查询用户可以使用的红包
     *
     * @param customerId 会员id
     * @return 返回用户可以使用的红包
     */

    List<RedEnvelopeCode> queryCustomerCanUseRedEnvelope(long customerId);

    /**
     * 根据会员id和红包状态查询会员领取红包信息
     *
     * @param map 查询条件
     * @return 会员领取红包信息集合
     */

    List<RedEnvelopeCode> queryRedEnvelopeCodeByCustomerId(Map<String, Object> map);

    /**
     * 根据会员id和红包状态查询会员领取红包条数
     *
     * @param map 查询条件
     * @return 领取红包条数
     */

    int queryRedEnvelopeCodeCountByCustomerId(Map<String, Object> map);

    /**
     * 查询可以领取的红包code
     *
     * @param redEnvelopeId 红包id
     * @return 红包集合
     */

    List<RedEnvelopeCode> queryCanReceiveRedEnvelopeCode(Long redEnvelopeId);

    /**
     * 查询用户可以领取的红包数量
     *
     * @param redEnvelopeId 红包id
     * @return 数量
     */

    int queryCanReceiveRedEnvelopeCodeCount(Long redEnvelopeId);

    /**
     * 查询用户已领取的红包数量
     *
     * @param map customerId 用户id redEnvelopeId 红包id
     * @return 数量
     */

    int queryReceivedRedEnvelopeCodeByCustomerId(Map<String, Object> map);

    /**
     * 更新红包领取状态
     *
     * @param redEnvelopeCode 红包code实体
     * @return 更新数量
     */

    int updateReceivedRedEnvelope(RedEnvelopeCode redEnvelopeCode);

    /**
     * 根据红包码查询红包id
     *
     * @param code 红包码
     * @return 对应的红包id
     */

    Long queryRedEnvelopeIdByCode(String code);

    /**
     * 设置用户红包已使用
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int setRedEnvelopeUsed(Map<String, Object> params);

}
