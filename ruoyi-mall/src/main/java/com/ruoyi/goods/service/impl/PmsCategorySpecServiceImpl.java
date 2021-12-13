package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsCategorySpec;
import com.ruoyi.goods.mapper.PmsCategorySpecMapper;
import com.ruoyi.goods.service.IPmsCategorySpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类和规格的关联Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsCategorySpecServiceImpl implements IPmsCategorySpecService {
    @Autowired
    private PmsCategorySpecMapper pmsCategorySpecMapper;

    /**
     * 查询分类和规格的关联
     *
     * @param id 分类和规格的关联ID
     * @return 分类和规格的关联
     */
    @Override
    public PmsCategorySpec selectPmsCategorySpecById(Long id) {
        return pmsCategorySpecMapper.selectPmsCategorySpecById(id);
    }

    /**
     * 查询分类和规格的关联列表
     *
     * @param pmsCategorySpec 分类和规格的关联
     * @return 分类和规格的关联
     */
    @Override
    public List<PmsCategorySpec> selectPmsCategorySpecList(PmsCategorySpec pmsCategorySpec) {
        return pmsCategorySpecMapper.selectPmsCategorySpecList(pmsCategorySpec);
    }

    /**
     * 新增分类和规格的关联
     *
     * @param pmsCategorySpec 分类和规格的关联
     * @return 结果
     */
    @Override
    public int insertPmsCategorySpec(PmsCategorySpec pmsCategorySpec) {
        return pmsCategorySpecMapper.insertPmsCategorySpec(pmsCategorySpec);
    }

    /**
     * 修改分类和规格的关联
     *
     * @param pmsCategorySpec 分类和规格的关联
     * @return 结果
     */
    @Override
    public int updatePmsCategorySpec(PmsCategorySpec pmsCategorySpec) {
        return pmsCategorySpecMapper.updatePmsCategorySpec(pmsCategorySpec);
    }

    /**
     * 批量删除分类和规格的关联
     *
     * @param ids 需要删除的分类和规格的关联ID
     * @return 结果
     */
    @Override
    public int deletePmsCategorySpecByIds(Long[] ids) {
        return pmsCategorySpecMapper.deletePmsCategorySpecByIds(ids);
    }

    /**
     * 删除分类和规格的关联信息
     *
     * @param id 分类和规格的关联ID
     * @return 结果
     */
    @Override
    public int deletePmsCategorySpecById(Long id) {
        return pmsCategorySpecMapper.deletePmsCategorySpecById(id);
    }
}
