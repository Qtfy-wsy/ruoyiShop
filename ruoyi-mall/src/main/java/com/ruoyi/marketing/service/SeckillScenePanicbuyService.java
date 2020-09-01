package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.SeckillScenePanicbuy;
import com.ruoyi.util.PageHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by 魔金商城 on 2020/5/12.
 * 秒杀场次折扣服务接口
 */
public interface SeckillScenePanicbuyService {

    /**
     * 添加秒杀场次折扣
     *
     * @param seckillScenePanicbuys 秒杀场次折扣
     */
    void addSeckillScenePanicbuys(List<SeckillScenePanicbuy> seckillScenePanicbuys);

    /**
     * 删除秒杀场次折扣
     *
     * @param params 删除参数
     */
    void deleteSeckillScenePanicbuys(Map<String, Object> params);

    /**
     * 根据秒杀场次id查询秒杀场次下面的折扣数量
     *
     * @param seckillSceneId 秒杀场次id
     * @return 返回秒杀场次下面的折扣数量
     */
    int querySeckillScenePanicbuyCount(long seckillSceneId);

    /**
     * 根据折扣促销id删除秒杀场次折扣
     *
     * @param panicBuyIds 折扣促销id集合
     */
    void deleteSeckillScenePanicbuysByPanicBuyIds(List<Long> panicBuyIds);

    /**
     * 分页查询秒杀场次折扣
     *
     * @param pageHelper     分页帮助类
     * @param seckillSceneId 秒杀场次id
     * @param storeId        店铺id
     * @return 返回秒杀场次折扣列表
     */
    PageHelper<SeckillScenePanicbuy> querySeckillScenePanicbuyList(PageHelper<SeckillScenePanicbuy> pageHelper, long seckillSceneId, long storeId);

    /**
     * 根据秒杀时间查询平台的秒杀活动
     *
     * @param pageHelper  分页帮助类
     * @param seckillTime 秒杀时间
     * @return 返回平台的秒杀获得
     */
    PageHelper querySeckillScenePanicbuyForPlatform(PageHelper pageHelper, String seckillTime);

    /**
     * 修改秒杀活动折扣
     *
     * @param id     秒杀活动折扣id
     * @param isShow 是否显示
     * @param sort   排序
     * @return 成功1 否则失败
     */
    int updateSeckillScenePanicbuy(long id, String isShow, Integer sort);

    /**
     * 批量修改秒杀活动折扣
     *
     * @param ids    秒杀活动折扣id数组
     * @param isShow 是否显示
     * @return 成功1 否则失败
     */
    int batchUpdateSeckillScenePanicbuy(Long[] ids, String isShow);

    /**
     * 根据促销id删除秒杀场次折扣
     *
     * @param marketingIds 促销id集合
     */
    void deleteSeckillScenePanicbuysByMarketingIds(List<Long> marketingIds);

}
