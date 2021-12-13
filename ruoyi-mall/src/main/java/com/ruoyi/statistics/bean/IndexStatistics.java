package com.ruoyi.statistics.bean;


import com.ruoyi.order.vo.RecommendSku;
import com.ruoyi.order.vo.StoreSaleAmount;
import com.ruoyi.store.vo.NewCustomerStatistics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 伊甸园商城 on 2018/7/19
 * 首页信息统计
 */
@Data
@ApiModel(description = "首页信息统计")
public class IndexStatistics {

    /**
     * 今日新增会员数
     */
    @ApiModelProperty(value = "今日新增会员数")
    private int newCustomerToday;

    /**
     * 今日店铺销售额
     */
    @ApiModelProperty(value = "今日店铺销售额")
    private BigDecimal saleAmountToday;

    /**
     * 今日店铺销售量
     */
    @ApiModelProperty(value = "今日店铺销售量")
    private int saleCountToday;

    /**
     * 本周店铺销售额
     */
    @ApiModelProperty(value = "本周店铺销售额")
    private BigDecimal saleAmountThisWeek;

    /**
     * 本周店铺销售量
     */
    @ApiModelProperty(value = "本周店铺销售量")
    private int saleCountThisWeek;

    /**
     * 本周新增会员数
     */
    @ApiModelProperty(value = "本周新增会员数")
    private int newCustomerThisWeek;

    /**
     * 本周店铺销售额（按日期分组）
     */
    @ApiModelProperty(value = "本周店铺销售额（按日期分组）")
    private List<StoreSaleAmount> saleAmountThisWeekGroupByDay;

    /**
     * 本周店铺销售量（按日期分组）
     */
    @ApiModelProperty(value = "本周店铺销售量（按日期分组）")
    private List<StoreSaleAmount> saleCountThisWeekGroupByDay;

    /**
     * 本周新增会员数（按日期分组）
     */
    @ApiModelProperty(value = "本周新增会员数（按日期分组）")
    private List<NewCustomerStatistics> newCustomerThisWeekGroupByDay;

    /**
     * 查询店铺30天内热销商品
     */
    @ApiModelProperty(value = "查询店铺30天内热销商品")
    private List<RecommendSku> queryRecommentSkusThirtyDays;

    /**
     * 是否为门店,true是门店，false是店铺
     */
    @ApiModelProperty(value = "是否为门店,true是门店，false是店铺")
    private boolean ifChainStore;

    /**
     * 构建首页统计
     *
     * @param newCustomerToday              今日新增会员数
     * @param saleAmountToday               今日店铺销售额
     * @param saleCountToday                今日店铺销售量
     * @param saleAmountThisWeek            本周店铺销售额
     * @param saleCountThisWeek             本周店铺销售量
     * @param newCustomerThisWeek           本周新增会员数
     * @param saleAmountThisWeekGroupByDay  查询本周店铺销售额（按日期分组）
     * @param saleCountThisWeekGroupByDay   查询本周店铺销售量（按日期分组）
     * @param newCustomerThisWeekGroupByDay 查询本周新增会员数（按日期分组）
     * @return 首页统计
     */
    public static IndexStatistics buildIndexStatistics(int newCustomerToday, BigDecimal saleAmountToday, int saleCountToday, BigDecimal saleAmountThisWeek, int saleCountThisWeek, int newCustomerThisWeek, List<StoreSaleAmount> saleAmountThisWeekGroupByDay, List<StoreSaleAmount> saleCountThisWeekGroupByDay, List<NewCustomerStatistics> newCustomerThisWeekGroupByDay) {
        IndexStatistics indexStatistics = new IndexStatistics();
        indexStatistics.newCustomerToday = newCustomerToday;
        indexStatistics.saleAmountToday = saleAmountToday;
        indexStatistics.saleCountToday = saleCountToday;
        indexStatistics.saleAmountThisWeek = saleAmountThisWeek;
        indexStatistics.saleCountThisWeek = saleCountThisWeek;
        indexStatistics.newCustomerThisWeek = newCustomerThisWeek;
        indexStatistics.saleAmountThisWeekGroupByDay = saleAmountThisWeekGroupByDay;
        indexStatistics.saleCountThisWeekGroupByDay = saleCountThisWeekGroupByDay;
        indexStatistics.newCustomerThisWeekGroupByDay = newCustomerThisWeekGroupByDay;
        return indexStatistics;
    }

    /**
     * 构建店铺首页统计
     *
     * @param saleAmountToday              今日店铺销售额
     * @param saleCountToday               今日店铺销售量
     * @param saleAmountThisWeek           本周店铺销售额
     * @param saleAmountThisWeekGroupByDay 查询本周店铺销售额（按日期分组）
     * @param queryRecommentSkusThirtyDays 查询店铺30天内热销商品
     * @return 首页统计
     */
    public static IndexStatistics buildIndexStatisticsForStore(BigDecimal saleAmountToday, int saleCountToday, BigDecimal saleAmountThisWeek, List<StoreSaleAmount> saleAmountThisWeekGroupByDay, List<RecommendSku> queryRecommentSkusThirtyDays, boolean ifChainStore) {
        IndexStatistics indexStatistics = new IndexStatistics();
        indexStatistics.saleAmountToday = saleAmountToday;
        indexStatistics.saleCountToday = saleCountToday;
        indexStatistics.saleAmountThisWeek = saleAmountThisWeek;
        indexStatistics.saleAmountThisWeekGroupByDay = saleAmountThisWeekGroupByDay;
        indexStatistics.queryRecommentSkusThirtyDays = queryRecommentSkusThirtyDays;
        indexStatistics.ifChainStore = ifChainStore;
        return indexStatistics;
    }

}
