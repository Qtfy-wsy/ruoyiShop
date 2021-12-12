package com.ruoyi.order.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IteratorUtils;
import com.ruoyi.order.domain.OmsBackOrder;
import com.ruoyi.order.domain.OmsBackOrderLog;
import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.domain.OmsOrderSku;
import com.ruoyi.order.mapper.OmsBackOrderMapper;
import com.ruoyi.order.service.IOmsBackOrderLogService;
import com.ruoyi.order.service.IOmsBackOrderService;
import com.ruoyi.order.service.IOmsOrderSettingService;
import com.ruoyi.order.service.IOmsOrderSkuService;
import com.ruoyi.order.vo.BackOrderItem;
import com.ruoyi.order.vo.QueryCriteria;
import com.ruoyi.order.vo.QueryOrderCriteria;
import com.ruoyi.order.vo.ReturnSku;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 退单退款Service业务层处理
 *
 * @author 商城
 */
@Service
public class OmsBackOrderServiceImpl implements IOmsBackOrderService {
    @Autowired
    private OmsBackOrderMapper omsBackOrderMapper;
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(OmsBackOrderServiceImpl.class);

    /**
     * 退单数据库接口
     */
    @Autowired
    private OmsBackOrderMapper backOrderMapper;

    /**
     * 退单操作日志服务接口
     */
    @Autowired
    private IOmsBackOrderLogService backOrderLogService;

    /**
     * 注入订单商品服务
     */
    @Autowired
    private IOmsOrderSkuService orderSkuService;

    /**
     * 注入订单设置服务接口
     */
    @Autowired
    private IOmsOrderSettingService orderSettingService;

    /**
     * 判断订单号是否含有中文
     *
     * @param waybillCode 订单号
     * @return true 含有中文 false 不含中文
     */
    private static boolean isContainChinese(String waybillCode) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(waybillCode);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @Override
    public PageHelper<OmsBackOrder> queryBackOrders(PageHelper<OmsBackOrder> pageHelper, QueryCriteria queryCriteria) {
        logger.debug("queryBackOrders pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        // 查询参数
        Map<String, Object> params = queryCriteria.getQueryMap();

        return pageHelper.setListDates(backOrderMapper.queryBackOrders(pageHelper.getQueryParams(params, backOrderMapper.queryBackOrderCount(params))));
    }

    @Override
    public PageHelper<OmsBackOrder> queryStoreBackOrders(PageHelper<OmsBackOrder> pageHelper, QueryCriteria queryCriteria) {
        logger.debug("queryStoreBackOrders pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);

        // 查询参数
        Map<String, Object> params = queryCriteria.getQueryMap();

        return pageHelper.setListDates(backOrderMapper.queryStoreBackOrders(pageHelper.getQueryParams(params, backOrderMapper.queryStoreBackOrdersCount(params))));
    }

    @Override
    public OmsBackOrder queryBackOrderById(long backOrderId, long storeId, long customerId, BackOrderItem... backOrderItems) {
        logger.debug("queryBackOrderById and backOrderId:{} \r\n storeId:{} \r\n customerId:{} \r\n backOrderItems:{}", backOrderId, storeId, customerId, backOrderItems);

        Map<String, Object> parmas = new HashMap<>();
        parmas.put("id", backOrderId);
        parmas.put("storeId", storeId);
        parmas.put("customerId", customerId);

        // 查询退单信息
        OmsBackOrder backOrder = backOrderMapper.queryBackOrderById(parmas);

        if (Objects.isNull(backOrder)) {
            logger.error("queryBackOrderById fail due to BackOrder is not exist....");
            return backOrder;
        }

        // 设置退单操作日志
        if (ArrayUtils.contains(backOrderItems, BackOrderItem.LOG)) {
            backOrder.setBackOrderLogs(backOrderLogService.queryByBackOrderId(backOrderId));
        }


        // 设置退单商品信息
        if (ArrayUtils.contains(backOrderItems, BackOrderItem.SKUS)) {
            setBackOrderSkus(backOrder);
        }

        return backOrder;
    }

    @Transactional
    @Override
    public int agreeToRefund(long backOrderId, long storeId, String message) {
        logger.debug("agreeToRefund and backOrderId:{} \r\n storeId:{} \r\n message:{}", backOrderId, storeId, message);

        Map<String, Object> params = new HashMap<>();
        params.put("id", backOrderId);
        params.put("storeId", storeId);


        if (backOrderMapper.agreeToRefund(params) == 0) {
            logger.error("agreeToRefund fail due to backorder status is not right.....");
            return -1;
        }

        // 新增退单日志
        backOrderLogService.insertOmsBackOrderLog(OmsBackOrderLog.buildForAgreeToRefund(backOrderId, message));

        return 1;
    }

    @Transactional
    @Override
    public int refuseRefund(long backOrderId, long storeId, String message) {
        logger.debug("refuseRefund and backOrderId:{} \r\n storeId:{} \r\n message:{}", backOrderId, storeId, message);


        Map<String, Object> params = new HashMap<>();
        params.put("id", backOrderId);
        params.put("storeId", storeId);


        if (backOrderMapper.refuseRefund(params) == 0) {
            logger.error("refuseRefund fail due to backorder status is not right.....");
            return -1;
        }

        // 新增退单日志
        backOrderLogService.insertOmsBackOrderLog(OmsBackOrderLog.buildForRefuseToRefund(backOrderId, message));

        return 1;
    }

    @Transactional
    @Override
    public int agreeToReturn(long backOrderId, long storeId, String message) {
        logger.debug("agreeToReturn and backOrderId:{} \r\n storeId:{} \r\n message:{}", backOrderId, storeId, message);

        Map<String, Object> params = new HashMap<>();
        params.put("id", backOrderId);
        params.put("storeId", storeId);

        if (backOrderMapper.agreeToReturn(params) == 0) {
            logger.error("agreeToReturn fail due to backorder status is not right.....");
            return -1;
        }

        // 新增退单日志
        backOrderLogService.insertOmsBackOrderLog(OmsBackOrderLog.buildForAgreeToReturn(backOrderId, message));

        return 1;
    }

    @Transactional
    @Override
    public int refuseReturn(long backOrderId, long storeId, String message) {
        logger.debug("refuseReturn and backOrderId:{} \r\n storeId:{} \r\n message:{}", backOrderId, storeId, message);

        Map<String, Object> params = new HashMap<>();
        params.put("id", backOrderId);
        params.put("storeId", storeId);

        if (backOrderMapper.refuseRrturn(params) == 0) {
            logger.error("refuseReturn fail due to backorder status is not right.....");
            return -1;
        }

        // 新增退单日志
        backOrderLogService.insertOmsBackOrderLog(OmsBackOrderLog.buildForRefuseReturn(backOrderId, message));

        return 1;
    }

    @Transactional
    @Override
    public int confirmReturn(long backOrderId, long storeId, String message, BigDecimal money) {
        logger.debug("confirmReturn and backOrderId:{} \r\n ,storeId:{} \r\n ,message:{} \r\n , money:{}", backOrderId, storeId, message, money);

        Map<String, Object> params = new HashMap<>();
        params.put("id", backOrderId);
        params.put("storeId", storeId);
        params.put("money", money);

        if (backOrderMapper.confirmReturn(params) == 0) {
            logger.error("confirmReturn fail due to backorder status is not right.....");
            return -1;
        }

        // 新增退单日志
        backOrderLogService.insertOmsBackOrderLog(OmsBackOrderLog.buildForConfirmReturn(backOrderId, message));

        return 1;
    }

    @Transactional
    @Override
    public int refuseToReceive(long backOrderId, long storeId, String message) {
        logger.debug("refuseToReceive and backOrderId:{} \r\n storeId:{} \r\n message:{}", backOrderId, storeId, message);


        Map<String, Object> params = new HashMap<>();
        params.put("id", backOrderId);
        params.put("storeId", storeId);

        if (backOrderMapper.refuseToReceive(params) == 0) {
            logger.error("refuseToReceive fail due to backorder status is not right.....");
            return -1;
        }

        // 新增退单日志
        backOrderLogService.insertOmsBackOrderLog(OmsBackOrderLog.buildForRefuseToReceive(backOrderId, message));

        return 1;
    }

    @Transactional
    @Override
    public int addBackOrder(OmsBackOrder backOrder) {
        logger.debug("addBackOrder and backOrder:{}", backOrder);

        if (Objects.isNull(backOrder)) {
            logger.error("addBackOrder fail due to backOrder is null...");
            return 0;
        }

        backOrderMapper.addBackOrder(backOrder);

        // 添加日志
        backOrderLogService.insertOmsBackOrderLog(OmsBackOrderLog.buildForApply(backOrder.getId(), backOrder.getType(), backOrder.getReason()));

        return 1;
    }

    @Override
    public boolean isCanApplyRefund(long orderId, long customerId, String orderType) {
        logger.debug("isCanApplyRefund and orderId :{} \r\n customerId:{} \r\n orderType:{}", orderId, customerId, orderType);

        // 判断系统设置是否可以退单
        if (!orderSettingService.isCanBackOrder()) {
            logger.debug("order can not back due to system not allow....");
            return false;
        }

        // 众筹订单不能退款
        if ("4".equals(orderType) || "5".equals(orderType) || "6".equals(orderType)) {
            return false;
        }

        // 虚拟商品订单不能退款
        if ("7".equals(orderType)) {
            return false;
        }

        // 社区团购商品订单不能退款
        if ("8".equals(orderType)) {
            return false;
        }

        List<OmsBackOrder> backOrders = queryByOrderId(orderId, customerId);

        // 订单还没有退单信息 直接返回true
        if (CollectionUtils.isEmpty(backOrders)) {
            logger.debug("order has no backorder can refund");
            return true;
        }

        // 过滤出可以退款的纪录
        List<OmsBackOrder> canBackOrders = backOrders.stream().filter(OmsBackOrder::isCanRefund).collect(Collectors.toList());

        // 去除可以退款的纪录 剩下不可以退款的纪录
        backOrders.removeAll(canBackOrders);

        // 不可以退款的纪录为空 就可以退款 返回true 否则不可以退款 返回false
        return CollectionUtils.isEmpty(backOrders);
    }

    @Override
    public List<OmsBackOrder> queryByOrderId(long orderId, long customerId) {
        logger.debug("queryByOrderId and orderId:{} \r\n customerId:{}", orderId, customerId);

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("customerId", customerId);
        return backOrderMapper.queryByOrderId(params);
    }

    @Override
    public List<OmsOrderSku> getCanRetrunOrderSkus(OmsOrder order) {
        logger.debug("getCanRetrunOrderSkus and order:{} ", order);

        // 如果系统设定不能退货 则直接返回
        if (!orderSettingService.isCanBackOrder()) {
            logger.error("system can not allow back order...");
            return Collections.emptyList();
        }


        if (Objects.isNull(order)) {
            logger.error("getCanRetrunOrderSkus fail due to order is not exist");
            return Collections.emptyList();
        }

        // 如果是众筹订单 则不能退货
        if (order.isCrowdfundingOrder()) {
            logger.error("getCanRetrunOrderSkus fail due to order is CrowdfundingOrder");
            return Collections.emptyList();
        }

        // 如果是虚拟商品订单 则不能退货
        if (order.isVirtualOrder()) {
            logger.error("getCanRetrunOrderSkus fail due to order is VirtualOrder");
            return Collections.emptyList();
        }

        // 社区团购订单不能退货
        if (order.isCommunityBuyOrder()) {
            logger.error("getCanRetrunOrderSkus fail due to order is CommunityBuyOrder");
            return Collections.emptyList();
        }


        // 查询该订单下的退货纪录
        List<OmsBackOrder> backOrders = this.queryByOrderId(order.getId(), order.getCustomerId());

        // 如果该订单还没有退货的纪录 则直接返回订单的单品信息
        if (CollectionUtils.isEmpty(backOrders)) {
            return order.getOrderSkus();
        }

        // 过滤出退货失败的纪录
        List<OmsBackOrder> refuseBackOrders = backOrders.stream().filter(OmsBackOrder::isReturnRefuse).collect(Collectors.toList());

        // 所有纪录去除退货失败的纪录
        backOrders.removeAll(refuseBackOrders);

        // 所有的退货纪录都是失败的 没有成功的或或者正在退货中的纪录 则直接返回
        if (CollectionUtils.isEmpty(backOrders)) {
            return order.getOrderSkus();
        }

        // 订单的单品数量减去正在退货或者已经退货成功的单品的数量
        IteratorUtils.zip(order.getOrderSkus(), getAllReturnSkus(backOrders.stream().map(OmsBackOrder::getSkuIdAndNums).collect(Collectors.toList())), (orderSku, returnSku) -> orderSku.getSkuId().equals(returnSku.getSkuId()),
                (orderSku1, returnSku1) -> orderSku1.setReturnNum(orderSku1.getReturnNum() - returnSku1.getNum()));

        // 排除可以退货数量为0 的单品
        return order.getOrderSkus().stream().filter(orderSku -> orderSku.getReturnNum() > 0).collect(Collectors.toList());
    }

    @Override
    public PageHelper<OmsBackOrder> queryBackOrderForSite(PageHelper<OmsBackOrder> pageHelper, QueryOrderCriteria queryCriteria) {
        logger.debug("queryBackOrderForSite and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);

        // 查询参数
        Map<String, Object> params = queryCriteria.getQueryMapForSite();

        // 退单信息
        List<OmsBackOrder> backOrders = backOrderMapper.queryBackForSiteOrders(pageHelper.getQueryParams(params, backOrderMapper.queryBackOrderForSiteCount(params)));

        if (!CollectionUtils.isEmpty(backOrders)) {
            backOrders.stream().forEach(this::setBackOrderSkus);
        }

        return pageHelper.setListDates(backOrders);

    }

    @Transactional
    @Override
    public int fillTheLogistics(long customerId, long backOrderId, String logisCompanyName, String waybillCode) {
        logger.debug("fillTheLogistics and customerId:{} \r\n backOrderId:{} \r\n logisCompanyName:{} \r\n waybillCode:{}", customerId, backOrderId, logisCompanyName, waybillCode);

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("backOrderId", backOrderId);
        params.put("logisCompanyName", logisCompanyName);
        params.put("waybillCode", waybillCode);

        //判断订单号是否含有中文，含有返回-2
        if (isContainChinese(waybillCode)) {
            logger.error("fillTheLogistics fail due to waybillCode has Chinese....");
            return -2;
        }

        if (backOrderMapper.fillTheLogistics(params) == 0) {
            logger.error("fillTheLogistics fail....");
            return -1;
        }

        // 纪录操作日志
        backOrderLogService.insertOmsBackOrderLog(OmsBackOrderLog.buildForFillTheLogistics(backOrderId));

        return 1;
    }

    /**
     * 设置退单的商品信息
     *
     * @param backOrder 退单信息
     */
    @Override
    public void setBackOrderSkus(OmsBackOrder backOrder) {

        logger.debug("setBackOrderSkus and backOrder:{}", backOrder);


        // 如果是退款 则直接返回订单下的单 因为退款只能整单退
        if (backOrder.isRefund()) {
            logger.debug("current backOrder is refund and return all skus...");
            backOrder.setOrderSkus(orderSkuService.queryByOrderId(backOrder.getOrderId()));
            return;
        }

        // 退货单没有退货的单品信息 直接返回
        if (StringUtils.isEmpty(backOrder.getSkuIdAndNums())) {
            return;
        }

        // 退货的单品和数量
        List<ReturnSku> returnSkus = getReturnSku(backOrder.getSkuIdAndNums());

        // 退货单品详情
        List<OmsOrderSku> orderSkus = orderSkuService.queryByOrderIdAndSkuIds(backOrder.getOrderId(), returnSkus.stream().map(ReturnSku::getSkuId).collect(Collectors.toList()));

        // 设置单品退货的数量
        IteratorUtils.zip(orderSkus, returnSkus, (orderSku, returnSku) -> orderSku.getSkuId().equals(returnSku.getSkuId()), (orderSku1, returnSku1) -> orderSku1.setReturnNum(returnSku1.getNum()));

        backOrder.setOrderSkus(orderSkus);
    }

    @Override
    public int queryInProcessBackOrder(long customerId) {
        logger.debug("queryInProcessBackOrder and customerId:{}", customerId);
        return backOrderMapper.queryInProcessBackOrder(customerId);
    }

    @Override
    public int queryInProcessBackOrderForStoreApp(long storeId) {
        logger.debug("queryInProcessBackOrderForStoreApp and storeId:{}", storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("processStatus", "0");
        return backOrderMapper.queryBackOrderCount(params);
    }

    /**
     * 根据字符串获得所有在退货中的单品 和单品的数量
     *
     * @param contentss 在退货中的单品和单品数量
     * @return 返回所有在退货中的单品和单品数量
     */
    private List<ReturnSku> getAllReturnSkus(List<String> contentss) {
        logger.debug("getAllReturnSkus and contentss:{}", contentss);
        if (CollectionUtils.isEmpty(contentss)) {
            return Collections.emptyList();
        }
        // 所有的正在退货中的单品
        return contentss.stream().flatMap(contents -> this.getReturnSku(contents).stream()).collect(Collectors.toList());
    }

    /**
     * 根据字符串获得在退货中的单品 和单品的数量
     *
     * @param contents 在退货中的单品和单品数量
     * @return 返回在退货中的单品和单品数量
     */
    private List<ReturnSku> getReturnSku(String contents) {
        logger.debug("getReturnSku and contents:{}", contents);
        if (StringUtils.isEmpty(contents)) {
            return Collections.emptyList();
        }

        return Stream.of(contents.split(",")).map(content -> {
            ReturnSku returnSku = new ReturnSku();
            returnSku.setSkuId(content.split("-")[0]);
            returnSku.setNum(Integer.parseInt(content.split("-")[1]));
            return returnSku;
        }).collect(Collectors.toList());
    }

    /**
     * 查询退单退款
     *
     * @param id 退单退款ID
     * @return 退单退款
     */
    @Override
    public OmsBackOrder selectOmsBackOrderById(Long id) {
        return omsBackOrderMapper.selectOmsBackOrderById(id);
    }

    /**
     * 查询退单退款列表
     *
     * @param omsBackOrder 退单退款
     * @return 退单退款
     */
    @Override
    public List<OmsBackOrder> selectOmsBackOrderList(OmsBackOrder omsBackOrder) {
        return omsBackOrderMapper.selectOmsBackOrderList(omsBackOrder);
    }

    /**
     * 新增退单退款
     *
     * @param omsBackOrder 退单退款
     * @return 结果
     */
    @Override
    public int insertOmsBackOrder(OmsBackOrder omsBackOrder) {
        omsBackOrder.setCreateTime(DateUtils.getNowDate());
        return omsBackOrderMapper.insertOmsBackOrder(omsBackOrder);
    }

    /**
     * 修改退单退款
     *
     * @param omsBackOrder 退单退款
     * @return 结果
     */
    @Override
    public int updateOmsBackOrder(OmsBackOrder omsBackOrder) {
        return omsBackOrderMapper.updateOmsBackOrder(omsBackOrder);
    }

    /**
     * 批量删除退单退款
     *
     * @param ids 需要删除的退单退款ID
     * @return 结果
     */
    @Override
    public int deleteOmsBackOrderByIds(Long[] ids) {
        return omsBackOrderMapper.deleteOmsBackOrderByIds(ids);
    }

    /**
     * 删除退单退款信息
     *
     * @param id 退单退款ID
     * @return 结果
     */
    @Override
    public int deleteOmsBackOrderById(Long id) {
        return omsBackOrderMapper.deleteOmsBackOrderById(id);
    }

    @Override
    public List<OmsBackOrder> queryStoreBackOrdersList(OmsBackOrder omsBackOrder) {
        return backOrderMapper.queryStoreBackOrdersList(omsBackOrder);
    }
}
