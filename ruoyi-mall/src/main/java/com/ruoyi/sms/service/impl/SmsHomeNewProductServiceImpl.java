package com.ruoyi.sms.service.impl;

import com.ruoyi.sms.domain.SmsHomeNewProduct;
import com.ruoyi.sms.mapper.SmsHomeNewProductMapper;
import com.ruoyi.sms.service.ISmsHomeNewProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 新鲜好物Service业务层处理
 *
 * @author é­éåå
 * @date 2020-08-06
 */
@Slf4j
@Service
public class SmsHomeNewProductServiceImpl implements ISmsHomeNewProductService {

    @Autowired
    private SmsHomeNewProductMapper smsHomeNewProductMapper;

    /**
     * 查询新鲜好物
     *
     * @param id 新鲜好物ID
     * @return 新鲜好物
     */
    @Override
    public SmsHomeNewProduct selectSmsHomeNewProductById(Long id) {
        return smsHomeNewProductMapper.selectSmsHomeNewProductById(id);
    }
    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        SmsHomeNewProduct record = new SmsHomeNewProduct();
        record.setRecommendStatus(recommendStatus);
        record.setId(ids.get(0));
        return smsHomeNewProductMapper.updateSmsHomeNewProduct(record);
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeNewProduct homeNewProduct = new SmsHomeNewProduct();
        homeNewProduct.setId(id);
        homeNewProduct.setSort(sort);
        return smsHomeNewProductMapper.updateSmsHomeNewProduct(homeNewProduct);
    }
    /**
     * 查询新鲜好物列表
     *
     * @param smsHomeNewProduct 新鲜好物
     * @return 新鲜好物
     */
    @Override
    public List<SmsHomeNewProduct> selectSmsHomeNewProductList(SmsHomeNewProduct smsHomeNewProduct) {
        return smsHomeNewProductMapper.selectSmsHomeNewProductList(smsHomeNewProduct);
    }

    /**
     * 新增新鲜好物
     *
     * @param smsHomeNewProduct 新鲜好物
     * @return 结果
     */
    @Override
    public int insertSmsHomeNewProduct(SmsHomeNewProduct smsHomeNewProduct) {
        return smsHomeNewProductMapper.insertSmsHomeNewProduct(smsHomeNewProduct);
    }

    /**
     * 修改新鲜好物
     *
     * @param smsHomeNewProduct 新鲜好物
     * @return 结果
     */
    @Override
    public int updateSmsHomeNewProduct(SmsHomeNewProduct smsHomeNewProduct) {
        return smsHomeNewProductMapper.updateSmsHomeNewProduct(smsHomeNewProduct);
    }

    /**
     * 批量删除新鲜好物
     *
     * @param ids 需要删除的新鲜好物ID
     * @return 结果
     */
    @Override
    public int deleteSmsHomeNewProductByIds(Long[] ids) {
        return smsHomeNewProductMapper.deleteSmsHomeNewProductByIds(ids);
    }

    /**
     * 删除新鲜好物信息
     *
     * @param id 新鲜好物ID
     * @return 结果
     */
    @Override
    public int deleteSmsHomeNewProductById(Long id) {
        return smsHomeNewProductMapper.deleteSmsHomeNewProductById(id);
    }
}
