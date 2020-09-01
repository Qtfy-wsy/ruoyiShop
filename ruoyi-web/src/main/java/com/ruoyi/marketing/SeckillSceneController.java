package com.ruoyi.marketing;


import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.SeckillSceneDetail;
import com.ruoyi.marketing.service.MarketingQueryService;
import com.ruoyi.marketing.service.MarketingServiceApi;
import com.ruoyi.marketing.service.SeckillSceneService;
import com.ruoyi.marketing.vo.SeckillSku;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 秒杀场次控制器
 *
 * @author 魔金商城 created on 2020/5/18
 */
@Controller
@Api("秒杀场次接口")
public class SeckillSceneController {

    /**
     * 注入促销api接口
     */
    @Autowired
    private MarketingServiceApi marketingServiceApi;

    /**
     * 注入秒杀场次服务接口
     */
    @Autowired
    private SeckillSceneService seckillSceneService;


    /**
     * 注入促销查询服务
     */
    @Autowired
    private MarketingQueryService marketingQueryService;


    /**
     * 分页查询秒杀活动列表
     *
     * @param pageHelper  分页帮助类
     * @param seckillTime 秒杀时间
     * @return 返回秒杀活动列表
     */
    @RequestMapping("/queryseckills")
    @ResponseBody
    @ApiOperation(value = "分页查询秒杀活动列表", notes = "分页查询秒杀活动列表（不需要认证）", httpMethod = "POST")
    @UnAuth
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回秒杀活动列表", response = SeckillSku.class)
    })
    public AjaxResult querySeckills(@ApiIgnore PageHelper<SeckillSku> pageHelper, String seckillTime) {
        return AjaxResult.success(marketingServiceApi.querySeckillScenePanicbuyForPlatform(pageHelper, seckillTime));
    }

    /**
     * 查询秒杀场次
     *
     * @return 返回秒杀场次
     */
    @RequestMapping("/queryseckilldetail")
    @ResponseBody
    @ApiOperation(value = "查询秒杀场次", notes = "查询秒杀场次（不需要认证）", httpMethod = "POST")
    @UnAuth
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回秒杀活动", response = SeckillSceneDetail.class)
    })
    public AjaxResult querySeckillSceneDetail() {
        return AjaxResult.success(seckillSceneService.querySeckillSceneDetail());
    }


    /**
     * 查询限时折扣促销
     *
     * @param skuId 单品id
     */
    @RequestMapping("/querypanicbuymarketing")
    @ResponseBody
    @UnAuth
    @ApiOperation(value = "查询限时折扣促销", notes = "查询限时折扣促销（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "skuId", value = "单品id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "限时折扣促销", response = Marketing.class)
    })
    public AjaxResult queryPanicBuyMarketing(String skuId) {
        return AjaxResult.success(marketingQueryService.queryPanicBuyMaketingBySkuId(skuId));
    }
}
