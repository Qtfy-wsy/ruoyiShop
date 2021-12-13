package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 分类和规格的关联对象 pms_category_spec
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsCategorySpec extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 分类id  对应pms_category表中的id
     */
    @Excel(name = "分类id  对应pms_category表中的id")
    private Long cateId;

    /**
     * 规格id 对应pms_goods_spec 表中的id
     */
    @Excel(name = "规格id 对应pms_goods_spec 表中的id")
    private Long specId;

    /**
     * 删除标记 0 未删除 1删除 默认0
     */
    private int delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
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
                .append("cateId", getCateId())
                .append("specId", getSpecId())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
