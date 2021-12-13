package com.ruoyi.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Created by 伊甸园商城 on 17/12/9.
 * 商品评价概括
 */
@Data
@ApiModel(description = "商品评价概括")
public class CommentSummarize {

    /**
     * 所有评价总数
     */
    @ApiModelProperty(value = "所有评价总数")
    private int allCommentCount;

    /**
     * 好评总数
     */
    @ApiModelProperty(value = "好评总数")
    private int goodCommentCount;

    /**
     * 中评总数
     */
    @ApiModelProperty(value = "中评总数")
    private int middleCommentCount;

    /**
     * 差评总数
     */
    @ApiModelProperty(value = "差评总数")
    private int badCommentCount;

    /**
     * 构造商品评价概括
     *
     * @param allCommentCount    评论总数
     * @param goodCommentCount   好评总数
     * @param middleCommentCount 中评总数
     * @param badCommentCount    差评总数
     * @return 返回评价概括
     */
    public static CommentSummarize build(int allCommentCount, int goodCommentCount, int middleCommentCount, int badCommentCount) {
        CommentSummarize commentSummarize = new CommentSummarize();
        commentSummarize.allCommentCount = allCommentCount;
        commentSummarize.goodCommentCount = goodCommentCount;
        commentSummarize.middleCommentCount = middleCommentCount;
        commentSummarize.badCommentCount = badCommentCount;
        return commentSummarize;
    }

    /**
     * 获得单品的好评百分比 前端使用不要删除
     *
     * @return 返回单品的好评百分比
     */
    public int getGoodPert() {
        // 没有评价 返回0
        if (this.allCommentCount == 0) {
            return 0;
        }

        // 没有好评 则返回 0
        if (this.goodCommentCount == 0) {
            return 0;
        }

        return new BigDecimal(this.goodCommentCount).divide(new BigDecimal(this.allCommentCount), 2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100)).setScale(0).intValue();
    }

    /**
     * 获得中评的百分比 前端使用不要删除
     *
     * @return 获得中评的百分比
     */
    public int getMiddlePert() {
        // 没有评价 返回0
        if (this.allCommentCount == 0) {
            return 0;
        }
        // 没有中评 则返回 0
        if (this.middleCommentCount == 0) {
            return 0;
        }
        return new BigDecimal(this.middleCommentCount).divide(new BigDecimal(this.allCommentCount), 2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100)).setScale(0).intValue();
    }

    /**
     * 获得差评的百分比 前端使用不要删除
     *
     * @return 获得差评的百分比
     */
    public int getBadPert() {
        // 没有评价 返回0
        if (this.allCommentCount == 0) {
            return 0;
        }
        // 没有差评 则返回 0
        if (this.badCommentCount == 0) {
            return 0;
        }

        return new BigDecimal(this.badCommentCount).divide(new BigDecimal(this.allCommentCount), 2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100)).setScale(0).intValue();

    }
}
