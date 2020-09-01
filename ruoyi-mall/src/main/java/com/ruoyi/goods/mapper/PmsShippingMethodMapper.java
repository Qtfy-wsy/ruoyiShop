package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsShippingMethod;

import java.util.List;
import java.util.Map;

/**
 * 运费方式Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsShippingMethodMapper {
    /**
     * 查询运费方式
     *
     * @param id 运费方式ID
     * @return 运费方式
     */
    public PmsShippingMethod selectPmsShippingMethodById(Long id);

    /**
     * 查询运费方式列表
     *
     * @param pmsShippingMethod 运费方式
     * @return 运费方式集合
     */
    public List<PmsShippingMethod> selectPmsShippingMethodList(PmsShippingMethod pmsShippingMethod);

    /**
     * 新增运费方式
     *
     * @param pmsShippingMethod 运费方式
     * @return 结果
     */
    public int insertPmsShippingMethod(PmsShippingMethod pmsShippingMethod);

    /**
     * 修改运费方式
     *
     * @param pmsShippingMethod 运费方式
     * @return 结果
     */
    public int updatePmsShippingMethod(PmsShippingMethod pmsShippingMethod);

    /**
     * 删除运费方式
     *
     * @param id 运费方式ID
     * @return 结果
     */
    public int deletePmsShippingMethodById(Long id);

    /**
     * 批量删除运费方式
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsShippingMethodByIds(Long[] ids);

    /**
     * 新增运费方式
     *
     * @param shippingMethods 运费方式
     */

    void addShippingMethods(List<PmsShippingMethod> shippingMethods);

    /**
     * 根据物流模版id查询运费方式
     *
     * @param id 物流模版id
     * @return 返回物流模版的运费方式
     */
    List<PmsShippingMethod> queryByTemplateId(long id);

    /**
     * 删除运费方式
     *
     * @param id 运费方式id
     */
    void deleteByTemplateId(long id);

    /**
     * 根据id查询运费方式
     *
     * @param params 查询参数
     * @return 返回运费方式
     */
    PmsShippingMethod queryById(Map<String, Object> params);

    /**
     * 查询模版下的默认运费方式
     *
     * @param templateId 运费模版id
     * @return 返回默认运费方式
     */
    PmsShippingMethod queryDefaultShippingMethod(Long templateId);
}
