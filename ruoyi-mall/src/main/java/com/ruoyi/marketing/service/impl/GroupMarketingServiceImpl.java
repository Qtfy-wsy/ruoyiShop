package com.ruoyi.marketing.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.GroupMarketing;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.mapper.GroupMarketingMapper;
import com.ruoyi.marketing.service.GroupMarketingService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 拼团促销接口实现
 */
@Service("groupMarketingService")
public class GroupMarketingServiceImpl extends MarketingTemplate implements GroupMarketingService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(GroupMarketingServiceImpl.class);


    /**
     * 注入拼团促销数据库接口
     */
    @Autowired
    private GroupMarketingMapper groupMarketingMapper;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;


    @Transactional
    @Override
    public int addMarketingDetail(Marketing marketing) {

        logger.debug("addMarketingDetail and marketing:{}", marketing);

        // 校验参数
        if (!validateParams(marketing)) {
            logger.error("addGroupMarketing fail deu to validate params fail....");
            return 0;
        }
        //校验拼团人数
        if (!marketing.getGroupMarketingList().stream().allMatch(GroupMarketing::checkGroupNum)) {
            logger.error("addGroupMarketing fail:checkGroupNum fail");
            throw new ServiceException("addGroupMarketing fail:checkGroupNum fail");
        }

        // 新增拼团促销详情表
        this.addGroupMarketingList(marketing.getGroupMarketingList());

        return 1;
    }

    @Transactional
    @Override
    public int updateMarketingDetail(Marketing marketing, Marketing oldMarketing) {
        logger.debug("updateMarketingDetail and marketing:{}", marketing);

        // 校验参数是否合法
        if (!validateParams(marketing)) {
            logger.error("updateMarketingDetail fail due to marketing is error...");
            return 0;
        }

        // 只有未开始的活动能修改
        if (LocalDateTime.now().compareTo(oldMarketing.getStartTime()) >= 0) {
            logger.error("updateMarketingDetail fail due to marketing has been start");
            return -1;
        }

        // 校验拼团人数
        if (!marketing.getGroupMarketingList().stream().allMatch(GroupMarketing::checkGroupNum)) {
            logger.error("updateMarketingDetail fail:checkGroupNum fail");
            throw new ServiceException();
        }

        // 获取原有的需要修改拼团列表
        List<GroupMarketing> updateGroupMarketingList = marketing.getGroupMarketingList().stream().filter(GroupMarketing::hasId).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(updateGroupMarketingList)) {

            // 遍历修改拼团详情表
            updateGroupMarketingList.forEach(groupMarketing -> groupMarketingMapper.updateGroupMarketing(groupMarketing));

        }

        // 获取原有的拼团列表
        Map<String, Object> map = new HashMap<>();
        map.put("marketingId", marketing.getId());
        List<GroupMarketing> originalGroupMarketingList = groupMarketingMapper.queryGroupMarketing(map);

        // 获取原有的需要修改拼团id列表
        List<Long> needUpdateGroupMarketingIdList = updateGroupMarketingList.stream().map(GroupMarketing::getId).collect(Collectors.toList());

        // 获取需要删除的拼团id列表（原有的拼团id列表和原有的需要修改拼团id列表差集）
        List<Long> needDeleteGroupMarketingIdList = originalGroupMarketingList.stream().map(GroupMarketing::getId).collect(Collectors.toList());
        needDeleteGroupMarketingIdList.removeAll(needUpdateGroupMarketingIdList);

        // 删除拼团促销详情
        if (!CollectionUtils.isEmpty(needDeleteGroupMarketingIdList)) {
            Map<String, Object> params = new HashMap<>();
            params.put("marketingId", marketing.getId());
            params.put("groupMarketingIdList", needDeleteGroupMarketingIdList);
            groupMarketingMapper.deleteGroupMarketingByIds(params);
        }

        // 获取新增加的拼团列表
        List<GroupMarketing> addGroupMarketingList = marketing.getGroupMarketingList().stream().filter(GroupMarketing::notHasId).collect(Collectors.toList());

        // 新增拼团详情表
        if (!CollectionUtils.isEmpty(addGroupMarketingList)) {
            addGroupMarketingList(addGroupMarketingList);
        }

        return 1;
    }


    @Override
    public void setMarketingDetail(Marketing marketing) {


        if (Objects.isNull(marketing) || !marketing.isGroupMarketingType()) {
            logger.error("setMarketingDetail fail ...due to marketing is error....");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("marketingId", marketing.getId());
        if (Objects.nonNull(marketing.getSku())) {
            params.put("skuId", marketing.getSku().getId());
        }

        List<GroupMarketing> groupMarketings = groupMarketingMapper.queryGroupMarketing(params);

        if (!CollectionUtils.isEmpty(groupMarketings)) {
            marketing.setGroupMarketing(groupMarketings.get(0));
            marketing.setGroupMarketingList(groupMarketings.stream().peek(groupMarketing -> groupMarketing.setSku(skuService.querySkuByIdWithSpecs(groupMarketing.getSkuId(), marketing.getStoreId()))).collect(Collectors.toList()));
        }

    }

    @Override
    public PageHelper<GroupMarketing> queryGroupMarketingList(PageHelper<GroupMarketing> pageHelper, String name, String skuNo, long storeId) {
        logger.debug("queryGroupMarketingList and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}", pageHelper, name, skuNo, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("skuNo", skuNo);
        params.put("storeId", storeId);
        return pageHelper.setListDates(groupMarketingMapper.queryGroupMarketingList(pageHelper.getQueryParams(params, groupMarketingMapper.queryGroupMarketingListCount(params)))
                .stream().peek(groupMarketing -> setOtherInfo(groupMarketing, storeId)).collect(Collectors.toList()));
    }

    @Override
    public GroupMarketing queryGroupMarketingById(long groupId, long storeId) {
        logger.debug("queryGroupMarketingById and groupId :{] \r\n storeId :{}", groupId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", groupId);
        params.put("storeId", storeId);
        return groupMarketingMapper.queryGroupMarketingById(params);
    }

    /**
     * 设置其他信息
     *
     * @param groupMarketing 拼团实体
     */
    private void setOtherInfo(GroupMarketing groupMarketing, long storeId) {
        logger.debug("setOtherInfo and groupMarketing :{} \r\n storeId :{}", groupMarketing, storeId);

        if (Objects.isNull(groupMarketing)) {
            logger.error("setOtherInfo fail due to groupMarketing is null");
            return;
        }

        // 设置单品信息
        PmsSku sku = skuService.querySkuByIdWithSpecs(groupMarketing.getSkuId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);
        if (Objects.nonNull(sku)) {
            groupMarketing.setSku(sku);
        }
    }

    /**
     * 新增拼团促销详情表
     *
     * @param groupMarketingList 拼团促销列表
     */
    private void addGroupMarketingList(List<GroupMarketing> groupMarketingList) {
        logger.debug("addGroupMarketingList and groupMarketingList :{}", groupMarketingList);

        if (CollectionUtils.isEmpty(groupMarketingList)) {
            logger.info("addGroupMarketingList fail due to groupMarketingList is empty");
            return;
        }

        // 遍历新增拼团详情表
        groupMarketingList.forEach(groupMarketing -> {

            // 新增拼团详情表
            groupMarketingMapper.addGroupMarketing(groupMarketing);

        });

    }

    /**
     * 校验当前促销是否正确
     *
     * @param marketing 促销信息
     * @return 正确返回 true 失败返回false
     */
    private boolean validateParams(Marketing marketing) {
        return Objects.nonNull(marketing) && !CollectionUtils.isEmpty(marketing.getGroupMarketingList()) && marketing.isGroupMarketingType();
    }

}
