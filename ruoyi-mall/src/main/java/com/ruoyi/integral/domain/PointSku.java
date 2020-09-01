package com.ruoyi.integral.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 积分商品实体类
 */
@Data
@ApiModel(description = "积分商品实体类")
public class PointSku {


    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 积分商品名称
     */
    @ApiModelProperty(value = "积分商品名称")
    private String name;

    /**
     * 积分商品副标题
     */
    @ApiModelProperty(value = "积分商品副标题")
    private String subTitle;

    /**
     * 积分商品编号
     */
    @ApiModelProperty(value = "积分商品编号")
    private String code;

    /**
     * 商品所需的积分
     */
    @ApiModelProperty(value = "商品所需的积分")
    private int point;

    /**
     * 发布状态 0 未发布 1 发布
     */
    @ApiModelProperty(value = "发布状态 0 未发布 1 发布")
    private String status;

    /**
     * 参考价格
     */
    @ApiModelProperty(value = "参考价格")
    private BigDecimal price;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private int stock;

    /**
     * 积分商品分类id
     */
    @ApiModelProperty(value = "积分商品分类id")
    private long cateId;

    /**
     * 积分商品分类名
     */
    @ApiModelProperty(value = "积分商品分类名")
    private String cateName;

    /**
     * 积分商品图片  多个图片用，分割
     */
    @ApiModelProperty(value = " 积分商品图片  多个图片用，分割")
    private String pics;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String desc;

    /**
     * 移动端描述
     */
    @ApiModelProperty(value = "移动端描述")
    private String mobileDesc;

    /**
     * 删除标记 0 未删 1 已删除
     */
    @ApiModelProperty(value = "删除标记 0 未删 1 已删除")
    private int delFlag;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime delTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;

    /**
     * 填充积分商品分类名
     *
     * @param cateName 积分商品分类名
     * @return 积分商品实体
     */
    public PointSku fillCateName(String cateName) {
        this.cateName = cateName;
        return this;
    }

    /**
     * 判断是否发布，发布返回true
     */
    @JsonIgnore
    public boolean isOnSale() {
        return "1".equals(this.status);
    }

    /**
     * 有详细信息返回true，否则返回false
     */
    @JsonIgnore
    public boolean hasDetailInfo() {
        return !StringUtils.isEmpty(this.name) && this.isOnSale();
    }

}
