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
 * 文章信息实体类
 *
 * @author 伊甸园商城 on 2017/5/22.
 */
@Data
@ApiModel(description = "文章信息实体类")
public class ArticleList {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;
    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题")
    private String title;
    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;
    /**
     * 文章描述
     */
    @ApiModelProperty(value = "文章描述")
    private String desc;

    /**
     * 文章所属栏目id
     */
    @ApiModelProperty(value = "文章所属栏目id")
    private long columnId;
    /**
     * 文章栏目名称
     */
    @ApiModelProperty(value = "文章栏目名称")
    private String columnName;
    /**
     * 排序 数值越小 排序越前
     */
    @ApiModelProperty(value = "排序 数值越小 排序越前")
    private int sort;
    /**
     * 是否发布 0 发布 1 不发布  默认0
     */
    @ApiModelProperty(value = "是否发布 0 发布 1 不发布  默认0")
    private String isRelease;
    /**
     * seo关键字
     */
    @ApiModelProperty(value = "seo关键字")
    private String seoKeywords;
    /**
     * seo描述
     */
    @ApiModelProperty(value = "seo描述")
    private String seoDesc;
    /**
     * 删除标记 0 未删除 1 删除
     */
    @ApiModelProperty(value = "删除标记 0 未删除 1 删除")
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
}
