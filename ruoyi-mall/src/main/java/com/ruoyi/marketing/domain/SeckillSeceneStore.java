package com.ruoyi.marketing.domain;

import lombok.Data;

/**
 * Created by 魔金商城 on 2020/5/13.
 * 店铺参与的秒杀场次
 */
@Data
public class SeckillSeceneStore {

    /**
     * 主键id
     */
    private long id;

    /**
     * 秒杀场次的id 对应sms_seckill_scene表中的id
     */
    private long seckillSceneId;

    /**
     * 店铺id
     */
    private long storeId;

    /**
     * 删除标记 0 未删除 1 已删除
     */
    private int delFlag;


    /**
     * 构建店铺参与的秒杀场次
     *
     * @param seckillSceneId 秒杀场次的id
     * @param storeId        店铺id
     * @return 当前实体
     */
    public static SeckillSeceneStore buildSeckillSeceneStore(long seckillSceneId, long storeId) {
        SeckillSeceneStore seckillSeceneStore = new SeckillSeceneStore();
        seckillSeceneStore.seckillSceneId = seckillSceneId;
        seckillSeceneStore.storeId = storeId;
        seckillSeceneStore.delFlag = 0;
        return seckillSeceneStore;
    }

}
