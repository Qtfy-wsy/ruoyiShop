package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.MarketingCate;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * Created by 魔金商城 on 18/1/10.
 * 促销分类服务接口
 */
public interface MarketingCateService {

    /**
     * 分页查询促销分类
     *
     * @param pageHelper 分页帮助类
     * @param name       促销分类名称
     * @param type       促销分类类型
     * @param storeId
     * @return 返回促销分类信息
     */
    PageHelper queryMarketingCates(PageHelper<MarketingCate> pageHelper, String name, String type, long storeId);

    /**
     * 根据促销分类type查询促销分类
     *
     * @param type 促销分类类型
     * @return 返回促销分类信息
     */
    List<MarketingCate> queryMarketingCatesByType(String type);

    /**
     * 根据促销分类id查询促销分类
     *
     * @param id 促销分类id
     * @return 返回促销分类信息
     */
    MarketingCate queryMarketingCateById(long id, long storeId);

    /**
     * 新增促销分类
     *
     * @param marketingcate 促销分类信息
     * @return 成功返回1 失败返回0
     */
    int addMarketingCate(MarketingCate marketingcate);

    /**
     * 修改促销分类
     *
     * @param marketingcate 促销分类信息
     * @return 成功返回1 失败返回0
     */
    int updateMarketingCate(MarketingCate marketingcate);

    /**
     * 批量删除促销分类
     *
     * @param ids     促销分类id数组
     * @param storeId 店铺id
     * @param type    促销分类类型
     * @return 成功返回1 失败返回0
     */
    int deleteMarketingCates(long[] ids, long storeId, String type);

    /**
     * 根据促销分类type和店铺id查询促销分类
     *
     * @param type    促销分类类型
     * @param storeId 店铺id
     * @return 返回促销分类信息
     */
    List<MarketingCate> queryMarketingCatesByTypeAndStoreId(String type, long storeId);

}
