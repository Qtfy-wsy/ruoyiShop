package com.ruoyi.goods.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 商品属性对象 pms_attribute
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsAttribute extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 商品属性关联的类型id 对应ls_type表中的id
     */
    @Excel(name = "商品属性关联的类型id 对应ls_type表中的id")
    private Long typeId;

    /**
     * 属性名称
     */
    @Excel(name = "属性名称")
    private String name;

    /**
     * 排序 数值越小 排序越前
     */
    @Excel(name = "排序 数值越小 排序越前")
    private Long sort;

    /**
     * 删除标记 0 未删除 1 删除 默认0
     */
    private int delFlag;

    /**
     * 属性关联的属性值
     */
    private List<PmsAttributeValue> attributeValues;

    /**
     * 设置自定义id 和 类型id
     *
     * @param typeId 类型id
     * @param index  索引
     */
    public void setCustomIdAndTypeId(long typeId, int index) {
        this.typeId = typeId;
        setCustomId(index);
    }

    /**
     * 设置自定义id
     *
     * @param index 索引
     */
    public void setCustomId(int index) {
        if (!hasId()) {
            this.id = String.valueOf(System.currentTimeMillis()) + this.typeId + index;
        }
    }

    /**
     * 设置属性值的id
     */
    public void setAttributeValuesId() {
        if (CollectionUtils.isEmpty(this.attributeValues)) {
            return;
        }
        IntStream.range(0, this.attributeValues.size()).forEach(index -> this.attributeValues.get(index).setCustomIdAndAttributeId(this.id, index, this.typeId));
    }

    /**
     * 判断是否已经存在主键
     *
     * @return 存在返回true  不存在返回false
     */
    @JsonIgnore
    public boolean hasId() {
        return !StringUtils.isEmpty(this.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PmsAttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<PmsAttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
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
                .append("typeId", getTypeId())
                .append("name", getName())
                .append("sort", getSort())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
