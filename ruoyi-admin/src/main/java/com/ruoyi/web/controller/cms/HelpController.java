package com.ruoyi.web.controller.cms;

import com.ruoyi.cms.bean.HelpCategory;
import com.ruoyi.cms.bean.HelpList;
import com.ruoyi.cms.service.HelpListService;
import com.ruoyi.util.BaseResponse;
import com.ruoyi.util.CommonResponse;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 帮助控制器
 *
 * @author 魔金商城 created on 2019/6/3
 */
@RestController
@Api(description = "帮助接口")
public class HelpController {

    /**
     * 注入帮助服务接口
     */
    @Autowired
    private HelpListService helpListService;


    /**
     * 分页查询帮助列表
     *
     * @param pageHelper 分页帮助类
     * @param name       帮助名称
     * @return 帮助列表
     */
    @GetMapping("/helplist")
    @ApiOperation(value = "分页查询帮助列表", notes = "分页查询帮助列表（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "name", value = "帮助名称"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "帮助列表", response = HelpList.class)
    })
    public BaseResponse queryHelpList(@ApiIgnore PageHelper<HelpList> pageHelper, String name) {
        return BaseResponse.build(helpListService.queryHelpList(pageHelper, name));
    }

    /**
     * 删除帮助
     *
     * @param ids 帮助id数组
     * @return 成功>=1 否则失败
     */
    @DeleteMapping("/help")
    @ApiOperation(value = "删除帮助", notes = "删除帮助（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "array", name = "ids", value = "帮助id数组"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功>=1 否则失败", response = Integer.class)
    })
    public int deleteHelp(long[] ids) {
        return helpListService.batchDeleteHelp(ids);
    }

    /**
     * 查询帮助分类列表（新增用）
     *
     * @return 帮助分类列表
     */
    @GetMapping("/addhelp/helpcatelist")
    @ApiOperation(value = "查询帮助分类列表（新增用）", notes = "查询帮助分类列表（新增用）（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "帮助分类列表", response = HelpCategory.class)
    })
    public List<HelpCategory> queryAllHelpCategoryForAdd() {
        return helpListService.queryHelpCate();
    }

    /**
     * 新增帮助
     *
     * @param helpList 帮助实体
     * @return 成功1 否则失败
     */
    @PostMapping("/help")
    @ApiOperation(value = "新增帮助", notes = "新增帮助（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功1 否则失败", response = Integer.class)
    })
    public int addHelp(@RequestBody HelpList helpList) {
        return helpListService.addHelp(helpList);
    }

    /**
     * 查询帮助分类列表（修改用）
     *
     * @return 帮助分类列表
     */
    @GetMapping("/updatehelp/helpcatelist")
    @ApiOperation(value = "查询帮助分类列表（修改用）", notes = "查询帮助分类列表（修改用）（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "帮助分类列表", response = HelpCategory.class)
    })
    public List<HelpCategory> queryAllHelpCategoryForUpdate() {
        return helpListService.queryHelpCate();
    }

    /**
     * 根据id查询帮助
     *
     * @param id 帮助id
     * @return 帮助信息
     */
    @GetMapping("/help/{id}")
    @ApiOperation(value = "根据id查询帮助", notes = "根据id查询帮助（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "帮助id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "帮助信息", response = CommonResponse.class)
    })
    public CommonResponse queryHelpById(@PathVariable long id) {
        return CommonResponse.build(helpListService.queryHelpById(id));
    }

    /**
     * 修改帮助
     *
     * @param helpList 帮助实体
     * @return 成功1 否则失败
     */
    @PutMapping("/help")
    @ApiOperation(value = "修改帮助", notes = "修改帮助（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功1 否则失败", response = Integer.class)
    })
    public int updateHelp(@RequestBody HelpList helpList) {
        return helpListService.updateHelp(helpList);
    }

}
