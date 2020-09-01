package com.ruoyi.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.function.Consumer;

/**
 * 免密登入参数实体
 */
@Data
@ApiModel(description = "免密登入参数实体")
public class UnAuthLoginParams {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phoneNo;
    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private Long timeStamp;
    /**
     * 请求签名
     */
    @ApiModelProperty(value = "请求签名")
    private String sign;

    /**
     * 渠道类型
     */
    @ApiModelProperty(value = "渠道类型")
    private String channelType;
    /**
     * 回调函数
     */
    private Consumer<Long> consumer;
}
