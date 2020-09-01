package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.PanicBuy;
import com.ruoyi.util.PageHelper;

/**
 * Created by 魔金商城 on 2020/5/15.
 * 抢购服务接口
 */
public interface PanicBuyService {

    /**
     * 根据id查询抢购
     *
     * @param id 抢购id
     * @return 返回抢购信息
     */
    PanicBuy queryById(long id);

    /**
     * 分页查询店铺抢购促销列表
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @return 返回店铺抢购促销列表
     */
    PageHelper<PanicBuy> queryStorePanicBuyList(PageHelper<PanicBuy> pageHelper, long storeId);

    /**
     * 设置抢购促销展示和排序
     *
     * @param panicBuyId 抢购id
     * @param isShow     是否显示
     * @param sort       排序
     * @param storeId    店铺id
     * @return 成功1 否则失败
     */
    int updatePanicBuyShow(long panicBuyId, String isShow, Integer sort, long storeId);

    /**
     * 批量设置抢购促销是否展示
     *
     * @param panicBuyIds 抢购id数组
     * @param isShow      是否显示
     * @param storeId     店铺id
     * @return 成功1 否则失败
     */
    int batchUpdatePanicBuyShow(Long[] panicBuyIds, String isShow, long storeId);

    /**
     * 分页查询店铺抢购促销列表（前端用）
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @return 返回店铺抢购促销列表
     */
    PageHelper queryStorePanicBuyListForSite(PageHelper pageHelper, long storeId);

}
