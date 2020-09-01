package com.ruoyi.integral.service.impl;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.integral.domain.CustomerPoint;
import com.ruoyi.integral.domain.PointMallOrder;
import com.ruoyi.integral.domain.PointSku;
import com.ruoyi.integral.service.CustomerPointService;
import com.ruoyi.integral.service.PointMallOrderService;
import com.ruoyi.integral.service.PointMallOrderServiceApi;
import com.ruoyi.integral.service.PointSkuService;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.order.domain.OmsLogisticsCompany;
import com.ruoyi.order.service.IOmsLogisticsCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 积分商城订单聚合服务接口实现
 */
@Service
public class PointMallOrderServiceApiImpl implements PointMallOrderServiceApi {


    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(PointMallOrderServiceImpl.class);

    /**
     * 注入积分商城订单服务接口
     */
    @Autowired
    private PointMallOrderService pointMallOrderService;

    /**
     * 注入物流公司服务接口
     */
    @Autowired
    private IOmsLogisticsCompanyService logisticsCompanyService;

    /**
     * 注入用户服务接口
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 注入积分商品服务接口
     */
    @Autowired
    private PointSkuService pointSkuService;

    /**
     * 注入会员积分服务
     */
    @Autowired
    private CustomerPointService customerPointService;

    /**
     * 注入序号生成器
     */
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

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
    public PointMallOrder queryPointMallOrderDetailById(long id, Long customerId) {
        logger.debug("queryPointMallOrderDetailById and id:{} \r\n customerId:{} \r\n requestFrom:{}", id, customerId);
        PointMallOrder pointMallOrder = pointMallOrderService.queryPointMallOrderById(id, customerId);
        if (ObjectUtils.isEmpty(pointMallOrder)) {
            logger.error("queryPointMallOrderDetailById fail :pointMallOrder is empty");
            return null;
        }

        //设置用户名
        if (!ObjectUtils.isEmpty(pointMallOrder.getCustomerId())) {
            UmsMember customer = customerService.queryCustomerWithNoPasswordById(pointMallOrder.getCustomerId());
            if (!ObjectUtils.isEmpty(customer)) {
                pointMallOrder.setCustomerName(customer.getUsername());
            }
        }
        //把积分商品信息json转化为对象
        if (!StringUtils.isEmpty(pointMallOrder.getSkuInfo())) {
            pointMallOrder.convertJsonToObject();
        }
        return pointMallOrder;
    }

    @Override
    @Transactional
    public AjaxResult addPointMallOrder(PointMallOrder pointMallOrder) {
        logger.debug("addPointMallOrder and pointMallOrder:{}", pointMallOrder);
        PointSku pointSku = pointSkuService.queryPointSkuById(pointMallOrder.getSkuId());
        //判断积分商品是否存在
        if (ObjectUtils.isEmpty(pointSku)) {
            logger.error("addPointMallOrder fail : queryPointSkuById fail,no pointSku");
            return AjaxResult.error(-3);
        }
        //判断积分商品是否已发布
        if (!pointSku.isOnSale()) {
            logger.error("addPointMallOrder fail : not on sale");
            return AjaxResult.error(-5);
        }
        //设置购买的积分商品详情
        pointMallOrder.setSkuDetail(PointMallOrder.SkuDetail.buildSkuDetail(pointSku));
        //构建的积分商城订单实体
        pointMallOrder.buildForAdd(snowflakeIdWorker.nextId());
        //校验参数
        if (pointMallOrder.checkParams()) {
            logger.error("addPointMallOrder fail : checkParams fail");
            return AjaxResult.error(-1);
        }
        //减积分商品库存
        if (pointSkuService.reducePointSkuStock(pointMallOrder.getSkuId(), pointMallOrder.getNum()) < 1) {
            logger.error("addPointMallOrder fail : reducePointSkuStock fail");
            return AjaxResult.error(-2);
        }
        //添加积分记录
        if (customerPointService.addCustomerPoint(CustomerPoint.buildForPointMallOrder(pointMallOrder.getCustomerId(), pointMallOrder.getPoint())) < 1) {
            logger.error("addPointMallOrder fail :addCustomerPoint fail");
            throw new ServiceException("R-000012");
        }
        //新增积分商城订单
        pointMallOrderService.savePointMallOrder(pointMallOrder);
        return AjaxResult.success(pointMallOrder);
    }

    @Override
    public int deliverPointMallOrder(long id, long logisticsId, String logisticsCode) {
        logger.debug("deliverPointMallOrder and id:{} \r\n logisticsId:{} \r\n logisticsCode:{}", id, logisticsId, logisticsCode);
        if (StringUtils.isEmpty(logisticsCode)) {
            logger.debug("deliverPointMallOrder fail : no logisticsCode");
            return -1;
        }
        //判断运单号是否含有中文，含有返回-2
        if (isContainChinese(logisticsCode)) {
            logger.error("deliverPointMallOrder fail due to logisticsCode has Chinese....");
            return -2;
        }

        // 查询物流公司信息
        OmsLogisticsCompany logisticsCompany = logisticsCompanyService.selectOmsLogisticsCompanyById(logisticsId);

        // 如果物流公司不存在则直接返回
        if (Objects.isNull(logisticsCompany)) {
            logger.error("deliverOrder fail due to logisticsCompany is not exist.....");
            return -6;
        }

        return pointMallOrderService.deliverPointMallOrder(id, logisticsCompany.getName(), logisticsCode);
    }
}
