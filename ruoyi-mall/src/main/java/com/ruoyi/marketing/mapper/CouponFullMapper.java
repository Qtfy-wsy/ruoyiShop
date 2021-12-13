package com.ruoyi.marketing.mapper;

import com.ruoyi.marketing.domain.CouponFull;
import org.springframework.stereotype.Repository;

/**
 * 优惠券满减mapper
 *
 * @author 伊甸园商城 on 2017/6/1.
 */
@Repository
public interface CouponFullMapper {

    /**
     * 添加满减
     *
     * @param couponFull 满减实体类
     * @return 添加返回码
     */

    int addCouponFull(CouponFull couponFull);

    /**
     * 删除优惠券满减信息
     *
     * @param couponIds 优惠券id数组
     * @return 删除返回码
     */

    int deleteCouponFull(long[] couponIds);

    /**
     * 根据优惠券id查询满减信息
     *
     * @param couponId 优惠券id
     * @return 查询满减信息
     */

    CouponFull queryCouponFullById(long couponId);
}
