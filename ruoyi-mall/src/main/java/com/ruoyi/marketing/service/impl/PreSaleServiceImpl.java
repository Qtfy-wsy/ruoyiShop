package com.ruoyi.marketing.service.impl;

import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.PreSale;
import com.ruoyi.marketing.mapper.PreSaleMapper;
import com.ruoyi.marketing.service.PreSaleService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 预售促销接口实现
 *
 * @author 伊甸园商城 created on 2020/6/12
 */
@Service("preSaleService")
public class PreSaleServiceImpl implements PreSaleService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PreSaleServiceImpl.class);


    /**
     * 注入预售促销数据库接口
     */
    @Autowired
    private PreSaleMapper preSaleMapper;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;


    @Override
    public PageHelper<PreSale> queryPreSaleList(PageHelper<PreSale> pageHelper, String name, String skuNo, long storeId) {
        logger.debug("queryPreSaleList and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}", pageHelper, name, skuNo, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("skuNo", skuNo);
        params.put("storeId", storeId);
        return pageHelper.setListDates(preSaleMapper.queryPreSaleList(pageHelper.getQueryParams(params, preSaleMapper.queryPreSaleListCount(params)))
                .stream().peek(preSale -> setOtherInfo(preSale, storeId)).collect(Collectors.toList()));
    }

    @Override
    public PreSale queryPreSaleById(long preSaleId, long storeId) {
        logger.debug("queryPreSaleById and preSaleId :{] \r\n storeId :{}", preSaleId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", preSaleId);
        params.put("storeId", storeId);
        return preSaleMapper.queryPreSaleById(params);
    }

    /**
     * 设置其他信息
     *
     * @param preSale 预售实体
     */
    private void setOtherInfo(PreSale preSale, long storeId) {
        logger.debug("setOtherInfo and preSale :{} \r\n storeId :{}", preSale, storeId);

        if (Objects.isNull(preSale)) {
            logger.error("setOtherInfo fail due to preSale is null");
            return;
        }

        // 设置单品信息
        PmsSku sku = skuService.querySkuByIdWithSpecs(preSale.getSkuId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);
        if (Objects.nonNull(sku)) {
            preSale.setSku(sku);
        }
    }

}
