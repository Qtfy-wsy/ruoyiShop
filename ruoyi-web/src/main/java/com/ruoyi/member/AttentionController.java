package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.goods.domain.PmsAttention;
import com.ruoyi.goods.service.IPmsAttentionService;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 伊甸园商城 on 18/7/3
 * 商品关注控制器
 */
@Api(description = "商品关注接口")
@RestController
public class AttentionController {

    /**
     * 注入商品关注服务接口
     */
    @Autowired
    private IPmsAttentionService attentionService;

    /**
     * 查询用户收藏的单品信息
     *
     * @param pageHelper 分页帮助类
     * @return 返回用户收藏的单品信息
     */
    @RequestMapping(value = "/queryattentions")
    @ResponseBody
    @ApiOperation(value = "查询用户收藏的单品信息", notes = "查询用户收藏的单品信息(需要认证)", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户收藏的单品信息", response = PmsAttention.class)
    })
    public AjaxResult queryAttentions(HttpServletRequest request, @ApiIgnore PageHelper<PmsAttention> pageHelper) {
        return AjaxResult.success(attentionService.queryAttentionsForCustomerCentre(pageHelper, AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 添加商品关注
     *
     * @return 成功返回1 失败返回0
     */
    @RequestMapping(value = "/addattention")
    @ResponseBody
    @ApiOperation(value = "添加商品关注", notes = "添加商品关注(需要认证)", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "skuId", value = "单品ID"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0", response = Integer.class)
    })
    public AjaxResult addAttention(HttpServletRequest request, String skuId) {
        return AjaxResult.success(attentionService.addAttention(AppletsLoginUtils.getInstance().getCustomerId(request), skuId));
    }

    /**
     * 取消商品关注
     *
     * @param skuId 单品id
     * @return 成功返回1 失败返回0
     */
    @RequestMapping(value = "/cancelattention")
    @ResponseBody
    @ApiOperation(value = "取消商品关注", notes = "取消商品关注(需要认证)", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "skuId", value = "单品ID"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0", response = Integer.class)
    })
    public AjaxResult cancelAttention(HttpServletRequest request, String skuId) {
        return AjaxResult.success(attentionService.cancelAttention(skuId, AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

}
