package com.ruoyi.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuSpecValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 门店购物车对象 t_store_shopping_cart
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Data
public class TStoreShoppingCart extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id = 0L;

    /**
     * 会员id
     */
    @Excel(name = "会员id")
    private long customerId;

    /**
     * 单品id
     */
    @Excel(name = "单品id")
    private String skuId;

    /**
     * 店铺id
     */
    @Excel(name = "店铺id")
    private long storeId;

    private long spuId ;
    /**
     * 删除标记 0 未删除 1删除 默认0
     */
    private int delFlag;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Integer num;

    /**
     * 单品信息
     */
    @JsonIgnore
    @ApiModelProperty(value = "单品信息")
    private PmsSku sku;

    /**
     * 构建门店购物车实体
     *
     * @param skuId      单品id
     * @param num        数量
     * @param customerId 用户id
     * @param storeId    店铺id
     * @return 门店购物车实体
     */
    public static TStoreShoppingCart build(String skuId, int num, long customerId, long storeId) {
        TStoreShoppingCart storeShoppingCart = new TStoreShoppingCart();
        storeShoppingCart.skuId = skuId;
        storeShoppingCart.num = num;
        storeShoppingCart.customerId = customerId;
        storeShoppingCart.storeId = storeId;
        return storeShoppingCart;
    }

    /**
     * 校验购物车参数是否正确
     *
     * @return 正确返回 true  不正确返回false
     */
    public boolean validate() {
        return customerId != 0 && num > 0 && !StringUtils.isEmpty(skuId);
    }

    /**
     * 设置新增门店购物车时候的默认参数
     *
     * @param customerId 会员id
     * @return 返回门店购物车
     */
    public TStoreShoppingCart setDefaultValuesForAdd(long customerId) {
        this.customerId = customerId;
        this.delFlag = 0;
        return this;
    }

    /**
     * 获得单品的价格
     *
     * @return 返回单品的价格
     */
    @JsonIgnore
    public BigDecimal getSkuPrice() {
        return Objects.isNull(sku) ? new BigDecimal(0) : sku.getPrice();
    }

    /**
     * 获得单品的库存
     *
     * @return 返回单品的库存
     */
    @JsonIgnore
    public int getSkuStock() {
        return Objects.isNull(sku) ? 0 : sku.getStock();
    }

    /**
     * 获得单品的重量
     *
     * @return 返回单品的重量
     */
    @JsonIgnore
    public BigDecimal getSkuWeight() {
        return Objects.isNull(sku) ? new BigDecimal(0) : sku.getWeight();
    }

    /**
     * 获得单品的规格值信息
     *
     * @return 返回单品的规格值信息
     */
    public List<PmsSkuSpecValue> getSkuSpecValues() {
        return Objects.isNull(sku) ? Collections.emptyList() : sku.getSkuSpecValues();
    }

    /**
     * 获得单品的默认图片
     *
     * @return 返回单品的图片
     */
    @JsonIgnore
    public String getSkuImage() {
        return Objects.isNull(sku) ? "" : sku.getUrl();
    }

    /**
     * 获得单品的名称
     *
     * @return 返回单品的名称
     */
    @JsonIgnore
    public String getSkuName() {
        return Objects.isNull(sku) ? "" : sku.getName();
    }

}
