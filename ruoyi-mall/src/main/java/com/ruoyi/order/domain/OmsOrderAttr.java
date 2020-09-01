package com.ruoyi.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 订单属性对象 oms_order_attr
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public class OmsOrderAttr extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单id  对应oms_order 表中的order_code
     */
    @Excel(name = "订单id  对应oms_order 表中的order_code")
    private Long orderId;

    /**
     * 收货人姓名
     */
    @Excel(name = "收货人姓名")
    private String receiptName;

    /**
     * 收货人的地址 （省＋市＋区）
     */
    @Excel(name = "收货人的地址 ", readConverterExp = "省=＋市＋区")
    private String receiptAddress;

    /**
     * 收货人的详细地址
     */
    @Excel(name = "收货人的详细地址")
    private String receiptDetailAddress;

    /**
     * 收货人的手机号码
     */
    @Excel(name = "收货人的手机号码")
    private String receiptMobile;

    /**
     * 收货人的固定电话
     */
    @Excel(name = "收货人的固定电话")
    private String receiptPhone;

    /**
     * 收货人的邮编
     */
    @Excel(name = "收货人的邮编")
    private String receiptZipCode;

    /**
     * 发票类型  0 不需要发票 1增值税普票 2增值税专票 默认0
     */
    @Excel(name = "发票类型  0 不需要发票 1增值税普票 2增值税专票 默认0")
    private String invoiceType;

    /**
     * 发票抬头
     */
    @Excel(name = "发票抬头")
    private String invoiceTitle;

    /**
     * 发票内容
     * 1:非图书商品
     * 2:明细
     * 3:耗材
     * 4:办公用品
     */
    @Excel(name = "发票内容")
    private String invoiceContent;

    /**
     * 税号
     */
    @Excel(name = "税号")
    private String invoiceTaxid;

    /**
     * 订单的赠品信息格式为
     * [
     * {
     * "num": 1,
     * "skuId": "15096899899251410",
     * "skuName": "平台商品(规格5)",
     * "skuNo": "201711031419380",
     * "specs": "版本:规格5|",
     * "url": "http://lecshop.b0.upaiyun.com/1509689972311.jpg"
     * }
     * ]
     */
    @Excel(name = "订单的赠品信息格式为")
    private String giftInfos;

    /**
     * 捐赠寄语
     */
    @Excel(name = "捐赠寄语")
    private String donationMessage;

    /**
     * 单位名称
     */
    @Excel(name = "单位名称")
    private String invoiceCompanyName;

    /**
     * 注册地址
     */
    @Excel(name = "注册地址")
    private String invoiceRegisterAddress;

    /**
     * 注册电话
     */
    @Excel(name = "注册电话")
    private String invoiceRegisterMobile;

    /**
     * 开户银行
     */
    @Excel(name = "开户银行")
    private String invoiceOpenBank;

    /**
     * 银行账户
     */
    @Excel(name = "银行账户")
    private String invoiceBankAccount;

    /**
     * 抬头类型  1 企业  2 个人
     */
    @Excel(name = "抬头类型  1 企业  2 个人 ")
    private String invoiceTitleType;

    /**
     * 送达时间 社区团购使用
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "送达时间 社区团购使用", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryTime;

    /**
     * 所在地区 省+市+区 社区团购使用
     */
    @Excel(name = "所在地区 省+市+区 社区团购使用")
    private String address;

    /**
     * 详细信息 取货小区 社区团购使用
     */
    @Excel(name = "详细信息 取货小区 社区团购使用")
    private String detailAddress;

    /**
     * 提货点 社区团购使用
     */
    @Excel(name = "提货点 社区团购使用")
    private String pickUpAddress;

    /**
     * 提货方式 1 团长送货上门 2 自提
     */
    @Excel(name = "提货方式 1 团长送货上门 2 自提")
    private String deliveryType;

    /**
     * 门牌号 社区团购使用
     */
    @Excel(name = "门牌号 社区团购使用")
    private String houseNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public String getReceiptAddress() {
        return receiptAddress;
    }

    public void setReceiptAddress(String receiptAddress) {
        this.receiptAddress = receiptAddress;
    }

    public String getReceiptDetailAddress() {
        return receiptDetailAddress;
    }

    public void setReceiptDetailAddress(String receiptDetailAddress) {
        this.receiptDetailAddress = receiptDetailAddress;
    }

    public String getReceiptMobile() {
        return receiptMobile;
    }

    public void setReceiptMobile(String receiptMobile) {
        this.receiptMobile = receiptMobile;
    }

    public String getReceiptPhone() {
        return receiptPhone;
    }

    public void setReceiptPhone(String receiptPhone) {
        this.receiptPhone = receiptPhone;
    }

    public String getReceiptZipCode() {
        return receiptZipCode;
    }

    public void setReceiptZipCode(String receiptZipCode) {
        this.receiptZipCode = receiptZipCode;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getInvoiceTaxid() {
        return invoiceTaxid;
    }

    public void setInvoiceTaxid(String invoiceTaxid) {
        this.invoiceTaxid = invoiceTaxid;
    }

    public String getGiftInfos() {
        return giftInfos;
    }

    public void setGiftInfos(String giftInfos) {
        this.giftInfos = giftInfos;
    }

    public String getDonationMessage() {
        return donationMessage;
    }

    public void setDonationMessage(String donationMessage) {
        this.donationMessage = donationMessage;
    }

    public String getInvoiceCompanyName() {
        return invoiceCompanyName;
    }

    public void setInvoiceCompanyName(String invoiceCompanyName) {
        this.invoiceCompanyName = invoiceCompanyName;
    }

    public String getInvoiceRegisterAddress() {
        return invoiceRegisterAddress;
    }

    public void setInvoiceRegisterAddress(String invoiceRegisterAddress) {
        this.invoiceRegisterAddress = invoiceRegisterAddress;
    }

    public String getInvoiceRegisterMobile() {
        return invoiceRegisterMobile;
    }

    public void setInvoiceRegisterMobile(String invoiceRegisterMobile) {
        this.invoiceRegisterMobile = invoiceRegisterMobile;
    }

    public String getInvoiceOpenBank() {
        return invoiceOpenBank;
    }

    public void setInvoiceOpenBank(String invoiceOpenBank) {
        this.invoiceOpenBank = invoiceOpenBank;
    }

    public String getInvoiceBankAccount() {
        return invoiceBankAccount;
    }

    public void setInvoiceBankAccount(String invoiceBankAccount) {
        this.invoiceBankAccount = invoiceBankAccount;
    }

    public String getInvoiceTitleType() {
        return invoiceTitleType;
    }

    public void setInvoiceTitleType(String invoiceTitleType) {
        this.invoiceTitleType = invoiceTitleType;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("receiptName", getReceiptName())
                .append("receiptAddress", getReceiptAddress())
                .append("receiptDetailAddress", getReceiptDetailAddress())
                .append("receiptMobile", getReceiptMobile())
                .append("receiptPhone", getReceiptPhone())
                .append("receiptZipCode", getReceiptZipCode())
                .append("invoiceType", getInvoiceType())
                .append("invoiceTitle", getInvoiceTitle())
                .append("invoiceContent", getInvoiceContent())
                .append("invoiceTaxid", getInvoiceTaxid())
                .append("remark", getRemark())
                .append("giftInfos", getGiftInfos())
                .append("donationMessage", getDonationMessage())
                .append("invoiceCompanyName", getInvoiceCompanyName())
                .append("invoiceRegisterAddress", getInvoiceRegisterAddress())
                .append("invoiceRegisterMobile", getInvoiceRegisterMobile())
                .append("invoiceOpenBank", getInvoiceOpenBank())
                .append("invoiceBankAccount", getInvoiceBankAccount())
                .append("invoiceTitleType", getInvoiceTitleType())
                .append("deliveryTime", getDeliveryTime())
                .append("address", getAddress())
                .append("detailAddress", getDetailAddress())
                .append("pickUpAddress", getPickUpAddress())
                .append("deliveryType", getDeliveryType())
                .append("houseNumber", getHouseNumber())
                .toString();
    }
}
