package com.ruoyi.order.vo;

import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.domain.TStoreOrder;
import lombok.Data;

/**
 * 门店订单详情实体
 *
 * @author SK
 * @since 2018/4/10
 */
@Data
public class StoreOrderDetail {

    /**
     * 门店订单实体
     */
    private TStoreOrder storeOrder;

    /**
     * 店铺信息实体
     */
    private TStoreInfo storeInfo;


    /**
     * 构建门店订单详情
     *
     * @param storeOrder 门店订单实体
     * @param storeInfo  店铺信息实体
     * @return 门店订单详情实体
     */
    public static StoreOrderDetail build(TStoreOrder storeOrder, TStoreInfo storeInfo) {
        StoreOrderDetail storeOrderDetail = new StoreOrderDetail();
        storeOrderDetail.storeOrder = storeOrder;
        storeOrderDetail.storeInfo = storeInfo;
        return storeOrderDetail;
    }

}