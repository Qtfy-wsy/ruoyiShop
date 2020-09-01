package com.ruoyi.store.vo;


import com.ruoyi.goods.domain.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 门店商品详情（前台用）
 */
@Data
@ApiModel(description = "门店商品详情（前台用）")
public class StoreSkuDetail {

    /**
     * 单品id
     */
    @ApiModelProperty(value = "单品id")
    private String id;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private long spuId;

    /**
     * 单品名称
     */
    @ApiModelProperty(value = "单品名称")
    private String name;

    /**
     * 单品副标题
     */
    @ApiModelProperty(value = "单品副标题")
    private String subTitle;


    /**
     * 单品价格(门店价)
     */
    @ApiModelProperty(value = "单品价格(门店价)")
    private BigDecimal price;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private int stock;

    /**
     * 单品规格值
     */
    @ApiModelProperty(value = "单品规格值")
    private List<PmsSkuSpecValue> skuSpecValues;

    /**
     * 单品图片
     */
    @ApiModelProperty(value = "单品图片")
    private List<String> images;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 单品编号
     */
    @ApiModelProperty(value = "单品编号")
    private String skuNo;

    /**
     * 商品的规格信息
     */
    @ApiModelProperty(value = "商品的规格信息")
    private List<PmsSpec> specs;

    /**
     * 分类信息
     */
    @ApiModelProperty(value = "分类信息")
    private List<PmsCategory> categories;

    /**
     * 构造门店单品详情信息
     *
     * @param storeSku 门店单品信息
     * @param sku      单品信息
     * @return 返回门店单品详情
     */
    public static StoreSkuDetail build(StoreSku storeSku, PmsSku sku) {
        StoreSkuDetail storeSkuDetail = new StoreSkuDetail();
        storeSkuDetail.id = sku.getId();
        storeSkuDetail.spuId = sku.getSpuId();
        storeSkuDetail.name = sku.getName();
        storeSkuDetail.subTitle = sku.getSubtitle();
        storeSkuDetail.price = storeSku.getPrice();
        storeSkuDetail.stock = storeSku.getStock();
        storeSkuDetail.storeId = sku.getStoreId();
        storeSkuDetail.skuNo = sku.getSkuNo();
        if (!CollectionUtils.isEmpty(sku.getSkuImages())) {
            storeSkuDetail.images = sku.getSkuImages().stream().map(PmsSkuImage::getUrl).collect(Collectors.toList());
        }
        storeSkuDetail.skuSpecValues = sku.getSkuSpecValues();
        return storeSkuDetail;
    }

    /**
     * 构建分类信息
     *
     * @param categories 分类信息
     * @return 门店单品详情
     */
    public StoreSkuDetail buildCategories(List<PmsCategory> categories) {
        this.categories = categories;
        return this;
    }

    /**
     * 构建商品的规格信息
     *
     * @param specs 商品的规格信息
     * @return 门店单品详情
     */
    public StoreSkuDetail buildSpecs(List<PmsSpec> specs) {
        this.specs = specs;
        return this;
    }
}
