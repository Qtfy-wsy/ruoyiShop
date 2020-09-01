package com.ruoyi.store.service;

import com.ruoyi.store.domain.TStoreShoppingCart;

import java.util.List;

/**
 * 门店购物车Service接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface ITStoreShoppingCartService {
    /**
     * 查询门店购物车
     *
     * @param id 门店购物车ID
     * @return 门店购物车
     */
    public TStoreShoppingCart selectTStoreShoppingCartById(Long id);

    /**
     * 加入门店购物车
     *
     * @param storeShoppingCart 门店购物车信息
     * @return 成功返回1 失败返回0
     */
    int addShoppingCart(TStoreShoppingCart storeShoppingCart);

    /**
     * 查询用户门店购物车中单品的数量
     *
     * @param customerId 会员id
     * @param storeId    店铺id
     * @param skuId      单品id
     * @return 返回用户门店购物车中单品的数量
     */
    int queryCustomerSkuCount(long customerId, long storeId, String skuId);

    /**
     * 更新门店购物车中单品的数量(累加)
     *
     * @param storeShoppingCart 门店购物车信息
     * @return 成功返回1 失败返回0
     */
    int updateShoppingCartNum(TStoreShoppingCart storeShoppingCart);

    /**
     * 修改购物车的数量(直接修改)
     *
     * @param storeShoppingCart 门店购物车
     * @return 成功返回1 失败返回0
     */
    int directUpdateShoppingCartNum(TStoreShoppingCart storeShoppingCart);

    /**
     * 查询用户的门店购物车
     *
     * @param customerId 会员id
     * @return 返回用户的门店购物车
     */
    List<TStoreShoppingCart> queryByCustomerId(long customerId);


    /**
     * 根据门店购物车id查询门店购物车信息
     *
     * @param ids        购物车id
     * @param customerId 用户id
     * @return 返回购物车信息
     */
    List<TStoreShoppingCart> queryByIds(Long[] ids, Long customerId);

    /**
     * 查询会员门店购物车总数
     *
     * @param customerId 会员id
     * @return 返回会员的购物车总数
     */
    int queryStoreShoppingCartCount(long customerId);

    /**
     * 删除门店购物车
     *
     * @param customerId 会员id
     * @param ids        购物车ids
     * @return 成功返回1 失败返回0
     */
    int deleteStoreShoppingCart(long customerId, Long[] ids);

    /**
     * 查询门店购物车列表
     *
     * @param tStoreShoppingCart 门店购物车
     * @return 门店购物车集合
     */
    public List<TStoreShoppingCart> selectTStoreShoppingCartList(TStoreShoppingCart tStoreShoppingCart);

    /**
     * 新增门店购物车
     *
     * @param tStoreShoppingCart 门店购物车
     * @return 结果
     */
    public int insertTStoreShoppingCart(TStoreShoppingCart tStoreShoppingCart);

    /**
     * 修改门店购物车
     *
     * @param tStoreShoppingCart 门店购物车
     * @return 结果
     */
    public int updateTStoreShoppingCart(TStoreShoppingCart tStoreShoppingCart);

    /**
     * 批量删除门店购物车
     *
     * @param ids 需要删除的门店购物车ID
     * @return 结果
     */
    public int deleteTStoreShoppingCartByIds(Long[] ids);

    /**
     * 删除门店购物车信息
     *
     * @param id 门店购物车ID
     * @return 结果
     */
    public int deleteTStoreShoppingCartById(Long id);
}
