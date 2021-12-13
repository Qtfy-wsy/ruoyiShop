package com.ruoyi.web.controller.cms;

import com.ruoyi.cms.bean.ColumnList;
import com.ruoyi.cms.service.ColumnListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 伊甸园商城
 * @date 2019-08-29 15:47
 * <p>
 * 栏目列表接口
 */
@RestController
@Api("栏目列表接口")
public class ColumnController {


    /**
     * 注入栏目列表service
     */
    @Autowired
    private ColumnListService columnListService;


    /**
     * 查询所有栏目列表
     *
     * @return 栏目列表集合
     */
    @GetMapping("/column")
    @ApiOperation(value = "查询所有栏目列表", notes = "查询所有栏目列表（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "栏目列表集合", response = ColumnList.class)
    })
    public List<ColumnList> queryColumnList() {
        return columnListService.queryColumnList();
    }


    /**
     * 添加栏目
     *
     * @param columnList 栏目实体类
     * @return 返回码 1 成功 -1 失败
     */
    @PostMapping("/column")
    @ApiOperation(value = "添加栏目", notes = "添加栏目（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码 1 成功 -1 失败", response = Integer.class)
    })
    public int addColumn(@RequestBody ColumnList columnList) {
        return columnListService.addColumn(columnList);
    }


    /**
     * 编辑栏目
     *
     * @param columnList 栏目实体类
     * @return 返回码 1成功 -1失败
     */
    @PutMapping("/column")
    @ApiOperation(value = "编辑栏目", notes = "编辑栏目（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码 1 成功 -1 失败", response = Integer.class)
    })
    public int editColumn(@RequestBody ColumnList columnList) {
        return columnListService.editColumn(columnList);
    }

    /**
     * 删除栏目
     *
     * @param columnList 栏目
     * @return -1 失败 1成功
     */
    @DeleteMapping("/column")
    @ApiOperation(value = "删除栏目", notes = "删除栏目（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码 1 成功 -1 失败", response = Integer.class)
    })
    public int deleteColumn(@RequestBody ColumnList columnList) {
        return columnListService.deleteColumn(columnList);
    }


}
