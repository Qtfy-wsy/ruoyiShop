package com.ruoyi.order.service;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 伊甸园商城 on 2019/1/16.
 * 批量下单返回实体
 */
@Data
public class BatchOrderSubmitResponse {

    /**
     * 返回结果 -1 没有一件代发的权限 -2 参数错误 -3 单品有误 单品不存在或者库存不足 1 成功
     */
    private int result;

    /**
     * 是否需要支付(0 需要  1不需要)
     */
    private int needPay;

    /**
     * 主订单号
     */
    private String masterCode;

    /**
     * 总价格
     */
    private BigDecimal totalPrice;


    /**
     * 构造成功的结果
     *
     * @param masterCode 主订单号
     * @param totalPrice 总价格
     * @return 返回成功的结果
     */
    public static BatchOrderSubmitResponse buildSuccess(String masterCode, BigDecimal totalPrice) {
        BatchOrderSubmitResponse batchOrderSubmitResponse = new BatchOrderSubmitResponse();
        batchOrderSubmitResponse.setResult(1);
        batchOrderSubmitResponse.setMasterCode(masterCode);
        batchOrderSubmitResponse.setTotalPrice(totalPrice);
        return batchOrderSubmitResponse;
    }

    /**
     * 构造不需要支付的返回实体
     *
     * @param masterCode 主订单号
     * @return 返回不需要支付的实体
     */
    public static BatchOrderSubmitResponse buildSuccessNoNeedPayResponse(String masterCode) {
        BatchOrderSubmitResponse batchOrderSubmitResponse = new BatchOrderSubmitResponse();
        batchOrderSubmitResponse.needPay = 1;
        batchOrderSubmitResponse.setResult(1);
        batchOrderSubmitResponse.totalPrice = BigDecimal.ZERO;
        batchOrderSubmitResponse.masterCode = masterCode;
        return batchOrderSubmitResponse;
    }

    /**
     * 构造需要支付的返回实体
     *
     * @param masterCode 主订单号
     * @param totalPrice 总价格
     * @return 返回实体
     */
    public static BatchOrderSubmitResponse buildSuccessNeedPayResponse(String masterCode, BigDecimal totalPrice) {
        BatchOrderSubmitResponse batchOrderSubmitResponse = new BatchOrderSubmitResponse();
        batchOrderSubmitResponse.needPay = 0;
        batchOrderSubmitResponse.setResult(1);
        batchOrderSubmitResponse.totalPrice = totalPrice;
        batchOrderSubmitResponse.masterCode = masterCode;
        return batchOrderSubmitResponse;
    }

    /**
     * 构造没有一件代发权限的失败结果
     *
     * @return 返回没有一件代发权限的失败结果
     */
    public static BatchOrderSubmitResponse buildPermissionFail() {
        BatchOrderSubmitResponse batchOrderSubmitResponse = new BatchOrderSubmitResponse();
        batchOrderSubmitResponse.result = -1;
        return batchOrderSubmitResponse;
    }

    /**
     * 构造单品有错误的失败结果
     *
     * @return 返回单品有错误的失败结果
     */
    public static BatchOrderSubmitResponse buildSkuInfoFail() {
        BatchOrderSubmitResponse batchOrderSubmitResponse = new BatchOrderSubmitResponse();
        batchOrderSubmitResponse.result = -3;
        return batchOrderSubmitResponse;
    }

    /**
     * 构造参数错误的返回结果
     *
     * @return 返回参数错误的返回结果
     */
    public static BatchOrderSubmitResponse buildParamsFail() {
        BatchOrderSubmitResponse batchOrderSubmitResponse = new BatchOrderSubmitResponse();
        batchOrderSubmitResponse.result = -2;
        return batchOrderSubmitResponse;
    }
}
