package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.marketing.domain.Coupon;
import com.ruoyi.marketing.domain.CouponCode;
import com.ruoyi.marketing.service.CouponService;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 伊甸园商城 on 18/6/21
 * 优惠券控制器
 */
@RestController
@Api(description = "优惠券接口")
public class CouponController {

    /**
     * 优惠券服务接口
     */
    @Autowired
    private CouponService couponService;

    /**
     * 查询优惠券
     *
     * @param pageHelper 分页对象
     * @return 返回优惠券
     */
    @RequestMapping(value = "/coupons")
    @ResponseBody
    @ApiOperation(value = "查询优惠券", notes = "查询优惠券（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回优惠券", response = CouponCode.class)
    })
    public AjaxResult queryCoupons(HttpServletRequest request, @ApiIgnore PageHelper<CouponCode> pageHelper,String status) {
        return AjaxResult.success(couponService.queryCouponCodeByCustomerId(pageHelper, AppletsLoginUtils.getInstance().getCustomerId(request), status));
    }

    /**
     * 通过优惠券码领取优惠券
     *
     * @param code 优惠券code
     * @return 返回码 1：成功 -1：参数不全 -2：活动已过期 -3：优惠券已领完 -4：用户领取的优惠券已达上限 -5优惠券不存在或已失效(删除状态) -6：该优惠券已被领取 -7：优惠券不存在
     */
    @ResponseBody
    @RequestMapping("/receivecouponbycode")
    @ApiOperation(value = "通过优惠券码领取优惠券", notes = "通过优惠券码领取优惠券（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "code", value = "优惠券code"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码 1：成功 -1：参数不全 -2：活动已过期 -3：优惠券已领完 -4：用户领取的优惠券已达上限 -5优惠券不存在或已失效(删除状态) -6：该优惠券已被领取 -7：优惠券不存在", response = Integer.class)
    })
    public AjaxResult receiveCouponByCode(HttpServletRequest request, String code) {
        return AjaxResult.success(couponService.receiveCouponByCode(AppletsLoginUtils.getInstance().getCustomerId(request), code, 1));
    }

    /**
     * 查询店铺的优惠券
     *
     * @param storeId 店铺id
     * @return 返回店铺优惠券
     */
    @UnAuth
    @RequestMapping(value = "/querycouponbystoreid")
    @ResponseBody
    @ApiOperation(value = "查询店铺的优惠券", notes = "查询店铺的优惠券（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回店铺优惠券", response = Coupon.class)
    })
    public AjaxResult queryCouponByStoreId(long storeId) {
        return AjaxResult.success(couponService.queryCouponByStoreId(storeId, true, 1));
    }

    /**
     * 分页查询优惠券
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @return 优惠券集合
     */
    @UnAuth
    @RequestMapping("/getcouponlist")
    @ResponseBody
    @ApiOperation(value = "分页查询优惠券", notes = "分页查询优惠券（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "优惠券集合", response = Coupon.class)
    })
    public AjaxResult getCouponList(@ApiIgnore PageHelper<Coupon> pageHelper, long storeId) {
        return AjaxResult.success(couponService.queryCouponForSite(pageHelper, storeId));
    }

    /**
     * 领取优惠券
     *
     * @param id 优惠券id
     * @return 返回码 1：成功 -1：参数不全 -2：活动已过期 -3：优惠券已领完 -4：用户领取的优惠券已达上限 -5优惠券已失效(删除状态) -6 系统繁忙，请重试
     */
    @RequestMapping(value = "/receivecoupon")
    @ResponseBody
    @ApiOperation(value = "领取优惠券", notes = "领取优惠券（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "id", value = "优惠券id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码 1：成功 -1：参数不全 -2：活动已过期 -3：优惠券已领完 -4：用户领取的优惠券已达上限 -5优惠券已失效(删除状态) -6 系统繁忙，请重试", response = Integer.class)
    })
    public AjaxResult receiveCoupon(HttpServletRequest request, long id) {
        return AjaxResult.success(couponService.receiveCoupon(AppletsLoginUtils.getInstance().getCustomerId(request), id, 1));
    }

}
