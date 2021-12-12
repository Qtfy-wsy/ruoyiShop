package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsBrand;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 品牌Service接口
 *
 * @author 商城
 */
public interface IPmsBrandService {
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
     * 批量删除品牌
     *
     * @param ids 需要删除的品牌ID
     * @return 结果
     */
    public int deletePmsBrandByIds(Long[] ids);

    /**
     * 删除品牌信息
     *
     * @param id 品牌ID
     * @return 结果
     */
    public int deletePmsBrandById(Long id);

    /**
     * 新增品牌
     *
     * @param brand 品牌
     * @return 返回码说明 0 失败 1成功
     */
    int addBrand(PmsBrand brand);

    /**
     * 根据id查询品牌
     *
     * @param id      品牌id
     * @param storeId 店铺id
     * @return 返回品牌信息
     */
    PmsBrand queryBrandById(long id, long storeId);

    /**
     * 更新品牌
     *
     * @param brand 品牌信息
     * @return 成功返回1 失败返回0
     */
    int updateBrand(PmsBrand brand);

    /**
     * 删除商品品牌
     *
     * @param brand 商品品牌
     * @return 成功返回1  失败返回0
     */
    int deleteBrand(PmsBrand brand);

    /**
     * 批量删除品牌
     *
     * @param brands 品牌信息
     * @return 成功返回1  失败返回0
     */
    int batchDeleteBrands(List<PmsBrand> brands);

    /**
     * 分页查询品牌
     *
     * @param pageHelper 分页帮助类
     * @param name       品牌名称
     * @param nickName   品牌昵称
     * @param storeId
     * @return 返回品牌数据
     */
    PageHelper<PmsBrand> queryBrands(PageHelper<PmsBrand> pageHelper, String name, String nickName, long storeId);

    /**
     * 查询所有品牌(审核通过的)
     *
     * @param storeId 店铺id
     * @return 返回所有品牌
     */
    List<PmsBrand> queryAllBrands(long storeId);

    /**
     * 查询所有admin品牌
     *
     * @return 返回admin品牌
     */
    List<PmsBrand> queryAllAdminBrands();

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
     * @param storeId 店铺id
     * @return 返回所有品牌
     */
    List<PmsBrand> queryStoreBrands(long storeId, String status);

    /**
     * 根据店铺id查询自定义品牌
     *
     * @param storeId 店铺id
     * @param status  状态
     * @return 返回所有品牌
     */
    List<PmsBrand> queryCustomBrandByStoreIdAndStatus(long storeId, String status);


    /**
     * 分页查询自定义品牌
     *
     * @param pageHelper 分页帮助类
     * @param brandName  品牌名称
     * @param storeName  店铺名称
     * @return 自定义品牌信息
     */
    PageHelper<PmsBrand> queryCustomBrandByStatus(PageHelper<PmsBrand> pageHelper, String brandName, String storeName);

    /**
     * 通过自定义品牌审核
     *
     * @param id 自定义品牌id
     * @return 成功返回1，失败返回0
     */
    int passCustomBrandAudit(long id);

    /**
     * 拒绝自定义品牌审核
     *
     * @param brand 自定义品牌实例
     * @return 成功返回1，失败返回0
     */
    int refuseCustomBrandAudit(PmsBrand brand);

    /**
     * 批量通过自定义品牌审核
     *
     * @param ids 自定义品牌id数组
     * @return 成功返回>=1，失败返回0
     */
    int batchPassCustomBrandAudit(long[] ids);

    /**
     * 批量拒绝自定义品牌审核
     *
     * @param ids    自定义品牌id数组
     * @param reason 拒绝原因
     * @return 成功返回>=1，失败返回0
     */
    int batchRefuseCustomBrandAudit(long[] ids, String reason);

    /**
     * 根据店铺id查找其自定义品牌并通过品牌审核
     *
     * @param storeId 店铺id
     * @return 成功返回>=1，失败返回0
     */
    int passCustomBrandByStoreId(long storeId);

    /**
     * 分页查询店铺下的所有品牌
     *
     * @param pageHelper 品牌分类
     * @param name       品牌名称
     * @return 分页数据
     */
    PageHelper queryStoreBrandsForPage(PageHelper<PmsBrand> pageHelper, long storeId, String name);

    /**
     * 分页查询待审核品牌
     * @param pmsBrand 品牌
     * @return 分页数据
     */
    List<PmsBrand> queryBrandToBeAudit(PmsBrand pmsBrand);

    /**
     * 分页查询自定义品牌
     * @param pmsBrand 自定义品牌
     * @return 分页数据
     */
    List<PmsBrand> queryMySelfBrands(PmsBrand pmsBrand);
}
