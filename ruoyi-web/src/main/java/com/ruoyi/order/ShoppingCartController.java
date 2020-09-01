package com.ruoyi.order;


import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.MarketingItem;
import com.ruoyi.marketing.service.MarketingQueryService;
import com.ruoyi.order.domain.OmsShoppingCart;
import com.ruoyi.order.service.IOmsShoppingCartService;
import com.ruoyi.order.service.ShoppingCartServiceApi;
import com.ruoyi.order.vo.ShoppingCartResponse;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 购物车控制器
 */
@Controller
@RequestMapping("/shoppingcart")
@Api(description = "购物车接口")
public class ShoppingCartController {

    /**
     * 注入购物车服务接口
     */
    @Autowired
    private IOmsShoppingCartService shoppingCartService;


    /**
     * 注入购物车混合api接口
     */
    @Autowired
    private ShoppingCartServiceApi shoppingCartServiceApi;

    /**
     * 注入促销查询接口
     */
    @Autowired
    private MarketingQueryService marketingQueryService;


    /**
     * 查询单品的促销信息
     *
     * @param skuId 单品id
     * @return 返回单品的促销信息
     */
    @ApiOperation(value = "查询商品的促销信息", notes = "查询商品的促销信息（需要认证）")
    @GetMapping(value = "/cart/marketings")
    public AjaxResult queryMarketings( String skuId) {
        return AjaxResult.success(convertToMarketingRes(marketingQueryService.queryMarketingsBySkuId(
                skuId, false, MarketingItem.FULL_DOWN_MARKETING, MarketingItem.FULL_DISCOUNT_MARKETING, MarketingItem.FULL_GIFT_MARKETING)));
    }

    /**
     * 查询用户购物车总数
     *
     * @return 返回用户购物车总数
     */
    @RequestMapping(value = "/count")
    @ResponseBody
    @ApiOperation(value = "查询用户购物车总数", notes = "查询用户购物车总数（不需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户购物车总数", response = Integer.class)
    })
    public AjaxResult queryShoppingCartCount(HttpServletRequest request) {
        return AjaxResult.success(shoppingCartService.queryShoppingCartCount(AppletsLoginUtils.getInstance().getCustomerId(request)).getAllNum());
    }

    /**
     * 查询用户购物车信息
     *
     * @return 返回用户购物车信息
     */
    @RequestMapping("carts")
    @ResponseBody
    @ApiOperation(value = "查询用户购物车信息", notes = "查询用户购物车信息（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户购物车信息", response = ShoppingCartResponse.class)
    })
    public AjaxResult queryShoppingCarts(HttpServletRequest request) {
        return AjaxResult.success(shoppingCartServiceApi.queryShoppingCarts(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 查询单品的促销信息
     *
     * @param skuId 单品id
     * @return 返回单品的促销信息
     */
    @RequestMapping(value = "/marketings")
    @ResponseBody
    @ApiOperation(value = "查询单品的促销信息", notes = "查询单品的促销信息（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "skuId", value = "单品id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回单品的促销信息", response = Marketing.class)
    })
    public AjaxResult queryMarketingss(String skuId) {
        return AjaxResult.success(marketingQueryService.queryMarketingsBySkuId(skuId, false, MarketingItem.FULL_DOWN_MARKETING, MarketingItem.FULL_DISCOUNT_MARKETING, MarketingItem.FULL_GIFT_MARKETING));
    }

    /**
     * 更新购物车促销信息
     *
     * @param id          主键id
     * @param marketingId 促销id
     * @return 成功返回1 失败返回0
     */
    @RequestMapping(value = "/updatecartmarketing")
    @ResponseBody
    @ApiOperation(value = "更新购物车促销信息", notes = "更新购物车促销信息（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "id", value = "主键id"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "marketingId", value = "促销id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0", response = Integer.class)
    })
    public AjaxResult updateShoppingCartMarketing(long id, long marketingId, HttpServletRequest request) {
        return AjaxResult.success(shoppingCartService.updateMarketing(OmsShoppingCart.buildForUpdateMarketing(id, marketingId, AppletsLoginUtils.getInstance().getCustomerId(request))));
    }


    /**
     * 删除购物车信息
     *
     * @return 成功返回1 失败返回0
     */
    @RequestMapping(value = "deletecart")
    @ResponseBody
    @ApiOperation(value = "删除购物车信息", notes = "删除购物车信息（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "array", name = "ids", value = "购物车ids"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0", response = Integer.class)
    })
    public AjaxResult deleteShoppingCart(Long[] ids, HttpServletRequest request) {
        return AjaxResult.success(shoppingCartService.deleteShoppingCart(AppletsLoginUtils.getInstance().getCustomerId(request), ids));
    }

    /**
     * 清空购物车
     *
     * @return 成功返回1 失败返回0
     */
    @RequestMapping(value = "clear")
    @ResponseBody
    @ApiOperation(value = "清空购物车", notes = "清空购物车（需要认证）", httpMethod = "POST")
    public AjaxResult clearShoppingCart(HttpServletRequest request) {
        return AjaxResult.success(shoppingCartService.clearShoppingCart(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 添加购物车
     *
     * @param shoppingCart 购物车
     * @return 成功返回1  失败返回0 -1 库存不足 -2 单品不存在 -3 参数错误 -4 单品已下架 -5 超出商品抢购限购数量 -6 预售商品不能加入购物车
     */
    @RequestMapping("/addshoppingcart")
    @ResponseBody
    @ApiOperation(value = "添加购物车", notes = "添加购物车（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "skuId", value = "单品id"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "num", value = "数量"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1  失败返回0 -1 库存不足 -2 单品不存在 -3 参数错误 -4 单品已下架 -5 超出商品抢购限购数量 -6 预售商品不能加入购物车", response = Integer.class)
    })
    public AjaxResult addShoppingCart(HttpServletRequest request, @RequestBody OmsShoppingCart shoppingCart) {
        return AjaxResult.success(shoppingCartServiceApi.addShoppingCart(shoppingCart.setDefaultValuesForAdd(AppletsLoginUtils.getInstance().getCustomerId(request))));
    }


    /**
     * 更新购物车数量
     *
     * @param shoppingCart 购物车
     * @return 成功返回1 失败返回0
     */
    @RequestMapping(value = "/cartnum")
    @ResponseBody
    @ApiOperation(value = "更新购物车数量", notes = "更新购物车数量（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0", response = Integer.class)
    })
    public AjaxResult updateShoppingCartNum(@RequestBody OmsShoppingCart shoppingCart, HttpServletRequest request) {
        shoppingCart.setCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request));
        return AjaxResult.success(shoppingCartService.updateShoppingCartNum(shoppingCart));
    }

    /**
     * 转化促销返回实体
     *
     * @param marketings 促销信息
     * @return 返回促销返回实体
     */
    private List<MobileMarketingRes> convertToMarketingRes(List<Marketing> marketings) {
        if (CollectionUtils.isEmpty(marketings)) {
            return Collections.emptyList();
        }

        return marketings.stream().map(MobileMarketingRes::mobileBuild).collect(Collectors.toList());
    }
    /**
     * H5购物车页面返回的促销信息
     */
    @Data
    @ApiModel(description = "H5购物车页面返回的促销信息")
    private static class MobileMarketingRes {

        /**
         * 促销id
         */
        @ApiModelProperty(value = "促销id")
        private long marketingId;

        /**
         * 促销名称
         */
        @ApiModelProperty(value = "促销名称")
        private String name;

        /**
         * 构造H5促销返回实体
         *
         * @param marketing 促销信息
         * @return 返回促销返回实体
         */
        public static MobileMarketingRes mobileBuild(Marketing marketing) {
            MobileMarketingRes marketingRes = new MobileMarketingRes();
            if (Objects.isNull(marketing)) {
                return marketingRes;
            }

            marketingRes.marketingId = marketing.getId();
            marketingRes.name = marketing.getName();

            return marketingRes;
        }
    }

}
