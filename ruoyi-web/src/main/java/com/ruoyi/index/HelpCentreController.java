package com.ruoyi.index;


import com.ruoyi.cms.bean.HelpCategory;
import com.ruoyi.cms.bean.HelpList;
import com.ruoyi.cms.service.HelpCategoryService;
import com.ruoyi.cms.service.HelpListService;
import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gxs
 * @date 2020-03-02 10:28
 * <p>
 * 帮助中心
 */
@RestController
@Api(description = "帮助中心")
public class HelpCentreController {


    /**
     * 自动注入帮助分类service
     */
    @Autowired
    private HelpCategoryService helpCategoryService;

    /**
     * 自动注入帮助service
     */
    @Autowired
    private HelpListService helpListService;


    /**
     * 查询所有帮助分类
     *
     * @return 帮助分类
     */
    @GetMapping("/help/cate")
    @UnAuth
    @ApiOperation(value = "查询所有帮助分类", notes = "查询所有帮助分类（不需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "帮助分类", response = HelpCategory.class)
    })
    public AjaxResult queryAllHelpCate() {
        List<HelpCategory> categoryList =helpCategoryService.queryHelpAllCate();
        for(HelpCategory category:categoryList){
            List<HelpList> helpLists =helpListService.queryHelpByCateId(category.getId());
            category.setHelpLists(helpLists);
        }
        return AjaxResult.success(categoryList);
    }

    /**
     * 根据帮助分类id查找帮助信息
     *
     * @param id 帮助分类id
     * @return 帮助信息
     */
    @GetMapping("/help/cate/{id}")
    @UnAuth
    @ApiOperation(value = "根据帮助分类id查找帮助信息", notes = "根据帮助分类id查找帮助信息（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "帮助分类id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "帮助信息", response = HelpList.class)
    })
    public AjaxResult queryHelpByCateId(@PathVariable long id) {
        return AjaxResult.success(helpListService.queryHelpByCateId(id));
    }

    /**
     * 根据帮助id查找帮助信息
     *
     * @param id 帮助id
     * @return 帮助信息
     */
    @GetMapping("/help/desc")
    @UnAuth
    @ApiOperation(value = "根据帮助id查找帮助信息", notes = "根据帮助id查找帮助信息（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "帮助分类id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "帮助信息", response = HelpList.class)
    })
    public AjaxResult qeryHelpListById(long id) {
        return AjaxResult.success(helpListService.queryHelpById(id));
    }

}
