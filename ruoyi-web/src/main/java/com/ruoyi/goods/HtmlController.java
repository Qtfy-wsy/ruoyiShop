package com.ruoyi.goods;


import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.setting.service.BaseInfoSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by 魔金商城 on 17/7/14.
 * 生成html的控制器
 */
@Api(description = "生成html接口")
@Controller
public class HtmlController {


    /**
     * 协议模版
     */
    private static final String PROTOCOL_TEMPLATE = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title></title><style>p { line-height:1.4!important } h3,h4,h5 { font-size:16px!important }</style></head><body>template</body></html>";


    /**
     * 注入基本信息服务接口
     */
    @Autowired
    private BaseInfoSetService baseInfoSetService;


    /**
     * 获取开店协议
     *
     * @throws Exception 异常
     */
    @ApiOperation(value = "获取开店协议", notes = "获取开店协议（不需要认证）")
    @RequestMapping(value = "/registerprotocol", method = RequestMethod.GET)
    @UnAuth
    public void openProtocol(HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(createHtml(PROTOCOL_TEMPLATE, baseInfoSetService.queryBaseInfoSet().getSiteRegisterProtocol()));
    }

    /**
     * 生成html
     *
     * @param html 要替换的内容
     * @return 返回html
     */
    private String createHtml(String template, String html) {
        return template.replaceAll("template", html);
    }
}
