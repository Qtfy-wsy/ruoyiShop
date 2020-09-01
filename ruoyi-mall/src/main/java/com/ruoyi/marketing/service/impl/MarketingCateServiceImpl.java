package com.ruoyi.marketing.service.impl;


import com.ruoyi.goods.domain.PmsBrand;
import com.ruoyi.marketing.domain.MarketingCate;
import com.ruoyi.marketing.mapper.MarketingCateMapper;
import com.ruoyi.marketing.service.GroupMarketingShowService;
import com.ruoyi.marketing.service.MarketingCateService;
import com.ruoyi.marketing.service.PreSaleShowService;
import com.ruoyi.marketing.service.TryMarketingShowService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 魔金商城 on 18/1/10.
 * 促销分类服务实现接口
 */
@Service
public class MarketingCateServiceImpl implements MarketingCateService {

    /**
     * 注入促销分类数据库接口
     */
    @Autowired
    private MarketingCateMapper marketingCateMapper;

    /**
     * 注入拼团活动服务接口
     */
    @Autowired
    private GroupMarketingShowService groupMarketingShowService;

    /**
     * 注入试用活动服务接口
     */
    @Autowired
    private TryMarketingShowService tryMarketingShowService;

    /**
     * 注入预售活动服务接口
     */
    @Autowired
    private PreSaleShowService preSaleShowService;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(MarketingCateServiceImpl.class);

    @Override
    public PageHelper<PmsBrand> queryMarketingCates(PageHelper<MarketingCate> pageHelper, String name, String type, long storeId) {
        logger.debug("queryMarketingCates and pageHelper :{} \r\n name : {} \r\n type : {} \r\n storeId:{}", pageHelper, name, type, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("type", type);
        params.put("storeId", storeId);
        return pageHelper.setListDates(marketingCateMapper.queryMarketingCates(pageHelper.getQueryParams(params, marketingCateMapper.queryMarketingCateCount(params))));
    }

    @Override
    public List<MarketingCate> queryMarketingCatesByType(String type) {
        logger.debug("queryMarketingCatesByType and type:{}", type);
        return marketingCateMapper.queryMarketingCatesByType(type);
    }

    @Override
    public MarketingCate queryMarketingCateById(long id, long storeId) {
        logger.debug("queryMarketingCateById and id {} \r\n storeId:{}", id, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        return marketingCateMapper.queryMarketingCateById(params);
    }

    @Override
    public int addMarketingCate(MarketingCate marketingcate) {

        if (Objects.isNull(marketingcate)) {
            logger.error("addMarketingCate fail due to marketingcate is null...");
            return 0;
        }

        logger.debug("addMarketingCate and marketingcate {}", marketingcate);

        return marketingCateMapper.addMarketingCate(marketingcate);
    }

    @Override
    public int updateMarketingCate(MarketingCate marketingcate) {
        logger.debug("updateMarketingCate and marketingcate {}", marketingcate);
        return marketingCateMapper.updateMarketingCate(marketingcate);
    }


    @Transactional
    @Override
    public int deleteMarketingCates(long[] ids, long storeId, String type) {
        logger.debug("deleteMarketingCates and ids : {} \r\n storeId:{} \r\n type :{}", ids, storeId, type);
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("deleteMarketingCates due to ids is empty....");
            return 0;
        }
        Arrays.stream(ids).forEach(id -> deleteMarketingCate(id, storeId, type));
        return 1;
    }

    @Override
    public List<MarketingCate> queryMarketingCatesByTypeAndStoreId(String type, long storeId) {
        logger.debug("queryMarketingCatesByTypeAndStoreId and type:{} \r\n storeId :{}", type, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("storeId", storeId);
        return marketingCateMapper.queryMarketingCatesByTypeAndStoreId(params);
    }

    /**
     * 删除促销分类
     *
     * @param id      促销分类id
     * @param storeId 店铺id
     * @return 成功返回1 失败返回0
     */
    private int deleteMarketingCate(long id, long storeId, String type) {
        logger.debug("deleteMarketingCate and id {} \r\n storeId:{} \r\n type :{}", id, storeId, type);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        marketingCateMapper.deleteMarketingCate(params);
        deleteOtherDetail(id, storeId, type);
        return 1;
    }

    /**
     * 删除关联的促销活动分类
     *
     * @param id      促销分类id
     * @param storeId 店铺id
     * @param type    促销分类类型
     */
    private void deleteOtherDetail(long id, long storeId, String type) {
        // 删除促销关联的相关信息
        switch (type) {
            case CommonConstant.PRESALE_MARKETING_CATE:
                // 预售
                preSaleShowService.deletePreSaleCate(id, storeId);
                break;
            case CommonConstant.GROUP_MARKETING_CATE:
                // 拼团促销
                groupMarketingShowService.deleteGroupCate(id, storeId);
                break;
            case CommonConstant.TRY_MARKETING_CATE:
                // 试用促销
                tryMarketingShowService.deleteTryCate(id, storeId);
                break;
            default:
                logger.error("no marketing...");
        }
    }

}
