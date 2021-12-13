package com.ruoyi.marketing.service.impl;

import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.MarketingCate;
import com.ruoyi.marketing.domain.MarketingsSku;
import com.ruoyi.marketing.domain.TryMarketing;
import com.ruoyi.marketing.domain.TryMarketingShow;
import com.ruoyi.marketing.mapper.TryMarketingShowMapper;
import com.ruoyi.marketing.service.MarketingCateService;
import com.ruoyi.marketing.service.TryMarketingService;
import com.ruoyi.marketing.service.TryMarketingShowService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 试用活动服务接口实现类
 *
 * @author 伊甸园商城 created on 2020/5/28
 */
@Service
@Slf4j
public class TryMarketingShowServiceImpl implements TryMarketingShowService {

    /**
     * 注入试用活动数据库接口
     */
    @Autowired
    private TryMarketingShowMapper tryMarketingShowMapper;

    /**
     * 注入试用促销服务接口
     */
    @Autowired
    private TryMarketingService tryMarketingService;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入促销分类服务接口
     */
    @Autowired
    private MarketingCateService marketingCateService;


    @Override
    public int addTryMarketingShows(List<TryMarketingShow> tryMarketingShowList, long storeId) {
        log.debug("addTryMarketingShows and tryMarketingShowList :{} \r\n storeId :{}", tryMarketingShowList, storeId);

        if (CollectionUtils.isEmpty(tryMarketingShowList)) {
            log.error("addTryMarketingShows fail due to tryMarketingShowList is empty");
            return 0;
        }

        // 判断是否重复添加试用活动
        if (tryMarketingShowList.stream().anyMatch(tryMarketingShow -> queryTryMarketingShowCountByTryId(tryMarketingShow.getTryId(), storeId) > 0)) {
            log.error("addTryMarketingShows fail due to tryMarketingShow has been exist");
            return -1;
        }

        return tryMarketingShowMapper.addTryMarketingShows(tryMarketingShowList.stream().map(tryMarketingShow -> tryMarketingShow.addStoreId(storeId)).collect(Collectors.toList()));
    }

    @Override
    public int deleteTryMarketingShows(Long[] ids, long storeId) {
        log.debug("deleteTryMarketingShows and ids :{} \r\n storeId :{}", ids, storeId);

        if (ArrayUtils.isEmpty(ids)) {
            log.error("deleteTryMarketingShows fail due to ids is empty");
            return 0;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("ids", Arrays.asList(ids));
        params.put("storeId", storeId);
        return tryMarketingShowMapper.deleteTryMarketingShows(params);
    }

    @Override
    public int updateTryMarketingShow(long id, Long cateId, int sort, long storeId) {
        log.debug("updateTryMarketingShow and id :{} \r\n cateId :{} \r\n sort :{} \r\n storeId :{}", id, cateId, sort, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("cateId", cateId);
        params.put("sort", sort);
        params.put("storeId", storeId);
        return tryMarketingShowMapper.updateTryMarketingShow(params);
    }

    @Override
    public PageHelper<TryMarketingShow> queryTryMarketingShowList(PageHelper<TryMarketingShow> pageHelper, String name, String skuNo, long storeId) {
        log.debug("queryTryMarketingShowList and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}", pageHelper, name, skuNo, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("skuNo", skuNo);
        params.put("storeId", storeId);
        return pageHelper.setListDates(tryMarketingShowMapper.queryTryMarketingShowList(pageHelper.getQueryParams(params, tryMarketingShowMapper.queryTryMarketingShowListCount(params)))
                .stream().peek(tryMarketingShow -> setOtherInfo(tryMarketingShow, storeId)).collect(Collectors.toList()));
    }

    @Override
    public PageHelper<TryMarketingShow> queryTryMarketingShowListForStore(PageHelper<TryMarketingShow> pageHelper, String name, String skuNo, long storeId) {
        log.debug("queryTryMarketingShowListForStore and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}", pageHelper, name, skuNo, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("skuNo", skuNo);
        params.put("storeId", storeId);
        return pageHelper.setListDates(tryMarketingShowMapper.queryTryMarketingShowListForStore(pageHelper.getQueryParams(params, tryMarketingShowMapper.queryTryMarketingShowListCountForStore(params)))
                .stream().peek(this::setOther).collect(Collectors.toList()));
    }

    @Override
    public int deleteTryCate(long cateId, long storeId) {
        log.debug("deleteTryCate and cateId :{} \r\n storeId :{}", cateId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("cateId", cateId);
        params.put("storeId", storeId);
        return tryMarketingShowMapper.deleteTryCate(params);
    }

    @Override
    public PageHelper<MarketingsSku> queryTrysForSite(PageHelper<MarketingsSku> pageHelper, long cateId, long storeId, int type,String name) {
        log.debug("queryTrysForSite and pageHelper :{} \r\n cateId :{} \r\n storeId :{}", pageHelper, cateId, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("cateId", cateId);
        params.put("storeId", storeId);
        params.put("type", type);
        params.put("name",name);
        return pageHelper.setListDates(tryMarketingShowMapper.queryTrysForSite(pageHelper.getQueryParams(params, tryMarketingShowMapper.queryTrysCountForSite(params)))
                );
    }

    @Override
    public void deleteTrysByMarketingIds(List<Long> marketingIds) {
        log.debug("deleteTrysByMarketingIds and marketingIds :{}", marketingIds);

        if (CollectionUtils.isEmpty(marketingIds)) {
            log.error("deleteTrysByMarketingIds fail due to marketingIds is empty");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("marketingIds", marketingIds);
        tryMarketingShowMapper.deleteTrysByMarketingIds(params);
    }

    @Override
    public void autoDeleteEndTrys() {
        log.debug("autoDeleteEndTrys......");
        tryMarketingShowMapper.deleteEndTrys();
    }

    /**
     * 设置其他信息
     *
     * @param tryMarketingShow 试用活动实体
     * @param storeId          店铺id
     */
    private void setOtherInfo(TryMarketingShow tryMarketingShow, long storeId) {
        log.debug("setOtherInfo and tryMarketingShow :{} \r\n storeId :{}", tryMarketingShow, storeId);

        if (Objects.isNull(tryMarketingShow)) {
            log.error("setOtherInfo fail due to tryMarketingShow is null");
            return;
        }

        // 设置试用促销信息
        TryMarketing tryMarketing = tryMarketingService.queryTryMarketingById(tryMarketingShow.getTryId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);

        if (Objects.nonNull(tryMarketing)) {
            tryMarketing.convertJsonToObject("");
            tryMarketingShow.setTryMarketing(tryMarketing);
        }

        // 设置促销分类名称
        MarketingCate marketingCate = marketingCateService.queryMarketingCateById(tryMarketingShow.getCateId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);

        if (Objects.nonNull(marketingCate)) {
            tryMarketingShow.setMarketingCateName(marketingCate.getName());
        }


    }

    /**
     * 设置其他信息（店铺参与平台试用活动列表）
     *
     * @param tryMarketingShow 试用活动实体
     */
    private void setOther(TryMarketingShow tryMarketingShow) {
        log.debug("setOther and tryMarketingShow :{}", tryMarketingShow);

        if (Objects.isNull(tryMarketingShow)) {
            log.error("setOther fail due to tryMarketingShow is null");
            return;
        }

        // 设置试用促销信息
        TryMarketing tryMarketing = tryMarketingService.queryTryMarketingById(tryMarketingShow.getTryId(), CommonConstant.QUERY_WITH_NO_STORE);

        if (Objects.nonNull(tryMarketing)) {
            tryMarketing.convertJsonToObject("");
            tryMarketingShow.setTryMarketing(tryMarketing);
        }

        // 设置促销分类名称
        MarketingCate marketingCate = marketingCateService.queryMarketingCateById(tryMarketingShow.getCateId(), CommonConstant.QUERY_WITH_NO_STORE);

        if (Objects.nonNull(marketingCate)) {
            tryMarketingShow.setMarketingCateName(marketingCate.getName());
        }

    }

    /**
     * 设置其他信息（前端用）
     *
     * @param tryMarketingShow 试用活动实体
     * @param storeId          店铺id
     */
    private void setOtherInfoForSite(TryMarketingShow tryMarketingShow, long storeId) {
        log.debug("setOtherInfoForSite and tryMarketingShow :{} \r\n storeId :{}", tryMarketingShow, storeId);

        if (Objects.isNull(tryMarketingShow)) {
            log.error("setOtherInfoForSite fail due to tryMarketingShow is null");
            return;
        }

        // 设置试用促销信息
        TryMarketing tryMarketing = tryMarketingService.queryTryMarketingById(tryMarketingShow.getTryId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);

        if (Objects.nonNull(tryMarketing)) {

            tryMarketing.convertJsonToObject("");
            tryMarketingShow.setTryMarketing(tryMarketing);

        }

    }

    /**
     * 查询试用活动数量
     *
     * @param tryId   团购id
     * @param storeId 店铺id
     * @return 试用活动数量
     */
    private int queryTryMarketingShowCountByTryId(long tryId, long storeId) {
        log.debug("queryTryMarketingShowCountByTryId and tryId :{} \r\n storeId :{}", tryId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("tryId", tryId);
        params.put("storeId", storeId);
        return tryMarketingShowMapper.queryTryMarketingShowCountByTryId(params);
    }

}
