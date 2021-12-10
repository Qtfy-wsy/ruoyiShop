package com.ruoyi.pointmall;


import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.integral.domain.PointCate;
import com.ruoyi.integral.domain.PointSku;
import com.ruoyi.integral.service.PointCateService;
import com.ruoyi.integral.service.PointSkuService;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 积分商品详情控制器
 *
 * @author 魔金商城 created on 2020/4/21
 */
@Controller
@Api( "积分商品详情接口")
public class PointSkuController {

    /**
     * 注入积分商品服务
     */
    @Autowired
    private PointSkuService pointSkuService;

    /**
     * 注入积分商品分类服务
     */
    @Autowired
    private PointCateService pointCateService;


    /**
     * 分页查询积分商品信息
     *
     * @param pageHelper 分页帮助类
     * @param cateId     积分商品分类id
     * @return 积分商品集合
     */
    @UnAuth
    @RequestMapping("/querypointskulist")
    @ResponseBody
    @ApiOperation(value = "分页查询积分商品信息", notes = "分页查询积分商品信息（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "cateId", value = "积分商品分类id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "积分商品集合", response = PointSku.class)
    })
    public AjaxResult queryPointSkuList(@ApiIgnore PageHelper<PointSku> pageHelper, Long cateId) {
        return AjaxResult.success(pointSkuService.queryPointSkus(pageHelper, cateId, null, true));
    }

    /**
     * 查询所有积分商品分类
     */
    @UnAuth
    @RequestMapping("/querypointtype")
    @ResponseBody
    @ApiOperation(value = "查询所有积分商品分类", notes = "查询所有积分商品分类（不需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "积分商品分类", response = PointCate.class)
    })
    public AjaxResult queryAllPointCates() {
        return AjaxResult.success(pointCateService.queryAllPointCates());
    }


    /**
     * 查询积分商品信息详情
     *
     * @param id 积分商品id
     * @return 积分商品实体
     */
    @UnAuth
    @RequestMapping("/querypointskudetail")
    @ResponseBody
    @ApiOperation(value = "查询积分商品信息详情", notes = "查询积分商品信息详情（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "id", value = "积分商品id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "积分商品实体", response = PointSku.class)
    })
    public AjaxResult queryPointSkuDetail(long id) {
        return AjaxResult.success(pointSkuService.queryPointSkuById(id));
    }

    /**
     * 查询推荐的积分商品列表
     *
     * @param num 推荐单品的数量
     * @return 返回推荐的积分商品列表
     */
    @UnAuth
    @RequestMapping("/queryhotpointskulist")
    @ResponseBody
    @ApiOperation(value = "查询推荐的积分商品列表", notes = "查询推荐的积分商品列表（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "num", value = "推荐单品的数量"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回推荐的积分商品列表", response = PointSku.class)
    })
    public AjaxResult queryHotPointSkuList(int num) {
        return AjaxResult.success(pointSkuService.queryHotPointSkus(num));
    }

}


