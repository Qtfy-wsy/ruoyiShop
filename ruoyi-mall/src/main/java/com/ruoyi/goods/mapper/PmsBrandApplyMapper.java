package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsBrandApply;

import java.util.List;
import java.util.Map;

/**
 * 品牌申请Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsBrandApplyMapper {
    /**
     * 查询店铺所有审核通过的品牌
     *
     * @param storeId 店铺id
     * @return 返回店铺所有审核通过的品牌
     */

    List<PmsBrandApply> queryAllPassBrand(long storeId);

    /**
     * 开店-添加店铺品牌
     *
     * @param list 品牌集合
     * @return 添加返回码
     */

    int addStoreBrand(List<PmsBrandApply> list);

    /**
     * 根据店铺id和品牌id查询签约品牌信息
     *
     * @param map 查询条件
     * @return
     */

    PmsBrandApply queryStoreBrandByStoreIdAndBrandId(Map<String, Object> map);

    /**
     * 删除店铺品牌
     *
     * @param storeId 店铺id
     * @return 删除返回码
     */

    int deleteStoreBrand(long storeId);

    /**
     * 根据店铺id和品牌id删除签约品牌
     *
     * @param map 删除条件
     * @return 删除返回码
     */

    int deleteStoreBrandByStoreIdAndBrandId(Map<String, Object> map);

    /**
     * 分页查询所有待审核的品牌
     *
     * @param params 查询参数
     * @return 待审核的品牌
     */

    List<PmsBrandApply> queryBrandToBeAudit(Map<String, Object> params);

    /**
     * 查询待审核品牌的总记录数
     *
     * @param params 查询参数
     * @return 总记录数
     */

    int queryBrandToBeAuditCount(Map<String, Object> params);

    /**
     * 通过品牌审核
     *
     * @param id 品牌审核id
     * @return 成功返回1，失败返回0
     */

    int passBrandAudit(long id);

    /**
     * 批量通过品牌审核
     *
     * @param ids 品牌审核id数组
     * @return 成功返回>=1，失败返回0
     */

    int batchPassBrandAudit(long[] ids);

    /**
     * 拒绝品牌审核
     *
     * @param brandApply 商品审核实例
     * @return 成功返回1，失败返回0
     */

    int refuseBrandAudit(PmsBrandApply brandApply);

    /**
     * 批量拒绝品牌审核
     *
     * @param params 品牌审核id数组及拒绝原因
     * @return 成功返回>=1，失败返回0
     */

    int batchRefuseBrandAudit(Map<String, Object> params);

    /**
     * 根据店铺id查询品牌并通过审核
     *
     * @param storeId 店铺id
     * @return 成功返回>=1，失败返回0
     */

    int passBrandAuditByStoreId(long storeId);

    /**
     * 查询品牌申请
     *
     * @param id 品牌申请ID
     * @return 品牌申请
     */
    public PmsBrandApply selectPmsBrandApplyById(Long id);

    /**
     * 查询品牌申请列表
     *
     * @param pmsBrandApply 品牌申请
     * @return 品牌申请集合
     */
    public List<PmsBrandApply> selectPmsBrandApplyList(PmsBrandApply pmsBrandApply);

    /**
     * 新增品牌申请
     *
     * @param pmsBrandApply 品牌申请
     * @return 结果
     */
    public int insertPmsBrandApply(PmsBrandApply pmsBrandApply);

    /**
     * 修改品牌申请
     *
     * @param pmsBrandApply 品牌申请
     * @return 结果
     */
    public int updatePmsBrandApply(PmsBrandApply pmsBrandApply);

    /**
     * 删除品牌申请
     *
     * @param id 品牌申请ID
     * @return 结果
     */
    public int deletePmsBrandApplyById(Long id);

    /**
     * 批量删除品牌申请
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsBrandApplyByIds(Long[] ids);
}
