package com.ruoyi.goods.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 规格对象 pms_spec
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsSpec extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 规格名称
     */
    @Excel(name = "规格名称")
    private String name;

    /**
     * 规格别名
     */
    @Excel(name = "规格别名")
    private String nickName;

    /**
     * 删除标记 0 未删除  1 删除 默认0
     */
    private int delFlag;

    /**
     * 创建者名称
     */
    @Excel(name = "创建者名称")
    private String createName;

    /**
     * 修改人
     */
    @Excel(name = "修改人")
    private String modifyName;

    /**
     * 删除人名称
     */
    @Excel(name = "删除人名称")
    private String delName;

    /**
     * 删除时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "删除时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    private List<PmsSpecValue> specValues;

    /**
     * 构造删除实体
     *
     * @param id      删除规格的id
     * @param delName 删除人
     * @return 返回删除规格的实体
     */
    public static PmsSpec buildForDelete(long id, String delName) {
        PmsSpec spec = new PmsSpec();
        spec.id = id;
        spec.delFlag = 1;
        spec.delName = delName;
        return spec;
    }

    /**
     * 设置新增的默认值
     *
     * @param name 操作人名称
     */
    public PmsSpec setDetaultValuesForAdd(String name) {
        this.createName = name;
        this.delFlag = 0;

        if (!CollectionUtils.isEmpty(this.specValues)) {
            specValues.stream().forEach(specValue -> specValue.setDefaultValuesForAdd(name));
        }
        return this;
    }

    /**
     * 设置修改的默认值
     *
     * @param name 操作人
     */
    public PmsSpec setDetaultValuesForUpdate(String name) {
        this.modifyName = name;
        this.modifyTime = new Date();

        if (!CollectionUtils.isEmpty(this.specValues)) {
            specValues.stream().forEach(specValue -> specValue.setDefaultValuesForAdd(name));
        }
        return this;
    }

    /**
     * 判断规格名称和别名是否改变
     *
     * @param newSpec 新的规格
     * @return 一样返回false 不一样返回true
     */
    public boolean hasNameChange(PmsSpec newSpec) {
        if (Objects.isNull(newSpec)) {
            return false;
        }

        return !this.name.equals(newSpec.name) || !this.nickName.equals(newSpec.nickName);
    }

    public List<PmsSpecValue> getSpecValues() {
        return specValues;
    }

    public void setSpecValues(List<PmsSpecValue> specValues) {
        this.specValues = specValues;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("nickName", getNickName())
                .append("delFlag", getDelFlag())
                .append("createName", getCreateName())
                .append("modifyName", getModifyName())
                .append("delName", getDelName())
                .append("createTime", getCreateTime())
                .append("delTime", getDelTime())
                .append("modifyTime", getModifyTime())
                .toString();
    }
}
