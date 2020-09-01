package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsCombination;
import com.ruoyi.goods.mapper.PmsCombinationMapper;
import com.ruoyi.goods.service.IPmsCombinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品组合Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsCombinationServiceImpl implements IPmsCombinationService {
    @Autowired
    private PmsCombinationMapper pmsCombinationMapper;

    /**
     * 查询商品组合
     *
     * @param id 商品组合ID
     * @return 商品组合
     */
    @Override
    public PmsCombination selectPmsCombinationById(Long id) {
        return pmsCombinationMapper.selectPmsCombinationById(id);
    }

    /**
     * 查询商品组合列表
     *
     * @param pmsCombination 商品组合
     * @return 商品组合
     */
    @Override
    public List<PmsCombination> selectPmsCombinationList(PmsCombination pmsCombination) {
        return pmsCombinationMapper.selectPmsCombinationList(pmsCombination);
    }

    /**
     * 新增商品组合
     *
     * @param pmsCombination 商品组合
     * @return 结果
     */
    @Override
    public int insertPmsCombination(PmsCombination pmsCombination) {
        pmsCombination.setCreateTime(DateUtils.getNowDate());
        return pmsCombinationMapper.insertPmsCombination(pmsCombination);
    }

    /**
     * 修改商品组合
     *
     * @param pmsCombination 商品组合
     * @return 结果
     */
    @Override
    public int updatePmsCombination(PmsCombination pmsCombination) {
        return pmsCombinationMapper.updatePmsCombination(pmsCombination);
    }

    /**
     * 批量删除商品组合
     *
     * @param ids 需要删除的商品组合ID
     * @return 结果
     */
    @Override
    public int deletePmsCombinationByIds(Long[] ids) {
        return pmsCombinationMapper.deletePmsCombinationByIds(ids);
    }

    /**
     * 删除商品组合信息
     *
     * @param id 商品组合ID
     * @return 结果
     */
    @Override
    public int deletePmsCombinationById(Long id) {
        return pmsCombinationMapper.deletePmsCombinationById(id);
    }
}
