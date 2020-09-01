package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsSku;

import java.util.List;
import java.util.Map;

/**
 * 单品Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsSkuMapper {


    int querySkuCount(Map<String, Object> params);

    /**
     * 查询单品数据
     *
     * @param params 参数
     * @return 返回单品数据
     */

    List<PmsSku> querySkus(Map<String, Object> params);

    /**
     * 查询单品
     *
     * @param id 单品ID
     * @return 单品
     */
    public PmsSku selectPmsSkuById(String id);

    /**
     * 查询单品列表
     *
     * @param pmsSku 单品
     * @return 单品集合
     */
    public List<PmsSku> selectPmsSkuList(PmsSku pmsSku);

    /**
     * 新增单品
     *
     * @param pmsSku 单品
     * @return 结果
     */
    public int insertPmsSku(PmsSku pmsSku);

    /**
     * 修改单品
     *
     * @param pmsSku 单品
     * @return 结果
     */
    public int updatePmsSku(PmsSku pmsSku);

    /**
     * 删除单品
     *
     * @param id 单品ID
     * @return 结果
     */
    public int deletePmsSkuById(String id);

    /**
     * 批量删除单品
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSkuByIds(String[] ids);

    List<PmsSku> querySkuBySpuId(Map<String, Object> params);

    /**
     * 修改单品上下架状态
     *
     * @param params 参数
     * @return 成功返回 >1 失败返回0
     */

    int updateShelvesStatus(Map<String, Object> params);

    /**
     * 物理删除单品信息
     *
     * @param params 参数
     */

    int deleteBySpuIdPhysical(Map<String, Object> params);


    /**
     * 根据商品id删除单品信息
     *
     * @param params 查询参数
     */
    int deleteBySpuId(Map<String, Object> params);

    /**
     * 单品审核通过
     *
     * @param spuId 商品id
     * @return 成功返回1 失败返回0
     */

    int auditPass(long spuId);

    /**
     * 单品审核拒绝
     *
     * @param prams 参数
     * @return 成功返回1 失败返回0
     */

    int auditRefuse(Map<String, Object> prams);

    /**
     * 根据店铺id查询前几条单品数据
     *
     * @param storeId 店铺id
     * @return 单品集合
     */

    List<PmsSku> queryFiveDataForAttentionStore(long storeId);

    /**
     * 根据单品id查询单品信息
     *
     * @param skuIds 单品id
     * @return 返回单品信息
     */

    List<PmsSku> querySkuByIds(List<String> skuIds);

    /**
     * 减去单品库存
     *
     * @param params 参数
     * @return 成功返回>0  失败返回0
     */

    int reduceSkuStock(Map<String, Object> params);

    /**
     * 增加单品库存
     *
     * @param params 参数
     * @return 成功返回>0  失败返回0
     */

    int increaseSkuStock(Map<String, Object> params);

    /**
     * 查询店铺最近7天上架的单品总量
     *
     * @param storeId 店铺id
     * @return 返回单品总量
     */

    int lastUpSkusNum(long storeId);

    /**
     * 查询店铺有促销的单品总数
     *
     * @param storeId 店铺id
     * @return 返回单品总数
     */

    int marketSkusNum(long storeId);

    /**
     * 查询最近7天上架的单品数据
     *
     * @param params 参数
     * @return 返回最近7天上架的单品数据
     */

    List<PmsSku> queryLastUpSkus(Map<String, Object> params);

    /**
     * 查询所有促销的单品数据
     *
     * @param params 查询参数
     * @return 返回单品数据
     */

    List<PmsSku> queryMarketSkus(Map<String, Object> params);

    /**
     * 佣金设置
     *
     * @param params 参数
     * @return 成功返回>0 失败返回0
     */

    int updateCommission(Map<String, Object> params);

    /**
     * 根据店铺id修改商品上下架状态
     *
     * @param params 参数
     * @return 成功返回 >0 失败返回0
     */

    int updateShelvesStatusByStoreIds(Map<String, Object> params);

    /**
     * 修改单品为审核状态
     *
     * @param params 参数
     * @return 成功>0 失败返回0
     */

    int updateSkuToAudit(Map<String, Object> params);

    /**
     * 根据店铺id查询商品数量（上架的和审核通过的）
     *
     * @param storeId 店铺id
     * @return 店铺下商品数量
     */

    int querySkuCountByStoreId(long storeId);

    /**
     * 修改单品物流模版id
     *
     * @param params 参数
     * @return 成功>0 失败0
     */

    int updateSkuLogisticsTemplateId(Map<String, Object> params);

}
