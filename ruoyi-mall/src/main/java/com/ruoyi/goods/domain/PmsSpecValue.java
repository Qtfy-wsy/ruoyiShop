package com.ruoyi.goods.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 规格值对象 pms_spec_value
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsSpecValue extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 规格id 对应pms_goods_spec表中的id
     */
    @Excel(name = "规格id 对应pms_goods_spec表中的id")
    private Long specId;

    /**
     * 规格值的名称
     */
    @Excel(name = "规格值的名称")
    private String name;

    /**
     * 排序  数值越小排序越前
     */
    @Excel(name = "排序  数值越小排序越前")
    private Long sort;

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

    private String url;

    /**
     * 构造删除实体
     *
     * @param specId  规格id
     * @param delName 删除人
     * @return 返回删除实体
     */
    public static PmsSpecValue buildForDelete(long specId, String delName) {
        PmsSpecValue specValue = new PmsSpecValue();
        specValue.specId = specId;
        specValue.delName = delName;
        specValue.delFlag = 1;
        return specValue;
    }

    /**
     * 判断规格值是否发生了变化  主要是名称
     *
     * @param oldSpecValue 老的规格值
     * @return 发生变化返回true  否则返回false
     */
    public boolean hasChange(PmsSpecValue oldSpecValue) {
        return !this.name.equals(oldSpecValue.name);
    }

    /**
     * 设置新增时候的默认值
     *
     * @param name 操作人
     */
    public void setDefaultValuesForAdd(String name) {
        this.createName = name;
        this.delFlag = 0;
    }

    /**
     * 设置自定义的主键id
     *
     * @param index 索引
     */
    public void setCustomId(int index) {
        if (!hasId()) {
            this.id = String.valueOf(System.currentTimeMillis()) + this.specId + index;
        }
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

    /**
     * 构造新增实体
     *
     * @param name 操作人名称
     * @return 返回规格值新增实体
     */
    public PmsSpecValue setDefaultValuesForAdd(PmsSpecValue specValue, String name) {
        specValue.createName = name;
        specValue.delFlag = 0;

        specValue.setCustomId(0);
        return specValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
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
                .append("specId", getSpecId())
                .append("name", getName())
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
