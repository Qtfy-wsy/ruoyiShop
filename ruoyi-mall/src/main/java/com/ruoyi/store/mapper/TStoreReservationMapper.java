package com.ruoyi.store.mapper;

import com.ruoyi.store.domain.TStoreReservation;

import java.util.List;
import java.util.Map;

/**
 * 门店预约Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface TStoreReservationMapper {
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
     * 删除门店预约
     *
     * @param id 门店预约ID
     * @return 结果
     */
    public int deleteTStoreReservationById(Long id);

    /**
     * 查询门店预约列表
     *
     * @param params 查询参数
     * @return 门店预约集合
     */

    List<TStoreReservation> queryReservationList(Map<String, Object> params);

    /**
     * 查询门店预约总记录数
     *
     * @param params 查询参数
     * @return 门店预约总记录数
     */

    int queryReservationListCount(Map<String, Object> params);

    /**
     * 取消门店预约
     *
     * @param params 参数
     * @return 成功>0 失败0
     */

    int cancelReservation(Map<String, Object> params);

    /**
     * 查询门店预约列表（门店用，不带分页）
     *
     * @param params 查询参数
     * @return 门店预约集合
     */
    List<TStoreReservation> queryReservationListForStore(Map<String, Object> params);

    /**
     * 通过id查询门店预约列表
     *
     * @param params 查询参数
     * @return 门店预约集合
     */

    List<TStoreReservation> queryReservationListForStoreByIds(Map<String, Object> params);

    /**
     * 修改预约商品数量
     *
     * @param params 修改参数
     * @return 成功1 失败0
     */
    int updateReservationNum(Map<String, Object> params);

    /**
     * 批量删除门店预约
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreReservationByIds(Long[] ids);
}
