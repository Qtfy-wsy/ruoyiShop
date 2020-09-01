package com.ruoyi.marketing.service.impl;


import com.ruoyi.marketing.domain.TryReport;
import com.ruoyi.marketing.domain.TrySkuApply;
import com.ruoyi.marketing.service.CouponService;
import com.ruoyi.marketing.service.TryReportServiceApi;
import com.ruoyi.marketing.service.TrySkuApplyService;
import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.service.IOmsOrderService;
import com.ruoyi.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * 试用报告聚合服务实现
 */
@Service
public class TryReportServiceApiImpl implements TryReportServiceApi {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(TryReportServiceApiImpl.class);


    /**
     * 注入试用申请服务
     */
    @Autowired
    private TrySkuApplyService trySkuApplyService;

    /**
     * 注入试用报告服务
     */
    @Autowired
    private TryReportService tryReportService;

    /**
     * 注入订单服务
     */
    @Autowired
    private IOmsOrderService orderService;

    /**
     * 注入优惠券服务
     */
    @Autowired
    private CouponService couponService;


    @Override
    @Transactional
    public int addTryReport(TryReport tryReport, long customerId) {
        logger.debug("addTryReport and tryReport:{} \r\n customerId：{}", customerId);
        TrySkuApply trySkuApply = trySkuApplyService.queryTrySkuApplyById(tryReport.getTryApplyId(), customerId, true);
        int res = checkAuth(trySkuApply, customerId);
        if (res != 1) {
            return res;
        }
        //提交试用报告
        tryReportService.addTryReport(tryReport);
        //更改申请状态为已完成
        trySkuApplyService.updateTrySkuApplyStatus(trySkuApply.getTryId(), customerId, "4");
        //如果是返券试用，则返券
        if (trySkuApply.getTryMarketing().isCouponType()) {
            couponService.receiveCoupon(customerId, trySkuApply.getTryMarketing().getCouponId(), 2);
        }
        return 1;
    }

    @Override
    public int hasAuthToAddTryReport(long tryApplyId, long customerId) {
        logger.debug("hasAuthToAddTryReport and tryApplyId:{} \r\n customerId:{}", tryApplyId, customerId);
        TrySkuApply trySkuApply = trySkuApplyService.queryTrySkuApplyById(tryApplyId, customerId, false);
        return checkAuth(trySkuApply, customerId);
    }

    /**
     * 检验上传报告资格
     *
     * @param trySkuApply 试用申请实体
     * @param customerId  用户id
     * @return 1:有资格 -1:用户没有资格 -2:没有订单 -3没有确认收货 -4已提交过报告
     */
    private int checkAuth(TrySkuApply trySkuApply, long customerId) {
        if (ObjectUtils.isEmpty(trySkuApply)) {
            logger.error("addTryReport fail: trySkuApply is empty");
            return -1;
        }
        if (ObjectUtils.isEmpty(trySkuApply.getOrderId())) {
            logger.error("addTryReport fail: no orderId");
            return -2;
        }
        if (!ObjectUtils.isEmpty(tryReportService.queryTryReportByTryApplyId(trySkuApply.getId(), CommonConstant.NO_CUSTOMER_ID))) {
            logger.error("addTryReport fail: already has tryReport");
            return -4;
        }
        OmsOrder order = orderService.queryOrderDetailById(trySkuApply.getOrderId(), customerId, CommonConstant.QUERY_WITH_NO_STORE);
        if (ObjectUtils.isEmpty(order)) {
            logger.error("addTryReport fail: no order");
            return -2;
        }
        if (!order.isConfirmReceipted()) {
            logger.error("addTryReport fail: not confirm receipt");
            return -3;
        }
        return 1;
    }

}
