package com.ruoyi.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 魔金商城 on 2018/4/10.
 * 门店结算实体类
 */
@Data
@ApiModel(description = "门店结算实体类")
public class StoreOrderSettlement {

    /**
     * 门店购物车id
     */
    @ApiModelProperty(value = "门店购物车id")
    private Long[] ids;

    /**
     * 每个门店的结算信息
     */
    @ApiModelProperty(value = "每个门店的结算信息")
    private List<StoreStoreSettlement> storeStoreSettlements;

    /**
     * 订单总的价格
     */
    @ApiModelProperty(value = "订单总的价格")
    private BigDecimal totalPrice;

    /**
     * 预约id
     */
    @ApiModelProperty(value = "预约id")
    private Long[] reservationIds;


    /**
     * 构造门店结算实体
     *
     * @param ids                   门店购物车id
     * @param storeStoreSettlements 门店结算实体
     * @return 返回门店结算实体
     */
    public static StoreOrderSettlement build(List<StoreStoreSettlement> storeStoreSettlements, Long[] ids, Long[] reservationIds) {
        StoreOrderSettlement storeOrderSettlement = new StoreOrderSettlement();
        storeOrderSettlement.ids = ids;
        storeOrderSettlement.reservationIds = reservationIds;
        if (CollectionUtils.isEmpty(storeStoreSettlements)) {
            return storeOrderSettlement;
        }
        storeOrderSettlement.storeStoreSettlements = storeStoreSettlements;
        storeOrderSettlement.totalPrice = storeStoreSettlements.stream().map(StoreStoreSettlement::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return storeOrderSettlement;
    }

    /**
     * 检查是否有单品信息
     *
     * @return 有返回true  没有返回fasle
     */
    public boolean validateSkus() {
        return !CollectionUtils.isEmpty(this.storeStoreSettlements);
    }

}
