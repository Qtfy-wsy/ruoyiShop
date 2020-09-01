package com.ruoyi.order.vo;

import com.ruoyi.order.domain.OmsShoppingCart;
import lombok.Data;


/**
 * Created by 魔金商城 on 17/7/5.
 * cookie中的购物车信息
 */
@Data
public class CookieShopingCart {

    /**
     * 单品id
     */
    private String skuId;

    /**
     * 购买数量
     */
    private int num;

    /**
     * 促销id
     */
    private long marketingId;

    /**
     * 构造cookie中的购物车信息
     *
     * @param shoppingCart 购物车信息
     * @return cookie中的购物车信息
     */
    public static CookieShopingCart build(OmsShoppingCart shoppingCart) {
        CookieShopingCart cookieShopingCart = new CookieShopingCart();
        cookieShopingCart.skuId = shoppingCart.getSkuId();
        cookieShopingCart.num = shoppingCart.getNum();
        cookieShopingCart.marketingId = shoppingCart.getMarketingId();
        return cookieShopingCart;
    }

    /**
     * 更新数量
     *
     * @param addNum 更新的值
     */
    public void updateNum(int addNum) {
        this.num = this.num + addNum;
    }

}
