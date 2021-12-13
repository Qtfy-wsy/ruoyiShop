package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.store.domain.TStoreInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 17/8/9.
 * 查询购物车的响应实体
 */
@Data
@ApiModel(description = "查询购物车的响应实体")
public class ShoppingCartResponse {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "主键id")
    private long storeId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    private String storeLogo;

    /**
     * 是否选中 默认false
     */
    @ApiModelProperty(value = "是否选中 默认false")
    private boolean checked = false;


    /**
     * 没有促销的单品信息
     */
    @ApiModelProperty(value = "没有促销的单品信息")
    private List<SkuResponse> normalSkus = new ArrayList<>();


    /**
     * 促销信息(满减,满折,满赠)
     */
    @ApiModelProperty(value = "促销信息(满减,满折,满赠)")
    private List<MarketingResponse> marketings = new ArrayList<>();

    /**
     * 构造购物车响应实体
     *
     * @param storeInfo 店铺信息
     * @return 返回购物车响应实体
     */
    public static ShoppingCartResponse build(TStoreInfo storeInfo) {
        ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();
        if (Objects.isNull(storeInfo)) {
            return shoppingCartResponse;
        } else {
            shoppingCartResponse.storeId = storeInfo.getId();
            shoppingCartResponse.storeName = storeInfo.getStoreName();
            shoppingCartResponse.storeLogo = storeInfo.getAvatarPicture();
        }
        return shoppingCartResponse;
    }

    /**
     * 获得单品的总数
     *
     * @return 返回单品的总数
     */
    @JsonIgnore
    public BigDecimal getAllSkuNum() {
        return normalSkus.stream().map(skuResponse -> new BigDecimal(skuResponse.getNum())).reduce(new BigDecimal(0), BigDecimal::add).
                add(marketings.stream().map(MarketingResponse::getAllSkuNum).reduce(new BigDecimal(0), BigDecimal::add));
    }

    /**
     * 获得单品的总重量
     *
     * @return 返回单品的总重量
     */
    @JsonIgnore
    public BigDecimal getAllSkuWeight() {
        return normalSkus.stream().map(SkuResponse::getLastWeight).reduce(new BigDecimal(0), BigDecimal::add).
                add(marketings.stream().map(MarketingResponse::getAllSkuWeight).reduce(new BigDecimal(0), BigDecimal::add));
    }

    /**
     * 对促销进行分类 同一促销的单品归类到同一个促销下面
     */
    public void categorizedMarketings() {
        if (CollectionUtils.isEmpty(this.marketings)) {
            return;
        }

        List<MarketingResponse> lastMarketings = new ArrayList<>();

        marketings.stream().collect(Collectors.groupingBy(MarketingResponse::getId)).forEach((k, v) -> {
            MarketingResponse marketingResponse = this.marketings.stream().filter(marketingResponse1 -> marketingResponse1.getId() == k).findFirst().get();
            marketingResponse.setSkus(v.stream().map(x -> x.getSkus()).collect(ArrayList::new, List::addAll, List::addAll));
            lastMarketings.add(marketingResponse);
        });

        this.marketings = lastMarketings;
    }
}
