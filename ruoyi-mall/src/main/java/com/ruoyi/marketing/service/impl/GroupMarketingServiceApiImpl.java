package com.ruoyi.marketing.service.impl;


import com.ruoyi.marketing.domain.GroupMarketing;
import com.ruoyi.marketing.service.GroupMarketingService;
import com.ruoyi.marketing.service.GroupMarketingServiceApi;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 拼团促销聚合服务接口实现类
 *
 * @author 魔金商城 created on 2020/6/1
 */
@Service
@Slf4j
public class GroupMarketingServiceApiImpl implements GroupMarketingServiceApi {

    /**
     * 注入拼团促销服务接口
     */
    @Autowired
    private GroupMarketingService groupMarketingService;

    /**
     * 注入店铺信息服务接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;


    @Override
    public PageHelper<GroupMarketing> queryGroupMarketingList(PageHelper<GroupMarketing> pageHelper, String name, String skuNo, long storeId) {
        log.debug("queryGroupMarketingList and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}");
        return pageHelper.setListDates(groupMarketingService.queryGroupMarketingList(pageHelper, name, skuNo, storeId).getList()
                .stream().peek(groupMarketing -> setOtherInfo(groupMarketing, storeId)).collect(Collectors.toList()));
    }


    /**
     * 设置列表其他信息
     *
     * @param groupMarketing 拼团促销
     * @param storeId        店铺id
     */
    private void setOtherInfo(GroupMarketing groupMarketing, long storeId) {
        log.debug("setOtherInfo and groupMarketing :{} \r\n storeId :{}", groupMarketing, storeId);

        if (Objects.isNull(groupMarketing)) {
            return;
        }

        // 平台查询，则设置店铺名称
        if (CommonConstant.ADMIN_STOREID == storeId) {

            if (CommonConstant.ADMIN_STOREID == groupMarketing.getStoreId()) {

                groupMarketing.setStoreName("平台自营");

            } else {

                // 设置店铺名称
                TStoreInfo storeInfo = storeInfoService.queryStoreInfo(groupMarketing.getStoreId());

                if (Objects.nonNull(storeInfo)) {
                    groupMarketing.setStoreName(storeInfo.getStoreName());
                }
            }

        }

    }

}
