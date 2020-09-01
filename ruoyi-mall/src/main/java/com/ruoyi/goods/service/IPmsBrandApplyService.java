package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsBrandApply;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 品牌申请Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsBrandApplyService {
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
     * 批量删除品牌申请
     *
     * @param ids 需要删除的品牌申请ID
     * @return 结果
     */
    public int deletePmsBrandApplyByIds(Long[] ids);

    /**
     * 删除品牌申请信息
     *
     * @param id 品牌申请ID
     * @return 结果
     */
    public int deletePmsBrandApplyById(Long id);

    /**
     * 搜索所有店铺审核通过的品牌
     *
     * @param storeId 店铺id
     * @return 返回店铺审核通过的所有品牌信息
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
     * 处理添加品牌数据
     *
     * @param brandApply 实体类
     * @param storeId    店铺id
     * @return 添加返回码
     */
    int doAddStoreBrand(PmsBrandApply brandApply, Long storeId);

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
     * @param storeId 删除条件
     * @param ids     品牌id
     * @return 删除返回码
     */
    int deleteStoreBrandByStoreIdAndBrandId(long storeId, long[] ids);

    /**
     * 分页查询待审核的品牌
     *
     * @param pageHelper 分页帮助类
     * @param name       品牌名称
     * @param storeName  店铺名称
     * @return 待审核的品牌
     */
    PageHelper<PmsBrandApply> queryBrandToBeAudit(PageHelper<PmsBrandApply> pageHelper, String name, String storeName);

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
     * @param ids    品牌审核id数组
     * @param reason 拒绝原因
     * @return 成功返回>=1，失败返回0
     */
    int batchRefuseBrandAudit(long[] ids, String reason);

    /**
     * 根据店铺id查询品牌并通过审核
     *
     * @param storeId 店铺id
     * @return 成功返回>=1，失败返回0
     */
    int passBrandAuditByStoreId(long storeId);
}
