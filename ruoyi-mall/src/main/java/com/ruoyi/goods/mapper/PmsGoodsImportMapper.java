package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsGoodsImport;

import java.util.List;

/**
 * 商品导入Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface PmsGoodsImportMapper {
    /**
     * 查询商品导入
     *
     * @param id 商品导入ID
     * @return 商品导入
     */
    public PmsGoodsImport selectPmsGoodsImportById(Long id);

    /**
     * 查询商品导入列表
     *
     * @param pmsGoodsImport 商品导入
     * @return 商品导入集合
     */
    public List<PmsGoodsImport> selectPmsGoodsImportList(PmsGoodsImport pmsGoodsImport);

    /**
     * 新增商品导入
     *
     * @param pmsGoodsImport 商品导入
     * @return 结果
     */
    public int insertPmsGoodsImport(PmsGoodsImport pmsGoodsImport);

    /**
     * 修改商品导入
     *
     * @param pmsGoodsImport 商品导入
     * @return 结果
     */
    public int updatePmsGoodsImport(PmsGoodsImport pmsGoodsImport);

    /**
     * 删除商品导入
     *
     * @param id 商品导入ID
     * @return 结果
     */
    public int deletePmsGoodsImportById(Long id);

    /**
     * 批量删除商品导入
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsGoodsImportByIds(Long[] ids);
}
