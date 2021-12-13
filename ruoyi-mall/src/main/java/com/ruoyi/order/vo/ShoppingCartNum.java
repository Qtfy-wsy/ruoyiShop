package com.ruoyi.order.vo;

import lombok.Data;

/**
 * Created by 伊甸园商城 on 2018/4/9.
 * 购物车数量实体类
 */
@Data
public class ShoppingCartNum {

    /**
     * 商城购物车总数
     */
    private int shoppingCartNum = 0;

    /**
     * 门店购物车总数
     */
    private int storeShoppingCartNum = 0;

    /**
     * 构造购物车数量返回实体
     *
     * @param shoppingCartNum 购物车数量
     * @return 返回购物车数量
     */
    public static ShoppingCartNum build(int shoppingCartNum, int storeShoppingCartNum) {
        ShoppingCartNum shoppingCartNum1 = new ShoppingCartNum();
        shoppingCartNum1.shoppingCartNum = shoppingCartNum;
        shoppingCartNum1.storeShoppingCartNum = storeShoppingCartNum;
        return shoppingCartNum1;
    }

    /**
     * 获得购物车总数 （门店+商城购物车）
     *
     * @return 返回购物车总数
     */
    public int getAllNum() {
        return this.shoppingCartNum + this.storeShoppingCartNum;
    }

}
