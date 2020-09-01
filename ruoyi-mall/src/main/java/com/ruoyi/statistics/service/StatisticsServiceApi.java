package com.ruoyi.statistics.service;


import com.ruoyi.order.vo.*;
import com.ruoyi.statistics.bean.IndexStatistics;
import com.ruoyi.statistics.bean.StoreAppOrderStatistics;
import com.ruoyi.statistics.bean.StoreAppStatistics;
import com.ruoyi.store.vo.NewCustomerStatistics;
import com.ruoyi.store.vo.NewStoreInfoStatistics;
import com.ruoyi.store.vo.StoreInfoAreaStatistics;
import com.ruoyi.util.PageHelper;

import java.util.List;


/**
 * Created by 魔金商城 on 18/2/2.
 * 统计服务接口
 */
public interface StatisticsServiceApi {

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
     * 统计新增用户数量（按日期分组）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回按日期分组的新增用户数量
     */
    List<NewCustomerStatistics> queryNewCustomerStatistics(String startTime, String endTime);

    /**
     * 分页统计新增用户数量（按日期分组）
     *
     * @param pageHelper 分页帮助类
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 返回分页的按日期分组的新增用户数量
     */
    PageHelper<NewCustomerStatistics> queryNewCustomerStatisticsWithPage(PageHelper<NewCustomerStatistics> pageHelper, String startTime, String endTime);

    /**
     * 统计新增店铺数量（按日期分组）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回按日期分组的新增店铺数量
     */
    List<NewStoreInfoStatistics> queryNewStoreInfoStatistics(String startTime, String endTime);

    /**
     * 分页统计新增店铺数量（按日期分组）
     *
     * @param pageHelper 分页帮助类
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 返回按日期分组的新增店铺数量（带分页）
     */
    PageHelper<NewStoreInfoStatistics> queryNewStoreInfoStatisticsWithPage(PageHelper<NewStoreInfoStatistics> pageHelper, String startTime, String endTime);

    /**
     * 统计店铺地区数量（按省级地区分组）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回店铺地区数量统计
     */
    List<StoreInfoAreaStatistics> queryStoreInfoAreaStatistics(String startTime, String endTime);

    /**
     * 查询系统首页统计
     *
     * @param storeId 店铺id
     * @return 返回今日新增会员数，今日销售额，今日订单数，本周销售额，本周店铺销售量，本周新增会员数，本周店铺销售额（按日期分组），本周店铺销售量（按日期分组），本周新增会员数（按日期分组）
     */
    IndexStatistics queryIndexStatistics(long storeId);

    /**
     * 查询店铺首页统计
     *
     * @param storeId 店铺id
     * @return 返回今日店铺销售额，今日店铺销售量，本周店铺销售额，查询本周店铺销售额（按日期分组），查询店铺30天内热销商品
     */
    IndexStatistics queryIndexStatisticsForStore(long storeId);

    /**
     * 店铺app首页统计
     *
     * @param storeId 店铺id
     * @return 返回店铺app首页统计
     */
    StoreAppStatistics queryStoreAppStatistics(long storeId);


    /**
     * 商家app 订单统计
     *
     * @param storeId 店铺id
     * @return 返回商家app订单统计
     */
    StoreAppOrderStatistics queryStoreAppOrderStatistics(long storeId);

    /**
     * 查询促销订单统计
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param storeId   店铺id
     * @return 促销订单统计
     */
    MarketingStatistics queryMarketingStatistics(String startTime, String endTime, long storeId);
}
