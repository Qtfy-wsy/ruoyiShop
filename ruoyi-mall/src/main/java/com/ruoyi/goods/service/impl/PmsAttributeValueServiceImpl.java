package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsAttributeValue;
import com.ruoyi.goods.mapper.PmsAttributeValueMapper;
import com.ruoyi.goods.service.IPmsAttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 属性值Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsAttributeValueServiceImpl implements IPmsAttributeValueService {
    @Autowired
    private PmsAttributeValueMapper pmsAttributeValueMapper;

    /**
     * 查询属性值
     *
     * @param id 属性值ID
     * @return 属性值
     */
    @Override
    public PmsAttributeValue selectPmsAttributeValueById(String id) {
        return pmsAttributeValueMapper.selectPmsAttributeValueById(id);
    }

    /**
     * 查询属性值列表
     *
     * @param pmsAttributeValue 属性值
     * @return 属性值
     */
    @Override
    public List<PmsAttributeValue> selectPmsAttributeValueList(PmsAttributeValue pmsAttributeValue) {
        return pmsAttributeValueMapper.selectPmsAttributeValueList(pmsAttributeValue);
    }

    /**
     * 新增属性值
     *
     * @param pmsAttributeValue 属性值
     * @return 结果
     */
    @Override
    public int insertPmsAttributeValue(PmsAttributeValue pmsAttributeValue) {
        return pmsAttributeValueMapper.insertPmsAttributeValue(pmsAttributeValue);
    }

    /**
     * 修改属性值
     *
     * @param pmsAttributeValue 属性值
     * @return 结果
     */
    @Override
    public int updatePmsAttributeValue(PmsAttributeValue pmsAttributeValue) {
        return pmsAttributeValueMapper.updatePmsAttributeValue(pmsAttributeValue);
    }

    /**
     * 批量删除属性值
     *
     * @param ids 需要删除的属性值ID
     * @return 结果
     */
    @Override
    public int deletePmsAttributeValueByIds(String[] ids) {
        return pmsAttributeValueMapper.deletePmsAttributeValueByIds(ids);
    }

    /**
     * 删除属性值信息
     *
     * @param id 属性值ID
     * @return 结果
     */
    @Override
    public int deletePmsAttributeValueById(String id) {
        return pmsAttributeValueMapper.deletePmsAttributeValueById(id);
    }
}
