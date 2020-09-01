package com.ruoyi.marketing.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.RandomMathLetter;
import com.ruoyi.marketing.domain.StoreRedEnvelope;
import com.ruoyi.marketing.domain.StoreRedEnvelopeCode;
import com.ruoyi.marketing.domain.StoreRedEnvelopeDetails;
import com.ruoyi.marketing.mapper.StoreRedEnvelopeCodeMapper;
import com.ruoyi.marketing.mapper.StoreRedEnvelopeMapper;
import com.ruoyi.marketing.service.StoreRedEnvelopeService;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by 魔金商城 on 18/4/9
 * 门店红包服务接口实现类
 */
@Service
public class StoreRedEnvelopeServiceimpl implements StoreRedEnvelopeService {

    /**
     * 调试日志
     */
    Logger logger = LoggerFactory.getLogger(StoreRedEnvelopeServiceimpl.class);

    /**
     * 注入门店红包数据库接口
     */
    @Autowired
    private StoreRedEnvelopeMapper storeRedEnvelopeMapper;

    /**
     * 注入门店红包码数据库接口
     */
    @Autowired
    private StoreRedEnvelopeCodeMapper storeRedEnvelopeCodeMapper;

    /**
     * 添加红包
     *
     * @param storeRedEnvelope 红包实体类
     * @return 返回 -1失败 -2开始时间大于结束时间 >=1成功
     */
    @Override
    @Transactional
    public int addStoreRedEnvelope(StoreRedEnvelope storeRedEnvelope) {
        logger.debug("addStoreRedEnvelope and storeRedEnvelope :{}", storeRedEnvelope);
        //添加红包为空
        if (Objects.isNull(storeRedEnvelope)) {
            logger.error("addStoreRedEnvelope fail due to storeRedEnvelope is null");
            return -1;
        }
        //开始时间大于结束时间
        if (storeRedEnvelope.toCompareStartTime()) {
            logger.error("addStoreRedEnvelope fail due to startTime > endTime");
            return -2;
        }
        //添加红包实体
        //满的金额不大于减的金额
        if (storeRedEnvelope.getFullPrice().compareTo(storeRedEnvelope.getPrice()) < 1) {
            logger.error("addStoreRedEnvelope fail : fullPrice <= price");
            throw new ServiceException("R-000013");
        }
        storeRedEnvelopeMapper.addStoreRedEnvelope(storeRedEnvelope);
        //红包红包码集合
        List<StoreRedEnvelopeCode> storeRedEnvelopeCodes = new ArrayList<>();
        for (int i = 0; i < storeRedEnvelope.getNum(); i++) {
            storeRedEnvelopeCodes.add(new StoreRedEnvelopeCode(0, storeRedEnvelope.getId(), RandomMathLetter.randomString(18), 0, "0", null, ""));
        }
        //添加红包红包码
        return storeRedEnvelopeCodeMapper.addStoreRedEnvelopeCode(storeRedEnvelopeCodes);
    }

    /**
     * 分页查询红包
     *
     * @param pageHelper 分页帮助类
     * @param name       红包名称
     * @param storeId    店铺id
     * @return 返回红包信息
     */
    @Override
    public PageHelper<StoreRedEnvelope> queryStoreRedEnvelopes(PageHelper<StoreRedEnvelope> pageHelper, String name, long storeId) {
        logger.debug("queryStoreRedEnvelopes and pageHelper :{} \r\n name :{} \r\n storeId :{}", pageHelper, name, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("storeId", storeId);
        return pageHelper.setListDates(storeRedEnvelopeMapper.queryStoreRedEnvelopes(pageHelper.getQueryParams(params, storeRedEnvelopeMapper.queryStoreRedEnvelopeCount(params))));
    }

    /**
     * 删除及批量删除红包
     *
     * @param ids 红包id数组
     * @return 成功1 失败0
     */
    @Override
    @Transactional
    public int deleteStoreRedEnvelope(long[] ids) {
        logger.debug("deleteStoreRedEnvelope and ids {}", ids);
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("deleteStoreRedEnvelope due to ids is empty....");
            return 0;
        }
        //删除红包码
        storeRedEnvelopeCodeMapper.deleteStoreRedEnvelopeCode(ids);
        //删除红包
        storeRedEnvelopeMapper.deleteStoreRedEnvelope(ids);
        return 1;
    }

    /**
     * 查询红包详情
     *
     * @param id 红包id
     * @return 返回红包详情
     */
    @Override
    public StoreRedEnvelopeDetails queryStoreRedEnvelopeDetails(long id) {
        logger.debug("queryStoreRedEnvelopeDetails and id :{}", id);
        return new StoreRedEnvelopeDetails(storeRedEnvelopeMapper.queryStoreRedEnvelopeById(id), storeRedEnvelopeCodeMapper.queryStoreRedEnvelopeCodeByRedEnvelopeId(id));
    }

    @Override
    public StoreRedEnvelope queryStoreRedEnvelopeById(long redEnvelopeId) {
        logger.debug("queryStoreRedEnvelopeById and redEnvelopeId:{}", redEnvelopeId);
        return storeRedEnvelopeMapper.queryStoreRedEnvelopeById(redEnvelopeId);
    }

    @Override
    public List<StoreRedEnvelope> queryCanUseStoreRedEnvelopes(BigDecimal price, long storeId) {
        logger.debug("queryCanUseStoreRedEnvelopes and price:{} \r\n storeId:{}", price, storeId);
        return storeRedEnvelopeMapper.queryCanUseStoreRedEnvelopes(price, storeId);
    }

    @Override
    public String receiveStoreRedEnvelopeCode(long redEnvelopeId, long customerId) {
        logger.debug("receiveStoreRedEnvelopeCode and redEnvelopeId:{} \r\n customerId:{}", redEnvelopeId, customerId);
        StoreRedEnvelope storeRedEnvelope = storeRedEnvelopeMapper.queryStoreRedEnvelopeById(redEnvelopeId);
        if (Objects.isNull(storeRedEnvelope)) {
            logger.error("receiveStoreRedEnvelopeCode fail : no storeRedEnvelope");
            return null;
        }
        if (storeRedEnvelope.getEndTime().isBefore(LocalDateTime.now())) {
            logger.error("receiveStoreRedEnvelopeCode fail : storeRedEnvelope is out of date");
            return null;
        }
        List<StoreRedEnvelopeCode> storeRedEnvelopeCodeList = storeRedEnvelopeCodeMapper.queryCanReceiveStoreRedEnvelopeCode(redEnvelopeId);
        if (CollectionUtils.isEmpty(storeRedEnvelopeCodeList)) {
            logger.error("receiveStoreRedEnvelopeCode fail: no storeRedEnvelopeCode ");
            return null;
        }
        //更新红包code领取状态
        storeRedEnvelopeCodeMapper.updateReceivedStoreRedEnvelope(storeRedEnvelopeCodeList.get(0).buildCustomerId(customerId));

        return storeRedEnvelopeCodeList.get(0).getCode();
    }

    @Override
    public int setStoreRedEnvelopeUsed(String redEnvelopeCode, long customerId) {
        logger.debug("setStoreRedEnvelopeUsed and redEnvelopeCode:{} \r\n customerId:{}", redEnvelopeCode, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("redEnvelopeCode", redEnvelopeCode);
        params.put("customerId", customerId);
        return storeRedEnvelopeCodeMapper.setStoreRedEnvelopeUsed(params);
    }

}
