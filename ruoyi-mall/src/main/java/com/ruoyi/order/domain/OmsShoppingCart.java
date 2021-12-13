package com.ruoyi.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuBatch;
import com.ruoyi.goods.domain.PmsSkuSpecValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 购物车对象 oms_shopping_cart
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Data
public class OmsShoppingCart extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private long id = 0;

    /**
     * 会员id
     */
    @Excel(name = "会员id")
    private long customerId;

    private long spuId ;

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

    /**
     * 单品数量
     */
    @Excel(name = "单品数量")
    private Integer num;

    /**
     * 默认的营销id(满减或者满折)
     */
    @Excel(name = "默认的营销id(满减或者满折)")
    private long marketingId;

    /**
     * 删除标记 0 未删除 1删除 默认0
     */
    private int delFlag;

    /**
     * 单品信息
     */
    @JsonIgnore
    @ApiModelProperty(value = "单品信息")
    private PmsSku sku;

    /**
     * 众筹id
     */
    @ApiModelProperty(value = "众筹id")
    private Long crowdfundingId;


    /**
     * 构造购物车信息
     *
     * @param skuId      单品id
     * @param num        数量
     * @param customerId 会员id
     * @return 返回购物车信息
     */
    public static OmsShoppingCart build(String skuId, int num, long customerId) {
        OmsShoppingCart shoppingCart = new OmsShoppingCart();
        shoppingCart.skuId = skuId;
        shoppingCart.num = num;
        shoppingCart.customerId = customerId;
        return shoppingCart;
    }

    /**
     * 构造更新购物车促销的实体
     *
     * @param id          主键id
     * @param marketingId 促销id
     * @param customerId  会员id
     * @return 返回更新购物车促销的实体
     */
    public static OmsShoppingCart buildForUpdateMarketing(long id, long marketingId, long customerId) {
        OmsShoppingCart shoppingCart = new OmsShoppingCart();
        shoppingCart.id = id;
        shoppingCart.marketingId = marketingId;
        shoppingCart.customerId = customerId;
        return shoppingCart;
    }

    /**
     * 设置新增购物车时候的默认参数
     *
     * @param customerId 会员id
     * @return 返回购物车
     */
    public OmsShoppingCart setDefaultValuesForAdd(long customerId) {
        this.customerId = customerId;
        this.delFlag = 0;
        return this;
    }

    /**
     * 设置购物车的店铺id
     *
     * @return 返回当前对象
     */
    public OmsShoppingCart setCartStoreId() {
        if (Objects.isNull(sku)) {
            this.storeId = -1L;
        } else {
            this.storeId = sku.getStoreId();
        }
        return this;
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

    /**
     * 获得单品的价格
     *
     * @return 返回单品的价格
     */
    @JsonIgnore
    public BigDecimal getSkuPrice() {
        if (Objects.isNull(sku)) {
            return new BigDecimal(0);
        }
        if (sku.isBatchSku() && !CollectionUtils.isEmpty(sku.getSkuBatchList())) {
            return sku.calculateBatchPrice(num);
        } else {
            return sku.getPrice();
        }
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
     * 获得单品的佣金比例
     *
     * @return 返回单品的佣金比例
     */
    @JsonIgnore
    public BigDecimal getSkuCommissionRate() {
        return Objects.isNull(sku) ? null : sku.getCommissionRate();
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
     * 获得单品的物流模版id
     *
     * @return 返回单品的物流模版id
     */
    public long getLogisticsTemplateId() {
        return Objects.isNull(sku) ? -1 : sku.getLogisticsTemplateId();
    }

    /**
     * 获得单品是否虚拟单品
     *
     * @return 返回单品是否虚拟单品
     */
    public String getIsVirtualSku() {
        return Objects.isNull(sku) ? "0" : sku.getIsVirtual();
    }

    /**
     * 判断是否是众筹
     *
     * @return 是返回true  否则返回false
     */
    @JsonIgnore
    public boolean isCrowdfunding() {
        return Objects.nonNull(crowdfundingId) && !crowdfundingId.equals(0L);
    }

    /**
     * 获取单品批发规则
     */
    @JsonIgnore
    public List<PmsSkuBatch> getSkuBatchList() {
        if (Objects.isNull(sku)) {
            return Collections.emptyList();
        } else {
            return sku.getSkuBatchList();
        }
    }

    /**
     * 获取单品isBatchSku属性
     */
    public String getIsBatchSku() {
        if (Objects.isNull(sku)) {
            return "0";
        } else {
            return sku.getIsBatchSku();
        }
    }

}
