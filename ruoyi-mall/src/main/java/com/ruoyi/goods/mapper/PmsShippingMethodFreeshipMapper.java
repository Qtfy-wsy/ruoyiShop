package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsShippingMethodFreeship;

import java.util.List;
import java.util.Map;

/**
 * 运费模版包邮Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsShippingMethodFreeshipMapper {
    /**
     * 查询运费模版包邮
     *
     * @param id 运费模版包邮ID
     * @return 运费模版包邮
     */
    public PmsShippingMethodFreeship selectPmsShippingMethodFreeshipById(Long id);

    /**
     * 查询运费模版包邮列表
     *
     * @param pmsShippingMethodFreeship 运费模版包邮
     * @return 运费模版包邮集合
     */
    public List<PmsShippingMethodFreeship> selectPmsShippingMethodFreeshipList(PmsShippingMethodFreeship pmsShippingMethodFreeship);

    /**
     * 新增运费模版包邮
     *
     * @param pmsShippingMethodFreeship 运费模版包邮
     * @return 结果
     */
    public int insertPmsShippingMethodFreeship(PmsShippingMethodFreeship pmsShippingMethodFreeship);

    /**
     * 修改运费模版包邮
     *
     * @param pmsShippingMethodFreeship 运费模版包邮
     * @return 结果
     */
    public int updatePmsShippingMethodFreeship(PmsShippingMethodFreeship pmsShippingMethodFreeship);

    /**
     * 删除运费模版包邮
     *
     * @param id 运费模版包邮ID
     * @return 结果
     */
    public int deletePmsShippingMethodFreeshipById(Long id);

    /**
     * 批量删除运费模版包邮
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsShippingMethodFreeshipByIds(Long[] ids);

    /**
     * 新增包邮运费方式
     *
     * @param shippingMethodFreeShips 包邮运费方式
     */

    void addShippingMethodFreeShips(List<PmsShippingMethodFreeship> shippingMethodFreeShips);

    /**
     * 根据模版id查询包邮运费模版
     *
     * @param templateId 模版id
     * @return 返回宝宝运费模版
     */

    List<PmsShippingMethodFreeship> queryShippingMethodFreeShips(long templateId);

    /**
     * 根据模版id删除包邮运费模版
     *
     * @param templateId 模版id
     */

    void deleteByTemplateId(long templateId);

    /**
     * 根据运费模版id和市id查询包邮运费模版
     *
     * @param params 查询参数
     * @return 返回包邮运费模版
     */

    PmsShippingMethodFreeship queryByCityIdAndTemplateId(Map<String, Object> params);
}
