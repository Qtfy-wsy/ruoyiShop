package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsAttribute;
import com.ruoyi.goods.mapper.PmsAttributeMapper;
import com.ruoyi.goods.service.IPmsAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品属性Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsAttributeServiceImpl implements IPmsAttributeService {
    @Autowired
    private PmsAttributeMapper pmsAttributeMapper;

    /**
     * 查询商品属性
     *
     * @param id 商品属性ID
     * @return 商品属性
     */
    @Override
    public PmsAttribute selectPmsAttributeById(String id) {
        return pmsAttributeMapper.selectPmsAttributeById(id);
    }

    /**
     * 查询商品属性列表
     *
     * @param pmsAttribute 商品属性
     * @return 商品属性
     */
    @Override
    public List<PmsAttribute> selectPmsAttributeList(PmsAttribute pmsAttribute) {
        return pmsAttributeMapper.selectPmsAttributeList(pmsAttribute);
    }

    /**
     * 新增商品属性
     *
     * @param pmsAttribute 商品属性
     * @return 结果
     */
    @Override
    public int insertPmsAttribute(PmsAttribute pmsAttribute) {
        return pmsAttributeMapper.insertPmsAttribute(pmsAttribute);
    }

    /**
     * 修改商品属性
     *
     * @param pmsAttribute 商品属性
     * @return 结果
     */
    @Override
    public int updatePmsAttribute(PmsAttribute pmsAttribute) {
        return pmsAttributeMapper.updatePmsAttribute(pmsAttribute);
    }

    /**
     * 批量删除商品属性
     *
     * @param ids 需要删除的商品属性ID
     * @return 结果
     */
    @Override
    public int deletePmsAttributeByIds(String[] ids) {
        return pmsAttributeMapper.deletePmsAttributeByIds(ids);
    }

    /**
     * 删除商品属性信息
     *
     * @param id 商品属性ID
     * @return 结果
     */
    @Override
    public int deletePmsAttributeById(String id) {
        return pmsAttributeMapper.deletePmsAttributeById(id);
    }
}
