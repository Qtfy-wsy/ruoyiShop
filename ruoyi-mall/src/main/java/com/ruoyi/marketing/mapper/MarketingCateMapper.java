package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.MarketingCate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 魔金商城 on 18/1/9.
 * 促销分类数据库接口
 */
public interface MarketingCateMapper {

    /**
     * 查询促销分类的总记录数
     *
     * @param params 查询参数
     * @return 返回促销分类总记录数
     */

    int queryMarketingCateCount(Map<String, Object> params);

    /**
     * 分页查询促销分类信息
     *
     * @param params 查询参数
     * @return 返回促销分类信息
     */

    List<MarketingCate> queryMarketingCates(Map<String, Object> params);

    /**
     * 根据type查询促销分类信息
     *
     * @param type 促销分类类型
     * @return 返回促销分类信息
     */

    List<MarketingCate> queryMarketingCatesByType(@Param("type") String type);

    /**
     * 根据促销分类id查询促销分类
     *
     * @param params 查询参数
     * @return 返回促销分类信息
     */

    MarketingCate queryMarketingCateById(Map<String, Object> params);

    /**
     * 新增促销分类
     *
     * @param marketingcate 促销分类信息
     * @return 成功返回 1 失败返回0
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
     * 删除促销分类
     *
     * @param params 删除促销分类参数
     * @return 成功返回1 失败返回0
     */

    int deleteMarketingCate(Map<String, Object> params);

    /**
     * 根据type和店铺id查询促销分类信息
     *
     * @param params 查询参数
     * @return 返回促销分类信息
     */

    List<MarketingCate> queryMarketingCatesByTypeAndStoreId(Map<String, Object> params);

}
