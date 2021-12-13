package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.SeckillSeceneStore;

import java.util.Map;

/**
 * 店铺参与秒杀活动的关联数据库接口
 *
 * @author 伊甸园商城 created on 2020/5/14
 */
public interface SeckillSeceneStoreMapper {

    /**
     * 新增店铺参与秒杀活动的关联
     *
     * @param seckillSeceneStore 店铺参与秒杀活动的关联实体
     * @return 成功1 否则失败
     */

    int addSeckillSeceneStore(SeckillSeceneStore seckillSeceneStore);

    /**
     * 查询店铺参与秒杀活动的关联
     *
     * @param params 查询参数
     * @return 店铺参与秒杀活动的关联
     */

    SeckillSeceneStore querySeckillSeceneStore(Map<String, Object> params);

    /**
     * 查询参与秒杀活动的店铺数量
     *
     * @param seckillSceneId 秒杀场次的id
     * @return 参与秒杀活动的店铺数量
     */

    int querySeckillSeceneStoreCount(long seckillSceneId);

    /**
     * 删除店铺参与秒杀活动的关联
     *
     * @param seckillSceneId 秒杀场次的id
     * @return 成功>0 否则失败
     */

    int deleteSeckillSeceneStore(long seckillSceneId);

}
