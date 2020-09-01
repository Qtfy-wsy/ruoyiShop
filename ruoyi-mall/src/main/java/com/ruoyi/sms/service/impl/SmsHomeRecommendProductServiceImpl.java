package com.ruoyi.sms.service.impl;

import com.ruoyi.sms.domain.SmsHomeRecommendProduct;
import com.ruoyi.sms.mapper.SmsHomeRecommendProductMapper;
import com.ruoyi.sms.service.ISmsHomeRecommendProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人气推荐商品Service业务层处理
 *
 * @author é­éåå
 * @date 2020-08-06
 */
@Slf4j
@Service
public class SmsHomeRecommendProductServiceImpl implements ISmsHomeRecommendProductService {

    @Autowired
    private SmsHomeRecommendProductMapper smsHomeRecommendProductMapper;

    /**
     * 查询人气推荐商品
     *
     * @param id 人气推荐商品ID
     * @return 人气推荐商品
     */
    @Override
    public SmsHomeRecommendProduct selectSmsHomeRecommendProductById(Long id) {
        return smsHomeRecommendProductMapper.selectSmsHomeRecommendProductById(id);
    }
    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeRecommendProduct recommendProduct = new SmsHomeRecommendProduct();
        recommendProduct.setId(id);
        recommendProduct.setSort(sort);
        return smsHomeRecommendProductMapper.updateSmsHomeRecommendProduct(recommendProduct);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        SmsHomeRecommendProduct record = new SmsHomeRecommendProduct();
        record.setRecommendStatus(recommendStatus);
        record.setId(ids.get(0));
        return smsHomeRecommendProductMapper.updateSmsHomeRecommendProduct(record);
    }
    /**
     * 查询人气推荐商品列表
     *
     * @param smsHomeRecommendProduct 人气推荐商品
     * @return 人气推荐商品
     */
    @Override
    public List<SmsHomeRecommendProduct> selectSmsHomeRecommendProductList(SmsHomeRecommendProduct smsHomeRecommendProduct) {
        return smsHomeRecommendProductMapper.selectSmsHomeRecommendProductList(smsHomeRecommendProduct);
    }

    /**
     * 新增人气推荐商品
     *
     * @param smsHomeRecommendProduct 人气推荐商品
     * @return 结果
     */
    @Override
    public int insertSmsHomeRecommendProduct(SmsHomeRecommendProduct smsHomeRecommendProduct) {
        return smsHomeRecommendProductMapper.insertSmsHomeRecommendProduct(smsHomeRecommendProduct);
    }

    /**
     * 修改人气推荐商品
     *
     * @param smsHomeRecommendProduct 人气推荐商品
     * @return 结果
     */
    @Override
    public int updateSmsHomeRecommendProduct(SmsHomeRecommendProduct smsHomeRecommendProduct) {
        return smsHomeRecommendProductMapper.updateSmsHomeRecommendProduct(smsHomeRecommendProduct);
    }

    /**
     * 批量删除人气推荐商品
     *
     * @param ids 需要删除的人气推荐商品ID
     * @return 结果
     */
    @Override
    public int deleteSmsHomeRecommendProductByIds(Long[] ids) {
        return smsHomeRecommendProductMapper.deleteSmsHomeRecommendProductByIds(ids);
    }

    /**
     * 删除人气推荐商品信息
     *
     * @param id 人气推荐商品ID
     * @return 结果
     */
    @Override
    public int deleteSmsHomeRecommendProductById(Long id) {
        return smsHomeRecommendProductMapper.deleteSmsHomeRecommendProductById(id);
    }
}
