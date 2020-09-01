package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.TrySkuApply;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 试用申请表数据库接口
 */
@Repository
public interface TrySkuApplyMapper {

    /**
     * 添加试用商品申请
     *
     * @param trySkuApply 试用商品申请实体
     * @return 添加试用商品申请
     */

    int addTrySkuApply(TrySkuApply trySkuApply);


    /**
     * 根据参数查询试用商品申请
     *
     * @param params 查询参数
     * @return 返回试用商品申请
     */

    TrySkuApply queryTrySkuApply(Map<String, Object> params);

    /**
     * 根据id查询试用商品申请
     *
     * @param params 查询参数
     * @return 返回试用商品申请
     */

    TrySkuApply queryTrySkuApplyById(Map<String, Object> params);

    /**
     * 根据试用促销id查询试用商品申请集合
     *
     * @param tryId 试用促销id
     * @return 返回试用商品申请集合
     */

    List<TrySkuApply> queryTrySkuApplyByTryId(long tryId);

    /**
     * 根据试用促销id查询申请通过名单
     *
     * @param tryId 试用促销id
     * @return 返回申请通过名单
     */

    List<TrySkuApply> querySuccessApplyByTryId(long tryId);


    /**
     * 分页查询试用申请
     *
     * @param params 查询参数
     * @return 返回试用申请集合
     */

    List<TrySkuApply> queryTrySkuApplys(Map<String, Object> params);

    /**
     * 根据条件查询试用申请个数
     *
     * @param params 查询参数
     * @return 返回个数
     */

    int queryTrySkuApplysCount(Map<String, Object> params);


    /**
     * 更新试用商品申请
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int updateTrySkuApplyStatus(Map<String, Object> params);

    /**
     * 更新试用商品为已下单
     *
     * @param trySkuApply 试用商品
     * @return 成功返回1 失败返回0
     */

    int updateTryApplyOrderdSingle(TrySkuApply trySkuApply);

    /**
     * 查询用户申请试用单品 (申请通过的)
     *
     * @param params 参数
     * @return 返回申请通过的申请试用单品纪录
     */

    TrySkuApply queryPassedApplyByCustomerIdAndSkuId(Map<String, Object> params);

    /**
     * 根据试用促销id和店铺id分页查询试用申请
     *
     * @param params 查询参数
     * @return 返回试用申请集合
     */

    List<TrySkuApply> queryApplyByTryIdAndStoreId(Map<String, Object> params);

    /**
     * 根据试用促销id和店铺id分页查询试用申请总记录数
     *
     * @param params 查询参数
     * @return 返回总记录数
     */

    int queryApplyByTryIdAndStoreIdCount(Map<String, Object> params);

}
