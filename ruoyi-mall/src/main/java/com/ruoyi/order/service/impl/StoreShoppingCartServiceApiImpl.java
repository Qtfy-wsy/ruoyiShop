package com.ruoyi.order.service.impl;


import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.domain.StoreSku;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.order.service.StoreShoppingCartServiceApi;
import com.ruoyi.order.vo.SkuResponse;
import com.ruoyi.order.vo.StoreShoppingCartResponse;
import com.ruoyi.store.domain.TStoreShoppingCart;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.store.service.ITStoreReservationService;
import com.ruoyi.store.service.ITStoreShoppingCartService;
import com.ruoyi.store.service.ITStoreSkuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by 魔金商城 on 2018/4/9.
 * 门店购物车服务接口实现
 */
@Slf4j
@Service
public class StoreShoppingCartServiceApiImpl implements StoreShoppingCartServiceApi {

    /**
     * 注入店铺服务接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入门店购物车服务接口
     */
    @Autowired
    private ITStoreShoppingCartService storeShoppingCartService;

    /**
     * 注入门店单品服务接口
     */
    @Autowired
    private ITStoreSkuService storeSkuService;

    /**
     * 注入门店预约服务
     */
    @Autowired
    private ITStoreReservationService reservationService;

    @Override
    public int addShoppingCart(TStoreShoppingCart storeShoppingCart) {

        log.debug("addShoppingCart and storeShoppingCart:{}", storeShoppingCart);

        // 校验参数的合法性
        if (Objects.isNull(storeShoppingCart) || !storeShoppingCart.validate()) {
            log.error("addShoppingCart fail due to params is error...");
            return -3;
        }

        // 根据单品id查询单品信息
        PmsSku sku = skuService.querySkuById(storeShoppingCart.getSkuId());

        // 如果单品不存在 直接返回错误
        if (Objects.isNull(sku)) {
            log.error("addShoppingCart fail due to sku is not exist and skuId:{}", storeShoppingCart.getSkuId());
            return -2;
        }
        // 校验单品是否上架状态
        if (!sku.validateStatus()) {
            log.error("addShoppingCart fail due to sku is not up .....");
            return -4;
        }

        //查询该门店是否有对应的单品
        List<StoreSku> storeSkus = storeSkuService.queryStoreSkuListBySkuId(storeShoppingCart.getSkuId(), storeShoppingCart.getStoreId());

        if (CollectionUtils.isEmpty(storeSkus)) {
            log.error("this store has no this sku.....");
            return -2;
        }

        // 设置单品的门店信息和单品在门店的价格和库存
        sku.setStoreSkuInfoAndPriceStock(storeSkus);

        // 查询用户已经购买的数量
        int count = storeShoppingCartService.queryCustomerSkuCount(storeShoppingCart.getCustomerId(), storeShoppingCart.getStoreId(), storeShoppingCart.getSkuId());

        //判断库存是否足够
        if (!sku.validateStock(storeShoppingCart.getNum() + count)) {
            log.error("addShoppingCart fail due to stock is not enough......");
            return -1;
        }

        // 判断用户购物车中是否已经有该商品
        if (count != 0) {

            // 更新购物车
            log.info("member shoppingcart has alerdy exist this sku and skuId:{} \r\n customerId:{}", storeShoppingCart.getSkuId(), storeShoppingCart.getCustomerId());

            return storeShoppingCartService.updateShoppingCartNum(storeShoppingCart);
        } else {

            log.info("member shoppingcart has no this sku...and begin to add....");

            // 新增购物车
            return storeShoppingCartService.addShoppingCart(storeShoppingCart);
        }
    }

    @Override
    public List<StoreShoppingCartResponse> queryShoppingCarts(long customerId) {
        log.debug("queryShoppingCarts and customerId:{}", customerId);

        return getShoppingCartDetails(storeShoppingCartService.queryByCustomerId(customerId));
    }

    @Override
    public List<StoreShoppingCartResponse> queryShoppingCartsByIds(Long[] ids, Long customerId) {
        log.debug("queryShoppingCartsByIds and ids:{} \r\n customerId:{}", ids, customerId);

        // 查询用户的购物车信息
        return getShoppingCartDetails(storeShoppingCartService.queryByIds(ids, customerId));
    }

    @Override
    public List<StoreShoppingCartResponse> queryShoppingCartsByReservationIds(Long[] reservationIds, Long customerId, long storeId) {
        log.debug("queryShoppingCartsByReservationIds and reservationIds:{} \r\n customerId:{}", reservationIds, customerId);
        // 构建虚拟购物车信息
        List<TStoreShoppingCart> storeShoppingCartList = buildVirtualStoreShoppingCart(reservationIds, customerId, storeId);

        return CollectionUtils.isEmpty(storeShoppingCartList) ? Collections.emptyList() : getShoppingCartDetails(storeShoppingCartList);
    }

    /**
     * 查询用户购物车详情
     *
     * @param storeShoppingCarts 门店购物车
     * @return 返回门店购物车详情
     */
    private List<StoreShoppingCartResponse> getShoppingCartDetails(List<TStoreShoppingCart> storeShoppingCarts) {

        // 如果没有购物车信息 则直接返回
        if (CollectionUtils.isEmpty(storeShoppingCarts)) {
            log.error("member has no shoppingcarts....");
            return new ArrayList<>();
        }

        // 所有店铺的购物车信息List<List<StoreShoppingCart>> List中的list<StoreShoppingCart> 是同一个店铺下的所有购物车信息,组织这样的数据模型方便后面的并行运算,每个店铺的购物车都并行计算
        List<List<TStoreShoppingCart>> allStoreShoppingCarts = new ArrayList<>();

        // 根据店铺进行购物后的分类,一个店铺的购物车放一起
        storeShoppingCarts.stream().collect(Collectors.groupingBy(TStoreShoppingCart::getStoreId)).forEach((key, value) -> allStoreShoppingCarts.add(value));


        // 并行计算每个店铺的购物车信息 然后进行汇总
        return allStoreShoppingCarts.parallelStream().map(this::getShoppingCartResponse).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 获得单个店铺的购物车信息
     *
     * @param storeShoppingCarts 单个店铺下的购物车信息
     * @return 返回单个店铺的购物车信息
     */
    private StoreShoppingCartResponse getShoppingCartResponse(List<TStoreShoppingCart> storeShoppingCarts) {
        log.debug("getShoppingCartResponse and storeShoppingCarts:{}", storeShoppingCarts);

        // 店铺购物车的返回数据 一个店铺返回一个实体
        return StoreShoppingCartResponse.build(storeInfoService.queryStoreInfo(storeShoppingCarts.stream().findFirst().get().getStoreId()), storeShoppingCarts.parallelStream().map(this::getsSkuResponse).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    /**
     * 获得单品的返回实体
     *
     * @param storeShoppingCart 门店购物车
     * @return 返回单品的实体
     */
    private SkuResponse getsSkuResponse(TStoreShoppingCart storeShoppingCart) {

        // 单品信息
        PmsSku sku = skuService.setSkuDetail(skuService.querySkuById(storeShoppingCart.getSkuId()), PmsSkuItem.SPEC);

        if (Objects.isNull(sku) || !sku.validate(false)) {
            log.warn("shoppingCart :{} no sku ....");
            return null;
        }

        // 设置单品信息
        storeShoppingCart.setSku(sku);

        //  查询该单品所在门店的价格和库存
        List<StoreSku> storeSkus = storeSkuService.queryStoreSkuListBySkuId(storeShoppingCart.getSkuId(), storeShoppingCart.getStoreId());

        if (CollectionUtils.isEmpty(storeSkus)) {
            log.error("storeSku is not exist");
            return null;
        }


        // 设置单品在门店的价格和库存
        sku.setStoreSkuInfoAndPriceStock(storeSkus);

        return SkuResponse.build(storeShoppingCart);
    }


    /**
     * 构造虚拟购物车(预约下单的时候使用)
     *
     * @param reservationIds 预约id
     * @param customerId     会员id
     * @param storeId        门店id
     * @return 返回虚拟购物车
     */
    private List<TStoreShoppingCart> buildVirtualStoreShoppingCart(Long[] reservationIds, Long customerId, long storeId) {
        log.debug("buildVirtualStoreShoppingCart and reservationIds:{} \r\n customerId:{} \r\n storeId:{}", reservationIds, customerId, storeId);
        if (ArrayUtils.isEmpty(reservationIds)) {
            return null;
        }
        // 购物车信息
        List<TStoreShoppingCart> storeShoppingCartList = reservationService.queryReservationListForStoreByIds(reservationIds, storeId)
                .stream().map(reservation -> TStoreShoppingCart.build(reservation.getSkuId(), reservation.getNum(), reservation.getCustomerId(), reservation.getStoreId())).collect(Collectors.toList());
        return storeShoppingCartList.stream().map(storeShoppingCart -> {
            // 查询单品信息
            PmsSku sku = skuService.querySkuById(storeShoppingCart.getSkuId());
            // 单品信息不存在 直接返回
            if (!Objects.isNull(sku)) {
                storeShoppingCart.setSku(sku);
            }
            return storeShoppingCart;
        }).collect(Collectors.toList());
    }

}
