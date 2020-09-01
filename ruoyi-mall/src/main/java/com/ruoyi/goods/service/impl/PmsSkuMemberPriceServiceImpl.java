package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsSkuMemberPrice;
import com.ruoyi.goods.mapper.PmsSkuMemberPriceMapper;
import com.ruoyi.goods.service.IPmsSkuMemberPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 单品的会员价Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsSkuMemberPriceServiceImpl implements IPmsSkuMemberPriceService {
    @Autowired
    private PmsSkuMemberPriceMapper pmsSkuMemberPriceMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsSkuMemberPriceServiceImpl.class);

    /**
     * 注入单品会员价数据库接口
     */
    @Autowired
    private PmsSkuMemberPriceMapper skuMemberPriceMapper;

    @Override
    public void addSkuMemberPrices(List<PmsSkuMemberPrice> skuMemberPrices) {

        logger.debug("addSkuMemberPrices and skuMemberPrices:{}", skuMemberPrices);

        if (CollectionUtils.isEmpty(skuMemberPrices)) {
            logger.warn("do not need to addSkuMemberPrices");
            return;
        }

        skuMemberPriceMapper.addSkuMemberPrices(skuMemberPrices);
    }

    @Override
    public List<PmsSkuMemberPrice> queryBySkuId(String skuId) {
        logger.debug("queryBySkuId and skuId:{}", skuId);
        return skuMemberPriceMapper.queryBySkuId(skuId);
    }

    @Override
    public void deleteBySpuId(long spuId) {
        logger.debug("deleteBySpuId and spuId:{}", spuId);

        skuMemberPriceMapper.deleteBySpuId(spuId);
    }

    @Override
    public void deleteBySpuIdPhysical(long spuId) {
        logger.debug("deleteBySpuIdPhysical and spuId:{}", spuId);

        skuMemberPriceMapper.deleteBySpuIdPhysical(spuId);
    }

    @Override
    public void deleteByLevelId(long customerLevelId) {
        logger.debug("deleteByLevelId and customerLevelId:{}", customerLevelId);

        skuMemberPriceMapper.deleteByLevelId(customerLevelId);
    }

    @Override
    public int querySkuMemberPriceCountBySkuIds(List<String> skuIds) {
        logger.debug("querySkuMemberPriceCountBySkuIds and skuIds:{}", skuIds);
        return skuMemberPriceMapper.querySkuMemberPriceCountBySkuIds(skuIds);
    }

    /**
     * 查询单品的会员价
     *
     * @param id 单品的会员价ID
     * @return 单品的会员价
     */
    @Override
    public PmsSkuMemberPrice selectPmsSkuMemberPriceById(Long id) {
        return pmsSkuMemberPriceMapper.selectPmsSkuMemberPriceById(id);
    }

    /**
     * 查询单品的会员价列表
     *
     * @param pmsSkuMemberPrice 单品的会员价
     * @return 单品的会员价
     */
    @Override
    public List<PmsSkuMemberPrice> selectPmsSkuMemberPriceList(PmsSkuMemberPrice pmsSkuMemberPrice) {
        return pmsSkuMemberPriceMapper.selectPmsSkuMemberPriceList(pmsSkuMemberPrice);
    }

    /**
     * 新增单品的会员价
     *
     * @param pmsSkuMemberPrice 单品的会员价
     * @return 结果
     */
    @Override
    public int insertPmsSkuMemberPrice(PmsSkuMemberPrice pmsSkuMemberPrice) {
        return pmsSkuMemberPriceMapper.insertPmsSkuMemberPrice(pmsSkuMemberPrice);
    }

    /**
     * 修改单品的会员价
     *
     * @param pmsSkuMemberPrice 单品的会员价
     * @return 结果
     */
    @Override
    public int updatePmsSkuMemberPrice(PmsSkuMemberPrice pmsSkuMemberPrice) {
        return pmsSkuMemberPriceMapper.updatePmsSkuMemberPrice(pmsSkuMemberPrice);
    }

    /**
     * 批量删除单品的会员价
     *
     * @param ids 需要删除的单品的会员价ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuMemberPriceByIds(Long[] ids) {
        return pmsSkuMemberPriceMapper.deletePmsSkuMemberPriceByIds(ids);
    }

    /**
     * 删除单品的会员价信息
     *
     * @param id 单品的会员价ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuMemberPriceById(Long id) {
        return pmsSkuMemberPriceMapper.deletePmsSkuMemberPriceById(id);
    }
}
