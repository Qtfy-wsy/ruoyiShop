package com.ruoyi.order.service;


import com.ruoyi.order.vo.BatchOrderResponse;
import com.ruoyi.order.vo.BatchOrderSku;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 魔金商城 on 2019/1/15.
 * 批量下单接口
 */
public interface BatchOrderApi {

    /**
     * 批量导入下单的商品
     *
     * @param inputStream 输入流
     * @param customerId  会员id
     */
    BatchOrderResponse importBatch(InputStream inputStream, long customerId) throws IOException;


    /**
     * 批量下单
     *
     * @param batchOrderSkus 批量下单的单品信息
     * @param customerId     会员id
     * @return 返回结果 详情见BatchOrderSubmitResponse
     */
    BatchOrderSubmitResponse batchOrderSubmit(List<BatchOrderSku> batchOrderSkus, long customerId);

    /**
     * 计算运费
     *
     * @param batchOrderSkuList 批量下单单品列表
     * @return 运费
     */
    BigDecimal calculateFright(List<BatchOrderSku> batchOrderSkuList);
}
