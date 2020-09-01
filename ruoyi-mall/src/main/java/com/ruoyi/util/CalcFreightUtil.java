package com.ruoyi.util;


import com.ruoyi.goods.domain.PmsShippingMethod;
import com.ruoyi.order.domain.OmsLogisticsTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * 运费计算工具类
 */
public class CalcFreightUtil {


    /**
     * 计算运费
     *
     * @param logisticsTemplate 运费模版
     * @param num               商品数量
     * @param weight            单件商品重量
     * @return 运费
     */
    public static BigDecimal calcFreightPrice(OmsLogisticsTemplate logisticsTemplate, int num, BigDecimal weight) {
        // 如果是商家承担运费 则直接返回 运费不计算
        if (logisticsTemplate.isStoreBearFreight()) {
            return new BigDecimal(0L);
        }

        // 获得运费的运费方式
        PmsShippingMethod shippingMethod = logisticsTemplate.getCustomerShippingMethod();

        // 没有运费方式直接返回
        if (Objects.isNull(shippingMethod)) {
            return new BigDecimal(0L);
        }

        // 计算运费
        switch (logisticsTemplate.getPricintMethod()) {
            case "0":
                // 按件
                return calcNumFreightPrice(shippingMethod, num);
            case "1":
                // 按重量
                return calcWeightFreightPrice(shippingMethod, weight.multiply(new BigDecimal(num)));
            default:
                // 不计算运费
                return new BigDecimal(0L);
        }
    }

    /**
     * 计算按件算的运费
     *
     * @param shippingMethod 运费方式
     * @param num            商品数量
     * @return 返回运费
     */
    private static BigDecimal calcNumFreightPrice(PmsShippingMethod shippingMethod, int num) {
        return calcCommonFreightPrice(shippingMethod, new BigDecimal(num));
    }

    /**
     * 计算按重量算的运费
     *
     * @param shippingMethod 运费方式
     * @param weight         商品重量
     * @return 返回运费
     */
    private static BigDecimal calcWeightFreightPrice(PmsShippingMethod shippingMethod, BigDecimal weight) {
        return calcCommonFreightPrice(shippingMethod, weight);
    }

    /**
     * 计算运费的通用方法
     *
     * @param shippingMethod 运费方式
     * @param factor         可变因子
     * @return 返回运费
     */
    private static BigDecimal calcCommonFreightPrice(PmsShippingMethod shippingMethod, BigDecimal factor) {
        if (factor.compareTo(new BigDecimal(shippingMethod.getFirst())) < 0) {
            return shippingMethod.getMoney();
        }
        return shippingMethod.getMoney().add(factor.subtract(new BigDecimal(shippingMethod.getFirst())).multiply(shippingMethod.getMoenyPlu().divide(new BigDecimal(shippingMethod.getFirstPlu()), 10, BigDecimal.ROUND_HALF_EVEN))).setScale(2, RoundingMode.HALF_EVEN);
    }
}
