package com.ruoyi.member.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 用户收货地址对象 ums_member_address
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
@Data
public class UmsMemberAddress extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id  对应ums_member表中的id
     */
    @Excel(name = "用户id  对应ums_member表中的id")
    private Long customerId;

    /**
     * 收货人姓名
     */
    @Excel(name = "收货人姓名")
    private String name;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String mobile;

    /**
     * 收货人固定电话
     */
    @Excel(name = "收货人固定电话")
    private String phone;

    /**
     * 邮编
     */
    @Excel(name = "邮编")
    private String zipCode;

    /**
     * 收货人地址（省＋市＋区）
     */
    @Excel(name = "收货人地址", readConverterExp = "省=＋市＋区")
    private String address;

    /**
     * 收货人详细地址
     */
    @Excel(name = "收货人详细地址")
    private String detailAddress;

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
     * 区id
     */
    @Excel(name = "区id")
    private Long countryId;

    /**
     * 是否默认收货地址  0 否 1 是  默认0
     */
    @Excel(name = "是否默认收货地址  0 否 1 是  默认0 ")
    private String isDefault;

    /**
     * 删除标记  0 未删除 1 删除
     */
    private int delFlag;

    /**
     * 删除时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "删除时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;
    /**
     * 没有被隐藏的电话号码
     */
    @ApiModelProperty(value = "没有被隐藏的电话号码")
    private String originMobile;
    /**
     * 没有被隐藏的固定电话
     */
    @ApiModelProperty(value = "没有被隐藏的固定电话")
    private String originPhone;

    /**
     * 设置用户id
     *
     * @param customerId 用户id
     * @return 收货地址实体
     */
    @JsonIgnore
    public UmsMemberAddress initCustomerId(Long customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    /**
     * 隐藏手机号码和固话
     */
    @JsonIgnore
    public UmsMemberAddress hideMobileAndPhone() {
        if (!StringUtils.isEmpty(this.mobile)) {
            this.mobile = this.mobile.substring(0, 3) + "****" + this.mobile.substring(7);
        }
        if (!StringUtils.isEmpty(this.phone) && this.phone.length() > 4) {
            this.phone = this.phone.substring(0, phone.length() - 4) + "****";
        }
        return this;
    }
}
