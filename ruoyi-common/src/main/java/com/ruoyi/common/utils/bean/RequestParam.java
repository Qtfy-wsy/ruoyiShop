package com.ruoyi.common.utils.bean;


import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import lombok.Data;

/**
 * 发送短信请求变量实体类
 *
 * @author 商城
 */
@Data
public class RequestParam extends SendSmsRequest {
    /**
     * 短信接收号码
     * (支持以逗号分隔的形式进行批量调用,批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式)
     * 样例取值：15000000000
     * 是否必填：是
     */
    private String phoneNumbers;
    /**
     * 短信签名
     * 样例取值：云通信
     * 是否必填：是
     */
    private String signName;
    /**
     * 短信模板ID
     * 样例取值：SMS_0000
     * 是否必填：是
     */
    private String templateCode;
    /**
     * 短信模板变量
     * (友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\r\n,否则会导致JSON在服务端解析失败)
     * 样例取值：{“code”:”1234”,”product”:”ytx”}
     * 是否必填：否
     */
    private String templateParam;
    /**
     * 上行短信扩展码,无特殊需要此字段的用户请忽略此字段
     * 样例取值：90999
     * 是否必填：否
     */
    private String smsUpExtendCode;
    /**
     * 外部流水扩展字段
     * 样例取值：abcdefgh
     * 是否必填：否
     */
    private String outId;
    /**
     * 短信接口URL
     */
    private String interfaceUrl;
    /**
     * accessKeyId
     */
    private String accessKeyId;
    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * 参数
     */
    private String SmsParamString;
}
