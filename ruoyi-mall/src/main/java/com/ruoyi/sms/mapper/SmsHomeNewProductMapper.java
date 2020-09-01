package com.ruoyi.sms.mapper;

import com.ruoyi.sms.domain.SmsHomeNewProduct;

import java.util.List;

/**
 * 新鲜好物Mapper接口
 *
 * @author é­éåå
 * @date 2020-08-06
 */
public interface SmsHomeNewProductMapper {
    /**
     * 查询新鲜好物
     *
     * @param id 新鲜好物ID
     * @return 新鲜好物
     */
    public SmsHomeNewProduct selectSmsHomeNewProductById(Long id);

    /**
     * 查询新鲜好物列表
     *
     * @param smsHomeNewProduct 新鲜好物
     * @return 新鲜好物集合
     */
    public List<SmsHomeNewProduct> selectSmsHomeNewProductList(SmsHomeNewProduct smsHomeNewProduct);

    /**
     * 新增新鲜好物
     *
     * @param smsHomeNewProduct 新鲜好物
     * @return 结果
     */
    public int insertSmsHomeNewProduct(SmsHomeNewProduct smsHomeNewProduct);

    /**
     * 修改新鲜好物
     *
     * @param smsHomeNewProduct 新鲜好物
     * @return 结果
     */
    public int updateSmsHomeNewProduct(SmsHomeNewProduct smsHomeNewProduct);

    /**
     * 删除新鲜好物
     *
     * @param id 新鲜好物ID
     * @return 结果
     */
    public int deleteSmsHomeNewProductById(Long id);

    /**
     * 批量删除新鲜好物
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSmsHomeNewProductByIds(Long[] ids);
}
