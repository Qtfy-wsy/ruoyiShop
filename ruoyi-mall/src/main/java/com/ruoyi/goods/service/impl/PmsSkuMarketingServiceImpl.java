package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsSkuMarketing;
import com.ruoyi.goods.mapper.PmsSkuMarketingMapper;
import com.ruoyi.goods.service.IPmsSkuMarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 单品和营销的关联Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsSkuMarketingServiceImpl implements IPmsSkuMarketingService {
    @Autowired
    private PmsSkuMarketingMapper pmsSkuMarketingMapper;

    /**
     * 查询单品和营销的关联
     *
     * @param id 单品和营销的关联ID
     * @return 单品和营销的关联
     */
    @Override
    public PmsSkuMarketing selectPmsSkuMarketingById(Long id) {
        return pmsSkuMarketingMapper.selectPmsSkuMarketingById(id);
    }

    /**
     * 查询单品和营销的关联列表
     *
     * @param pmsSkuMarketing 单品和营销的关联
     * @return 单品和营销的关联
     */
    @Override
    public List<PmsSkuMarketing> selectPmsSkuMarketingList(PmsSkuMarketing pmsSkuMarketing) {
        return pmsSkuMarketingMapper.selectPmsSkuMarketingList(pmsSkuMarketing);
    }

    /**
     * 新增单品和营销的关联
     *
     * @param pmsSkuMarketing 单品和营销的关联
     * @return 结果
     */
    @Override
    public int insertPmsSkuMarketing(PmsSkuMarketing pmsSkuMarketing) {
        return pmsSkuMarketingMapper.insertPmsSkuMarketing(pmsSkuMarketing);
    }

    /**
     * 修改单品和营销的关联
     *
     * @param pmsSkuMarketing 单品和营销的关联
     * @return 结果
     */
    @Override
    public int updatePmsSkuMarketing(PmsSkuMarketing pmsSkuMarketing) {
        return pmsSkuMarketingMapper.updatePmsSkuMarketing(pmsSkuMarketing);
    }

    /**
     * 批量删除单品和营销的关联
     *
     * @param ids 需要删除的单品和营销的关联ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuMarketingByIds(Long[] ids) {
        return pmsSkuMarketingMapper.deletePmsSkuMarketingByIds(ids);
    }

    /**
     * 删除单品和营销的关联信息
     *
     * @param id 单品和营销的关联ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuMarketingById(Long id) {
        return pmsSkuMarketingMapper.deletePmsSkuMarketingById(id);
    }
}
