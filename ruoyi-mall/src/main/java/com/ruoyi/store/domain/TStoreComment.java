package com.ruoyi.store.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 店铺评论对象 t_store_comment
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Data
public class TStoreComment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 店铺id
     */
    @Excel(name = "店铺id")
    private Long storeId;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private Long orderId;

    /**
     * 会员id
     */
    @Excel(name = "会员id")
    private Long customerId;

    /**
     * 描述相符 评分1到5分
     */
    @Excel(name = "描述相符 评分1到5分")
    private Long descScore;

    /**
     * 卖家服务评分 1到5分
     */
    @Excel(name = "卖家服务评分 1到5分")
    private Long sellerScore;

    /**
     * 物流评分 1到5分
     */
    @Excel(name = "物流评分 1到5分")
    private Long logisticsScore;

    /**
     * 店铺名称
     */
    private String storeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getDescScore() {
        return descScore;
    }

    public void setDescScore(Long descScore) {
        this.descScore = descScore;
    }

    public Long getSellerScore() {
        return sellerScore;
    }

    public void setSellerScore(Long sellerScore) {
        this.sellerScore = sellerScore;
    }

    public Long getLogisticsScore() {
        return logisticsScore;
    }

    public void setLogisticsScore(Long logisticsScore) {
        this.logisticsScore = logisticsScore;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("storeId", getStoreId())
                .append("orderId", getOrderId())
                .append("customerId", getCustomerId())
                .append("descScore", getDescScore())
                .append("sellerScore", getSellerScore())
                .append("logisticsScore", getLogisticsScore())
                .append("createTime", getCreateTime())
                .toString();
    }
}
