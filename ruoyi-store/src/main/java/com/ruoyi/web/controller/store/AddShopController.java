package com.ruoyi.web.controller.store;


import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.setting.domain.LsCity;
import com.ruoyi.setting.domain.LsDistrict;
import com.ruoyi.setting.domain.LsProvince;
import com.ruoyi.setting.service.AreaService;
import com.ruoyi.setting.vo.AreaItem;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.store.vo.StoreBusiness;
import com.ruoyi.system.service.ISysStoreUserService;
import com.ruoyi.util.CommonConstant;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 魔金商城
 * @date 2019-08-16 09:31
 * <p>
 * 新增门店控制器
 */
@RestController
@Api(description = "新增门店接口")
public class AddShopController {

    /**
     * 密码工具类
     */
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    /**
     * 会员服务接口
     */
    @Autowired
    private ISysStoreUserService customerService;


    /**
     * 注入开店店铺信息service
     */
    @Autowired
    private ITStoreInfoService storeInfoService;

    /**
     * 注入地区服务接口
     */
    @Autowired
    private AreaService areaService;

    /**
     * 查找所有没有开店的用户手机号
     *
     * @return 没有开店用户手机号集合
     */
    @GetMapping("/addshop/customer")
    @ApiOperation(value = "查找所有没有开店的用户手机号", notes = "查找所有没有开店的用户手机号（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "没有开店用户手机号集合", response = String.class)
    })
    public List<String> queryAllCustomerMobileForCreateStore() {
        return customerService.queryAllCustomerMobileForCreateStore();
    }


    /**
     * 新增门店时新增会员
     *
     * @param customer 会员信息
     * @return 成功返回 1 失败返回0
     */
    @PostMapping("/addshop/customer")
    @ApiOperation(value = "新增门店时新增会员", notes = "新增门店时新增会员（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回 1 失败返回0", response = Integer.class)
    })
    public int addCustomer(@RequestBody SysUser customer) {
        // 设置会员密码
        customer.setPassword(SecurityUtils.encryptPassword(customer.getPassword()));
        return customerService.insertUser(customer);
    }


    /**
     * 校验手机号码是否存在
     *
     * @param mobile 手机号码
     * @return 存在返回>0  不存在返回0
     */
    @GetMapping("/addshop/checkmobile")
    @ApiOperation(value = "校验手机号码是否存在", notes = "校验手机号码是否存在（不需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "mobile", value = "手机号码"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "存在返回>0  不存在返回0", response = Integer.class)
    })
    public int checkMobileExist(String mobile) {
        return customerService.isMobileExist(mobile);
    }


    /**
     * 查询所有省份信息
     *
     * @return 返回所有省份信息
     */
    @GetMapping("/addshop/provinces")
    @ApiOperation(value = "查询所有省份信息", notes = "查询所有省份信息（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回所有省份信息", response = LsProvince.class)
    })
    public List<LsProvince> queryAllProvinces() {
        return areaService.queryAllProvinces(AreaItem.NO);
    }


    /**
     * 根据省份id查询市
     *
     * @param parentId 省份id
     * @return 返回市信息
     */
    @GetMapping("/addshop/city{parentId}")
    @ApiOperation(value = "根据省份id查询市", notes = "根据省份id查询市（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "parentId", value = "省份id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回市信息", response = LsCity.class)
    })
    public List<LsCity> queryCityByProvinceId(@PathVariable long parentId) {
        return areaService.queryCityByProvinceId(parentId);
    }

    /**
     * 根据市id查询下面的区
     *
     * @param parentId 市id
     * @return 返回市下面的区
     */
    @GetMapping("/addshop/district/{parentId}")
    @ApiOperation(value = "根据市id查询下面的区", notes = "根据市id查询下面的区（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "parentId", value = "市id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回市下面的区", response = LsDistrict.class)
    })
    public List<LsDistrict> queryDistrictByCityId(@PathVariable long parentId) {
        return areaService.queryDistrictByCityId(parentId);
    }


    /**
     * 校验店铺名是否存在
     *
     * @param storeName 店铺名
     * @return >0存在 否则不存在
     */
    @GetMapping("/addshop/storename")
    @ApiOperation(value = "校验店铺名是否存在", notes = "校验店铺名是否存在（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "storeName", value = "店铺名"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = ">0存在 否则不存在", response = Integer.class)
    })
    public int checkStoreNameExist(String storeName) {
        return storeInfoService.checkStoreNameExist(storeName, CommonConstant.QUERY_WITH_NO_STORE);
    }

    /**
     * 校验公司名是否存在
     *
     * @param companyName 公司名
     * @return >0存在 否则不存在
     */
    @GetMapping("/addshop/companyname")
    @ApiOperation(value = "校验公司名是否存在", notes = "校验公司名是否存在（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "companyName", value = "公司名"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = ">0存在 否则不存在", response = Integer.class)
    })
    public int checkCompanyNameExist(String companyName) {
        return storeInfoService.checkCompanyNameExist(companyName, CommonConstant.QUERY_WITH_NO_STORE);
    }


    /**
     * 新增店铺
     *
     * @param storeBusiness 店铺信息
     * @return -1用户不存在 1成功 -2 该用户下已有店铺
     */
    @PostMapping("/addshop")
    @ApiOperation(value = "新增店铺", notes = "新增店铺（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "-1用户不存在 1成功 -2 该用户下已有店铺", response = Integer.class)
    })
    public int fillAllStoreInfo(@RequestBody StoreBusiness storeBusiness) {
        return storeInfoService.addStore(storeBusiness);
    }


}
