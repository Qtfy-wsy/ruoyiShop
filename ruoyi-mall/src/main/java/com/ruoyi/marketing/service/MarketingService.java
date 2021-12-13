package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.PanicBuy;
import com.ruoyi.marketing.domain.TryMarketing;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 伊甸园商城 on 17/6/7.
 * 促销服务接口
 */
public interface MarketingService {

    /**
     * 新增促销
     *
     * @param marketing 促销信息
     * @return 成功返回1 失败返回0
     */
    default int addMarketing(Marketing marketing) {
        return 0;
    }


    /**
     * 设置促销的详情
     *
     * @param marketing 促销信息
     */
    default void setMarketingDetail(Marketing marketing) {
    }


    /**
     * 更新促销信息
     *
     * @param marketing 促销信息
     * @return 成功返回1 失败发回0
     */
    default int updateMarketing(Marketing marketing) {
        return 0;
    }

    /**
     * 删除促销
     *
     * @param marketingId 促销id
     * @param storeId     店铺id
     * @return 成功返回1 失败返回0
     */
    default int deleteMarketing(long marketingId, long storeId) {
        return 0;
    }

    /**
     * 批量删除促销
     *
     * @param marketingIds 促销id数组
     * @param storeId      店铺id
     * @param type         促销类型
     * @return 成功返回>1 失败返回0
     */
    default int deleteMarketings(Long[] marketingIds, long storeId, String type) {
        return 0;
    }

    /**
     * 更新已申请的人数（试用促销用）
     *
     * @param tryMarketing 试用促销
     * @return 1 成功 0失败
     */
    default int updateAlreadyApplyNum(TryMarketing tryMarketing) {
        return 0;
    }

    /**
     * 更改审核状态（试用促销用）
     *
     * @param tryMarketing 试用促销
     * @return 1成功 0失败
     */
    default int updateAuditStatus(TryMarketing tryMarketing) {
        return 0;
    }

    /**
     * 新增众筹的已筹金额
     *
     * @param money       金额
     * @param marketingId 众筹id
     * @return 成功返回1 失败返回0
     */
    default int addCrowdFundingAlerdyMoney(BigDecimal money, long marketingId) {
        return 0;
    }

    /**
     * 更新众筹促销已处理状态
     *
     * @param marketingId 促销id
     * @return 1成功 否则失败
     */
    default int updateCrowdFundingAutoHandleStatus(long marketingId) {
        return 0;
    }

    /**
     * 根据秒杀场次时间查询秒杀记录
     *
     * @param time    秒杀场次
     * @param storeId 店铺id
     * @return 返回秒杀场次
     */
    default List<PanicBuy> queryPanicBuysByTime(String time, long storeId) {
        return null;
    }

}
