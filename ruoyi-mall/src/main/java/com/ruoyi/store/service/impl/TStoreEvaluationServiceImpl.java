package com.ruoyi.store.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.store.domain.TStoreEvaluation;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.mapper.TStoreEvaluationMapper;
import com.ruoyi.store.service.ITStoreEvaluationService;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 门店评价Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Service
public class TStoreEvaluationServiceImpl implements ITStoreEvaluationService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreEvaluationServiceImpl.class);
    @Autowired
    private TStoreEvaluationMapper tStoreEvaluationMapper;

    /**
     * 注入门店评价数据接口
     */
    @Autowired
    private TStoreEvaluationMapper storeEvaluationMapper;

    /**
     * 注入店铺服务接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;

    /**
     * 新增门店评分
     *
     * @param storeEvaluation 门店评分
     * @return 成功1 失败0
     */
    @Override
    public int addStoreEvaluation(TStoreEvaluation storeEvaluation) {
        logger.debug("addStoreEvaluation and addStoreEvaluation:{}", storeEvaluation);

        if (Objects.isNull(storeEvaluation)) {
            logger.error("addStoreEvaluation fail due to storeEvaluation is null...");
            return 0;
        }

        //修改门店订单评价状态为已评价
        storeEvaluationMapper.updEvaluationStatus(storeEvaluation.getOrderCode());

        return storeEvaluationMapper.addStoreEvaluation(storeEvaluation);
    }

    /**
     * 分页查询门店评分
     *
     * @param pageHelper 分页辅助类
     * @param userName   评分人
     * @param storeId    门店ID
     * @return 门店评分信息
     */
    @Override
    public PageHelper<TStoreEvaluation> queryStoreEvaluation(PageHelper<TStoreEvaluation> pageHelper, String userName, long storeId) {
        logger.debug("queryStoreEvaluation and pageHelper:{} \n\n userName:{}", pageHelper, userName);
        // 获得查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        params.put("storeId", storeId);
        return pageHelper.setListDates(storeEvaluationMapper.queryStoreEvaluation(pageHelper.getQueryParams(params, storeEvaluationMapper.queryStoreEvaluationCount(params))));
    }

    /**
     * 分页查询门店评分列表页
     *
     * @param pageHelper 分页辅助类
     * @param customerId 会员ID
     * @return 门店评分列表页
     */
    @Override
    public PageHelper<TStoreEvaluation> selStoreEvaluationList(PageHelper<TStoreEvaluation> pageHelper, long customerId) {
        logger.debug("selStoreEvaluationList and pageHelper:{} \n\n customerId:{}", pageHelper, customerId);
        // 获得查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        return pageHelper.setListDates(storeEvaluationMapper.selStoreEvaluationList(pageHelper.getQueryParams(params, storeEvaluationMapper.selStoreEvaluationListCount(params))));
    }

    /**
     * 查询门店评分
     *
     * @param storeId 门店id
     * @return 评分
     */
    @Override
    public long queryStoreAveScore(long storeId) {
        logger.debug("queryStoreAveScore and storeId:{}", storeId);
        return storeEvaluationMapper.queryStoreAveScore(storeId);
    }

    /**
     * 查询门店评分详情
     *
     * @param orderCode  订单编号
     * @param customerId 会员ID
     * @return 评分信息
     */
    @Override
    public TStoreEvaluation queryStoreInfoDetail(String orderCode, long customerId) {
        logger.debug("queryStoreInfoDetail and orderCode:{} \n\n customerId:{}", orderCode, customerId);
        // 获得查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("customerId", customerId);

        // 店铺评价
        TStoreEvaluation storeEvaluation = storeEvaluationMapper.queryStoreInfoDetail(params);

        if (Objects.isNull(storeEvaluation)) {
            return storeEvaluation;
        }

        // 店铺信息
        TStoreInfo storeInfo = storeInfoService.selStoreInfo(storeEvaluation.getStoreId());

        if (Objects.nonNull(storeInfo)) {
            storeEvaluation.setAvatarPicture(storeInfo.getAvatarPicture());
            storeEvaluation.setStoreName(storeInfo.getStoreName());
            storeEvaluation.setCompanyAddress(storeInfo.getCompanyAddress());
            storeEvaluation.setPhone(storeInfo.getContactPhone());
            storeEvaluation.setBusinessTime(storeInfo.getBusinessTime());
            storeEvaluation.setBusRoute(storeInfo.getBusRoutes());
            storeEvaluation.setAvgScore(storeInfo.getAveScore());
        }

        return storeEvaluation;
    }

    /**
     * 分页查询门店评分
     *
     * @param pageHelper 分页辅助类
     * @param storeName  店铺名称
     * @return 门店评分信息
     */
    @Override
    public PageHelper<TStoreEvaluation> queryAdminStoreEvaluation(PageHelper<TStoreEvaluation> pageHelper, String storeName) {
        // 获得查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("storeName", storeName);
        return pageHelper.setListDates(storeEvaluationMapper.queryAdminStoreEvaluationList(pageHelper.getQueryParams(params, storeEvaluationMapper.queryAdminStoreEvaluationCount(params))));
    }

    /**
     * 查询门店评价
     *
     * @param id 门店评价ID
     * @return 门店评价
     */
    @Override
    public TStoreEvaluation selectTStoreEvaluationById(Long id) {
        return tStoreEvaluationMapper.selectTStoreEvaluationById(id);
    }

    /**
     * 查询门店评价列表
     *
     * @param tStoreEvaluation 门店评价
     * @return 门店评价
     */
    @Override
    public List<TStoreEvaluation> selectTStoreEvaluationList(TStoreEvaluation tStoreEvaluation) {
        return tStoreEvaluationMapper.selectTStoreEvaluationList(tStoreEvaluation);
    }

    /**
     * 新增门店评价
     *
     * @param tStoreEvaluation 门店评价
     * @return 结果
     */
    @Override
    public int insertTStoreEvaluation(TStoreEvaluation tStoreEvaluation) {
        tStoreEvaluation.setCreateTime(DateUtils.getNowDate());
        return tStoreEvaluationMapper.insertTStoreEvaluation(tStoreEvaluation);
    }

    /**
     * 修改门店评价
     *
     * @param tStoreEvaluation 门店评价
     * @return 结果
     */
    @Override
    public int updateTStoreEvaluation(TStoreEvaluation tStoreEvaluation) {
        return tStoreEvaluationMapper.updateTStoreEvaluation(tStoreEvaluation);
    }

    /**
     * 批量删除门店评价
     *
     * @param ids 需要删除的门店评价ID
     * @return 结果
     */
    @Override
    public int deleteTStoreEvaluationByIds(Long[] ids) {
        return tStoreEvaluationMapper.deleteTStoreEvaluationByIds(ids);
    }

    /**
     * 删除门店评价信息
     *
     * @param id 门店评价ID
     * @return 结果
     */
    @Override
    public int deleteTStoreEvaluationById(Long id) {
        return tStoreEvaluationMapper.deleteTStoreEvaluationById(id);
    }
}
