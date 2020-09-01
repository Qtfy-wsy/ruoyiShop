package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.MarketingPic;

/**
 * 促销图片服务接口
 *
 * @author 魔金商城 created on 2020/5/12
 */
public interface MarketingPicService {

    /**
     * 保存促销图片
     *
     * @param marketingPic 促销图片实体
     * @return 成功1 否则失败
     */
    int saveMarketingPic(MarketingPic marketingPic);

    /**
     * 查询促销图片
     *
     * @param type    促销图片类型
     * @param storeId 店铺id
     * @return 返回促销图片信息
     */
    MarketingPic queryMarketingPic(String type, long storeId);

}
