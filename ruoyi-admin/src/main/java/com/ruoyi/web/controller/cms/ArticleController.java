package com.ruoyi.web.controller.cms;

import com.ruoyi.cms.bean.ArticleList;
import com.ruoyi.cms.bean.ColumnList;
import com.ruoyi.cms.service.ArticleListService;
import com.ruoyi.cms.service.ColumnListService;
import com.ruoyi.util.BaseResponse;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.CommonResponse;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 文章控制器
 *
 * @author 魔金商城 created on 2019/5/31
 */
@RestController
@Api(description = "文章接口")
public class ArticleController {

    /**
     * 注入文章服务接口
     */
    @Autowired
    private ArticleListService articleListService;

    /**
     * 注入栏目列表服务接口
     */
    @Autowired
    private ColumnListService columnListService;


    /**
     * 分页查询文章列表
     *
     * @param pageHelper   分页帮助类
     * @param title        文章标题
     * @param releaseState 发布状态
     * @return 文章列表
     */
    @GetMapping("/articlelist")
    @ApiOperation(value = "分页查询文章列表", notes = "分页查询文章列表（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "title", value = "文章标题"),
            @ApiImplicitParam(paramType = "form", dataType = "Long", name = "releaseState", value = "发布状态"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "文章列表", response = ArticleList.class)
    })
    public BaseResponse queryArticleList(@ApiIgnore PageHelper<ArticleList> pageHelper, String title, Long releaseState) {
        return BaseResponse.build(articleListService.queryArticleList(pageHelper, title, CommonConstant.QUERY_WITH_NO_COLUMNID, releaseState));
    }

    /**
     * 删除文章
     *
     * @param ids 文章id数组
     * @return 成功>=1 否则失败
     */
    @DeleteMapping("/article")
    @ApiOperation(value = "删除文章", notes = "删除文章（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "array", name = "ids", value = "文章id数组"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功>=1 否则失败", response = Integer.class)
    })
    public int deleteArticle(long[] ids) {
        return articleListService.deleteArticle(ids);
    }

    /**
     * 查询所有二级栏目列表（新增用）
     *
     * @return 二级栏目列表
     */
    @GetMapping("/article/columnlistforadd")
    @ApiOperation(value = "查询所有二级栏目列表（新增用）", notes = "查询所有二级栏目列表（新增用）（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "二级栏目列表", response = ColumnList.class)
    })
    public List<ColumnList> queryChildColumnListForArticleForAdd() {
        return columnListService.queryChildColumnList();
    }

    /**
     * 新增文章
     *
     * @param articleList 文章实体
     * @return 成功1 否则失败
     */
    @PostMapping("/article")
    @ApiOperation(value = "新增文章", notes = "新增文章（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功1 否则失败", response = Integer.class)
    })
    public int addArticle(@RequestBody ArticleList articleList) {
        return articleListService.addArticle(articleList);
    }

    /**
     * 查询所有二级栏目列表（修改用）
     *
     * @return 二级栏目列表
     */
    @GetMapping("/article/columnlistforupdate")
    @ApiOperation(value = "查询所有二级栏目列表（修改用）", notes = "查询所有二级栏目列表（修改用）（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "二级栏目列表", response = ColumnList.class)
    })
    public List<ColumnList> queryChildColumnListForArticleForUpdate() {
        return columnListService.queryChildColumnList();
    }

    /**
     * 根据id查询文章
     *
     * @param id 文章id
     * @return 文章信息
     */
    @GetMapping("/article/{id}")
    @ApiOperation(value = "根据id查询文章", notes = "根据id查询文章（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "文章id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "文章信息", response = ArticleList.class)
    })
    public CommonResponse queryArticleById(@PathVariable long id) {
        return CommonResponse.build(articleListService.queryArticleById(id));
    }

    /**
     * 修改文章
     *
     * @param articleList 文章实体
     * @return 成功1 否则失败
     */
    @PutMapping("/article")
    @ApiOperation(value = "修改文章", notes = "修改文章（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功1 否则失败", response = Integer.class)
    })
    public int updateArticle(@RequestBody ArticleList articleList) {
        return articleListService.editArticle(articleList);
    }

}
