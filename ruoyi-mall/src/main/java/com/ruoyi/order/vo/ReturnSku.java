package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 伊甸园商城 on 17/11/15.
 * 申请退货的单品
 */
@Data
public class ReturnSku {

    /**
     * 单品id
     */
    private String skuId;

    /**
     * 退货的数量
     */
    private int num;

    /**
     * 退货的价格
     */
    private BigDecimal price;

    /**
     * 可以退货的数量 - 用户想退货的数量 = 该值
     * 默认值为最小值
     */
    private int subNum = Integer.MIN_VALUE;

    /**
     * 订单下原始的单品个数-退货成功的单品个数 如果该值为0 则说明订单下该单品已经全部退货成功
     */
    private int leftover;

    /**
     * 判断该单品是否完成了退货
     *
     * @return leftover == 0 说明该单品全部退货成功
     */
    @JsonIgnore
    public boolean hasFinishReturn() {
        return this.leftover == 0;
    }

    /**
     * 判断该单品是否可以退货
     *
     * @return 如果subNum == Integer.MIN_VALUE 则说明该订单下可以退货的单品不包含该单品 返回false
     * 如果subNum 的值>=0 则说名该订单下该单品可以退货的数量 >= 用户想退货的数量 返回true
     */
    @JsonIgnore
    public boolean isCanReturn() {
        if (this.subNum == Integer.MIN_VALUE) {
            return false;
        }

        return this.subNum >= 0;
    }
}
