package com.ruoyi.common.utils.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class WechatNotifyResult {

    /**
     * 返回状态码
     */
    @XmlElement(name = "return_code")
    private String return_code;

    /**
     * 返回信息
     */
    @XmlElement(name = "return_msg")
    private String return_msg;

    /**
     * 公众账号ID
     */
    @XmlElement(name = "appid")
    private String appid;

    /**
     * 商户号
     */
    @XmlElement(name = "mch_id")
    private String mch_id;

    /**
     * 随机字符串
     */
    @XmlElement(name = "nonce_str")
    private String nonce_str;

    /**
     * 签名
     */
    @XmlElement(name = "sign")
    private String sign;

    /**
     * 业务结果
     */
    @XmlElement(name = "result_code")
    private String result_code;

    /**
     * 错误代码
     */
    @XmlElement(name = "err_code")
    private String err_code;

    /**
     * 错误代码描述
     */
    @XmlElement(name = "err_code_des")
    private String err_code_des;

    /**
     * 交易类型
     */
    @XmlElement(name = "trade_type")
    private String trade_type;

    /**
     * 附加参数(交易类型)
     */
    @XmlElement(name = "attach")
    private int attach;

    /**
     * 订单号
     */
    @XmlElement(name = "out_trade_no")
    private String out_trade_no;

    /**
     * 微信交易流水号
     */
    @XmlElement(name = "transaction_id")
    private String transaction_id;

    /**
     * 判断是否失败
     */
    @JsonIgnore
    public boolean isError() {
        return "FAIL".equals(this.return_code);
    }
}
