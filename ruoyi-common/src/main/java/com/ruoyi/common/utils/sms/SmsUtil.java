package com.ruoyi.common.utils.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.ruoyi.common.utils.bean.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 发送短信工具类
 *
 * @author 伊甸园商城 on 2017/9/7.
 */
public class SmsUtil {

    private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    /**
     * 发送短信-新
     *
     * @param requestParam 请求中的参数实体类
     * @return 0 成功 1失败
     */
    public static int newSendSms(RequestParam requestParam) {
        logger.info("newSendSms response info:{}", requestParam);
        if (Objects.isNull(requestParam)) {
            logger.info("newSendSms error due to requestParam is null");
            return 1;
        }
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = requestParam.getInterfaceUrl();//短信API产品域名（接口地址固定，无需修改）
        //你的accessKeyId
        final String accessKeyId = requestParam.getAccessKeyId();
        //你的accessKeySecret
        final String accessKeySecret = requestParam.getAccessKeySecret();
        //组装请求对象
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        //使用post提交
        sendSmsRequest.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        sendSmsRequest.setPhoneNumbers(requestParam.getPhoneNumbers());
        //必填:短信签名-可在短信控制台中找到
        sendSmsRequest.setSignName(requestParam.getSignName());
        //必填:短信模板-可在短信控制台中找到
        sendSmsRequest.setTemplateCode(requestParam.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为(友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败)
        sendSmsRequest.setTemplateParam("{\"code\":\"" + requestParam.getTemplateParam() + "\"}");
        //1.可选-上行短信扩展码(无特殊需求用户请忽略此字段):request.setSmsUpExtendCode("90997");//2.可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者:request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse;
        try {
            //初始化ascClient,暂时不支持多region
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            sendSmsResponse = new DefaultAcsClient(DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret)).getAcsResponse(sendSmsRequest);
        } catch (ClientException e) {
            logger.error("newSendSms error{}:", e);
            return 1;
        }
        logger.info("newSendSms response code:{} \r\n message:{}", sendSmsResponse.getCode(), sendSmsResponse.getMessage());
        return "OK".equals(sendSmsResponse.getCode()) ? 0 : 1;
    }


    /**
     * 发送短信-新通用
     *
     * @param requestParam 请求中的参数实体类
     * @return 0 成功 1失败
     */
    public static int newSendSmsCommon(RequestParam requestParam) {
        logger.info("newSendSms response info:{}", requestParam);
        if (Objects.isNull(requestParam)) {
            logger.info("newSendSms error due to requestParam is null");
            return 1;
        }
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = requestParam.getInterfaceUrl();//短信API产品域名（接口地址固定，无需修改）
        //你的accessKeyId
        final String accessKeyId = requestParam.getAccessKeyId();
        //你的accessKeySecret
        final String accessKeySecret = requestParam.getAccessKeySecret();
        //组装请求对象
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        //使用post提交
        sendSmsRequest.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        sendSmsRequest.setPhoneNumbers(requestParam.getPhoneNumbers());
        //必填:短信签名-可在短信控制台中找到
        sendSmsRequest.setSignName(requestParam.getSignName());
        //必填:短信模板-可在短信控制台中找到
        sendSmsRequest.setTemplateCode(requestParam.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为(友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败)
        sendSmsRequest.setTemplateParam(requestParam.getSmsParamString());
        //1.可选-上行短信扩展码(无特殊需求用户请忽略此字段):request.setSmsUpExtendCode("90997");//2.可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者:request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse;
        try {
            //初始化ascClient,暂时不支持多region
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            sendSmsResponse = new DefaultAcsClient(DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret)).getAcsResponse(sendSmsRequest);
        } catch (ClientException e) {
            logger.error("newSendSms error{}:", e);
            return 1;
        }
        logger.info("newSendSms response code:{} \r\n message:{}", sendSmsResponse.getCode(), sendSmsResponse.getMessage());
        return "OK".equals(sendSmsResponse.getCode()) ? 0 : 1;
    }


}
