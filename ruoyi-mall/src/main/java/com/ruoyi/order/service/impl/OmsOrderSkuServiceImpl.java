package com.ruoyi.order.service.impl;

import com.ruoyi.order.domain.OmsOrderSku;
import com.ruoyi.order.mapper.OmsOrderSkuMapper;
import com.ruoyi.order.service.IOmsOrderSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单单品Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class OmsOrderSkuServiceImpl implements IOmsOrderSkuService {
    @Autowired
    private OmsOrderSkuMapper omsOrderSkuMapper;
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(OmsOrderSkuServiceImpl.class);

    /**
     * 订单单品数据库接口
     */
    @Autowired
    private OmsOrderSkuMapper orderSkuMapper;

    @Override
    public List<OmsOrderSku> queryByOrderId(long orderId) {
        logger.debug("queryByOrderId and orderId:{}", orderId);
        return orderSkuMapper.queryByOrderId(orderId);
    }

    @Override
    public List<OmsOrderSku> queryByOrderIdAndSkuIds(long orderId, List<String> skuIds) {
        logger.debug("queryByOrderCodeAndSkuIds and orderId:{} \r\n skuIds:{}", orderId, skuIds);

        if (CollectionUtils.isEmpty(skuIds)) {
            logger.error("queryByOrderCodeAndSkuIds fail due to params is empty....");
            return Collections.emptyList();
        }

        return skuIds.stream().map(skuId -> queryByOrderIdAndSkuId(orderId, skuId)).collect(Collectors.toList());

    }

    @Override
    public int saveOrderSkus(List<OmsOrderSku> orderSkus) {
        logger.debug("saveOrderSkus and orderSkus:{}", orderSkus);

        if (CollectionUtils.isEmpty(orderSkus)) {
            logger.error("saveOrderSkus fail...");
            return 0;
        }

        // 设置每个单品的优惠价格详情
        orderSkus.stream().forEach(orderSku -> orderSku.setSkuPriceDetail());
        return orderSkuMapper.saveOrderSkus(orderSkus);
    }

    @Transactional
    @Override
    public int updateOrderSkusPrice(List<OmsOrderSku> orderSkus) {
        logger.debug("updateOrderSkusPrice and orderSkus:{}", orderSkus);

        if (CollectionUtils.isEmpty(orderSkus)) {
            logger.error("updateOrderSkusPrice fail due to orderSkus is empty....");
            return 0;
        }

        // 修改订单单品金额
        orderSkus.stream().forEach(this::updateOrderSkuPrice);

        return 1;
    }

    @Override
    public int querySkuSaleCount(String skuId) {
        logger.debug("querySkuSaleCount and skuId:{}", skuId);
        Integer count = orderSkuMapper.querySkuSaleCount(skuId);
        return Objects.isNull(count) ? 0 : count.intValue();
    }

    @Override
    public List<OmsOrderSku> queryRecommentSkus(int pageSize) {
        logger.debug("queryRecommentSkus and pageSize :{}", pageSize);
        return orderSkuMapper.queryRecommentSkus(pageSize);
    }

    /**
     * 查询店铺30天内热销商品
     *
     * @param storeId 店铺id
     * @return 热销商品
     */
    @Override
    public List<OmsOrderSku> queryRecommentSkusThirtyDays(long storeId) {
        logger.debug("queryRecommentSkusThirtyDays and storeId :{}", storeId);
        return orderSkuMapper.queryRecommentSkusThirtyDays(storeId);
    }

    /**
     * 根据订单订单id,查询该订单下的sku销量信息( 因字段过多, 仅查询 skuid, num 等必要信息)
     *
     * @param orderId 订单 id
     * @return 结果集
     */
    @Override
    public List<OmsOrderSku> queryByOrderIdForCommunityBuy(Long orderId) {

        logger.debug("queryByOrderIdForCommunityBuy and orderId :{}", orderId);
        return orderSkuMapper.queryByOrderIdForCommunityBuy(orderId);
    }


    /**
     * 修改订单单品金额
     *
     * @param orderSku 订单单品
     */
    private void updateOrderSkuPrice(OmsOrderSku orderSku) {
        logger.debug("updateOrderSkuPrice fail and orderSku:{}", orderSku);
        //设置每个单品的优惠价格
        orderSku.setSkuPriceDetail();
        orderSkuMapper.updateOrderSkuPrice(orderSku);
    }

    /**
     * 根据订单id和单品id查询订单商品
     *
     * @param orderId 订单id
     * @param skuId   单品id
     * @return 返回订单商品
     */
    private OmsOrderSku queryByOrderIdAndSkuId(long orderId, String skuId) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("skuId", skuId);
        OmsOrderSku orderSku = new OmsOrderSku();
        BeanUtils.copyProperties(orderSkuMapper.queryByOrderIdAndSkuId(params), orderSku);
        return orderSku;
    }

    /**
     * 查询订单单品
     *
     * @param id 订单单品ID
     * @return 订单单品
     */
    @Override
    public OmsOrderSku selectOmsOrderSkuById(Long id) {
        return omsOrderSkuMapper.selectOmsOrderSkuById(id);
    }

    /**
     * 查询订单单品列表
     *
     * @param omsOrderSku 订单单品
     * @return 订单单品
     */
    @Override
    public List<OmsOrderSku> selectOmsOrderSkuList(OmsOrderSku omsOrderSku) {
        return omsOrderSkuMapper.selectOmsOrderSkuList(omsOrderSku);
    }

    /**
     * 新增订单单品
     *
     * @param omsOrderSku 订单单品
     * @return 结果
     */
    @Override
    public int insertOmsOrderSku(OmsOrderSku omsOrderSku) {
        return omsOrderSkuMapper.insertOmsOrderSku(omsOrderSku);
    }

    /**
     * 修改订单单品
     *
     * @param omsOrderSku 订单单品
     * @return 结果
     */
    @Override
    public int updateOmsOrderSku(OmsOrderSku omsOrderSku) {
        return omsOrderSkuMapper.updateOmsOrderSku(omsOrderSku);
    }

    /**
     * 批量删除订单单品
     *
     * @param ids 需要删除的订单单品ID
     * @return 结果
     */
    @Override
    public int deleteOmsOrderSkuByIds(Long[] ids) {
        return omsOrderSkuMapper.deleteOmsOrderSkuByIds(ids);
    }

    /**
     * 删除订单单品信息
     *
     * @param id 订单单品ID
     * @return 结果
     */
    @Override
    public int deleteOmsOrderSkuById(Long id) {
        return omsOrderSkuMapper.deleteOmsOrderSkuById(id);
    }
}
