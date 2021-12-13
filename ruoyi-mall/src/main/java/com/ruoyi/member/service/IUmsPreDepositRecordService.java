package com.ruoyi.member.service;

import com.ruoyi.member.domain.UmsPreDepositRecord;
import com.ruoyi.member.vo.QueryCriteria;
import com.ruoyi.member.vo.UpdatePayPwdBean;
import com.ruoyi.util.PageHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 会员预存款记录Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
public interface IUmsPreDepositRecordService {
    /**
     * 分页查询预存款记录
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回预存款记录
     */
    PageHelper<UmsPreDepositRecord> queryPredepositRecords(PageHelper<UmsPreDepositRecord> pageHelper, QueryCriteria queryCriteria);

    /**
     * 查询会员的预存款总金额
     *
     * @param id 会员id
     * @return 返回会员的预存款总金额
     */
    BigDecimal queryCutomerAllPredeposit(long id);

    /**
     * 新增预存款纪录
     *
     * @param predepositRecord 预存款纪录
     * @return 成功>0 失败= 0
     */
    int addPredepositRecord(UmsPreDepositRecord predepositRecord);


    /**
     * 发送修改支付密码短验证码
     *
     * @param customerId 用户id
     * @param consumer   回调接口
     * @return 成功返回0 失败返回1
     */
    int sendUpdatePayPwdSmsCode(long customerId, BiConsumer<String, String> consumer);

    /**
     * 发送修改支付密码短验证码（PC端用，带有图片验证码）
     *
     * @param customerId 用户id
     * @param kaptcha    用户输入的图片验证码
     * @param oldKaptcha redis中的图片验证码
     * @param consumer   回调接口
     * @return 成功返回0 失败返回1 -1 图片验证码失效或不存在 -2 图片验证码为空 -3 图片证码不正确
     */
    int sendUpdatePayPwdSmsCodeForPc(long customerId, String kaptcha, String oldKaptcha, BiConsumer<String, String> consumer);

    /**
     * 修改用户支付密码
     *
     * @param updatePayPwdBean 修改支付密码实体
     * @return -1 参数错误  -2 验证码错误 -3 用户不匹配 -4 图片验证码错误 0 失败 1 成功
     */
    int updatePayPassword(UpdatePayPwdBean updatePayPwdBean);

    /**
     * 校验验证码
     *
     * @param code       用户输入的验证码
     * @param originCode 原始的验证码
     * @param mobile     手机号码
     * @return 0 成功  -1 手机号码不存在  -2 验证码错误
     */
    int validateCode(String code, String originCode, String mobile);

    /**
     * 校验验证码
     *
     * @param kaptcha          验证码
     * @param kaptchaInSession session中的验证码
     * @return -1 验证码失效或不存在 -2 验证码为空 -3验证码不正确  1 成功
     */
    int checkKaptcha(String kaptcha, String kaptchaInSession);

    /**
     * 根据交易码查询预存款记录
     *
     * @param transCode  交易码
     * @param customerId 用户id
     */
    UmsPreDepositRecord queryPredepositRecordByTransCode(String transCode, long customerId);

    /**
     * 根据会员id和交易号更新支付成功状态
     *
     * @param transCode  交易号
     * @param customerId 会员id
     * @return 1:成功
     */
    int updateStatusSuccessByTransCode(String transCode, long customerId, String channel, Consumer<UmsPreDepositRecord> consumer);

    /**
     * 查询会员预存款记录
     *
     * @param id 会员预存款记录ID
     * @return 会员预存款记录
     */
    public UmsPreDepositRecord selectUmsPreDepositRecordById(Long id);

    /**
     * 查询会员预存款记录列表
     *
     * @param umsPreDepositRecord 会员预存款记录
     * @return 会员预存款记录集合
     */
    public List<UmsPreDepositRecord> selectUmsPreDepositRecordList(UmsPreDepositRecord umsPreDepositRecord);

    /**
     * 新增会员预存款记录
     *
     * @param umsPreDepositRecord 会员预存款记录
     * @return 结果
     */
    public int insertUmsPreDepositRecord(UmsPreDepositRecord umsPreDepositRecord);

    /**
     * 修改会员预存款记录
     *
     * @param umsPreDepositRecord 会员预存款记录
     * @return 结果
     */
    public int updateUmsPreDepositRecord(UmsPreDepositRecord umsPreDepositRecord);

    /**
     * 批量删除会员预存款记录
     *
     * @param ids 需要删除的会员预存款记录ID
     * @return 结果
     */
    public int deleteUmsPreDepositRecordByIds(Long[] ids);

    /**
     * 删除会员预存款记录信息
     *
     * @param id 会员预存款记录ID
     * @return 结果
     */
    public int deleteUmsPreDepositRecordById(Long id);
}
