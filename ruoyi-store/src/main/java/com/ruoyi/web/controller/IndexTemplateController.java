package com.ruoyi.web.controller;


import com.ruoyi.cms.bean.ArticleList;
import com.ruoyi.cms.bean.HelpList;
import com.ruoyi.cms.service.ArticleListService;
import com.ruoyi.cms.service.HelpListService;
import com.ruoyi.goods.domain.PmsCategory;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsCategoryService;
import com.ruoyi.goods.service.SkuServiceApi;
import com.ruoyi.util.BaseResponse;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dujinkai on 2020/3/18.
 * pc首页模版
 */
@RestController
@Api(description = "pc首页模版接口")
public class IndexTemplateController {


    /**
     * 注入单品聚合服务
     */
    @Autowired
    private SkuServiceApi skuServiceApi;

    /**
     * 注入文章列表service
     */
    @Autowired
    private ArticleListService articleListService;


    /**
     * 自动注入帮助列表业务层接口
     */
    @Autowired
    private HelpListService helpListService;

    /**
     * 注入分类服务接口
     */
    @Autowired
    private IPmsCategoryService categoryService;




    /**
     * 分页查询单品信息(全站，包含店铺)
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @return 返回单品信息(包括规格信息)
     */
    @GetMapping("/template/allskus")
    @ApiOperation(value = "分页查询单品信息(全站，包含店铺)", notes = "分页查询单品信息(全站，包含店铺)（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "name", value = "单品名称"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回商品信息", response = PmsSku.class)
    })
    public BaseResponse queryAllSkus(PageHelper<PmsSku> pageHelper, String name) {
        return BaseResponse.build(skuServiceApi.queryAllSkusWithSpecs(pageHelper, name, ""));
    }

    /**
     * 分页查询文章列表
     *
     * @param pageHelper 分页帮助类
     * @param title      查询条件，文章标题
     * @return BaseResponse集合对象
     */
    @GetMapping("/template/articlelist")
    @ApiOperation(value = "分页查询文章列表", notes = "分页查询文章列表（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "title", value = "文章标题"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回文章信息", response = ArticleList.class)
    })
    public BaseResponse queryArticleList(PageHelper<ArticleList> pageHelper, String title) {
        return BaseResponse.build(articleListService.queryArticleList(pageHelper, title, CommonConstant.QUERY_WITH_NO_COLUMNID, -1L));
    }




    /**
     * 查询帮助列表
     *
     * @param pageHelper 分页帮助类
     * @param name       帮助名称
     * @return 返回帮助信息
     */
    @GetMapping("/template/helplist")
    @ApiOperation(value = "查询帮助列表", notes = "查询帮助列表（需要认证）")

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "name", value = "帮助名称"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回帮助列表", response = HelpList.class)
    })
    public BaseResponse queryHelpList(PageHelper<HelpList> pageHelper, String name) {
        return BaseResponse.build(helpListService.queryHelpList(pageHelper, name));
    }

    /**
     * 查找所有boss分类
     *
     * @return 所有boss分类
     */
    @GetMapping("/template/cates")
    @ApiOperation(value = "查找所有boss分类", notes = "查找所有boss分类（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回boss所有分类列表", response = PmsCategory.class)
    })
    public List<PmsCategory> queryAllCategory() {
        return categoryService.queryAllCategory();
    }


    /**
     * 根据ID查询单品信息
     *
     * @param id 主键ID
     * @return 返回单品信息
     */
    @GetMapping("/template/sku/{id}")
    @ApiOperation(value = "根据ID查询单品信息", notes = "根据ID查询单品信息（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "String", name = "id", value = "单品id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回单品信息", response = PmsSku.class)
    })
    public PmsSku querySkuById(@PathVariable String id) {
        return skuServiceApi.querySkuByIdWithSpecs(id);
    }

    /**
     * 根据文章id查询文章信息
     *
     * @param id 文章主键id
     * @return 文章信息
     */
    @GetMapping("/template/article/{id}")
    @ApiOperation(value = "根据文章id查询文章信息", notes = "根据文章id查询文章信息（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "文章id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回文章信息", response = ArticleList.class)
    })

    public ArticleList queryArticleById(@PathVariable long id) {
        return articleListService.queryArticleById(id);
    }




    /**
     * 根据id查询帮助
     *
     * @param id 帮助id
     * @return 返回帮助
     */
    @GetMapping("/template/help/{id}")
    @ApiOperation(value = "根据id查询帮助", notes = "根据id查询帮助（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "帮助id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回帮助", response = HelpList.class)
    })

    public HelpList queryHelpById(@PathVariable long id) {
        return helpListService.queryHelpById(id);
    }




}
