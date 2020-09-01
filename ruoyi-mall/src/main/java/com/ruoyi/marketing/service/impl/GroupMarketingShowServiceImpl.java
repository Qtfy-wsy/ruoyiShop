package com.ruoyi.marketing.service.impl;

import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.GroupMarketing;
import com.ruoyi.marketing.domain.GroupMarketingShow;
import com.ruoyi.marketing.domain.MarketingCate;
import com.ruoyi.marketing.mapper.GroupMarketingShowMapper;
import com.ruoyi.marketing.service.GroupMarketingService;
import com.ruoyi.marketing.service.GroupMarketingShowService;
import com.ruoyi.marketing.service.MarketingCateService;
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
 * 拼团活动服务接口实现类
 *
 * @author 魔金商城 created on 2020/5/28
 */
@Service
@Slf4j
public class GroupMarketingShowServiceImpl implements GroupMarketingShowService {

    /**
     * 注入拼团活动数据库接口
     */
    @Autowired
    private GroupMarketingShowMapper groupMarketingShowMapper;

    /**
     * 注入拼团促销服务接口
     */
    @Autowired
    private GroupMarketingService groupMarketingService;

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
    public int addGroupMarketingShows(List<GroupMarketingShow> groupMarketingShowList, long storeId) {
        log.debug("addGroupMarketingShows and groupMarketingShowList :{} \r\n storeId :{}", groupMarketingShowList, storeId);

        if (CollectionUtils.isEmpty(groupMarketingShowList)) {
            log.error("addGroupMarketingShows fail due to groupMarketingShowList is empty");
            return 0;
        }

        // 判断是否重复添加拼团活动
        if (groupMarketingShowList.stream().anyMatch(groupMarketingShow -> queryGroupMarketingShowCountByGroupId(groupMarketingShow.getGroupId(), storeId) > 0)) {
            log.error("addGroupMarketingShows fail due to groupMarketingShow has been exist");
            return -1;
        }

        return groupMarketingShowMapper.addGroupMarketingShows(groupMarketingShowList.stream().map(groupMarketingShow -> groupMarketingShow.addStoreId(storeId)).collect(Collectors.toList()));
    }

    @Override
    public int deleteGroupMarketingShows(Long[] ids, long storeId) {
        log.debug("deleteGroupMarketingShows and ids :{} \r\n storeId :{}", ids, storeId);

        if (ArrayUtils.isEmpty(ids)) {
            log.error("deleteGroupMarketingShows fail due to ids is empty");
            return 0;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("ids", Arrays.asList(ids));
        params.put("storeId", storeId);
        return groupMarketingShowMapper.deleteGroupMarketingShows(params);
    }

    @Override
    public int updateGroupMarketingShow(long id, Long cateId, int sort, long storeId) {
        log.debug("updateGroupMarketingShow and id :{} \r\n cateId :{} \r\n sort :{} \r\n storeId :{}", id, cateId, sort, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("cateId", cateId);
        params.put("sort", sort);
        params.put("storeId", storeId);
        return groupMarketingShowMapper.updateGroupMarketingShow(params);
    }

    @Override
    public PageHelper<GroupMarketingShow> queryGroupMarketingShowList(PageHelper<GroupMarketingShow> pageHelper, String name, String skuNo, long storeId) {
        log.debug("queryGroupMarketingShowList and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}", pageHelper, name, skuNo, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("skuNo", skuNo);
        params.put("storeId", storeId);
        return pageHelper.setListDates(groupMarketingShowMapper.queryGroupMarketingShowList(pageHelper.getQueryParams(params, groupMarketingShowMapper.queryGroupMarketingShowListCount(params)))
                .stream().peek(groupMarketingShow -> setOtherInfo(groupMarketingShow, storeId)).collect(Collectors.toList()));
    }

    @Override
    public PageHelper<GroupMarketingShow> queryGroupMarketingShowListForStore(PageHelper<GroupMarketingShow> pageHelper, String name, String skuNo, long storeId) {
        log.debug("queryGroupMarketingShowListForStore and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}", pageHelper, name, skuNo, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("skuNo", skuNo);
        params.put("storeId", storeId);
        return pageHelper.setListDates(groupMarketingShowMapper.queryGroupMarketingShowListForStore(pageHelper.getQueryParams(params, groupMarketingShowMapper.queryGroupMarketingShowListCountForStore(params)))
                .stream().peek(this::setOther).collect(Collectors.toList()));
    }

    @Override
    public int deleteGroupCate(long cateId, long storeId) {
        log.debug("deleteGroupCate and cateId :{} \r\n storeId :{}", cateId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("cateId", cateId);
        params.put("storeId", storeId);
        return groupMarketingShowMapper.deleteGroupCate(params);
    }

    @Override
    public PageHelper<GroupMarketingShow> queryGroupsForSite(PageHelper<GroupMarketingShow> pageHelper, long cateId, long storeId) {
        log.debug("queryGroupsForSite and pageHelper :{} \r\n cateId :{} \r\n storeId :{}", pageHelper, cateId, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("cateId", cateId);
        params.put("storeId", storeId);
        return pageHelper.setListDates(groupMarketingShowMapper.queryGroupsForSite(pageHelper.getQueryParams(params, groupMarketingShowMapper.queryGroupsCountForSite(params)))
                .stream().peek(groupMarketingShow -> setOtherInfoForSite(groupMarketingShow, storeId)).collect(Collectors.toList()));
    }

    @Override
    public void deleteGroupsByMarketingIds(List<Long> marketingIds) {
        log.debug("deleteGroupsByMarketingIds and marketingIds :{}", marketingIds);

        if (CollectionUtils.isEmpty(marketingIds)) {
            log.error("deleteGroupsByMarketingIds fail due to marketingIds is empty");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("marketingIds", marketingIds);
        groupMarketingShowMapper.deleteGroupsByMarketingIds(params);
    }

    @Override
    public void autoDeleteEndGroups() {
        log.debug("autoDeleteEndGroups......");
        groupMarketingShowMapper.deleteEndGroups();
    }

    /**
     * 设置其他信息
     *
     * @param groupMarketingShow 拼团活动实体
     * @param storeId            店铺id
     */
    private void setOtherInfo(GroupMarketingShow groupMarketingShow, long storeId) {
        log.debug("setOtherInfo and groupMarketingShow :{} \r\n storeId :{}", groupMarketingShow, storeId);

        if (Objects.isNull(groupMarketingShow)) {
            log.error("setOtherInfo fail due to groupMarketingShow is null");
            return;
        }

        // 设置拼团促销信息
        GroupMarketing groupMarketing = groupMarketingService.queryGroupMarketingById(groupMarketingShow.getGroupId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);

        if (Objects.nonNull(groupMarketing)) {
            groupMarketingShow.setGroupMarketing(groupMarketing);
        }

        // 设置单品信息
        PmsSku sku = skuService.querySkuByIdWithSpecs(groupMarketingShow.getSkuId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);

        if (Objects.nonNull(sku)) {
            groupMarketingShow.setSku(sku);
        }

        // 设置促销分类名称
        MarketingCate marketingCate = marketingCateService.queryMarketingCateById(groupMarketingShow.getCateId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);

        if (Objects.nonNull(marketingCate)) {
            groupMarketingShow.setMarketingCateName(marketingCate.getName());
        }


    }

    /**
     * 设置其他信息（店铺参与平台拼团活动列表）
     *
     * @param groupMarketingShow 拼团活动实体
     */
    private void setOther(GroupMarketingShow groupMarketingShow) {
        log.debug("setOther and groupMarketingShow :{}", groupMarketingShow);

        if (Objects.isNull(groupMarketingShow)) {
            log.error("setOther fail due to groupMarketingShow is null");
            return;
        }

        // 设置拼团促销信息
        GroupMarketing groupMarketing = groupMarketingService.queryGroupMarketingById(groupMarketingShow.getGroupId(), CommonConstant.QUERY_WITH_NO_STORE);

        if (Objects.nonNull(groupMarketing)) {
            groupMarketingShow.setGroupMarketing(groupMarketing);
        }

        // 设置单品信息
        PmsSku sku = skuService.querySkuByIdWithSpecs(groupMarketingShow.getSkuId(), CommonConstant.QUERY_WITH_NO_STORE);

        if (Objects.nonNull(sku)) {
            groupMarketingShow.setSku(sku);
        }

        // 设置促销分类名称
        MarketingCate marketingCate = marketingCateService.queryMarketingCateById(groupMarketingShow.getCateId(), CommonConstant.QUERY_WITH_NO_STORE);

        if (Objects.nonNull(marketingCate)) {
            groupMarketingShow.setMarketingCateName(marketingCate.getName());
        }

    }

    /**
     * 设置其他信息（前端用）
     *
     * @param groupMarketingShow 拼团活动实体
     * @param storeId            店铺id
     */
    private void setOtherInfoForSite(GroupMarketingShow groupMarketingShow, long storeId) {
        log.debug("setOtherInfoForSite and groupMarketingShow :{} \r\n storeId :{}", groupMarketingShow, storeId);

        if (Objects.isNull(groupMarketingShow)) {
            log.error("setOtherInfoForSite fail due to groupMarketingShow is null");
            return;
        }

        // 设置拼团促销信息
        GroupMarketing groupMarketing = groupMarketingService.queryGroupMarketingById(groupMarketingShow.getGroupId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);

        if (Objects.nonNull(groupMarketing)) {
            groupMarketingShow.setGroupMarketing(groupMarketing);

            // 设置单品信息
            PmsSku sku = skuService.querySkuByIdWithSpecs(groupMarketing.getSkuId(), CommonConstant.QUERY_WITH_NO_STORE);

            if (Objects.nonNull(sku)) {
                groupMarketingShow.setSku(sku);
            }

        }

    }

    /**
     * 查询拼团活动数量
     *
     * @param groupId 团购id
     * @param storeId 店铺id
     * @return 拼团活动数量
     */
    private int queryGroupMarketingShowCountByGroupId(long groupId, long storeId) {
        log.debug("queryGroupMarketingShowCountByGroupId and groupId :{} \r\n storeId :{}", groupId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("storeId", storeId);
        return groupMarketingShowMapper.queryGroupMarketingShowCountByGroupId(params);
    }

}
