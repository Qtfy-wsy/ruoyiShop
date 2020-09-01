package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.goods.service.SkuServiceApi;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 单品聚合服务接口实现类
 */
@Service
public class SkuServiceApiImpl implements SkuServiceApi {


    /**
     * 注入单品服务
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入店铺信息服务
     */
    @Autowired
    private ITStoreInfoService storeInfoService;

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(SkuServiceApiImpl.class);

    @Override
    public PageHelper<PmsSku> queryAllSkusWithSpecs(PageHelper<PmsSku> pageHelper, String name, String skuNo) {
        logger.debug("queryAllSkus and pageHelper:{} \r\n name:{} \r\n skuNo:{}", pageHelper, name, skuNo);
        pageHelper = skuService.querySkusWithSpecs(pageHelper, CommonConstant.QUERY_WITH_NO_STORE, name, skuNo);
        return pageHelper.setListDates(pageHelper.getList().parallelStream().map(sku -> {
            //查询店铺信息
            TStoreInfo storeInfo = storeInfoService.queryStoreInfo(sku.getStoreId());
            if (Objects.nonNull(storeInfo)) {
                sku.setStoreName(storeInfo.getStoreName());
            }
            return sku;
        }).collect(Collectors.toList()));
    }

    @Override
    public PmsSku querySkuByIdWithSpecs(String skuId) {
        PmsSku sku = skuService.querySkuByIdWithSpecs(skuId, CommonConstant.QUERY_WITH_NO_STORE);
        if (Objects.isNull(sku)) {
            return sku;
        }
        //查询店铺信息
        TStoreInfo storeInfo = storeInfoService.queryStoreInfo(sku.getStoreId());
        if (Objects.nonNull(storeInfo)) {
            sku.setStoreName(storeInfo.getStoreName());
        }
        return sku;
    }
}
