package com.ruoyi.order;


import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.order.service.SettlementService;
import com.ruoyi.order.vo.OrderSettlement;
import com.ruoyi.order.vo.OrderSettlementRequest;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * 结算控制器
 */
@Controller
@Api(description = "结算接口")
public class SettlementController {

    /**
     * 注入结算服务接口
     */
    @Autowired
    private SettlementService settlementService;


    /**
     * 查询结算信息
     *
     * @param orderSettlementRequest 订单结算请求参数
     * @return 返回结算信息
     */
    @RequestMapping(value = "/settlement")
    @ResponseBody
    @ApiOperation(value = "查询结算信息", notes = "查询结算信息（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "array", name = "ids", value = "购物车id"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "addressId", value = "收货地址id"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "skuInfo", value = "单品信息 (立即购买的时候使用  单品id,单品数量)"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "isGroup", value = "是否拼团 0 否 1 是"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回结算信息", response = OrderSettlement.class)
    })
    public AjaxResult queryStoreSettlements(HttpServletRequest request, @ApiIgnore OrderSettlementRequest orderSettlementRequest) {
        return AjaxResult.success(settlementService.orderSettlement(orderSettlementRequest.addCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request))));
    }
}
