package com.ruoyi.common.utils.bean;

import lombok.Data;

/**
 * 微信小程序用户信息实体
 *
 * @author SK
 * @since 2018/6/13
 */
@Data
public class WeChatAppletUserInfo {

    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 包括敏感数据在内的完整用户信息的加密数据，详细见加密数据解密算法
     */
    private String encryptedData;

    /**
     * 加密算法的初始向量，详细见加密数据解密算法
     */
    private String iv;

    /**
     * 使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息，参考文档
     */
    private String signature;

    /**
     * 不包括敏感信息的原始数据字符串，用于计算签名。
     */
    private String rawData;

    /**
     * 联合登录id 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionId;

}
