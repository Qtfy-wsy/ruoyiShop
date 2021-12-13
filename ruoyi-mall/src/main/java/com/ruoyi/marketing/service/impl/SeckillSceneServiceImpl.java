package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.domain.*;
import com.ruoyi.marketing.mapper.SeckillSceneMapper;
import com.ruoyi.marketing.service.*;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 2020/5/11.
 * 秒杀场次服务接口实现
 */
@Slf4j
@Service
public class SeckillSceneServiceImpl implements SeckillSceneService {

    /**
     * 注入秒杀场次数据库接口
     */
    @Autowired
    private SeckillSceneMapper seckillSceneMapper;

    /**
     * 注入抢购服务接口
     */
    @Resource(name = "panicBuyService")
    private MarketingService panicBuyService;

    /**
     * 注入秒杀场次折扣服务接口
     */
    @Autowired
    private SeckillScenePanicbuyService seckillScenePanicbuyService;

    /**
     * 注入店铺参与的秒杀场次服务接口
     */
    @Autowired
    private SeckillSeceneStoreService seckillSeceneStoreService;

    /**
     * 注入促销图片服务接口
     */
    @Autowired
    private MarketingPicService marketingPicService;

    @Override
    public List<SeckillScene> querySeckillSceneByTime(String time) {
        log.debug("querySeckillSceneByTime and time:{}", time);

        List<SeckillScene> list = seckillSceneMapper.queryByTime(time);

        if (CollectionUtils.isEmpty(list)) {
            return list;
        }

        //  设置场次下参与的秒杀商品数量
        list.stream().forEach(seckillScene -> seckillScene.setSkuNum(seckillScenePanicbuyService.querySeckillScenePanicbuyCount(seckillScene.getId())));

        return list;
    }

    @Transactional
    @Override
    public int updateSeckillScenes(List<SeckillScene> seckillScenes, String seckillTime) {
        log.debug("updateSeckillScenes and seckillScenes:{} \r\n seckillTime", seckillScenes, seckillTime);

        if (!CollectionUtils.isEmpty(seckillScenes) && seckillScenes.size() > 12) {
            log.error("Scene can not > 12........");
            return -1;
        }


        Map<String, Object> params = new HashMap<>();

        params.put("seckillTime", seckillTime);

        // 不需要删除的秒杀场次id
        List<Long> noNeedDeleteIDs = seckillScenes.stream().filter(SeckillScene::isNoNeedDeleteSeckillScene).map(SeckillScene::getId).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(noNeedDeleteIDs)) {
            params.put("ids", noNeedDeleteIDs);
        }

        // 删除秒杀场次
        seckillSceneMapper.deleteSeckillScene(params);

        // 删除秒杀场次折扣
        seckillScenePanicbuyService.deleteSeckillScenePanicbuys(params);

        // 需要新增的秒杀场次
        List<SeckillScene> newSeckillScenes = seckillScenes.stream().filter(SeckillScene::isAddSeckillScene).collect(Collectors.toList());

        // 新增秒杀场次
        addSeckillScenes(newSeckillScenes);

        return 1;
    }

    @Override
    public PageHelper<SeckillScene> querySeckillScenes(PageHelper<SeckillScene> pageHelper, String type, String seckillTime, long storeId) {
        log.debug("querySeckillScenes  and pageHelper:{} \r\n type:{} \r\n seckillTime:{} \r\n storeId :{}", pageHelper, type, seckillTime, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("seckillTime", seckillTime);

        return pageHelper.setListDates(setStoreAndSkuNum(seckillSceneMapper.querySeckillScenes(pageHelper.getQueryParams(params, seckillSceneMapper.querySeckillScenesCount(params))), storeId));
    }

    @Override
    public SeckillScene querySeckillSceneByStartTime(String startTime) {
        log.debug("querySeckillSceneByStartTime and startTime:{}", startTime);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        return seckillSceneMapper.queryByStartTime(params);
    }

    @Override
    public SeckillScene querySeckillSceneById(long seckillSceneId) {
        log.debug("querySeckillSceneById and seckillSceneId :{}", seckillSceneId);
        return seckillSceneMapper.querySeckillSceneById(seckillSceneId);
    }

    @Override
    public SeckillSceneDetail querySeckillSceneDetail() {
        log.debug("querySeckillSceneDetail....");

        SeckillSceneDetail seckillSceneDetail = new SeckillSceneDetail();

        // 秒杀促销图片
        MarketingPic marketingPic = marketingPicService.queryMarketingPic(CommonConstant.PANIC_MARKETING_PIC_TYPE, CommonConstant.ADMIN_STOREID);

        if (Objects.nonNull(marketingPic)) {
            seckillSceneDetail.setPcPic(marketingPic.getPcPic());
            seckillSceneDetail.setMobilePic(marketingPic.getMobilePic());
        }

        // 当前时间的5场秒杀活动
        List<SeckillScene> seckillScenes = this.queryCurrentSeckillScenes();

        if (!CollectionUtils.isEmpty(seckillScenes)) {
            seckillSceneDetail.setSecenes(seckillScenes.stream().map(SeckillSceneVo::build).collect(Collectors.toList()));
        }

        return seckillSceneDetail;
    }

    /**
     * 查询当前时间的5场秒杀活动
     *
     * @return 返回当前时间的5场秒杀活动
     */
    private List<SeckillScene> queryCurrentSeckillScenes() {
        log.debug("queryCurrentSeckillScenes ......");
        // 今天的所有的秒杀场次
        List<SeckillScene> seckillScenes = this.querySeckillSceneByTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        log.info("queryCurrentSeckillScenes and seckillScenes :{}", seckillScenes);

        if (CollectionUtils.isEmpty(seckillScenes)) {
            log.warn("today has no SeckillScene...");
            return null;
        }

        // 当前的时
        int hour = LocalDateTime.now().getHour();

        //当天有效的场次
        List<SeckillScene> effectiveSeckillScene = seckillScenes.stream().filter(seckillScene -> seckillScene.noExpire(hour)).collect(Collectors.toList());

        // 如果当天有效的场次为空 则直接返回
        if (CollectionUtils.isEmpty(effectiveSeckillScene)) {
            log.warn("today has no effectiveSeckillScene");
            return effectiveSeckillScene;
        }

        // 如果有效场次<=5个则直接返回
        if (effectiveSeckillScene.size() <= 5) {
            return effectiveSeckillScene;
        }

        // 如果>5个则截取前面的5个返回
        return effectiveSeckillScene.subList(0, 5);
    }

    /**
     * 设置秒杀活动的商家数量和商品数量
     *
     * @param seckillScenes 秒杀活动
     * @param storeId       店铺id
     * @return 返回秒杀活动
     */
    private List<SeckillScene> setStoreAndSkuNum(List<SeckillScene> seckillScenes, long storeId) {

        log.debug("setStoreAndSkuNum and seckillScenes:{} \r\n storeId :{}", seckillScenes, storeId);

        if (CollectionUtils.isEmpty(seckillScenes)) {
            log.warn("setStoreAndSkuNum fail due to seckillScenes is empty....");
            return seckillScenes;
        }

        seckillScenes.stream().forEach(seckillScene -> {
            seckillScene.setStoreNum(1 + seckillSeceneStoreService.querySeckillSeceneStoreCount(seckillScene.getId()));
            seckillScene.setEndTime();
            seckillScene.setSkuNum(seckillScenePanicbuyService.querySeckillScenePanicbuyCount(seckillScene.getId()));
        });

        // 店铺需要设置是否可以参与秒杀活动，未开始且店铺没有参与过的秒杀活动才能参加
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        if (storeId != CommonConstant.ADMIN_STOREID) {
            seckillScenes.stream().forEach(seckillScene -> {
                seckillScene.setIfCanAttend(Objects.isNull(seckillSeceneStoreService.querySeckillSeceneStore(seckillScene.getId(), storeId)) && LocalDateTime.parse(seckillScene.getSeckillTime().format(formatter), formatter).compareTo(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter)) > 0);
            });
        }

        return seckillScenes;
    }

    /**
     * 新增秒杀场次
     *
     * @param seckillScenes 秒杀场次
     */
    private void addSeckillScenes(List<SeckillScene> seckillScenes) {
        log.debug("addSeckillScenes and seckillScenes:{}", seckillScenes);

        if (CollectionUtils.isEmpty(seckillScenes)) {
            log.warn("no need to addSeckillScenes due to seckillScenes is empty....");
            return;
        }

        seckillScenes.stream().forEach(this::addSeckillScene);
    }

    /**
     * 新增秒杀场次
     *
     * @param seckillScene 秒杀场次
     */
    private void addSeckillScene(SeckillScene seckillScene) {
        log.debug("addSeckillScene and seckillScene:{} \r\n seckillTime:{}", seckillScene);

        if (Objects.isNull(seckillScene)) {
            log.error("addSeckillScene fail due to seckillScene is null...");
            return;
        }

        // 设置完整的秒杀场次时间 年月日时
        seckillScene.setFullSeckillTime();

        // 新增秒杀场次
        seckillSceneMapper.addSeckillScene(seckillScene);

        // 自动匹配时间相同的折扣 从sms_marketing_panicbuy表匹配
        List<PanicBuy> panicBuys = panicBuyService.queryPanicBuysByTime(seckillScene.getSeckillTimeForPanicBuy(), CommonConstant.ADMIN_STOREID);

        if (CollectionUtils.isEmpty(panicBuys)) {
            log.info("there is no panicBuys suit seckillTime:{}", seckillScene.getSeckillTimeForPanicBuy());
            return;
        }

        // 符合秒杀场次 时间的折扣活动
        List<SeckillScenePanicbuy> seckillScenePanicbuys = panicBuys.stream().map(panicBuy -> SeckillScenePanicbuy.build(panicBuy, seckillScene)).filter(Objects::nonNull).collect(Collectors.toList());

        // 增加秒杀场次折扣
        seckillScenePanicbuyService.addSeckillScenePanicbuys(seckillScenePanicbuys);

    }
}
