package com.ruoyi.store.mapper;

import com.ruoyi.store.domain.TStoreEvaluation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 门店评价Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface TStoreEvaluationMapper {
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
     * 新增门店评价
     *
     * @param tStoreEvaluation 门店评价
     * @return 结果
     */
    public int insertTStoreEvaluation(TStoreEvaluation tStoreEvaluation);

    /**
     * 新增门店评分
     *
     * @param storeEvaluation 门店评分
     * @return 成功1 失败0
     */

    int addStoreEvaluation(TStoreEvaluation storeEvaluation);

    /**
     * 查询门店评分总数
     *
     * @param params 参数条件
     * @return 行数
     */

    int queryStoreEvaluationCount(Map<String, Object> params);

    /**
     * 查询门店评分
     *
     * @param params 参数条件
     * @return 返回门店评分
     */

    List<TStoreEvaluation> queryStoreEvaluation(Map<String, Object> params);

    /**
     * 查询门店评分列表总数
     *
     * @param params 参数
     * @return 行数
     */

    int selStoreEvaluationListCount(Map<String, Object> params);

    /**
     * 查询门店评分列表
     *
     * @param params 参数条件
     * @return 返回门店评分列表
     */

    List<TStoreEvaluation> selStoreEvaluationList(Map<String, Object> params);


    /**
     * 修改门店订单评价状态为已评价
     *
     * @param orderCode 订单号
     * @return 1成功 0失败
     */

    int updEvaluationStatus(String orderCode);

    /**
     * 查询门店评分
     *
     * @param storeId 门店id
     * @return 评分
     */

    long queryStoreAveScore(@Param("storeId") long storeId);

    /**
     * 查询门店评分详情
     *
     * @param params 参数条件
     * @return 门店评分详情
     */

    TStoreEvaluation queryStoreInfoDetail(Map<String, Object> params);

    /**
     * 查询门店评分总数
     *
     * @return 行数
     */

    int queryAdminStoreEvaluationCount(Map<String, Object> params);


    /**
     * 分页查询门店评分
     *
     * @param params 参数条件
     * @return 返回门店评分
     */

    List<TStoreEvaluation> queryAdminStoreEvaluationList(Map<String, Object> params);

    /**
     * 修改门店评价
     *
     * @param tStoreEvaluation 门店评价
     * @return 结果
     */
    public int updateTStoreEvaluation(TStoreEvaluation tStoreEvaluation);

    /**
     * 删除门店评价
     *
     * @param id 门店评价ID
     * @return 结果
     */
    public int deleteTStoreEvaluationById(Long id);

    /**
     * 批量删除门店评价
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreEvaluationByIds(Long[] ids);
}
