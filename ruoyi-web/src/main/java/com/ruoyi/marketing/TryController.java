package com.ruoyi.marketing;


import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.marketing.domain.*;
import com.ruoyi.marketing.service.*;
import com.ruoyi.marketing.service.impl.TryReportService;
import com.ruoyi.store.service.ITStoreCommentService;
import com.ruoyi.store.vo.StoreScore;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 魔金商城
 * @date 2019-12-19 16:40
 * <p>
 * 试用促销接口
 */
@RestController
@Api(description = "试用促销接口")
public class TryController {

    /**
     * 注入试用申请服务
     */
    @Autowired
    private TrySkuApplyService trySkuApplyService;

    /**
     * 注入试用报告服务
     */
    @Autowired
    private TryReportService tryReportService;

    /**
     * 注入店铺评分服务
     */
    @Autowired
    private ITStoreCommentService storeCommentService;

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
     * 注入试用活动服务接口
     */
    @Autowired
    private TryMarketingShowService tryMarketingShowService;

    /**
     * 注入试用促销服务接口
     */
    @Autowired
    private TryMarketingService tryMarketingService;


    /**
     * 根据试用促销id查询试用商品详情
     *
     * @param tryId 试用促销id
     * @return 试用商品详情
     */
    @GetMapping("/tryspudetail")
    @UnAuth
    @ApiOperation(value = "根据试用促销id查询试用商品详情", notes = "根据试用促销id查询试用商品详情（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "tryId", value = "试用促销id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "试用商品详情", response = TryMarketing.class)
    })
    public AjaxResult queryTrySpuDetail( long tryId) {
        return AjaxResult.success(tryMarketingService.queryTryMarketingById(tryId, CommonConstant.QUERY_WITH_NO_STORE));
    }

    /**
     * 查看店铺评分
     *
     * @param storeId 店铺id
     * @return 店铺信息
     */
    @UnAuth
    @GetMapping(value = "/storescore")
    @ApiOperation(value = "查看店铺评分", notes = "查看店铺评分（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "店铺信息", response = StoreScore.class)
    })
    public AjaxResult queryStoreScore( Long storeId) {
        return AjaxResult.success(storeCommentService.queryStoreScoreWithStoreInfo(storeId));
    }

    /**
     * 根据试用促销id查询试用申请
     *
     * @param tryId 试用促销id
     * @return 试用申请实体
     */
    @UnAuth
    @GetMapping("/tryskuapplybytryid")
    @ApiOperation(value = "根据试用促销id查询试用申请", notes = "根据试用促销id查询试用申请（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "tryId", value = "试用促销id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "试用申请实体", response = TrySkuApply.class)
    })
    public AjaxResult queryTrySkuApply( long tryId, HttpServletRequest request) {
        return  AjaxResult.success(trySkuApplyService.queryTrySkuApply(tryId, AppletsLoginUtils.getInstance().getCustomerId(request))) ;
    }

    /**
     * 申请试用
     *
     * @param trySkuApply 试用申请实体
     * @return 1：成功 -1：活动还没有开始 -2：活动已经结束 -3：您已申请过 -4活动不存在 -5 申请人数已达上限
     */
    @PostMapping("/applytrysku")
    @ApiOperation(value = "申请试用", notes = "申请试用（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "1：成功 -1：活动还没有开始 -2：活动已经结束 -3：您已申请过 -4活动不存在 -5 申请人数已达上限", response = Integer.class)
    })
    public AjaxResult applyTrySku(@RequestBody TrySkuApply trySkuApply, HttpServletRequest request) {
        return AjaxResult.success(trySkuApplyService.addTrySkuApply(trySkuApply.buildCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request))));
    }

    /**
     * 分页查询试用报告
     *
     * @param pageHelper  分页帮助类
     * @param tryId 试用促销id
     * @return 试用报告集合
     */
    @GetMapping("/tryreportlist")
    @UnAuth
    @ApiOperation(value = "分页查询试用报告", notes = "分页查询试用报告（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "tryId", value = "试用促销id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "试用报告集合", response = TryReport.class)
    })
    public AjaxResult queryTryReportList(@ApiIgnore PageHelper<TryReport> pageHelper, @ApiIgnore long tryId) {
        return AjaxResult.success(tryReportService.queryTryReportList(pageHelper, tryId));
    }

    /**
     * 查询试用促销图片信息
     *
     * @param storeId 店铺id
     * @return 试用促销图片信息
     */
    @GetMapping("/try/pic")
    @ApiOperation(value = "查询试用促销图片信息", notes = "查询试用促销图片信息（不需要认证）")
    @UnAuth
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "试用促销图片信息", response = MarketingPic.class)
    })
    public AjaxResult queryTryPic(long storeId) {
        return AjaxResult.success(marketingPicService.queryMarketingPic(CommonConstant.TRY_MARKETING_PIC_TYPE, storeId));
    }

    /**
     * 查询试用促销分类列表
     *
     * @param storeId 店铺id
     * @return 返回试用促销分类列表
     */
    @GetMapping("/tryCate")
    @UnAuth
    @ApiOperation(value = "查询试用促销分类列表", notes = "查询试用促销分类列表（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "试用促销分类列表", response = MarketingCate.class)
    })
    public AjaxResult queryMarketingCateList(long storeId) {
        return AjaxResult.success(marketingCateService.queryMarketingCatesByTypeAndStoreId(CommonConstant.TRY_MARKETING_CATE, storeId));
    }

    /**
     * 分页查询试用促销列表
     *
     * @param pageHelper 分页帮助类
     * @param cateId     促销分类id
     * @return 返回试用促销列表
     */
    @GetMapping("/try")
    @UnAuth
    @ApiOperation(value = "分页查询试用促销列表", notes = "分页查询试用促销列表（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "cateId", value = "促销分类id"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回试用促销列表", response = TryMarketingShow.class)
    })
    public AjaxResult queryTryMarketingList(@ApiIgnore PageHelper<MarketingsSku> pageHelper, long cateId, long storeId,int type,String name) {
        return AjaxResult.success(tryMarketingShowService.queryTrysForSite(pageHelper, cateId, storeId,type,name));
    }

    /**
     * 查询试用促销规则信息
     *
     * @return 试用促销规则信息
     */
    @GetMapping("/tryRule")
    @ApiOperation(value = "查询试用促销规则信息", notes = "查询试用促销规则信息（不需要认证）")
    @UnAuth
    @ApiResponses({
            @ApiResponse(code = 200, message = "试用促销规则信息", response = MarketingPic.class)
    })
    public AjaxResult queryTryRule() {
        return AjaxResult.success(marketingPicService.queryMarketingPic(CommonConstant.TRY_MARKETING_PIC_TYPE, CommonConstant.ADMIN_STOREID));
    }

}
