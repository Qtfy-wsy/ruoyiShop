package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.setting.domain.LsStationLetter;
import com.ruoyi.setting.service.ILsStationLetterService;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 魔金商城 on 18/6/14.
 * 消息控制器
 */
@Api(description = "消息接口")
@RestController
@Slf4j
public class MessageController {

    /**
     * 注入消息服务接口
     */
    @Autowired
    private ILsStationLetterService stationLetterService;

    /**
     * 查询用户消息
     *
     * @param pageHelper 分页帮助类
     * @return 返回用户消息
     */
    @RequestMapping(value = "/querymessages", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户消息", notes = "查询用户消息（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户消息", response = LsStationLetter.class)
    })
    public AjaxResult queryMessages(HttpServletRequest request, @ApiIgnore PageHelper<LsStationLetter> pageHelper) {
        return AjaxResult.success(stationLetterService.queryStationLettersByCustomerId(pageHelper, AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 查询用户未读的站内信
     *
     * @return 返回用户未读的站内信
     */
    @RequestMapping(value = "/message/total")
    @ResponseBody
    @ApiOperation(value = "查询用户未读的站内信数量", notes = "查询用户未读的站内信数量（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户未读的站内信数量", response = Integer.class)
    })
    public AjaxResult queryUnReadMessagecount(HttpServletRequest request) {
        return AjaxResult.success(stationLetterService.unReadNum(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 更新消息阅读状态
     *
     * @param id 消息id
     * @return 返回成功1 失败0
     */
    @RequestMapping("/updatemessageisread")
    @ResponseBody
    @ApiOperation(value = "更新消息阅读状态", notes = "更新消息阅读状态（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "id", value = "消息id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功1 失败0", response = Integer.class)
    })
    public AjaxResult updateMessageIsRead(long id, HttpServletRequest request) {
        return AjaxResult.success(stationLetterService.updateStationLettersIsRead(id, AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

}
