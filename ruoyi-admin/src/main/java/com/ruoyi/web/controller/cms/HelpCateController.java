package com.ruoyi.web.controller.cms;

import com.ruoyi.cms.bean.HelpCategory;
import com.ruoyi.cms.service.HelpCategoryService;
import com.ruoyi.util.BaseResponse;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 帮助分类控制器
 *
 * @author 伊甸园商城 created on 2019/6/3
 */
@RestController
@Api(description = "帮助分类接口")
public class HelpCateController {

    /**
     * 注入帮助分类服务接口
     */
    @Autowired
    private HelpCategoryService helpCategoryService;


    /**
     * 分页查询帮助分类
     *
     * @param pageHelper 分页帮助类
     * @param name       帮助分类名称
     * @return 帮助分类列表
     */
    @GetMapping("/helpcatelist")
    @ApiOperation(value = "分页查询帮助分类", notes = "分页查询帮助分类（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "name", value = "帮助分类名称"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "帮助分类列表", response = HelpCategory.class)
    })
    public BaseResponse queryHelpCateList(@ApiIgnore PageHelper<HelpCategory> pageHelper, String name) {
        return BaseResponse.build(helpCategoryService.queryHelpCategory(pageHelper, name));
    }

    /**
     * 新增帮助分类
     *
     * @param helpCategory 帮助分类实体
     * @return 成功1 否则失败
     */
    @PostMapping("/helpcate")
    @ApiOperation(value = "新增帮助分类", notes = "新增帮助分类（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功1 否则失败", response = Integer.class)
    })
    public int addHelpCategory(@RequestBody HelpCategory helpCategory) {
        return helpCategoryService.addHelpCategory(helpCategory);
    }

    /**
     * 根据id查询帮助分类
     *
     * @param id 帮助分类id
     * @return 帮助分类信息
     */
    @GetMapping("/helpcate/{id}")
    @ApiOperation(value = "根据id查询帮助分类", notes = "根据id查询帮助分类（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "帮助分类id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "帮助分类信息", response = HelpCategory.class)
    })
    public HelpCategory queryHelpCategoryById(@PathVariable long id) {
        return helpCategoryService.queryHelpCategoryById(id);
    }

    /**
     * 修改帮助分类
     *
     * @param helpCategory 帮助分类实体
     * @return 成功1 否则失败
     */
    @PutMapping("/helpcate")
    @ApiOperation(value = "修改帮助分类", notes = "修改帮助分类（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功1 否则失败", response = Integer.class)
    })
    public int updateHelpCategory(@RequestBody HelpCategory helpCategory) {
        return helpCategoryService.updateHelpCategory(helpCategory);
    }

    /**
     * 删除帮助分类
     *
     * @param ids 帮助分类id数组
     * @return 成功>=1 否则失败
     */
    @DeleteMapping("/helpcate")
    @ApiOperation(value = "删除帮助分类", notes = "删除帮助分类（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "array", name = "ids", value = "帮助分类id数组"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功>=1 否则失败", response = Integer.class)
    })
    public int deleteHelpCategory(long[] ids) {
        return helpCategoryService.batchDeleteHelpCategory(ids);
    }

}
