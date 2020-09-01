package com.ruoyi.marketing.service;


import com.ruoyi.marketing.domain.MarketingSetting;

/**
 * 营销设置服务接口
 */
public interface MarketingSettingService {

    /**
     * 查找营销设置
     *
     * @return 营销设置实体
     */
    MarketingSetting queryMarketingSetting();

    /**
     * 保存或者更新营销设置
     *
     * @param marketingSetting 营销设置实体
     * @return 1成功 否则失败
     */
    int saveOrUpdateMarketingSetting(MarketingSetting marketingSetting);
}
