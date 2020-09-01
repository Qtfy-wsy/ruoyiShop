package com.ruoyi.store.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 店铺自定义品牌列对象 t_store_customize_brand
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public class TStoreCustomizeBrand extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 品牌名称
     */
    @Excel(name = "品牌名称")
    private String name;

    /**
     * 品牌的图片
     */
    @Excel(name = "品牌的图片")
    private String url;

    /**
     * 证书图片
     */
    @Excel(name = "证书图片")
    private String certificatUrl;

    /**
     * 店铺id
     */
    @Excel(name = "店铺id")
    private String storeId;

    /**
     * 对应品牌表中的id
     */
    @Excel(name = "对应品牌表中的id")
    private Long brandId;

    /**
     * 状态  0 申请中  1通过 2 拒绝
     */
    @Excel(name = "状态  0 申请中  1通过 2 拒绝")
    private String status;

    /**
     * 拒绝原因
     */
    @Excel(name = "拒绝原因")
    private String reason;

    /**
     * 删除标记  0 未删除 1 删除默认0
     */
    private int delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCertificatUrl() {
        return certificatUrl;
    }

    public void setCertificatUrl(String certificatUrl) {
        this.certificatUrl = certificatUrl;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
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

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("url", getUrl())
                .append("certificatUrl", getCertificatUrl())
                .append("storeId", getStoreId())
                .append("brandId", getBrandId())
                .append("status", getStatus())
                .append("reason", getReason())
                .append("delFlag", getDelFlag())
                .append("createTime", getCreateTime())
                .toString();
    }
}
