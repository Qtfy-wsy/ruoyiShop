package com.ruoyi.order.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 2020/4/26.
 * 运费实体
 */
@Data
@Builder
public class FreightPrice {

    /**
     * 首件/首重的价格
     */
    private BigDecimal firstPrice = BigDecimal.ZERO;

    /**
     * 续件/续重的价格
     */
    private BigDecimal continuationPrice = BigDecimal.ZERO;

    /**
     * 全部都是续件/续重的价格
     */
    private BigDecimal allContinuationPrice = BigDecimal.ZERO;

    @Tolerate
    FreightPrice() {
    }

    /**
     * 构建不需要运费
     *
     * @return 返回不需要运费的实体
     */
    public static FreightPrice buildNoFreightPrice() {
        return null;
    }

    public static void main(String[] args) {
        List<FreightPrice> freightPrices = new ArrayList<>();
        freightPrices.add(FreightPrice.builder().firstPrice(new BigDecimal(1)).build());
        freightPrices.add(FreightPrice.builder().firstPrice(new BigDecimal(2)).build());
        freightPrices.add(FreightPrice.builder().firstPrice(new BigDecimal(3)).build());
        freightPrices.add(FreightPrice.builder().firstPrice(new BigDecimal(4)).build());
        freightPrices.add(FreightPrice.builder().firstPrice(new BigDecimal(5)).build());
        freightPrices.add(FreightPrice.builder().firstPrice(new BigDecimal(5)).build());
        List<FreightPrice> a = freightPrices.stream().sorted((o1, o2) -> o2.getFirstPrice().compareTo(o1.getFirstPrice())).collect(Collectors.toList());
        System.out.print(a.remove(0));

    }

}
