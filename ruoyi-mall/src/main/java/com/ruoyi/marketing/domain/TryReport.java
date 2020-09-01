package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 试用报告实体类
 */
@Data
@ApiModel(description = "试用报告实体类")
public class TryReport {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 试用申请id
     */
    @ApiModelProperty(value = "试用申请id")
    private long tryApplyId;

    /**
     * 满意度 1-5分
     */
    @ApiModelProperty(value = "满意度 1-5分")
    private int satisfaction;

    /**
     * 亮点和建议
     */
    @ApiModelProperty(value = "亮点和建议")
    private String advice;

    /**
     * 试用感受
     */
    @ApiModelProperty(value = "试用感受")
    private String feel;

    /**
     * 图片 多个图片用,分割
     */
    @ApiModelProperty(value = "图片 多个图片用,分割")
    private String pics;

    /**
     * 审核状态 1:已审核  0:未审核 默认1
     */
    @ApiModelProperty(value = "审核状态 1:已审核  0:未审核 默认1")
    private String status;

    /**
     * 提交时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "提交时间")
    private LocalDateTime submitTime;

    /**
     * 试用申请实体
     */
    @ApiModelProperty(value = "试用申请实体")
    private TrySkuApply trySkuApply;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String customerName;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String customerPic;
}
