package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.service.IOmsOrderService;
import com.ruoyi.order.vo.QueryOrderCriteria;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * 会员分销控制器
 *
 * @author 伊甸园商城 created on 2020/6/22
 */
@RestController
@Api(description = "会员分销接口")
public class DistributionController {

    /**
     * 注入会员服务
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 注入订单服务
     */
    @Autowired
    private IOmsOrderService orderService;

    /**
     * 注入单品服务
     */
    @Autowired
    private IPmsSkuService skuService;


    /**
     * 查询分销下级会员数量
     *
     * @return 下级会员数量
     */
    @RequestMapping("/queryspreadcustomerbycustomeridcount")
    @ResponseBody
    @ApiOperation(value = "查询分销下级会员数量", notes = "查询分销下级会员数量（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "下级会员数量", response = Integer.class)
    })
    public AjaxResult querySpreadCustomerByCustomerIdCount(HttpServletRequest request) {
        return AjaxResult.success(customerService.querySpreadCustomerCountByCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 分页查询分销单品
     *
     * @param pageHelper 分页帮助类
     * @return 分销商品集合
     */
    @RequestMapping("/queryspreadskulist")
    @ResponseBody
    @ApiOperation(value = "分页查询分销单品", notes = "分页查询分销单品（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "分销商品集合", response = PmsSku.class)
    })
    public AjaxResult querySpreadSkuList(@ApiIgnore PageHelper<PmsSku> pageHelper) {
        return AjaxResult.success(skuService.queryCommissionSkuList(pageHelper));
    }

    /**
     * 分页查询会员分销订单信息
     *
     * @param pageHelper 分页帮助类
     * @return 分销订单信息
     */
    @RequestMapping("/queryspreadorders")
    @ResponseBody
    @ApiOperation(value = "分页查询会员分销订单信息", notes = "分页查询会员分销订单信息（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "分销订单信息", response = OmsOrder.class)
    })
    public AjaxResult querySpreadOrders(@ApiIgnore PageHelper<OmsOrder> pageHelper, HttpServletRequest request) {
        QueryOrderCriteria queryCriteria = new QueryOrderCriteria();
        queryCriteria.setCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request));
        queryCriteria.setStoreId(CommonConstant.QUERY_WITH_NO_STORE);
        return AjaxResult.success(orderService.querySpreadOrders(pageHelper, queryCriteria));
    }

    /**
     * 绑定推荐码
     *
     * @param recommendCode 推荐码
     * @return 1成功 -1 用户不存在 -2 已经绑定过推荐码 -3 推荐人不存在 -4 推荐人不能是自己
     */
    @RequestMapping("/bindrecommendcode")
    @ResponseBody
    @ApiOperation(value = "绑定推荐码", notes = "绑定推荐码（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "form", name = "recommendCode", value = "推荐码"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "1成功 -1 用户不存在 -2 已经绑定过推荐码 -3 推荐人不存在 -4 推荐人不能是自己", response = Integer.class)
    })
    public AjaxResult bindRecommendCode(String recommendCode, HttpServletRequest request) {
        return AjaxResult.success(customerService.bindCustomerRecommendCode(recommendCode, AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

}
