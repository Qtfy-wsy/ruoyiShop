package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.RedEnvelope;
import com.ruoyi.marketing.domain.RedEnvelopeCode;
import com.ruoyi.marketing.domain.RedEnvelopeDetails;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * Created by 伊甸园商城 on 18/1/15
 * 红包服务接口
 */
public interface RedEnvelopeService {

    /**
     * 添加红包
     *
     * @param redEnvelope 红包实体类
     * @return 返回 -1失败 -2限领个数小于等于零 -3限领个数大于总个数 -4开始时间大于结束时间 -5店铺报名截止时间大于开始时间 >=1成功
     */
    int addRedEnvelope(RedEnvelope redEnvelope);

    /**
     * 分页查询红包
     *
     * @param pageHelper    分页帮助类
     * @param name          红包名称
     * @param storeId       店铺id
     * @param isNeedHasJoin 是否需要参加红包
     * @return 返回红包信息
     */
    PageHelper<RedEnvelope> queryRedEnvelopes(PageHelper<RedEnvelope> pageHelper, String name, long storeId, boolean isNeedHasJoin);

    /**
     * 删除及批量删除红包
     *
     * @param ids 红包id数组
     * @return 成功1 失败0
     */
    int deleteRedEnvelope(long[] ids);

    /**
     * 查询红包详情页
     *
     * @param id 红包id
     * @return 返回红包详情页
     */
    RedEnvelopeDetails queryRedEnvelopeDetails(long id);

    /**
     * 复制红包链接
     *
     * @param id 红包id
     * @return -1 没有查到该红包  0 该红包已过期  其他字符串均为红包链接
     */
    String copyRedEnvelopeUrl(long id);

    /**
     * 用户领取红包
     *
     * @param customerId    用户id
     * @param redEnvelopeId 红包id
     * @return 返回码 1：成功 -1：参数不全 -2：活动已过期 -3：红包已领完 -4：用户领取的红包已达上限 -5红包已失效(删除状态) -6 系统繁忙，请重试
     */
    int receiveRedEnvelope(long customerId, Long redEnvelopeId);

    /**
     * 根据id查询红包信息（pc用）
     *
     * @param redEnvelopeId 红包id
     * @return 红包实体
     */
    RedEnvelope queryRedEnvelopeByRedEnvelopeId(Long redEnvelopeId);


    /**
     * 查询红包
     *
     * @param pageHelper 分页帮助类
     * @return 红包集合
     */
    PageHelper<RedEnvelope> queryRedEnvelopeForSite(PageHelper<RedEnvelope> pageHelper);


    /**
     * 根据会员id查询会员领取红包信息
     *
     * @param customerId 会员id
     * @return 会员领取红包信息集合
     */
    PageHelper<RedEnvelopeCode> queryRedEnvelopeCodeByCustomerId(PageHelper<RedEnvelopeCode> pageHelper, long customerId, String status);

    /**
     * 查询用户可以试用的红包
     *
     * @param customerId 用户id
     * @return 返回用户用户使用的红包
     */
    List<RedEnvelopeCode> queryCustomerCanUseRedEnvelope(long customerId);

    /**
     * 参加红包（store用）
     *
     * @param redEnvelopeId 红包id
     * @param storeId       店铺id
     * @return 返回 -2 失败  -1 红包已过期  0 已经参加过红包  1 成功
     */
    int joinRedEnvelope(long redEnvelopeId, long storeId);


    /**
     * 设置用户的红包已经使用
     *
     * @param customerId      用户id
     * @param redEnvelopeCode 红包的code
     * @return 成功>1 失败=0
     */
    int setRedEnvelopeUsed(Long customerId, String redEnvelopeCode);

}
