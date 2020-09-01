package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.domain.GoodsCombination;
import com.ruoyi.marketing.mapper.GoodsCombinationMapper;
import com.ruoyi.marketing.service.CombinationSkuService;
import com.ruoyi.marketing.service.GoodsCombinationService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商品组合service实现类
 * <p>
 * Created by 魔金商城 on 2017/6/12.
 */
@Service
public class GoodsCombinationServiceImpl implements GoodsCombinationService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(GoodsCombinationServiceImpl.class);

    /**
     * 自动注入商品组合数据库接口
     */
    @Autowired
    private GoodsCombinationMapper goodsCombinationMapper;

    /**
     * 注入组合单品服务接口
     */
    @Autowired
    private CombinationSkuService combinationSkuService;


    /**
     * 分页查询商品组合
     *
     * @param pageHelper 查询参数
     * @param name       商品组合名称
     * @param storeId    店铺id
     * @return 返回商品组合数据
     */
    @Override
    public PageHelper<GoodsCombination> queryGoodsCombination(PageHelper<GoodsCombination> pageHelper, String name, long storeId) {
        logger.debug("queryGoodsCombination and pageHelper :{} \r\n name :{} \r\n storeId :{}", pageHelper, name, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("storeId", storeId);
        return pageHelper.setListDates(goodsCombinationMapper.queryGoodsCombination(pageHelper.getQueryParams(params, goodsCombinationMapper.queryGoodsCombinationCount(params))));
    }

    /**
     * 添加商品组合
     *
     * @param goodsCombination 商品组合
     * @return 成功返回1，失败返回0
     */
    @Override
    public int addGoodsCombination(GoodsCombination goodsCombination) {
        logger.debug("addGoodsCombination and goodsCombination :{}", goodsCombination);

        return goodsCombinationMapper.addGoodsCombination(goodsCombination);
    }

    /**
     * 批量删除商品组合
     *
     * @param ids     商品组合id数组
     * @param storeId 店铺id
     * @return 成功返回>=1，失败返回0
     */
    @Transactional
    @Override
    public int batchDeleteGoodsCombination(long[] ids, long storeId) {
        logger.debug("batchDeleteGoodsCombination and ids :{}", ids);
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("storeId", storeId);
        return goodsCombinationMapper.batchDeleteGoodsCombination(params);
    }

    /**
     * 通过id查找商品组合
     *
     * @param id      商品组合id
     * @param storeId 店铺id
     * @return 商品组合
     */
    @Override
    public GoodsCombination queryGoodsCombinationById(long id, long storeId) {
        logger.debug("queryGoodsCombinationById and id :{}", id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        return goodsCombinationMapper.queryGoodsCombinationById(params);
    }

    /**
     * 修改商品组合
     *
     * @param goodsCombination 商品组合
     * @return 成功返回1，失败返回0
     */
    @Override
    public int updateGoodsCombination(GoodsCombination goodsCombination) {
        logger.debug("updateGoodsCombination and goodsCombination :{}", goodsCombination);
        return goodsCombinationMapper.updateGoodsCombination(goodsCombination);
    }


    @Override
    public GoodsCombination querySkusBySku(String skuId) {
        logger.debug("querySkusBySku and skuId :{}", skuId);

        if (StringUtils.isEmpty(skuId)) {
            logger.error("querySkusBySku fail due to skuId is empty....");
            return null;
        }

        // 查询单品对应的组合信息
        GoodsCombination goodsCombination = goodsCombinationMapper.queryBySkuId(skuId);

        // 单品没有组合信息直接返回
        if (Objects.isNull(goodsCombination)) {
            logger.info("sku :{} has no GoodsCombination...", skuId);
            return goodsCombination;
        }

        // 单品有组合信息 则查询该组合下的所有单品信息
        goodsCombination.setSkuIds(combinationSkuService.queryByCombiantionId(goodsCombination.getId()));

        return goodsCombination;
    }

    @Override
    public int queryGoodsCombinationCountBySkuIds(List<String> skuIds) {
        logger.debug("queryGoodsCombinationCountBySkuIds and skuIds:{}", skuIds);
        return goodsCombinationMapper.queryGoodsCombinationCountBySkuIds(skuIds);
    }
}
