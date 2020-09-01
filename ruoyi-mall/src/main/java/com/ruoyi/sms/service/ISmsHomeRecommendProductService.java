package com.ruoyi.sms.service;

import com.ruoyi.sms.domain.SmsHomeRecommendProduct;

import java.util.List;

/**
 * 人气推荐商品Service接口
 *
 * @author é­éåå
 * @date 2020-08-06
 */
public interface ISmsHomeRecommendProductService {
    /**
     * 查询人气推荐商品
     *
     * @param id 人气推荐商品ID
     * @return 人气推荐商品
     */
    public SmsHomeRecommendProduct selectSmsHomeRecommendProductById(Long id);

    /**
     * 查询人气推荐商品列表
     *
     * @param smsHomeRecommendProduct 人气推荐商品
     * @return 人气推荐商品集合
     */
    public List<SmsHomeRecommendProduct> selectSmsHomeRecommendProductList(SmsHomeRecommendProduct smsHomeRecommendProduct);

    /**
     * 修改推荐排序
     */
    int updateSort(Long id, Integer sort);

    /**
     * 更新推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 新增人气推荐商品
     *
     * @param smsHomeRecommendProduct 人气推荐商品
     * @return 结果
     */
    public int insertSmsHomeRecommendProduct(SmsHomeRecommendProduct smsHomeRecommendProduct);

    /**
     * 修改人气推荐商品
     *
     * @param smsHomeRecommendProduct 人气推荐商品
     * @return 结果
     */
    public int updateSmsHomeRecommendProduct(SmsHomeRecommendProduct smsHomeRecommendProduct);

    /**
     * 批量删除人气推荐商品
     *
     * @param ids 需要删除的人气推荐商品ID
     * @return 结果
     */
    public int deleteSmsHomeRecommendProductByIds(Long[] ids);

    /**
     * 删除人气推荐商品信息
     *
     * @param id 人气推荐商品ID
     * @return 结果
     */
    public int deleteSmsHomeRecommendProductById(Long id);
}
