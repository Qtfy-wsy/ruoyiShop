package com.ruoyi.member.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created By Caizize on 2018.3.21
 * 平台投诉实体
 */

@Data
@ApiModel(description = "平台投诉实体")
public class Complaints {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private long customerId;

    /**
     * 投诉人用户名
     */
    @ApiModelProperty(value = "投诉人用户名")
    private String username;

    /**
     * 投诉描述
     */
    @ApiModelProperty(value = "投诉描述")
    private String complaintsDesc;

    /**
     * 状态 0未处理 1已处理 默认为0
     */
    @ApiModelProperty(value = "状态 0未处理 1已处理 默认为0")
    private String status;
    // 1功能建议 2BUG反馈 3业务咨询
    private String type;
    private String pics;
    private String mobile;

    /**
     * 投诉回复
     */
    @ApiModelProperty(value = "投诉回复")
    private String complaintsReplay;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String operator;


    /**
     * 添加投诉人用户名
     *
     * @param username
     * @return
     */
    public Complaints addUsername(String username) {
        this.username = username;
        return this;
    }

}
