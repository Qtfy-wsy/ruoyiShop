package com.ruoyi.marketing.service.impl;

import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.*;
import com.ruoyi.marketing.mapper.PanicBuyMapper;
import com.ruoyi.marketing.service.PanicBuyService;
import com.ruoyi.marketing.service.SeckillScenePanicbuyService;
import com.ruoyi.marketing.service.SeckillSceneService;
import com.ruoyi.marketing.service.SeckillSeceneStoreService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 17/6/8.
 * 抢购服务接口实现
 */
@Service("panicBuyService")
public class PanicBuyServiceImpl extends MarketingTemplate implements PanicBuyService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PanicBuyServiceImpl.class);

    /**
     * 注入抢购数据库接口
     */
    @Autowired
    private PanicBuyMapper panicBuyMapper;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入秒杀场次服务接口
     */
    @Autowired
    private SeckillSceneService seckillSceneService;

    /**
     * 注入秒杀场次折扣服务接口
     */
    @Autowired
    private SeckillScenePanicbuyService seckillScenePanicbuyService;

    /**
     * 注入店铺参与秒杀活动的关联服务接口
     */
    @Autowired
    private SeckillSeceneStoreService seckillSeceneStoreService;


    @Transactional
    @Override
    public int addMarketingDetail(Marketing marketing) {

        logger.debug("addMarketingDetail and marketing:{}", marketing);

        if (!validateParams(marketing)) {
            logger.error("addMarketingDetail fail due to param is error....");
            return 0;
        }

        // 新增抢购详情表
        addPanicBuyList(marketing.getPanicBuyList(), marketing);

        // 新增秒杀场次折扣表
        addSeckillScenePanicbuy(marketing.getPanicBuyList(), marketing);

        return 1;
    }

    @Transactional
    @Override
    public int updateMarketingDetail(Marketing marketing, Marketing oldMarketing) {
        logger.debug("updateMarketingDetail and marketing:{}", marketing);

        if (!validateParams(marketing)) {
            logger.error("updateMarketingDetail fail due to params is error...");
            return 0;
        }

        if (Objects.isNull(oldMarketing)) {
            logger.error("updateMarketingDetail fail due to oldMarketing is null...");
            return 0;
        }

        // 只有未开始的活动能修改
        if (LocalDateTime.now().compareTo(oldMarketing.getStartTime()) >= 0) {
            logger.error("updatePanci fail due to marketing has been start");
            return -1;
        }

        // 获取原有的需要修改抢购列表
        List<PanicBuy> updatePanicBuyList = marketing.getPanicBuyList().stream().filter(PanicBuy::hasId).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(updatePanicBuyList)) {

            // 遍历修改抢购详情表
            updatePanicBuyList.forEach(panicBuy -> panicBuyMapper.updatePanicBuy(panicBuy));

        }

        // 获取原有的抢购列表
        Map<String, Object> map = new HashMap<>();
        map.put("marketingId", marketing.getId());
        List<PanicBuy> originalPanicBuyList = panicBuyMapper.queryByMarketingId(map);

        // 获取原有的需要修改抢购id列表
        List<Long> needUpdatePanicBuyIdList = updatePanicBuyList.stream().map(PanicBuy::getId).collect(Collectors.toList());

        // 获取需要删除的抢购id列表（原有的抢购id列表和原有的需要修改抢购id列表差集）
        List<Long> needDeletePanicBuyIdList = originalPanicBuyList.stream().map(PanicBuy::getId).collect(Collectors.toList());
        needDeletePanicBuyIdList.removeAll(needUpdatePanicBuyIdList);

        // 删除抢购促销详情
        if (!CollectionUtils.isEmpty(needDeletePanicBuyIdList)) {
            Map<String, Object> params = new HashMap<>();
            params.put("marketingId", marketing.getId());
            params.put("panicBuyIdList", needDeletePanicBuyIdList);
            panicBuyMapper.deletePanicBuyByIds(params);
        }

        // 获取新增加的抢购列表
        List<PanicBuy> addPanicBuyList = marketing.getPanicBuyList().stream().filter(PanicBuy::notHasId).collect(Collectors.toList());

        // 新增抢购详情表
        if (!CollectionUtils.isEmpty(addPanicBuyList)) {
            addPanicBuyList(addPanicBuyList, marketing);
        }

        // 判断抢购促销开始时间是否变化
        if (oldMarketing.getStartTime().compareTo(marketing.getStartTime()) == 0) {

            // 抢购促销开始时间没有变化


            // 抢购开始时间是整点
            if (marketing.startTimeIsHourly()) {

                // 删除秒杀场次折扣
                if (!CollectionUtils.isEmpty(needDeletePanicBuyIdList)) {
                    seckillScenePanicbuyService.deleteSeckillScenePanicbuysByPanicBuyIds(needDeletePanicBuyIdList);
                }

                // 新增秒杀场次折扣
                if (!CollectionUtils.isEmpty(addPanicBuyList)) {
                    addSeckillScenePanicbuy(addPanicBuyList, marketing);
                }

            }

        } else {

            // 抢购促销开始时间有变化

            // 删除秒杀场次折扣
            List<Long> marketingIds = new ArrayList<>();
            marketingIds.add(marketing.getId());
            seckillScenePanicbuyService.deleteSeckillScenePanicbuysByMarketingIds(marketingIds);

            // 新增秒杀场次折扣
            if (!CollectionUtils.isEmpty(updatePanicBuyList)) {
                addSeckillScenePanicbuy(updatePanicBuyList, marketing);
            }
            if (!CollectionUtils.isEmpty(addPanicBuyList)) {
                addSeckillScenePanicbuy(addPanicBuyList, marketing);
            }

        }

        return 1;
    }

    @Override
    public void setMarketingDetail(Marketing marketing) {
       //

        if (Objects.isNull(marketing) || !marketing.isPanicBuyMarketing()) {

            logger.error("setMarketingDetail fail due to params is error....");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("marketingId", marketing.getId());
        if (Objects.nonNull(marketing.getSku())) {
            params.put("skuId", marketing.getSku().getId());
            List<PanicBuy> panicBuyList = panicBuyMapper.queryByMarketingId(params);
            if (!CollectionUtils.isEmpty(panicBuyList)) {
                marketing.setPanicBuy(panicBuyList.get(0));
            }
        } else {
            // 多条记录
            List<PanicBuy> panicBuyList = panicBuyMapper.queryByMarketingId(params);
            if (!CollectionUtils.isEmpty(panicBuyList)) {
                marketing.setPanicBuyList(panicBuyList.stream().peek(panicBuy -> panicBuy.setSku(skuService.querySkuByIdWithSpecs(panicBuy.getSkuId(), marketing.getStoreId()))).collect(Collectors.toList()));
            }
        }

    }

    @Override
    public PageHelper<PanicBuy> queryStorePanicBuyList(PageHelper<PanicBuy> pageHelper, long storeId) {
        logger.debug("queryStorePanicBuyList and pageHelper :{} \r\n storeId :{}", pageHelper, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        return pageHelper.setListDates(panicBuyMapper.queryStorePanicBuyList(pageHelper.getQueryParams(params, panicBuyMapper.queryStorePanicBuyCount(params)))
                .stream().peek(this::setSkuDetail).collect(Collectors.toList()));
    }

    @Override
    public int updatePanicBuyShow(long panicBuyId, String isShow, Integer sort, long storeId) {
        logger.debug("updatePanicBuyShow and panicBuyId :{} \r\n isShow :{} \r\n sort :{} \r\n storeId", panicBuyId, isShow, sort, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("panicBuyId", panicBuyId);
        params.put("isShow", isShow);
        params.put("storeId", storeId);
        params.put("sort", sort);
        return panicBuyMapper.updatePanicBuyShow(params);
    }

    @Transactional
    @Override
    public int batchUpdatePanicBuyShow(Long[] panicBuyIds, String isShow, long storeId) {
        logger.debug("batchUpdatePanicBuyShow and panicBuyIds :{} \r\n isShow :{} \r\n storeId", panicBuyIds, isShow, storeId);
        if (ArrayUtils.isEmpty(panicBuyIds)) {
            logger.error("batchUpdatePanicBuyShow fail due to panicBuyIds is empty");
            return -1;
        }
        Arrays.stream(panicBuyIds).forEach(panicBuyId -> updatePanicBuyShow(panicBuyId, isShow, null, storeId));
        return 1;
    }


    /**
     * 设置单品信息
     *
     * @param panicBuy 抢购实体
     */
    private void setSkuDetail(PanicBuy panicBuy) {
        // 设置单品信息
        PmsSku sku = skuService.querySkuByIdWithSpecs(panicBuy.getSkuId(), panicBuy.getStoreId());
        if (Objects.nonNull(sku)) {
            panicBuy.setSku(sku);
        }
    }

    @Override
    public List<PanicBuy> queryPanicBuysByTime(String time, long storeId) {
        logger.debug("queryPanicBuysByTime and time:{} \r\n storeId :{}", time, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("time", time);
        params.put("storeId", storeId);
        return panicBuyMapper.queryPanicBuysByTime(params);
    }


    @Override
    public PanicBuy queryById(long id) {
        logger.debug("queryById and id:{}", id);
        return panicBuyMapper.queryById(id);
    }

    @Override
    public PageHelper queryStorePanicBuyListForSite(PageHelper pageHelper, long storeId) {
        logger.debug("queryStorePanicBuyListForSite and pageHelper :{} \r\n storeId :{}", pageHelper, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        return pageHelper.setListDates(panicBuyMapper.queryStorePanicBuyListForSite(pageHelper.getQueryParams(params, panicBuyMapper.queryStorePanicBuyCountForSite(params))));
    }


    /**
     * 新增抢购促销详情表
     *
     * @param panicBuyList 抢购促销列表
     * @param marketing    促销实体
     */
    private void addPanicBuyList(List<PanicBuy> panicBuyList, Marketing marketing) {
        logger.debug("addPanicBuyList and panicBuyList :{} \r\n marketing :{}", panicBuyList, marketing);

        if (CollectionUtils.isEmpty(panicBuyList)) {
            logger.info("addPanicBuyList fail due to panicBuyList is empty");
            return;
        }

        // 遍历新增抢购详情表
        panicBuyList.forEach(panicBuy -> {

            // 设置店铺id
            panicBuy.setStoreId(marketing.getStoreId());

            // 新增抢购详情表
            panicBuyMapper.addPanicBuy(panicBuy);

        });

    }

    /**
     * 新增秒杀场次折扣表
     *
     * @param panicBuyList 抢购促销列表
     * @param marketing    促销实体
     */
    private void addSeckillScenePanicbuy(List<PanicBuy> panicBuyList, Marketing marketing) {
        logger.debug("addSeckillScenePanicbuy and panicBuyList :{} \r\n marketing :{}", panicBuyList, marketing);

        if (CollectionUtils.isEmpty(panicBuyList)) {
            logger.info("addPanicBuyList fail due to panicBuyList is empty");
            return;
        }

        // 判断抢购开始时间是整点
        if (!marketing.startTimeIsHourly()) {

            // 不是整点直接返回
            logger.info("addSeckillScenePanicbuys fail due to marketing startTime is not startTimeIsHourly");
            return;
        }

        // 根据抢购开始时间查询秒杀场次是否存在

        SeckillScene seckillScene = seckillSceneService.querySeckillSceneByStartTime(marketing.getStartTime().toString());

        if (Objects.isNull(seckillScene)) {

            // 该抢购开始时间不在秒杀场次，直接返回
            logger.info("addSeckillScenePanicbuys fail due to seckillScene is not exist");
            return;
        }

        if (CommonConstant.ADMIN_STOREID == marketing.getStoreId()) {

            // 新增来自平台

            // 构建秒杀场次折扣实体列表
            List<SeckillScenePanicbuy> seckillScenePanicbuyList = panicBuyList.stream().map(panicBuy -> SeckillScenePanicbuy.build(panicBuy, seckillScene)).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(seckillScenePanicbuyList)) {

                // 新增秒杀场次折扣
                logger.info("addSeckillScenePanicbuys success form admin");
                seckillScenePanicbuyService.addSeckillScenePanicbuys(seckillScenePanicbuyList);
            }

        } else {

            // 新增来自店铺

            // 查询店铺是否参与该秒杀场次
            SeckillSeceneStore seckillSeceneStore = seckillSeceneStoreService.querySeckillSeceneStore(seckillScene.getId(), marketing.getStoreId());

            if (Objects.isNull(seckillSeceneStore)) {

                // 店铺没有参与该秒杀场次，直接返回
                logger.info("addSeckillScenePanicbuys fail due to store do not attend this seckillSeceneStore");
                return;
            }

            // 构建秒杀场次折扣实体列表
            List<SeckillScenePanicbuy> seckillScenePanicbuyList = panicBuyList.stream().map(panicBuy -> SeckillScenePanicbuy.build(panicBuy, seckillScene)).filter(Objects::nonNull).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(seckillScenePanicbuyList)) {

                // 新增秒杀场次折扣
                logger.info("addSeckillScenePanicbuys success form store");
                seckillScenePanicbuyService.addSeckillScenePanicbuys(seckillScenePanicbuyList);
            }
        }
    }

    /**
     * 校验当前促销是否正确
     *
     * @param marketing 促销信息
     * @return 正确返回true 不正确返回false
     */
    private boolean validateParams(Marketing marketing) {
        return Objects.nonNull(marketing) && !CollectionUtils.isEmpty(marketing.getPanicBuyList()) && marketing.isPanicBuyMarketing();
    }
}
