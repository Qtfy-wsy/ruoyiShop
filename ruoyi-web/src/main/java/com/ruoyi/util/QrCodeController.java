package com.ruoyi.util;

import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.utils.QRCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 二维码控制器
 *
 * @author 伊甸园商城 created on 2020/6/23
 */
@Controller
@Api(description = "二维码接口")
public class QrCodeController {


    /**
     * 创建二维码图片
     *
     * @param url 地址
     * @param w   宽
     * @param h   高
     */
    @RequestMapping("/createqrcode")
    @ResponseBody
    @UnAuth
    @ApiIgnore
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "url", value = "地址"),
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "w", value = "宽"),
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "h", value = "高"),
    })
    public void createQrCode(HttpServletResponse response, String url, Integer w, Integer h) {
        createQRImg(response, url, w, h);
    }

    /**
     * 创建二维码图片
     *
     * @param url    链接地址
     * @param width  宽
     * @param height 高
     */
    private void createQRImg(HttpServletResponse response, String url, Integer width, Integer height) {
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            QRCodeUtils.createQrCode(url, width, height, "jpg", outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
