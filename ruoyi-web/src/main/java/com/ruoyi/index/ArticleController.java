package com.ruoyi.index;


import com.ruoyi.cms.bean.ArticleList;
import com.ruoyi.cms.bean.ColumnList;
import com.ruoyi.cms.service.ArticleListService;
import com.ruoyi.cms.service.ColumnListService;
import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author gxs
 * @date 2020-02-28 12:08
 * <p>
 * 文章接口
 */
@RestController
@Api(description = "文章接口")
public class ArticleController {


    /**
     * 注入文章服务
     */
    @Autowired
    private ArticleListService articleListService;

    /**
     * 注入栏目列表service
     */
    @Autowired
    private ColumnListService columnListService;


    /**
     * 分页查询文章列表
     *
     * @param pageHelper 分页帮助类
     * @param columnId   栏目id
     * @return BaseResponse集合对象
     */
    @GetMapping("/article/list")
    @UnAuth
    @ApiOperation(value = "分页查询文章列表", notes = "分页查询文章列表（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的数量"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "columnId", value = "栏目id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "文章信息集合", response = ArticleList.class)
    })
    public AjaxResult queryArticleList(@ApiIgnore PageHelper<ArticleList> pageHelper, Long columnId) {
        return AjaxResult.success(articleListService.queryArticleList(pageHelper, null, columnId, CommonConstant.QUERY_WITH_ISRELEASE));
    }


    /**
     * 查询文章详情
     *
     * @param articleId 文章id
     * @return 文章详情
     */
    @GetMapping("/article")
    @UnAuth
    @ApiOperation(value = "查询文章详情", notes = "查询文章详情（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "articleId", value = "文章id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询文章详情", response = ArticleList.class)
    })
    public AjaxResult queryArticleById(long articleId) {
        return AjaxResult.success(articleListService.queryArticleById(articleId));
    }

    /**
     * 查询首篇文章
     *
     * @return 文章详情
     */
    @GetMapping("/article/first")
    @UnAuth
    @ApiOperation(value = "查询首篇文章", notes = "查询首篇文章（不需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "文章详情", response = ArticleList.class)
    })
    public AjaxResult queryFirstArticle() {
        return AjaxResult.success(articleListService.queryFirstArticle());
    }


    /**
     * 查询所有栏目列表
     *
     * @return 栏目列表集合
     */
    @GetMapping("/article/column")
    @UnAuth
    @ApiOperation(value = "查询所有栏目列表", notes = "查询所有栏目列表（不需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "栏目列表集合", response = ColumnList.class)
    })
    public AjaxResult queryColumnList() {
        List<ColumnList> categoryList =columnListService.queryColumnList();
        for(ColumnList category:categoryList){
            List<ArticleList> articleLists =articleListService.queryArticleByCateId(category.getId());
            category.setArticleLists(articleLists);
        }
        return AjaxResult.success(categoryList);
    }


}
