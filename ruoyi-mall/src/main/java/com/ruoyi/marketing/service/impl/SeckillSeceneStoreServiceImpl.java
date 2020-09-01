package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.domain.PanicBuy;
import com.ruoyi.marketing.domain.SeckillScene;
import com.ruoyi.marketing.domain.SeckillScenePanicbuy;
import com.ruoyi.marketing.domain.SeckillSeceneStore;
import com.ruoyi.marketing.mapper.SeckillSeceneStoreMapper;
import com.ruoyi.marketing.service.MarketingService;
import com.ruoyi.marketing.service.SeckillScenePanicbuyService;
import com.ruoyi.marketing.service.SeckillSceneService;
import com.ruoyi.marketing.service.SeckillSeceneStoreService;
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
 * 店铺参与秒杀活动的关联服务接口实现类
 *
 * @author 魔金商城 created on 2020/5/14
 */
@Service
@Slf4j
public class SeckillSeceneStoreServiceImpl implements SeckillSeceneStoreService {

    /**
     * 店铺参与秒杀活动的关联数据库接口
     */
    @Autowired
    private SeckillSeceneStoreMapper seckillSeceneStoreMapper;

    /**
     * 注入秒杀场次服务接口
     */
    @Autowired
    private SeckillSceneService seckillSceneService;

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


    @Transactional
    @Override
    public int addSeckillSeceneStore(SeckillSeceneStore seckillSeceneStore) {
        log.debug("addSeckillSeceneStore and seckillSeceneStore :{}", seckillSeceneStore);

        if (Objects.isNull(seckillSeceneStore)) {
            log.error("addSeckillSeceneStore fail due to seckillSeceneStore is null");
            return -1;
        }

        // 查询秒杀场次活动
        SeckillScene seckillScene = seckillSceneService.querySeckillSceneById(seckillSeceneStore.getSeckillSceneId());

        if (Objects.isNull(seckillScene)) {
            log.error("addSeckillSeceneStore fail due to seckillScene is null");
            return -1;
        }

        // 判断活动是否开始，只有未开始的才能参与
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        if (LocalDateTime.parse(seckillScene.getSeckillTime().format(formatter), formatter).compareTo(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter)) <= 0) {
            log.error("addSeckillSeceneStore fail due to seckillScene is null");
            return -2;
        }

        // 判断店铺是否已经参与过该秒杀场次活动
        if (Objects.nonNull(querySeckillSeceneStore(seckillSeceneStore.getSeckillSceneId(), seckillSeceneStore.getStoreId()))) {
            log.error("addSeckillSeceneStore fail due to the store has been attend seckillSecene");
            return -3;
        }

        // 新增店铺参与秒杀活动的关联
        seckillSeceneStoreMapper.addSeckillSeceneStore(seckillSeceneStore);

        // 自动匹配时间相同的折扣 从sms_marketing_panicbuy表匹配
        List<PanicBuy> panicBuyList = panicBuyService.queryPanicBuysByTime(seckillScene.getSeckillTimeForPanicBuy(), seckillSeceneStore.getStoreId());

        // 构建秒杀场次折扣实体列表
        List<SeckillScenePanicbuy> seckillScenePanicbuyList = panicBuyList.stream().map(panicBuy -> SeckillScenePanicbuy.build(panicBuy, seckillScene)).filter(Objects::nonNull).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(seckillScenePanicbuyList)) {

            // 新增秒杀场次折扣
            log.info("addSeckillScenePanicbuys success form store");
            seckillScenePanicbuyService.addSeckillScenePanicbuys(seckillScenePanicbuyList);
        }

        return 1;
    }

    @Override
    public SeckillSeceneStore querySeckillSeceneStore(long seckillSceneId, long storeId) {
        log.debug("querySeckillSeceneStore and seckillSceneId :{} \r\n storeId :{}", seckillSceneId, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("seckillSceneId", seckillSceneId);
        params.put("storeId", storeId);

        return seckillSeceneStoreMapper.querySeckillSeceneStore(params);
    }

    @Override
    public int querySeckillSeceneStoreCount(long seckillSceneId) {
        log.debug("querySeckillSeceneStore and seckillSceneId :{}", seckillSceneId);

        return seckillSeceneStoreMapper.querySeckillSeceneStoreCount(seckillSceneId);
    }

    @Override
    public int deleteSeckillSeceneStore(long seckillSceneId) {
        log.debug("deleteSeckillSeceneStore and seckillSceneId :{}", seckillSceneId);
        return seckillSeceneStoreMapper.deleteSeckillSeceneStore(seckillSceneId);
    }

}
