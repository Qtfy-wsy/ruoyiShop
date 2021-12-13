package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 品牌申请对象 pms_brand_apply
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsBrandApply extends BaseEntity {
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
     * 申请的品牌id
     */
    @Excel(name = "申请的品牌id")
    private Long brandId;

    /**
     * 申请状态
     */
    @Excel(name = "申请状态 ")
    private String status;

    /**
     * 拒绝原因
     */
    @Excel(name = "拒绝原因")
    private String reason;

    /**
     * 品牌信息
     */
    private PmsBrand brand;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 获取品牌申请对象
     *
     * @param storeId 店铺id
     * @param brandId 品牌id
     * @return 品牌申请对象
     */
    public PmsBrandApply getBrandApply(long storeId, long brandId) {
        this.storeId = storeId;
        this.brandId = brandId;
        return this;
    }

    public PmsBrand getBrand() {
        return brand;
    }

    public void setBrand(PmsBrand brand) {
        this.brand = brand;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("storeId", getStoreId())
                .append("brandId", getBrandId())
                .append("status", getStatus())
                .append("reason", getReason())
                .append("createTime", getCreateTime())
                .toString();
    }
}
