package com.ruoyi.member.service.impl;

import com.ruoyi.common.md5.MD5Utils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.mapper.UmsMemberMapper;
import com.ruoyi.member.service.IUmsMemberLevelService;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.IUmsPreDepositRecordService;
import com.ruoyi.member.vo.CustomerSearchCondition;
import com.ruoyi.member.vo.StoreStaff;
import com.ruoyi.store.vo.NewCustomerStatistics;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 会员Service业务层处理
 *
 * @author 商城
 */

@Service
public class UmsMemberServiceImpl implements IUmsMemberService {
    private static final Logger log = LoggerFactory.getLogger(UmsMemberServiceImpl.class);

    @Autowired
    private UmsMemberMapper umsMemberMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(UmsMemberServiceImpl.class);

    /**
     * 注入会员数据库接口
     */
    @Autowired
    private UmsMemberMapper customerMapper;

    /**
     * 注入预存款服务接口
     */
    @Autowired
    private IUmsPreDepositRecordService predepositRecordService;

    /**
     * 注入会员等级服务接口
     */
    @Autowired
    private IUmsMemberLevelService customerLevelService;

    /**
     * 注入店铺角色service
     */
    //  @Autowired
    // private StoreRoleService storeRoleService;


    /**
     * 注入微信用户关联服务接口
     */
    //  @Autowired
    //  @Lazy
    //  private WeChatCustomerLinkService weChatCustomerLinkService;


    /**
     * 注入客服服务
     */
    //  @Autowired
//    private CustomerServiceService customerServiceService;

    /**
     * 密码工具类
     */
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;


    @Override
    public PageHelper<UmsMember> queryCustomers(PageHelper<UmsMember> pageHelper, CustomerSearchCondition customerSearchCondition) {
        logger.debug("queryCustomers and pageHelper:{} \r\n customerSearchCondition:{}", pageHelper, customerSearchCondition);
        // 查询参数
        Map<String, Object> params = customerSearchCondition.getSearchParams();
        return pageHelper.setListDates(clearPassword(setCustomerLevelName(setCustomerPredeposit(customerMapper.queryCustomers(pageHelper.getQueryParams(params, customerMapper.queryCustomerCount(params)))))));
    }

    @Override
    public UmsMember queryCustomerWithNoPasswordById(long id) {
        logger.debug("queryCustomerById and id:{}", id);
        UmsMember customer = queryCustomerInfoById(id);
        if (Objects.isNull(customer)) {
            logger.error("member is not exist and id :{}", id);
            return customer;
        }
        customer.setAllPredeposit(predepositRecordService.queryCutomerAllPredeposit(customer.getId()));
        customer.setCustomerLevel(customerLevelService.queryCustomerLevelByMoney(customer.getConsumptionAmount()));
        return customer.clearPassword();
    }


    /**
     * 根据会员id查询会员信息(包含密码信息)
     *
     * @param id 会员id
     * @return 返回会员信息
     */
    @Override
    public UmsMember queryCustomerInfoById(long id) {
        logger.debug("queryCustomerInfoById and id:{}", id);
        return customerMapper.queryCustomerById(id);
    }

    @Override
    public UmsMember queryCustomerWithStoreQQ(long id) {
        logger.debug("queryCustomerWithStoreQQ and id:{}", id);
        UmsMember customer = this.queryCustomerWithNoPasswordById(id);
        //设置客服qq
        /*CustomerService customerService = customerServiceService.queryCustomerService();
        if (Objects.nonNull(customerService) && !CollectionUtils.isEmpty(customerService.getCustomerServiceInfo())) {
            member.setServiceQQ(customerService.getCustomerServiceInfo().stream().findFirst().orElse(new CustomerServiceInfo()).getQq());
        }*/
        return customer;
    }

    @Override
    public UmsMember queryCustomerWithCustomerLevel(long id) {
        logger.debug("queryCustomerWithCustomerLevel and id :{}", id);
        UmsMember customer = customerMapper.queryCustomerById(id);
        if (Objects.isNull(customer)) {
            logger.error("member is not exist and id :{}", id);
            return customer;
        }
        customer.clearPassword().setCustomerLevel(customerLevelService.queryCustomerLevelByMoney(customer.getConsumptionAmount()));
        return customer;
    }


    @Transactional
    @Override
    public int deleteCustomers(List<Long> ids) {
        logger.debug("deleteCustomers and ids:{}", ids);
        if (CollectionUtils.isEmpty(ids)) {
            logger.error("deleteCustomers fail due to ids is null....");
            return 0;
        }

        // 删除会员和微信的绑定关系
        deleteCustomerLink(ids);

        return customerMapper.deleteCustomers(ids);
    }

    /**
     * 删除会员和openid的绑定关系
     *
     * @param ids 会员id
     */
    private void deleteCustomerLink(List<Long> ids) {
        //  ids.stream().forEach(id -> weChatCustomerLinkService.unbindWeChatCustomerLink(id, ""));
    }

    /**
     * 添加用户
     *
     * @param customer 用户对象
     * @return 主键id
     */
    @Override
    public int addCustomer(UmsMember customer) {
        logger.debug("addCustomer and member :{}", customer);

        // 设置会员密码
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        //设置自己的推荐码
        customer.addSelfRecommondCode();

        // 如果有推荐人 则查询推荐人信息
        if (customer.hasRecommonded()) {
            // 推荐人
            UmsMember recommonded = customerMapper.queryCustomerByRecommondCode(customer.getRecommondCode());

            // 如果推荐人不存在  则返回错误
            if (Objects.isNull(recommonded)) {
                logger.error("addCustomer fail due to recommoned is not exist...and code:{}", customer.getRecommondCode());
                return -10;
            }
            // 设置推荐人的会员id
            customer.setRecommended(recommonded.getId());

            // 设置会员的二级推荐人/ 推荐人的推荐人
            customer.setSRecommended(recommonded.getRecommended());
        }
        customerMapper.insertUmsMember(customer);
        return 1;
    }

    /**
     * 自动添加用户 用于免密登录场景
     *
     * @param customer 用户对象
     * @return 主键id
     */
    @Override
    public Long autoAddCustomer(UmsMember customer) {
        logger.debug("addCustomer and member :{}", customer);
        //设置自己的推荐码
        customer.addSelfRecommondCode();
        // 设置会员密码
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerMapper.insertUmsMember(customer);
        return customer.getId();
    }

    @Override
    public int updateCustomer(UmsMember customer) {
        logger.debug("updateCustomer and member:{}", customer);
        if (Objects.isNull(customer)) {
            logger.error("updateCustomer fail due to member is null...");
            return 0;
        }
        if (!StringUtils.isEmpty(customer.getRecommondCode())) {
            UmsMember oldCustomer = customerMapper.queryCustomerById(customer.getId());
            if (-1 != oldCustomer.getRecommended()) {
                logger.error("updateCustomer fail :already has recommendCode");
                return -2;
            }
            //查询推荐人
            UmsMember recommendCustomer = customerMapper.queryCustomerByRecommondCode(customer.getRecommondCode());
            if (Objects.isNull(recommendCustomer)) {
                logger.error("updateCustomer fail : queryCustomerByRecommondCode fail ");
                return -3;
            }
            if (customer.getId() == recommendCustomer.getId()) {
                logger.error("updateCustomer fail : recommendCustomer  is self  ");
                return -4;
            }
            customer.setRecommended(recommendCustomer.getId());
        }
        return customerMapper.updateUmsMember(customer);
    }


    /**
     * 更新用户密码
     *
     * @param customer      会员实体类
     * @param oldPassword   原始密码
     * @param newPassword   新密码
     * @param reNewPassword 重新输入
     * @return 0 未登录 -1 输入为空 1 成功过 2 两次输入不一致 3 原密码不正确
     */
    @Override
    public int updateCustomerPassWord(UmsMember customer, String oldPassword, String newPassword, String reNewPassword) {
        if (Objects.isNull(customer)) {
            return 0;
        }
        //判断是否问空
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(reNewPassword)) {
            logger.error("params is error...");
            return -1;
        }
        if (!newPassword.equals(reNewPassword)) {
            logger.error("two password is not same...");
            return 2;
        }
        // 根据用户名查询管理员信息queryManagerByName
        UmsMember queryCustomer = customerMapper.queryCustomerById(customer.getId());
        //判断原密码是否正确
        if (!passwordEncoder.matches(oldPassword, queryCustomer.getPassword())) {//oldPassword
            logger.error("old password is error...");
            return 3;
        }

        // 设置密码
        customer.setPassword(passwordEncoder.encode(newPassword));

        return customerMapper.updatePassWord(customer);
    }

    /**
     * 查询店铺员工
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @return 店铺员工信息
     */
    @Override
    public PageHelper<StoreStaff> queryStoreStall(PageHelper<StoreStaff> pageHelper, long storeId, String name) {
        logger.debug("queryCustomers and pageHelper:{} \r\n customerSearchCondition:{}", pageHelper, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("name", name);
        return pageHelper.setListDates(customerMapper.queryStoreStall(pageHelper.getQueryParams(params, customerMapper.queryStoreStallCount(params))));
    }

    /**
     * 添加员工会员
     *
     * @param storeStaff 员工实体类
     * @return 添加返回码
     */
    @Override
    @Transactional
    public int addStoreStaff(StoreStaff storeStaff, long storeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("name", "");

        // 判断员工的数量是否已经大于50个
        if (customerMapper.queryStoreStallCount(params) >= 50) {
            return -1;
        }

        // 判断登录名称是否包含中文
        if (MD5Utils.getInstance().isContainChinese(storeStaff.getUserName())) {
            logger.error("addStoreStaff fail due to username has Chinese");
            return -2;
        }

        // 判断登录名称是否是纯数字
        if (storeStaff.getUserName().matches("[0-9]+")) {
            logger.error("addStoreStaff fail due to username is number");
            return -4;
        }
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("username", storeStaff.getUserName());
        UmsMember customer = customerMapper.queryCustomerByName(queryParams);

        // 用户名称已经存在
        if (Objects.nonNull(customer)) {
            logger.error("addStoreStaff fail : username is exist");
            return -3;
        }
        // 设置密码
        storeStaff.setPassword(passwordEncoder.encode(storeStaff.getPassword()));
        //添加会员-员工
        customerMapper.addStoreStaff(storeStaff.getAddStoreStaffData(storeStaff, storeId, "3"));
        //添加员工关联角色
        return 1;
        //return storeRoleService.linkStaffRole(new RoleAndCustomer().getRoleAndCustomer(storeStaff.getId(), storeStaff.getRoleId()));
    }

    /**
     * 删除会员和关联的角色
     *
     * @param customerIds 会员id
     * @return 删除返回码
     */
    @Override
    @Transactional
    public int delCustomersAndStaffs(Long[] customerIds) {
        if (ArrayUtils.isEmpty(customerIds)) {
            return -1;
        }
        //删除会员
        customerMapper.deleteCustomers(Arrays.asList(customerIds));
        return 1;
        //删除关联的角色
        // return storeRoleService.deleteStoreStaff(Arrays.asList(customerIds));
    }

    /**
     * 编辑员工
     *
     * @param storeStaff 员工实体类
     * @return 编辑返回码
     */
    @Override
    public int editCustomerAndStaff(StoreStaff storeStaff) {
        //storeRoleService.updateRoleId(new RoleAndCustomer().getRoleAndCustomer(storeStaff.getId(), storeStaff.getRoleId()));
        return customerMapper.updateStatus(storeStaff);
    }

    /**
     * 更新会员storeId和type
     *
     * @param customer 会员实体类
     * @return 添加返回码
     */
    @Override
    public int updateStoreIdAndType(UmsMember customer) {
        return customerMapper.updateStoreIdAndType(customer);
    }

    /**
     * 根据店铺id查询会员id
     *
     * @param storeId 店铺id
     * @return 会员信息
     */
    @Override
    public UmsMember queryCustomerIdByStoreId(long storeId) {
        return customerMapper.queryCustomerIdByStoreId(storeId);
    }

    /**
     * 更新会员个人信息
     *
     * @param customer 会员实体类
     * @param param    参数
     * @return 编辑返回码
     */
    @Override
    public int updatePersonalInfo(UmsMember customer, int param) {
        Map<String, Object> map = new HashMap<>();
        map.put("param", param);
        map.put("customer", customer);
        return customerMapper.updatePersonalInfo(map);
    }

    @Override
    public UmsMember queryCustomerByName(String userName) {
        logger.debug("queryCustomerByName and userName:{}", userName);

        if (StringUtils.isEmpty(userName)) {
            logger.error("queryCustomerByName fail due to userName is empty ....");
            return null;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("username", userName);
        return customerMapper.queryCustomerByName(params);
    }

    @Override
    public UmsMember queryCustomerByNameInWriteDataSource(String userName) {
        logger.debug("queryCustomerByNameInWriteDataSource and userName:{}", userName);

        if (StringUtils.isEmpty(userName)) {
            logger.error("queryCustomerByNameInWriteDataSource fail due to userName is empty ....");
            return null;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("username", userName);
        return customerMapper.queryCustomerByNameInWriteDataSource(params);
    }

    @Override
    public int unlockUser(long customerId) {
        logger.debug("unlockUser and customerId:{}", customerId);
        return customerMapper.unlockUser(customerId);
    }

    @Override
    public int updateLoginErrorCount(long customerId) {
        logger.debug("updateLoginErrorCount and customerId:{}", customerId);
        return customerMapper.updateLoginErrorCount(customerId);
    }

    @Override
    public int lockUser(long customerId) {
        logger.debug("lockUser and customerId:{}", customerId);
        return customerMapper.lockUser(customerId);
    }

    @Override
    public int updateLoginTime(long customerId) {
        return customerMapper.updateLoginTime(customerId);
    }

    @Override
    public int isMobileExist(String mobile) {
        logger.debug("isMobileExist and mobile :{}", mobile);

        if (StringUtils.isEmpty(mobile)) {
            logger.error("mobile is empty.....");
            return 0;
        }
        return customerMapper.queryByMobile(mobile);
    }

    @Override
    public int isEmailExist(String email) {
        logger.debug("isEmailExist and email:{}", email);
        if (StringUtils.isEmpty(email)) {
            logger.error("email is empty.....");
            return 0;
        }
        return customerMapper.queryByEmail(email);
    }

    @Override
    public int updatePassword(long customerId, String password) {
        logger.debug("updatePassword and customerId:{}", customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("password", passwordEncoder.encode(password));
        return customerMapper.updatePassword(params);
    }

    @Override
    public int updatePayPassword(long customerId, String payPassword) {
        logger.debug("updatePayPassword and customerId:{}", customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("payPassword", passwordEncoder.encode(payPassword));
        return customerMapper.updatePayPassword(params);
    }

    @Override
    public int bindNewMobile(long customerId, String mobile) {
        logger.debug("bindNewMobile and customerId:{} \r\n mobile:{}", customerId, mobile);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("mobile", mobile);
        return customerMapper.bindNewMobile(params);
    }

    @Override
    public int updatePasswordByMobile(String mobile, String password) {

        logger.debug("updatePasswordByMobile and mobile:{}", mobile);

        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", passwordEncoder.encode(password));
        return customerMapper.updatePasswordByMobile(params);
    }


    @Override
    public int updateCustomerConsumptionAmount(long customerId, BigDecimal orderMoney) {
        logger.debug("updateCustomerConsumptionAmount and customerId:{} \r\n orderMoney:{}", customerId, orderMoney);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("orderMoney", orderMoney);
        return customerMapper.updateCustomerConsumptionAmount(params);
    }

    @Override
    public int updateCustomerConsumptionAmountByAdmin(long customerId, BigDecimal money) {
        logger.debug("updateCustomerConsumptionAmountByAdmin and customerId:{} \r\n orderMoney:{}", customerId, money);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("orderMoney", money);
        UmsMember customer = customerMapper.queryCustomerById(customerId);
        if (!customer.checkConsumptionAmount(money)) {
            logger.error("updateCustomerConsumptionAmountByAdmin fail : will be minus");
            return -1;
        }
        return customerMapper.updateCustomerConsumptionAmount(params);
    }

    @Override
    public int updateModifiedEmailInfo(String checkCode, String email, long customerId) {
        logger.debug("updateModifiedEmailInfo and checkCode:{}\r\n email:{}\r\n customerId:{}", checkCode, email, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("modifiedEmail", email);
        params.put("checkCode", checkCode);
        return customerMapper.updateModifiedEmailInfo(params);
    }

    @Override
    public int modifiedEmail(long customerId) {
        logger.debug("modifiedEmail and customerId:{}", customerId);
        return customerMapper.modifiedEmail(customerId);
    }

    @Override
    public UmsMember selectModifiedEmailInfo(String checkCode, long customerId) {
        logger.debug("selectModifiedEmailInfo and checkCode:{}\r\ncustomerId:{}", checkCode, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("checkCode", checkCode);
        return customerMapper.selectModifiedEmailInfo(params);
    }

    @Override
    public int updateSignNum(long customerId, boolean continueFlag) {
        logger.debug("updateSignNum and customerId:{}\r\n continueFlag:{}", customerId, continueFlag);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("continueFlag", continueFlag);
        return customerMapper.updateSignNum(params);
    }

    @Override
    public int updateCustomerCommission(long customerId, BigDecimal commission) {
        logger.debug("updateCustomerCommission and customerId:{} \r\n commission:{}", customerId, commission);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("commission", commission);
        return customerMapper.updateCustomerCommission(params);
    }

    @Override
    public List<UmsMember> queryCustomersByIds(List<Long> ids) {
        logger.debug("queryCustomersByIds and ids:{}", Arrays.toString(ids.toArray()));
        return CollectionUtils.isEmpty(ids) ? new ArrayList<>() : customerMapper.queryCustomersByIds(ids);
    }

    @Override
    public List<UmsMember> queryAllCustomer() {
        logger.debug("queryAllCustomer...");
        return customerMapper.queryAllCustomer();
    }

    @Override
    public int queryNewCustomerToday() {
        logger.debug("begin to queryNewCustomerToday .....");
        return customerMapper.queryNewCustomerToday();
    }

    @Override
    public int queryNewCustomerThisWeek() {
        logger.debug("begin to queryNewCustomerThisWeek .....");
        return customerMapper.queryNewCustomerThisWeek();
    }

    @Override
    public List<NewCustomerStatistics> queryNewCustomerThisWeekGroupByDay() {
        logger.debug("begin to queryNewCustomerThisWeekGroupByDay .....");
        return customerMapper.queryNewCustomerThisWeekGroupByDay();
    }

    @Override
    public List<String> queryAllCustomerMobileForCreateStore() {
        logger.debug("queryAllCustomerMobile....");
        return customerMapper.queryAllCustomerMobileForCreateStore();
    }

    @Override
    public int bindCustomerRecommendCode(String recommendCode, long customerId) {
        logger.debug("bindCustomerRecommendCode and recommendCode:{} \r\n customerId:{}", recommendCode, customerId);
        //查询用户
        UmsMember customer = customerMapper.queryCustomerById(customerId);
        if (Objects.isNull(customer)) {
            logger.error("bindCustomerRecommendCode fail : member is null");
            return -1;
        }
        if (-1 != customer.getRecommended()) {
            logger.error("bindCustomerRecommendCode fail :already has recommendCode");
            return -2;
        }
        //查询推荐人
        UmsMember recommendCustomer = customerMapper.queryCustomerByRecommondCode(recommendCode);
        if (Objects.isNull(recommendCustomer)) {
            logger.error("bindCustomerRecommendCode fail : queryCustomerByRecommondCode fail ");
            return -3;
        }
        if (customer.getId() == recommendCustomer.getId()) {
            logger.error("bindCustomerRecommendCode fail : recommendCustomer  is self  ");
            return -4;
        }
        UmsMember updateCustomer = new UmsMember();
        updateCustomer.setId(customer.getId());
        updateCustomer.setRecommended(recommendCustomer.getId());
        return customerMapper.updateCustomer(updateCustomer);
    }

    @Override
    public PageHelper<UmsMember> querySpreadCustomerList(PageHelper<UmsMember> pageHelper) {
        Map<String, Object> params = new HashMap<>();
        return pageHelper.setListDates(clearPassword(setCustomerLevelName(setCustomerPredeposit(
                queryCustomersByIds(customerMapper.querySpreadCustomerIdList(pageHelper.getQueryParams(params, customerMapper.querySpreadCustomerIdListCount(params)))))))
                .stream().peek(customer -> customer.addSubSpreadCustomerCount(querySpreadCustomerCountByCustomerId(customer.getId()))).collect(Collectors.toList()));
    }

    /**
     * 根据会员id查询分销下级会员
     *
     * @param customerId 会员id
     * @return 下级会员列表
     */
    @Override
    public List<UmsMember> querySpreadCustomerByCustomerId(long customerId) {
        logger.debug("querySpreadCustomerByCustomerId and customerId:{}", customerId);
        return clearPassword(setCustomerLevelName(setCustomerPredeposit(customerMapper.querySpreadCustomerByCustomerId(customerId))));
    }

    /**
     * 根据会员id查询分销下级会员数量
     *
     * @param customerId 会员id
     * @return 下级会员数量
     */
    @Override
    public int querySpreadCustomerCountByCustomerId(long customerId) {
        logger.debug("querySpreadCustomerCountByCustomerId and customerId:{}", customerId);
        return customerMapper.querySpreadCustomerCountByCustomerId(customerId);
    }

    /**
     * 清除用户密码
     *
     * @param customers 用户信息
     * @return 返回用户信息
     */
    private List<UmsMember> clearPassword(List<UmsMember> customers) {
        customers.parallelStream().forEach(customer -> customer.clearPassword());
        return customers;
    }

    /**
     * 设置会员等级
     *
     * @param customers 会员信息
     * @return 返回会员信息
     */
    private List<UmsMember> setCustomerLevelName(List<UmsMember> customers) {
        customers.parallelStream().forEach(customer -> customer.setCustomerLevel(customerLevelService.queryCustomerLevelByMoney(customer.getConsumptionAmount())));
        return customers;
    }

    /**
     * 设置会员的预存款总金额
     *
     * @param customers 会员信息
     * @return 返回会员的信息
     */
    private List<UmsMember> setCustomerPredeposit(List<UmsMember> customers) {
        customers.parallelStream().forEach(customer -> customer.setAllPredeposit(predepositRecordService.queryCutomerAllPredeposit(customer.getId())));
        return customers;
    }

    /**
     * 查询会员
     *
     * @param id 会员ID
     * @return 会员
     */
    @Override
    public UmsMember selectUmsMemberById(Long id) {
        return umsMemberMapper.selectUmsMemberById(id);
    }

    @Override
    public UmsMember  queryCustomerByh5OpenId(String openid){
        return umsMemberMapper.queryCustomerByh5OpenId(openid);
    }

    @Override
    public UmsMember  queryCustomerByappOpenId(String openid){
        return umsMemberMapper.queryCustomerByappOpenId(openid);
    }

    @Override
    public  UmsMember  queryCustomerByappletOpenId(String openid){
        return umsMemberMapper.queryCustomerByappletOpenId(openid);
    }
    /**
     * 查询会员列表
     *
     * @param umsMember 会员
     * @return 会员
     */
    @Override
    public List<UmsMember> selectUmsMemberList(UmsMember umsMember) {
        return umsMemberMapper.selectUmsMemberList(umsMember);
    }

    /**
     * 新增会员
     *
     * @param umsMember 会员
     * @return 结果
     */
    @Override
    public int insertUmsMember(UmsMember umsMember) {
        //设置自己的推荐码
        umsMember.addSelfRecommondCode();

        // 如果有推荐人 则查询推荐人信息
        if (umsMember.hasRecommonded()) {
            // 推荐人
            UmsMember recommonded = umsMemberMapper.queryCustomerByRecommondCode(umsMember.getRecommondCode());

            // 如果推荐人不存在  则返回错误
            if (Objects.isNull(recommonded)) {
                log.error("addCustomer fail due to recommoned is not exist...and code:{}", umsMember.getRecommondCode());
                return -10;
            }
            // 设置推荐人的会员id
            umsMember.setRecommended(recommonded.getId());

            // 设置会员的二级推荐人/ 推荐人的推荐人
            umsMember.setSRecommended(recommonded.getRecommended());
        }
        umsMember.setPassword(SecurityUtils.encryptPassword(umsMember.getPassword()));

        umsMember.setCreateTime(DateUtils.getNowDate());
        return umsMemberMapper.insertUmsMember(umsMember);
    }

    /**
     * 修改会员
     *
     * @param umsMember 会员
     * @return 结果
     */
    @Override
    public int updateUmsMember(UmsMember umsMember) {
        return umsMemberMapper.updateUmsMember(umsMember);
    }

    /**
     * 批量删除会员
     *
     * @param ids 需要删除的会员ID
     * @return 结果
     */
    @Override
    public int deleteUmsMemberByIds(Long[] ids) {
        return umsMemberMapper.deleteUmsMemberByIds(ids);
    }

    /**
     * 删除会员信息
     *
     * @param id 会员ID
     * @return 结果
     */
    @Override
    public int deleteUmsMemberById(Long id) {
        return umsMemberMapper.deleteUmsMemberById(id);
    }
}
