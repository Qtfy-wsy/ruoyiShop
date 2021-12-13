package com.ruoyi.store.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 门店预约对象 t_store_reservation
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Data
public class TStoreReservation extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private long customerId;

    /**
     * 会员手机号
     */
    @ApiModelProperty(value = "会员手机号")
    private String mobile;

    /**
     * 单品id
     */
    @ApiModelProperty(value = "单品id")
    private String skuId;

    /**
     * 预约数量
     */
    @ApiModelProperty(value = "预约数量")
    private int num;

    /**
     * 删除标记 0 未删除 1 已删除 默认0
     */
    @ApiModelProperty(value = " 删除标记 0 未删除 1 已删除 默认0")
    private int delFlag;


    /**
     * 单品的门店价格
     */
    @ApiModelProperty(value = "单品的门店价格")
    private BigDecimal price;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 单品名称
     */
    @ApiModelProperty(value = "单品名称")
    private String skuName;

    /**
     * 单品图片url
     */
    @ApiModelProperty(value = "单品图片url")
    private String url;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private int stock;

    /**
     * 门店预约总价格（单品价格 * 预约数量）
     *
     * @return 总价格
     */
    public BigDecimal getTotalPrice() {
        return price.multiply(new BigDecimal(num));
    }

    /**
     * 构建门店id
     *
     * @param storeId 门店id
     * @return 门店预约实体
     */
    public TStoreReservation buildStoreId(long storeId) {
        this.storeId = storeId;
        return this;
    }


    /**
     * 构建用户手机号
     *
     * @param mobile 手机号
     * @return 门店预约实体
     */
    public TStoreReservation buildMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    /**
     * 构建用户id
     *
     * @param customerId 用户id
     * @return 门店预约实体
     */
    public TStoreReservation buildCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }

}
