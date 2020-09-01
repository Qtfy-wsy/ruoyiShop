package com.ruoyi.store.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import com.ruoyi.goods.domain.PmsSku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 店铺信息实体类
 *
 * @author 魔金商城 on 2017/6/13.
 */
@Data
@ApiModel(description = "店铺信息实体类")
public class SupplierInfo {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;
    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;
    /**
     * 店铺状态： 0 填写资料中 1 店铺审核中 2 审核通过 3 审核不通过 4 店铺关闭
     */
    @ApiModelProperty(value = "店铺状态： 0 填写资料中 1 店铺审核中 2 审核通过 3 审核不通过 4 店铺关闭")
    private String status;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    /**
     * 公司地址
     */
    @ApiModelProperty(value = "公司地址")
    private String companyAddress;
    /**
     * 公司电话
     */
    @ApiModelProperty(value = "公司电话")
    private String companyPhone;
    /**
     * 公司邮箱
     */
    @ApiModelProperty(value = "公司邮箱")
    private String companyEmail;
    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String contactPerson;
    /**
     * 联系人电话
     */
    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;
    /**
     * 法定代表人
     */
    @ApiModelProperty(value = "法定代表人")
    private String legalPerson;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String cardNo;
    /**
     * 身份证照片
     */
    @ApiModelProperty(value = "身份证照片")
    private String carPic;
    /**
     * 营业执照号
     */
    @ApiModelProperty(value = "营业执照号")
    private String busLicense;
    /**
     * 营业执照图片
     */
    @ApiModelProperty(value = "营业执照图片")
    private String busLicensePic;
    /**
     * 经营范围
     */
    @ApiModelProperty(value = "经营范围")
    private String businessScope;
    /**
     * 组织机构代码图片
     */
    @ApiModelProperty(value = "组织机构代码图片")
    private String orgPic;
    /**
     * 税务登记图片
     */
    @ApiModelProperty(value = "税务登记图片")
    private String taxPic;
    /**
     * 银行开户名
     */
    @ApiModelProperty(value = "银行开户名")
    private String bankUserName;
    /**
     * 公司银行账号
     */
    @ApiModelProperty(value = "公司银行账号")
    private String bankAccount;
    /**
     * 开户银行支行名称
     */
    @ApiModelProperty(value = "开户银行支行名称")
    private String bankName;
    /**
     * 支行银行号
     */
    @ApiModelProperty(value = "支行银行号")
    private String bankNumber;
    /**
     * 开户银行所在地
     */
    @ApiModelProperty(value = "开户银行所在地")
    private String bankAddress;
    /**
     * 开户银行许可证电子版
     */
    @ApiModelProperty(value = "开户银行许可证电子版")
    private String bankPic;
    /**
     * 结算周期
     */
    @ApiModelProperty(value = "结算周期")
    private String billingCycle;
    /**
     * 删除标记 0 未删除 1 删除 默认0
     */
    @ApiModelProperty(value = "删除标记 0 未删除 1 删除 默认0")
    private int delFlag;
    /**
     * 0三证合一 1 三证不合一
     */
    @ApiModelProperty(value = "0三证合一 1 三证不合一")
    private String isMerge;
    /**
     * 客服QQ
     */
    @ApiModelProperty(value = "客服QQ")
    private String serviceQQ;
    /**
     * 拒绝原因
     */
    @ApiModelProperty(value = "拒绝原因")
    private String reason;
    /**
     * 店铺有效期
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "店铺有效期")
    private LocalDateTime effectiveTime = LocalDateTime.now().plusYears(1);
    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime = LocalDateTime.now();
    /**
     * 修改时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;
    /**
     * 删除时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime delTime;

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
     * 用户手机号
     */
    @ApiModelProperty(value = "用户手机号")
    private String customerMobile;

    /**
     * 店铺类型 0 普通店铺 1 加盟店铺 2 连锁店铺
     */
    @ApiModelProperty(value = "店铺类型 0 普通店铺 1 加盟店铺 2 连锁店铺")
    private String type;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    /**
     * 店铺平均评分
     */
    @ApiModelProperty(value = "店铺平均评分")
    private long aveScore;

    /**
     * 营业时间
     */
    @ApiModelProperty(value = "营业时间")
    private String businessTime;

    /**
     * 公交路线
     */
    @ApiModelProperty(value = "公交路线")
    private String busRoutes;

    /**
     * 店铺头像
     */
    @ApiModelProperty(value = "店铺头像")
    private String avatarPicture;

    /**
     * 省id
     */
    @ApiModelProperty(value = "省id")
    private long provinceId;

    /**
     * 市id
     */
    @ApiModelProperty(value = "市id")
    private long cityId;

    /**
     * 区域id
     */
    @ApiModelProperty(value = "区域id")
    private long districtId;

    /**
     * 公司详细地址
     */
    @ApiModelProperty(value = "公司详细地址")
    private String companyDetailAddress;

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
    public SupplierInfo bulidSkus(List<PmsSku> skus) {
        this.skus = skus;
        return this;
    }

    /**
     * 组装商品数量
     *
     * @param skusCount 商品数量
     */
    @JsonIgnore
    public SupplierInfo buildSkusCount(int skusCount) {
        this.skusCount = skusCount;
        return this;
    }

    /**
     * 组装店铺评分
     *
     * @param storeScore 店铺评分
     */
    @JsonIgnore
    public SupplierInfo buildStoreScore(StoreScore storeScore) {
        this.storeScore = storeScore;
        return this;
    }

    public SupplierInfo getStoreInfoToEditStoreName(long storeId, String status, String storeName) {
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
    public SupplierInfo buildAveScore(long aveScore) {
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
}
