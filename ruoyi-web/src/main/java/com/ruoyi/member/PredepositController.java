package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.member.domain.UmsPreDepositRecord;
import com.ruoyi.member.service.IUmsPreDepositRecordService;
import com.ruoyi.member.vo.QueryCriteria;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Created by 伊甸园商城 on 18/6/27
 * 预存款控制器
 */
@Api(description = "预存款接口")
@RestController
public class PredepositController {

    /**
     * 注入预存款服务接口
     */
    @Autowired
    private IUmsPreDepositRecordService predepositRecordService;

    /**
     * 查询用户预存款总额
     *
     * @return 返回用户预存款总额
     */
    @RequestMapping(value = "/predeposit/total")
    @ResponseBody
    @ApiOperation(value = "查询用户预存款总额", notes = "查询用户预存款总额（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户预存款总额", response = BigDecimal.class)
    })
    public AjaxResult queryAllPredeposit(HttpServletRequest request) {
        return AjaxResult.success(predepositRecordService.queryCutomerAllPredeposit(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }


    /**
     * 查询用户预存款记录
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回用户预存款记录
     */
    @RequestMapping(value = "/predeposits")
    @ResponseBody
    @ApiOperation(value = "分页查询用户预存款记录", notes = "分页查询用户预存款记录（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "filterType", value = "查询类型 1全部 2 收入 3支出 默认全部"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户预存款记录", response = UmsPreDepositRecord.class)
    })
    public AjaxResult queryPredeposits(HttpServletRequest request, @ApiIgnore PageHelper<UmsPreDepositRecord> pageHelper, @ApiIgnore QueryCriteria queryCriteria) {
        queryCriteria.setCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request));
        return AjaxResult.success(predepositRecordService.queryPredepositRecords(pageHelper, queryCriteria));
    }

}
