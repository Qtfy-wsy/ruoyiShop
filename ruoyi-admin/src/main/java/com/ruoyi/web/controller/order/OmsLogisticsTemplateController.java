package com.ruoyi.web.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.order.domain.OmsLogisticsTemplate;
import com.ruoyi.order.service.IOmsLogisticsTemplateService;
import com.ruoyi.order.service.LogisticsTemplateServiceApi;
import com.ruoyi.setting.domain.LsProvince;
import com.ruoyi.setting.service.AreaService;
import com.ruoyi.setting.vo.AreaItem;
import com.ruoyi.util.CommonConstant;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物流模版Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/order/OmsLogisticsTemplate")
public class OmsLogisticsTemplateController extends BaseController {
    @Autowired
    private IOmsLogisticsTemplateService omsLogisticsTemplateService;
    /**
     * 注入物流模版服务api接口
     */
    @Autowired
    private LogisticsTemplateServiceApi logisticsTemplateServiceApi;

    /**
     * 注入地区服务接口
     */
    @Autowired
    private AreaService areaService;
    /**
     * 查询物流模版列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsTemplate:list')")
    @GetMapping("/list")
    public TableDataInfo list(OmsLogisticsTemplate omsLogisticsTemplate) {
        omsLogisticsTemplate.setStoreId(CommonConstant.ADMIN_STOREID);
        startPage();
        List<OmsLogisticsTemplate> list = omsLogisticsTemplateService.selectOmsLogisticsTemplateList(omsLogisticsTemplate);
        return getDataTable(list);
    }

    /**
     * 导出物流模版列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsTemplate:export')")
    @Log(title = "物流模版", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OmsLogisticsTemplate omsLogisticsTemplate) {
        List<OmsLogisticsTemplate> list = omsLogisticsTemplateService.selectOmsLogisticsTemplateList(omsLogisticsTemplate);
        ExcelUtil<OmsLogisticsTemplate> util = new ExcelUtil<OmsLogisticsTemplate>(OmsLogisticsTemplate.class);
        return util.exportExcel(list, "OmsLogisticsTemplate");
    }

    /**
     * 获取物流模版详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsTemplate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(omsLogisticsTemplateService.queryLogisticsTemplate(id, CommonConstant.ADMIN_STOREID));
    }

    /**
     * 新增物流模版
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsTemplate:add')")
    @Log(title = "物流模版", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OmsLogisticsTemplate omsLogisticsTemplate) {
        omsLogisticsTemplate.setStoreId(CommonConstant.ADMIN_STOREID);
        return toAjax(omsLogisticsTemplateService.addLogisticsTemplate(omsLogisticsTemplate));
    }

    /**
     * 修改物流模版
     */
    @Log(title = "物流模版", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OmsLogisticsTemplate omsLogisticsTemplate) {
        omsLogisticsTemplate.setStoreId(CommonConstant.ADMIN_STOREID);
        return toAjax(omsLogisticsTemplateService.updateLogisticsTemplate(omsLogisticsTemplate));
    }

    /**
     * 删除物流模版
     */
    @Log(title = "物流模版", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(logisticsTemplateServiceApi.deleteLogisticsTemplate(ids[0], CommonConstant.ADMIN_STOREID));
    }
    /**
     * 设置默认运费模版
     *
     * @param id 模版id
     * @return 成功返回1 失败返回0
     */
    @PutMapping("/defaulttemplate/{id}")
    @ApiOperation(value = "设置默认运费模版", notes = "设置默认运费模版（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "模版id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0", response = Integer.class)
    })
    public int setDefaultTemplate(@PathVariable long id) {
        return omsLogisticsTemplateService.setDefaultTemplate(id, CommonConstant.ADMIN_STOREID);
    }
    /**
     * 查询所有省份包括省份下面的市(修改时候用)
     *
     * @return 返回所有省份
     */
    @GetMapping("/update/allprovinceswithcity")
    @ApiOperation(value = "查询所有省份包括省份下面的市", notes = "查询所有省份包括省份下面的市（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回所有省份", response = LsProvince.class)
    })
    public List<LsProvince> queryAllProvincesWithCityForUpdate() {
        return areaService.queryAllProvinces(AreaItem.CITY);
    }
}
