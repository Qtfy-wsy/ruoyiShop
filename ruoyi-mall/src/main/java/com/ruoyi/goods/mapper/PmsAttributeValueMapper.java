package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsAttributeValue;

import java.util.List;

/**
 * 属性值Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface PmsAttributeValueMapper {
    /**
     * 查询属性值
     *
     * @param id 属性值ID
     * @return 属性值
     */
    public PmsAttributeValue selectPmsAttributeValueById(String id);

    /**
     * 查询属性值列表
     *
     * @param pmsAttributeValue 属性值
     * @return 属性值集合
     */
    public List<PmsAttributeValue> selectPmsAttributeValueList(PmsAttributeValue pmsAttributeValue);

    /**
     * 新增属性值
     *
     * @param pmsAttributeValue 属性值
     * @return 结果
     */
    public int insertPmsAttributeValue(PmsAttributeValue pmsAttributeValue);

    /**
     * 修改属性值
     *
     * @param pmsAttributeValue 属性值
     * @return 结果
     */
    public int updatePmsAttributeValue(PmsAttributeValue pmsAttributeValue);

    /**
     * 删除属性值
     *
     * @param id 属性值ID
     * @return 结果
     */
    public int deletePmsAttributeValueById(String id);

    /**
     * 批量删除属性值
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsAttributeValueByIds(String[] ids);
}
