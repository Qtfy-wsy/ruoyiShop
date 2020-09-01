package com.ruoyi.login;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.setting.bean.OssSetting;
import com.ruoyi.setting.bean.UploadData;
import com.ruoyi.setting.service.OssService;
import com.ruoyi.util.CommonConstant;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

/**
 * Oss相关操作接口
 * https://github.com/shenzhuan/mallplus on 2018/4/26.
 */
@RestController
@Api(tags = "OssController", description = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
    @Resource
    private OssService ossService;


    /**
     * 上传图片
     *
     * @param name 上传文件的name 默认为image
     * @param type 上传文件的类型 默认为图片 0 图片 1 视频
     * @return 返回图片在腾讯云的地址
     * @throws Exception
     */
    @PostMapping("uploadToQqOSSYun")
    @ApiOperation(value = "上传图片", notes = "上传图片（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "name", value = "上传文件的name 默认为image"),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "type", value = "上传文件的类型 默认为图片 0 图片 1 视频"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回图片在腾讯云的地址", response = String.class)
    })
    public AjaxResult uploadToQqOSSYun(@RequestParam("file") MultipartFile multipartFile,MultipartHttpServletRequest request, String name, String type) throws Exception {
        if (StringUtils.isEmpty(name)) {
            name = "image";
        }

        // 默认上传图片
        if (StringUtils.isEmpty(type)) {
            type = CommonConstant.UPLOAD_PIC;
        }
        if (Objects.isNull(multipartFile)) {
            return AjaxResult.error();
        }
        return AjaxResult.success(ossService.uploadToQqOss(Arrays.asList(UploadData.build(multipartFile.getInputStream(), multipartFile.getBytes(), multipartFile.getOriginalFilename(), type, multipartFile))).stream().findFirst().orElse(""));
    }

    /**
     * 上传图片
     *
     * @param name 上传文件的name 默认为image
     * @param type 上传文件的类型 默认为图片 0 图片 1 视频
     * @return 返回图片在阿里云的地址
     * @throws Exception
     */
    @PostMapping("uploadToAliOss")
    @ApiOperation(value = "上传图片", notes = "上传图片（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "name", value = "上传文件的name 默认为image"),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "type", value = "上传文件的类型 默认为图片 0 图片 1 视频"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回图片在阿里云的地址", response = String.class)
    })
    public AjaxResult uploadToOSSYun(@RequestParam("file") MultipartFile multipartFile, String name, String type) throws Exception {
        if (StringUtils.isEmpty(name)) {
            name = "image";
        }

        // 默认上传图片
        if (StringUtils.isEmpty(type)) {
            type = CommonConstant.UPLOAD_PIC;
        }

        if (Objects.isNull(multipartFile)) {
            return AjaxResult.error();
        }
        return AjaxResult.success(ossService.uploadToOss(Arrays.asList(UploadData.build(multipartFile.getInputStream(), multipartFile.getBytes(), multipartFile.getOriginalFilename(), type, multipartFile))).stream().findFirst().orElse(""));
    }


    /**
     * 查询云存储设置
     *
     * @return 云存储设置信息
     */
    @GetMapping("/oss")
    @PreAuthorize("@ss.hasPermi('setting:ossSetting:list')")
    @ApiOperation(value = "查询阿里云存储设置", notes = "查询阿里云存储设置（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "又拍云设置信息", response = OssSetting.class)
    })
    public OssSetting queryUpYunSet() {
        return ossService.queryOssSetting();
    }

    /**
     * 修改又拍云设置
     *
     * @param ossSetting 又拍云设置信息
     * @return 成功1 否则失败
     */
    @PutMapping("/oss")
    @ApiOperation(value = "修改阿里云存储设置", notes = "修改阿里云存储设置（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功1 否则失败", response = Integer.class)
    })
    public int updateOss(@RequestBody OssSetting ossSetting) {
        return ossService.updateOss(ossSetting);
    }

}
