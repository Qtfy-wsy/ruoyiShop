package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsShippingMethodArea;

import java.util.List;
import java.util.Map;

/**
 * 运费方式关联的区域Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsShippingMethodAreaMapper {
    /**
     * 查询运费方式关联的区域
     *
     * @param id 运费方式关联的区域ID
     * @return 运费方式关联的区域
     */
    public PmsShippingMethodArea selectPmsShippingMethodAreaById(Long id);

    /**
     * 查询运费方式关联的区域列表
     *
     * @param pmsShippingMethodArea 运费方式关联的区域
     * @return 运费方式关联的区域集合
     */
    public List<PmsShippingMethodArea> selectPmsShippingMethodAreaList(PmsShippingMethodArea pmsShippingMethodArea);

    /**
     * 新增运费方式关联的区域
     *
     * @param pmsShippingMethodArea 运费方式关联的区域
     * @return 结果
     */
    public int insertPmsShippingMethodArea(PmsShippingMethodArea pmsShippingMethodArea);

    /**
     * 修改运费方式关联的区域
     *
     * @param pmsShippingMethodArea 运费方式关联的区域
     * @return 结果
     */
    public int updatePmsShippingMethodArea(PmsShippingMethodArea pmsShippingMethodArea);

    /**
     * 删除运费方式关联的区域
     *
     * @param id 运费方式关联的区域ID
     * @return 结果
     */
    public int deletePmsShippingMethodAreaById(Long id);

    /**
     * 批量删除运费方式关联的区域
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsShippingMethodAreaByIds(Long[] ids);

    /**
     * 新增运费方式区域
     *
     * @param shippingMethodAreas 运费方式区域
     */

    void addShippingMethodAreas(List<PmsShippingMethodArea> shippingMethodAreas);

    /**
     * 根据运费方式id查询运费方式的区域
     *
     * @param id 运费方式id
     * @return 返回运费方式区域
     */

    List<PmsShippingMethodArea> queryByShippingMethodId(long id);

    /**
     * 根据运费模版id删除运费方式区域
     *
     * @param id 运费模版id
     */

    void deleteByTemplateId(long id);

    /**
     * 根据运费模版id和市id查询运费方式的区域
     *
     * @param params 参数
     * @return 返回运费方式区域
     */

    PmsShippingMethodArea queryShippingMethodAreaByTemplateIdAndCityId(Map<String, Object> params);
}
