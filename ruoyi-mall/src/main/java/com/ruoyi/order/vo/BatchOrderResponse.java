package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 2019/1/15.
 * 订单批量导入结果
 */
@Data
public class BatchOrderResponse {

    /**
     * 导入结果 -1 超出100条纪录限制 1 成功 -2 没有内容 -3 格式错误 -4 用户没有一件代发权限
     */
    private int result;
    /**
     * 所有的批量订单导入的订单
     */
    @JsonIgnore
    private List<BatchOrderSku> batchOrderSkus;
    /**
     * 有错误的订单
     */
    private List<BatchOrderSku> errorOrderSkus;
    /**
     * 成功的订单
     */
    private List<BatchOrderSku> successOrderSkus;
    /**
     * 总运费
     */
    private BigDecimal totalFreight;

    private BatchOrderResponse() {

    }

    /**
     * 构造超出100条纪录限制的结果
     *
     * @return 返回  构造超出100条纪录限制的结果
     */
    public static BatchOrderResponse buildRowLimitFail() {
        BatchOrderResponse batchOrderResponse = new BatchOrderResponse();
        batchOrderResponse.result = -1;
        return batchOrderResponse;
    }

    /**
     * 构造没有内容的失败结果
     *
     * @return 返回没有内容的失败结果
     */
    public static BatchOrderResponse buildNoContentFail() {
        BatchOrderResponse batchOrderResponse = new BatchOrderResponse();
        batchOrderResponse.result = -2;
        return batchOrderResponse;
    }

    /**
     * 构造格式错误的失败结果
     *
     * @return 返回格式错误的失败结果
     */
    public static BatchOrderResponse buildFormatFail() {
        BatchOrderResponse batchOrderResponse = new BatchOrderResponse();
        batchOrderResponse.result = -3;
        return batchOrderResponse;
    }

    /**
     * 构造成功的结果
     *
     * @param batchOrderSkus 批量订单
     * @return 成功结果
     */
    public static BatchOrderResponse buildSuccess(List<BatchOrderSku> batchOrderSkus) {
        BatchOrderResponse batchOrderResponse = new BatchOrderResponse();
        batchOrderResponse.result = 1;
        batchOrderResponse.batchOrderSkus = batchOrderSkus;
        return batchOrderResponse;
    }

    /**
     * 构造没有一件代发权限的失败结果
     *
     * @return 返回没有一件代发权限的失败结果
     */
    public static BatchOrderResponse buildPermissionFail() {
        BatchOrderResponse batchOrderResponse = new BatchOrderResponse();
        batchOrderResponse.result = -4;
        return batchOrderResponse;
    }

    /**
     * 是否成功
     *
     * @return 成功返回true  失败返回false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return 1 == result;
    }

    /**
     * 设置成功和失败的订单
     */
    public BatchOrderResponse setSuccessAndErrorOrderSkus() {
        // 解析不成功直接返回
        if (!this.isSuccess() || CollectionUtils.isEmpty(batchOrderSkus)) {
            return this;
        }

        // 成功的
        this.successOrderSkus = this.batchOrderSkus.stream().filter(BatchOrderSku::isEffective).collect(Collectors.toList());

        // 失败的
        this.errorOrderSkus = this.batchOrderSkus.stream().filter(batchOrderSku -> !batchOrderSku.isEffective()).collect(Collectors.toList());

        // 所有清零
        this.batchOrderSkus = Collections.emptyList();
        return this;
    }
}
