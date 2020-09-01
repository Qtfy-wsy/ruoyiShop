package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsSkuBatch;
import com.ruoyi.goods.mapper.PmsSkuBatchMapper;
import com.ruoyi.goods.service.IPmsSkuBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 单品起批价格标Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsSkuBatchServiceImpl implements IPmsSkuBatchService {

    private static final Logger log = LoggerFactory.getLogger(PmsSkuBatchServiceImpl.class);

    @Autowired
    private PmsSkuBatchMapper pmsSkuBatchMapper;

    @Override
    public int addSkuBatch(List<PmsSkuBatch> skuBatchList) {
        log.debug("addSkuBatch and skuBatchList:{}", skuBatchList);
        if (CollectionUtils.isEmpty(skuBatchList)) {
            log.warn("not need to addSkuBatch: skuBatchList is empty");
            return 0;
        }
        return pmsSkuBatchMapper.addSkuBatch(skuBatchList);
    }

    @Override
    public int deleteSkuBatchBySpuId(long spuId) {
        log.debug("deleteSkuBatchBySpuId and spuId:{}", spuId);
        return pmsSkuBatchMapper.deleteSkuBatchBySpuId(spuId);
    }


    @Override
    public int deleteSkuBatchBySpuIdPhysical(long spuId) {
        log.debug("deleteSkuBatchBySpuIdPhysical and spuId:{}", spuId);
        return pmsSkuBatchMapper.deleteSkuBatchBySpuIdPhysical(spuId);
    }


    @Override
    public List<PmsSkuBatch> querySkuBatchBySkuId(String skuId) {
        log.debug("querySkuBatchBySkuId and skuId:{}", skuId);
        return pmsSkuBatchMapper.querySkuBatchBySkuId(skuId);
    }

    @Override
    public int querySkuBatchCountBySkuIds(List<String> skuIds) {
        log.debug("querySkuBatchCountBySkuIds and skuIds:{}", skuIds);
        if (CollectionUtils.isEmpty(skuIds)) {
            log.warn("querySkuBatchCountBySkuIds and skuId is empty");
            return 0;
        }
        return pmsSkuBatchMapper.querySkuBatchCountBySkuIds(skuIds);
    }

    /**
     * 查询单品起批价格标
     *
     * @param id 单品起批价格标ID
     * @return 单品起批价格标
     */
    @Override
    public PmsSkuBatch selectPmsSkuBatchById(Long id) {
        return pmsSkuBatchMapper.selectPmsSkuBatchById(id);
    }

    /**
     * 查询单品起批价格标列表
     *
     * @param pmsSkuBatch 单品起批价格标
     * @return 单品起批价格标
     */
    @Override
    public List<PmsSkuBatch> selectPmsSkuBatchList(PmsSkuBatch pmsSkuBatch) {
        return pmsSkuBatchMapper.selectPmsSkuBatchList(pmsSkuBatch);
    }

    /**
     * 新增单品起批价格标
     *
     * @param pmsSkuBatch 单品起批价格标
     * @return 结果
     */
    @Override
    public int insertPmsSkuBatch(PmsSkuBatch pmsSkuBatch) {
        return pmsSkuBatchMapper.insertPmsSkuBatch(pmsSkuBatch);
    }

    /**
     * 修改单品起批价格标
     *
     * @param pmsSkuBatch 单品起批价格标
     * @return 结果
     */
    @Override
    public int updatePmsSkuBatch(PmsSkuBatch pmsSkuBatch) {
        return pmsSkuBatchMapper.updatePmsSkuBatch(pmsSkuBatch);
    }

    /**
     * 批量删除单品起批价格标
     *
     * @param ids 需要删除的单品起批价格标ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuBatchByIds(Long[] ids) {
        return pmsSkuBatchMapper.deletePmsSkuBatchByIds(ids);
    }

    /**
     * 删除单品起批价格标信息
     *
     * @param id 单品起批价格标ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuBatchById(Long id) {
        return pmsSkuBatchMapper.deletePmsSkuBatchById(id);
    }
}
