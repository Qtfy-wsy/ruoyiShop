package com.ruoyi.marketing.service;


import com.ruoyi.marketing.domain.TrySkuApply;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 试用商品申请服务接口
 */
public interface TrySkuApplyService {

    /**
     * 添加试用商品申请
     *
     * @param trySkuApply 试用商品申请实体
     * @return 1：成功 -1：活动还没有开始 -2：活动已经结束 -3：您已申请过 -4活动不存在 -5 申请人数已达上限
     */
    int addTrySkuApply(TrySkuApply trySkuApply);


    /**
     * 根据参数查询试用商品申请
     *
     * @param tryId      试用促销id
     * @param customerId 用户id
     * @return 返回试用商品申请
     */
    TrySkuApply queryTrySkuApply(long tryId, long customerId);

    /**
     * 根据id查询试用商品申请
     *
     * @param id           试用申请id
     * @param customerId   用户id
     * @param isNeedDetail 是否需要详细信息
     * @return 返回试用商品申请
     */
    TrySkuApply queryTrySkuApplyById(long id, long customerId, boolean isNeedDetail);

    /**
     * 根据试用促销id查询试用商品申请集合
     *
     * @param tryId 试用促销id
     * @return 申请集合
     */
    List<TrySkuApply> queryTrySkuApplyByTryId(long tryId);

    /**
     * 分页查询用户的试用申请
     *
     * @param customerId 用户id
     * @param status     状态
     * @param pageHelper 分页帮助类
     * @return 申请集合
     */
    PageHelper<TrySkuApply> queryTrySkuApplys(long customerId, String status, PageHelper<TrySkuApply> pageHelper);

    /**
     * 更新试用商品申请
     *
     * @param tryId      试用促销id
     * @param customerId 用户id
     * @param status     申请状态
     * @return 成功返回1 失败返回0
     */
    int updateTrySkuApplyStatus(long tryId, long customerId, String status);


    /**
     * 查找申请通过人员名单
     *
     * @param tryId 试用促销id
     * @return 申请通过的人员名单
     */
    List<TrySkuApply> querySuccessApplyList(long tryId);

    /**
     * 更新试用申请为已下单
     *
     * @param trySkuApplies 试用申请
     */
    void updateTryApplyOrderd(List<TrySkuApply> trySkuApplies);

    /**
     * 根据用户id和单品id查询审核通过的试用申请
     *
     * @param customerId 会员id
     * @param skuId      单品id
     * @return 返回审核通过的试用申请
     */
    TrySkuApply queryPassedApplyByCustomerIdAndSkuId(long customerId, String skuId);

    /**
     * 根据试用促销id和店铺id分页查询试用申请
     *
     * @param pageHelper 分页帮助类
     * @param tryId      试用促销id
     * @param storeId    店铺id
     * @return 返回试用申请集合
     */
    PageHelper<TrySkuApply> queryApplyByTryIdAndStoreId(PageHelper<TrySkuApply> pageHelper, long tryId, long storeId);


    /**
     * 随机审核申请人，定时任务（勿删）
     */
    int randomExtractApplyCustomer();


}
