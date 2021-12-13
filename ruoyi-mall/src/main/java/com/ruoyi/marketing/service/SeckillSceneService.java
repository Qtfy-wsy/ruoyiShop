package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.SeckillScene;
import com.ruoyi.marketing.domain.SeckillSceneDetail;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * Created by 伊甸园商城 on 2020/5/11.
 * 秒杀场次服务接口
 */
public interface SeckillSceneService {

    /**
     * 根据秒杀日期查询秒杀活动
     *
     * @param time 秒杀日期
     * @return 返回秒杀活动
     */
    List<SeckillScene> querySeckillSceneByTime(String time);

    /**
     * 修改秒杀活动
     *
     * @param seckillScenes 秒杀活动
     * @param seckillTime   秒杀时间
     * @return 成功返回1 失败返回0 -1秒杀场次不能超过12场
     */
    int updateSeckillScenes(List<SeckillScene> seckillScenes, String seckillTime);


    /**
     * 分页查询秒杀活动列表
     *
     * @param type        类型 1 即将开始 2 进行中 3 已结束
     * @param seckillTime 秒杀时间
     * @param storeId     店铺id
     * @return 返回秒杀活动列表
     */
    PageHelper<SeckillScene> querySeckillScenes(PageHelper<SeckillScene> pageHelper, String type, String seckillTime, long storeId);

    /**
     * 根据抢购活动开始时间查询秒杀场次
     *
     * @param startTime 抢购活动开始时间
     * @return 返回秒杀活动
     */
    SeckillScene querySeckillSceneByStartTime(String startTime);

    /**
     * 根据id查询秒杀场次
     *
     * @param seckillSceneId 秒杀场次id
     * @return 返回秒杀场次
     */
    SeckillScene querySeckillSceneById(long seckillSceneId);

    /**
     * 查询当前时间的5场秒杀活动详情
     *
     * @return 返回当前时间的5场秒杀活动详情
     */
    SeckillSceneDetail querySeckillSceneDetail();

}
