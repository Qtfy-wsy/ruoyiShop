package com.ruoyi.store.service;

import com.ruoyi.store.domain.TStoreReservation;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 门店预约Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface ITStoreReservationService {
    /**
     * 查询门店预约
     *
     * @param id 门店预约ID
     * @return 门店预约
     */
    public TStoreReservation selectTStoreReservationById(Long id);

    /**
     * 查询门店预约列表
     *
     * @param tStoreReservation 门店预约
     * @return 门店预约集合
     */
    public List<TStoreReservation> selectTStoreReservationList(TStoreReservation tStoreReservation);

    /**
     * 新增门店预约
     *
     * @param tStoreReservation 门店预约
     * @return 结果
     */
    public int insertTStoreReservation(TStoreReservation tStoreReservation);

    /**
     * 修改门店预约
     *
     * @param tStoreReservation 门店预约
     * @return 结果
     */
    public int updateTStoreReservation(TStoreReservation tStoreReservation);

    /**
     * 查询门店预约列表
     *
     * @param pageHelper 分页帮助类
     * @param customerId 会员id
     * @param skuName    单品名称
     * @return 门店预约列表
     */
    PageHelper<TStoreReservation> queryReservationList(PageHelper<TStoreReservation> pageHelper, long customerId, String skuName);

    /**
     * 取消门店预约
     *
     * @param ids        门店预约id数组
     * @param customerId 会员id
     * @param storeId    门店id
     * @return 成功>0 失败0
     */
    int cancelReservation(Long[] ids, long customerId, long storeId);

    /**
     * 查询门店预约列表（门店用，不带分页）
     *
     * @param customerId 会员id
     * @param storeId    店铺id
     * @return 门店预约集合
     */
    List<TStoreReservation> queryReservationListForStore(long customerId, long storeId);

    /**
     * 通过id查询门店预约列表
     *
     * @param ids     预约id数组
     * @param storeId 门店id
     * @return 门店预约集合
     */
    List<TStoreReservation> queryReservationListForStoreByIds(Long[] ids, long storeId);


    /**
     * 修改预约商品数量
     *
     * @param num     预约商品数量
     * @param id      预约id
     * @param storeId 门店id
     * @return 成功1 失败0
     */
    int updateReservationNum(int num, long id, long storeId);

    /**
     * 批量删除门店预约
     *
     * @param ids 需要删除的门店预约ID
     * @return 结果
     */
    public int deleteTStoreReservationByIds(Long[] ids);

    /**
     * 删除门店预约信息
     *
     * @param id 门店预约ID
     * @return 结果
     */
    public int deleteTStoreReservationById(Long id);
}
