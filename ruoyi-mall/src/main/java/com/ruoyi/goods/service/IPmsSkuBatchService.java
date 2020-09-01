package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsSkuBatch;

import java.util.List;

/**
 * 单品起批价格标Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsSkuBatchService {
    /**
     * 查询单品起批价格标
     *
     * @param id 单品起批价格标ID
     * @return 单品起批价格标
     */
    public PmsSkuBatch selectPmsSkuBatchById(Long id);

    /**
     * 查询单品起批价格标列表
     *
     * @param pmsSkuBatch 单品起批价格标
     * @return 单品起批价格标集合
     */
    public List<PmsSkuBatch> selectPmsSkuBatchList(PmsSkuBatch pmsSkuBatch);

    /**
     * 新增单品起批价格标
     *
     * @param pmsSkuBatch 单品起批价格标
     * @return 结果
     */
    public int insertPmsSkuBatch(PmsSkuBatch pmsSkuBatch);

    /**
     * 修改单品起批价格标
     *
     * @param pmsSkuBatch 单品起批价格标
     * @return 结果
     */
    public int updatePmsSkuBatch(PmsSkuBatch pmsSkuBatch);

    /**
     * 批量删除单品起批价格标
     *
     * @param ids 需要删除的单品起批价格标ID
     * @return 结果
     */
    public int deletePmsSkuBatchByIds(Long[] ids);

    /**
     * 删除单品起批价格标信息
     *
     * @param id 单品起批价格标ID
     * @return 结果
     */
    public int deletePmsSkuBatchById(Long id);

    /**
     * 添加单品批发规则
     *
     * @param skuBatchList 单品批发规则列表
     * @return 新增成功数量
     */
    int addSkuBatch(List<PmsSkuBatch> skuBatchList);

    /**
     * 根据spuId删除单品批发规则
     *
     * @param spuId 单品id
     * @return 删除成功数量
     */
    int deleteSkuBatchBySpuId(long spuId);


    /**
     * 根据spuId删除单品批发规则（物理删除）
     *
     * @param spuId 单品id
     * @return 删除成功数量
     */
    int deleteSkuBatchBySpuIdPhysical(long spuId);

    /**
     * 根据skuId查询单品批发规则
     *
     * @param skuId 单品id
     * @return 批发规则列表
     */
    List<PmsSkuBatch> querySkuBatchBySkuId(String skuId);


    /**
     * 根据单品id集合查询有批发规则的单品数量
     *
     * @param skuIds 单品id集合
     * @return 有批发规则的单品数量
     */
    int querySkuBatchCountBySkuIds(List<String> skuIds);
}
