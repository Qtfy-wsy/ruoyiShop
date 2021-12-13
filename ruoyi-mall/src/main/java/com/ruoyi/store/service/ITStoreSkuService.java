package com.ruoyi.store.service;

import com.ruoyi.goods.domain.StoreSku;
import com.ruoyi.store.domain.TStoreSku;
import com.ruoyi.store.vo.StoreSkuDetail;

import java.util.List;

/**
 * 门店单品Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface ITStoreSkuService {
    /**
     * 查询门店单品
     *
     * @param id 门店单品ID
     * @return 门店单品
     */
    public TStoreSku selectTStoreSkuById(Long id);

    /**
     * 根据商品id查询门店单品关联
     *
     * @param spuId   商品id
     * @param storeId 门店id
     * @return 门店单品关联集合
     */
    List<StoreSku> queryStoreSkuListBySpuId(long spuId, long storeId);

    /**
     * 根据单品id查询门店单品关联
     *
     * @param skuId   单品id
     * @param storeId 门店id
     * @return 门店单品关联集合
     */
    List<StoreSku> queryStoreSkuListBySkuId(String skuId, long storeId);


    /**
     * 添加门店单品关联
     *
     * @param storeSkuList 门店单品关联集合
     * @param spuId        商品id
     * @param storeId      门店id
     * @return >0成功 否则失败
     */
    int addStoreSkuList(List<StoreSku> storeSkuList, long spuId, long storeId);

    /**
     * 减去门店单品的库存
     *
     * @param storeId 店铺id
     * @param skuId   单品id
     * @param num     购买的数量
     * @return 失败返回0 成功返回1
     */
    int reduceStoreSkusStock(Long storeId, String skuId, int num);

    /**
     * 查找门店单品详情
     *
     * @param skuId   单品id
     * @param storeId 门店id
     * @return 门店单品详情实体
     */
    StoreSkuDetail queryStoreSkuDetail(String skuId, long storeId);


    /**
     * 删除单品关联
     *
     * @param spuId spuId
     * @return 1 成功 0 失败
     */
    int deleteStoreSkuBySpuId(long spuId);

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
     * 批量删除门店单品
     *
     * @param ids 需要删除的门店单品ID
     * @return 结果
     */
    public int deleteTStoreSkuByIds(Long[] ids);

    /**
     * 删除门店单品信息
     *
     * @param id 门店单品ID
     * @return 结果
     */
    public int deleteTStoreSkuById(Long id);
}
