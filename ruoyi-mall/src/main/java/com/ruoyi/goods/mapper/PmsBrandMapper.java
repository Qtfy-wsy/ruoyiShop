package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsBrand;

import java.util.List;
import java.util.Map;

/**
 * 品牌Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsBrandMapper {
    /**
     * 查询品牌
     *
     * @param id 品牌ID
     * @return 品牌
     */
    public PmsBrand selectPmsBrandById(Long id);

    /**
     * 查询品牌列表
     *
     * @param pmsBrand 品牌
     * @return 品牌集合
     */
    public List<PmsBrand> selectPmsBrandList(PmsBrand pmsBrand);

    /**
     * 新增品牌
     *
     * @param pmsBrand 品牌
     * @return 结果
     */
    public int insertPmsBrand(PmsBrand pmsBrand);

    /**
     * 修改品牌
     *
     * @param pmsBrand 品牌
     * @return 结果
     */
    public int updatePmsBrand(PmsBrand pmsBrand);

    /**
     * 删除品牌
     *
     * @param id 品牌ID
     * @return 结果
     */
    public int deletePmsBrandById(Long id);

    /**
     * 批量删除品牌
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsBrandByIds(Long[] ids);

    /**
     * 新增品牌
     *
     * @param brand 品牌
     * @return 返回0 失败 1 成功
     */

    int addBrand(PmsBrand brand);

    /**
     * 根据品牌查询品牌id
     *
     * @param params 查询参数
     * @return 返回品牌信息
     */

    PmsBrand queryBrandById(Map<String, Object> params);

    /**
     * 更新品牌
     *
     * @param brand 品牌信息
     * @return 成功返回1  失败返回0
     */

    int updateBrand(PmsBrand brand);

    /**
     * 删除品牌信息
     *
     * @param brand 品牌信息
     * @return 成功返回1  失败返回0
     */

    int deleteBrand(PmsBrand brand);

    /**
     * 分页查询品牌数据
     *
     * @param params 查询参数
     * @return 返回品牌数据
     */

    List<PmsBrand> queryBrands(Map<String, Object> params);

    /**
     * 查询品牌的总记录数
     *
     * @param params 查询参数
     * @return 返回总记录数
     */

    int queryBrandsCount(Map<String, Object> params);

    /**
     * 查询所有品牌
     *
     * @param storeId
     * @return 返回所有品牌
     */

    List<PmsBrand> queryAllBrands(long storeId);

    /**
     * 添加自定义品牌
     *
     * @param list 品牌集合
     * @return 添加返回码
     */

    int batchAddCustomBrand(List<PmsBrand> list);

    /**
     * 删除自定义品牌
     *
     * @param storeId 店铺id
     * @return 删除返回码
     */

    int batchDeleteCustomBrand(long storeId);

    /**
     * 根据店铺id查询主营品牌
     *
     * @param brand 品牌信息
     * @return 返回所有品牌
     */

    List<PmsBrand> queryStoreBrands(PmsBrand brand);

    /**
     * 查询店铺下的所有签约品牌总数
     *
     * @param map 查询条件
     * @return 返回个数
     */

    int queryStoreBrandsForPageCount(Map<String, Object> map);

    /**
     * 查询店铺下的所有品牌
     *
     * @param map 查询条件
     * @return 返回个数
     */

    List<PmsBrand> queryStoreBrandsForPage(Map<String, Object> map);

    /**
     * 根据店铺id和状态查询自定义品牌
     *
     * @param brand 品牌信息
     * @return 返回所有品牌
     */

    List<PmsBrand> queryCustomBrandByStoreIdAndStatus(PmsBrand brand);


    /**
     * 分页查询自定义品牌
     *
     * @param params 品牌名称及店铺名称
     * @return 返回自定义品牌数据
     */

    List<PmsBrand> queryCustomBrandByStatus(Map<String, Object> params);

    /**
     * 查询自定义品牌总记录数
     *
     * @param params 品牌名称及店铺名称
     * @return 自定义品牌总记录数
     */

    int queryCustomBrandCount(Map<String, Object> params);

    /**
     * 通过自定义品牌审核
     *
     * @param id 自定义品牌id
     * @return 成功返回1，失败返回0
     */

    int passCustomBrandAudit(long id);

    /**
     * 批量通过自定义品牌审核
     *
     * @param ids 自定义品牌id数组
     * @return 成功返回>=1，失败返回0
     */

    int batchPassCustomBrandAudit(long[] ids);

    /**
     * 拒绝自定义品牌审核
     *
     * @param brand 自定义品牌实例
     * @return 成功返回1，失败返回0
     */

    int refuseCustomBrandAudit(PmsBrand brand);

    /**
     * 批量拒绝自定义品牌审核
     *
     * @param params 自定义品牌id数组及拒绝原因
     * @return 成功返回>=1，失败返回0
     */

    int batchRefuseCustomBrandAudit(Map<String, Object> params);

    /**
     * 根据店铺id查找其自定义品牌并通过品牌审核
     *
     * @param storeId 店铺id
     * @return 成功返回>=1，失败返回0
     */

    int passCustomBrandByStoreId(long storeId);
}
