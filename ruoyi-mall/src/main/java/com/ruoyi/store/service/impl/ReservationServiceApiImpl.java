package com.ruoyi.store.service.impl;


import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.StoreSku;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.goods.service.IPmsSkuSpecValueService;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.store.domain.TStoreReservation;
import com.ruoyi.store.service.ITStoreReservationService;
import com.ruoyi.store.service.ITStoreSkuService;
import com.ruoyi.store.service.ReservationServiceApi;
import com.ruoyi.store.vo.ReservationResponse;
import com.ruoyi.util.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by 魔金商城 on 18/4/10
 * 门店预约服务接口实现类
 */
@Slf4j
@Service
public class ReservationServiceApiImpl implements ReservationServiceApi {

    /**
     * 注入门店预约服务接口
     */
    @Autowired
    private ITStoreReservationService reservationService;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入门店单品服务接口
     */
    @Autowired
    private ITStoreSkuService storeSkuService;

    /**
     * 注入单品规格值服务接口
     */
    @Autowired
    private IPmsSkuSpecValueService skuSpecValueService;

    /**
     * 注入会员服务接口
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 新增门店预约
     *
     * @param reservation 预约实体
     * @return 1成功  -1参数为空 -2单品不存在 -3单品下架 -4库存不足
     */
    @Override
    public int addReservation(TStoreReservation reservation) {
        log.debug("addReservation and reservation", reservation);

        if (Objects.isNull(reservation)) {
            log.error("addReservation fail due to reservation is empty");
            return -1;
        }

        // 根据单品id查询单品信息
        PmsSku sku = skuService.querySkuById(reservation.getSkuId());

        // 如果单品不存在 直接返回错误
        if (Objects.isNull(sku)) {
            log.error("addShoppingCart fail due to sku is not exist and skuId:{}", reservation.getSkuId());
            return -2;
        }
        // 校验单品是否上架状态
        if (!sku.validateStatus()) {
            log.error("addShoppingCart fail due to sku is not up .....");
            return -3;
        }

        //查询该门店是否有对应的单品
        List<StoreSku> storeSkus = storeSkuService.queryStoreSkuListBySkuId(reservation.getSkuId(), reservation.getStoreId());

        if (CollectionUtils.isEmpty(storeSkus)) {
            log.error("this store has no this sku.....");
            return -2;
        }

        // 设置单品的门店信息和单品在门店的价格和库存
        sku.setStoreSkuInfoAndPriceStock(storeSkus);

        //判断库存是否足够
        if (!sku.validateStock(reservation.getNum())) {
            log.error("addShoppingCart fail due to stock is not enough......");
            return -4;
        }

        return reservationService.insertTStoreReservation(reservation);
    }

    @Override
    public int addReservationForBack(TStoreReservation reservation) {
        log.debug("addReservationForBack and reservation", reservation);
        if (Objects.isNull(reservation)) {
            log.error("addReservationForBack fail due to reservation is empty");
            return -1;
        }
        UmsMember customer = customerService.queryCustomerByName(reservation.getMobile());
        if (Objects.isNull(customer)) {
            log.error("addReservationForBack fail : member is not exist");
            return -5;
        }
        return addReservation(reservation.buildCustomerId(customer.getId()));
    }

    /**
     * 查询门店预约列表
     *
     * @param pageHelper 分页帮助类
     * @param customerId 会员id
     * @param skuName    单品名称
     * @return 门店预约列表
     */
    @Override
    public PageHelper<ReservationResponse> queryReservationLists(PageHelper pageHelper, long customerId, String skuName, boolean isNeedSpecs) {
        log.debug("queryReservationLists and pageHelper :{} \r\n customerId :{} \r\n skuName :{} \r\n isNeedSpecs:{}", pageHelper, customerId, skuName, isNeedSpecs);

        //查询用户门店预约信息
        List<TStoreReservation> reservations = reservationService.queryReservationList(pageHelper, customerId, skuName).getList();

        // 如果没有门店预约信息，则直接返回
        if (CollectionUtils.isEmpty(reservations)) {
            log.error("member has no reservations....");
            return pageHelper;
        }

        // 需要查询规格信息
        if (isNeedSpecs) {
            //循环添加单品规格值
            List<ReservationResponse> reservationResponses = new ArrayList<>();
            reservations.forEach(reservation -> reservationResponses.add(new ReservationResponse().buildReservationResponse(reservation, skuSpecValueService.queryBySkuId(reservation.getSkuId()))));
            return pageHelper.setListDates(reservationResponses);
        } else {
            return pageHelper.setListDates(reservations);
        }
    }

    /**
     * 查询门店预约列表（门店用，不带分页）
     *
     * @param mobile  手机号码
     * @param storeId 店铺id
     * @return 门店预约集合
     */
    @Override
    public List<ReservationResponse> queryReservationListsForStore(String mobile, long storeId) {
        log.debug("queryReservationListsForStore and mobile :{} \r\n storeId :{}", mobile, storeId);

        //根据手机号查找用户
        UmsMember customer = customerService.queryCustomerByName(mobile);

        if (Objects.isNull(customer)) {
            log.error("queryReservationListsForStore fail due to member is not exist");
            return Collections.emptyList();
        }

        //查询用户门店预约信息
        List<TStoreReservation> reservations = reservationService.queryReservationListForStore(customer.getId(), storeId);

        // 如果没有门店预约信息，则直接返回
        if (CollectionUtils.isEmpty(reservations)) {
            log.error("member has no reservations....");
            return Collections.emptyList();
        }
        //循环添加单品规格值
        List<ReservationResponse> reservationResponses = new ArrayList<>();
        reservations.forEach(reservation -> reservationResponses.add(new ReservationResponse().buildReservationResponse(reservation, skuSpecValueService.queryBySkuId(reservation.getSkuId()))));
        return reservationResponses;
    }

    /**
     * 通过id查询门店预约列表
     *
     * @param ids     预约id数组
     * @param storeId 门店id
     * @return 门店预约集合
     */
    @Override
    public List<ReservationResponse> queryReservationListsForStoreByIds(Long[] ids, long storeId) {
        log.debug("queryReservationListsForStoreByIds and ids :{} \r\n storeId :{}", ids, storeId);

        if (ArrayUtils.isEmpty(ids)) {
            log.error("queryReservationListsForStoreByIds fail due to ids is empty");
            return Collections.emptyList();
        }

        //查询用户门店预约信息
        List<TStoreReservation> reservations = reservationService.queryReservationListForStoreByIds(ids, storeId);

        // 如果没有门店预约信息，则直接返回
        if (CollectionUtils.isEmpty(reservations)) {
            log.error("member has no reservations....");
            return Collections.emptyList();
        }
        //循环添加单品规格值
        List<ReservationResponse> reservationResponses = new ArrayList<>();
        reservations.forEach(reservation -> reservationResponses.add(new ReservationResponse().buildReservationResponse(reservation, skuSpecValueService.queryBySkuId(reservation.getSkuId()))));
        return reservationResponses;
    }

}
