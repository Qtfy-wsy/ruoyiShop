package com.ruoyi.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 伊甸园商城 on 17/5/16.
 * 商品搜索的条件
 */
@Data
@ApiModel(description = "商品搜索的条件")
public class SpuSearchCondition {

    /**
     * 商品编号
     */
    @ApiModelProperty(value = "商品编号")
    private String id;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;

    /**
     * 商品品牌
     */
    @ApiModelProperty(value = "商品品牌")
    private long brandId;

    /**
     * 一级分类id
     */
    @ApiModelProperty(value = "一级分类id")
    private long firstCateId;

    /**
     * 二级分类id
     */
    @ApiModelProperty(value = "二级分类id")
    private long secondCateId;

    /**
     * 三级分类id
     */
    @ApiModelProperty(value = "三级分类id")
    private long thirdCateId;

    /**
     * 店铺商品一级分类
     */
    @ApiModelProperty(value = "店铺商品一级分类")
    private long storeFcateId;

    /**
     * 店铺商品二级分类
     */
    @ApiModelProperty(value = "店铺商品二级分类")
    private long storeScateId;

    /**
     * 店铺商品三级分类
     */
    @ApiModelProperty(value = "店铺商品三级分类")
    private long storeTcateId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 商品的状态 审核状态 0 审核通过 1 审核未通过 2 审核中
     */
    @ApiModelProperty(value = "商品的状态")
    private String status;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 商品上下架状态 0 下架 1上架 2违规下架 默认0
     */
    @ApiModelProperty(value = "商品上下架状态")
    private String shelvesStatus;

    /**
     * 是否虚拟商品
     */
    @ApiModelProperty(value = "是否虚拟商品")
    private int isVirtual = -1;

    private String brandIds;
    private String typeIds;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    // 1时间倒序 2 价格倒序 3 价格升序
    private int orderBys = 1;
    /**
     * 获得搜索的参数
     *
     * @return 返回搜索参数
     */
    public Map<String, Object> getSearchMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("brandId", brandId);
        params.put("firstCateId", firstCateId);
        params.put("secondCateId", secondCateId);
        params.put("thirdCateId", thirdCateId);
        params.put("storeFcateId", storeFcateId);
        params.put("storeScateId", storeScateId);
        params.put("storeTcateId", storeTcateId);
        params.put("storeId", storeId);
        params.put("id", id);
        params.put("status", status);
        params.put("storeName", storeName);
        params.put("shelvesStatus", shelvesStatus);
        params.put("isVirtual", isVirtual);
        params.put("minPrice", minPrice);
        params.put("maxPrice", maxPrice);
        params.put("orderBys", orderBys);
       /* if (!ObjectUtils.isEmpty(brandIds)){
            params.put("brandIds", ((brandIds.substring(0,brandIds.length() - 1).split(","))));
        }
        if (!ObjectUtils.isEmpty(typeIds)) {
            params.put("typeIds", (typeIds.substring(0,typeIds.length() - 1).split(",")));
        }*/
        if (!ObjectUtils.isEmpty(brandIds)){
            params.put("brandIds", Arrays.asList((brandIds.split(","))));
        }
        if (!ObjectUtils.isEmpty(typeIds)) {
            params.put("typeIds", Arrays.asList(typeIds.split(",")));
        }
        return params;
    }
}
