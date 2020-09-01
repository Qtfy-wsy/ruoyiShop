package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.member.service.IUmsPreDepositRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 会员预存款控制器
 */
@Controller
@RequestMapping("/customer")
@Api(description = "会员预存款接口")
public class CustomerPreDepositController {

    /**
     * 自动注入预存款service
     */
    @Autowired
    private IUmsPreDepositRecordService predepositRecordService;


    /**
     * 查询会员预存款总额
     *
     * @return 预存款总额
     */
    @RequestMapping("/querypredepositcount")
    @ResponseBody
    @ApiOperation(value = "查询会员预存款总额", notes = "查询会员预存款总额（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "预存款总额", response = BigDecimal.class)
    })
    public AjaxResult queryPreDepositCount(HttpServletRequest request) {
        return AjaxResult.success(predepositRecordService.queryCutomerAllPredeposit(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

}
