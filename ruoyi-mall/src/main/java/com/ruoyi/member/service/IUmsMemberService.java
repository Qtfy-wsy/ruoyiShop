package com.ruoyi.member.service;

import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.vo.CustomerSearchCondition;
import com.ruoyi.member.vo.StoreStaff;
import com.ruoyi.store.vo.NewCustomerStatistics;
import com.ruoyi.util.PageHelper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
public interface IUmsMemberService {
    /**
     * 查询会员
     *
     * @param id 会员ID
     * @return 会员
     */
    public UmsMember selectUmsMemberById(Long id);

    public UmsMember  queryCustomerByh5OpenId(String openid);
    public UmsMember  queryCustomerByappOpenId(String openid);
    public UmsMember  queryCustomerByappletOpenId(String openid);
    /**
     * 查询会员列表
     *
     * @param umsMember 会员
     * @return 会员集合
     */
    public List<UmsMember> selectUmsMemberList(UmsMember umsMember);

    /**
     * 新增会员
     *
     * @param umsMember 会员
     * @return 结果
     */
    public int insertUmsMember(UmsMember umsMember);

    /**
     * 修改会员
     *
     * @param umsMember 会员
     * @return 结果
     */
    public int updateUmsMember(UmsMember umsMember);

    /**
     * 批量删除会员
     *
     * @param ids 需要删除的会员ID
     * @return 结果
     */
    public int deleteUmsMemberByIds(Long[] ids);

    /**
     * 删除会员信息
     *
     * @param id 会员ID
     * @return 结果
     */
    public int deleteUmsMemberById(Long id);

    /**
     * 分页查询会员信息
     *
     * @param pageHelper              分页帮助类
     * @param customerSearchCondition 搜索条件
     * @return 返回会员信息
     */
    PageHelper<UmsMember> queryCustomers(PageHelper<UmsMember> pageHelper, CustomerSearchCondition customerSearchCondition);

    /**
     * 根据会员id查询会员信息(没有密码信息)
     *
     * @param id 会员id
     * @return 返回会员信息
     */
    UmsMember queryCustomerWithNoPasswordById(long id);


    /**
     * 根据会员id查询会员信息(包含密码信息)
     *
     * @param id 会员id
     * @return 返回会员信息
     */
    UmsMember queryCustomerInfoById(long id);


    /**
     * 查询会员信息和店铺联系方式
     *
     * @param id 会员id
     * @return 返回会员信息
     */
    UmsMember queryCustomerWithStoreQQ(long id);


    /**
     * 根据店铺id查询会员信息和会员等级
     *
     * @param id 会员id
     * @return 会员信息
     */
    UmsMember queryCustomerWithCustomerLevel(long id);

    /**
     * 删除会员信息
     *
     * @param ids 会员id集合
     * @return 成功>1  失败返回0
     */
    int deleteCustomers(List<Long> ids);

    /**
     * 新增会员信息
     *
     * @param customer 会员
     * @return 成功返回1 失败返回0
     */
    int addCustomer(UmsMember customer);

    /**
     * 自动添加用户 用于免密登录场景
     *
     * @param customer 用户对象
     * @return 主键id
     */
    Long autoAddCustomer(UmsMember customer);

    /**
     * 修改会员信息
     *
     * @param customer 会员信息
     * @return 成功返回1 失败返回0 -2 已经绑定过推荐码 -3 推荐人不存在 -4 推荐人不能是自己
     */
    int updateCustomer(UmsMember customer);

    /**
     * 更新会员密码
     *
     * @param customer      会员实体类
     * @param oldPassword   原始密码
     * @param newPassword   新密码
     * @param reNewPassword 重新输入
     * @return 0 未登录 -1 输入为空 1 成功过 2 两次输入不一致 3 原密码不正确
     */
    int updateCustomerPassWord(UmsMember customer, String oldPassword, String newPassword, String reNewPassword);

    /**
     * 分页查询会员信息
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @return 返回会员信息
     */
    PageHelper<StoreStaff> queryStoreStall(PageHelper<StoreStaff> pageHelper, long storeId, String name);

    /**
     * 添加店铺员工
     *
     * @param storeStaff 员工实体类
     * @param storeId    店铺id
     * @return 添加返回值 主键id -1 大于50个 -2 含有中文 -3 用户名已存在
     */
    int addStoreStaff(StoreStaff storeStaff, long storeId);

    /**
     * 删除会员和关联的角色
     *
     * @param customerIds 会员id
     * @return 删除返回码
     */
    int delCustomersAndStaffs(Long[] customerIds);

    /**
     * 编辑会员和关联的角色
     *
     * @param storeStaff 员工实体类
     * @return 删除返回码
     */
    int editCustomerAndStaff(StoreStaff storeStaff);

    /**
     * 更新会员storeId和type
     *
     * @param customer 会员实体类
     * @return 添加返回码
     */
    int updateStoreIdAndType(UmsMember customer);

    /**
     * 根据店铺id查询会员id
     *
     * @param storeId 店铺id
     * @return 会员信息
     */
    UmsMember queryCustomerIdByStoreId(long storeId);

    /**
     * 更新会员个人信息
     *
     * @param customer 会员实体类
     * @param param
     * @return 成功返回1 失败返回0
     */
    int updatePersonalInfo(UmsMember customer, int param);

    /**
     * 根据用户名称查询用户信息
     *
     * @param userName 用户名称(可能是用户名 , 邮箱或者手机号码)
     * @return 返回用户信息
     */
    UmsMember queryCustomerByName(String userName);

    /**
     * 根据用户名称查询用户信息(在写库中查询，有硬性需求时才可以调用！)
     *
     * @param userName 用户名称(可能是用户名 , 邮箱或者手机号码)
     * @return 返回用户信息
     */
    UmsMember queryCustomerByNameInWriteDataSource(String userName);

    /**
     * 解锁用户
     *
     * @param customerId 用户id
     * @return 成功返回1  失败返回0
     */
    int unlockUser(long customerId);

    /**
     * 增加登录错误次数
     *
     * @param customerId 用户id
     * @return 成功返回1  失败返回0
     */
    int updateLoginErrorCount(long customerId);

    /**
     * 锁定用户
     *
     * @param customerId 会员id
     * @return 成功返回1 失败返回0
     */
    int lockUser(long customerId);

    /**
     * 更新登录时间
     *
     * @param customerId 会员id
     * @return 成功返回1  失败返回0
     */
    int updateLoginTime(long customerId);

    /**
     * 校验手机号码是否存在
     *
     * @param mobile 手机号码
     * @return 存在返回>0  不存在返回0
     */
    int isMobileExist(String mobile);

    /**
     * 校验邮箱是否存在
     *
     * @param email 邮箱
     * @return 存在返回>0  不存在返回0
     */
    int isEmailExist(String email);


    /**
     * 修改用户密码
     *
     * @param customerId 用户id
     * @param password   密码
     * @return 成功返回1 失败返回0
     */
    int updatePassword(long customerId, String password);

    /**
     * 修改用户支付密码
     *
     * @param customerId  用户id
     * @param payPassword 支付密码
     * @return 成功返回1 失败返回0
     */
    int updatePayPassword(long customerId, String payPassword);

    /**
     * 用户绑定新的手机号码
     *
     * @param customerId 用户id
     * @param mobile     手机号码
     * @return 成功返回1  失败返回0
     */
    int bindNewMobile(long customerId, String mobile);


    /**
     * 修改用户密码
     *
     * @param mobile   用户手机号码
     * @param password 密码
     * @return 成功返回1 失败返回0
     */
    int updatePasswordByMobile(String mobile, String password);

    /**
     * 修改用户总的消费金额
     *
     * @param customerId 会员id
     * @param orderMoney 订单金额
     * @return 成功返回1 失败返回0
     */
    int updateCustomerConsumptionAmount(long customerId, BigDecimal orderMoney);

    /**
     * 修改用户总的消费金额(admin端用)
     *
     * @param customerId 会员id
     * @param money      更改金额
     * @return 成功返回1 失败返回0 -1:更改后金额小于0
     */
    int updateCustomerConsumptionAmountByAdmin(long customerId, BigDecimal money);

    /**
     * 更新绑定新邮箱信息
     *
     * @param checkCode  校验码
     * @param email      邮箱
     * @param customerId 用户id
     * @return 1:成功
     */
    int updateModifiedEmailInfo(String checkCode, String email, long customerId);

    /**
     * 绑定新邮箱
     *
     * @param customerId 用户id
     * @return 1:成功
     */
    int modifiedEmail(long customerId);

    /**
     * 查找绑定新邮箱信息
     *
     * @param checkCode  校验码
     * @param customerId 用户id
     * @return 用户实体
     */
    UmsMember selectModifiedEmailInfo(String checkCode, long customerId);

    /**
     * 更新签到天数
     *
     * @param customerId   用户id
     * @param continueFlag 连续签到标记 true表示连续签到，否则为断签
     * @return 1:成功
     */
    int updateSignNum(long customerId, boolean continueFlag);

    /**
     * 修改推荐人的佣金
     *
     * @param customerId 推荐人会员id
     * @param commission 佣金
     * @return 成功返回>0
     */
    int updateCustomerCommission(long customerId, BigDecimal commission);


    /**
     * 查找用户信息
     *
     * @param ids 用户id集合
     * @return 用户信息集合
     */
    List<UmsMember> queryCustomersByIds(List<Long> ids);

    /**
     * 查找所有用户信息
     */
    List<UmsMember> queryAllCustomer();

    /**
     * 查询今日新增会员数
     *
     * @return 返回今日新增会员数
     */
    int queryNewCustomerToday();

    /**
     * 查询本周新增会员数
     *
     * @return 返回本周新增会员数
     */
    int queryNewCustomerThisWeek();

    /**
     * 查询本周新增会员数（按日期分组）
     *
     * @return 返回本周新增会员数
     */
    List<NewCustomerStatistics> queryNewCustomerThisWeekGroupByDay();

    /**
     * 查找所有没有开店的用户手机号
     *
     * @return 没有开店用户手机号集合
     */
    List<String> queryAllCustomerMobileForCreateStore();


    /**
     * 绑定用户推荐码
     *
     * @param recommendCode 推荐码
     * @param customerId    用户id
     * @return 1成功 -1 用户不存在 -2 已经绑定过推荐码 -3 推荐人不存在 -4 推荐人不能是自己
     */
    int bindCustomerRecommendCode(String recommendCode, long customerId);

    /**
     * 分页查询有下级的会员信息
     *
     * @param pageHelper 分页帮助类
     * @return 会员列表
     */
    PageHelper<UmsMember> querySpreadCustomerList(PageHelper<UmsMember> pageHelper);

    /**
     * 根据会员id查询分销下级会员
     *
     * @param customerId 会员id
     * @return 下级会员列表
     */
    List<UmsMember> querySpreadCustomerByCustomerId(long customerId);

    /**
     * 根据会员id查询分销下级会员数量
     *
     * @param customerId 会员id
     * @return 下级会员数量
     */
    int querySpreadCustomerCountByCustomerId(long customerId);
}
