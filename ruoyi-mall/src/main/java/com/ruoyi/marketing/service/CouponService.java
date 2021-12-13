package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.Coupon;
import com.ruoyi.marketing.domain.CouponCode;
import com.ruoyi.marketing.domain.CouponDetails;
import com.ruoyi.util.PageHelper;

import java.io.OutputStream;
import java.util.List;

/**
 * 优惠券service接口
 *
 * @author 伊甸园商城 on 2017/6/1.
 */
public interface CouponService {
    /**
     * 添加优惠券
     *
     * @param coupon  优惠券实体类
     * @param storeId 店铺id
     * @return 添加返回码 -1 失败 1 成功
     */
    int addCoupon(Coupon coupon, long storeId);

    /**
     * 分页查询店铺优惠券
     *
     * @param pageHelper 分页帮助类
     * @param name       优惠券名称
     * @param storeId    店铺id
     * @return 优惠券集合
     */
    PageHelper<Coupon> queryCoupon(PageHelper<Coupon> pageHelper, String name, long storeId);

    /**
     * 删除优惠券
     *
     * @param ids     优惠券id数组
     * @param storeId 店铺id
     * @return 删除返回码 -1 失败 >=1成功
     */
    int deleteCoupon(long[] ids, long storeId);

    /**
     * 复制优惠券链接
     *
     * @param id      优惠券id
     * @param storeId 店铺id
     * @return 返回优惠券链接 -1:没有查询到该优惠券, 0:优惠券已过期, 其他字符串为优惠券链接
     */
    String copyCoupon(long id, long storeId);

    /**
     * 导出优惠券券码
     *
     * @param storeId  店铺id
     * @param couponId 优惠券id
     */
    Void exportCoupon(OutputStream outputStream, long storeId, long couponId);

    /**
     * 根据店铺id查询优惠券
     *
     * @param storeId      店铺id
     * @param isNeedDetail 是否需要查询详情
     * @param useType      获得方式 1领取 2发放
     * @return 优惠券集合
     */
    List<Coupon> queryCouponByStoreId(long storeId, boolean isNeedDetail, long useType);


    /**
     * 根据店铺id查询优惠券(商品详情使用)
     *
     * @param storeId      店铺id
     * @param isNeedDetail 是否需要查询详情
     * @param useType      获得方式 1领取 2发放
     * @return 优惠券集合
     */
    List<Coupon> queryCouponForSpu(long storeId, boolean isNeedDetail, long useType);

    /**
     * 查询优惠券详情页数据
     *
     * @return 优惠券详情页数据
     */
    CouponDetails queryCouponDetails(long storeId, long couponId);

    /**
     * 根据会员id查询会员领取优惠券信息
     *
     * @param customerId 会员id
     * @return 会员领取优惠券信息集合
     */
    PageHelper<CouponCode> queryCouponCodeByCustomerId(PageHelper<CouponCode> pageHelper, long customerId, String status);

    /**
     * 查询用户可以使用的优惠券
     *
     * @param storeId    店铺id
     * @param customerId 用户id
     * @return 返回优惠券信息
     */
    List<CouponCode> queryCustomerCanUserCoupon(Long storeId, Long customerId);

    /**
     * 设置用户的优惠券已经使用
     *
     * @param customerId 用户id
     * @param couponCode 优惠券code
     * @return 成功>1 失败=0
     */
    int setCouponUsed(Long customerId, String couponCode,long orderId);

    /**
     * 反还优惠券
     *
     * @param customerId 用户id
     * @param couponCode 优惠券code
     */
    void backCoupon(long customerId, String couponCode);


    /**
     * 用户领取优惠券
     *
     * @param customerId 用户id
     * @param couponId   优惠券id
     * @param useType    获得方式 1领取 2发放
     * @return 返回码 1：成功 -1：参数不全 -2：活动已过期 -3：优惠券已领完 -4：用户领取的优惠券已达上限 -5优惠券已失效(删除状态) -6 系统繁忙，请重试
     */
    int receiveCoupon(long customerId, Long couponId, long useType);

    /**
     * 用户通过券码领取优惠券
     *
     * @param customerId 用户id
     * @param code       优惠券code
     * @param useType    获得方式 1领取 2发放
     * @return 返回码 1：成功 -1：参数不全 -2：活动已过期 -3：优惠券已领完 -4：用户领取的优惠券已达上限 -5优惠券不存在或已失效(删除状态) -6 系统繁忙，请重试 -7 优惠券不存在
     */
    int receiveCouponByCode(long customerId, String code, long useType);

    /**
     * 根据id查询优惠券信息（pc用）
     *
     * @param couponId 优惠券id
     * @return 优惠券
     */
    Coupon queryCouponByCouponId(Long couponId);

    /**
     * 查询用户的可用优惠券数量
     *
     * @param customerId 会员id
     * @return 返回用户的可用优惠券数量
     */
    int queryCustomerCouponCount(long customerId);

    /**
     * 查询优惠券
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @return 优惠券集合
     */
    PageHelper<Coupon> queryCouponForSite(PageHelper<Coupon> pageHelper, long storeId);
}
