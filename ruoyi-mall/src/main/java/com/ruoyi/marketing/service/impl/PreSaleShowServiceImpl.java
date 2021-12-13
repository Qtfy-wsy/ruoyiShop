package com.ruoyi.marketing.service.impl;

import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsSkuImageService;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.MarketingCate;
import com.ruoyi.marketing.domain.PreSale;
import com.ruoyi.marketing.domain.PreSaleShow;
import com.ruoyi.marketing.mapper.PreSaleShowMapper;
import com.ruoyi.marketing.service.MarketingCateService;
import com.ruoyi.marketing.service.PreSaleService;
import com.ruoyi.marketing.service.PreSaleShowService;
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
 * 预售活动服务接口实现类
 *
 * @author 伊甸园商城 created on 2020/6/12
 */
@Service
@Slf4j
public class PreSaleShowServiceImpl implements PreSaleShowService {

    /**
     * 注入预售活动数据库接口
     */
    @Autowired
    private PreSaleShowMapper preSaleShowMapper;

    /**
     * 注入预售促销服务接口
     */
    @Autowired
    private PreSaleService preSaleService;

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

    /**
     * 注入单品图片服务接口
     */
    @Autowired
    private IPmsSkuImageService skuImageService;


    @Override
    public int addPreSaleShows(List<PreSaleShow> preSaleShowList, long storeId) {
        log.debug("addPreSaleShows and preSaleShowList :{} \r\n storeId :{}", preSaleShowList, storeId);

        if (CollectionUtils.isEmpty(preSaleShowList)) {
            log.error("addPreSaleShows fail due to preSaleShowList is empty");
            return 0;
        }

        // 判断是否重复添加预售活动
        if (preSaleShowList.stream().anyMatch(preSaleShow -> queryPreSaleShowCountByPreSaleId(preSaleShow.getPreSaleId(), storeId) > 0)) {
            log.error("addPreSaleShows fail due to preSaleShow has been exist");
            return -1;
        }

        return preSaleShowMapper.addPreSaleShows(preSaleShowList.stream().map(preSaleShow -> preSaleShow.addStoreId(storeId)).collect(Collectors.toList()));
    }

    @Override
    public int deletePreSaleShows(Long[] ids, long storeId) {
        log.debug("deletePreSaleShows and ids :{} \r\n storeId :{}", ids, storeId);

        if (ArrayUtils.isEmpty(ids)) {
            log.error("deletePreSaleShows fail due to ids is empty");
            return 0;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("ids", Arrays.asList(ids));
        params.put("storeId", storeId);
        return preSaleShowMapper.deletePreSaleShows(params);
    }

    @Override
    public int updatePreSaleShow(long id, Long cateId, int sort, long storeId) {
        log.debug("updatePreSaleShow and id :{} \r\n cateId :{} \r\n sort :{} \r\n storeId :{}", id, cateId, sort, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("cateId", cateId);
        params.put("sort", sort);
        params.put("storeId", storeId);
        return preSaleShowMapper.updatePreSaleShow(params);
    }

    @Override
    public PageHelper<PreSaleShow> queryPreSaleShowList(PageHelper<PreSaleShow> pageHelper, String name, String skuNo, long storeId) {
        log.debug("queryPreSaleShowList and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}", pageHelper, name, skuNo, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("skuNo", skuNo);
        params.put("storeId", storeId);
        return pageHelper.setListDates(preSaleShowMapper.queryPreSaleShowList(pageHelper.getQueryParams(params, preSaleShowMapper.queryPreSaleShowListCount(params)))
                .stream().peek(preSaleShow -> setOtherInfo(preSaleShow, storeId)).collect(Collectors.toList()));
    }

    @Override
    public PageHelper<PreSaleShow> queryPreSaleShowListForStore(PageHelper<PreSaleShow> pageHelper, String name, String skuNo, long storeId) {
        log.debug("queryPreSaleShowListForStore and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}", pageHelper, name, skuNo, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("skuNo", skuNo);
        params.put("storeId", storeId);
        return pageHelper.setListDates(preSaleShowMapper.queryPreSaleShowListForStore(pageHelper.getQueryParams(params, preSaleShowMapper.queryPreSaleShowListCountForStore(params)))
                .stream().peek(this::setOther).collect(Collectors.toList()));
    }

    @Override
    public int deletePreSaleCate(long cateId, long storeId) {
        log.debug("deletePreSaleCate and cateId :{} \r\n storeId :{}", cateId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("cateId", cateId);
        params.put("storeId", storeId);
        return preSaleShowMapper.deletePreSaleCate(params);
    }

    @Override
    public PageHelper<PreSaleShow> queryPreSalesForSite(PageHelper<PreSaleShow> pageHelper, long cateId, long storeId) {
        log.debug("queryPreSalesForSite and pageHelper :{} \r\n cateId :{} \r\n storeId :{}", pageHelper, cateId, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("cateId", cateId);
        params.put("storeId", storeId);
        return pageHelper.setListDates(preSaleShowMapper.queryPreSalesForSite(pageHelper.getQueryParams(params, preSaleShowMapper.queryPreSalesCountForSite(params)))
                .stream().peek(preSaleShow -> setOtherInfoForSite(preSaleShow, storeId)).collect(Collectors.toList()));
    }

    @Override
    public void deletePreSalesByMarketingIds(List<Long> marketingIds) {
        log.debug("deletePreSalesByMarketingIds and marketingIds :{}", marketingIds);

        if (CollectionUtils.isEmpty(marketingIds)) {
            log.error("deletePreSalesByMarketingIds fail due to marketingIds is empty");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("marketingIds", marketingIds);
        preSaleShowMapper.deletePreSalesByMarketingIds(params);
    }

    @Override
    public void autoDeleteEndPreSales() {
        log.debug("autoDeleteEndPreSales......");
        preSaleShowMapper.deleteEndPreSales();
    }

    /**
     * 设置其他信息
     *
     * @param preSaleShow 预售活动实体
     * @param storeId     店铺id
     */
    private void setOtherInfo(PreSaleShow preSaleShow, long storeId) {
        log.debug("setOtherInfo and preSaleShow :{} \r\n storeId :{}", preSaleShow, storeId);

        if (Objects.isNull(preSaleShow)) {
            log.error("setOtherInfo fail due to preSaleShow is null");
            return;
        }

        // 设置预售促销信息
        PreSale preSale = preSaleService.queryPreSaleById(preSaleShow.getPreSaleId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);

        if (Objects.nonNull(preSale)) {
            preSaleShow.setPreSale(preSale);
        }

        // 设置单品信息
        PmsSku sku = skuService.querySkuByIdWithSpecs(preSaleShow.getSkuId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);

        if (Objects.nonNull(sku)) {
            preSaleShow.setSku(sku);
        }

        // 设置促销分类名称
        MarketingCate marketingCate = marketingCateService.queryMarketingCateById(preSaleShow.getCateId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);

        if (Objects.nonNull(marketingCate)) {
            preSaleShow.setMarketingCateName(marketingCate.getName());
        }


    }

    /**
     * 设置其他信息（店铺参与平台预售活动列表）
     *
     * @param preSaleShow 预售活动实体
     */
    private void setOther(PreSaleShow preSaleShow) {
        log.debug("setOther and preSaleShow :{}", preSaleShow);

        if (Objects.isNull(preSaleShow)) {
            log.error("setOther fail due to preSaleShow is null");
            return;
        }

        // 设置预售促销信息
        PreSale preSale = preSaleService.queryPreSaleById(preSaleShow.getPreSaleId(), CommonConstant.QUERY_WITH_NO_STORE);

        if (Objects.nonNull(preSale)) {
            preSaleShow.setPreSale(preSale);
        }

        // 设置单品信息
        PmsSku sku = skuService.querySkuByIdWithSpecs(preSaleShow.getSkuId(), CommonConstant.QUERY_WITH_NO_STORE);

        if (Objects.nonNull(sku)) {
            preSaleShow.setSku(sku);
        }

        // 设置促销分类名称
        MarketingCate marketingCate = marketingCateService.queryMarketingCateById(preSaleShow.getCateId(), CommonConstant.QUERY_WITH_NO_STORE);

        if (Objects.nonNull(marketingCate)) {
            preSaleShow.setMarketingCateName(marketingCate.getName());
        }

    }

    /**
     * 设置其他信息（前端用）
     *
     * @param preSaleShow 预售活动实体
     * @param storeId     店铺id
     */
    private void setOtherInfoForSite(PreSaleShow preSaleShow, long storeId) {
        log.debug("setOtherInfoForSite and preSaleShow :{} \r\n storeId :{}", preSaleShow, storeId);

        if (Objects.isNull(preSaleShow)) {
            log.error("setOtherInfoForSite fail due to preSaleShow is null");
            return;
        }

        // 设置预售促销信息
        PreSale preSale = preSaleService.queryPreSaleById(preSaleShow.getPreSaleId(), CommonConstant.ADMIN_STOREID == storeId ? CommonConstant.QUERY_WITH_NO_STORE : storeId);

        if (Objects.nonNull(preSale)) {
            preSaleShow.setPreSale(preSale);

            // 设置单品信息
            PmsSku sku = skuService.querySkuByIdWithSpecs(preSale.getSkuId(), CommonConstant.QUERY_WITH_NO_STORE);

            if (Objects.nonNull(sku)) {

                // 设置单品图片
                sku.setSkuImages(skuImageService.queryBySkuId(sku.getId()));

                preSaleShow.setSku(sku);
            }

        }

    }

    /**
     * 查询预售活动数量
     *
     * @param preSaleId 团购id
     * @param storeId   店铺id
     * @return 预售活动数量
     */
    private int queryPreSaleShowCountByPreSaleId(long preSaleId, long storeId) {
        log.debug("queryPreSaleShowCountByPreSaleId and preSaleId :{} \r\n storeId :{}", preSaleId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("preSaleId", preSaleId);
        params.put("storeId", storeId);
        return preSaleShowMapper.queryPreSaleShowCountByPreSaleId(params);
    }

}
