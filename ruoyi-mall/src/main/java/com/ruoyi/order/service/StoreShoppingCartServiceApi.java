package com.ruoyi.order.service;


import com.ruoyi.order.vo.StoreShoppingCartResponse;
import com.ruoyi.store.domain.TStoreShoppingCart;

import java.util.List;

/**
 * Created by 魔金商城 on 2018/4/9.
 * 门店购物车服务接口
 */
public interface StoreShoppingCartServiceApi {


    /**
     * 加入购物车
     *
     * @param storeShoppingCart 门店购物车
     * @return 返回码: 1 成功 0 失败 -1 库存不足  -2 单品不存在 -3 参数错误 -4 单品已下架
     */
    int addShoppingCart(TStoreShoppingCart storeShoppingCart);

    /**
     * 查询会员门店购物车信息
     *
     * @param customerId 会员id
     * @return 返回会员门店购物车信息
     */
    List<StoreShoppingCartResponse> queryShoppingCarts(long customerId);

    /**
     * 根据门店购物车id查询门店购物车信息
     *
     * @param ids        购物车id
     * @param customerId 用户id
     * @return 返回购物车信息
     */
    List<StoreShoppingCartResponse> queryShoppingCartsByIds(Long[] ids, Long customerId);

    /**
     * 根据门店预约id查询门店购物车信息
     *
     * @param reservationIds 门店预约id
     * @param customerId     用户id
     * @param storeId        门店id
     * @return 返回门店购物车信息
     */
    List<StoreShoppingCartResponse> queryShoppingCartsByReservationIds(Long[] reservationIds, Long customerId, long storeId);
}
