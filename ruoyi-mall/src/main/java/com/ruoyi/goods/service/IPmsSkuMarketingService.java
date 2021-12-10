package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsSkuMarketing;

import java.util.List;

/**
 * 单品和营销的关联Service接口
 *
 * @author 商城
 */
public interface IPmsSkuMarketingService {
    /**
     * 查询单品和营销的关联
     *
     * @param id 单品和营销的关联ID
     * @return 单品和营销的关联
     */
    public PmsSkuMarketing selectPmsSkuMarketingById(Long id);

    /**
     * 查询单品和营销的关联列表
     *
     * @param pmsSkuMarketing 单品和营销的关联
     * @return 单品和营销的关联集合
     */
    public List<PmsSkuMarketing> selectPmsSkuMarketingList(PmsSkuMarketing pmsSkuMarketing);

    /**
     * 新增单品和营销的关联
     *
     * @param pmsSkuMarketing 单品和营销的关联
     * @return 结果
     */
    public int insertPmsSkuMarketing(PmsSkuMarketing pmsSkuMarketing);

    /**
     * 修改单品和营销的关联
     *
     * @param pmsSkuMarketing 单品和营销的关联
     * @return 结果
     */
    public int updatePmsSkuMarketing(PmsSkuMarketing pmsSkuMarketing);

    /**
     * 批量删除单品和营销的关联
     *
     * @param ids 需要删除的单品和营销的关联ID
     * @return 结果
     */
    public int deletePmsSkuMarketingByIds(Long[] ids);

    /**
     * 删除单品和营销的关联信息
     *
     * @param id 单品和营销的关联ID
     * @return 结果
     */
    public int deletePmsSkuMarketingById(Long id);
}
