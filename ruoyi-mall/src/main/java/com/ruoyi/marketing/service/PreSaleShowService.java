package com.ruoyi.marketing.service;


import com.ruoyi.marketing.domain.PreSaleShow;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 预售活动服务接口
 *
 * @author 伊甸园商城 created on 2020/6/12
 */
public interface PreSaleShowService {

    /**
     * 新增预售活动
     *
     * @param preSaleShowList 预售活动列表
     * @param storeId         店铺id
     * @return 成功>0 否则失败 -1 存在重复添加
     */
    int addPreSaleShows(List<PreSaleShow> preSaleShowList, long storeId);

    /**
     * 删除预售活动
     *
     * @param ids     预售活动id数组
     * @param storeId 店铺id
     * @return 成功>0 否则失败
     */

    int deletePreSaleShows(Long[] ids, long storeId);

    /**
     * 修改预售活动
     *
     * @param id      预售活动id
     * @param cateId  促销分类id
     * @param sort    排序
     * @param storeId 店铺id
     * @return 成功>0 否则失败
     */

    int updatePreSaleShow(long id, Long cateId, int sort, long storeId);

    /**
     * 分页查询预售活动列表
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @param skuNo      单品编号
     * @param storeId    店铺id
     * @return 返回预售活动列表
     */
    PageHelper<PreSaleShow> queryPreSaleShowList(PageHelper<PreSaleShow> pageHelper, String name, String skuNo, long storeId);

    /**
     * 分页查询店铺参与平台预售活动列表
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @param skuNo      单品编号
     * @param storeId    店铺id
     * @return 返回店铺参与平台预售活动列表
     */
    PageHelper<PreSaleShow> queryPreSaleShowListForStore(PageHelper<PreSaleShow> pageHelper, String name, String skuNo, long storeId);

    /**
     * 删除预售活动促销分类id（删除促销分类时用）
     *
     * @param cateId  分类id
     * @param storeId 店铺id
     * @return 成功>0 否则失败
     */
    int deletePreSaleCate(long cateId, long storeId);

    /**
     * 分页查询预售活动列表（前端用）
     *
     * @param pageHelper 分页帮助类
     * @param cateId     分类id
     * @param storeId    店铺id
     * @return 返回预售活动列表
     */
    PageHelper<PreSaleShow> queryPreSalesForSite(PageHelper<PreSaleShow> pageHelper, long cateId, long storeId);

    /**
     * 根据促销id删除预售活动
     *
     * @param marketingIds 促销id集合
     */
    void deletePreSalesByMarketingIds(List<Long> marketingIds);

    /**
     * 自动删除结束的预售活动（定时任务）
     */
    void autoDeleteEndPreSales();

}
