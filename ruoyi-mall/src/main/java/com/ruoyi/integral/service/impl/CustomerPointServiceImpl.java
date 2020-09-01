package com.ruoyi.integral.service.impl;


import com.ruoyi.integral.domain.CustomerPoint;
import com.ruoyi.integral.domain.PointSignRule;
import com.ruoyi.integral.mapper.CustomerPointMapper;
import com.ruoyi.integral.service.CustomerPointService;
import com.ruoyi.integral.service.PointSignRuleService;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by 魔金商城 on 17/5/25.
 * 会员积分服务接口实现
 */
@Service
public class CustomerPointServiceImpl implements CustomerPointService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(CustomerPointServiceImpl.class);

    /**
     * 注入会员积分数据库接口
     */
    @Autowired
    private CustomerPointMapper customerPointMapper;

    /**
     * 注入会员服务
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 注入签到积分规则服务
     */
    @Autowired
    private PointSignRuleService pointSignRuleService;

    @Override
    public PageHelper<CustomerPoint> queryCustomerPoints(PageHelper<CustomerPoint> pageHelper, long customerId, String sourceType,String type) {
        logger.debug("queryCustomerPoints and pageHelper:{} \r\n customerId:{}", pageHelper, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("sourceType", sourceType);
        params.put("type", type);
        return pageHelper.setListDates(customerPointMapper.queryCustomerPoints(pageHelper.getQueryParams(params, customerPointMapper.queryCustomerPointCount(params))));
    }

    @Override
    public int queryCustomerPointCount(long customerId) {
        logger.debug("queryCustomerPointCount and customerId:{}", customerId);
        Integer point = customerPointMapper.queryCustomerPointAllCount(customerId);
        return Objects.isNull(point) ? 0 : point;
    }

    @Override
    public List<CustomerPoint> queryCustomerPointGroupByType(long customerId){
        return customerPointMapper.queryCustomerPointGroupByType(customerId);
    }
    @Override
    public int addCustomerPoint(CustomerPoint customerPoint) {
        logger.debug("addCustomerPoint and customerPoint:{}", customerPoint);

        if (Objects.isNull(customerPoint)) {
            logger.error("addCustomerPoint fail due to params is null...");
            return 0;
        }
        Integer point = customerPointMapper.queryCustomerPointAllCount(customerPoint.getCustomerId());
        if (!customerPoint.checkChangePoint(point == null ? 0 : point)) {
            logger.error("addCustomerPoint fail:will be minus");
            return -1;
        }
        return customerPointMapper.addCustomerPoint(customerPoint);
    }

    @Override
    public int isTodaySign(long customerId) {
        logger.debug("isTodaySign and customerId:{}", customerId);
        CustomerPoint customerPoint = customerPointMapper.queryLastSign(customerId);
        if (ObjectUtils.isEmpty(customerPoint)) {
            logger.info("isTodaySign : not sign");
            return 1;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (Integer.parseInt(customerPoint.getCreateTime().format(dateTimeFormatter)) >= Integer.parseInt(LocalDateTime.now().format(dateTimeFormatter))) {
            logger.error("isTodaySign : already sign");
            return -1;
        }
        return 1;
    }


    @Override
    @Transactional
    public int addSignRecord(long customerId, Consumer<Integer> consumer) {
        logger.debug("addSignRecord and customerId:{}", customerId);
        CustomerPoint lastSign = customerPointMapper.queryLastSign(customerId);
        PointSignRule pointSignRule = pointSignRuleService.queryPointSignRule();
        if (ObjectUtils.isEmpty(pointSignRule) || !pointSignRule.isOpen()) {
            logger.error("addSignRecord fail:pointSignRule is empty or not used");
            return -1;
        }
        //没有签到记录，开始签到
        if (ObjectUtils.isEmpty(lastSign)) {
            logger.info("addSignRecord:no signRecord");
            return signFirstDay(pointSignRule.getRuleInfos(), customerId, consumer);
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //最后一次签到日期大于等于现在，标识已经签到过
        if (Integer.parseInt(lastSign.getCreateTime().format(dateTimeFormatter)) >= Integer.parseInt(LocalDateTime.now().format(dateTimeFormatter))) {
            logger.error("addSignRecord : already sign");
            return -2;
        }
        //最后一次签到日期是昨天，连续签到
        if (Integer.parseInt(lastSign.getCreateTime().format(dateTimeFormatter)) + 1 == Integer.parseInt(LocalDateTime.now().format(dateTimeFormatter))) {
            UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);
            return signContinue(pointSignRule.getRuleInfos(), customerId, customer.getSignNum(), consumer);
        } else {
            //断签，重新开始签到
            logger.info("addSignRecord: not continue");
            return signFirstDay(pointSignRule.getRuleInfos(), customerId, consumer);
        }

    }

    /**
     * 开始签到
     *
     * @param ruleInfos  规则详细
     * @param customerId 用户id
     * @return 1:成功
     */
    private int signFirstDay(List<PointSignRule.RuleInfo> ruleInfos, long customerId, Consumer<Integer> consumer) {
        if (!CollectionUtils.isEmpty(ruleInfos)) {
            customerService.updateSignNum(customerId, false);
            PointSignRule.RuleInfo fistDayRule = ruleInfos.stream().filter(ruleInfo -> ruleInfo.filterDay(1)).findFirst().orElse(null);
            return addSignByRule(fistDayRule, customerId, consumer);
        }
        return -1;
    }

    /**
     * 连续签到
     *
     * @param ruleInfos      规则详细
     * @param customerId     用户id
     * @param alreadySignNum 已经签到的天数
     * @return 1:成功
     */
    private int signContinue(List<PointSignRule.RuleInfo> ruleInfos, long customerId, int alreadySignNum, Consumer<Integer> consumer) {
        if (!CollectionUtils.isEmpty(ruleInfos)) {
            PointSignRule.RuleInfo lastDayRule = ruleInfos.stream().sorted(Comparator.reverseOrder()).findFirst().get();
            //如果已经签到的天数大于等于规则最大天数，则重新开始签到
            if (lastDayRule.getDay() <= alreadySignNum) {
                return signFirstDay(ruleInfos, customerId, consumer);
            } else {
                //连续签到
                customerService.updateSignNum(customerId, true);
                PointSignRule.RuleInfo continueDayRule = ruleInfos.stream().filter(ruleInfo -> ruleInfo.filterDay(alreadySignNum + 1)).findFirst().orElse(null);
                return addSignByRule(continueDayRule, customerId, consumer);
            }
        }
        return -1;
    }

    /**
     * 按规则增加积分记录
     *
     * @param ruleInfo   规则
     * @param customerId 用户id
     * @param consumer   回调
     * @return 1:成功
     */
    private int addSignByRule(PointSignRule.RuleInfo ruleInfo, long customerId, Consumer<Integer> consumer) {
        if (!ObjectUtils.isEmpty(ruleInfo)) {
            consumer.accept(ruleInfo.getPoint());
            return customerPointMapper.addCustomerPoint(CustomerPoint.buildForSignIn(customerId, ruleInfo.getPoint()));
        } else {
            consumer.accept(0);
            return customerPointMapper.addCustomerPoint(CustomerPoint.buildForSignIn(customerId, 0));
        }
    }
}


