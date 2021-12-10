package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.domain.RegisterMarketing;
import com.ruoyi.marketing.mapper.RegisterMarketingMapper;
import com.ruoyi.marketing.service.CouponService;
import com.ruoyi.marketing.service.RegisterMarketingService;
import com.ruoyi.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 注册营销service实现类
 * <p>
 * Created by 商城 on
 */
@Service
public class RegisterMarketingServiceImpl implements RegisterMarketingService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(RegisterMarketingServiceImpl.class);

    /**
     * 自动注入注册营销数据库接口
     */
    @Autowired
    private RegisterMarketingMapper registerMarketingMapper;

    /**
     * 自动注入优惠券service接口
     */
    @Autowired
    private CouponService couponService;

    /**
     * 查找注册营销
     *
     * @return 注册营销
     */
    @Override
    public RegisterMarketing queryRegisterMarketing() {
        logger.debug("queryRegisterMarketing...");
        RegisterMarketing marketing = registerMarketingMapper.queryRegisterMarketing();
        if(marketing!=null){
            marketing.setCouponList(couponService.queryCouponByStoreId(CommonConstant.ADMIN_STOREID, false, 2));
        }
        return marketing;
    }

    /**
     * 修改注册营销
     *
     * @param registerMarketing 注册营销
     * @return 成功返回1  失败返回0
     */
    @Override
    public int updateRegisterMarketing(RegisterMarketing registerMarketing) {
        logger.debug("updateRegisterMarketing and registerMarketing :{}", registerMarketing);
        return registerMarketingMapper.updateRegisterMarketing(registerMarketing);
    }

    /**
     * 根据id删除优惠券
     *
     * @param ids 优惠券id
     * @return 成功返回1  失败返回0
     */
    @Override
    public int batchDeleteCoupon(long[] ids) {
        logger.debug("deleteCoupon and id :{}", ids);
        return registerMarketingMapper.batchDeleteCoupon(ids);
    }

}
