package com.ruoyi.cms.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * 栏目列表实体类
 *
 * @author 伊甸园商城 on 2017/5/22.
 */
@Data
@ApiModel(description = "栏目列表实体类")
public class ColumnList {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;
    /**
     * 栏目名称
     */
    @ApiModelProperty(value = "栏目名称")
    private String name;
    /**
     * 上级分类id   0 表示无上级
     */
    @ApiModelProperty(value = "上级分类id   0 表示无上级")
    private long parentId;
    /**
     * 排序 数值越小排序越前
     */
    @ApiModelProperty(value = "排序 数值越小排序越前")
    private int sort;
    /**
     * 前端是否显示  0 显示 1 不显示 默认0
     */
    @ApiModelProperty(value = "前端是否显示  0 显示 1 不显示 默认0")
    private String isShow;
    /**
     * 删除标记  0 未删除 1 删除  默认0
     */
    @ApiModelProperty(value = "删除标记  0 未删除 1 删除  默认0")
    private String delFlag;
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

    /**
     * 判断是否为顶级栏目
     */
    @JsonIgnore
    public boolean isParent() {
        return this.parentId == 0;
    }

    List<ArticleList> articleLists;
}
