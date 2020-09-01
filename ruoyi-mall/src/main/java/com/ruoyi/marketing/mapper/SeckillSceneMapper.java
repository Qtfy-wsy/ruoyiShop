package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.SeckillScene;

import java.util.List;
import java.util.Map;

/**
 * Created by 魔金商城 on 2020/5/11.
 * 秒杀场次数据库接口
 */
public interface SeckillSceneMapper {

    /**
     * 根据时间查询秒杀场次记录
     *
     * @param time 时间
     * @return 返回秒杀场次
     */

    List<SeckillScene> queryByTime(String time);

    /**
     * 删除秒杀场次
     *
     * @param params 参数
     */

    int deleteSeckillScene(Map<String, Object> params);

    /**
     * 新增秒杀场次
     *
     * @param seckillScene 秒杀场次
     * @return 成功返回1 失败返回0
     */

    int addSeckillScene(SeckillScene seckillScene);

    /**
     * 查询秒杀活动总记录数
     *
     * @param params 参数
     * @return 返回秒杀活动总记录数
     */

    int querySeckillScenesCount(Map<String, Object> params);

    /**
     * 查询秒杀活动
     *
     * @param params 参数
     * @return 返回秒杀获得
     */

    List<SeckillScene> querySeckillScenes(Map<String, Object> params);

    /**
     * 根据抢购活动开始时间查询秒杀场次
     *
     * @param params 查询参数
     * @return 返回秒杀场次
     */

    SeckillScene queryByStartTime(Map<String, Object> params);

    /**
     * 根据id查询秒杀场次
     *
     * @param seckillSceneId 秒杀场次id
     * @return 返回秒杀场次
     */

    SeckillScene querySeckillSceneById(long seckillSceneId);

}
