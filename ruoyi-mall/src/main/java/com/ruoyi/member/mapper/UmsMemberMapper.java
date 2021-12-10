package com.ruoyi.member.mapper;

import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.vo.StoreStaff;
import com.ruoyi.store.vo.NewCustomerStatistics;

import java.util.List;
import java.util.Map;

/**
 * 会员Mapper接口
 *
 * @author 商城
 */
public interface UmsMemberMapper {
    /**
     * 查询会员总数
     *
     * @param params 查询参数
     * @return 返回会员总数
     */

    int queryCustomerCount(Map<String, Object> params);

    /**
     * 查询会员数据
     *
     * @param params 查询参数
     * @return 返回会员数据
     */

    List<UmsMember> queryCustomers(Map<String, Object> params);

    UmsMember  queryCustomerByh5OpenId(String openid);
    UmsMember  queryCustomerByappOpenId(String openid);
    UmsMember  queryCustomerByappletOpenId(String openid);
    /**
     * 根据会员id查询会员信息
     *
     * @param id 会员id
     * @return 返回会员信息
     */

    UmsMember queryCustomerById(long id);

    /**
     * 删除会员信息
     *
     * @param ids 会员集合
     * @return 成功返回>1 失败返回0
     */

    int deleteCustomers(List<Long> ids);

    /**
     * 编辑员工-更新员工status
     *
     * @param storeStaff 员工实体类
     * @return 编辑返回码
     */

    int updateStatus(StoreStaff storeStaff);


    /**
     * 修改会员信息
     *
     * @param customer 会员信息
     * @return 成功返回 1 失败返回0
     */

    int updateCustomer(UmsMember customer);

    /**
     * 更新最后登录时间
     *
     * @param customerId 会员id
     * @return 更新返回码
     */

    int updateLoginTime(long customerId);

    /**
     * 查询店铺员工总条数
     *
     * @param map 查询条件
     * @return 店铺员工总条数
     */

    int queryStoreStallCount(Map<String, Object> map);

    /**
     * 分页查询店铺员工
     *
     * @param map 查询条件
     * @return 店铺员工
     */

    List<StoreStaff> queryStoreStall(Map<String, Object> map);

    /**
     * 添加会员,store添加员工
     *
     * @param storeStaff 会员信息
     * @return 返回添加主键id
     */

    int addStoreStaff(StoreStaff storeStaff);

    /**
     * 更新密码
     *
     * @param customer 会员实体类
     * @return 更新返回码
     */

    int updatePassWord(UmsMember customer);

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
     * @param map 更新参数
     * @return 更新返回码
     */

    int updatePersonalInfo(Map<String, Object> map);

    /**
     * 根据用户名查询用户信息(用户名,手机,邮箱)
     *
     * @param params 查询参数
     * @return 返回用户信息
     */

    UmsMember queryCustomerByName(Map<String, Object> params);


    /**
     * 根据用户名查询用户信息(用户名,手机,邮箱) 在写库中查询，有硬性需求时才可以调用！
     *
     * @param params 查询参数
     * @return 返回用户信息
     */

    UmsMember queryCustomerByNameInWriteDataSource(Map<String, Object> params);

    /**
     * 解锁用户
     *
     * @param customerId 会员id
     * @return 成功返回1 失败返回0
     */

    int unlockUser(long customerId);

    /**
     * 增加登录错误次数
     *
     * @param customerId 会员id
     * @return 成功返回1 失败返回0
     */

    int updateLoginErrorCount(long customerId);

    /**
     * 锁定用户
     *
     * @param customerId 用户id
     * @return 成功返回1 失败返回0
     */

    int lockUser(long customerId);

    /**
     * 根据手机号码查询总数
     *
     * @param mobile 手机号码
     * @return 返回手机号码对应的用户总数
     */

    int queryByMobile(String mobile);

    /**
     * 根据邮箱查询总数
     *
     * @param email 邮箱
     * @return 返回邮箱对应的用户总数
     */

    int queryByEmail(String email);

    /**
     * 修改用户密码
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int updatePassword(Map<String, Object> params);

    /**
     * 修改用户支付密码
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int updatePayPassword(Map<String, Object> params);

    /**
     * 重新绑定手机号码
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int bindNewMobile(Map<String, Object> params);

    /**
     * 根据手机号码修改密码
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int updatePasswordByMobile(Map<String, Object> params);

    /**
     * 修改用户总的消费金额
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int updateCustomerConsumptionAmount(Map<String, Object> params);

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
     * 更新需更改的邮箱信息
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int updateModifiedEmailInfo(Map<String, Object> params);

    /**
     * 更新邮箱信息
     *
     * @param customerId 用户id
     * @return 成功返回1 失败返回0
     */

    int modifiedEmail(long customerId);

    /**
     * 查找需更新的邮箱信息
     *
     * @param params 参数
     * @return 用户实体
     */

    UmsMember selectModifiedEmailInfo(Map<String, Object> params);

    /**
     * 更新签到次数
     *
     * @param params 参数
     * @return 1:成功，否则失败
     */

    int updateSignNum(Map<String, Object> params);

    /**
     * 统计新增用户数量（按日期分组）
     *
     * @param params 参数
     * @return 返回按日期分组的新增用户数量
     */

    List<NewCustomerStatistics> queryNewCustomerStatistics(Map<String, Object> params);

    /**
     * 统计新增用户数量（按日期分组）总组数
     *
     * @param params 参数
     * @return 返回按日期分组的新增用户数量总组数
     */

    int queryNewCustomerStatisticsCount(Map<String, Object> params);

    /**
     * 分页统计新增用户数量（按日期分组）
     *
     * @param params 参数
     * @return 返回分页查询按日期分组的新增用户数量
     */

    List<NewCustomerStatistics> queryNewCustomerStatisticsWithPage(Map<String, Object> params);


    /**
     * 更新用户佣金
     *
     * @param params 参数
     * @return 成功>0
     */

    int updateCustomerCommission(Map<String, Object> params);

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
     * 查询所有有分销下级的会员id集合
     *
     * @return 会员id集合
     */

    List<Long> querySpreadCustomerIdList(Map<String, Object> params);

    /**
     * 查询所有有分销下级的会员数量
     *
     * @return 会员数量
     */

    int querySpreadCustomerIdListCount(Map<String, Object> params);

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

    /**
     * 查询会员
     *
     * @param id 会员ID
     * @return 会员
     */
    public UmsMember selectUmsMemberById(Long id);

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
     * 删除会员
     *
     * @param id 会员ID
     * @return 结果
     */
    public int deleteUmsMemberById(Long id);

    /**
     * 批量删除会员
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUmsMemberByIds(Long[] ids);

    UmsMember queryCustomerByRecommondCode(String code);
}
