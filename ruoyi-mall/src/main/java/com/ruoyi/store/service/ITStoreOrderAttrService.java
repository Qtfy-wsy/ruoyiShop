package com.ruoyi.store.service;

import com.ruoyi.store.domain.TStoreOrderAttr;

import java.util.List;

/**
 * 门店订单附属信息Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface ITStoreOrderAttrService {
    /**
     * 查询门店订单附属信息
     *
     * @param id 门店订单附属信息ID
     * @return 门店订单附属信息
     */
    public TStoreOrderAttr selectTStoreOrderAttrById(Long id);

    /**
     * 查询门店订单附属信息列表
     *
     * @param tStoreOrderAttr 门店订单附属信息
     * @return 门店订单附属信息集合
     */
    public List<TStoreOrderAttr> selectTStoreOrderAttrList(TStoreOrderAttr tStoreOrderAttr);

    /**
     * 新增门店订单附属信息
     *
     * @param tStoreOrderAttr 门店订单附属信息
     * @return 结果
     */
    public int insertTStoreOrderAttr(TStoreOrderAttr tStoreOrderAttr);

    /**
     * 修改门店订单附属信息
     *
     * @param tStoreOrderAttr 门店订单附属信息
     * @return 结果
     */
    public int updateTStoreOrderAttr(TStoreOrderAttr tStoreOrderAttr);

    /**
     * 根据订单id查询订单的附属信息
     *
     * @param orderId 订单id
     * @return 返回订单附属信息
     */
    TStoreOrderAttr queryByStoreOrderId(long orderId);

    /**
     * 批量删除门店订单附属信息
     *
     * @param ids 需要删除的门店订单附属信息ID
     * @return 结果
     */
    public int deleteTStoreOrderAttrByIds(Long[] ids);

    /**
     * 删除门店订单附属信息信息
     *
     * @param id 门店订单附属信息ID
     * @return 结果
     */
    public int deleteTStoreOrderAttrById(Long id);
}
