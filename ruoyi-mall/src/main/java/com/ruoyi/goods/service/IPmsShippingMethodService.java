package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsShippingMethod;

import java.util.List;

/**
 * 运费方式Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsShippingMethodService {
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
     * 批量删除运费方式
     *
     * @param ids 需要删除的运费方式ID
     * @return 结果
     */
    public int deletePmsShippingMethodByIds(Long[] ids);

    /**
     * 删除运费方式信息
     *
     * @param id 运费方式ID
     * @return 结果
     */
    public int deletePmsShippingMethodById(Long id);

    /**
     * 新增运费方式
     *
     * @param shippingMethods 运费方式
     */
    void addShippingMethod(List<PmsShippingMethod> shippingMethods);

    /**
     * 根据物流模版id查询运费方式
     *
     * @param id 物流模版id
     * @return 返回物流模版的运费方式
     */
    List<PmsShippingMethod> queryByTemplateId(long id);

    /**
     * 根据物流模版id删除运费方式
     *
     * @param id 物流模版id
     */
    void deleteByTemplateId(long id);

    /**
     * 更新运费方式
     *
     * @param shippingMethods 运费方式
     * @param templateId      物流模版id
     */
    void updateShippingMethods(List<PmsShippingMethod> shippingMethods, long templateId);

    /**
     * 根据市id查询运费方式
     *
     * @param templageId 运费模版id
     * @param cityId     市id
     * @return 返回运费方式
     */
    PmsShippingMethod queryShippingMethodByCityId(Long templageId, Long cityId);
}
