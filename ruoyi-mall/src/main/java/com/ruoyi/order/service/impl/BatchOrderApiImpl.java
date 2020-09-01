package com.ruoyi.order.service.impl;


import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.service.IPmsGoodsService;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.goods.service.SpuServiceApi;
import com.ruoyi.order.domain.OmsLogisticsTemplate;
import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.domain.OmsOrderAttr;
import com.ruoyi.order.domain.OmsOrderSku;
import com.ruoyi.order.service.BatchOrderApi;
import com.ruoyi.order.service.BatchOrderSubmitResponse;
import com.ruoyi.order.service.IOmsLogisticsTemplateService;
import com.ruoyi.order.service.OrderServiceApi;
import com.ruoyi.order.vo.BatchOrderResponse;
import com.ruoyi.order.vo.BatchOrderSku;
import com.ruoyi.setting.service.AreaService;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 魔金商城 on 2019/1/15.
 * 批量下单接口实现
 */
@Service
@Slf4j
public class BatchOrderApiImpl implements BatchOrderApi {

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;


    /**
     * 注入序列生成器
     */
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;


    /**
     * 注入订单服务接口
     */
    @Autowired
    private OrderServiceApi orderServiceApi;

    /**
     * 注入商品聚合服务
     */
    @Autowired
    private SpuServiceApi spuServiceApi;

    /**
     * 注入商品服务
     */
    @Autowired
    private IPmsGoodsService spuService;

    /**
     * 注入区域服务
     */
    @Autowired
    private AreaService areaService;

    /**
     * 注入物流模板服务
     */
    @Autowired
    private IOmsLogisticsTemplateService logisticsTemplateService;

    /**
     * 注入店铺信息服务
     */
    @Autowired
    private ITStoreInfoService storeInfoService;


    @Override
    public BatchOrderResponse importBatch(InputStream inputStream, long customerId) throws IOException {

        log.debug("begin to importBatch");

        // 填充订单信息
        BatchOrderResponse batchOrderResponse = fillOrderInfo(this.parseExecele(inputStream), customerId);

        // 最后将成功和失败的进行分类
        batchOrderResponse.setSuccessAndErrorOrderSkus();

        // 设置订单号
        setOrderCode(batchOrderResponse.getSuccessOrderSkus());

        //计算运费
        batchOrderResponse.setTotalFreight(calculateFright(batchOrderResponse.getSuccessOrderSkus()));

        // 关闭流
        closeStream(inputStream);

        return batchOrderResponse;
    }


    @Override
    @Transactional
    public BatchOrderSubmitResponse batchOrderSubmit(List<BatchOrderSku> batchOrderSkus, long customerId) {
        log.debug("batchOrderSubmit and batchOrderSkus:{} \r\n customerId:{}", batchOrderSkus, customerId);


        // 校验参数
        if (!validateParams(batchOrderSkus)) {
            log.error("batchOrderSubmit fail due to batchOrderSkus is empty....");
            return BatchOrderSubmitResponse.buildParamsFail();
        }

        // 设置单品信息
        batchOrderSkus.stream().forEach(batchOrderSku -> this.fillOrderInfoOneByOne(batchOrderSku, customerId));

        // 校验单品信息
        if (!validateSkuInfo(batchOrderSkus)) {
            log.error("batchOrderSubmit due to sku is error....");
            return BatchOrderSubmitResponse.buildSkuInfoFail();
        }

        // 获得订单信息
        List<OmsOrder> orders = getOrdersInfo(batchOrderSkus, customerId);

        // 保存订单
        orderServiceApi.saveOrders(orders, false);

        return getSubmitOrderResponse(orders);
    }

    @Override
    public BigDecimal calculateFright(List<BatchOrderSku> batchOrderSkuList) {
        if (CollectionUtils.isEmpty(batchOrderSkuList)) {
            return BigDecimal.ZERO;
        }
        List<BigDecimal> frightList = new ArrayList<>();
        batchOrderSkuList.stream().collect(Collectors.groupingBy(BatchOrderSku::getOrderNo)).forEach((orderNo, batchOrderSkus) ->
                frightList.add(batchOrderSkus.stream()
                        .map(batchOrderSku -> spuServiceApi.calculateFreight(batchOrderSku.getSkuNo(), batchOrderSku.getStoreId(), batchOrderSku.getReceiptCityId(), batchOrderSku.getNum()))
                        .filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO)));
        return frightList.stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /**
     * 获得订单返回信息
     *
     * @param orders 订单信息
     * @return 返回订单信息
     */
    private BatchOrderSubmitResponse getSubmitOrderResponse(List<OmsOrder> orders) {

        // 不需要付款的订单
        List<OmsOrder> noNeedPayOrders = orders.stream().filter(OmsOrder::isNoNeedPay).collect(Collectors.toList());

        // 需要付款的订单
        List<OmsOrder> needPayOrders = orders.stream().filter(order -> !order.isNoNeedPay()).collect(Collectors.toList());

        // 没有需要付款的订单 直接返回
        if (CollectionUtils.isEmpty(needPayOrders)) {
            return BatchOrderSubmitResponse.buildSuccessNoNeedPayResponse(noNeedPayOrders.get(0).getMasterOrderCode());
        }

        // 有需要付款的订单 则返回需要付款的实体  注意 订单号拿的的主订单号
        return BatchOrderSubmitResponse.buildSuccessNeedPayResponse(needPayOrders.get(0).getMasterOrderCode(), needPayOrders.stream().map(OmsOrder::getPrice).reduce(new BigDecimal(0), BigDecimal::add));
    }


    /**
     * 获得订单信息
     *
     * @param batchOrderSkus 订单单品
     * @param customerId     会员id
     * @return 返回订单信息
     */
    private List<OmsOrder> getOrdersInfo(List<BatchOrderSku> batchOrderSkus, long customerId) {

        log.debug("getOrdersInfo and batchOrderSkus:{} \r\n customerId:{}", batchOrderSkus, customerId);

        // 同一个订单号的单品归类在一起
        Map<String, List<BatchOrderSku>> batchOrderSkuMap = batchOrderSkus.stream().collect(Collectors.groupingBy(BatchOrderSku::getOrderNo));

        // 订单信息
        List<OmsOrder> orders = new ArrayList<>();

        // 主订单号 同一次批量下单的为一个主订单号
        String masterOrderCode = String.valueOf(snowflakeIdWorker.nextId());

        // 便利单品信息
        batchOrderSkuMap.forEach((orderCode, batchOrderSkus1) -> {
            // 生成订单
            OmsOrder order = new OmsOrder();
            // 设置订单的用户id
            order.setCustomerId(customerId);

            // 设置订单号
            order.setOrderCode(orderCode);

            // 设置主订单号
            order.setMasterOrderCode(masterOrderCode);

            // 设置订单类型
            order.setOrderType("0");

            // 订单的原始价格(每个单品详情页面的价格)
            order.setOriginalPrice(batchOrderSkus1.stream().map(BatchOrderSku::getPrice).reduce(new BigDecimal(0), BigDecimal::add));

            // 运费
            order.setFreightPrice(calculateFright(batchOrderSkus1));


            // 优惠价格
            order.setModifyPrice(batchOrderSkus1.stream().map(BatchOrderSku::getReducedPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

            // 订单的最终价格
            order.setPrice(order.getOriginalPrice().add(order.getFreightPrice()).subtract(order.getModifyPrice()));

            // 总的优惠价格
            order.setConcessionalRate(BigDecimal.ZERO);

            // 设置支付方式
            order.setPayType("0");

            // 设置店铺的店铺id
            order.setStoreId(batchOrderSkus1.get(0).getStoreId());

            // 订单来源
            order.setSource("1");

            // 设置订单状态
            order.setCreateOrderStatus();

            // 设置订单的属性
            order.setOrderAttr(getOrderAttr(batchOrderSkus1.get(0)));

            // 设置订单的每个单品信息
            order.setOrderSkus(getOrderSkus(batchOrderSkus1));

            // 设置订单的推荐人
            orderServiceApi.setOrderRecommoned(order);

            orders.add(order);
        });

        return orders;
    }

    /**
     * 获得订单下的单品
     *
     * @param batchOrderSkus 订单单品
     * @return 返回订单下的单品
     */
    private List<OmsOrderSku> getOrderSkus(List<BatchOrderSku> batchOrderSkus) {
        return batchOrderSkus.stream().map(batchOrderSku -> {
            OmsOrderSku orderSku = new OmsOrderSku();
            PmsSku sku = skuService.querySkuByIdWithSpecs(batchOrderSku.getSkuNo(), CommonConstant.QUERY_WITH_NO_STORE);
            orderSku.setSkuId(sku.getId());
            orderSku.setNum(batchOrderSku.getNum());
            orderSku.setPrice(batchOrderSku.getPrice().subtract(batchOrderSku.getReducedPrice()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            orderSku.setSkuPrice(batchOrderSku.getUnitPrice());
            orderSku.setSkuName(sku.getName());
            orderSku.setSkuImage(sku.getUrl());
            orderSku.setSkuNo(sku.getSkuNo());
            orderSku.setSkuSpecs(sku.getSpecValuesString());
            orderSku.setCommissionRate(sku.getCommissionRate());
            orderSku.setCateRate(spuService.queryCateRateBySkuId(orderSku.getSkuId()));
            orderSku.setPanicMarketingId(CommonConstant.NO_PANIC_MARKETING);
            return orderSku;
        }).collect(Collectors.toList());
    }


    /**
     * 获得订单属性
     *
     * @param batchOrderSku 订单单品
     * @return 返回订单属性
     */
    private OmsOrderAttr getOrderAttr(BatchOrderSku batchOrderSku) {
        OmsOrderAttr orderAttr = new OmsOrderAttr();

        // 订单备注
        orderAttr.setRemark("");

        // 设置订单的用户收货地址
        orderAttr.setReceiptName(batchOrderSku.getReceiptName());
        orderAttr.setReceiptAddress(batchOrderSku.getReceiptAddress());
        orderAttr.setReceiptDetailAddress(batchOrderSku.getReceiptDetailAddress());
        orderAttr.setReceiptMobile(batchOrderSku.getReceiptMobile());
        return orderAttr;
    }


    /**
     * 检查单品是否 有错误 单品不存在 单品下架 单品库存不足 没满足单品起批价格
     *
     * @param batchOrderSkus 订单单品
     * @return 成功返回true  失败返回false
     */
    private boolean validateSkuInfo(List<BatchOrderSku> batchOrderSkus) {
        log.debug("begin to validateSkuInfo and batchOrderSkus:{}", batchOrderSkus);
        // 只有有一个校验失败 则返回失败
        return batchOrderSkus.stream().noneMatch(BatchOrderSku::isHasError);
    }

    /**
     * 校验参数
     *
     * @param batchOrderSkus 批量下单商品
     * @return 成功返回true  失败返回false
     */
    private boolean validateParams(List<BatchOrderSku> batchOrderSkus) {
        if (CollectionUtils.isEmpty(batchOrderSkus)) {
            log.error("validateParams fail due to batchOrderSkus is empty...");
            return false;
        }

        // 只有有一个校验失败 则返回失败
        return batchOrderSkus.stream().allMatch(BatchOrderSku::validateParams);
    }

    /**
     * 关闭输入流
     */
    private void closeStream(InputStream inputStream) throws IOException {
        if (Objects.nonNull(inputStream)) {
            inputStream.close();
        }
    }

    /**
     * 设置订单号
     *
     * @param batchOrderSkus 批量订单
     */
    private void setOrderCode(List<BatchOrderSku> batchOrderSkus) {
        log.debug("setOrderCode and batchOrderSkus:{}", batchOrderSkus);

        Map<String, String> orderCodes = new HashMap<>();

        if (CollectionUtils.isEmpty(batchOrderSkus)) {
            log.error("setOrderCode fail due to no success batchOrderSkus...");
            return;
        }

        // 同一个收货信息的订单设置成一个订单号
        batchOrderSkus.stream().forEach(batchOrderSku -> {

            // 如果没有收货信息 则往map里面加数据  有的话就不管
            if (!orderCodes.containsKey(batchOrderSku.getOrderCodeDistinctKey())) {
                orderCodes.put(batchOrderSku.getOrderCodeDistinctKey(), String.valueOf(snowflakeIdWorker.nextId()));
            }

            // 根据收货信息从map中获取订单号
            batchOrderSku.setOrderNo(orderCodes.get(batchOrderSku.getOrderCodeDistinctKey()));

            // 设置主键id
            batchOrderSku.setId(String.valueOf(snowflakeIdWorker.nextId()));
        });

    }


    /**
     * 填充订单信息
     *
     * @param batchOrderResponse 结果
     * @param customerId         会员id
     * @return 返回结果
     */
    private BatchOrderResponse fillOrderInfo(BatchOrderResponse batchOrderResponse, long customerId) {

        // 不成功直接返回
        if (!batchOrderResponse.isSuccess()) {
            log.warn("fillOrderInfo fail due to batchOrderResponse is not success...");
            return batchOrderResponse;
        }

        // 过滤出没有错误有效的导入的单品信息
        List<BatchOrderSku> effectives = batchOrderResponse.getBatchOrderSkus().stream().filter(BatchOrderSku::isEffective).collect(Collectors.toList());

        // 如果过滤出来没有有效的数据则直接返回
        if (CollectionUtils.isEmpty(effectives)) {
            log.warn("fillOrderInfo fail due to no effectives");
            return batchOrderResponse;
        }

        // 合并单品 如果单品编号相同 并且收货信息也相同 则合并成同一个单品
        effectives = getMergedOrderSku(effectives, batchOrderResponse);

        // 填充订单信息
        effectives.stream().forEach(batchOrderSku -> this.fillOrderInfoOneByOne(batchOrderSku, customerId));

        return batchOrderResponse;
    }

    /**
     * 合并单品 如果单品编号相同 并且收货信息也相同 则合并成同一个单品
     *
     * @param effectives 订单单品
     * @return 返回合并后的订单单品
     */
    private List<BatchOrderSku> getMergedOrderSku(List<BatchOrderSku> effectives, BatchOrderResponse batchOrderResponse) {
        log.debug("getMergedOrderSku and effectives:{}", effectives);

        List<BatchOrderSku> afterMerged = new ArrayList<>();

        // 按照单品编号进行分类 同一个分类的分在一起
        Map<String, List<BatchOrderSku>> merged = effectives.stream().collect(Collectors.groupingBy(BatchOrderSku::getSkuNo));

        merged.forEach((skuNo, batchOrderSkus) -> {
            if (batchOrderSkus.size() > 1) {
                // 相同的单品根据收货地址进行分类 同一个收货信息的单品放在一起
                batchOrderSkus.stream().collect(Collectors.groupingBy(BatchOrderSku::getReceiptInfo)).forEach((receiptinfo, batchOrderSkus2) -> {
                    if (batchOrderSkus2.size() > 1) {
                        // 如果有相同的单品 并且收货地址信一样 则合并在一起算数量
                        int num = batchOrderSkus2.stream().map(x -> new BigDecimal(x.getNum())).reduce(BigDecimal.ZERO, BigDecimal::add).intValue();
                        BigDecimal reducePrice = batchOrderSkus2.stream().map(BatchOrderSku::getReducedPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                        batchOrderSkus2.get(0).setNum(num);
                        batchOrderSkus2.get(0).setReducedPrice(reducePrice);
                        afterMerged.add(batchOrderSkus2.remove(0));
                        // 总的订单商品里面扣除合并的商品
                        batchOrderResponse.getBatchOrderSkus().removeAll(batchOrderSkus2);
                    } else {
                        // 单品编号相同但是收货信息不同 则直接加入
                        afterMerged.add(batchOrderSkus2.get(0));
                    }
                });
            } else {
                // 只有一个单品的则直接加入
                afterMerged.add(batchOrderSkus.get(0));
            }
        });

        log.debug("getMergedOrderSku success and effectives:{}", effectives);
        return afterMerged;
    }


    /**
     * 填充订单信息
     *
     * @param batchOrderSku 订单单品
     * @param customerId    会员id
     */
    private void fillOrderInfoOneByOne(BatchOrderSku batchOrderSku, long customerId) {

        log.debug("fillOrderInfoOneByOne and batchOrderSku:{} \r\n customerId:{}", batchOrderSku, customerId);
        // 单品信息
        PmsSku sku = skuService.setSkuDetail(skuService.querySkuById(batchOrderSku.getSkuNo()), PmsSkuItem.SPEC, PmsSkuItem.BATCH);

        // 如果单品不存在 则直接返回
        if (Objects.isNull(sku)) {
            log.error("fillOrderInfoOneByOne due to sku is not exist....");
            batchOrderSku.setError("单品不存在");
            return;
        }

        // 判断单品是否是上架状态
        if (!sku.validateStatus()) {
            log.error("fillOrderInfoOneByOne due to sku shelvesStatus is not up....");
            batchOrderSku.setError("单品下架状态");
            return;
        }

        // 判断单品是否审核通过
        if (!sku.inAuditedStatus()) {
            log.error("fillOrderInfoOneByOne due to sku status is not audited ....");
            batchOrderSku.setError("单品审核中或未审核通过");
            return;
        }

        // 如果不是平台，需判断店铺状态
        if (sku.getStoreId() != CommonConstant.ADMIN_STOREID) {
            //判断店铺是否有效
            if (!storeInfoService.isEffective(sku.getStoreId())) {
                log.error("fillOrderInfoOneByOne due to store status error ...");
                batchOrderSku.setError("店铺状态不对");
                return;
            }
        }

        // 判断单品是否虚拟单品
        if (sku.isVirtualSku()) {
            log.error("fillOrderInfoOneByOne due to sku is virtual sku...");
            batchOrderSku.setError("虚拟单品不可以批量下单");
            return;
        }

        // 判断单品库存是否足够
        if (!sku.validateStock(batchOrderSku.getNum())) {
            log.error("fillOrderInfoOneByOne due to sku stock is not enough....");
            batchOrderSku.setError("单品库存不足");
            return;
        }

        // 如果单品是起批单品 并且填的数量小于单品的最低起批量则返回
        if (sku.isBatchSku() && batchOrderSku.getNum() < sku.getLimitBatchNum()) {
            batchOrderSku.setError("单品最低起批量为" + sku.getLimitBatchNum());
            return;
        }

        // 设置订单单品信息
        batchOrderSku.convertSkuToBatchOrderSku(sku);

        // 判断优惠价格是否大于商品总价
        if (batchOrderSku.getReducedPrice().compareTo(batchOrderSku.getPrice()) > 0) {
            log.error("fillOrderInfoOneByOne due to reducePrice over price");
            batchOrderSku.setError("优惠价格不能大于单品总价");
            return;
        }
        // 设置市id
        batchOrderSku.setReceiptCityId(areaService.queryCityIdByName(batchOrderSku.getReceiptCityName()));

    }


    /**
     * 解析execel
     *
     * @param inputStream 输入流
     * @return 返回解析的结果
     * @throws IOException
     */
    private BatchOrderResponse parseExecele(InputStream inputStream) throws IOException {

        log.debug("begin to getBatchOrderSkus");

        List<BatchOrderSku> batchOrderSkus = new ArrayList<>();
        POIFSFileSystem fs = new POIFSFileSystem(inputStream);
        HSSFWorkbook wb = new HSSFWorkbook(fs);

        HSSFSheet sheet = wb.getSheetAt(0);
        // 如果超出100 条纪录则直接返回
        if (sheet.getLastRowNum() > 100) {
            return BatchOrderResponse.buildRowLimitFail();
        }

        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = sheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }

            // 导入的单品信息
            BatchOrderSku batchOrderSku = new BatchOrderSku();

            batchOrderSkus.add(batchOrderSku);

            // 单品编号
            HSSFCell skuno = hssfRow.getCell(0);

            if (Objects.isNull(skuno)) {
                batchOrderSku.setError("单品编号不正确");
                continue;
            } else {
                batchOrderSku.setSkuNo(skuno.getStringCellValue());
            }

            // 数量
            HSSFCell num = hssfRow.getCell(1);

            if (Objects.isNull(num) || !org.apache.commons.lang3.StringUtils.isNumeric(num.getStringCellValue()) || Integer.parseInt(num.getStringCellValue()) <= 0) {
                batchOrderSku.setError("数量不正确");
                continue;
            } else {
                batchOrderSku.setNum(Integer.parseInt(num.getStringCellValue()));
            }

            // 收件人姓名
            HSSFCell name = hssfRow.getCell(2);
            if (Objects.isNull(name)) {
                batchOrderSku.setError("收件人姓名不正确");
                continue;
            } else {
                batchOrderSku.setReceiptName(name.getStringCellValue());
            }

            // 收件人手机
            HSSFCell mobile = hssfRow.getCell(3);
            if (Objects.isNull(mobile)) {
                batchOrderSku.setError("收件人手机不正确");
                continue;
            } else {
                batchOrderSku.setReceiptMobile(mobile.getStringCellValue());
            }

            // 收件人地址(省名称)
            HSSFCell provinceCell = hssfRow.getCell(4);
            if (Objects.isNull(provinceCell)) {
                batchOrderSku.setError("收件人地址(省名称)不正确");
                continue;
            } else {
                batchOrderSku.setReceiptProvinceName(provinceCell.getStringCellValue());
            }
            // 收件人地址(市名称)
            HSSFCell cityCell = hssfRow.getCell(5);
            if (Objects.isNull(cityCell)) {
                batchOrderSku.setError("收件人地址(市名称)不正确");
                continue;
            } else {
                batchOrderSku.setReceiptCityName(cityCell.getStringCellValue());
            }
            // 收件人地址(区名称)
            HSSFCell districtCell = hssfRow.getCell(6);
            if (Objects.isNull(districtCell)) {
                batchOrderSku.setError("收件人地址(区名称)不正确");
                continue;
            } else {
                batchOrderSku.setReceiptDistrictName(districtCell.getStringCellValue());
            }
            // 收件人详细地址
            HSSFCell detailAddress = hssfRow.getCell(7);
            if (Objects.isNull(detailAddress)) {
                batchOrderSku.setError("收件人详细地址不正确");
                continue;
            } else {
                batchOrderSku.setReceiptDetailAddress(detailAddress.getStringCellValue());
            }
            // 优惠金额
            HSSFCell reducedPriceCell = hssfRow.getCell(8);
            if (Objects.isNull(reducedPriceCell) || org.springframework.util.StringUtils.isEmpty(reducedPriceCell.getStringCellValue())) {
                batchOrderSku.setReducedPrice(BigDecimal.ZERO);
            } else {
                if (!StringUtils.isMoney(reducedPriceCell.getStringCellValue())) {
                    batchOrderSku.setError("优惠金额不正确");
                    continue;
                } else {
                    batchOrderSku.setReducedPrice(new BigDecimal(reducedPriceCell.getStringCellValue()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
            }
        }

        fs.close();

        log.debug("getBatchOrderSkus success and result:{}", batchOrderSkus);
        return CollectionUtils.isEmpty(batchOrderSkus) ? BatchOrderResponse.buildNoContentFail() : BatchOrderResponse.buildSuccess(batchOrderSkus);

    }

    /**
     * 根据市id得到运费模版id
     *
     * @param storeId 店铺id
     * @param cityId  市id
     * @return 返回运费模版
     */
    private long getLogisticsTemplateId(long storeId, long cityId) {
        OmsLogisticsTemplate logisticsTemplate = logisticsTemplateService.queryLogisticsTemplateByCityId(storeId, cityId);
        if (Objects.isNull(logisticsTemplate)) {
            throw new ServiceException("");
        }
        return logisticsTemplate.getId();
    }

}
