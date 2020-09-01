package com.ruoyi.marketing.service.impl;


import com.ruoyi.marketing.domain.TryMarketing;
import com.ruoyi.marketing.service.TryMarketingService;
import com.ruoyi.marketing.service.TryMarketingServiceApi;
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
 * 试用促销聚合服务接口实现类
 *
 * @author 魔金商城 created on 2020/6/9
 */
@Service
@Slf4j
public class TryMarketingServiceApiImpl implements TryMarketingServiceApi {

    /**
     * 注入试用促销服务接口
     */
    @Autowired
    private TryMarketingService tryMarketingService;

    /**
     * 注入店铺信息服务接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;


    @Override
    public PageHelper<TryMarketing> queryTryMarketingList(PageHelper<TryMarketing> pageHelper, String name, String skuNo, long storeId) {
        log.debug("queryTryMarketingList and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}");
        return pageHelper.setListDates(tryMarketingService.queryTryMarketingList(pageHelper, name, skuNo, storeId).getList()
                .stream().peek(tryMarketing -> setOtherInfo(tryMarketing, storeId)).collect(Collectors.toList()));
    }


    /**
     * 设置列表其他信息
     *
     * @param tryMarketing 试用促销
     * @param storeId      店铺id
     */
    private void setOtherInfo(TryMarketing tryMarketing, long storeId) {
        log.debug("setOtherInfo and tryMarketing :{} \r\n storeId :{}", tryMarketing, storeId);

        if (Objects.isNull(tryMarketing)) {
            return;
        }

        // 平台查询，则设置店铺名称
        if (CommonConstant.ADMIN_STOREID == storeId) {

            if (CommonConstant.ADMIN_STOREID == tryMarketing.getStoreId()) {

                tryMarketing.setStoreName("平台自营");

            } else {

                // 设置店铺名称
                TStoreInfo storeInfo = storeInfoService.queryStoreInfo(tryMarketing.getStoreId());

                if (Objects.nonNull(storeInfo)) {
                    tryMarketing.setStoreName(storeInfo.getStoreName());
                }
            }

        }

    }

}
