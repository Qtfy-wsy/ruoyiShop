package com.ruoyi.setting.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 区域市对象 ls_city
 *
 * @author 魔金商城
 * @date 2020-07-29
 */
@Setter
@Getter
public class LsCity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 市的名称
     */
    @Excel(name = "市的名称")
    private String name;

    /**
     * 该市所属的省份id  对应ls_province 中的id
     */
    @Excel(name = "该市所属的省份id  对应ls_province 中的id")
    private Long provinceId;

    /**
     * 排序  数值越小 排序越前
     */
    @Excel(name = "排序  数值越小 排序越前")
    private Long sort;

    /**
     * 删除标记 0未删除 1删除 默认0
     */
    private int delFlag;

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
     * 市下面的区
     */
    @ApiModelProperty(value = "市下面的区")
    private List<LsDistrict> child;


    /**
     * 设置添加市的默认
     *
     * @return 返回市
     */
    public LsCity setDefaultValuesForAdd() {
        this.delFlag = 0;
        return this;
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

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
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
                .append("provinceId", getProvinceId())
                .append("sort", getSort())
                .append("delFlag", getDelFlag())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("delTime", getDelTime())
                .toString();
    }
}
