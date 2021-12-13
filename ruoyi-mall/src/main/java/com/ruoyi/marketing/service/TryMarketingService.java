package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.TryMarketing;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 试用促销服务接口
 *
 * @author 伊甸园商城 created on 2020/6/9
 */
public interface TryMarketingService {

    /**
     * 分页查询试用促销列表
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @param skuNo      单品编号
     * @param storeId    店铺id
     * @return 返回试用促销列表
     */
    PageHelper<TryMarketing> queryTryMarketingList(PageHelper<TryMarketing> pageHelper, String name, String skuNo, long storeId);

    /**
     * 根据id查询试用促销信息
     *
     * @param tryId   试用促销id
     * @param storeId 店铺id
     * @return 试用促销信息
     */
    TryMarketing queryTryMarketingById(long tryId, long storeId);

    /**
     * 查询已过期且没有审核过的试用促销列表
     */
    List<TryMarketing> queryTimeOutAndUnAuditTryMarketingList();

}
