package com.ruoyi.goods.domain;

import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 门店商品实体
 *
 * @author SK
 * @since 2018/4/9
 */

public class StoreSpu {

    /**
     * 商品id
     */

    private long spuId;

    /**
     * 商品名称
     */

    private String name;

    /**
     * 商品图片地址
     */

    private String url;

    /**
     * 单品规格值
     */

    private List<PmsGoodsSpecValue> spuSpecValues;

    /**
     * 单品集合
     */

    private List<PmsSku> skuList;

    /**
     * 单品最低价
     */

    private BigDecimal minPrice;

    /**
     * 单品最高价
     */

    private BigDecimal maxPrice;

    /**
     * 门店单品关联实体集合
     */

    private List<StoreSku> storeSkuList;

    /**
     * 库存
     */

    private int stock = 0;

    /**
     * 访问地址
     */

    private String visitUrl;

    /**
     * 设置其他信息
     */
    public StoreSpu buildOtherInfo(List<StoreSku> storeSkuList) {
        if (!CollectionUtils.isEmpty(storeSkuList)) {
            this.storeSkuList = storeSkuList.stream().sorted().collect(Collectors.toList());
            this.minPrice = this.storeSkuList.get(0).getPrice();
            this.maxPrice = this.storeSkuList.get(this.storeSkuList.size() - 1).getPrice();
            this.stock = this.storeSkuList.stream().map(StoreSku::getStock).reduce(0, (x, y) -> x + y);
        }
        return this;
    }

    public long getSpuId() {
        return spuId;
    }

    public void setSpuId(long spuId) {
        this.spuId = spuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<PmsGoodsSpecValue> getSpuSpecValues() {
        return spuSpecValues;
    }

    public void setSpuSpecValues(List<PmsGoodsSpecValue> spuSpecValues) {
        this.spuSpecValues = spuSpecValues;
    }

    public List<PmsSku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<PmsSku> skuList) {
        this.skuList = skuList;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<StoreSku> getStoreSkuList() {
        return storeSkuList;
    }

    public void setStoreSkuList(List<StoreSku> storeSkuList) {
        this.storeSkuList = storeSkuList;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }
}
