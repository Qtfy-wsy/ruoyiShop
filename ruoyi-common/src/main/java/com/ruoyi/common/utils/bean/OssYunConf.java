package com.ruoyi.common.utils.bean;

import lombok.Data;

/**
 * Created by 魔金商城 on 17/5/8.
 * 又拍云配置
 */

@Data
public class OssYunConf {

    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String endPoint;
    private String styleName;
    private String prefix;
    private String address;

    private OssYunConf() {

    }

    private OssYunConf(String accessKeyId, String accessKeySecret, String bucketName, String endPoint, String styleName, String prefix, String address) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
        this.endPoint = endPoint;
        this.styleName = styleName;
        this.prefix = prefix;
        this.address = address;
    }

    /**
     * @param accessKeyId
     * @param accessKeySecret
     * @param bucketName
     * @param endPoint
     * @param styleName
     * @param prefix
     * @return
     */
    public static OssYunConf buildOssYunConf(String accessKeyId, String accessKeySecret, String bucketName, String endPoint, String styleName, String prefix, String address) {
        return new OssYunConf(accessKeyId, accessKeySecret, bucketName, endPoint, styleName, prefix, address);
    }
}
