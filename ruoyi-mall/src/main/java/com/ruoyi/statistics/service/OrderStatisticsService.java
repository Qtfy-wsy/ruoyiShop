package com.ruoyi.statistics.service;


import com.ruoyi.order.vo.*;
import com.ruoyi.util.PageHelper;


/**
 * Created by 魔金商城 on 18/2/2.
 * 订单统计服务接口
 */
public interface OrderStatisticsService {

    /**
     * 查询用户下单量
     *
     * @param pageHelper 分页帮助类
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 返回用户下单量
     */
    PageHelper<CustomerOrderAmount> queryCustomerOrderAmounts(PageHelper<CustomerOrderAmount> pageHelper, String startTime, String endTime);


    /**
     * 查询用户消费额
     *
     * @param pageHelper 分页帮助类
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 返回用户消费额
     */
    PageHelper<CustomerConsumption> queryCustomerConsumption(PageHelper<CustomerConsumption> pageHelper, String startTime, String endTime);

    /**
     * 查询单品销售量排行
     *
     * @param pageHelper 分页帮助类
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param storeId    店铺id
     * @return 返回单品销售量排行
     */
    PageHelper<SkuSaleAmount> querySkuSaleVolume(PageHelper<SkuSaleAmount> pageHelper, String startTime, String endTime, long storeId);

    /**
     * 查询单品销售额排行
     *
     * @param pageHelper 分页帮助类
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param storeId    店铺id
     * @return 返回单品销售额排行
     */
    PageHelper<SkuSaleAmount> querySkuSaleAmount(PageHelper<SkuSaleAmount> pageHelper, String startTime, String endTime, long storeId);

    /**
     * 查询店铺销售量排行
     *
     * @param pageHelper 分页帮助类
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 返回店铺销售量排行
     */
    PageHelper<StoreSaleAmount> queryStoreSaleVolume(PageHelper<StoreSaleAmount> pageHelper, String startTime, String endTime);

    /**
     * 查询店铺销售额排行
     *
     * @param pageHelper 分页帮助类
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 返回店铺销售额排行
     */
    PageHelper<StoreSaleAmount> queryStoreSaleAmount(PageHelper<StoreSaleAmount> pageHelper, String startTime, String endTime);

    /**
     * 查询促销订单统计
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param orderType 订单类型
     * @param storeId   店铺id
     * @return 促销订单统计
     */
    MarketingOrderStatistics queryMarketingOrderStatistics(String startTime, String endTime, String orderType, long storeId);
}
