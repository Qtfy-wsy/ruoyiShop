package com.ruoyi.cms.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 帮助分类实体类
 *
 * Created by 魔金商城 on 2017/5/23.
 */
@Data
@ApiModel(description = "帮助分类实体类")
public class HelpCategory {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 帮助分类名称
     */
    @ApiModelProperty(value = "帮助分类名称")
    private String name;

    /**
     * 排序 数值越小 排序越前
     */
    @ApiModelProperty(value = "排序 数值越小 排序越前")
    private int sort;
    /**
     * 是否显示  0 显示，1 不显示。默认0
     */
    @ApiModelProperty(value = "是否显示  0 显示，1 不显示。默认0")
    private int isShow;

    /**
     * 删除标记 0 未删除，1 删除。默认0
     */
    @ApiModelProperty(value = "删除标记 0 未删除，1 删除。默认0")
    private int delFlag = 0;

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

    List<HelpList> helpLists ;
}
