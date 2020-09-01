package com.ruoyi.store.vo;


import com.ruoyi.goods.domain.PmsSkuSpecValue;
import com.ruoyi.store.domain.TStoreReservation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by 魔金商城 on 18/4/10
 * 门店预约返回实体类
 */
@Data
@ApiModel(description = "门店预约返回实体类")
public class ReservationResponse {


    /**
     * 门店预约实体类
     */
    @ApiModelProperty(value = "门店预约实体类")
    private TStoreReservation reservation;

    /**
     * 单品规格值
     */
    @ApiModelProperty(value = "单品规格值")
    private List<PmsSkuSpecValue> skuSpecValues;

    /**
     * 构造返回实体
     *
     * @param reservation
     * @param skuSpecValues
     * @return
     */
    public ReservationResponse buildReservationResponse(TStoreReservation reservation, List<PmsSkuSpecValue> skuSpecValues) {
        this.reservation = reservation;
        this.skuSpecValues = skuSpecValues;
        return this;
    }

}
