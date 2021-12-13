package com.ruoyi.marketing.service;


import com.ruoyi.marketing.domain.SeckillScenePanicbuy;
import com.ruoyi.util.PageHelper;

/**
 * 秒杀场次折扣聚合服务接口
 *
 * @author 伊甸园商城 created on 2020/5/15
 */
public interface SeckillScenePanicbuyServiceApi {

    /**
     * 分页查询秒杀场次折扣
     *
     * @param pageHelper     分页帮助类
     * @param seckillSceneId 秒杀场次id
     * @param storeId        店铺id
     * @return 返回秒杀场次折扣列表
     */
    PageHelper<SeckillScenePanicbuy> querySeckillScenePanicbuyList(PageHelper<SeckillScenePanicbuy> pageHelper, long seckillSceneId, long storeId);

}
