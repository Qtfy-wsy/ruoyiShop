package com.ruoyi.store.service;


import com.ruoyi.store.domain.TStoreReservation;
import com.ruoyi.store.vo.ReservationResponse;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * Created by 魔金商城 on 18/4/10
 * 门店预约服务接口
 */
public interface ReservationServiceApi {

    /**
     * 新增门店预约
     *
     * @param reservation 预约实体
     * @return 1成功  -1参数为空 -2单品不存在 -3单品下架 -4库存不足
     */
    int addReservation(TStoreReservation reservation);

    /**
     * 新增门店预约(后台添加)
     *
     * @param reservation 预约实体
     * @return 1成功  -1参数为空 -2单品不存在 -3单品下架 -4库存不足 -5用户不存在
     */
    int addReservationForBack(TStoreReservation reservation);

    /**
     * 查询门店预约列表
     *
     * @param pageHelper  分页帮助类
     * @param customerId  会员id
     * @param skuName     单品名称
     * @param isNeedSpecs 是否需要查询规格信息
     * @return 门店预约列表
     */
    PageHelper<ReservationResponse> queryReservationLists(PageHelper pageHelper, long customerId, String skuName, boolean isNeedSpecs);

    /**
     * 查询门店预约列表（门店用，不带分页）
     *
     * @param mobile  手机号码
     * @param storeId 门店id
     * @return 门店预约集合
     */
    List<ReservationResponse> queryReservationListsForStore(String mobile, long storeId);

    /**
     * 通过id查询门店预约列表
     *
     * @param ids     预约id数组
     * @param storeId 门店id
     * @return 门店预约集合
     */
    List<ReservationResponse> queryReservationListsForStoreByIds(Long[] ids, long storeId);

}
