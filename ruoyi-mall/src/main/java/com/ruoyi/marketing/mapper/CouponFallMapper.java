package com.ruoyi.marketing.mapper;

import com.ruoyi.marketing.domain.CouponFall;
import org.springframework.stereotype.Repository;

/**
 * 优惠券直降mapper
 *
 * @author 魔金商城 on 2017/6/1.
 */
@Repository
public interface CouponFallMapper {
    /**
     * 添加直降
     *
     * @param couponFall 直降实体类
     * @return 添加返回码
     */

    int addCouponFall(CouponFall couponFall);

    /**
     * 删除优惠券直降信息
     *
     * @param couponIds 优惠券id数组
     * @return 删除返回码
     */

    int deleteCouponFall(long[] couponIds);

    /**
     * 根据优惠券id查询直降信息
     *
     * @param couponId 优惠券id
     * @return 直降信息
     */

    CouponFall queryCouponFallById(long couponId);
}
