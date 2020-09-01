package com.ruoyi.common.utils.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 统一下单接口返回实体类
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
@ApiModel(description = "统一下单接口返回实体类")
public class PrepayResult {

    /**
     * 返回状态码
     */
    @XmlElement(name = "return_code")
    @ApiModelProperty(value = "返回状态码")
    private String return_code;

    /**
     * 返回信息
     */
    @XmlElement(name = "return_msg")
    @ApiModelProperty(value = "返回信息")
    private String return_msg;

    /**
     * 公众账号ID
     */
    @XmlElement(name = "appid")
    @ApiModelProperty(value = "公众账号ID")
    private String appid;

    /**
     * 商户号
     */
    @XmlElement(name = "mch_id")
    @ApiModelProperty(value = "商户号")
    private String mch_id;

    /**
     * 随机字符串
     */
    @XmlElement(name = "nonce_str")
    @ApiModelProperty(value = "随机字符串")
    private String nonce_str;

    /**
     * 签名
     */
    @XmlElement(name = "sign")
    @ApiModelProperty(value = "签名")
    private String sign;

    /**
     * 业务结果
     */
    @XmlElement(name = "result_code")
    @ApiModelProperty(value = "业务结果")
    private String result_code;

    /**
     * 错误代码
     */
    @XmlElement(name = "err_code")
    @ApiModelProperty(value = "错误代码")
    private String err_code;

    /**
     * 错误代码描述
     */
    @XmlElement(name = "err_code_des")
    @ApiModelProperty(value = "错误代码描述")
    private String err_code_des;

    /**
     * 交易类型
     */
    @XmlElement(name = "trade_type")
    @ApiModelProperty(value = "交易类型")
    private String trade_type;

    /**
     * 预支付交易会话标识
     */
    @XmlElement(name = "prepay_id")
    @ApiModelProperty(value = "预支付交易会话标识")
    private String prepay_id;

    /**
     * 二维码链接
     */
    @XmlElement(name = "code_url")
    @ApiModelProperty(value = "二维码链接")
    private String code_url;

    /**
     * H5支付链接
     */
    @XmlElement(name = "mweb_url")
    @ApiModelProperty(value = "H5支付链接")
    private String mweb_url;

    /**
     * 时间戳（公众号支付时返回）
     */
    @ApiModelProperty(value = "时间戳（公众号支付时返回）")
    private String time_stamp;

    /**
     * 订单详情扩展字符串（公众号支付时返回）
     */
    @ApiModelProperty(value = "订单详情扩展字符串（公众号支付时返回）")
    private String package_;
    /**
     * 签名方式（公众号支付时返回）
     */
    @ApiModelProperty(value = " 签名方式（公众号支付时返回）")
    private String sign_type;
    /**
     * 签名（公众号支付时返回）
     */
    @ApiModelProperty(value = "签名（公众号支付时返回）")
    private String pay_sign;

    /**
     * 生成订单详情扩展字符串
     */
    @JsonIgnore
    public void setPackage() {
        this.package_ = "prepay_id=" + prepay_id;
    }

    /**
     * 判断是否失败
     */
    @JsonIgnore
    public boolean isError() {
        return "FAIL".equals(this.return_code);
    }
}
