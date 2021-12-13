package com.ruoyi.marketing;


import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.marketing.domain.MarketingCate;
import com.ruoyi.marketing.domain.MarketingPic;
import com.ruoyi.marketing.domain.PreSaleShow;
import com.ruoyi.marketing.service.MarketingCateService;
import com.ruoyi.marketing.service.MarketingPicService;
import com.ruoyi.marketing.service.PreSaleShowService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by 伊甸园商城 on 18/7/11
 * 预售控制器
 */
@Controller
@Api(description = "预售接口")
public class PresaleController {

    /**
     * 注入促销图片服务接口
     */
    @Autowired
    private MarketingPicService marketingPicService;

    /**
     * 注入促销分类服务接口
     */
    @Autowired
    private MarketingCateService marketingCateService;

    /**
     * 注入预售活动服务接口
     */
    @Autowired
    private PreSaleShowService preSaleShowService;


    /**
     * 查询预售促销图片信息
     *
     * @param storeId 店铺id
     * @return 预售促销图片信息
     */
    @RequestMapping("/querypresalepic")
    @ResponseBody
    @ApiOperation(value = "查询预售促销图片信息", notes = "查询预售促销图片信息（不需要认证）")
    @UnAuth
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "预售促销图片信息", response = MarketingPic.class)
    })
    public AjaxResult queryPreSalePic(long storeId) {
        return AjaxResult.success(marketingPicService.queryMarketingPic(CommonConstant.PRESALE_MARKETING_PIC_TYPE, storeId));
    }

    /**
     * 查询预售促销分类列表
     *
     * @param storeId 店铺id
     * @return 返回预售促销分类列表
     */
    @RequestMapping("/querypresalecates")
    @ResponseBody
    @UnAuth
    @ApiOperation(value = "查询预售促销分类列表", notes = "查询预售促销分类列表（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "预售促销分类列表", response = MarketingCate.class)
    })
    public AjaxResult queryMarketingCateList(long storeId) {
        return AjaxResult.success(marketingCateService.queryMarketingCatesByTypeAndStoreId(CommonConstant.PRESALE_MARKETING_CATE, storeId));
    }

    /**
     * 分页查询预售促销列表
     *
     * @param pageHelper 分页帮助类
     * @param cateId     促销分类id
     * @return 返回预售促销列表
     */
    @RequestMapping("/querypresales")
    @ResponseBody
    @UnAuth
    @ApiOperation(value = "分页查询预售促销列表", notes = "分页查询预售促销列表（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "cateId", value = "促销分类id"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回预售促销列表", response = PreSaleShow.class)
    })
    public AjaxResult queryPreSaleList(@ApiIgnore PageHelper<PreSaleShow> pageHelper, long cateId, long storeId) {
        return AjaxResult.success(preSaleShowService.queryPreSalesForSite(pageHelper, cateId, storeId));
    }

    /**
     * 查询预售促销规则信息
     *
     * @return 预售促销规则信息
     */
    @RequestMapping("/querypresalerule")
    @ResponseBody
    @ApiOperation(value = "查询预售促销规则信息", notes = "查询预售促销规则信息（不需要认证）")
    @UnAuth
    @ApiResponses({
            @ApiResponse(code = 200, message = "预售促销规则信息", response = MarketingPic.class)
    })
    public AjaxResult queryPreSaleRule() {
        return AjaxResult.success(marketingPicService.queryMarketingPic(CommonConstant.PRESALE_MARKETING_PIC_TYPE, CommonConstant.ADMIN_STOREID));
    }

}
