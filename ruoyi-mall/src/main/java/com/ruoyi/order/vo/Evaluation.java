package com.ruoyi.order.vo;

import com.ruoyi.goods.domain.PmsComment;
import com.ruoyi.store.domain.TStoreComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by 伊甸园商城 on 17/12/5.
 * 评论实体
 */
@Data
@ApiModel(description = "评论实体")
public class Evaluation {

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private long customerId;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private long orderId;

    /**
     * 单品评论集合
     */
    @ApiModelProperty(value = "单品评论集合")
    private List<PmsComment> comments;

    /**
     * 店铺评分
     */
    @ApiModelProperty(value = "店铺评分")
    private TStoreComment storeComment;

    /**
     * 构造订单评分
     *
     * @param comments     单品评论
     * @param storeComment 店铺评论
     * @return 返回订单评论
     */
    public static Evaluation build(List<PmsComment> comments, TStoreComment storeComment) {
        Evaluation evaluation = new Evaluation();
        evaluation.comments = comments;
        evaluation.storeComment = storeComment;
        return evaluation;
    }

    /**
     * 设置店铺id和会员id和订单id
     *
     * @param storeId 店铺id
     */
    public void setStoreIdAndCustomerId(long storeId) {
        if (Objects.nonNull(this.storeComment)) {
            this.storeComment.setOrderId(orderId);
            this.storeComment.setStoreId(storeId);
            this.storeComment.setCustomerId(customerId);
        }

        if (!CollectionUtils.isEmpty(this.comments)) {
            this.comments.stream().forEach(comment -> {
                comment.setOrderId(this.orderId);
                comment.setStoreId(storeId);
                comment.setCustomerId(customerId);
            });
        }
    }
}
