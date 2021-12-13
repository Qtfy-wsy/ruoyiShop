package com.ruoyi.store.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.store.vo.StoreScore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 店铺信息对象 t_store_info
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Data
public class TStoreInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称")
    private String storeName;

    /**
     * 店铺状态
     * 0 填写资料中
     * 1 店铺审核中
     * 2 审核通过
     * 3 审核不通过
     * 4 店铺关闭
     */
    @Excel(name = "店铺状态")
    private String status;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 公司地址
     */
    @Excel(name = "公司地址")
    private String companyAddress;

    /**
     * 公司电话
     */
    @Excel(name = "公司电话")
    private String companyPhone;

    /**
     * 公司邮箱
     */
    @Excel(name = "公司邮箱")
    private String companyEmail;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contactPerson;

    /**
     * 联系人电话
     */
    @Excel(name = "联系人电话")
    private String contactPhone;

    /**
     * 法定代表人
     */
    @Excel(name = "法定代表人")
    private String legalPerson;

    /**
     * 身份证号码
     */
    @Excel(name = "身份证号码")
    private String cardNo;

    /**
     * 身份证照片
     */
    @Excel(name = "身份证照片")
    private String cardPic;

    /**
     * 营业执照号
     */
    @Excel(name = "营业执照号")
    private String busLicenec;

    /**
     * 营业执照图片
     */
    @Excel(name = "营业执照图片")
    private String busLicenecPic;

    /**
     * 经营范围
     */
    @Excel(name = "经营范围")
    private String businessScope;

    /**
     * 组织机构代码图片
     */
    @Excel(name = "组织机构代码图片")
    private String orgPic;

    /**
     * 税务登记图片
     */
    @Excel(name = "税务登记图片")
    private String taxPic;

    /**
     * 银行开户名
     */
    @Excel(name = "银行开户名")
    private String bankUserName;

    /**
     * 公司银行账号
     */
    @Excel(name = "公司银行账号")
    private String bankAccount;

    /**
     * 开户银行支行名称
     */
    @Excel(name = "开户银行支行名称")
    private String bankName;

    /**
     * 支行银行号
     */
    @Excel(name = "支行银行号")
    private String bankNumber;

    /**
     * 开户银行所在地
     */
    @Excel(name = "开户银行所在地")
    private String bankAddress;

    /**
     * 开户银行许可证电子版
     */
    @Excel(name = "开户银行许可证电子版")
    private String bankPic;

    /**
     * 结算周期
     */
    @Excel(name = "结算周期")
    private String billingCycle;

    /**
     * 0三证合一 1 三证不合一
     */
    @Excel(name = "0三证合一 1 三证不合一")
    private String ismerge;

    /**
     * 删除标记 0 未删除 1 删除 默认0
     */
    private int delFlag;

    /**
     * 客服QQ
     */
    @Excel(name = "客服QQ")
    private String serviceQq;

    /**
     * 拒绝原因
     */
    @Excel(name = "拒绝原因")
    private String reason;

    /**
     * 店铺评分
     */
    @Excel(name = "店铺评分")
    private long aveScore;

    /**
     * 店铺有效期
     */
    @Excel(name = "店铺有效期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "店铺有效期")
    private LocalDateTime effectiveTime = LocalDateTime.now().plusYears(1);

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
     * 店铺类型 0 普通店铺 1 加盟 2连锁
     */
    @Excel(name = "店铺类型 0 普通店铺 1 加盟 2连锁")
    private String type;

    /**
     * 经度
     */
    @Excel(name = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @Excel(name = "纬度")
    private BigDecimal latitude;

    /**
     * 营业时间
     */
    @Excel(name = "营业时间")
    private String businessTime;

    /**
     * 公交线路
     */
    @Excel(name = "公交线路")
    private String busRoutes;

    /**
     * 店铺头像
     */
    @Excel(name = "店铺头像")
    private String avatarPicture;

    /**
     * 省id
     */
    @Excel(name = "省id")
    private Long provinceId;

    /**
     * 市id
     */
    @Excel(name = "市id")
    private Long cityId;

    /**
     * 区域id
     */
    @Excel(name = "区域id")
    private Long districtId;

    /**
     * 公司详细地址
     */
    @Excel(name = "公司详细地址")
    private String companyDetailAddress;


    /**
     * 评论数
     */
    @ApiModelProperty(value = "评论数")
    private int commentCount;

    /**
     * 单品销量
     */
    @ApiModelProperty(value = "单品销量")
    private int saleCount;

    /**
     * 店铺评分
     */
    @ApiModelProperty(value = "店铺评分")
    private StoreScore storeScore;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private int skusCount;

    /**
     * 搜索店铺时的展示商品
     */
    @ApiModelProperty(value = "搜索店铺时的展示商品")
    private List<PmsSku> skus;

    /**
     * 关注人数
     */
    @ApiModelProperty(value = "关注人数")
    private int followNum;

    /**
     * 到店价（门店列表页使用）
     */
    @ApiModelProperty(value = "到店价（门店列表页使用）")
    private BigDecimal reachPrice;

    /**
     * 距离
     */
    @ApiModelProperty(value = "距离")
    private BigDecimal distance;


    /**
     * 组装展示商品
     *
     * @param skus 商品集合
     */
    @JsonIgnore
    public TStoreInfo bulidSkus(List<PmsSku> skus) {
        this.skus = skus;
        return this;
    }

    /**
     * 组装商品数量
     *
     * @param skusCount 商品数量
     */
    @JsonIgnore
    public TStoreInfo buildSkusCount(int skusCount) {
        this.skusCount = skusCount;
        return this;
    }

    /**
     * 组装店铺评分
     *
     * @param storeScore 店铺评分
     */
    @JsonIgnore
    public TStoreInfo buildStoreScore(StoreScore storeScore) {
        this.storeScore = storeScore;
        return this;
    }

    public TStoreInfo getStoreInfoToEditStoreName(long storeId, String status, String storeName) {
        this.id = storeId;
        this.status = status;
        this.storeName = storeName;
        return this;
    }

    /**
     * 构建门店评分
     *
     * @param aveScore 评分
     * @return 店铺信息实体
     */
    public TStoreInfo buildAveScore(long aveScore) {
        this.aveScore = aveScore;
        return this;
    }

    /**
     * 判断是否为门店
     *
     * @return 门店返回true, 否则返回false
     */
    public boolean isOutLetStore() {
        return "1".equals(this.type) || "2".equals(this.type);
    }


    /**
     * 判断店铺是否有效
     *
     * @return 有效返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isEffective() {
        return Objects.nonNull(effectiveTime) && isOpen() && LocalDateTime.now().isBefore(effectiveTime);
    }

    /**
     * 判断店铺是否为开店状态
     *
     * @return 开店状态返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isOpen() {
        return "2".equals(status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardPic() {
        return cardPic;
    }

    public void setCardPic(String cardPic) {
        this.cardPic = cardPic;
    }

    public String getBusLicenec() {
        return busLicenec;
    }

    public void setBusLicenec(String busLicenec) {
        this.busLicenec = busLicenec;
    }

    public String getBusLicenecPic() {
        return busLicenecPic;
    }

    public void setBusLicenecPic(String busLicenecPic) {
        this.busLicenecPic = busLicenecPic;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getOrgPic() {
        return orgPic;
    }

    public void setOrgPic(String orgPic) {
        this.orgPic = orgPic;
    }

    public String getTaxPic() {
        return taxPic;
    }

    public void setTaxPic(String taxPic) {
        this.taxPic = taxPic;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankPic() {
        return bankPic;
    }

    public void setBankPic(String bankPic) {
        this.bankPic = bankPic;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public String getIsmerge() {
        return ismerge;
    }

    public void setIsmerge(String ismerge) {
        this.ismerge = ismerge;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getServiceQq() {
        return serviceQq;
    }

    public void setServiceQq(String serviceQq) {
        this.serviceQq = serviceQq;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public String getBusRoutes() {
        return busRoutes;
    }

    public void setBusRoutes(String busRoutes) {
        this.busRoutes = busRoutes;
    }

    public String getAvatarPicture() {
        return avatarPicture;
    }

    public void setAvatarPicture(String avatarPicture) {
        this.avatarPicture = avatarPicture;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getCompanyDetailAddress() {
        return companyDetailAddress;
    }

    public void setCompanyDetailAddress(String companyDetailAddress) {
        this.companyDetailAddress = companyDetailAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("storeName", getStoreName())
                .append("status", getStatus())
                .append("companyName", getCompanyName())
                .append("companyAddress", getCompanyAddress())
                .append("companyPhone", getCompanyPhone())
                .append("companyEmail", getCompanyEmail())
                .append("contactPerson", getContactPerson())
                .append("contactPhone", getContactPhone())
                .append("legalPerson", getLegalPerson())
                .append("cardNo", getCardNo())
                .append("cardPic", getCardPic())
                .append("busLicenec", getBusLicenec())
                .append("busLicenecPic", getBusLicenecPic())
                .append("businessScope", getBusinessScope())
                .append("orgPic", getOrgPic())
                .append("taxPic", getTaxPic())
                .append("bankUserName", getBankUserName())
                .append("bankAccount", getBankAccount())
                .append("bankName", getBankName())
                .append("bankNumber", getBankNumber())
                .append("bankAddress", getBankAddress())
                .append("bankPic", getBankPic())
                .append("billingCycle", getBillingCycle())
                .append("ismerge", getIsmerge())
                .append("delFlag", getDelFlag())
                .append("serviceQq", getServiceQq())
                .append("reason", getReason())
                .append("aveScore", getAveScore())
                .append("effectiveTime", getEffectiveTime())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("delTime", getDelTime())
                .append("type", getType())
                .append("longitude", getLongitude())
                .append("latitude", getLatitude())
                .append("businessTime", getBusinessTime())
                .append("busRoutes", getBusRoutes())
                .append("avatarPicture", getAvatarPicture())
                .append("provinceId", getProvinceId())
                .append("cityId", getCityId())
                .append("districtId", getDistrictId())
                .append("companyDetailAddress", getCompanyDetailAddress())
                .toString();
    }
}
