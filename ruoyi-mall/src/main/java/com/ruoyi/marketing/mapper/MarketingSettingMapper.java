package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.MarketingSetting;

/**
 * 营销设置数据库接口
 */
public interface MarketingSettingMapper {

    /**
     * 查找营销设置
     *
     * @return 营销设置
     */

    MarketingSetting queryMarketingSetting();

    /**
     * 更新营销设置
     *
     * @param marketingSetting 营销设置实体
     * @return 1成功 否则失败
     */

    int updateMarketingSetting(MarketingSetting marketingSetting);

    /**
     * 添加营销设置
     *
     * @param marketingSetting 营销设置实体
     * @return 1成功 否则失败
     */

    int addMarketingSetting(MarketingSetting marketingSetting);
}

