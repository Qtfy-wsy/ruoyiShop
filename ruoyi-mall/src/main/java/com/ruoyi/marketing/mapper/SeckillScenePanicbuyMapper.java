package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.SeckillScenePanicbuy;

import java.util.List;
import java.util.Map;

/**
 * Created by 伊甸园商城 on 2020/5/12.
 * 秒杀场次折扣数据库接口
 */
public interface SeckillScenePanicbuyMapper {

    /**
     * 新增秒杀场次折扣
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
     * 查询秒杀场次下面的折扣
     *
     * @param seckillSceneId 秒杀场次id
     * @return 返回秒杀场次下面的折扣
     */

    int querySeckillScenePanicbuyCount(long seckillSceneId);

    /**
     * 根据折扣促销id删除秒杀场次折扣
     *
     * @param params 删除参数
     */

    void deleteSeckillScenePanicbuysByPanicBuyIds(Map<String, Object> params);

    /**
     * 分页查询秒杀场次折扣
     *
     * @param params 查询参数
     * @return 返回秒杀场次折扣列表
     */

    List<SeckillScenePanicbuy> querySeckillScenePanicbuyList(Map<String, Object> params);

    /**
     * 查询秒杀场次折扣总记录数
     *
     * @param params 查询参数
     * @return 返回秒杀场次折扣列表总记录数
     */

    int querySeckillScenePanicbuyListCount(Map<String, Object> params);


    /**
     * 查询平台秒杀折扣活动总数
     *
     * @param params 查询参数
     * @return 返回平台秒杀活动总数
     */

    int querySeckillScenePanicbuyForPlatformCount(Map<String, Object> params);

    /**
     * 平台秒杀活动折扣
     *
     * @param params 查询参数
     * @return 返回平台秒杀活动折扣
     */

    List<SeckillScenePanicbuy> querySeckillScenePanicbuyForPlatform(Map<String, Object> params);

    /**
     * 修改秒杀活动折扣
     *
     * @param params 修改参数
     * @return 成功1 否则失败
     */

    int updateSeckillScenePanicbuy(Map<String, Object> params);

    /**
     * 根据促销id删除秒杀场次折扣
     *
     * @param params 删除参数
     */

    void deleteSeckillScenePanicbuysByMarketingIds(Map<String, Object> params);

}
