package com.ruoyi.member.service.impl;


import com.ruoyi.integral.domain.CustomerPoint;
import com.ruoyi.integral.service.CustomerPointService;
import com.ruoyi.marketing.domain.RegisterMarketing;
import com.ruoyi.marketing.service.CouponService;
import com.ruoyi.marketing.service.RegisterMarketingService;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.RegisterService;
import com.ruoyi.member.service.RegisterServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * 注册服务聚合接口实现
 */
@Service
public class RegisterServiceApiImpl implements RegisterServiceApi {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(RegisterServiceApiImpl.class);

    /**
     * 注入注册服务
     */
    @Autowired
    private RegisterService registerService;

    /**
     * 注入注册营销服务
     */
    @Autowired
    private RegisterMarketingService registerMarketingService;

    /**
     * 注入会员积分服务
     */
    @Autowired
    private CustomerPointService customerPointService;

    /**
     * 注入优惠券服务
     */
    @Autowired
    private CouponService couponService;

    /**
     * 注入会员服务
     */
    @Autowired
    private IUmsMemberService customerService;

    @Override
    @Transactional
    public int registerCustomer(String mobile, String password, String code, String originCode, String recommondCode) {
        logger.debug("registerCustomer and mobile :{} \r\n code:{} \r\n: originCode{} \r\n recommondCode:{}", mobile, code, originCode, recommondCode);
        //注册用户
        int res = registerService.registerCustomer(mobile, password, code, originCode, recommondCode);
        //注册成功后执行注册营销
        if (res > 0) {
            //根据手机号查找用户
            UmsMember customer = customerService.queryCustomerByNameInWriteDataSource(mobile);
            //查找注册营销
            RegisterMarketing registerMarketing = registerMarketingService.queryRegisterMarketing();
            if (!ObjectUtils.isEmpty(registerMarketing)) {
                if (registerMarketing.isEffective()) {
                    //赠送积分
                    logger.info("registerCustomer success : present point");
                    customerPointService.addCustomerPoint(CustomerPoint.buildForRegister(customer.getId(), registerMarketing.getPointNum()));
                }
                if (registerMarketing.isCanReceiveCoupon()) {
                    //赠送优惠券
                    logger.info("registerCustomer success : present coupon");
                    couponService.receiveCoupon(customer.getId(), registerMarketing.getCouponId(), 2);
                }
            }
        }
        return res;
    }
}
