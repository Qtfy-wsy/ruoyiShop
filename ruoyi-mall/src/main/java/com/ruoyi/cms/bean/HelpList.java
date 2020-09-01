package com.ruoyi.cms.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 帮助列表实体类
 * <p>
 * Created by 魔金商城 on 2017/5/27.
 */
@Data
@ApiModel(description = "帮助列表实体类")
public class HelpList {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 帮助名称
     */
    @ApiModelProperty(value = "帮助名称")
    private String name;

    /**
     * 排序 数组
     */
    @ApiModelProperty(value = "排序 数组")
    private int sort;

    /**
     * 帮助分类id
     */
    @ApiModelProperty(value = "帮助分类id")
    private long helpCateId;

    /**
     * 是否显示 0 显示 1 不显示 默认0
     */
    @ApiModelProperty(value = "是否显示 0 显示 1 不显示 默认0")
    private int isShow;

    /**
     * 帮助内容
     */
    @ApiModelProperty(value = "帮助内容")
    private String desc;

    /**
     * 删除标记 0 未删除 1 删除 默认0
     */
    @ApiModelProperty(value = "删除标记 0 未删除 1 删除 默认0")
    private int delFlag;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime creteTime;

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

    /**
     * 帮助分类名称
     */
    @ApiModelProperty(value = "帮助分类名称")
    private String cateName;
}
