package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by 魔金商城 on 2018/4/10.
 * 门店结算店铺实体
 */
@Data
public class StoreStoreSettlement {

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 门店下商品的总价
     */
    private BigDecimal totalPrice = new BigDecimal(0);

    /**
     * 店铺订单的购物车信息
     */
    private StoreShoppingCartResponse storeShoppingCartResponse;

    /**
     * 店铺一共有几件商品
     */
    private int skuNum;


    /**
     * 备注
     */
    private String remark;


    /**
     * 取货时间
     */
    private String pickTime;

    /**
     * 构造门店结算信息
     *
     * @param storeShoppingCartResponse 门店购物车信息
     * @return 返回门店结算信息
     */
    public static StoreStoreSettlement build(StoreShoppingCartResponse storeShoppingCartResponse) {
        if (Objects.isNull(storeShoppingCartResponse)) {
            return null;
        }
        StoreStoreSettlement storeStoreSettlement = new StoreStoreSettlement();
        storeStoreSettlement.storeName = storeShoppingCartResponse.getStoreName();
        storeStoreSettlement.storeId = storeShoppingCartResponse.getStoreId();
        storeStoreSettlement.storeShoppingCartResponse = storeShoppingCartResponse;
        storeStoreSettlement.totalPrice = storeShoppingCartResponse.getNormalSkus().stream().map(SkuResponse::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        storeStoreSettlement.skuNum = storeShoppingCartResponse.getNormalSkus().stream().map(skuResponse -> new BigDecimal(skuResponse.getNum())).reduce(BigDecimal.ZERO, BigDecimal::add).intValue();
        return storeStoreSettlement;
    }

    /**
     * 获得所有的购物车id
     *
     * @return 返回购物车id
     */
    @JsonIgnore
    public Long[] getCartIds() {

        if (Objects.isNull(storeShoppingCartResponse) || CollectionUtils.isEmpty(storeShoppingCartResponse.getNormalSkus())) {
            return null;
        }

        return storeShoppingCartResponse.getNormalSkus().stream().map(skuResponse -> Long.valueOf(skuResponse.getCartId())).toArray(Long[]::new);
    }
}
