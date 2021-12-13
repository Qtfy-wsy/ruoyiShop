package com.ruoyi.order.service.impl;


import com.ruoyi.order.domain.CustomerPanicRecord;
import com.ruoyi.order.domain.OmsOrderSku;
import com.ruoyi.order.mapper.CustomerPanicRecordMapper;
import com.ruoyi.order.service.CustomerPanicRecordService;
import com.ruoyi.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 伊甸园商城 on 17/12/27.
 * 会员抢购服务接口实现类
 */
@Service
public class CustomerPanicRecordServiceImpl implements CustomerPanicRecordService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(CustomerPanicRecordServiceImpl.class);

    /**
     * 注入会员抢购纪录数据库接口
     */
    @Autowired
    private CustomerPanicRecordMapper customerPanicRecordMapper;

    @Override
    public int queryCanUseNum(long customerId, long marketingId) {
        logger.debug("queryCanUseNum and customerId:{} \r\n marketingId:{} \r\n", customerId, marketingId);

        // 数据库查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("marketingId", marketingId);

        // 用户抢购纪录
        CustomerPanicRecord customerPanicRecord = customerPanicRecordMapper.queryByCustomerIdAndMarktingId(params);
        return Objects.isNull(customerPanicRecord) ? CommonConstant.NO_USE_PANIC : customerPanicRecord.getCanUseNum();
    }

    @Override
    public int updateCustomerPanicRecord(OmsOrderSku orderSku) {

        logger.debug("updateCustomerPanicRecord and orderSku:{}", orderSku);

        if (Objects.isNull(orderSku)) {
            logger.error("updateCustomerPanicRecord fail due to orderSku is empty...");
            return 0;
        }

        // 说明用户还没有使用过抢购则新增  否则更新
        if (this.queryCanUseNum(orderSku.getCustomerId(), orderSku.getPanicMarketingId()) == CommonConstant.NO_USE_PANIC) {

            // 判断用户购买的数量是否超过了抢购限制的数量
            if (orderSku.getNum() > orderSku.getPanicLimitNum()) {
                logger.error("oooo what? panic num is limit");
                return 0;
            }

            logger.info("user has not use panic");
            try {
                return customerPanicRecordMapper.addCustomerPanicRecord(CustomerPanicRecord.build(orderSku));
            } catch (Exception e) {
                logger.error("the same time to add panic...haha....", e);
            }
            return 0;
        } else {
            logger.info("user has use panic");
            Map<String, Object> params = new HashMap<>();
            params.put("customerId", orderSku.getCustomerId());
            params.put("marketingId", orderSku.getPanicMarketingId());
            params.put("num", orderSku.getNum());
            return customerPanicRecordMapper.updateCustomerPanicRecord(params);
        }

    }

    @Override
    public int queryPanicUsedCount(long panicbuyId) {
        logger.debug("queryPanicUsedCount and panicbuyId:{}", panicbuyId);
        return customerPanicRecordMapper.queryPanicUsedCount(panicbuyId);
    }
}
