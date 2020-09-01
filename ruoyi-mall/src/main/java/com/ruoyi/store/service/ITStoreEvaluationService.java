package com.ruoyi.store.service;

import com.ruoyi.store.domain.TStoreEvaluation;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 门店评价Service接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface ITStoreEvaluationService {
    /**
     * 查询门店评价
     *
     * @param id 门店评价ID
     * @return 门店评价
     */
    public TStoreEvaluation selectTStoreEvaluationById(Long id);

    /**
     * 查询门店评价列表
     *
     * @param tStoreEvaluation 门店评价
     * @return 门店评价集合
     */
    public List<TStoreEvaluation> selectTStoreEvaluationList(TStoreEvaluation tStoreEvaluation);

    /**
     * 新增门店评分
     *
     * @param storeEvaluation 门店评分
     * @return 成功1 失败0
     */
    int addStoreEvaluation(TStoreEvaluation storeEvaluation);

    /**
     * 新增门店评价
     *
     * @param tStoreEvaluation 门店评价
     * @return 结果
     */
    public int insertTStoreEvaluation(TStoreEvaluation tStoreEvaluation);

    /**
     * 修改门店评价
     *
     * @param tStoreEvaluation 门店评价
     * @return 结果
     */
    public int updateTStoreEvaluation(TStoreEvaluation tStoreEvaluation);

    /**
     * 批量删除门店评价
     *
     * @param ids 需要删除的门店评价ID
     * @return 结果
     */
    public int deleteTStoreEvaluationByIds(Long[] ids);

    /**
     * 删除门店评价信息
     *
     * @param id 门店评价ID
     * @return 结果
     */
    public int deleteTStoreEvaluationById(Long id);

    /**
     * 分页查询门店评分
     *
     * @param pageHelper 分页辅助类
     * @param userName   评分人
     * @param storeId    门店ID
     * @return 门店评分信息
     */
    PageHelper<TStoreEvaluation> queryStoreEvaluation(PageHelper<TStoreEvaluation> pageHelper, String userName, long storeId);

    /**
     * 分页查询门店评分列表页
     *
     * @param pageHelper 分页辅助类
     * @param customerId 会员ID
     * @return 门店评分列表页
     */
    PageHelper<TStoreEvaluation> selStoreEvaluationList(PageHelper<TStoreEvaluation> pageHelper, long customerId);

    /**
     * 查询门店评分
     *
     * @param storeId 门店id
     * @return 评分
     */
    long queryStoreAveScore(long storeId);

    /**
     * 查询门店评分详情
     *
     * @param orderCode  订单编号
     * @param customerId 会员ID
     * @return 评分信息
     */
    TStoreEvaluation queryStoreInfoDetail(String orderCode, long customerId);

    /**
     * 分页查询门店评分
     *
     * @param pageHelper 分页辅助类
     * @param storeName  店铺名称
     * @return 门店评分信息
     */
    PageHelper<TStoreEvaluation> queryAdminStoreEvaluation(PageHelper<TStoreEvaluation> pageHelper, String storeName);

}
