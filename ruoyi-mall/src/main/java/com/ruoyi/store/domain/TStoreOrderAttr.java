package com.ruoyi.store.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 门店订单附属信息对象 t_store_order_attr
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public class TStoreOrderAttr extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单id
     */
    @Excel(name = "订单id")
    private Long orderId;

    /**
     * 发票类型  0 不需要发票 1普通发票 2增值税发票 默认0
     */
    @Excel(name = "发票类型  0 不需要发票 1普通发票 2增值税发票 默认0")
    private String invoiceType;

    /**
     * 发票抬头
     */
    @Excel(name = "发票抬头")
    private String invoiceTitle;

    /**
     * 发票内容 \n1:非图书商品\n2:明细\n3:耗材\n4:办公用品
     */
    @Excel(name = "发票内容 \n1:非图书商品\n2:明细\n3:耗材\n4:办公用品")
    private String invoiceContent;

    /**
     * 税号
     */
    @Excel(name = "税号")
    private String invoiceTaxid;

    /**
     * 抬头类型  1 企业  2 个人
     */
    @Excel(name = "抬头类型  1 企业  2 个人")
    private String invoiceTitleType;

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

    public String getInvoiceTitleType() {
        return invoiceTitleType;
    }

    public void setInvoiceTitleType(String invoiceTitleType) {
        this.invoiceTitleType = invoiceTitleType;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("invoiceType", getInvoiceType())
                .append("invoiceTitle", getInvoiceTitle())
                .append("invoiceContent", getInvoiceContent())
                .append("invoiceTaxid", getInvoiceTaxid())
                .append("remark", getRemark())
                .append("invoiceTitleType", getInvoiceTitleType())
                .append("invoiceCompanyName", getInvoiceCompanyName())
                .append("invoiceRegisterAddress", getInvoiceRegisterAddress())
                .append("invoiceRegisterMobile", getInvoiceRegisterMobile())
                .append("invoiceOpenBank", getInvoiceOpenBank())
                .append("invoiceBankAccount", getInvoiceBankAccount())
                .toString();
    }
}
