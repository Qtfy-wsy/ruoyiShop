package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsCategorySpec;

import java.util.List;

/**
 * 分类和规格的关联Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsCategorySpecMapper {
    /**
     * 查询分类和规格的关联
     *
     * @param id 分类和规格的关联ID
     * @return 分类和规格的关联
     */
    public PmsCategorySpec selectPmsCategorySpecById(Long id);

    /**
     * 查询分类和规格的关联列表
     *
     * @param pmsCategorySpec 分类和规格的关联
     * @return 分类和规格的关联集合
     */
    public List<PmsCategorySpec> selectPmsCategorySpecList(PmsCategorySpec pmsCategorySpec);

    /**
     * 新增分类和规格的关联
     *
     * @param pmsCategorySpec 分类和规格的关联
     * @return 结果
     */
    public int insertPmsCategorySpec(PmsCategorySpec pmsCategorySpec);

    /**
     * 修改分类和规格的关联
     *
     * @param pmsCategorySpec 分类和规格的关联
     * @return 结果
     */
    public int updatePmsCategorySpec(PmsCategorySpec pmsCategorySpec);

    /**
     * 删除分类和规格的关联
     *
     * @param id 分类和规格的关联ID
     * @return 结果
     */
    public int deletePmsCategorySpecById(Long id);

    /**
     * 批量删除分类和规格的关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsCategorySpecByIds(Long[] ids);
}
