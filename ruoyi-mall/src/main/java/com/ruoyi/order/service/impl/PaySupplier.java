package com.ruoyi.order.service.impl;

/**
 * Created by 魔金商城 on 17/12/22.
 * 支付接口
 */
@FunctionalInterface
public interface PaySupplier<T, U, D> {

    /**
     * 获得支付返回的参数结果
     *
     * @return 返回支付参数结果
     */
    D getPayParams(T t, U u);
}
