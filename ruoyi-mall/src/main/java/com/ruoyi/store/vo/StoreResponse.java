package com.ruoyi.store.vo;

import com.ruoyi.goods.domain.PmsBrand;
import com.ruoyi.store.domain.TStoreInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by 伊甸园商城 on 17/11/23.
 * 店铺新返回实体
 */
@Data
@ApiModel(description = "店铺新返回实体")
public class StoreResponse {

    /**
     * 店铺信息
     */
    @ApiModelProperty(value = "店铺信息")
    private TStoreInfo storeInfo;

    /**
     * 所有商品的总数
     */
    @ApiModelProperty(value = "所有商品的总数")
    private int allSkuNum;

    /**
     * 新品上架的总数
     */
    @ApiModelProperty(value = "新品上架的总数")
    private int newSkuNum;

    /**
     * 促销商品的总数
     */
    @ApiModelProperty(value = "促销商品的总数")
    private int marketSkuNum;

    /**
     * 店铺被关注的数量
     */
    @ApiModelProperty(value = "店铺被关注的数量")
    private int followNum;

    /**
     * 店铺总的销量
     */
    @ApiModelProperty(value = "店铺总的销量")
    private int saleNum;

    /**
     * 店铺签约的品牌
     */
    @ApiModelProperty(value = "店铺签约的品牌")
    private List<PmsBrand> brands;

    /**
     * 构造店铺信息
     *
     * @param storeInfo 店铺信息
     * @return 返回店铺信息
     */
    public static StoreResponse build(TStoreInfo storeInfo) {
        StoreResponse storeResponse = new StoreResponse();
        storeResponse.storeInfo = storeInfo;
        return storeResponse;
    }
}
