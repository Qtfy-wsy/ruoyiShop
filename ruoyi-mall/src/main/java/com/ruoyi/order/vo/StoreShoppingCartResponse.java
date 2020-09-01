package com.ruoyi.order.vo;


import com.ruoyi.store.domain.TStoreInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 魔金商城 on 2018/4/9.
 * 门店购物车响应实体
 */
@Data
@ApiModel(description = "门店购物车响应实体")
public class StoreShoppingCartResponse {
    /**
     * 店铺id
     */
    @ApiModelProperty(value = "主键id")
    private long storeId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 是否选中 默认false
     */
    @ApiModelProperty(value = "是否选中 默认false")
    private boolean checked = false;

    /**
     * 购物车中的单品信息
     */
    @ApiModelProperty(value = "购物车中的单品信息")
    private List<SkuResponse> normalSkus;

    /**
     * 有促销的单品信息 暂时没用
     */
    @ApiModelProperty(value = "有促销的单品信息 暂时没用")
    private List<MarketingResponse> marketings = new ArrayList<>();

    /**
     * 构造门店购物车响应实体
     *
     * @param storeInfo    店铺信息
     * @param skuResponses 单品信息
     * @return 返回门店购物车响应实体
     */
    public static StoreShoppingCartResponse build(TStoreInfo storeInfo, List<SkuResponse> skuResponses) {
        StoreShoppingCartResponse storeShoppingCartResponse = new StoreShoppingCartResponse();
        storeShoppingCartResponse.normalSkus = skuResponses;
        if (Objects.isNull(storeInfo)) {
            return storeShoppingCartResponse;
        } else {
            if (skuResponses == null || skuResponses.size() == 0) {
                return null;
            }
            storeShoppingCartResponse.storeId = storeInfo.getId();
            storeShoppingCartResponse.storeName = storeInfo.getStoreName();
        }
        return storeShoppingCartResponse;
    }
}
