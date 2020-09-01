package com.ruoyi.marketing.service.impl;


import com.ruoyi.marketing.domain.PreSaleShow;
import com.ruoyi.marketing.service.PreSaleShowService;
import com.ruoyi.marketing.service.PreSaleShowServiceApi;
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
 * 预售活动聚合服务接口实现类
 *
 * @author 魔金商城 created on 2020/6/12
 */
@Service
@Slf4j
public class PreSaleShowServiceApiImpl implements PreSaleShowServiceApi {

    /**
     * 注入预售活动服务接口
     */
    @Autowired
    private PreSaleShowService preSaleShowService;

    /**
     * 注入店铺信息服务接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;


    @Override
    public PageHelper<PreSaleShow> queryPreSaleShowList(PageHelper<PreSaleShow> pageHelper, String name, String skuNo, long storeId) {
        log.debug("queryPreSaleShowList and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}");

        return pageHelper.setListDates(preSaleShowService.queryPreSaleShowList(pageHelper, name, skuNo, storeId).getList()
                .stream().peek(preSaleShow -> setOtherInfo(preSaleShow, storeId)).collect(Collectors.toList()));
    }


    /**
     * 设置列表其他信息
     *
     * @param preSaleShow 预售活动
     * @param storeId     店铺id
     */
    private void setOtherInfo(PreSaleShow preSaleShow, long storeId) {
        log.debug("setOtherInfo and preSaleShow :{} \r\n storeId :{}", preSaleShow, storeId);

        if (Objects.isNull(preSaleShow)) {
            return;
        }

        // 平台查询，则设置店铺名称
        if (CommonConstant.ADMIN_STOREID == storeId) {

            if (CommonConstant.ADMIN_STOREID == preSaleShow.getOldStoreId()) {

                preSaleShow.setStoreName("平台自营");

            } else {

                // 设置店铺名称
                TStoreInfo storeInfo = storeInfoService.queryStoreInfo(preSaleShow.getOldStoreId());

                if (Objects.nonNull(storeInfo)) {
                    preSaleShow.setStoreName(storeInfo.getStoreName());
                }
            }

        }

    }

}
