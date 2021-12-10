package com.ruoyi.web.controller.member;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsAttention;
import com.ruoyi.goods.service.IPmsAttentionService;
import com.ruoyi.integral.domain.CustomerPoint;
import com.ruoyi.integral.service.CustomerPointService;
import com.ruoyi.marketing.domain.CouponCode;
import com.ruoyi.marketing.service.CouponService;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.domain.UmsPreDepositRecord;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.IUmsPreDepositRecordService;
import com.ruoyi.member.vo.QueryCriteria;
import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.service.IOmsOrderService;
import com.ruoyi.order.service.OrderServiceApi;
import com.ruoyi.order.vo.OrderItem;
import com.ruoyi.order.vo.QueryOrderCriteria;
import com.ruoyi.setting.domain.LsStationLetter;
import com.ruoyi.setting.service.ILsStationLetterService;
import com.ruoyi.util.BaseResponse;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员Controller
 *
 * @author 商城
 */
@RestController
@RequestMapping("/member/UmsMember")
public class UmsMemberController extends BaseController {
    @Autowired
    private IUmsMemberService umsMemberService;
    /**
     * 注入优惠券service
     */
    @Autowired
    private CouponService couponService;

    /**
     * 注入商品关注服务接口
     */
    @Autowired
    private IPmsAttentionService attentionService;

    /**
     * 注入会员积分服务接口
     */
    @Autowired
    private CustomerPointService customerPointService;
    /**
     * 订单服务接口
     */
    @Autowired
    private IOmsOrderService orderService;

    /**
     * 注入订单混合api接口
     */
    @Autowired
    private OrderServiceApi orderServiceApi;
    /**
     * 注入序号生成器
     */
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 预存款服务接口
     */
    @Autowired
    private IUmsPreDepositRecordService predepositRecordService;
    /**
     * 注入站内信服务接口
     */
    @Autowired
    private ILsStationLetterService stationLetterService;

    /**
     * 查询会员列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMember:list')")
    @GetMapping("/list")
    public TableDataInfo list(UmsMember umsMember) {
        startPage();
        List<UmsMember> list = umsMemberService.selectUmsMemberList(umsMember);
        return getDataTable(list);
    }
    /**
     * 新增站内信
     *
     * @param stationLetters 站内信实体
     * @return 成功返回>1 失败返回0
     */
    @PostMapping("/stationletters")
    @ApiOperation(value = "新增站内信", notes = "新增站内信（需要认证）")
    public int addstationLetters(@RequestBody List<LsStationLetter> stationLetters) {
        return stationLetterService.addStationLetters(stationLetters);
    }
    /**
     * 校验手机号码是否存在
     *
     * @param mobile 手机号码
     * @return 存在返回>0  不存在返回0
     */
    @GetMapping("/checkmobileexist/{mobile}")
    @ApiOperation(value = "校验手机号码是否存在", notes = "校验手机号码是否存在（不需要认证）")
    public int checkMobileExist(@PathVariable String mobile) {
        return umsMemberService.isMobileExist(mobile);
    }
    /**
     * 校验邮箱是否存在
     *
     * @param email 邮箱
     * @return 存在返回>0  不存在返回0
     */
    @GetMapping("/checkemailexist/{email}")
    @ApiOperation(value = "校验邮箱是否存在", notes = "校验邮箱是否存在（不需要认证）")
    public int checkEmailExist(@PathVariable String email) {
        return umsMemberService.isEmailExist(email);
    }

    /**
     * 导出会员列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMember:export')")
    @Log(title = "会员", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UmsMember umsMember) {
        List<UmsMember> list = umsMemberService.selectUmsMemberList(umsMember);
        ExcelUtil<UmsMember> util = new ExcelUtil<UmsMember>(UmsMember.class);
        return util.exportExcel(list, "UmsMember");
    }

    /**
     * 获取会员详细信息
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMember:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(umsMemberService.selectUmsMemberById(id));
    }

    /**
     * 新增会员
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMember:add')")
    @Log(title = "会员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody String param) {
        UmsMember umsMember = JSON.parseObject(param,UmsMember.class);
        umsMember.setCreateBy(SecurityUtils.getUsername());
        umsMember.setSource("4");
        return toAjax(umsMemberService.addCustomer(umsMember));
    }

    /**
     * 修改会员
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMember:edit')")
    @Log(title = "会员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UmsMember umsMember) {
        return toAjax(umsMemberService.updateUmsMember(umsMember));
    }

    /**
     * 删除会员
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMember:remove')")
    @Log(title = "会员", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(umsMemberService.deleteUmsMemberByIds(ids));
    }

    /**
     * 分页查询用户订单
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 用户订单列表
     */
    @GetMapping("/customer/orderlist")
    @ApiOperation(value = "分页查询用户订单", notes = "分页查询用户订单（需要认证）")
    public BaseResponse queryCustomerOrderList(@ApiIgnore PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria) {
        queryCriteria.setStoreId(CommonConstant.ADMIN_STOREID);
        return BaseResponse.build(orderService.queryOrders(pageHelper, queryCriteria));
    }

    /**
     * 根据订单id查询会员订单信息
     *
     * @param id 订单id
     * @return 订单信息
     */
    @GetMapping("/customer/orderdetail/{id}")
    @ApiOperation(value = "根据订单id查询会员订单信息", notes = "根据订单id查询会员订单信息（需要认证）")

    public OmsOrder queryOrderDetailById(@PathVariable long id) {
        return orderServiceApi.queryOrderDetailById(id, CommonConstant.QUERY_WITH_NO_STORE, CommonConstant.QUERY_WITH_NO_CUSTOMER, OrderItem.LOG, OrderItem.ATTR, OrderItem.SKUS);
    }

    /**
     * 分页查询用户优惠券
     *
     * @param pageHelper 分页帮助类
     * @param customerId 会员id
     * @param status     null:全部 1:可用 2:已使用 3:已过期
     * @return 用户优惠券列表
     */
    @GetMapping("/customer/couponlist")
    @ApiOperation(value = "分页查询用户优惠券", notes = "分页查询用户优惠券（需要认证）")
    public BaseResponse queryCustomerCouponList(@ApiIgnore PageHelper<CouponCode> pageHelper, long customerId, String status) {
        return BaseResponse.build(couponService.queryCouponCodeByCustomerId(pageHelper, customerId, status));
    }

    /**
     * 分页查询用户关注列表
     *
     * @param pageHelper 分页帮助类
     * @param customerId 会员id
     * @return 会员关注列表
     */
    @GetMapping("/customer/attentionlist")
    @ApiOperation(value = "分页查询用户关注列表", notes = "分页查询用户关注列表（需要认证）")
    public BaseResponse queryAttentionList(@ApiIgnore PageHelper<PmsAttention> pageHelper, long customerId) {
        return BaseResponse.build(attentionService.queryAttentions(pageHelper, customerId));
    }

    /**
     * 分页查询用户积分列表
     *
     * @param pageHelper 分页帮助类
     * @param customerId 会员id
     * @return 用户积分列表
     */
    @GetMapping("/customer/pointlist")
    @ApiOperation(value = "分页查询用户积分列表", notes = "分页查询用户积分列表（需要认证）")
    @PreAuthorize("hasAuthority('customer/querycustomerpointlist')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "customerId", value = "会员id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "用户积分列表", response = CustomerPoint.class)
    })
    public BaseResponse queryCustomerPointList(@ApiIgnore PageHelper<CustomerPoint> pageHelper, long customerId) {
        return BaseResponse.build(customerPointService.queryCustomerPoints(pageHelper, customerId, null,null));
    }

    /**
     * 查询用户积分总额
     *
     * @param customerId 会员id
     * @return 用户积分总额
     */
    @GetMapping("/customer/totalpoint/{customerId}")
    @ApiOperation(value = "查询用户积分总额", notes = "查询用户积分总额（需要认证）")
    @PreAuthorize("hasAuthority('customer/querycustomertotalpoint')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "customerId", value = "会员id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "用户积分总额", response = Integer.class)
    })
    public int queryCustomerTotalPoint(@PathVariable long customerId) {
        return customerPointService.queryCustomerPointCount(customerId);
    }

    /**
     * 新增用户积分
     *
     * @param customerPoint 会员积分实体
     * @return 成功1 否则失败
     */
    @PostMapping("/customer/addpoint")
    @ApiOperation(value = "新增用户积分", notes = "新增用户积分（需要认证）")
    @PreAuthorize("hasAuthority('customer/addcustomerpoint')")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功1 否则失败", response = Integer.class)
    })
    public int addCustomerPoint(@RequestBody CustomerPoint customerPoint) {
        return customerPointService.addCustomerPoint(customerPoint.setValuesForManagerAdd());
    }

    /**
     * 分页查询用户预存款列表
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 用户预存款列表
     */
    @GetMapping("/customer/predepositlist")
    @ApiOperation(value = "分页查询用户预存款列表", notes = "分页查询用户预存款列表（需要认证）")
    @PreAuthorize("hasAuthority('customer/querypredepositlist')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "customerId", value = "会员id"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "transCode", value = "交易单号"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "startTime", value = "开始时间"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "endTime", value = "结束时间"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "用户预存款列表", response = UmsPreDepositRecord.class)
    })
    public BaseResponse queryPredepositList(@ApiIgnore PageHelper<UmsPreDepositRecord> pageHelper, @ApiIgnore QueryCriteria queryCriteria) {
        return BaseResponse.build(predepositRecordService.queryPredepositRecords(pageHelper, queryCriteria));
    }

    /**
     * 查询用户可用预存款
     *
     * @param customerId 会员id
     * @return 用户可用预存款
     */
    @GetMapping("/customer/currentpredeposit/{customerId}")
    @ApiOperation(value = "查询用户可用预存款", notes = "查询用户可用预存款（需要认证）")
    @PreAuthorize("hasAuthority('customer/querycurrentpredeposit')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "customerId", value = "会员id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "用户可用预存款", response = BigDecimal.class)
    })
    public BigDecimal queryCurrentPredeposit(@PathVariable long customerId) {
        return predepositRecordService.queryCutomerAllPredeposit(customerId);
    }

    /**
     * 更新会员消费金额
     *
     * @param customMoney 会员消费金额实体类
     * @return 成功1 否则失败 -1 更改后金额小于0
     */
    @PutMapping("/customer/money")
    @ApiOperation(value = "修改会员信息", notes = "修改会员信息（需要认证）")
    @PreAuthorize("hasAuthority('customer/updatecustommoney')")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功1 否则失败 -1 更改后金额小于0", response = Integer.class)
    })
    public int updateCustomMoney(@RequestBody CustomMoney customMoney) {
        return umsMemberService.updateCustomerConsumptionAmountByAdmin(customMoney.getCustomerId(), customMoney.getMoney());
    }
    /**
     * 增加预存款记录
     *
     * @param predepositRecord 预存款记录实体
     * @return 成功1 否则失败  -1 更改后金额小于0
     */
    @PostMapping("/customer/addpredeposit")
    @ApiOperation(value = "增加预存款记录", notes = "增加预存款记录（需要认证）")
    @PreAuthorize("hasAuthority('customer/addcustomerpredeposit')")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功1 否则失败  -1 更改后金额小于0", response = Integer.class)
    })
    public int addCustomerPredeposit(@RequestBody UmsPreDepositRecord predepositRecord) {
        return predepositRecordService.addPredepositRecord(UmsPreDepositRecord.buildManageChange(predepositRecord.getMoney(), predepositRecord.getCustomerId(), snowflakeIdWorker.nextId()));
    }
    /**
     * 会员消费金额实体类
     */
    @Data
    private static class CustomMoney {

        /**
         * 会员id
         */
        private long customerId;

        /**
         * 消费金额
         */
        private BigDecimal money;

    }

}
