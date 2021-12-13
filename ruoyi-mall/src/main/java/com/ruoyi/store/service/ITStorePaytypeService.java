package com.ruoyi.store.service;

import com.ruoyi.store.domain.TStorePaytype;

import java.util.List;

/**
 * 门店支付类型Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface ITStorePaytypeService {
    /**
     * 查询门店支付类型
     *
     * @param id 门店支付类型ID
     * @return 门店支付类型
     */
    public TStorePaytype selectTStorePaytypeById(Long id);

    /**
     * 查询门店支付类型列表
     *
     * @param tStorePaytype 门店支付类型
     * @return 门店支付类型集合
     */
    public List<TStorePaytype> selectTStorePaytypeList(TStorePaytype tStorePaytype);

    /**
     * 新增门店支付类型
     *
     * @param tStorePaytype 门店支付类型
     * @return 结果
     */
    public int insertTStorePaytype(TStorePaytype tStorePaytype);

    /**
     * 修改门店支付类型
     *
     * @param tStorePaytype 门店支付类型
     * @return 结果
     */
    public int updateTStorePaytype(TStorePaytype tStorePaytype);

    /**
     * 批量删除门店支付类型
     *
     * @param ids 需要删除的门店支付类型ID
     * @return 结果
     */
    public int deleteTStorePaytypeByIds(Long[] ids);

    /**
     * 删除门店支付类型信息
     *
     * @param id 门店支付类型ID
     * @return 结果
     */
    public int deleteTStorePaytypeById(Long id);
}
