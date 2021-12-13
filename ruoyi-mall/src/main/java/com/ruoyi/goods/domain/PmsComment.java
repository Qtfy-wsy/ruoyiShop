package com.ruoyi.goods.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 单品评论对象 pms_comment
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Data
public class PmsComment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 会员id
     */
    @Excel(name = "会员id")
    private Long customerId;
    private Long spuId;
    /**
     * 单品id
     */
    @Excel(name = "单品id")
    private String skuId;

    /**
     * 店铺id
     */
    @Excel(name = "店铺id")
    private Long storeId;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private Long orderId;

    /**
     * 是否匿名  0 否 1是  默认0
     */
    @Excel(name = "是否匿名  0 否 1是  默认0 ")
    private String isAnonymous;

    /**
     * 评论
     */
    @Excel(name = "评论")
    private String comment;

    /**
     * 商品满意度评分1到5分
     */
    @Excel(name = "商品满意度评分1到5分")
    private Long score;

    /**
     * 是否有图片  0 没有 1 有 默认0
     */
    @Excel(name = "是否有图片  0 没有 1 有 默认0 ")
    private String hasPic;

    /**
     * 是否可见 0 可见 1 不可见 默认0
     */
    @Excel(name = "是否可见 0 可见 1 不可见 默认0 ")
    private String isShow;

    /**
     * 是否删除 0 未删除1 删除 默认0
     */
    private int delFlag;

    /**
     * 单品信息
     */
    @ApiModelProperty(value = "单品信息")
    private PmsSku sku;

    /**
     * 发表人名称
     */
    @ApiModelProperty(value = "发表人名称")
    private String customerName;

    /**
     * 发表人头像
     */
    @ApiModelProperty(value = "发表人头像")
    private String customerImage;
    /**
     * 评论图片
     */
    @ApiModelProperty(value = "评论图片")
    private List<PmsCommentPicture> commentPics;

    /**
     * 回复
     */
    @ApiModelProperty(value = "回复")
    private List<PmsCommentReplay> commentReplies;


    /**
     * 设置评论是否有图片
     */
    public void setCommentHasPic() {
        this.hasPic = CollectionUtils.isEmpty(this.commentPics) ? "0" : "1";
    }


    /**
     * 是否匿名
     *
     * @return 是返回true
     */
    @JsonIgnore
    public boolean isAnonymous() {
        return "1".equals(this.isAnonymous);
    }

    /**
     * 设置评论图片的评论id
     */
    public void setPicCommentId() {
        if (!CollectionUtils.isEmpty(this.commentPics)) {
            this.commentPics.stream().forEach(commentPic -> commentPic.setCommentId(this.id));
        }
    }
}
