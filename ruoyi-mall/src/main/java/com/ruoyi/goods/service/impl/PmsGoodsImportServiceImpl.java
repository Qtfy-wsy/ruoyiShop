package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsGoodsImport;
import com.ruoyi.goods.mapper.PmsGoodsImportMapper;
import com.ruoyi.goods.service.IPmsGoodsImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品导入Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsGoodsImportServiceImpl implements IPmsGoodsImportService {
    @Autowired
    private PmsGoodsImportMapper pmsGoodsImportMapper;

    /**
     * 查询商品导入
     *
     * @param id 商品导入ID
     * @return 商品导入
     */
    @Override
    public PmsGoodsImport selectPmsGoodsImportById(Long id) {
        return pmsGoodsImportMapper.selectPmsGoodsImportById(id);
    }

    /**
     * 查询商品导入列表
     *
     * @param pmsGoodsImport 商品导入
     * @return 商品导入
     */
    @Override
    public List<PmsGoodsImport> selectPmsGoodsImportList(PmsGoodsImport pmsGoodsImport) {
        return pmsGoodsImportMapper.selectPmsGoodsImportList(pmsGoodsImport);
    }

    /**
     * 新增商品导入
     *
     * @param pmsGoodsImport 商品导入
     * @return 结果
     */
    @Override
    public int insertPmsGoodsImport(PmsGoodsImport pmsGoodsImport) {
        return pmsGoodsImportMapper.insertPmsGoodsImport(pmsGoodsImport);
    }

    /**
     * 修改商品导入
     *
     * @param pmsGoodsImport 商品导入
     * @return 结果
     */
    @Override
    public int updatePmsGoodsImport(PmsGoodsImport pmsGoodsImport) {
        return pmsGoodsImportMapper.updatePmsGoodsImport(pmsGoodsImport);
    }

    /**
     * 批量删除商品导入
     *
     * @param ids 需要删除的商品导入ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsImportByIds(Long[] ids) {
        return pmsGoodsImportMapper.deletePmsGoodsImportByIds(ids);
    }

    /**
     * 删除商品导入信息
     *
     * @param id 商品导入ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsImportById(Long id) {
        return pmsGoodsImportMapper.deletePmsGoodsImportById(id);
    }
}
