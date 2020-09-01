package com.ruoyi.goods.domain;


import java.math.BigDecimal;

/**
 * 门店单品关联实体
 *
 * @author SK
 * @since 2018/4/9
 */

public class StoreSku {

    /**
     * 主键id
     */

    private long id;

    /**
     * 门店id
     */

    private long storeId;

    /**
     * 商品id
     */

    private long spuId;

    /**
     * 单品id
     */

    private String skuId;

    /**
     * 价格
     */

    private BigDecimal price;

    /**
     * 库存
     */

    private int stock;


    /**
     * 构建门店id
     *
     * @param storeId 门店id
     * @return 门店单品关联实体
     */
    public StoreSku buildStoreId(long storeId) {
        this.storeId = storeId;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getSpuId() {
        return spuId;
    }

    public void setSpuId(long spuId) {
        this.spuId = spuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
