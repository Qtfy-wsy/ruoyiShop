package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsSkuMarketing;

import java.util.List;

/**
 * 单品和营销的关联Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsSkuMarketingMapper {
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
     * 删除单品和营销的关联
     *
     * @param id 单品和营销的关联ID
     * @return 结果
     */
    public int deletePmsSkuMarketingById(Long id);

    /**
     * 批量删除单品和营销的关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSkuMarketingByIds(Long[] ids);
}
