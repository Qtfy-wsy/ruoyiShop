package com.ruoyi.marketing.service;


import com.ruoyi.marketing.domain.CrowdFunding;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.PanicBuySku;
import com.ruoyi.marketing.vo.SeckillSku;
import com.ruoyi.util.PageHelper;

/**
 * 促销混合api
 */
public interface MarketingServiceApi {

    /**
     * 更新抢购信息
     *
     * @param marketing 抢购信息
     * @return 成功返回1 失败返回0 -1 只有未开始的活动才能修改
     */
    int updatePanic(Marketing marketing);

    /**
     * 新增定金预售信息
     *
     * @param marketing 定金预售信息
     * @return 成功返回1 失败返回0
     */
    int addDepositPreSale(Marketing marketing);

    /**
     * 更新定金预售信息
     *
     * @param marketing 定金预售信息
     * @return 成功返回1 失败返回0
     */
    int updateDepositPreSale(Marketing marketing);

    /**
     * 新增全款预售信息
     *
     * @param marketing 全款预售信息
     * @return 成功返回1 失败返回0
     */
    int addFullPreSale(Marketing marketing);

    /**
     * 更新全款预售信息
     *
     * @param marketing 全款预售信息
     * @return 成功返回1 失败返回0
     */
    int updateFullPreSale(Marketing marketing);

    /**
     * 分页查询众筹促销
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询参数
     * @return 众筹促销集合
     */
    PageHelper<Marketing> queryCrowdFundingMarketings(PageHelper<Marketing> pageHelper, CrowdFunding.QueryCriteria queryCriteria);

    /**
     * 分页查询店铺众筹促销
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询参数
     * @return 店铺众筹促销集合
     */
    PageHelper<Marketing> queryStoreCrowdFundingMarketings(PageHelper<Marketing> pageHelper, CrowdFunding.QueryCriteria queryCriteria);

    /**
     * 查询平台秒杀活动
     *
     * @param seckillTime 查询平台秒杀活动
     * @return 返回平台秒杀活动
     */
    PageHelper<SeckillSku> querySeckillScenePanicbuyForPlatform(PageHelper<SeckillSku> pageHelper, String seckillTime);

    /**
     * 查询店铺秒杀活动
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @return 返回店铺秒杀活动
     */
    PageHelper<PanicBuySku> queryStorePanicBuyListForSite(PageHelper<PanicBuySku> pageHelper, long storeId);
}
