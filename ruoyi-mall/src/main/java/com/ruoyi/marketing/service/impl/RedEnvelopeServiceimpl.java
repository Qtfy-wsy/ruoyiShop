package com.ruoyi.marketing.service.impl;


import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.RandomMathLetter;
import com.ruoyi.marketing.domain.RedEnvelope;
import com.ruoyi.marketing.domain.RedEnvelopeCode;
import com.ruoyi.marketing.domain.RedEnvelopeDetails;
import com.ruoyi.marketing.mapper.RedEnvelopeCodeMapper;
import com.ruoyi.marketing.mapper.RedEnvelopeMapper;
import com.ruoyi.marketing.mapper.RedEnvelopeStoreMapper;
import com.ruoyi.marketing.service.RedEnvelopeService;
import com.ruoyi.setting.service.BaseInfoSetService;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 魔金商城 on 18/1/15
 * 红包服务接口实现类
 */
@Service
public class RedEnvelopeServiceimpl implements RedEnvelopeService {

    /**
     * 红包状态字典
     */
    private static Map<String, String> redEnvelopeCodeStatusDic = new HashMap<>();

    static {
        redEnvelopeCodeStatusDic.put("0", "未使用");
        redEnvelopeCodeStatusDic.put("1", "已领取未使用");
        redEnvelopeCodeStatusDic.put("2", "已使用");
        redEnvelopeCodeStatusDic.put("3", "已失效");
    }

    /**
     * 调试日志
     */
    Logger logger = LoggerFactory.getLogger(RedEnvelopeServiceimpl.class);
    /**
     * 注入红包数据库接口
     */
    @Autowired
    private RedEnvelopeMapper redEnvelopeMapper;
    /**
     * 注入红包红包码数据库接口
     */
    @Autowired
    private RedEnvelopeCodeMapper redEnvelopeCodeMapper;
    /**
     * 注入红包店铺数据库接口
     */
    @Autowired
    private RedEnvelopeStoreMapper redEnvelopeStoreMapper;
    /**
     * 注入信息设置
     */
    @Autowired
    private BaseInfoSetService baseInfoSetService;

    /**
     * 添加红包
     *
     * @param redEnvelope 红包实体类
     * @return 返回 -1失败 -2限领个数小于等于零 -3限领个数大于总个数 -4开始时间大于结束时间 -5店铺报名截止时间大于开始时间 >=1成功
     */
    @Override
    @Transactional
    public int addRedEnvelope(RedEnvelope redEnvelope) {
        logger.debug("addRedEnvelop and redEnvelope :{}", redEnvelope);
        //添加红包为空
        if (Objects.isNull(redEnvelope)) {
            logger.error("addRedEnvelop fail due to redEnvelope is null");
            return -1;
        }
        //限领个数小于等于零
        if (redEnvelope.getLimitNum() <= 0) {
            logger.error("addRedEnvelop fail due to limitNum <= 0");
            return -2;
        }
        //限领个数大于总个数
        if (redEnvelope.getNum() < redEnvelope.getLimitNum()) {
            logger.error("addRedEnvelop fail due to num < limitNum");
            return -3;
        }
        //开始时间大于结束时间
        if (redEnvelope.toCompareStartTime()) {
            logger.error("addRedEnvelop fail due to startTime > endTime");
            return -4;
        }
        //店铺报名截止时间大于开始时间
        if (redEnvelope.toCompareSignupTime()) {
            logger.error("addRedEnvelop fail due to signupTime > startTime");
            return -5;
        }
        //添加红包实体
        //满的金额不大于减的金额
        if (redEnvelope.getFullPrice().compareTo(redEnvelope.getPrice()) < 1) {
            logger.error("addRedEnvelop fail : fullPrice <= price");
            throw new ServiceException("R-000013");
        }
        redEnvelopeMapper.addRedEnvelope(redEnvelope);
        //添加红包店铺
        redEnvelopeStoreMapper.addRedEnvelopeStore(redEnvelope.getId());
        //红包红包码集合
        List<RedEnvelopeCode> redEnvelopeCode = new ArrayList<>();
        for (int i = 0; i < redEnvelope.getNum(); i++) {
            redEnvelopeCode.add(new RedEnvelopeCode(0, redEnvelope.getId(), RandomMathLetter.randomString(18), 0, "0", null, ""));
        }
        //添加红包红包码
        return redEnvelopeCodeMapper.addRedEnvelopeCode(redEnvelopeCode);
    }

    /**
     * 分页查询红包
     *
     * @param pageHelper    分页帮助类
     * @param name          红包名称
     * @param storeId       店铺id
     * @param isNeedHasJoin 是否需要参加红包
     * @return 返回红包信息
     */
    @Override
    public PageHelper<RedEnvelope> queryRedEnvelopes(PageHelper<RedEnvelope> pageHelper, String name, long storeId, boolean isNeedHasJoin) {
        logger.debug("queryRedEnvelopes and pageHelper :{} \r\n name :{} \r\n isNeedHasJoin :{}", pageHelper, name, isNeedHasJoin);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        List<RedEnvelope> redEnvelopes = redEnvelopeMapper.queryRedEnvelopes(pageHelper.getQueryParams(params, redEnvelopeMapper.queryRedEnvelopeCount(params)));
        //判断是否需要参加红包和红包集合是否为空
        if (isNeedHasJoin && !CollectionUtils.isEmpty(redEnvelopes)) {
            //循环判断店铺是否可以参加红包，并给ifCanJoin赋值
            redEnvelopes.stream().forEach(redEnvelope -> this.setRedEnvelopesJoinFlag(redEnvelope, storeId));
        }
        //不需要参加红包
        return pageHelper.setListDates(redEnvelopes);
    }

    /**
     * 删除及批量删除红包
     *
     * @param ids 红包id数组
     * @return 成功1 失败0
     */
    @Override
    @Transactional
    public int deleteRedEnvelope(long[] ids) {
        logger.debug("deletePointCate and ids {}", ids);
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("deleteRedEnvelope due to ids is empty....");
            return 0;
        }
        //删除红包码
        redEnvelopeCodeMapper.deleteRedEnvelopeCode(ids);
        //删除红包
        redEnvelopeMapper.deleteRedEnvelope(ids);
        return 1;
    }

    /**
     * 查询红包详情
     *
     * @param id 红包id
     * @return 返回红包详情
     */
    @Override
    public RedEnvelopeDetails queryRedEnvelopeDetails(long id) {
        logger.debug("queryRedEnvelopeDetails and id :{}", id);
        return new RedEnvelopeDetails(redEnvelopeMapper.queryRedEnvelopeById(id), redEnvelopeCodeMapper.queryRedEnvelopeCodeByRedEnvelopeId(id));
    }


    /**
     * 复制红包链接
     *
     * @param id 红包id
     * @return -1 不存在该红包  0 该红包已过期  其他字符串均为红包链接
     */
    @Override
    public String copyRedEnvelopeUrl(long id) {
        logger.debug("copyRedEnvelope and id :{}", id);
        RedEnvelope redEnvelope = redEnvelopeMapper.queryRedEnvelopeById(id);
        //不存在该红包
        if (Objects.isNull(redEnvelope)) {
            logger.error("copyRedEnvelope fail due to redEnvelope is null");
            return "-1";
        }
        //该红包已过期
        if (!redEnvelope.getEndTime().isAfter(LocalDateTime.now())) {
            logger.error("copyRedEnvelope fail due to endTime <= nowTime");
            return "0";
        }
        //返回该红包链接
        return baseInfoSetService.queryBaseInfoSet().getSiteUrl() + "/#/getbonus?redEnvelopeId=" + redEnvelope.getId();
    }


    @Override
    public int receiveRedEnvelope(long customerId, Long redEnvelopeId) {
        logger.debug("receiveRedEnvelope and customerId:{} \r\n redEnvelopeId:{}", customerId, redEnvelopeId);
        if (ObjectUtils.isEmpty(customerId) || ObjectUtils.isEmpty(redEnvelopeId)) {
            logger.error("receiveRedEnvelope fail:customerId or redEnvelopeId is empty");
            return -1;
        }
        RedEnvelope redEnvelope = redEnvelopeMapper.queryRedEnvelopeByIdForReceive(redEnvelopeId);
        if (ObjectUtils.isEmpty(redEnvelope)) {
            logger.error("receiveRedEnvelope error : redEnvelope deleted");
            return -5;
        }
        if (redEnvelope.getEndTime().isBefore(LocalDateTime.now())) {
            logger.error("receiveRedEnvelope error : activity finish");
            return -2;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", customerId);
        map.put("redEnvelopeId", redEnvelopeId);
        if (redEnvelopeCodeMapper.queryReceivedRedEnvelopeCodeByCustomerId(map) >= redEnvelope.getLimitNum()) {
            logger.error("receiveRedEnvelope error : over limitNum");
            return -4;
        }

        // 可以使用的红包码
        List<RedEnvelopeCode> redEnvelopeCodeList = redEnvelopeCodeMapper.queryCanReceiveRedEnvelopeCode(redEnvelopeId);
        if (redEnvelopeCodeList.size() < 1) {
            logger.error("receiveRedEnvelope error : redEnvelope run out");
            return -3;
        }
        RedEnvelopeCode redEnvelopeCode = redEnvelopeCodeList.get(0);
        redEnvelopeCode.setCustomerId(customerId);
        int i = redEnvelopeCodeMapper.updateReceivedRedEnvelope(redEnvelopeCode);
        return updateRedEnvelopeReceived(i, redEnvelopeId, redEnvelopeCodeList);
    }

    @Override
    public RedEnvelope queryRedEnvelopeByRedEnvelopeId(Long redEnvelopeId) {
        logger.debug("queryRedEnvelopeByRedEnvelopeId and redEnvelopeId:{}", redEnvelopeId);
        if (ObjectUtils.isEmpty(redEnvelopeId)) {
            logger.error("queryRedEnvelopeByRedEnvelopeId fail :redEnvelopeId is null");
            return null;
        }
        RedEnvelope redEnvelope = redEnvelopeMapper.queryRedEnvelopeById(redEnvelopeId);
        if (ObjectUtils.isEmpty(redEnvelope)) {
            logger.error("queryRedEnvelopeByRedEnvelopeId fail :redEnvelope is null");
            return null;
        }
        return redEnvelope;
    }

    @Override
    public PageHelper<RedEnvelope> queryRedEnvelopeForSite(PageHelper<RedEnvelope> pageHelper) {
        logger.debug("queryRedEnvelopeForSite.....");
        return pageHelper.setListDates(redEnvelopeMapper.queryRedEnvelopeForPc(pageHelper.getQueryParams(new HashMap<>(), redEnvelopeMapper.queryRedEnvelopeForPcCount()))
                .stream().map(redEnvelope -> {
                    redEnvelope.buildCanReceiveCount(redEnvelopeCodeMapper.queryCanReceiveRedEnvelopeCodeCount(redEnvelope.getId()));
                    return redEnvelope.buildIsRunOut();
                }).collect(Collectors.toList())
        );
    }

    @Override
    public PageHelper<RedEnvelopeCode> queryRedEnvelopeCodeByCustomerId(PageHelper<RedEnvelopeCode> pageHelper, long customerId, String status) {
        logger.debug("queryRedEnvelopeCodeByCustomerId and  customerId:{}\r\n status:{}", customerId, status);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        if (!StringUtils.isEmpty(status)) {
            params.put("status", status);
        }
        List<RedEnvelopeCode> redEnvelopeCodeList = redEnvelopeCodeMapper.queryRedEnvelopeCodeByCustomerId(pageHelper.getQueryParams(params, redEnvelopeCodeMapper.queryRedEnvelopeCodeCountByCustomerId(params))).stream()
                .map(redEnvelopeCode -> getRedEnvelopeDetailInfo(redEnvelopeCode)).collect(Collectors.toList());
        return pageHelper.setListDates(redEnvelopeCodeList);
    }


    @Override
    public List<RedEnvelopeCode> queryCustomerCanUseRedEnvelope(long customerId) {
        logger.debug("queryCustomerCanUseRedEnvelope and customerId:{}", customerId);
        List<RedEnvelopeCode> list = redEnvelopeCodeMapper.queryCustomerCanUseRedEnvelope(customerId);

        if (CollectionUtils.isEmpty(list)) {
            logger.info("user has no redEnvelope.....");
            return list;
        }

        // 设置红包关联的店铺
        list.stream().forEach(redEnvelopeCode -> redEnvelopeCode.setRedEnvelopeStores(redEnvelopeStoreMapper.queryByRedEnvelopeId(redEnvelopeCode.getRedEnvelopeId())));

        return list;
    }

    @Override
    public int setRedEnvelopeUsed(Long customerId, String redEnvelopeCode) {
        logger.debug("setCouponUsed and customerId:{} \r\n redEnvelopeCode:{}", customerId, redEnvelopeCode);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("redEnvelopeCode", redEnvelopeCode);
        return redEnvelopeCodeMapper.setRedEnvelopeUsed(params);
    }

    /**
     * 参加红包（store用）
     *
     * @param redEnvelopeId 红包id
     * @param storeId       店铺id
     * @return 返回 -2 失败  -1 红包已过期  0 已经参加过红包  1 成功
     */
    @Override
    public int joinRedEnvelope(long redEnvelopeId, long storeId) {
        logger.debug("joinRedEnvelope and redEnvelopeId :{} \r\n storeId :{}", redEnvelopeId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("redEnvelopeId", redEnvelopeId);
        params.put("storeId", storeId);
        //失败，红包id或店铺id为空
        if (ObjectUtils.isEmpty(redEnvelopeId) || ObjectUtils.isEmpty(storeId)) {
            logger.error("joinRedEnvelope fail due to redEnvelopeId or storeId is empty");
            return -2;
        }
        //红包已过期
        if (redEnvelopeMapper.queryRedEnvelopeById(redEnvelopeId).getSignupTime().isBefore(LocalDateTime.now())) {
            logger.error("joinRedEnvelope fail due to redEnvelope has expired");
            return -1;
        }
        //已经参加过红包
        if (redEnvelopeStoreMapper.hasJoinRedEnvelope(params) > 0) {
            logger.error("joinRedEnvelope fail due to has joined redEnvelope");
            return 0;
        }
        //成功参加红包
        return redEnvelopeStoreMapper.joinRedEnvelope(params);
    }

    /**
     * 查询店铺是否可以参加红包
     *
     * @param redEnvelopeId 红包id
     * @param storeId       店铺id
     * @return 返回 -2 不可以  -1 红包已过期  0 已经参加过红包  1 可以参加
     */
    private int queryIfCanJoinRedEnvelope(long redEnvelopeId, long storeId) {
        logger.debug("queryIfCanJoinRedEnvelope and redEnvelopeId :{} \r\n storeId :{}", redEnvelopeId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("redEnvelopeId", redEnvelopeId);
        params.put("storeId", storeId);
        //不可以，红包id或店铺id为空
        if (ObjectUtils.isEmpty(redEnvelopeId) || ObjectUtils.isEmpty(storeId)) {
            logger.error("queryIfCanJoinRedEnvelope can not  due to redEnvelopeId or storeId is empty");
            return -2;
        }
        //红包已过期
        if (redEnvelopeMapper.queryRedEnvelopeById(redEnvelopeId).getSignupTime().isBefore(LocalDateTime.now())) {
            logger.error("queryIfCanJoinRedEnvelope can not due to redEnvelope has expired");
            return -1;
        }
        //已经参加过红包
        if (redEnvelopeStoreMapper.hasJoinRedEnvelope(params) > 0) {
            logger.error("queryIfCanJoinRedEnvelope can not due to has joined redEnvelope");
            return 0;
        }
        return 1;
    }

    /**
     * 判断店铺是否可以参加红包，并给ifCanJoin赋值
     *
     * @param redEnvelope 红包实体
     * @param storeId     店铺id
     */
    private void setRedEnvelopesJoinFlag(RedEnvelope redEnvelope, final long storeId) {
        //判断店铺是否可以参加红包
        if (queryIfCanJoinRedEnvelope(redEnvelope.getId(), storeId) == 1) {
            //店铺可以参加红包，给ifCanJoin赋值true
            redEnvelope.buildIfCanJoin(true);
        } else {
            //店铺不可以参加红包，给ifCanJoin赋值false
            redEnvelope.buildIfCanJoin(false);
        }
    }

    /**
     * 获取红包详情
     *
     * @param redEnvelopeCode 红包
     * @return 红包
     */
    private RedEnvelopeCode getRedEnvelopeDetailInfo(RedEnvelopeCode redEnvelopeCode) {
        logger.debug("getRedEnvelopeDetailInfo and redEnvelopeCode", redEnvelopeCode);
        redEnvelopeCode.setRedEnvelope(redEnvelopeMapper.queryRedEnvelopeById(redEnvelopeCode.getRedEnvelopeId()));
        redEnvelopeCode.setRedEnvelopeStores(redEnvelopeStoreMapper.queryStoresByRedEnvelopeId(redEnvelopeCode.getRedEnvelopeId()));
        return redEnvelopeCode;
    }

    /**
     * 判断红包是否领完，领完则更新红包状态为已领完
     *
     * @param i                   更新红包领取状态返回码
     * @param redEnvelopeId       红包id
     * @param redEnvelopeCodeList 红包码集合
     * @return 1 成功  -6 系统繁忙，请重试
     */
    private int updateRedEnvelopeReceived(int i, Long redEnvelopeId, List<RedEnvelopeCode> redEnvelopeCodeList) {
        if (i > 0) {
            // 红包码已经都被使用 则设置状态为已领完
            if (redEnvelopeCodeList.size() == 1) {
                logger.debug("redEnvelope has all received");
                redEnvelopeMapper.updateRedEnvelopeAllReceived(redEnvelopeId);
            }
            logger.info("receiveRedEnvelope success");
            return 1;
        } else {
            logger.error("receiveRedEnvelope error : already been used");
            return -6;
        }
    }
}
