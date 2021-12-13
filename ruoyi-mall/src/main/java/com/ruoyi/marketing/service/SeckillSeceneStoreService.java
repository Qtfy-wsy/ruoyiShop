package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.SeckillSeceneStore;


/**
 * Created by 伊甸园商城 on 2020/5/13.
 * 店铺参与秒杀活动的关联服务接口
 */
public interface SeckillSeceneStoreService {

    /**
     * 新增店铺参与秒杀活动的关联
     *
     * @param seckillSeceneStore 店铺参与秒杀活动的关联实体
     * @return 成功1 否则失败 -1 秒杀活动不存在 -2 秒杀活动不是未开始状态 -3 店铺已经参与过该秒杀活动
     */
    int addSeckillSeceneStore(SeckillSeceneStore seckillSeceneStore);

    /**
     * 查询店铺参与秒杀活动的关联
     *
     * @param seckillSceneId 秒杀场次的id
     * @param storeId        店铺id
     * @return 店铺参与秒杀活动的关联
     */
    SeckillSeceneStore querySeckillSeceneStore(long seckillSceneId, long storeId);

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
