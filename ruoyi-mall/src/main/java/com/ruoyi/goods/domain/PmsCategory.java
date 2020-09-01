package com.ruoyi.goods.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品分类对象 pms_category
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Data
public class PmsCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String name;

    /**
     * 父级分类id   没有父级 则为0
     */
    @Excel(name = "父级分类id   没有父级 则为0")
    private Long parentId;

    /**
     * 类型id，只有分类是三级分类的时候才有 一级和二级分类没有
     */
    @Excel(name = "类型id，只有分类是三级分类的时候才有 一级和二级分类没有")
    private Long typeId;

    /**
     * 分类的层级 1 一级 2 二级 3 三级
     */
    @Excel(name = "分类的层级 1 一级 2 二级 3 三级")
    private int grade;

    /**
     * 分类扣率 三级分类的时候才有，主要是和店铺对账使用
     */
    @Excel(name = "分类扣率 三级分类的时候才有，主要是和店铺对账使用")
    private BigDecimal rate;

    /**
     * 排序  数值越低  排序越前
     */
    @Excel(name = "排序  数值越低  排序越前")
    private Long sort;

    /**
     * 删除标记  0未删除 1删除 默认0
     */
    private int delFlag;

    /**
     * 创建者名称
     */
    @Excel(name = "创建者名称")
    private String createName;

    /**
     * 修改者名称
     */
    @Excel(name = "修改者名称")
    private String modifyName;

    /**
     * 删除者名称
     */
    @Excel(name = "删除者名称")
    private String delName;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /**
     * 删除时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "删除时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /**
     * 子分类
     */
    private List<PmsCategory> childCateGory;

    /**
     * 三级分类关联的规格
     */
    private List<PmsCategorySpec> cateSpecs;

    /**
     * 父级分类
     */
    @JsonIgnore
    private PmsCategory parentCategory;

    /**
     * 构造删除实体
     *
     * @param id   分类id
     * @param name 操作人
     * @return 返回删除实体
     */
    public static PmsCategory buildForDelete(long id, String name) {
        PmsCategory category = new PmsCategory();
        category.id = id;
        category.delFlag = 1;
        category.delTime = new Date();
        return category;
    }

    /**
     * 设置添加分类的默认值
     *
     * @param name 操作人
     * @return 返回分类
     */
    public PmsCategory setDefaultsForAdd(String name) {
        this.createName = name;
        this.delFlag = 0;
        return this;
    }

    /**
     * 设置修改分类的默认值
     *
     * @param name 操作人
     * @return 返回分类
     */
    public PmsCategory setDefaultValuesForModify(String name) {
        this.modifyTime = new Date();
        return this;
    }

    /**
     * 是否一级分类
     *
     * @return 是返回true  否返回false
     */
    @JsonIgnore
    public boolean isFirstCategory() {
        return this.grade == 1;
    }

    /**
     * 是否三级分类
     *
     * @return 是返回true 否返回false
     */
    @JsonIgnore
    public boolean isThirdCategory() {
        return this.grade == 3;
    }

    /**
     * 设置分类与规格关联的分类id
     */
    public void setCateSpecTypeId() {
        if (CollectionUtils.isEmpty(this.cateSpecs)) {
            return;
        }
        cateSpecs.stream().forEach(typeSpec -> typeSpec.setCateId(this.id));
    }

    public List<PmsCategory> getChildCateGory() {
        return childCateGory;
    }

    public PmsCategory setChildCateGory(List<PmsCategory> cateGory) {
        this.childCateGory = cateGory;
        return this;
    }


    public List<PmsCategorySpec> getCateSpecs() {
        return cateSpecs;
    }

    public void setCateSpecs(List<PmsCategorySpec> cateSpecs) {
        this.cateSpecs = cateSpecs;
    }

    public PmsCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(PmsCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public String getDelName() {
        return delName;
    }

    public void setDelName(String delName) {
        this.delName = delName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("parentId", getParentId())
                .append("typeId", getTypeId())

                .append("rate", getRate())
                .append("sort", getSort())
                .append("delFlag", getDelFlag())
                .append("createName", getCreateName())
                .append("modifyName", getModifyName())
                .append("delName", getDelName())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("delTime", getDelTime())
                .toString();
    }
}
