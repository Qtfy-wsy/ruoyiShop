package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.domain.TryMarketing;
import com.ruoyi.marketing.domain.TrySkuApply;
import com.ruoyi.marketing.mapper.TrySkuApplyMapper;
import com.ruoyi.marketing.service.MarketingService;
import com.ruoyi.marketing.service.TryMarketingService;
import com.ruoyi.marketing.service.TrySkuApplyService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 试用申请接口实现类
 */
@Service
public class TrySkuApplyServiceImpl implements TrySkuApplyService {


    /**
     * 注入试用申请数据库接口
     */
    @Autowired
    private TrySkuApplyMapper trySkuApplyMapper;

    /**
     * 注入试用促销服务
     */
    @Resource(name = "tryService")
    private MarketingService tryService;

    /**
     * 注入试用促销服务
     */
    @Autowired
    private TryMarketingService tryMarketingService;


    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(TrySkuApplyServiceImpl.class);

    @Override
    @Transactional
    public int addTrySkuApply(TrySkuApply trySkuApply) {
        logger.debug("addTrySkuApply and trySkuApply:{}", trySkuApply);
        TryMarketing tryMarketing = tryMarketingService.queryTryMarketingById(trySkuApply.getTryId(), CommonConstant.QUERY_WITH_NO_STORE);
        //设置skuId
        trySkuApply.setSkuId(tryMarketing.getSkuId());
        if (ObjectUtils.isEmpty(tryMarketing)) {
            logger.error("addTrySkuApply fail : no tryMarketing");
            return -4;
        }
        if (tryMarketing.getStartTime().isAfter(LocalDateTime.now())) {
            logger.error("addTrySkuApply fail : not start");
            return -1;
        }
        if (tryMarketing.getEndTime().isBefore(LocalDateTime.now())) {
            logger.error("addTrySkuApply fail : already end");
            return -2;
        }
        if (tryMarketing.isApplyFull()) {
            logger.error("addTrySkuApply fail : applyNum is full");
            return -5;
        }
        TrySkuApply old = this.queryTrySkuApply(trySkuApply.getTryId(), trySkuApply.getCustomerId());
        if (!ObjectUtils.isEmpty(old)) {
            logger.error("addTrySkuApply fail : already apply");
            return -3;
        }
        //更新申请试用人数
        if (tryService.updateAlreadyApplyNum(tryMarketing) > 0) {
            //添加申请试用记录
            trySkuApplyMapper.addTrySkuApply(trySkuApply);
        }
        return 1;
    }

    @Override
    public TrySkuApply queryTrySkuApply(long tryId, long customerId) {
        logger.debug("queryTrySkuApply and tryId:{} \r\n customerId:{}", tryId, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("tryId", tryId);
        params.put("customerId", customerId);
        return trySkuApplyMapper.queryTrySkuApply(params);
    }

    @Override
    public TrySkuApply queryTrySkuApplyById(long id, long customerId, boolean isNeedDetail) {
        logger.debug("queryTrySkuApplyById and id:{}", id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("customerId", customerId);
        TrySkuApply trySkuApply = trySkuApplyMapper.queryTrySkuApplyById(params);
        if (!ObjectUtils.isEmpty(trySkuApply) && isNeedDetail) {
            trySkuApply.buildTryMarketing(tryMarketingService.queryTryMarketingById(trySkuApply.getTryId(), CommonConstant.QUERY_WITH_NO_STORE)).buildSku();
        }
        return trySkuApply;
    }

    @Override
    public List<TrySkuApply> queryTrySkuApplyByTryId(long tryId) {
        logger.debug("queryTrySkuApplyByTryId and tryId:{}", tryId);
        return trySkuApplyMapper.queryTrySkuApplyByTryId(tryId);
    }

    @Override
    public PageHelper<TrySkuApply> queryTrySkuApplys(long customerId, String status, PageHelper<TrySkuApply> pageHelper) {
        logger.debug("queryTrySkuApplys and customerId:{} \r\n status:{}", customerId, status);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        //如果是-1，表示查询全部
        if (!"-1".equals(status)) {
            params.put("status", status);
        }
        return pageHelper.setListDates(trySkuApplyMapper.queryTrySkuApplys(pageHelper.getQueryParams(params, trySkuApplyMapper.queryTrySkuApplysCount(params)))
                .stream().map(trySkuApply -> trySkuApply.buildTryMarketing(tryMarketingService.queryTryMarketingById(trySkuApply.getTryId(), CommonConstant.QUERY_WITH_NO_STORE))).collect(Collectors.toList())
                .stream().map(trySkuApply -> trySkuApply.buildSku()).collect(Collectors.toList()));
    }

    @Override
    public int updateTrySkuApplyStatus(long tryId, long customerId, String status) {
        logger.debug("updateTrySkuApplyStatus  and tryId:{} \r\n customerId:{} \r\n status:{}", tryId, customerId, status);
        Map<String, Object> params = new HashMap<>();
        params.put("tryId", tryId);
        params.put("customerId", customerId);
        params.put("status", status);
        return trySkuApplyMapper.updateTrySkuApplyStatus(params);
    }


    @Override
    public List<TrySkuApply> querySuccessApplyList(long tryId) {
        logger.debug("querySuccessApplyList and tryId:{}", tryId);
        return trySkuApplyMapper.querySuccessApplyByTryId(tryId);
    }


    @Override
    public void updateTryApplyOrderd(List<TrySkuApply> trySkuApplies) {
        logger.debug("updateTryApplyOrderd and trySkuApplies:{}", trySkuApplies);

        if (CollectionUtils.isEmpty(trySkuApplies)) {
            logger.warn("no trySkuApplies to update...");
            return;
        }

        //更新试用申请为已下单
        trySkuApplies.stream().forEach(this::updateTryApplyOrderdSingle);
    }

    @Override
    public TrySkuApply queryPassedApplyByCustomerIdAndSkuId(long customerId, String skuId) {
        logger.debug("queryPassedApplyByCustomerIdAndSkuId and customerId:{} \r\n skuId:{} ", customerId, skuId);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("skuId", skuId);

        TrySkuApply trySkuApply = trySkuApplyMapper.queryPassedApplyByCustomerIdAndSkuId(params);

        if (Objects.isNull(trySkuApply)) {
            return trySkuApply;
        }

        trySkuApply.setTryMarketing(tryMarketingService.queryTryMarketingById(trySkuApply.getTryId(), CommonConstant.QUERY_WITH_NO_STORE));

        return trySkuApply;
    }

    /**
     * 根据促销id和店铺id分页查询试用申请
     *
     * @param pageHelper 分页帮助类
     * @param tryId      试用促销id
     * @param storeId    店铺id
     * @return 返回试用申请集合
     */
    @Override
    public PageHelper<TrySkuApply> queryApplyByTryIdAndStoreId(PageHelper<TrySkuApply> pageHelper, long tryId, long storeId) {
        logger.debug("queryApplyByMarketingIdAndStoreId and pageHelper :{} \r\n tryId :{} \r\n storeId :{}", pageHelper, tryId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("tryId", tryId);
        params.put("storeId", storeId);
        return pageHelper.setListDates(trySkuApplyMapper.queryApplyByTryIdAndStoreId(pageHelper.getQueryParams(params, trySkuApplyMapper.queryApplyByTryIdAndStoreIdCount(params))));
    }

    /**
     * 更新试用申请为已下单
     *
     * @param trySkuApply 试用申请
     */
    private void updateTryApplyOrderdSingle(TrySkuApply trySkuApply) {
        trySkuApplyMapper.updateTryApplyOrderdSingle(trySkuApply);
    }

    @Override
    @Transactional
    public int randomExtractApplyCustomer() {
        logger.debug("randomExtractApplyCustomer......");
        tryMarketingService.queryTimeOutAndUnAuditTryMarketingList().stream().forEach(this::extractApplyCustomer);
        return 1;
    }


    /**
     * 随机抽取申请人
     *
     * @param tryMarketing 试用促销实体
     */
    private void extractApplyCustomer(TryMarketing tryMarketing) {
        List<TrySkuApply> trySkuApplies = this.queryTrySkuApplyByTryId(tryMarketing.getId());
        //如果没有人申请，直接返回
        if (CollectionUtils.isEmpty(trySkuApplies)) {
            return;
        }
        int tryNum = tryMarketing.getTryNum();
        //如果试用数量大于等于申请数量,则全部申请成功
        if (tryNum >= trySkuApplies.size()) {
            IntStream.range(0, trySkuApplies.size()).forEach(index -> updateTrySkuApplyStatus(trySkuApplies.get(index).getTryId(), trySkuApplies.get(index).getCustomerId(), "1"));
        } else {
            //打乱集合
            Collections.shuffle(trySkuApplies);
            //被选中的
            IntStream.range(0, tryNum).forEach(index -> updateTrySkuApplyStatus(trySkuApplies.get(index).getTryId(), trySkuApplies.get(index).getCustomerId(), "1"));
            //没有选中的
            IntStream.range(tryNum, trySkuApplies.size()).forEach(index -> updateTrySkuApplyStatus(trySkuApplies.get(index).getTryId(), trySkuApplies.get(index).getCustomerId(), "2"));
        }
        //更新审核状态
        tryService.updateAuditStatus(tryMarketing);
    }
}
