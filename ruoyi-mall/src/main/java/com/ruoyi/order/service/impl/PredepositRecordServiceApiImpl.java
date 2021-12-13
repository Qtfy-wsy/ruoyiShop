package com.ruoyi.order.service.impl;


import com.ruoyi.member.service.IUmsPreDepositRecordService;
import com.ruoyi.order.domain.OmsTransRecords;
import com.ruoyi.order.service.IOmsTransRecordsService;
import com.ruoyi.order.service.PredepositRecordServiceApi;
import com.ruoyi.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by 伊甸园商城 on 2020/4/8.
 * 预存款服务api接口实现
 */
@Service
@Slf4j
public class PredepositRecordServiceApiImpl implements PredepositRecordServiceApi {

    /**
     * 注入预存款服务接口
     */
    @Autowired
    private IUmsPreDepositRecordService predepositRecordService;

    /**
     * 注入交易流水服务接口
     */
    @Autowired
    private IOmsTransRecordsService transRecordsService;


    @Transactional
    @Override
    public int updateStatusSuccessByTransCode(String transCode, long customerId, String channel, String outTransCode) {
        log.debug("updateStatusSuccessByTransCode and transCode:{} \r\n customerId:{} \r\n channel:{} \r\n outTransCode:{}", transCode, customerId, channel, outTransCode);

        return predepositRecordService.updateStatusSuccessByTransCode(transCode, customerId, channel, predepositRecord -> transRecordsService.insertOmsTransRecords(OmsTransRecords.builder().money(predepositRecord.getMoney()).payTime(new Date()).lsTransCode(transCode).transCode(outTransCode).channel(channel).type(CommonConstant.PREALSE_ORDER).build()));
    }
}
