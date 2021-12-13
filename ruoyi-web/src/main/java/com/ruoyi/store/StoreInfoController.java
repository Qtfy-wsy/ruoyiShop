package com.ruoyi.store;


import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.store.domain.TStoreCategory;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.service.ITStoreCategoryService;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by 伊甸园商城 on 2018/8/23.
 * 店铺信息控制器
 */
@Api(description = "店铺信息接口")
@RestController
@RequestMapping("/store")
public class StoreInfoController {


    /**
     * 注入店铺信息服务接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;

    /**
     * 注入店铺分类服务接口
     */
    @Autowired
    private ITStoreCategoryService spuCategoryService;

    /**
     * 分页搜索店铺
     *
     * @param pageHelper 分页帮助类
     * @param keyword    关键字
     * @param orderBy    排序条件 0:综合 1:销量 2:评论数
     * @return 店铺信息
     */
    @UnAuth
    @RequestMapping("/searchstore")
    @ResponseBody
    @ApiOperation(value = "分页搜索店铺", notes = "分页搜索店铺（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "keyword", value = "关键字"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "orderBy", value = "排序条件 0:综合 1:销量 2:评论数"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "店铺信息", response = TStoreInfo.class)
    })
    public AjaxResult queryStoreInfoForSearch(@ApiIgnore PageHelper pageHelper, @ApiIgnore String keyword, @ApiIgnore int orderBy) {
        return AjaxResult.success(storeInfoService.queryStoreInfoForSearch(pageHelper, keyword, orderBy));
    }

    /**
     * 根据店铺ID查询店铺信息
     *
     * @param storeId 店铺ID
     * @return 店铺信息
     */
    @RequestMapping("/querystoreinfo")
    @ResponseBody
    @UnAuth
    @ApiOperation(value = "查询店铺信息", notes = "查询店铺信息（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "storeId", value = "店铺ID"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "店铺信息", response = TStoreInfo.class)
    })
    public AjaxResult queryStoreInfo(long storeId) {
        return AjaxResult.success(storeInfoService.selStoreInfo(storeId));
    }

    /**
     * 查询店铺所有分类
     *
     * @param storeId 店铺id
     * @return 分类集合
     */
    @UnAuth
    @RequestMapping("/queryallstorecategory")
    @ResponseBody
    @ApiOperation(value = "查询店铺所有分类", notes = "查询店铺所有分类（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "storeId", value = "店铺id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "分类集合", response = TStoreCategory.class)
    })
    public AjaxResult querySortedAllStoreCategory(long storeId) {
        return AjaxResult.success(spuCategoryService.querySortedAllSpuCategory(storeId));
    }

}
