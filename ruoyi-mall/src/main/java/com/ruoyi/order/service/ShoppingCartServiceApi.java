package com.ruoyi.order.service;


import com.ruoyi.order.domain.OmsShoppingCart;
import com.ruoyi.order.vo.ShoppingCartResponse;
import com.ruoyi.order.vo.SkuResponse;

import java.util.List;

/**
 * Created by 伊甸园商城 on 17/8/9.
 * 购物车服务接口(混合api)
 */
public interface ShoppingCartServiceApi {


    /**
     * 加入购物车
     *
     * @param shoppingCart 购物车
     * @return 返回码: 1 成功 0 失败 -1 库存不足  -2 单品不存在 -3 参数错误 -4 单品已下架 -5 超出商品抢购限购数量 -6 预售商品不能加入购物车 -7 店铺状态异常
     */
    int addShoppingCart(OmsShoppingCart shoppingCart);

    /**
     * 查询会员购物车信息
     *
     * @param customerId 会员id
     * @return 返回会员购物车信息
     */
    List<ShoppingCartResponse> queryShoppingCarts(long customerId);

    /**
     * 根据购物车id查询购物车信息
     *
     * @param ids        购物车id
     * @param customerId 用户id
     * @return 返回购物车信息
     */
    List<ShoppingCartResponse> queryShoppingCartsByIds(Long[] ids, Long customerId);

    /**
     * 根据单品id查询购物车信息(立即购买场景下使用,数据库中没有真正的购物车纪录)
     *
     * @param skuInfo    单品信息  立即购买的时候使用格式为skuId,num   12131231,2 格式
     * @param customerId 会员id
     * @return 返回购物车信息
     */
    List<ShoppingCartResponse> queryShoppingCartsBySkuId(String skuInfo, Long customerId, Long crowdfundingId);

    /**
     * 查询用户迷你购物车 （购物车和门店购物车）
     *
     * @param customerId 会员id
     * @return 返回用户的迷你购物车
     */
    List<SkuResponse> queryMiniShoppingCarts(long customerId);
}
