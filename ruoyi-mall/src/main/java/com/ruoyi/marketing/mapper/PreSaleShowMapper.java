package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.PreSaleShow;

import java.util.List;
import java.util.Map;

/**
 * 预售活动数据库接口
 *
 * @author 魔金商城 created on 2020/6/12
 */
public interface PreSaleShowMapper {

    /**
     * 新增预售活动
     *
     * @param preSaleShowList 预售活动列表
     * @return 成功>0 否则失败
     */

    int addPreSaleShows(List<PreSaleShow> preSaleShowList);

    /**
     * 删除预售活动
     *
     * @param params 删除参数
     * @return 成功>0 否则失败
     */

    int deletePreSaleShows(Map<String, Object> params);

    /**
     * 修改预售活动
     *
     * @param params 修改参数
     * @return 成功>0 否则失败
     */

    int updatePreSaleShow(Map<String, Object> params);

    /**
     * 根据预售id查询预售活动数量
     *
     * @param params 查询参数
     * @return 预售活动数量
     */

    int queryPreSaleShowCountByPreSaleId(Map<String, Object> params);

    /**
     * 分页查询预售活动集合
     *
     * @param parsms 查询参数
     * @return 预售活动集合
     */

    List<PreSaleShow> queryPreSaleShowList(Map<String, Object> parsms);

    /**
     * 查询预售活动总记录数
     *
     * @param parsms 查询参数
     * @return 预售活动总记录数
     */

    int queryPreSaleShowListCount(Map<String, Object> parsms);

    /**
     * 分页查询店铺参与平台预售活动集合
     *
     * @param parsms 查询参数
     * @return 店铺参与平台预售活动集合
     */

    List<PreSaleShow> queryPreSaleShowListForStore(Map<String, Object> parsms);

    /**
     * 查询店铺参与平台预售活动总记录数
     *
     * @param parsms 查询参数
     * @return 店铺参与平台预售活动总记录数
     */

    int queryPreSaleShowListCountForStore(Map<String, Object> parsms);

    /**
     * 删除预售活动促销分类id（删除促销分类时用）
     *
     * @param parsms 参数
     * @return 成功>0 否则失败
     */

    int deletePreSaleCate(Map<String, Object> parsms);

    /**
     * 分页查询预售活动列表（前端用）
     *
     * @param parsms 查询参数
     * @return 预售活动列表
     */

    List<PreSaleShow> queryPreSalesForSite(Map<String, Object> parsms);

    /**
     * 分页查询预售活动总记录数（前端用）
     *
     * @param parsms 查询参数
     * @return 预售活动总记录数
     */

    int queryPreSalesCountForSite(Map<String, Object> parsms);

    /**
     * 根据促销id删除预售活动
     *
     * @param params 删除参数
     */

    void deletePreSalesByMarketingIds(Map<String, Object> params);

    /**
     * 删除结束的预售活动（定时任务）
     */

    void deleteEndPreSales();

}
