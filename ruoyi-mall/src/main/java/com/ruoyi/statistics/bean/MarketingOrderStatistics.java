package com.ruoyi.statistics.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 促销订单统计实体类
 *
 * @author 伊甸园商城 created on 2019/4/11
 */
@Data
public class MarketingOrderStatistics {

    /**
     * 促销名称
     */
    private String marketingName;

    /**
     * 下单量
     */
    private int orderVolume;

    /**
     * 下单额
     */
    private BigDecimal orderAmount;

    /**
     * 下单单品销量
     */
    private int skuSalesVolume;

    /**
     * 添加促销名称
     *
     * @param marketingName 促销名称
     * @return 当前实体
     */
    public MarketingOrderStatistics addMarketingName(String marketingName) {
        this.marketingName = marketingName;
        return this;
    }

}
