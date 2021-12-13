package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 品牌对象 pms_brand
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsBrand extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 品牌名称
     */
    @Excel(name = "品牌名称")
    private String name;

    /**
     * 品牌别名
     */
    @Excel(name = "品牌别名")
    private String nickName;

    /**
     * 品牌的图片地址
     */
    @Excel(name = "品牌的图片地址")
    private String url;

    /**
     * 证书图片
     */
    @Excel(name = "证书图片")
    private String certificatUrl;

    /**
     * 店铺id 平台的为0
     */
    @Excel(name = "店铺id 平台的为0 ")
    private Long storeId;

    /**
     * 状态  0 申请中  1通过 2 拒绝
     */
    @Excel(name = "状态  0 申请中  1通过 2 拒绝")
    private String status;

    /**
     * 删除标记 0 未删除 1删除 默认0
     */
    private int delFlag;

    /**
     * 拒绝原因
     */
    @Excel(name = "拒绝原因")
    private String reason;

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
    private Date modifyTime;

    /**
     * 删除时间
     */
    private Date delTime;

    /**
     * 品牌所属的店铺名称
     */
    @ApiModelProperty(value = "品牌所属的店铺名称")
    private String storeName;

    /**
     * 构造删除品牌实体
     *
     * @param id      品牌id
     * @param delName 操作人
     * @param storeId 店铺id
     * @return 返回删除品牌实体
     */
    public static PmsBrand buildDeleteBrand(long id, String delName, long storeId) {
        PmsBrand brand = new PmsBrand();
        brand.id = id;
        brand.delFlag = 1;
        brand.delName = delName;
        brand.delTime = new Date();
        brand.storeId = storeId;
        return brand;
    }

    /**
     * 设置新增品牌默认参数
     *
     * @param name    操作人
     * @param storeId 店铺id
     */
    public PmsBrand setDefaultValuesForAdd(String name, long storeId) {
        this.createName = name;
        this.delFlag = 0;
        this.storeId = storeId;

        // 如果品牌为平台的则不需要审核直接通过
        if (0 == storeId) {
            this.status = "1";
        } else {
            this.status = "0";
        }

        return this;
    }

    /**
     * 设置修改品牌默认参数
     *
     * @param name    操作人
     * @param storeId 店铺id
     */
    public PmsBrand setDefaultValuesForModify(String name, long storeId) {
        this.storeId = storeId;
        this.modifyName = name;
        this.modifyTime = new Date();
        return this;
    }

    public PmsBrand addMySelfBrand(String name, String url, String certificatUrl, long storeId, String createName) {
        this.name = name;
        this.url = url;
        this.certificatUrl = certificatUrl;
        this.storeId = storeId;
        this.status = "0";
        this.delFlag = 0;
        this.createName = createName;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCertificatUrl() {
        return certificatUrl;
    }

    public void setCertificatUrl(String certificatUrl) {
        this.certificatUrl = certificatUrl;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
                .append("nickName", getNickName())
                .append("url", getUrl())
                .append("certificatUrl", getCertificatUrl())
                .append("storeId", getStoreId())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("reason", getReason())
                .append("createName", getCreateName())
                .append("modifyName", getModifyName())
                .append("delName", getDelName())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("delTime", getDelTime())
                .toString();
    }
}
