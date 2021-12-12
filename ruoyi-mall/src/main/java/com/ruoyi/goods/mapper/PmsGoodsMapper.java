package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsGoods;
import com.ruoyi.goods.domain.StoreSpu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsGoodsMapper {
    /**
     * 查询商品
     *
     * @param id 商品ID
     * @return 商品
     */
    public PmsGoods selectPmsGoodsById(Long id);

    /**
     * 查询商品列表
     *
     * @param pmsGoods 商品
     * @return 商品集合
     */
    public List<PmsGoods> selectPmsGoodsList(PmsGoods pmsGoods);

    /**
     * 新增商品
     *
     * @param pmsGoods 商品
     * @return 结果
     */
    public int insertPmsGoods(PmsGoods pmsGoods);

    /**
     * 修改商品
     *
     * @param pmsGoods 商品
     * @return 结果
     */
    public int updatePmsGoods(PmsGoods pmsGoods);

    /**
     * 删除商品
     *
     * @param id 商品ID
     * @return 结果
     */
    public int deletePmsGoodsById(Long id);

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsGoodsByIds(Long[] ids);

    /**
     * 添加商品
     *
     * @param spu 商品
     * @return 成功返回1  失败返回0
     */

    int addSpu(PmsGoods spu);

    /**
     * 查询商品信息
     *
     * @param params 查询参数
     * @return 返回商品信息
     */

    PmsGoods querySpu(Map<String, Object> params);

    /**
     * 查询商品总记录数
     *
     * @param params 查询参数
     * @return 返回商品总记录数
     */

    int querySpuCount(Map<String, Object> params);

    /**
     * 查询商品数据
     *
     * @param params 查询参数
     * @return 返回商品数据
     */

    List<PmsGoods> querySpus(Map<String, Object> params);

    /**
     * 删除商品信息
     *
     * @param spu 商品信息
     * @return 成功发回1  失败返回0
     */

    int deleteSpu(PmsGoods spu);

    /**
     * 更新商品信息
     *
     * @return 成功返回 1 失败返回0
     */

    int updateSpu(PmsGoods spu);

    /**
     * 查询所有店铺商品的总数
     *
     * @param params 查询参数
     * @return 返回店铺商品总数
     */

    int queryAllStoreSpusCount(Map<String, Object> params);


    /**
     * 查询所有店铺的商品
     *
     * @param params 查询参数
     * @return 返回店铺的所有商品
     */

    List<PmsGoods> queryAllStoreSpus(Map<String, Object> params);

    /**
     * 根据三级分类id查询是否关联商品总条数
     *
     * @param ThirdCateId 商品三级分类id
     * @return 返回 0 即表示该三级分类不关联商品 >0 即表示该三级分类关联商品
     */

    int querySpuByThirdCateId(long ThirdCateId);

    /**
     * 查询商品的总数 (es使用)
     *
     * @return 返回商品的总数
     */

    int querySpuCountForEs();

    /**
     * 查询商品信息(es使用)
     *
     * @param params 查询参数
     * @return 返回商品信息
     */

    List<PmsGoods> querySpuForEs(Map<String, Object> params);

    /**
     * 佣金设置
     *
     * @param params 参数
     * @return 成功返回>0 失败返回0
     */

    int updateCommission(Map<String, Object> params);

    /**
     * 商品审核通过
     *
     * @param spuId 商品id
     * @return 成功返回1 失败返回0
     */

    int auditPass(long spuId);

    /**
     * 商品审核拒绝
     *
     * @param spuId 商品id
     * @return 成功返回1 失败返回0
     */

    int auditRefuse(long spuId);

    /**
     * 修改商品上下架状态
     *
     * @param params 参数
     * @return 成功返回 >1 失败返回0
     */

    int updateShelvesStatus(Map<String, Object> params);

    /**
     * 修改商品的审核状态
     *
     * @param params 参数
     * @return 成功返回>=1 失败返回0
     */

    int updateSpuAuditStatus(Map<String, Object> params);

    /**
     * 根据店铺id修改商品上下架状态
     *
     * @param params 参数
     * @return 成功返回 >1 失败返回0
     */

    int updateShelvesStatusByStoreIds(Map<String, Object> params);

    /**
     * 查找类型关联的商品数量
     *
     * @param typeId 类型id
     * @return 类型关联的商品数量
     */

    int querySpuCountByTypeId(@Param("typeId") long typeId);

    /**
     * 根据spuId查找seo设置(仅包含seo信息)
     *
     * @param spuId 商品id
     * @return 商品实体
     */

    PmsGoods querySeoBySpuId(@Param("spuId") long spuId);

    /**
     * 查询门店商品信息
     *
     * @param params 查询参数
     * @return 返回商品信息
     */

    List<StoreSpu> queryStoreSpuList(Map<String, Object> params);

    /**
     * 查询门店商品总记录数
     *
     * @param params 查询参数
     * @return 返回商品总记录数
     */

    int queryStoreSpuListCount(Map<String, Object> params);


    /**
     * 分页查询门店在售商品
     *
     * @param params 查询参数
     * @return 门店在售商品集合
     */

    List<StoreSpu> queryStoreOnSaleSpuList(Map<String, Object> params);

    /**
     * 查询门店在售商品总数
     *
     * @param params 查询参数
     * @return 门店在售商品数量
     */

    int queryStoreOnSaleSpuListCount(Map<String, Object> params);

    /**
     * 查询未关联店铺三级分类的商品
     *
     * @param params 查询参数
     * @return 返回商品数据
     */

    List<PmsGoods> queryAllSpusWithoutStoreCategory(Map<String, Object> params);

    /**
     * 查询未关联店铺三级分类的商品总记录数
     *
     * @param params 查询参数
     * @return 返回总记录数
     */

    int queryAllSpusWithoutStoreCategoryCount(Map<String, Object> params);

    /**
     * 根据店铺三级分类查询商品信息
     *
     * @param params 查询参数
     * @return 返回商品信息
     */

    List<PmsGoods> queryAllSpusByStoreCategory(Map<String, Object> params);

    /**
     * 根据店铺三级分类查询商品信息总记录数
     *
     * @param params 查询参数
     * @return 返回商品总记录数
     */

    int queryAllSpusByStoreCategoryCount(Map<String, Object> params);

    /**
     * 根据店铺三级分类查询商品id
     *
     * @param params 查询参数
     * @return 返回商品id数组
     */

    Long[] queryAllSpuIdByStoreCategory(Map<String, Object> params);

    /**
     * 修改店铺三级分类关联商品
     *
     * @param params 参数
     * @return 成功>0 失败0
     */

    int updateSpuWithStoreCategory(Map<String, Object> params);

    /**
     * 取消店铺三级分类关联商品
     *
     * @param params 参数
     * @return 成功>0 失败0
     */

    int cancelSpuWithStoreCategory(Map<String, Object> params);

    /**
     * 根据三级分类id取消关联商品
     *
     * @param params 参数
     * @return 成功>0 失败0
     */

    int cancelSpuWithStoreCategoryByStoreTcateId(Map<String, Object> params);

    /**
     * 根据id查询商品信息（导出用）
     *
     * @param params 查询参数
     * @return 商品信息
     */

    List<PmsGoods> querySpuByIdsForExport(Map<String, Object> params);

    /**
     * 查询全部商品信息（导出用）
     *
     * @param storeId 店铺id
     * @return 商品信息
     */

    List<PmsGoods> queryAllSpuForExport(@Param("storeId") long storeId);

    /**
     * 修改商品物流模版id
     *
     * @param params 参数
     * @return 成功>0 失败0
     */

    int updateSpuLogisticsTemplateId(Map<String, Object> params);

    List<PmsGoods> queryAllStoreToBeAuditdSpus();

    int queryAllStoreToBeAuditdSpusCount(Map<String, Object> params);
}
