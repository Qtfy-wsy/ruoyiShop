package com.ruoyi.member.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.domain.UmsPreDepositRecord;
import com.ruoyi.member.mapper.UmsPreDepositRecordMapper;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.IUmsPreDepositRecordService;
import com.ruoyi.member.service.SmsService;
import com.ruoyi.member.vo.QueryCriteria;
import com.ruoyi.member.vo.UpdatePayPwdBean;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 会员预存款记录Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
@Service
public class UmsPreDepositRecordServiceImpl implements IUmsPreDepositRecordService {
    @Autowired
    private UmsPreDepositRecordMapper umsPreDepositRecordMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(UmsPreDepositRecordServiceImpl.class);

    /**
     * 注入预存款数据库接口
     */
    @Autowired
    private UmsPreDepositRecordMapper predepositRecordMapper;

    /**
     * 注入用户服务接口
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 注入短信服务接口
     */
    @Autowired
    private SmsService smsService;

    @Override
    public PageHelper<UmsPreDepositRecord> queryPredepositRecords(PageHelper<UmsPreDepositRecord> pageHelper, QueryCriteria queryCriteria) {
        logger.debug("queryPredepositRecords and pageHelper :{} \r\n queryCriteria:{}", pageHelper, queryCriteria);

        Map<String, Object> params = queryCriteria.getQueryMap();

        return pageHelper.setListDates(predepositRecordMapper.queryPredepositRecords(pageHelper.getQueryParams(params, predepositRecordMapper.queryPredepositRecordCount(params))));
    }

    @Override
    public BigDecimal queryCutomerAllPredeposit(long id) {

        UmsPreDepositRecord predepositRecord = predepositRecordMapper.queryLastRecordByCustomerId(id);

       // logger.debug("queryCutomerAllPredeposit and id :{} \r\n and result:{}", id, predepositRecord);

        // 如果用户还没有预存款记录 则直接返回0
        if (Objects.isNull(predepositRecord)) {
            return new BigDecimal(0);
        }

        // 返回用户的预存款总金额
        return predepositRecord.getCurrentMoney();
    }

    @Transactional
    @Override
    public int addPredepositRecord(UmsPreDepositRecord predepositRecord) {

        logger.debug("addPredepositRecord and predepositRecord:{}", predepositRecord);

        if (Objects.isNull(predepositRecord)) {
            logger.error("addPredepositRecord fail due to predepositRecord is null...");
            return 0;
        }

        // 用户预存款总金额
        BigDecimal currentMoney = this.queryCutomerAllPredeposit(predepositRecord.getCustomerId());

        // 如果是收入 则用户金额+当前金额
        if (predepositRecord.isIncome()) {
            // 设置用户的当前总金额
            predepositRecord.setCurrentMoney(currentMoney.add(predepositRecord.getMoney()));
        }

        // 如果是支出 则用户金额-当前金额
        if (predepositRecord.isExpenditure()) {
            // 设置用户的当前总金额
            predepositRecord.setCurrentMoney(currentMoney.subtract(predepositRecord.getMoney()));
        }
        if (predepositRecord.isCurrentMoneyMinus()) {
            logger.error("addPredepositRecord fail : will be minus");
            return -1;
        }

        return predepositRecordMapper.addPredepositRecord(predepositRecord);
    }

    @Override
    public int sendUpdatePayPwdSmsCode(long customerId, BiConsumer<String, String> consumer) {
        logger.debug("sendUpdatePayPwdSmsCode and customerId:{}", customerId);

        // 根据用户id查询用户信息
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);

        if (Objects.isNull(customer)) {
            logger.error("sendUpdatePayPwdSmsCode fail due to member is not exist");
            return 1;
        }


        // 生成的6位数数字
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 发送短信失败
        if (smsService.sendSms(customer.getMobile(), code) == 1) {
            logger.error("send sms fail....");
            return 1;
        }

        // 短信发送成功后回调
        if (Objects.nonNull(consumer)) {
            consumer.accept(customer.getMobile(), code);
        }

        return 0;
    }

    @Override
    public int sendUpdatePayPwdSmsCodeForPc(long customerId, String kaptcha, String oldKaptcha, BiConsumer<String, String> consumer) {
        logger.debug("sendUpdatePayPwdSmsCodeForPc and customerId:{} \r\n kaptcha:{} \r\n oldKaptcha:{}", customerId, kaptcha, oldKaptcha);

        // 根据用户id查询用户信息
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);

        if (Objects.isNull(customer)) {
            logger.error("sendUpdatePayPwdSmsCode fail due to member is not exist");
            return 1;
        }

        int checkKaptchaRes = checkKaptcha(kaptcha, oldKaptcha);
        if (checkKaptchaRes != 1) {
            logger.error("sendUpdatePayPwdSmsCode fail due to kaptcha is error...");
            return checkKaptchaRes;
        }

        // 生成的6位数数字
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 发送短信失败
        if (smsService.sendSms(customer.getMobile(), code) == 1) {
            logger.error("send sms fail....");
            return 1;
        }

        // 短信发送成功后回调
        if (Objects.nonNull(consumer)) {
            consumer.accept(customer.getMobile(), code);
        }

        return 0;
    }

    @Override
    public int updatePayPassword(UpdatePayPwdBean updatePayPwdBean) {

        if (Objects.isNull(updatePayPwdBean)) {
            logger.error("updatePayPassword fail due to updatePayPwdBean is empty...");
            return -1;
        }

        // 获取用户信息
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(updatePayPwdBean.getCustomerId());

        if (Objects.isNull(customer)) {
            logger.error("updatePayPassword fail due to member is not exist");
            return -3;
        }

        // 用户不匹配
        if (!customer.getMobile().equals(updatePayPwdBean.getMobile())) {
            logger.error("updatePayPassword due to mobile error...");
            return -3;
        }
        //判断是不是pc请求
        if (CommonConstant.FROM_PC.equals(updatePayPwdBean.getRequestFrom())) {
            if (StringUtils.isEmpty(updatePayPwdBean.getPassword())) {
                logger.error("updatePayPassword fail due to params is error....");
                return -1;
            }

            // 校验是否有凭证
            if (!updatePayPwdBean.hasCertificate()) {
                logger.error("updatePayPassword fail due to no certificate ...");
                return -2;
            }

            int checkKaptchaRes = checkKaptcha(updatePayPwdBean.getKaptcha(), updatePayPwdBean.getOldKaptcha());
            if (checkKaptchaRes != 1) {
                logger.error("updatePayPassword fail due to kaptcha is error...");
                return -4;
            }

        } else {
            if (StringUtils.isEmpty(updatePayPwdBean.getCode()) || StringUtils.isEmpty(updatePayPwdBean.getPassword())) {
                logger.error("updatePayPassword fail due to params is error....");
                return -1;
            }

            // 校验用户输入的验证码是否正确
            if (!updatePayPwdBean.validateCode()) {
                logger.error("updatePayPassword fail due to code is error...");
                return -2;
            }
        }


        return customerService.updatePayPassword(updatePayPwdBean.getCustomerId(), updatePayPwdBean.getPassword());
    }

    @Override
    public int validateCode(String code, String originCode, String mobile) {
        logger.debug("validatCode and code:{} \r\n originCode:{} \r\n :{}", code, originCode, mobile);

        // 手机号码不存在直接返回
        if (customerService.isMobileExist(mobile) == 0) {
            logger.error("validatCode fail due to mobile is not exist....");
            return -1;
        }

        if (StringUtils.isEmpty(code)) {
            logger.error("validatCode fail due to code is empty...");
            return -2;
        }
        return code.equals(originCode) ? 0 : -2;
    }

    @Override
    public int checkKaptcha(String kaptcha, String kaptchaInSession) {
        logger.debug("updatepaypwd checkKaptcha and kaptcha:{}\r\n kaptchaInSession:{}", kaptcha, kaptchaInSession);
        if (StringUtils.isEmpty(kaptchaInSession)) {
            logger.error("updatepaypwd checkKaptcha fail:kaptchaInSession is not exist ");
            return -1;
        }
        if (StringUtils.isEmpty(kaptcha)) {
            logger.error("updatepaypwd checkKaptcha fail:kaptcha is not exist ");
            return -2;
        }
        if (!kaptcha.equals(kaptchaInSession)) {
            logger.error("updatepaypwd checkKaptcha fail:kaptchaInSession is not equal to kaptcha ");
            return -3;
        }
        return 1;
    }

    @Override
    public UmsPreDepositRecord queryPredepositRecordByTransCode(String transCode, long customerId) {
        logger.debug("queryPredepositRecordByTransCode and transCode:{} \r\n customerId:{}", transCode, customerId);
        Map<String, Object> map = new HashMap();
        map.put("transCode", transCode);
        map.put("customerId", customerId);
        return predepositRecordMapper.queryPredepositRecordByTransCode(map);
    }


    @Override
    public int updateStatusSuccessByTransCode(String transCode, long customerId, String channel, Consumer<UmsPreDepositRecord> consumer) {
        logger.debug("updateStatusSuccessByTransCode and transCode:{} \r\n customerId:{}", transCode, customerId);
        Map<String, Object> map = new HashMap();
        map.put("transCode", transCode);
        map.put("customerId", customerId);
        UmsPreDepositRecord predepositRecord = predepositRecordMapper.queryPredepositRecordByTransCode(map);
        if (ObjectUtils.isEmpty(predepositRecord) || predepositRecord.isPaid()) {
            logger.error("updateStatusSuccessByTransCode fail: no record for change");
            return -1;
        }
        predepositRecord.setRemark("在线充值-" + channel);
        predepositRecord.setStatus("1");

        int result = predepositRecordMapper.updatePredepositRecordStatus(predepositRecord);

        // 更新成功回调记录交易流水
        if (result > 0) {
            consumer.accept(predepositRecord);
        }

        return result;
    }

    /**
     * 查询会员预存款记录
     *
     * @param id 会员预存款记录ID
     * @return 会员预存款记录
     */
    @Override
    public UmsPreDepositRecord selectUmsPreDepositRecordById(Long id) {
        return umsPreDepositRecordMapper.selectUmsPreDepositRecordById(id);
    }

    /**
     * 查询会员预存款记录列表
     *
     * @param umsPreDepositRecord 会员预存款记录
     * @return 会员预存款记录
     */
    @Override
    public List<UmsPreDepositRecord> selectUmsPreDepositRecordList(UmsPreDepositRecord umsPreDepositRecord) {
        return umsPreDepositRecordMapper.selectUmsPreDepositRecordList(umsPreDepositRecord);
    }

    /**
     * 新增会员预存款记录
     *
     * @param umsPreDepositRecord 会员预存款记录
     * @return 结果
     */
    @Override
    public int insertUmsPreDepositRecord(UmsPreDepositRecord umsPreDepositRecord) {
        umsPreDepositRecord.setCreateTime(DateUtils.getNowDate());
        return umsPreDepositRecordMapper.insertUmsPreDepositRecord(umsPreDepositRecord);
    }

    /**
     * 修改会员预存款记录
     *
     * @param umsPreDepositRecord 会员预存款记录
     * @return 结果
     */
    @Override
    public int updateUmsPreDepositRecord(UmsPreDepositRecord umsPreDepositRecord) {
        return umsPreDepositRecordMapper.updateUmsPreDepositRecord(umsPreDepositRecord);
    }

    /**
     * 批量删除会员预存款记录
     *
     * @param ids 需要删除的会员预存款记录ID
     * @return 结果
     */
    @Override
    public int deleteUmsPreDepositRecordByIds(Long[] ids) {
        return umsPreDepositRecordMapper.deleteUmsPreDepositRecordByIds(ids);
    }

    /**
     * 删除会员预存款记录信息
     *
     * @param id 会员预存款记录ID
     * @return 结果
     */
    @Override
    public int deleteUmsPreDepositRecordById(Long id) {
        return umsPreDepositRecordMapper.deleteUmsPreDepositRecordById(id);
    }
}
