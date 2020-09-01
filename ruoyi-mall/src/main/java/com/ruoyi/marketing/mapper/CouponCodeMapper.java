package com.ruoyi.marketing.mapper;

import com.ruoyi.marketing.domain.CouponCode;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 优惠券券码mapper
 *
 * @author 魔金商城 on 2017/6/1.
 */
@Repository
public interface CouponCodeMapper {
    /**
     * 添加优惠券券码
     *
     * @param couponCode 优惠券券码实体类
     * @return 添加返回码
     */

    int addCouponCode(List<CouponCode> couponCode);

    /**
     * 删除优惠券券码
     *
     * @param couponIds 优惠券id数组
     * @return 删除返回码
     */

    int deleteCouponCode(long[] couponIds);

    /**
     * 根据优惠券id查询券码信息
     *
     * @param couponId 优惠券id
     * @return 券码信息
     */

    List<CouponCode> queryCouponCodeByCouponId(long couponId);

    /**
     * 根据会员id和优惠券状态查询会员领取优惠券信息
     *
     * @param map 查询条件
     * @return 会员领取优惠券信息集合
     */

    List<CouponCode> queryCouponCodeByCustomerId(Map<String, Object> map);

    /**
     * 根据会员id和优惠券状态查询会员领取优惠券条数
     *
     * @param map 查询条件
     * @return 领取优惠券条数
     */

    int queryCouponCodeCountByCustomerId(Map<String, Object> map);

    /**
     * 查询用户还未使用的优惠券信息
     *
     * @param params 查询参数
     * @return 返回用户还未使用的优惠券
     */

    List<CouponCode> queryCustomerCanUserCoupon(Map<String, Object> params);

    /**
     * 设置用户优惠券为已使用
     *
     * @param params 参数
     * @return 成功>1 失败=0
     */

    int setCouponUsed(Map<String, Object> params);

    /**
     * 返还优惠券
     *
     * @param params 参数
     */

    void backCoupon(Map<String, Object> params);


    /**
     * 查询可以领取的优惠券code
     *
     * @param couponId 优惠券id
     * @return 优惠券集合
     */

    List<CouponCode> queryCanReceiveCouponCode(Long couponId);

    /**
     * 查询用户可以领取的优惠券数量
     *
     * @param couponId 优惠券id
     * @return 数量
     */

    int queryCanReceiveCouponCodeCount(Long couponId);

    /**
     * 查询用户已领取的优惠券数量
     *
     * @param map customerId 用户ID couponId 优惠券ID
     * @return 数量
     */

    int queryReceivedCouponCodeByCustomerId(Map map);

    /**
     * 更新优惠券领取状态
     *
     * @param couponCode 优惠券code实体
     * @return 更新数量
     */

    int updateReceivedCoupon(CouponCode couponCode);

    /**
     * 根据券码更新优惠券领取状态
     *
     * @param couponCode 优惠券code实体
     * @return 更新数量
     */

    int updateReceivedCouponByCode(CouponCode couponCode);

    /**
     * 根据券码查询优惠券id
     *
     * @param code 优惠券code
     * @return 对应的优惠券id
     */

    Long queryCouponIdByCode(String code);

}
