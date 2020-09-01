package com.ruoyi.store.mapper;

import com.ruoyi.goods.domain.StoreSku;
import com.ruoyi.store.domain.TStoreSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 门店单品Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface TStoreSkuMapper {
    /**
     * 查询门店单品
     *
     * @param id 门店单品ID
     * @return 门店单品
     */
    public TStoreSku selectTStoreSkuById(Long id);


    /**
     * 根据参数查询门店单品关联
     *
     * @param params 查询参数
     * @return 门店单品关联集合
     */

    List<StoreSku> queryStoreSkuList(Map<String, Object> params);

    /**
     * 添加门店单品关联
     *
     * @param storeSkuList 门店单品关联集合
     * @return >0成功 否则失败
     */
    int addStoreSkuList(@Param("storeSkuList") List<StoreSku> storeSkuList);

    /**
     * 根据spuId删除门店单品关联
     *
     * @param spuId   商品id
     * @param storeId 门店id
     * @return >0成功，否则失败
     */
    int deleteStoreSkuBySpuId(@Param("spuId") long spuId, @Param("storeId") Long storeId);

    /**
     * 减去门店单品库存
     *
     * @param params 参数
     * @return 成功返回0 失败返回1
     */

    int reduceStoreSkusStock(Map<String, Object> params);

    /**
     * 查询门店单品列表
     *
     * @param tStoreSku 门店单品
     * @return 门店单品集合
     */
    public List<TStoreSku> selectTStoreSkuList(TStoreSku tStoreSku);

    /**
     * 新增门店单品
     *
     * @param tStoreSku 门店单品
     * @return 结果
     */
    public int insertTStoreSku(TStoreSku tStoreSku);

    /**
     * 修改门店单品
     *
     * @param tStoreSku 门店单品
     * @return 结果
     */
    public int updateTStoreSku(TStoreSku tStoreSku);

    /**
     * 删除门店单品
     *
     * @param id 门店单品ID
     * @return 结果
     */
    public int deleteTStoreSkuById(Long id);

    /**
     * 批量删除门店单品
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreSkuByIds(Long[] ids);
}
