package com.ruoyi.web.task;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.marketing.service.GroupMarketingShowService;
import com.ruoyi.marketing.service.PreSaleShowService;
import com.ruoyi.marketing.service.TryMarketingShowService;
import com.ruoyi.marketing.service.TrySkuApplyService;
import com.ruoyi.order.service.OrderServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask {
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }

    /**
     * 试用申请接口实现类
     */
    @Autowired
    private TrySkuApplyService trySkuApplyService;


    /**
     * 注入订单服务接口
     */
    @Autowired
    private OrderServiceApi orderServiceApi;



    /**
     * 注入拼团活动服务接口
     */
    @Autowired
    private GroupMarketingShowService groupMarketingShowService;

    /**
     * 注入试用活动服务接口
     */
    @Autowired
    private TryMarketingShowService tryMarketingShowService;

    /**
     * 注入预售活动服务接口
     */
    @Autowired
    private PreSaleShowService preSaleShowService;


    /**
     * 扫描已结束的试用促销，并随机抽取获得申请资格的人
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void randomExtractApplyCustomer() {
        trySkuApplyService.randomExtractApplyCustomer();
    }

    /**
     * 确认收货
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void autoConfirmReceipt() {
        orderServiceApi.autoConfirmReceipt();

    }

    /**
     * 取消订单
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void autoCancelOrder() {
        orderServiceApi.autoCancelOrder();

    }

    /**
     * 处理超过24小时的拼团订单
     */
    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void autoCancelGroupOrder() {
        orderServiceApi.autoCancelGroupOrder();

    }

    /**
     * 自动取消定金预售订单
     */
    @Scheduled(cron = "0 0 0 24 * ? ")
    public void autoCancelDepositPreSaleOrder() {
        orderServiceApi.autoCancelDepositPreSaleOrder();
    }


    /**
     * 处理已结束的众筹订单
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void autoHandleCrowdFundingOrder() {
        orderServiceApi.autoHandleCrowdFundingOrder();
    }


    /**
     * 自动删除结束的拼团活动
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoDeleteEndGroups() {
        groupMarketingShowService.autoDeleteEndGroups();
    }

    /**
     * 自动删除结束的试用活动
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoDeleteEndTrys() {
        tryMarketingShowService.autoDeleteEndTrys();
    }

    /**
     * 自动删除结束的预售活动
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoDeleteEndPreSales() {
        preSaleShowService.autoDeleteEndPreSales();
    }

}
