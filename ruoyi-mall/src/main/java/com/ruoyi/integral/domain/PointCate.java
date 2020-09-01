package com.ruoyi.integral.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by 魔金商城 on 18/1/11.
 * 积分商城分类
 */
@Data
@ApiModel(description = "积分商城分类")
public class PointCate {

    /**
     * 积分商城分类id
     */
    @ApiModelProperty(value = "积分商城分类id")
    private long id;

    /**
     * 积分商城分类名称
     */
    @ApiModelProperty(value = "积分商城分类名称")
    private String name;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private int sort;

    /**
     * 删除标记 0 未删除 1 已删除
     */
    @ApiModelProperty(value = "删除标记 0 未删除 1 已删除")
    private int delFlag;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;

    /**
     * 删除时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime delTime;

}
