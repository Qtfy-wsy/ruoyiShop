package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 商品和规格值的关联对象 pms_goods_spec_value
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public class PmsGoodsSpecValue extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品id 对应pms_goods表中的id
     */
    @Excel(name = "商品id 对应pms_goods表中的id")
    private Long spuId;

    /**
     * 规格id  对应pms_spec表中的id
     */
    @Excel(name = "规格id  对应pms_spec表中的id")
    private Long specId;

    /**
     * 规格值id  对应pms_spec_value表中的id
     */
    @Excel(name = "规格值id  对应pms_spec_value表中的id")
    private String specValueId;

    /**
     * 规格值的图片地址
     */
    @Excel(name = "规格值的图片地址")
    private String url;

    /**
     * 规格值
     */
    @Excel(name = "规格值")
    private String valueRemark;

    /**
     * 删除标记  0 未删除 1 删除 默认0
     */
    private int delFlag;

    /**
     * 规格信息
     */

    private PmsSpec spec;

    public PmsSpec getSpec() {
        return spec;
    }

    /**
     * 设置规格
     *
     * @param spec 规格信息
     * @return 返回当前对象
     */
    public PmsGoodsSpecValue setSpec(PmsSpec spec) {
        this.spec = spec;
        return this;
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

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    public String getSpecValueId() {
        return specValueId;
    }

    public void setSpecValueId(String specValueId) {
        this.specValueId = specValueId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValueRemark() {
        return valueRemark;
    }

    public void setValueRemark(String valueRemark) {
        this.valueRemark = valueRemark;
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
                .append("specId", getSpecId())
                .append("specValueId", getSpecValueId())
                .append("url", getUrl())
                .append("valueRemark", getValueRemark())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
