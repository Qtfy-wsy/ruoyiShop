package com.ruoyi.web.controller.setting;


import com.alibaba.fastjson.JSON;
import com.ruoyi.setting.bean.BaseInfoSet;
import com.ruoyi.setting.service.BaseInfoSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 商城
 * 信息设置控制器
 */
@RestController
@Api(description = "信息设置接口")
public class SettingController {

    /**
     * 注入信息设置实现类
     */
    @Autowired
    private BaseInfoSetService baseInfoSetService;

    /**
     * 查询基本信息和高级信息设置,用于页面图标展示,不需要拦截
     *
     * @return 基本信息和高级信息设置实体类
     */
    @GetMapping("/baseinfoset")
    public BaseInfoSet queryBaseInfoSetUnAuth() {
        return baseInfoSetService.queryBaseInfoSet();
    }

    /**
     * 查询基本信息和高级信息设置
     *
     * @return 基本信息和高级信息设置
     */
    @GetMapping("infoset/baseinfoset")
    public BaseInfoSet queryBaseInfoSet() {
        return baseInfoSetService.queryBaseInfoSet();
    }

    /**
     * 修改基本信息设置
     *
     * @param param 基本信息设置
     * @return 成功1 否则失败
     */
    @PutMapping("baseinfoset")
    @ApiOperation(value = "修改基本信息设置", notes = "修改基本信息设置（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功1 否则失败", response = Integer.class)
    })
    public int updateBaseInfoSet(@RequestBody String param) {
        BaseInfoSet baseInfoSet = JSON.parseObject(param,BaseInfoSet.class);
        return baseInfoSetService.editBaseInfoSet(baseInfoSet, 1);
    }

}
