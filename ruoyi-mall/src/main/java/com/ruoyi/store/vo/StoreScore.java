package com.ruoyi.store.vo;


import com.ruoyi.store.domain.TStoreInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 店铺评分实体
 */
@Data
@ApiModel(description = "店铺评分实体")
public class StoreScore {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 描述评分
     */
    @ApiModelProperty(value = "描述评分")
    private BigDecimal descScore;

    /**
     * 卖家服务评分
     */
    @ApiModelProperty(value = "卖家服务评分")
    private BigDecimal sellerScore;

    /**
     * 物流评分
     */
    @ApiModelProperty(value = "物流评分")
    private BigDecimal logisticsScore;

    /**
     * 店铺信息
     */
    @ApiModelProperty(value = "店铺信息")
    private TStoreInfo storeInfo;


    /**
     * 构建店铺信息
     *
     * @param storeInfo 店铺信息
     * @return 店铺评分实体
     */
    public StoreScore buildStoreInfo(TStoreInfo storeInfo) {
        this.storeInfo = storeInfo;
        return this;
    }

}
