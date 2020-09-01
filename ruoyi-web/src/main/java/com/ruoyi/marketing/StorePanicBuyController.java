package com.ruoyi.marketing;


import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.marketing.domain.MarketingPic;
import com.ruoyi.marketing.domain.PanicBuySku;
import com.ruoyi.marketing.service.MarketingPicService;
import com.ruoyi.marketing.service.MarketingServiceApi;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 店铺秒杀活动控制器
 *
 * @author 魔金商城 created on 2020/5/19
 */
@Controller
@Api("店铺秒杀活动接口")
public class StorePanicBuyController {

    /**
     * 注入促销api接口
     */
    @Autowired
    private MarketingServiceApi marketingServiceApi;

    /**
     * 注入促销图片服务接口
     */
    @Autowired
    private MarketingPicService marketingPicService;


    /**
     * 查询秒杀促销图片信息
     *
     * @return 秒杀促销图片信息
     */
    @RequestMapping("/querypanicbuypic")
    @ResponseBody
    @ApiOperation(value = "查询秒杀促销图片信息", notes = "查询秒杀促销图片信息（不需要认证）")
    @UnAuth
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "秒杀促销图片信息", response = MarketingPic.class)
    })
    public AjaxResult queryPanicBuyPic(long storeId) {
        return AjaxResult.success(marketingPicService.queryMarketingPic(CommonConstant.PANIC_MARKETING_PIC_TYPE, storeId));
    }

    /**
     * 分页查询店铺秒杀活动列表
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @return 返回店铺秒杀活动列表
     */
    @RequestMapping("/querystorepanicbuylist")
    @ResponseBody
    @ApiOperation(value = "分页查询店铺秒杀活动列表", notes = "分页查询店铺秒杀活动列表（不需要认证）")
    @UnAuth
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回店铺秒杀活动列表", response = PanicBuySku.class)
    })
    public AjaxResult queryStorePanicBuyList(@ApiIgnore PageHelper<PanicBuySku> pageHelper, long storeId) {
        return AjaxResult.success(marketingServiceApi.queryStorePanicBuyListForSite(pageHelper, storeId));
    }

}
