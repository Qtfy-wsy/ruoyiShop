package com.ruoyi.setting.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.utils.bean.OssYunConf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by luozhuo on 20/7/7.
 * 云存储配置类
 */
@Data
@ApiModel(description = "云存储配置实体")
public class OssSetting {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 域名
     */
    @ApiModelProperty(value = "域名")
    private String address;

    /**
     * 授权ID
     */
    @ApiModelProperty(value = "授权ID")
    private String accessKeyId;

    /**
     * 授权密钥
     */
    @ApiModelProperty(value = "授权密钥")
    private String accessKeySecret;

    /**
     * 桶名
     */
    @ApiModelProperty(value = "桶名")
    private String bucketName;

    /**
     * 节点名
     */
    @ApiModelProperty(value = "节点名")
    private String endPoint;

    /**
     * 样式
     */
    @ApiModelProperty(value = "样式")
    private String styleName;

    /**
     * 前缀
     */
    @ApiModelProperty(value = "前缀")
    private String prefix;

    /**
     * 获得阿里云的配置
     *
     * @return 返回阿里云配置
     */
    @JsonIgnore
    public OssYunConf getUpYunConf() {
        return OssYunConf.buildOssYunConf(this.accessKeyId, this.accessKeySecret, this.bucketName, this.endPoint, this.styleName, this.prefix, this.address);
    }
}
