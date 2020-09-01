package com.ruoyi.marketing.service.impl;

import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.SeckillScenePanicbuy;
import com.ruoyi.marketing.mapper.SeckillScenePanicbuyMapper;
import com.ruoyi.marketing.service.SeckillScenePanicbuyService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 魔金商城 on 2020/5/12.
 * 秒杀场次折扣服务接口实现
 */
@Slf4j
@Service
public class SeckillScenePanicbuyServiceImpl implements SeckillScenePanicbuyService {

    /**
     * 注入秒杀场次折扣
     */
    @Autowired
    private SeckillScenePanicbuyMapper seckillScenePanicbuyMapper;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;


    @Override
    public void addSeckillScenePanicbuys(List<SeckillScenePanicbuy> seckillScenePanicbuys) {
        log.debug("addSeckillScenePanicbuys and seckillScenePanicbuys:{}", seckillScenePanicbuys);

        if (CollectionUtils.isEmpty(seckillScenePanicbuys)) {
            log.warn("addSeckillScenePanicbuys fail due to seckillScenePanicbuys is empty");
            return;
        }
        seckillScenePanicbuyMapper.addSeckillScenePanicbuys(seckillScenePanicbuys);
    }

    @Override
    public void deleteSeckillScenePanicbuys(Map<String, Object> params) {
        log.debug("deleteSeckillScenePanicbuys and params:{}", params);

        seckillScenePanicbuyMapper.deleteSeckillScenePanicbuys(params);
    }


    @Override
    public int querySeckillScenePanicbuyCount(long seckillSceneId) {
        log.debug("querySeckillScenePanicbuyCount and seckillSceneId:{}", seckillSceneId);
        return seckillScenePanicbuyMapper.querySeckillScenePanicbuyCount(seckillSceneId);
    }

    @Override
    public void deleteSeckillScenePanicbuysByPanicBuyIds(List<Long> panicBuyIds) {
        log.debug("deleteSeckillScenePanicbuysByPanicBuyIds and panicBuyIds :{}", panicBuyIds);

        if (CollectionUtils.isEmpty(panicBuyIds)) {
            log.error("deleteSeckillScenePanicbuysByPanicBuyIds fail due to panicBuyIds is empty");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("panicBuyIds", panicBuyIds);
        seckillScenePanicbuyMapper.deleteSeckillScenePanicbuysByPanicBuyIds(params);
    }

    @Override
    public PageHelper<SeckillScenePanicbuy> querySeckillScenePanicbuyList(PageHelper<SeckillScenePanicbuy> pageHelper, long seckillSceneId, long storeId) {
        log.debug("querySeckillScenePanicbuyList and pageHelper :{} \r\n seckillSceneId :{} \r\n storeId :{}", pageHelper, seckillSceneId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("seckillSceneId", seckillSceneId);
        params.put("storeId", storeId);
        return pageHelper.setListDates(seckillScenePanicbuyMapper.querySeckillScenePanicbuyList(pageHelper.getQueryParams(params, seckillScenePanicbuyMapper.querySeckillScenePanicbuyListCount(params)))
                .stream().peek(this::setOtherInfo).collect(Collectors.toList()));
    }

    @Override
    public PageHelper querySeckillScenePanicbuyForPlatform(PageHelper pageHelper, String seckillTime) {
        log.debug("querySeckillScenePanicbuyForPlatform and pageHelper:{} r\n seckillTime:{}", pageHelper, seckillTime);
        Map<String, Object> params = new HashMap<>();
        params.put("seckillTime", seckillTime);
        return pageHelper.setListDates(seckillScenePanicbuyMapper.querySeckillScenePanicbuyForPlatform(pageHelper.getQueryParams(params, seckillScenePanicbuyMapper.querySeckillScenePanicbuyForPlatformCount(params))));
    }

    @Override
    public int updateSeckillScenePanicbuy(long id, String isShow, Integer sort) {
        log.debug("updateSeckillScenePanicbuy and id :{} \r\n isShow :{} \r\n sort :{}", id, isShow, sort);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("isShow", isShow);
        params.put("sort", sort);
        return seckillScenePanicbuyMapper.updateSeckillScenePanicbuy(params);
    }

    @Transactional
    @Override
    public int batchUpdateSeckillScenePanicbuy(Long[] ids, String isShow) {
        log.debug("batchUpdateSeckillScenePanicbuy and ids :{} \r\n isShow :{}", ids, isShow);
        if (ArrayUtils.isEmpty(ids)) {
            log.error("batchUpdateSeckillScenePanicbuy fail due to ids is empty");
            return -1;
        }
        Arrays.stream(ids).forEach(id -> updateSeckillScenePanicbuy(id, isShow, null));
        return 1;
    }

    @Override
    public void deleteSeckillScenePanicbuysByMarketingIds(List<Long> marketingIds) {
        log.debug("deleteSeckillScenePanicbuysByMarketingIds and marketingIds :{}", marketingIds);

        if (CollectionUtils.isEmpty(marketingIds)) {
            log.error("deleteSeckillScenePanicbuysByMarketingIds fail due to marketingIds is empty");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("marketingIds", marketingIds);
        seckillScenePanicbuyMapper.deleteSeckillScenePanicbuysByMarketingIds(params);
    }


    /**
     * 设置列表其他信息
     *
     * @param seckillScenePanicbuy 秒杀场次折扣
     */
    private void setOtherInfo(SeckillScenePanicbuy seckillScenePanicbuy) {
        log.debug("setOtherInfo and seckillScenePanicbuy :{}", seckillScenePanicbuy);

        if (Objects.isNull(seckillScenePanicbuy)) {
            return;
        }

        // 设置单品信息
        PmsSku sku = skuService.querySkuByIdWithSpecs(seckillScenePanicbuy.getSkuId(), CommonConstant.QUERY_WITH_NO_STORE);

        if (Objects.nonNull(sku)) {
            seckillScenePanicbuy.setSku(sku);
        }

    }

}
