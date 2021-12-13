package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.marketing.domain.RedEnvelope;
import com.ruoyi.marketing.domain.RedEnvelopeCode;
import com.ruoyi.marketing.service.RedEnvelopeService;
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
 * 会员红包控制器
 */
@RestController
@Api(description = "会员红包接口")
public class RedEnvelopeController {

    /**
     * 注入红包服务
     */
    @Autowired
    private RedEnvelopeService redEnvelopeService;

    /**
     * 分页查询用户查询红包
     *
     * @param pageHelper 分页对象
     * @return 返回红包
     */
    @RequestMapping(value = "/getmyredenvelopelist")
    @ResponseBody
    @ApiOperation(value = "分页查询用户查询红包", notes = "分页查询用户查询红包（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回红包", response = RedEnvelopeCode.class)
    })
    public AjaxResult queryRedEnvelopes(HttpServletRequest request, @ApiIgnore PageHelper<RedEnvelopeCode> pageHelper) {
        return AjaxResult.success(redEnvelopeService.queryRedEnvelopeCodeByCustomerId(pageHelper, AppletsLoginUtils.getInstance().getCustomerId(request), ""));
    }

    /**
     * 分页查询红包
     *
     * @param pageHelper 分页帮助类
     * @return 红包集合
     */
    @UnAuth
    @RequestMapping("/getredenvelopelist")
    @ResponseBody
    @ApiOperation(value = "分页查询红包", notes = "分页查询红包（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "红包集合", response = RedEnvelope.class)
    })
    public AjaxResult getRedEnvelopeList(@ApiIgnore PageHelper<RedEnvelope> pageHelper) {
        return AjaxResult.success(redEnvelopeService.queryRedEnvelopeForSite(pageHelper));
    }

    /**
     * 领取红包
     *
     * @param redEnvelopeId 红包id
     * @return 返回码 1：成功 -1：参数不全 -2：活动已过期 -3：红包已领完 -4：用户领取的红包已达上限 -5红包已失效(删除状态) -6 系统繁忙，请重试
     */
    @RequestMapping(value = "/receiveredenvelope")
    @ResponseBody
    @ApiOperation(value = "领取红包", notes = "领取红包（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "redEnvelopeId", value = "红包id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码 1：成功 -1：参数不全 -2：活动已过期 -3：红包已领完 -4：用户领取的红包已达上限 -5红包已失效(删除状态) -6 系统繁忙，请重试", response = Integer.class)
    })
    public AjaxResult receiveRedEnvelope(HttpServletRequest request, long redEnvelopeId) {
        return AjaxResult.success(redEnvelopeService.receiveRedEnvelope(AppletsLoginUtils.getInstance().getCustomerId(request), redEnvelopeId));
    }

}
