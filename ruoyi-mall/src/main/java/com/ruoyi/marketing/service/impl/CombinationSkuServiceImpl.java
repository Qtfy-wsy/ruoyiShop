package com.ruoyi.marketing.service.impl;


import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.CombinationSku;
import com.ruoyi.marketing.mapper.CombinationSkuMapper;
import com.ruoyi.marketing.service.CombinationSkuService;
import com.ruoyi.marketing.service.MarketingQueryService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品组合下的单品service实现类
 * <p>
 * Created by 伊甸园商城 on 2017/6/13.
 */
@Service
public class CombinationSkuServiceImpl implements CombinationSkuService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(CombinationSkuServiceImpl.class);

    /**
     * 自动注入商品组合与单品关联数据库接口
     */
    @Autowired
    private CombinationSkuMapper combinationSkuMapper;
    /**
     * 注入促销查询服务
     */
    @Autowired
    private MarketingQueryService marketingQueryService;

    /**
     * 注入单品服务
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 分页查询商品组合下的商品
     *
     * @param pageHelper 分页帮助类
     * @param id         商品组合id
     * @param storeId    店铺id
     * @return 商品组合下的所有单品
     */
    @Override
    public PageHelper<CombinationSku> querySkuOfGoodCom(PageHelper<CombinationSku> pageHelper, long id, long storeId) {
        logger.debug("querySkuOfGoodCom and id :{} \r\n storeId :{}", id, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        return pageHelper.setListDates(combinationSkuMapper.querySkuOfGoodCom(pageHelper.getQueryParams(params, combinationSkuMapper.querySkuCount(params)))
                .stream().peek(combinationSku -> {
                    combinationSku.getSku().setId(combinationSku.getSkuId());
                    skuService.setSkuDetail(combinationSku.getSku(), PmsSkuItem.SPEC);
                }).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public int addCombinationSkus(List<CombinationSku> combinationSkus) {
        logger.debug("addCombinationSkus and combinationSkus:{}", combinationSkus);
        if (org.springframework.util.CollectionUtils.isEmpty(combinationSkus)) {
            logger.error("addCombinationSkus fail due to combinationSkus is empty....");
            return 0;
        }
        combinationSkus.forEach(this::addCombinationSku);
        return 1;
    }

    /**
     * 批量删除单品
     *
     * @param ids           单品id数组
     * @param combinationId 商品组合id
     * @return 成功返回>=1，失败返回0
     */
    @Override
    public int batchDeleteCombiantionSKu(String[] ids, long combinationId) {
        logger.debug("batchDeleteCombiantionSKu and ids :{} \r\n combinationId :{}", combinationId, ids);
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("combinationId", combinationId);
        return combinationSkuMapper.batchDeleteCombiantionSKu(params);
    }

    @Override
    public List<String> queryByCombiantionId(long combinationId) {
        logger.debug("queryByCombiantionId and combinationId:{}", combinationId);
        List<CombinationSku> combinationSkus = combinationSkuMapper.queryByCombiantionId(combinationId);
        return CollectionUtils.isEmpty(combinationSkus) ? Collections.emptyList() : combinationSkus.stream().map(CombinationSku::getSkuId).collect(Collectors.toList());
    }


    /**
     * 为商品组合添加单品
     *
     * @param combinationSku 商品组合单品
     * @return 成功返回1，失败返回0
     */
    private int addCombinationSku(CombinationSku combinationSku) {
        logger.debug("addCombinationSku and combinationSku :{}", combinationSku);
        List<String> list = combinationSkuMapper.querySkuIdWithAdd(combinationSku);
        if (!CollectionUtils.isEmpty(list)) {
            if (list.stream().filter(id -> id.equals(combinationSku.getSkuId())).count() != 0) {
                return 0;
            }
        }
        if (marketingQueryService.queryExclusionMarketingCountBySkuIds(Collections.singletonList(combinationSku.getSkuId()), CommonConstant.COMBINATION_EXCLUSION) > 0) {
            logger.error("addCombinationSku fail:queryExclusionMarketingCountBySkuIds over 0 ");
            return -1;
        }
        return combinationSkuMapper.addCombinationSku(combinationSku);
    }
}
