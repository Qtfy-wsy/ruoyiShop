package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 商品导入对象 pms_goods_import
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public class PmsGoodsImport extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String name;

    /**
     * 商品副标题
     */
    @Excel(name = "商品副标题")
    private String subTitle;

    /**
     * 商品价格
     */
    @Excel(name = "商品价格")
    private BigDecimal price;

    /**
     * see标题
     */
    @Excel(name = "see标题")
    private String seoTitle;

    /**
     * seo关键字
     */
    @Excel(name = "seo关键字")
    private String seoKeywords;

    /**
     * see描述
     */
    @Excel(name = "see描述")
    private String seoDesc;

    /**
     * 是否发布 0 未发布 1 发布 默认0
     */
    @Excel(name = "是否发布 0 未发布 1 发布 默认0 ")
    private String isRelease;

    /**
     * 删除标记 0 未删除 1 删除 默认0
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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getSeoDesc() {
        return seoDesc;
    }

    public void setSeoDesc(String seoDesc) {
        this.seoDesc = seoDesc;
    }

    public String getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(String isRelease) {
        this.isRelease = isRelease;
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
                .append("subTitle", getSubTitle())
                .append("price", getPrice())
                .append("seoTitle", getSeoTitle())
                .append("seoKeywords", getSeoKeywords())
                .append("seoDesc", getSeoDesc())
                .append("isRelease", getIsRelease())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
