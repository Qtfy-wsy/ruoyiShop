package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * 单品和图片的关联对象 pms_sku_image
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public class PmsSkuImage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品id
     */
    @Excel(name = "商品id")
    private Long spuId;

    /**
     * 单品id  对应pms_sku表中的id
     */
    @Excel(name = "单品id  对应pms_sku表中的id")
    private String skuId;

    /**
     * 图片地址
     */
    @Excel(name = "图片地址")
    private String url;

    /**
     * 默认图片标记  0 不是默认 1默认    默认为0
     */
    @Excel(name = "默认图片标记  0 不是默认 1默认    默认为0")
    private String defaultFlag;

    /**
     * 删除标记  0未删除 1删除 默认0
     */
    private int delFlag;

    /**
     * 构造单品图片
     *
     * @param sku 单品信息
     * @return 返回单品图片
     */
    public static PmsSkuImage build(PmsSku sku) {
        if (Objects.isNull(sku)) {
            return null;
        }
        PmsSkuImage skuImage = new PmsSkuImage();
        skuImage.url = sku.getUrl();
        skuImage.skuId = sku.getId();
        return skuImage;
    }

    /**
     * 设置单品id和商品id
     *
     * @param skuId 单品id
     * @param spuId 商品id
     */
    public void setSkuIdAndSpuId(String skuId, long spuId) {
        this.skuId = skuId;
        this.spuId = spuId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
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
                .append("spuId", getSpuId())
                .append("skuId", getSkuId())
                .append("url", getUrl())
                .append("defaultFlag", getDefaultFlag())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
