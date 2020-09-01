package com.ruoyi.marketing.mapper;

import com.ruoyi.marketing.domain.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 优惠券mapper
 *
 * @author 魔金商城 on 2017/6/1.
 */
@Repository
public interface CouponMapper {
    /**
     * 添加优惠券
     *
     * @param coupon 优惠券实体类
     * @return 添加返回码
     */

    int addCoupon(Coupon coupon);

    /**
     * 该店铺下的所有优惠券条数
     *
     * @param map 查询条件
     * @return 返回条数
     */

    int queryCouponCount(Map<String, Object> map);

    /**
     * 该店铺下的所有优惠券
     *
     * @param map 查询条件
     * @return 返回优惠券集合
     */

    List<Coupon> queryCoupon(Map<String, Object> map);

    /**
     * 根据Id删除优惠券
     *
     * @param map 删除参数
     * @return 返回删除码
     */

    int deleteCoupon(Map<String, Object> map);

    /**
     * 根据优惠券id查询优惠券信息
     *
     * @param map 查询条件(店铺id和优惠券id)
     * @return 优惠券信息
     */

    Coupon queryCouponById(Map<String, Object> map);

    /**
     * 根据优惠券id查询优惠券信息
     *
     * @param couponId 优惠券id
     * @param useType  获得方式 1领取 2发放
     * @return 优惠券信息
     */

    Coupon queryCouponByIdForReceive(@Param("couponId") long couponId, @Param("useType") long useType);

    /**
     * 根据店铺id查询优惠券
     *
     * @param storeId 店铺id
     * @param useType 获得方式 1领取 2发放
     * @return 优惠券集合
     */

    List<Coupon> queryCouponByStoreId(@Param("storeId") long storeId, @Param("useType") long useType);

    /**
     * 查询优惠券
     *
     * @return 优惠券集合
     */

    List<Coupon> queryCouponForPc(Map params);

    /**
     * 查询优惠券数量
     *
     * @return 优惠券数量
     */

    int queryCouponForPcCount(Map params);

    /**
     * 更新优惠券为已领完状态
     *
     * @param id 优惠券id
     * @return 成功>0 失败=0
     */

    int updateCouponAllReceived(long id);

}
