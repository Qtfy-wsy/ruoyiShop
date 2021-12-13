package com.ruoyi.goods.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 商品类型对象 pms_type
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 类型名称
     */
    @Excel(name = "类型名称")
    private String name;

    /**
     * 删除标记 0 未删除 1删除 默认0
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
     * 类型关联的属性
     */
    private List<PmsAttribute> attributes;

    /**
     * 构造删除的实体
     *
     * @param name 操作人
     * @return 返回删除实体
     */
    public static PmsType buildForDelete(long id, String name) {
        PmsType type = new PmsType();
        type.id = id;
        type.delFlag = 1;
        type.delName = name;
        type.delTime = new Date();
        return type;
    }

    /**
     * 设置属性的类型id
     */
    public void setAttributesTypeId() {
        if (CollectionUtils.isEmpty(this.attributes)) {
            return;
        }
        IntStream.range(0, this.attributes.size()).forEach(index -> this.attributes.get(index).setCustomIdAndTypeId(this.id, index));
    }

    /**
     * 设置新增时候的默认值
     *
     * @param name 操作人
     */
    public PmsType setDefaultValuesForAdd(String name) {
        this.createName = name;
        this.delFlag = 0;
        return this;
    }

    /**
     * 设置修改时候的默认值
     *
     * @param name 操作人
     * @return 返回类型信息
     */
    public PmsType setDefalutValuesForModify(String name) {
        this.modifyName = name;
        this.modifyTime = new Date();
        return this;
    }

    public List<PmsAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<PmsAttribute> attributes) {
        this.attributes = attributes;
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
