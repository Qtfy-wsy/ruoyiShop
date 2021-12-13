package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.member.domain.UmsBrowseRecord;
import com.ruoyi.member.service.IUmsBrowseRecordService;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 18/7/3
 * 浏览记录控制器
 */
@Api(description = "浏览记录接口")
@RestController
public class BrowseController {

    /**
     * 注入浏览记录服务接口
     */
    @Autowired
    private IUmsBrowseRecordService browseRecordService;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 查询用户浏览记录
     *
     * @param pageHelper 分页帮助类
     * @return 返回用户浏览记录
     */
    @RequestMapping(value = "/querybrowserecords")
    @ResponseBody
    @ApiOperation(value = "查询用户浏览记录", notes = "查询用户浏览记录（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户浏览记录", response = UmsBrowseRecord.class)
    })
    public AjaxResult queryBrowseRecords(HttpServletRequest request, @ApiIgnore PageHelper<UmsBrowseRecord> pageHelper) {
        return AjaxResult.success(browseRecordService.queryBrowseRecords(pageHelper, AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 查询浏览记录(未登录状态下)
     *
     * @param skuIds 单品id
     * @return 返回浏览记录
     */
    @UnAuth
    @RequestMapping(value = "/querybrowserecordbyskuids")
    @ResponseBody
    @ApiOperation(value = "查询浏览记录(未登录状态下)", notes = "查询浏览记录(未登录状态下)（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "array", name = "skuIds", value = "单品id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户浏览记录", response = PmsSku.class)
    })
    public AjaxResult queryBrowseRecordBySkuIds(String... skuIds) {
        return AjaxResult.success(skuService.querySkuByIds(skuIds).stream().peek(sku -> skuService.setSkuDetail(sku, PmsSkuItem.BATCH)).collect(Collectors.toList()));
    }


    /**
     * 查询浏览纪录的单品类型
     *
     * @return 返回浏览纪录的单品类型
     */
    @RequestMapping(value = "/querybrowseskutype")
    @ResponseBody
    @ApiOperation(value = "查询浏览纪录的单品类型", notes = "查询浏览纪录的单品类型（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回浏览纪录的单品类型", response = Long.class)
    })
    public AjaxResult queryBrowseSkuType(HttpServletRequest request) {
        return AjaxResult.success(browseRecordService.queryBrowseSkuType(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 添加浏览记录
     *
     * @param skuId 单品id
     * @return 成功返回1 失败返回0
     */
    @RequestMapping(value = "/addbrowserecord")
    @ResponseBody
    @ApiOperation(value = "添加浏览记录", notes = "添加浏览记录（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "skuId", value = "单品id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0", response = Integer.class)
    })
    public AjaxResult addBrowseRecord(HttpServletRequest request, String skuId) {
        return AjaxResult.success(browseRecordService.addBrowseRecord(UmsBrowseRecord.buildForAdd(AppletsLoginUtils.getInstance().getCustomerId(request), skuId)));
    }
}