package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.MarketingPic;

import java.util.Map;

/**
 * 促销图片数据库接口
 *
 * @author 伊甸园商城 created on 2020/5/12
 */
public interface MarketingPicMapper {

    /**
     * 新增促销图片
     *
     * @param marketingPic 促销图片实体
     * @return 成功返回1 失败返回0
     */

    int addMarketingPic(MarketingPic marketingPic);

    /**
     * 查询促销图片
     *
     * @param params 参数
     * @return 返回促销图片信息
     */

    MarketingPic queryMarketingPic(Map<String, Object> params);

    /**
     * 查询促销图片数量
     *
     * @param params 参数
     * @return 返回促销图片数量
     */

    int queryMarketingPicCount(Map<String, Object> params);

    /**
     * 更新促销图片
     *
     * @param marketingPic 促销图片
     * @return 成功返回1 失败返回0
     */

    int updateMarketingPic(MarketingPic marketingPic);

}
